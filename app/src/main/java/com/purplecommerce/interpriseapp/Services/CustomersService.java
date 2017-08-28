package com.purplecommerce.interpriseapp.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.purplecommerce.interpriseapp.CustomerDetailsActivity;
import com.purplecommerce.interpriseapp.Database.ChangeLogDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.OrdersActivity;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerDetailsResponse;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerSalesOrdersResponse;
import com.purplecommerce.interpriseapp.SetterGetters.CustomersChangeLogResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import io.realm.Realm;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class CustomersService extends IntentService {



    public static final String BROADCASTCUSTOMER = "CustomerBroadcastAction" ;
    private static final String TAG = "DownloadItemsService";
    ArrayList<String> NewCustomers = new ArrayList<>();
    SessionManager sm ;
    GsonBuilder gsonBuilder;
    Gson gson;
    public static final String ADDRESPONSE = "1";
    public static final String UPDATERESPONSE = "2";
    public static final String ERRORRESPONSE = "3";
    public static final String NONEWCUSTOMER = "4";




    public CustomersService() {
        super(CustomersService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        String requestUrl = intent.getStringExtra("URL");
        String lastUpdateTime = intent.getStringExtra("TODATETIME");


//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Get a Realm instance for this thread
//                        Realm realm = Realm.getInstance(CustomersService.this);
//                        changeLogDBManager.SaveNewChangeLog("CustomersChangeLog" , date , ""+url);
//                    }
//                });

        ChangeLogDBManager changeLogDBManager  = new ChangeLogDBManager(CustomersService.this);;
        changeLogDBManager.SaveNewChangeLog(Utils.CustomerChangeLog , lastUpdateTime , ""+requestUrl);



        GetCustomersChangeLog(requestUrl);

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }



    private void GetCustomersChangeLog(final String url){

        Log.e("*** URL ", "" + url);

        sm = new SessionManager(CustomersService.this);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        AndroidNetworking.get(sm.getUrlDetails().get(SessionManager.URL)+url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(Utils.GetOkHttp(sm))
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
                Log.e("*** RESPONSE ", "" + response);

                NewCustomers.clear();

                CustomersChangeLogResponse changeLogResponse = new CustomersChangeLogResponse();
                changeLogResponse = gson.fromJson(response , CustomersChangeLogResponse.class);

                for (int i = 0 ; i < changeLogResponse.getData().size() ; i++){

                    if (changeLogResponse.getData().get(i).getAttributes().getAction().equals("INSERT")){

                     NewCustomers.add(changeLogResponse.getData().get(i).getAttributes().getData());

                    }

                }

                Log.e("**new Customers","List"+NewCustomers);

                if (!NewCustomers.isEmpty()){

                    for (int j = 0 ; j < NewCustomers.size() ; j++){

                        GetCustomersDetails("/customer?customercode="+NewCustomers.get(j));

                    }

                }else {

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCASTCUSTOMER);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putExtra("Response", NONEWCUSTOMER);
                    sendBroadcast(broadcastIntent);

                }


            }

            @Override
            public void onError(ANError anError) {


                Log.e("*errror", "" + anError.getErrorBody());
                Log.e("*errror", "" + anError.getErrorDetail());
                Log.e("*errror", "" + anError.getMessage());
                Log.e("*error", "" + anError.getStackTrace());
                Log.e("*error", "" + anError.getCause());

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse = gson.fromJson(anError.getErrorBody() , ErrorResponse.class);

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(BROADCASTCUSTOMER);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putExtra("Response", ERRORRESPONSE);
                sendBroadcast(broadcastIntent);

                Toast.makeText(CustomersService.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void GetCustomersDetails(String url){

        Log.e("*** URL",""+url);

        AndroidNetworking.get(sm.getUrlDetails().get(SessionManager.URL)+url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(Utils.GetOkHttp(sm))
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


                CustomerDetailsResponse detailsResponse = new CustomerDetailsResponse();
                detailsResponse = gson.fromJson(response , CustomerDetailsResponse.class);

                CustomersDBManager customersDBManager = new CustomersDBManager(CustomersService.this);
                JSONObject obj = null;
                try {
                     obj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                customersDBManager.SaveCustomers(detailsResponse.getData().getId() , detailsResponse.getData().getAttributes().getCustomerName(),
                        detailsResponse.getData().getAttributes().getTelephone() , detailsResponse.getData().getAttributes().getEmail() ,
                        detailsResponse.getData().getAttributes().getDefaultPrice(),""
                        , detailsResponse.getData().getAttributes().getDefaultShipToCode(),detailsResponse.getData().getAttributes().getCountry()
                        ,detailsResponse.getData().getAttributes().getDefaultContact());

                 Log.e("**name",""+customersDBManager.getCustomersCount());


                if (detailsResponse.getData().getId().equals(NewCustomers.get(NewCustomers.size()-1))){

                 Log.e("**last","add hua"+detailsResponse.getData().getId());

                  if (CustomersDBManager.AddOrUpdateDB==0){

                      Intent broadcastIntent = new Intent();
                      broadcastIntent.setAction(BROADCASTCUSTOMER);
                      broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                      broadcastIntent.putExtra("Response", UPDATERESPONSE);
                      sendBroadcast(broadcastIntent);
                  }else {
                      Intent broadcastIntent = new Intent();
                      broadcastIntent.setAction(BROADCASTCUSTOMER);
                      broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                      broadcastIntent.putExtra("Response", ADDRESPONSE);
                      sendBroadcast(broadcastIntent);

                  }



                }else {


                }


            }

            @Override
            public void onError(ANError anError) {

                Log.e("*errror", "" + anError.getErrorBody());
                Log.e("*errror", "" + anError.getErrorDetail());
                Log.e("*errror", "" + anError.getMessage());
                Log.e("*error", "" + anError.getStackTrace());
                Log.e("*error", "" + anError.getCause());

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse = gson.fromJson(anError.getErrorBody() , ErrorResponse.class);

                Toast.makeText(CustomersService.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }





}
