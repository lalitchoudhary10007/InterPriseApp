package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
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
import com.google.gson.JsonObject;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerDetailsResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.StringTokenizer;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CustomerDetailsActivity extends AppCompatActivity {

    TextView name , address , telephone , fax , email , website , since , Addresses , Contacts , Orders , Invoices ;
     SessionManager sessionManager ;
    OkHttpClient okHttpClient ;
    final String TAG = "CUSTOMERDETAILS";
    GsonBuilder gsonBuilder;
    Gson gson;
    Dialog progress_dialog ;
    String Customer_Code ;
    FrameLayout ContentFrame ;
    TextView tool_bar_txt;
    LinearLayout ll_telephone , ll_fax , ll_mail , ll_website , ll_since  , ll_toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);


        init();

        Intent i = getIntent();

        ll_toolbar.addView(Toolbartxts("HOME > "));
        ll_toolbar.addView(Toolbartxts("CUSTOMERS > "));
        ll_toolbar.addView(Toolbartxts(""+i.getStringExtra("CustCode")));

        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();


        Customer_Code = i.getStringExtra("CustCode");
        //CUST-001003
        String url = sessionManager.getUrlDetails().get(SessionManager.URL)+"/customer?customercode="+i.getStringExtra("CustCode");
        GetCustomerDetails(url);


        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(CustomerDetailsActivity.this , OrdersActivity.class);
                ii.putExtra("CUST-CODE",Customer_Code);
                ii.putExtra("CUST-NAME", name.getText().toString());
                startActivity(ii);

            }
        });

        Addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(CustomerDetailsActivity.this , AddressesActivity.class);
                ii.putExtra("CUST-CODE",Customer_Code);
                ii.putExtra("CUST-NAME", name.getText().toString());
                startActivity(ii);

            }
        });

        Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(CustomerDetailsActivity.this , CustomerContactsActivity.class);
                ii.putExtra("CUST-CODE",Customer_Code);
                ii.putExtra("CUST-NAME", name.getText().toString());
                startActivity(ii);
            }
        });

        Invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(CustomerDetailsActivity.this , InvoicesActivity.class);
                ii.putExtra("CUST-CODE",Customer_Code);
                ii.putExtra("CUST-NAME", name.getText().toString());
                startActivity(ii);
            }
        });





    }

    private void init() {
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        name = (TextView)findViewById(R.id.txt_name);
        address = (TextView)findViewById(R.id.txt_address);
        telephone = (TextView)findViewById(R.id.txt_customer_telephone);
        fax = (TextView)findViewById(R.id.txt_fax);
        email = (TextView)findViewById(R.id.txt_email);
        website = (TextView)findViewById(R.id.txt_website);
        since = (TextView)findViewById(R.id.txt_since);
        Addresses = (TextView)findViewById(R.id.txt_addresses);
        Contacts = (TextView)findViewById(R.id.txt_contacts);
        Orders = (TextView)findViewById(R.id.txt_orders);
        Invoices = (TextView)findViewById(R.id.txt_invoices);
        ContentFrame = (FrameLayout)findViewById(R.id.content_frame);
        ContentFrame.setVisibility(View.GONE);

        ll_telephone = (LinearLayout)findViewById(R.id.ll_telephone);
        ll_fax = (LinearLayout)findViewById(R.id.ll_fax);
        ll_mail = (LinearLayout)findViewById(R.id.ll_email);
        ll_website = (LinearLayout)findViewById(R.id.ll_website);
        ll_since = (LinearLayout)findViewById(R.id.ll_since);


        sessionManager = new SessionManager(CustomerDetailsActivity.this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(CustomerDetailsActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);

    }


    private void GetCustomerDetails(String url){

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
                ContentFrame.setVisibility(View.VISIBLE);
                progress_dialog.dismiss();

                CustomerDetailsResponse detailsResponse = new CustomerDetailsResponse();
                detailsResponse = gson.fromJson(response , CustomerDetailsResponse.class);

                JSONObject obj = null ;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("**customer name",""+detailsResponse.getData().getAttributes().getCustomerName());

                name.setText(detailsResponse.getData().getAttributes().getCustomerName());
                address.setText(detailsResponse.getData().getAttributes().getAddress()+" , "+detailsResponse.getData().getAttributes().getCity()
                +" , "+detailsResponse.getData().getAttributes().getPostalCode()+" , "+detailsResponse.getData().getAttributes().getCounty()+" , "
                +detailsResponse.getData().getAttributes().getCounty());



                if (detailsResponse.getData().getAttributes().getTelephone()==null){
                    ll_telephone.setVisibility(View.GONE);
                }else {
                    ll_telephone.setVisibility(View.VISIBLE);
                    telephone.setText(detailsResponse.getData().getAttributes().getTelephone());
                }


                if (detailsResponse.getData().getAttributes().getFax()==null){
                    ll_fax.setVisibility(View.GONE);
                }else {
                    ll_fax.setVisibility(View.VISIBLE);
                    fax.setText(detailsResponse.getData().getAttributes().getFax());
                }
                if (detailsResponse.getData().getAttributes().getEmail()==null){
                    ll_mail.setVisibility(View.GONE);
                }else {
                    ll_mail.setVisibility(View.VISIBLE);
                    email.setText(detailsResponse.getData().getAttributes().getEmail());
                }
                if (detailsResponse.getData().getAttributes().getWebsite()==null){
                    ll_website.setVisibility(View.GONE);
                }else {
                    ll_website.setVisibility(View.VISIBLE);
                    website.setText(detailsResponse.getData().getAttributes().getWebsite());
                }
                if (obj.opt("dateCreated")==null){
                    ll_since.setVisibility(View.GONE);
                }else {
                    ll_since.setVisibility(View.VISIBLE);
                    StringTokenizer tokens = new StringTokenizer(detailsResponse.getData().getAttributes().getDateCreated(), "T");
                    since.setText(tokens.nextToken()+"\n"+tokens.nextToken());

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

                Toast.makeText(CustomerDetailsActivity.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

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
                if (toolbartxt.getText().toString().equals("HOME > ")){
                    finish();
                    Intent i = new Intent(CustomerDetailsActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals("CUSTOMERS > ")){
                    finish();
                }else {

                }
            }
        });


        return vv ;

    }




}
