package com.purplecommerce.interpriseapp.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplecommerce.interpriseapp.Database.ChangeLogDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryDBManager;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.ChangeLogItemsResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ItemInventoryDetailsResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by purplecommerce on 28/08/17.
 */

public class ItemsInventoryService extends IntentService {


    public static final String BROADCASTACTIONITEMS = "ItemsBroadcastAction" ;
    private static final String TAG = "DownloadService";
    ArrayList<String> NewItems = new ArrayList<>();
    SessionManager sm ;
    GsonBuilder gsonBuilder;
    Gson gson;
    public static final String ADDRESPONSE = "1";
    public static final String UPDATERESPONSE = "2";
    public static final String ERRORRESPONSE = "3";
    public static final String NONEWCUSTOMER = "4";


    public ItemsInventoryService() {
        super(ItemsInventoryService.class.getName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "Service Started!");

        String requestUrl = intent.getStringExtra("URL");
        String lastUpdateTime = intent.getStringExtra("TODATETIME");


        ChangeLogDBManager changeLogDBManager  = new ChangeLogDBManager(ItemsInventoryService.this);;
        changeLogDBManager.SaveNewChangeLog(Utils.ItemsChangeLog , lastUpdateTime , ""+requestUrl);

        GetItemsInventoryChangeLog(requestUrl);

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();



    }


    public void GetItemsInventoryChangeLog(final String url){

        Log.e("*** URL",""+url);

        sm = new SessionManager(ItemsInventoryService.this);

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

                NewItems.clear();

                ChangeLogItemsResponse logItemsResponse = new ChangeLogItemsResponse();
                logItemsResponse = gson.fromJson(response , ChangeLogItemsResponse.class);

                for (int i = 0 ; i < logItemsResponse.getData().size() ; i++){

                 //   if (logItemsResponse.getData().get(i).getAttributes().getAction().equals("INSERT")){

                        NewItems.add(logItemsResponse.getData().get(i).getAttributes().getData());

                //    }

                }

                Log.e("**new Items","List"+NewItems);

                if (!NewItems.isEmpty()){

                    for (int j = 0 ; j < NewItems.size() ; j++){

                        GetItemsDetails("/inventory/"+NewItems.get(j));

                    }

                }else {

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCASTACTIONITEMS);
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
                broadcastIntent.setAction(BROADCASTACTIONITEMS);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putExtra("Response", ERRORRESPONSE);
                sendBroadcast(broadcastIntent);

                Toast.makeText(ItemsInventoryService.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void GetItemsDetails(String url){

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


                ItemInventoryDetailsResponse detailsResponse = new ItemInventoryDetailsResponse();
                detailsResponse = gson.fromJson(response , ItemInventoryDetailsResponse.class);

                ItemsInventoryDBManager inventoryDBManager = new ItemsInventoryDBManager(ItemsInventoryService.this);
                JSONObject obj = null;
                byte[] photo = new byte[0];
                String inventoryCode = "";
                try {
                    obj = new JSONObject(response);

                    JSONObject js1 = obj.getJSONObject("data");
                    JSONObject js2 = js1.getJSONObject("attributes");
                    inventoryCode = js2.getString("itemCode");

                    if (js2.optString("photo").equals("")){

                    }else {
                        photo = Base64.decode(obj.getString("photo") , Base64.DEFAULT);
                    }

                    inventoryDBManager.SaveNewItems(js2.optString("itemCode", ""),js2.optString("itemName" , "") , js2.optString("itemType" , "") ,
                            js2.optString("status" , "") , photo , js2.optString("manufacturerCode" , "")  , js2.optString("dateCreated" , "") );


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("**name",""+inventoryDBManager.GetItemsIventoryCount());


                if (inventoryCode.equals(NewItems.get(NewItems.size()-1))){

                    Log.e("**last","add hua"+detailsResponse.getData().getId());

                    if (ItemsInventoryDBManager.AddOrUpdateDB==0){

                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(BROADCASTACTIONITEMS);
                        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        broadcastIntent.putExtra("Response", UPDATERESPONSE);
                        sendBroadcast(broadcastIntent);
                    }else {
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(BROADCASTACTIONITEMS);
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

                Toast.makeText(ItemsInventoryService.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }










}
