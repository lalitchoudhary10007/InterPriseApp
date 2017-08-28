package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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


import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.Utils.Utils;

public class ApiAuthenticationActivity extends AppCompatActivity {


    EditText ed_Url , ed_Key ;
    TextView  submit ;
    Dialog progress_dialog ;
    ApiManager apiManager ;
    final String ConnectUrl = "verify" ;

    final String TAG = "URlKEYVERIFY";
   // customer?onlyActive=true

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

        ed_Url.setText("http://seventies.apexhelp.co.uk:82/Interprise.Web.Services");
        ed_Key.setText("0ab63a19-d325-4439-90ba-a845611bacbe");


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
                progress_dialog.dismiss();

                sm.CreateUrlCredentials(BaseUrl , key);
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

                Toast.makeText(ApiAuthenticationActivity.this, "Something Went wrong with url and key!!", Toast.LENGTH_SHORT).show();

            }
        });




    }


}
