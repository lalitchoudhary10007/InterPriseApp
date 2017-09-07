package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.Database.InvoicesDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryDBManager;
import com.purplecommerce.interpriseapp.Database.SalesOrdersDBManager;
import com.purplecommerce.interpriseapp.Services.ItemsInventoryService;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

public class ApiAuthenticationActivity extends AppCompatActivity {


    EditText ed_Url , ed_Key ;
    TextView  submit ;
    Dialog progress_dialog ;
    ApiManager apiManager ;
    final String ConnectUrl = "verify" ;

    final String TAG = "URlKEYVERIFY";
   // customer?onlyActive=true
    GsonBuilder gsonBuilder;
    Gson gson;
    SessionManager sm ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_authentication);


        //http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/customer?onlyActive=true

        init();





        if (sm.isLoggedIn()){

            ed_Url.setText(sm.getUrlDetails().get(SessionManager.URL));
            ed_Key.setText(sm.getUrlDetails().get(SessionManager.KEY));
            ed_Key.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_Key.setSelection(ed_Key.getText().length());


        }else {
            ed_Url.setText("");
            ed_Key.setText("");
            ed_Url.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_URI);
            ed_Key.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_URI);
        }

        ed_Url.setText("http://test.interprise.co.uk:82/Interprise.Web.Services.issidemouk");
        ed_Key.setText("73a0d866-770a-46be-a26f-1b837ce5a371");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {

                        String credential = Credentials.basic(ed_Key.getText().toString().trim() , "");


                        return response.request().newBuilder().header("Authorization" , credential).build();
                    }
                }).build();


                if (ed_Url.getText().toString().isEmpty()){
                    Toast.makeText(ApiAuthenticationActivity.this, "Please Enter URL !!", Toast.LENGTH_SHORT).show();
                }else if (ed_Key.getText().toString().isEmpty()){
                    Toast.makeText(ApiAuthenticationActivity.this, "Please Enter Key !!", Toast.LENGTH_SHORT).show();
                }else if (ed_Url.getText().toString().equals(sm.getUrlDetails().get(SessionManager.URL))&&ed_Key.getText().toString().equals(sm.getUrlDetails().get(SessionManager.KEY))){

                    startActivity(new Intent(ApiAuthenticationActivity.this , MainActivity.class));
                    finish();
                }

                else {
                    String url = ed_Url.getText().toString()+"/customer?onlyActive=true";

                    VerifyKeyUrl(url , ed_Key.getText().toString() , ed_Url.getText().toString() ,okHttpClient);



                }




            }
        });




    }

    private void init() {

        ed_Url = (EditText)findViewById(R.id.ed_url);
        ed_Key = (EditText)findViewById(R.id.ed_key);
        submit = (TextView) findViewById(R.id.txt_submit);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(ApiAuthenticationActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);
      //  apiManager = new ApiManager(ApiAuthenticationActivity.this);
        sm = new SessionManager(ApiAuthenticationActivity.this);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    public void VerifyKeyUrl(final String url, final String key, final String BaseUrl, OkHttpClient httpClient){

        progress_dialog.show();

        Log.d("*** URL ", "" + url);

        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(httpClient)
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


                sm.CreateUrlCredentials(BaseUrl , key);

                CustomersDBManager customersDBManager = new CustomersDBManager(ApiAuthenticationActivity.this);
                InvoicesDBManager invoicesDBManager = new InvoicesDBManager(ApiAuthenticationActivity.this);
                SalesOrdersDBManager ordersDBManager = new SalesOrdersDBManager(ApiAuthenticationActivity.this);
                ItemsInventoryDBManager itemsInventoryDBManager = new ItemsInventoryDBManager(ApiAuthenticationActivity.this);


                    invoicesDBManager.SaveNewOrders("INV-000001" , "2007-01-23T00:00:00" , "2007-01-23T00:00:00" ,
                         "2007-01-23T00:00:00" , "127.34" , "116.34" , "SO-000003" , "Close");
                    invoicesDBManager.SaveNewOrders("INV-000002" , "2007-01-23T00:00:00" , "2007-01-23T00:00:00" ,
                            "2007-01-23T00:00:00" , "150.48" , "137.48" , "SO-000004" , "Close");
                    invoicesDBManager.SaveNewOrders("INV-000003" , "2007-01-23T00:00:00" , "2007-01-23T00:00:00" ,
                            "2007-01-23T00:00:00" , "1053.33" , "394.34" , "SO-000005" , "Close");
                    invoicesDBManager.SaveNewOrders("INV-000004" , "2007-01-23T00:00:00" , "2007-01-23T00:00:00" ,
                            "2007-01-23T00:00:00" , "0.00" , "0.00" , "SO-000009" , "Close");
                    invoicesDBManager.SaveNewOrders("INV-000005" , "2007-01-23T00:00:00" , "" ,
                            "2007-01-23T00:00:00" , "0.00" , "0.00" , "[To be generated]" , "Close");

                    customersDBManager.SaveCustomers("CUST-000001" , "Toy Prospect UK Ltd" , "0800 032 4000" ,"info@toyprospect.co.uk" , "Retail" ,"" , "SHIP-000002" , "United Kingdom" , "CCTC-000002");
                    customersDBManager.SaveCustomers("CUST-000002" , "Undergound Toys UK" , "0194 494839" ,"simon.mermon@underground.co.uk" , "Wholesale" ,"" , "SHIP-000003" , "United Kingdom" , "CCTC-000003");
                    customersDBManager.SaveCustomers("CUST-000003" , "The Gadget Store" , "0191 948392" ,"tony.sandland@gadgetstore.co.uk" , "Wholesale" ,"" , "SHIP-000004" , "United Kingdom" , "CCTC-000004");
                    customersDBManager.SaveCustomers("CUST-000004" , "We Love Toys" , "01978 828348" ,"cherry.mcclenent@lovetoys.co.uk" , "Wholesale" ,"" , "SHIP-000005" , "United Kingdom" , "CCTC-000005");
                    customersDBManager.SaveCustomers("CUST-000005" , "All Toys R Us" , "01244 59403" ,"ali.mcaulay@alltoys.co.uk" , "Wholesale" ,"" , "SHIP-000006" , "United Kingdom" , "CCTC-000006");


                    ordersDBManager.SaveNewOrders("QU-000001" , "2008-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                            "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "1768.74" , "Close" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                            "Manchester","Lancashire" , "United Kingdom" , "1528.00" ,"0.00");
                    ordersDBManager.SaveNewOrders("QU-000002" , "2008-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                            "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "129.25" , "Close" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                            "Manchester","Lancashire" , "United Kingdom" , "100.00" ,"10.00");

                    ordersDBManager.SaveNewOrders("QU-000003" , "2007-01-19T00:00:00" , "A French Customer" , "3000 De La Rue" ,
                            "Paris" ,"AR123" , "PAR123" , "France" , "Unknown" , "", "400.00" , "Close" , "0800 111 2100" , "3000 De La Rue",
                            "Paris","AR123" , "France" , "400.00" ,"0.00");

                    ordersDBManager.SaveNewOrders("QU-000004" , "2008-01-23T00:00:00" , "The Gadget Store" , "Summerville House\\r\\n20-22 Harlow Road\\r\\nHarlow" ,
                            "London" ,"" , "B15 2AA" , "United Kingdom" , "Internet" , "", "6283.90" , "Close" , "0191 948392" , "Summerville House\\r\\n20-22 Harlow Road\\r\\nHarlow",
                            "London","" , "United Kingdom" , "5338.00" ,"10.00");

                    ordersDBManager.SaveNewOrders("QU-000005" , "2008-01-23T00:00:00" , "Bikes and Trikes Ltd" , "\\r\\n13 Ashcroft Terrace" ,
                            "Burnley" ,"Lancashire" , "BB10 1QW" , "United Kingdom" , "Internet" , "", "714.62" , "Close" , "01282428676" , "\\r\\n13 Ashcroft Terrace",
                            "Burnley","Lancashire" , "United Kingdom" , "600.00" ,"10.00");

                    ordersDBManager.SaveNewOrders("SO-000001" , "2007-01-23T00:00:00" , "A US Customer Inc" , "1400 Roxton Avenue" ,
                            "Beverly Hills" ,"Los Angeles" , "90210" , "United States of America" , "Unknown" , "", "101.03" , "Completed" , "0870 445 332" , "1400 Roxton Avenue",
                            "Beverly Hills","Los Angeles" , "United States of America" , "101.03" ,"0.00");

                    ordersDBManager.SaveNewOrders("SO-000002" , "2007-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                            "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "1618.26" , "Open" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                            "Manchester","Lancashire" , "United Kingdom" , "1398.00" ,"0.00");

                    ordersDBManager.SaveNewOrders("SO-000003" , "2007-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                        "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "127.34" , "Completed" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                        "Manchester","Lancashire" , "United Kingdom" , "100.00" ,"10.00");


                    ordersDBManager.SaveNewOrders("SO-000004" , "2007-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                        "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "92611.58" , "Completed" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                        "Manchester","Lancashire" , "United Kingdom" , "80000.00" ,"10.00");

                    ordersDBManager.SaveNewOrders("SO-000005" , "2007-01-23T00:00:00" , "A Toy Customer" , "845 Evergreen Terrace, Eastham Road." ,
                        "Manchester" ,"Lancashire" , "M54 5TG" , "United Kingdom" , "Internet" , "", "1053.33" , "Completed" , "0870 555 3200" , "845 Evergreen Terrace, Eastham Road.",
                        "Manchester","Lancashire" , "United Kingdom" , "900.00" ,"10.00");


                    byte[] photo = Base64.decode(Utils.image , Base64.DEFAULT);

                    byte[] photo1 = Base64.decode("" , Base64.DEFAULT);

                    itemsInventoryDBManager.SaveNewItems("ITEM-000001","Hand Gun" , "Stock" , "A" , photo ,"Guns R Us" , "2006-12-31T08:58:30.657");
                    itemsInventoryDBManager.SaveNewItems("ITEM-000002","Bullets" , "Stock" , "A" , photo1 ,"" , "2006-12-31T09:00:15.453");
                    itemsInventoryDBManager.SaveNewItems("ITEM-000003","Holster" , "Stock" , "A" , photo1 ,"" , "2006-12-31T09:02:12.483");
                    itemsInventoryDBManager.SaveNewItems("ITEM-000004","Installation 1 Day" , "Service" , "A" , photo1 ,"" , "2006-12-31T09:03:24.5");
                    itemsInventoryDBManager.SaveNewItems("ITEM-000005","2Ghz Motherboard" , "Stock" , "A" , photo1 ,"" , "2006-12-31T09:07:37.25");



                progress_dialog.dismiss();

                startActivity(new Intent(ApiAuthenticationActivity.this , MainActivity.class));
                finish();
            }

            @Override
            public void onError(ANError anError) {
                progress_dialog.dismiss();
                Log.e("errror", "" + anError.getErrorBody());
                Log.e("errror", "" + anError.getErrorDetail());
                Log.e("errror", "" + anError.getMessage());
                Log.e("error", "" + anError.getStackTrace());
                Log.e("error", "" + anError.getCause());

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse = gson.fromJson(anError.getErrorBody() , ErrorResponse.class);

                Toast.makeText(ApiAuthenticationActivity.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });




    }


}
