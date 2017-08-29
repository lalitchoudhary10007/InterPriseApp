package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.purplecommerce.interpriseapp.Database.ItemsInventoryTable;
import com.purplecommerce.interpriseapp.InventoryItems.InventoryItemsActivity;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerDetailsResponse;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerSalesOrdersResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.Utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.realm.RealmResults;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static java.security.AccessController.getContext;


public class OrdersActivity extends AppCompatActivity {


    LinearLayout Orders_parent_layout , ll_toolbar ;
    TextView nxt , previous , find ;
    int pageCount = 0 , totalPage = 1  , perpagecount = 5 , remainingcount = 0  ;
    EditText ed_srch_order ;
    SessionManager sessionManager ;
    OkHttpClient okHttpClient ;
    final String TAG = "CUSTOMER-ORDERS";
    GsonBuilder gsonBuilder;
    Gson gson;
    Dialog progress_dialog ;
    TextView  OrderPageCount ;
    String CustName , CustCode ;
    List<CustomerSalesOrdersResponse.DataBean> OrdersData ;
    ArrayList<CustomerSalesOrdersResponse.DataBean.AttributesBean> OrderAttributes = new ArrayList<>();
    Spinner OrdersSpinner ;
    String[] spinnerItems = { "SO" , "CRMA" , "BKO"};
    String SelectedOrderType  ;

    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        init();



        Intent ii = getIntent();
       // toolbartxt.setText("HOME > "+ii.getStringExtra("CUST-CODE")+ " > ORDERS");
        CustName = ii.getStringExtra("CUST-CODE");
        CustCode = ii.getStringExtra("CUST-NAME");

        ll_toolbar.addView(Toolbartxts("HOME >"));
        ll_toolbar.addView(Toolbartxts(" "+ii.getStringExtra("CUST-CODE")));
        ll_toolbar.addView(Toolbartxts(" > ORDERS"));

        //customer/salesorder?customercode=CUST-001003&page[number]=1&page[size]=200
        String url = sessionManager.getUrlDetails().get(SessionManager.URL)+"/customer/salesorder?customercode="+ii.getStringExtra("CUST-CODE")+"&page[number]=1&page[size]=200";
        GetCustomerSalesOrders(url ,ii.getStringExtra("CUST-CODE") , ii.getStringExtra("CUST-NAME"));


        OrdersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("**Selected","ordr"+spinnerItems[i]);
                SelectedOrderType = spinnerItems[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderAttributes.clear();

                if (ed_srch_order.getText().toString().isEmpty()){
                    Toast.makeText(OrdersActivity.this, "Please Enter Order Number !!", Toast.LENGTH_SHORT).show();
                }else {

                    Log.e("**Searched Order",""+SelectedOrderType+"-"+ed_srch_order.getText().toString());

                    for (int i = 0 ; i < OrdersData.size(); i++)
                    {
                        if (OrdersData.get(i).getAttributes().getSalesOrderCode().contains(SelectedOrderType+"-"+ed_srch_order.getText().toString()))
                        {

                            OrderAttributes.add(OrdersData.get(i).getAttributes());

                        }
                    }
                    if (OrderAttributes.isEmpty()){
                        Toast.makeText(OrdersActivity.this, "No Order Found !!", Toast.LENGTH_SHORT).show();
                    }else {

                        Log.e("**Order Size","*"+OrderAttributes.size());
                        Orders_parent_layout.removeAllViews();
                        Utils.hideKeyboard(OrdersActivity.this);
                        for (int i = 0 ; i < OrderAttributes.size(); i++){
                            Log.e("**Order Search","*"+OrderAttributes.get(i).getSalesOrderCode());
                          Orders_parent_layout.addView(AddOrdersOnPArent(OrderAttributes.get(i)));
                        }


                    }


                }
            }
        });




        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              nextClick(Orders_parent_layout , OrdersData.size() , perpagecount , hs_page , hs_orderpage , OrderPageCount ,
                      OrdersData);

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               PreviousClick(Orders_parent_layout , OrdersData.size()  , perpagecount , hs_page , hs_orderpage ,OrderPageCount , OrdersData);

            }
        });




    }







    private View AddOrdersOnPArent(final CustomerSalesOrdersResponse.DataBean.AttributesBean attributes) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.layout_orders_include_two, null);

        TextView first = (TextView)vv.findViewById(R.id.first_txt);
        TextView second = (TextView)vv.findViewById(R.id.second_txt);
        TextView third = (TextView)vv.findViewById(R.id.third_txt);

        StringTokenizer tokens = new StringTokenizer(attributes.getSalesOrderDate(), "T");

        first.setText(tokens.nextToken()+"\n"+tokens.nextToken()+"\n"+String.format( "%.2f", Double.parseDouble(""+attributes.getTotal()  ))+"\n"+attributes.getOrderStatus());

        if (attributes.getShipToPhone()==null){
            second.setText(attributes.getSalesOrderCode()+"\n"+attributes.getShipToName()+"\n"+attributes.getShipToAddress()+"\n"+attributes.getShipToCity()+" , "+attributes.getShipToCounty()+" , "+attributes.getShipToPostalCode()
                    +"\n"+attributes.getShipToCountry()+ "\n"+"source : "+attributes.getSourceCode()+"\n"+"PO Code : "+attributes.getPoCode());
        }else {
            second.setText(attributes.getSalesOrderCode()+"\n"+attributes.getShipToName()+"\n"+attributes.getShipToAddress()+"\n"+attributes.getShipToCity()+" , "+attributes.getShipToCounty()+" , "+attributes.getShipToPostalCode()
                    +"\n"+attributes.getShipToCountry()+"\n"+attributes.getShipToPhone() + "\n"+"source : "+attributes.getSourceCode()+"\n"+"PO Code : "+attributes.getPoCode());
        }

      //  second.setText(attributes.getSalesOrderCode()+"\n"+attributes.getShipToName()+"\n"+attributes.getShipToAddress()+"\n"+attributes.getShipToCity()+" , "+attributes.getShipToCounty()+" , "+attributes.getShipToPostalCode()
      //  +"\n"+attributes.getShipToCountry()+"\n"+attributes.getShipToPhone() + "\n"+"source : "+attributes.getSourceCode()+"\n"+"PO Code : "+attributes.getPoCode());
       // txt_amount.setText( String.format( "%.2f", Double.parseDouble(""+total )) );
       // txt_amount.setText(""+total);
       // txt_status.setText(orderStatus);
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrdersActivity.this , OrderDetailsActivity.class);
                i.putExtra("OrderNo",attributes.getSalesOrderCode());
                i.putExtra("CustomeId" , ""+CustCode);
                i.putExtra("CustomerName", ""+attributes.getShipToName());
                i.putExtra("ShipTo" , ""+attributes.getShipToAddress()+"\n"+attributes.getShipToCity()+"\n"+attributes.getShipToCounty()+"\n"+attributes.getShipToCountry());
                i.putExtra("BillTo" , ""+attributes.getBillToAddress()+"\n"+attributes.getBillToCity()+"\n"+attributes.getBillToCounty()+"\n"+attributes.getBillToCountry());
                i.putExtra("SubTotal",""+attributes.getSubTotal());
                i.putExtra("Freight", ""+attributes.getFreight());
                i.putExtra("Total",""+attributes.getTotal());
                startActivity(i);
            }
        });

        return vv ;

    }

    private void init() {

        Orders_parent_layout = (LinearLayout)findViewById(R.id.ll_orders_add_parent);
        nxt = (TextView)findViewById(R.id.txt_next);
        previous = (TextView)findViewById(R.id.txt_previous);
        ed_srch_order = (EditText) findViewById(R.id.ed_search_order);
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        find = (TextView) findViewById(R.id.txt_find);
        OrderPageCount = (TextView)findViewById(R.id.ordr_count_page);
        OrdersSpinner = (Spinner)findViewById(R.id.spinner);
        OrdersSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, R.id.customSpinnerItemTextView , spinnerItems);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OrdersSpinner.setAdapter(dataAdapter);


        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();

        sessionManager = new SessionManager(OrdersActivity.this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(OrdersActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);


    }



    private void GetCustomerSalesOrders(String url, final String customercode, final String customername){

        Log.d("*** URL",""+url);

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

                CustomerSalesOrdersResponse salesOrdersResponse = new CustomerSalesOrdersResponse();
                salesOrdersResponse = gson.fromJson(response , CustomerSalesOrdersResponse.class);
               // totalOrders = salesOrdersResponse.getData().size();
                OrdersData = salesOrdersResponse.getData();


                if (OrdersData.size() < perpagecount){

                    OrderPageCount.setText("1"+"-"+OrdersData.size()+"/"+OrdersData.size());

                    for (int i = 0 ; i < OrdersData.size() ; i++){

                        Orders_parent_layout.addView(AddOrdersOnPArent(OrdersData.get(i).getAttributes()));
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

                    OrderPageCount.setText("1"+"-"+siz+"/"+OrdersData.size());

                    for (int i = 0 ; i < siz ; i++){

                        Orders_parent_layout.addView(AddOrdersOnPArent(OrdersData.get(i).getAttributes()));

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

                Toast.makeText(OrdersActivity.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();


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
                    Intent i = new Intent(OrdersActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals(" > ORDERS")){

                }else {
                    finish();
                }
            }
        });


        return vv ;

    }


    public void nextClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                          ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , List<CustomerSalesOrdersResponse.DataBean> data1){


        if (!hs_orderpage1.isEmpty()) {

            prevoiusOne = "2" ;

            if (pageCount+1 == hs_page1.size()) {
                Toast.makeText(OrdersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
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
                        ll_parent1.addView(AddOrdersOnPArent(data1.get(i).getAttributes()));

                        }

                    } else {

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {
                            ll_parent1.addView(AddOrdersOnPArent(data1.get(i).getAttributes()));
                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(OrdersActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }

    }




    public void PreviousClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                              ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , List<CustomerSalesOrdersResponse.DataBean> data1){

        if (!hs_orderpage1.isEmpty()) {

            if (prevoiusOne.equals("1")){

                Toast.makeText(OrdersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

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

                        ll_parent1.addView(AddOrdersOnPArent(data1.get(i).getAttributes()));
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

                            ll_parent1.addView(AddOrdersOnPArent(data1.get(i).getAttributes()));
                        }



                    }else {

                        ll_parent1.removeAllViews();

                        pagetx1.setText(min+"-"+max+"/"+totaldata1);
                        for (int i = min ; i < max ; i++){

                            ll_parent1.addView(AddOrdersOnPArent(data1.get(i).getAttributes()));
                        }



                    }


                }
                else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(OrdersActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }


    }





}
