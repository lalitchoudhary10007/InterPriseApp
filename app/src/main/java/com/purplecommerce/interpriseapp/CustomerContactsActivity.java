package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerContactsResponse;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerInvoicesResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CustomerContactsActivity extends AppCompatActivity {


    LinearLayout Contacts_parent_layout , ll_toolbar ;
    TextView nxt , previous , find ;
    int pageCount = 0 , totalPage = 1  , perpagecount = Utils.PerPageCount , remainingcount = 0  ;
    EditText ed_srch_contacts ;
    SessionManager sessionManager ;
    OkHttpClient okHttpClient ;
    final String TAG = "CUSTOMER-CONTACTS";
    GsonBuilder gsonBuilder;
    Gson gson;
    Dialog progress_dialog ;
    TextView  ContactsPageCount ;
    String CustName , CustCode ;
    List<CustomerContactsResponse.DataBean> OrdersData ;
    ArrayList<CustomerContactsResponse.DataBean.AttributesBean> OrderAttributes = new ArrayList<>();

    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_contacts);


        init();



        Intent ii = getIntent();
        CustName = ii.getStringExtra("CUST-CODE");
        CustCode = ii.getStringExtra("CUST-NAME");

        ll_toolbar.addView(Toolbartxts("HOME >"));
        ll_toolbar.addView(Toolbartxts(" "+ii.getStringExtra("CUST-CODE")));
        ll_toolbar.addView(Toolbartxts(" > CONTACTS"));


        String url = sessionManager.getUrlDetails().get(SessionManager.URL)+"/customer/contact?customercode="+ii.getStringExtra("CUST-CODE")+"&page[number]=1&page[size]=2000";
        GetCustomerContacts(url ,ii.getStringExtra("CUST-CODE"));


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderAttributes.clear();

                if (ed_srch_contacts.getText().toString().isEmpty()){
                    Toast.makeText(CustomerContactsActivity.this, "Please Enter Order Number !!", Toast.LENGTH_SHORT).show();
                }else {


                    for (int i = 0 ; i < OrdersData.size(); i++)
                    {
                        if (OrdersData.get(i).getAttributes().getContactCode().contains("CCTC-"+ed_srch_contacts.getText().toString()))
                        {
                        OrderAttributes.add(OrdersData.get(i).getAttributes());
                        }
                    }
                    if (OrderAttributes.isEmpty()){
                        Toast.makeText(CustomerContactsActivity.this, "No Order Found !!", Toast.LENGTH_SHORT).show();
                    }else {

                        Log.e("**Order Size","*"+OrderAttributes.size());
                        Contacts_parent_layout.removeAllViews();
                        Utils.hideKeyboard(CustomerContactsActivity.this);
                        for (int i = 0 ; i < OrderAttributes.size(); i++){

                            Contacts_parent_layout.addView(AddContactsOnPArent(OrderAttributes.get(i)));
                        }


                    }


                }
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         nextClick(Contacts_parent_layout , OrdersData.size() , perpagecount , hs_page , hs_orderpage , ContactsPageCount , OrdersData);

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        PreviousClick(Contacts_parent_layout , OrdersData.size() , perpagecount , hs_page , hs_orderpage , ContactsPageCount , OrdersData);


            }
        });




    }


    private View AddContactsOnPArent(CustomerContactsResponse.DataBean.AttributesBean attributesBean) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.layout_orders_include_two, null);

        TextView first = (TextView)vv.findViewById(R.id.first_txt);
        TextView second = (TextView)vv.findViewById(R.id.second_txt);
        TextView third = (TextView)vv.findViewById(R.id.third_txt);


        first.setText(attributesBean.getContactCode());

        second.setText(attributesBean.getContactFirstName()+" "+attributesBean.getContactLastName() +"\n"+attributesBean.getAddress()+"\n"+attributesBean.getCity()+" , "+attributesBean.getCounty()+" , "+attributesBean.getPostalCode()
                +"\n"+attributesBean.getCountry()+"\n"+attributesBean.getBusinessPhone() + "\n"+attributesBean.getEmail1());


        return vv ;

    }

    private void init() {

        Contacts_parent_layout = (LinearLayout)findViewById(R.id.ll_orders_add_parent);
        nxt = (TextView)findViewById(R.id.txt_next);
        previous = (TextView)findViewById(R.id.txt_previous);
        ed_srch_contacts = (EditText) findViewById(R.id.ed_search_order);
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        find = (TextView) findViewById(R.id.txt_find);
        ContactsPageCount = (TextView)findViewById(R.id.ordr_count_page);



        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();

        sessionManager = new SessionManager(CustomerContactsActivity.this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(CustomerContactsActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);


    }


    private void GetCustomerContacts(String url, final String customercode){

        progress_dialog.show();

        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsString(new StringRequestListener() {


            @Override
            public void onResponse(String response) {
                Log.d("*** RESPONSE ", "" + response);

                progress_dialog.dismiss();
                OrderAttributes.clear();

                CustomerContactsResponse contactsResponse = new CustomerContactsResponse();
                contactsResponse = gson.fromJson(response , CustomerContactsResponse.class);
                // totalOrders = salesOrdersResponse.getData().size();
                OrdersData = contactsResponse.getData();


                if (OrdersData.size() < perpagecount){

                    ContactsPageCount.setText("1"+"-"+OrdersData.size()+"/"+OrdersData.size());

                    for (int i = 0 ; i < OrdersData.size() ; i++){

                        OrderAttributes.add(OrdersData.get(i).getAttributes());
                    }

                    for (int j = 0 ; j < OrderAttributes.size() ; j++){

                        Contacts_parent_layout.addView(AddContactsOnPArent(OrderAttributes.get(j)));

                    }



                }else {

                    hs_page.clear();
                    hs_orderpage.clear();

                    pageCount = 0 ;

                    totalPage = OrdersData.size() / perpagecount ;
                    remainingcount = OrdersData.size() % perpagecount ;

                    for (int i = 1 ; i <= totalPage ; i++){
                        hs_page.add(""+i);
                        hs_orderpage.add(String.valueOf(i*perpagecount));
                    }
                    if (remainingcount == 0){
                        // Toast.makeText(OrdersActivity.this, "No remain", Toast.LENGTH_SHORT).show();
                    }else {
                        // Toast.makeText(OrdersActivity.this, "remains", Toast.LENGTH_SHORT).show();
                        hs_page.add(String.valueOf(totalPage+1));
                        hs_orderpage.add(String.valueOf(remainingcount));
                    }
                    int siz = Integer.parseInt(hs_orderpage.get(pageCount));

                    ContactsPageCount.setText("1"+"-"+siz+"/"+OrdersData.size());

                    for (int i = 0 ; i < siz ; i++){

                        OrderAttributes.add(OrdersData.get(i).getAttributes());

                    }

                    for (int j = 0 ; j < OrderAttributes.size() ; j++){

                        Contacts_parent_layout.addView(AddContactsOnPArent(OrderAttributes.get(j)));
                    }

                }



            }

            @Override
            public void onError(ANError anError) {

                progress_dialog.dismiss();

                Log.e("*errror", "" + anError.getErrorBody());
                Log.e("*errror", "" + anError.getErrorDetail());
                Log.e("*errror", "" + anError.getMessage());
                Log.e("*error", "" + anError.getStackTrace());
                Log.e("*error", "" + anError.getCause());

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse = gson.fromJson(anError.getErrorBody() , ErrorResponse.class);

                Toast.makeText(CustomerContactsActivity.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private View Toolbartxts(String txt){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.tool_bar_txt_view, null);
        final TextView toolbartxt = (TextView)vv.findViewById(R.id.toolbar_txt);
        toolbartxt.setText(txt);

        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toolbartxt.getText().toString().equals("HOME >")){
                    finish();
                    Intent i = new Intent(CustomerContactsActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals(" > CONTACTS")){

                }else {
                    finish();
                }
            }
        });


        return vv ;

    }


    public void nextClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                          ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , List<CustomerContactsResponse.DataBean> data1){


        if (!hs_orderpage1.isEmpty()) {

            prevoiusOne = "2" ;

            if (pageCount+1 == hs_page1.size()) {
                Toast.makeText(CustomerContactsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
            }else {

                pageCount = pageCount + 1;

                if (pageCount < hs_page1.size()) {

                    ll_parent1.removeAllViews();

                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    int min = Integer.parseInt(hs_orderpage1.get(pageCount - 1));

                    Log.e("**max", "" + max);
                    Log.e("**min", "" + min);

                    if (max < perpagecount1) {

                        int max2 = min + max;
                        pagetx1.setText(min + "-" + max2 + "/" + totaldata1);
                        Log.e("max2", "for last" + max2);

                        for (int i = min; i < max2; i++) {
                            ll_parent1.addView(AddContactsOnPArent(data1.get(i).getAttributes()));

                        }

                    } else {

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {
                            ll_parent1.addView(AddContactsOnPArent(data1.get(i).getAttributes()));
                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(CustomerContactsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }

    }




    public void PreviousClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                              ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , List<CustomerContactsResponse.DataBean> data1){

        if (!hs_orderpage1.isEmpty()) {

            if (prevoiusOne.equals("1")){

                Toast.makeText(CustomerContactsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

            }else {

                if (pageCount==0){

                }else {
                    pageCount = pageCount - 1;
                }


                if (pageCount == 0){

                    prevoiusOne = "1" ;

                    ll_parent1.removeAllViews();

                    Log.e("order page",""+hs_orderpage);
                    Log.e(" page",""+hs_page);
                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    pagetx1.setText("1"+"-"+max+"/"+totaldata1);
                    for (int i= 0 ; i < max ; i++){

                        ll_parent1.addView(AddContactsOnPArent(data1.get(i).getAttributes()));
                    }




                }else if (pageCount < hs_page1.size()){

                    prevoiusOne = "2" ;

                    Log.e("**page","count"+pageCount);

                    ll_parent1.removeAllViews();

                    Log.e("order page",""+hs_orderpage);
                    Log.e(" page",""+hs_page);
                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    int min = Integer.parseInt(hs_orderpage1.get(pageCount-1));

                    Log.e("**max",""+max);
                    Log.e("**min",""+min);

                    if (max < perpagecount1){

                        int max2 = min + max ;
                        pagetx1.setText(min+"-"+max2+"/"+totaldata1);
                        Log.e("max2","for last"+max2);
                        for (int i = min ; i < max2 ; i++){

                            ll_parent1.addView(AddContactsOnPArent(data1.get(i).getAttributes()));
                        }



                    }else {

                        ll_parent1.removeAllViews();

                        pagetx1.setText(min+"-"+max+"/"+totaldata1);
                        for (int i = min ; i < max ; i++){

                            ll_parent1.addView(AddContactsOnPArent(data1.get(i).getAttributes()));
                        }



                    }


                }
                else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(CustomerContactsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }


    }







}
