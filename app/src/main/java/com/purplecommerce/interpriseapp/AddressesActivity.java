package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.purplecommerce.interpriseapp.SetterGetters.CustomerAddressesResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AddressesActivity extends AppCompatActivity {


    LinearLayout Addresses_parent_layout , ll_toolbar ;
    TextView nxt , previous , find ;
    int pageCount = 0 , totalPage = 1  , perpagecount = 5 , remainingcount = 0  ;
    EditText ed_srch_address ;
    SessionManager sessionManager ;
    OkHttpClient okHttpClient ;
    final String TAG = "CUSTOMER-ADDRESSES";
    GsonBuilder gsonBuilder;
    Gson gson;
    Dialog progress_dialog ;
    TextView  AddressesPageCount ;
    String CustName , CustCode ;
    List<CustomerAddressesResponse.DataBean> OrdersData ;
    ArrayList<CustomerAddressesResponse.DataBean.AttributesBean> OrderAttributes = new ArrayList<>();

    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);


        init();



        Intent ii = getIntent();
        // toolbartxt.setText("HOME > "+ii.getStringExtra("CUST-CODE")+ " > ORDERS");
        CustName = ii.getStringExtra("CUST-CODE");
        CustCode = ii.getStringExtra("CUST-NAME");

        ll_toolbar.addView(Toolbartxts("HOME >"));
        ll_toolbar.addView(Toolbartxts(" "+ii.getStringExtra("CUST-CODE")));
        ll_toolbar.addView(Toolbartxts(" > ADDRESSES"));


        String url = sessionManager.getUrlDetails().get(SessionManager.URL)+"/customer/shipto?customercode="+ii.getStringExtra("CUST-CODE")+"&page[number]=1&page[size]=2000";
        GetCustomerAddresses(url ,ii.getStringExtra("CUST-CODE"));





        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderAttributes.clear();

                if (ed_srch_address.getText().toString().isEmpty()){
                    Toast.makeText(AddressesActivity.this, "Please Enter Order Number !!", Toast.LENGTH_SHORT).show();
                }else {


                    for (int i = 0 ; i < OrdersData.size(); i++)
                    {
                        if (OrdersData.get(i).getAttributes().getShipToCode().contains("SHIP-"+ed_srch_address.getText().toString()))
                        {

                            OrderAttributes.add(OrdersData.get(i).getAttributes());
                        }
                    }
                    if (OrderAttributes.isEmpty()){
                        Toast.makeText(AddressesActivity.this, "No Order Found !!", Toast.LENGTH_SHORT).show();
                    }else {

                        Log.e("**Order Size","*"+OrderAttributes.size());
                        Addresses_parent_layout.removeAllViews();
                        Utils.hideKeyboard(AddressesActivity.this);
                        for (int i = 0 ; i < OrderAttributes.size(); i++){

                            Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(i).getShipToCode(),OrderAttributes.get(i).getShipToName() ,
                                    OrderAttributes.get(i).getAddress(),  OrderAttributes.get(i).getCity() , OrderAttributes.get(i).getPostalCode() ,
                                    OrderAttributes.get(i).getCounty() , OrderAttributes.get(i).getCountry() , OrderAttributes.get(i).getTelephone() ,
                                    OrderAttributes.get(i).getEmail()));
                        }


                    }


                }
            }
        });




        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prevoiusOne = "2" ;

                if (pageCount+1 == hs_page.size()) {
                    Toast.makeText(AddressesActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
                }else {

                    pageCount = pageCount + 1;

                    if (pageCount < hs_page.size()) {

                        Addresses_parent_layout.removeAllViews();
                        OrderAttributes.clear();

                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        int min = Integer.parseInt(hs_orderpage.get(pageCount - 1));

                        Log.e("**max", "" + max);
                        Log.e("**min", "" + min);

                        if (max < perpagecount) {

                            int max2 = min + max;
                            AddressesPageCount.setText(min + "-" + max2 + "/" + OrdersData.size());
                            Log.e("max2", "for last" + max2);

                            for (int i = min; i < max2; i++) {

                                OrderAttributes.add(OrdersData.get(i).getAttributes());

                            }

                            for (int j = 0; j < OrderAttributes.size(); j++) {

                                Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                        OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                        OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                        OrderAttributes.get(j).getEmail()));
                            }


                        } else {



                            AddressesPageCount.setText(min + "-" + max + "/" + OrdersData.size());
                            for (int i = min; i < max; i++) {

                                OrderAttributes.add(OrdersData.get(i).getAttributes());

                            }

                            for (int j = 0; j < OrderAttributes.size(); j++) {

                                Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                        OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                        OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                        OrderAttributes.get(j).getEmail()));

                            }

                        }


                    } else {
                        //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (prevoiusOne.equals("1")){

                    Toast.makeText(AddressesActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

                }else {

                    pageCount = pageCount - 1 ;
                    if (pageCount == 0){

                        prevoiusOne = "1" ;

                        Addresses_parent_layout.removeAllViews();
                        OrderAttributes.clear();

                        Log.e("order page",""+hs_orderpage);
                        Log.e(" page",""+hs_page);
                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        AddressesPageCount.setText("1"+"-"+max+"/"+OrdersData.size());
                        for (int i= 0 ; i < max ; i++){

                            OrderAttributes.add(OrdersData.get(i).getAttributes());

                        }

                        for (int j = 0 ; j < OrderAttributes.size() ; j++){

                            Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                    OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                    OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                    OrderAttributes.get(j).getEmail()));
                        }


                    }else if (pageCount < hs_page.size()){

                        prevoiusOne = "2" ;

                        Addresses_parent_layout.removeAllViews();
                        OrderAttributes.clear();

                        Log.e("order page",""+hs_orderpage);
                        Log.e(" page",""+hs_page);
                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        int min = Integer.parseInt(hs_orderpage.get(pageCount-1));

                        Log.e("**max",""+max);
                        Log.e("**min",""+min);

                        if (max < perpagecount){

                            int max2 = min + max ;
                            AddressesPageCount.setText(min+"-"+max2+"/"+OrdersData.size());
                            Log.e("max2","for last"+max2);
                            for (int i = min ; i < max2 ; i++){

                                OrderAttributes.add(OrdersData.get(i).getAttributes());
                            }

                            for (int j = 0 ; j < OrderAttributes.size() ; j++){

                                Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                        OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                        OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                        OrderAttributes.get(j).getEmail()));
                            }

                        }else {

                            Addresses_parent_layout.removeAllViews();
                            OrderAttributes.clear();
                            AddressesPageCount.setText(min+"-"+max+"/"+OrdersData.size());
                            for (int i = min ; i < max ; i++){

                                OrderAttributes.add(OrdersData.get(i).getAttributes());
                            }

                            for (int j = 0 ; j < OrderAttributes.size() ; j++){

                                Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                        OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                        OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                        OrderAttributes.get(j).getEmail()));
                            }

                        }


                    }
                    else {
                        //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });




    }

    private View AddOrdersOnPArent(String shiptocode, String shiptoname, String address,
                                   String city, String postalcode, String county, String country , String telephone , String email
                                       ) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.layout_orders_include_two, null);

        TextView first = (TextView)vv.findViewById(R.id.first_txt);
        TextView second = (TextView)vv.findViewById(R.id.second_txt);
        TextView third = (TextView)vv.findViewById(R.id.third_txt);


        first.setText(shiptocode);


        second.setText(shiptoname+"\n"+address+"\n"+city+" , "+county+" , "+postalcode
                +"\n"+country+"\n"+telephone + "\n"+email);


        return vv ;

    }

    private void init() {

        Addresses_parent_layout = (LinearLayout)findViewById(R.id.ll_orders_add_parent);
        nxt = (TextView)findViewById(R.id.txt_next);
        previous = (TextView)findViewById(R.id.txt_previous);
        ed_srch_address = (EditText) findViewById(R.id.ed_search_order);
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        find = (TextView) findViewById(R.id.txt_find);
        AddressesPageCount = (TextView)findViewById(R.id.ordr_count_page);



        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();

        sessionManager = new SessionManager(AddressesActivity.this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(AddressesActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);


    }



    private void GetCustomerAddresses(String url, final String customercode){

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

                CustomerAddressesResponse addressesResponse = new CustomerAddressesResponse();
                addressesResponse = gson.fromJson(response , CustomerAddressesResponse.class);
                // totalOrders = salesOrdersResponse.getData().size();
                OrdersData = addressesResponse.getData();


                if (OrdersData.size() < perpagecount){

                    AddressesPageCount.setText("1"+"-"+OrdersData.size()+"/"+OrdersData.size());

                    for (int i = 0 ; i < OrdersData.size() ; i++){

                        OrderAttributes.add(OrdersData.get(i).getAttributes());
                    }

                    for (int j = 0 ; j < OrderAttributes.size() ; j++){

                        Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                OrderAttributes.get(j).getEmail()));

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

                    AddressesPageCount.setText("1"+"-"+siz+"/"+OrdersData.size());

                    for (int i = 0 ; i < siz ; i++){

                        OrderAttributes.add(OrdersData.get(i).getAttributes());

                    }

                    for (int j = 0 ; j < OrderAttributes.size() ; j++){

                        Addresses_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(j).getShipToCode(),OrderAttributes.get(j).getShipToName() ,
                                OrderAttributes.get(j).getAddress(),  OrderAttributes.get(j).getCity() , OrderAttributes.get(j).getPostalCode() ,
                                OrderAttributes.get(j).getCounty() , OrderAttributes.get(j).getCountry() , OrderAttributes.get(j).getTelephone() ,
                                OrderAttributes.get(j).getEmail()));
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

                Toast.makeText(AddressesActivity.this, "Orders Not Found !!", Toast.LENGTH_SHORT).show();

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
                    Intent i = new Intent(AddressesActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals(" > ADDRESSES")){

                }else {
                    finish();
                }
            }
        });


        return vv ;

    }







}
