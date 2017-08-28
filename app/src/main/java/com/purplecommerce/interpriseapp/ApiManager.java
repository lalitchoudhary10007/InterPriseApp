package com.purplecommerce.interpriseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


/**
 * Created by samir on 30/01/17.
 */

public class ApiManager {

    public Context context;
    String url;
    HashMap map;
    GsonBuilder gsonBuilder;
    Gson gson;
    APIFETCHER apifetcher;

    private static final String TAG = "APIExecution";


    OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {

            String credential = Credentials.basic("0ab63a19-d325-4439-90ba-a845611bacbe" , "");


            return response.request().newBuilder().header("Authorization" , credential).build();
        }
    }).build();



    public ApiManager(APIFETCHER apifetcher) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        map = new HashMap();
        this.apifetcher = apifetcher;
    }


    @SuppressLint("LongLogTag")
    public void execution_method_post(final String tag, String url , HashMap<String , String > bodyparameter) {
        Log.d("** Executing API (POST) ", "Hashparameters => " +bodyparameter);
        Log.d("** Executing API (POST) ", "Name => " + tag + "  " + "URL => " + url);
        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STARTED);
        AndroidNetworking.post(""+url)
                .addBodyParameter(bodyparameter)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d("*** RESPONSE ", "" + jsonObject);
                        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                        //apifetcher.onFetchComplete(jsonObject, tag);
                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                        apifetcher.onFetchFailed(anError);
                        Log.e("errror", "" + anError.getErrorBody());
                        Log.e("errror", "" + anError.getErrorDetail());
                        Log.e("errror", "" + anError.getMessage());
                        Log.e("error", "" + anError.getStackTrace());
                        Log.e("error", "" + anError.getCause());
                    }
                });
    }

    public void execution_method_get(final String tag, String url) {

        Log.d("** Executing API ", "Name => " + tag + "  " + "URL => " + url);


        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STARTED);
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
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchComplete(response, tag);
            }

            @Override
            public void onError(ANError anError) {
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchFailed(anError);
                Log.e("errror", "" + anError.getErrorBody());
                Log.e("errror", "" + anError.getErrorDetail());
                Log.e("errror", "" + anError.getMessage());
                Log.e("error", "" + anError.getStackTrace());
                Log.e("error", "" + anError.getCause());

                Toast.makeText(context, "Something Went wrong with url and key!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public interface APIFETCHER {

        public static int KEY_API_IS_STARTED = 0;
        public static int KEY_API_IS_RUNNING = 1;
        public static int KEY_API_IS_STOPPED = 2;
        public static int KEY_API_IS_ERRORED = 3;

        void onAPIRunningState(int a);  // state - API Starts(0) , API Running(1) , API Stops(2)  API Error(3)

        void onFetchProgress(int progress);  // This Will useful when downloading file

        void onFetchComplete(String script, String APINAME); // This will give the full script

        void onFetchFailed(ANError error);  // This will give the cause of error if occurred

        void WhichApi(String APINAME);
    }
}
