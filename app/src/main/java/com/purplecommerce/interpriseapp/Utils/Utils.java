package com.purplecommerce.interpriseapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.purplecommerce.interpriseapp.CustomersActivity;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static android.graphics.Color.*;

/**
 * Created by purplecommerce on 23/08/17.
 */

public class Utils {


    public static final String CustomerChangeLog = "CustomersChangeLog" ;
    public static final String ItemsChangeLog = "ItemsChangeLog" ;
    public static final int PerPageCount = 20 ;


    public static void clearParentsBackgrounds(View view) {
        while (view != null) {
            final ViewParent parent = view.getParent();
            if (parent instanceof View) {
                view = (View) parent;
                view.setBackgroundResource(android.R.color.transparent);
            } else {
                view = null;
            }
        }
    }


    public static void SetCountPerPage(int pgcount , int totalCustomers , int totalperpp , TextView txt){

        int end  = pgcount * totalperpp ;
        int start = end - totalperpp + 1 ;
        txt.setText(String.valueOf(start+"-"+end+"/"+totalCustomers));

    }

    public static void hideKeyboard(Activity act) {

        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    public static OkHttpClient GetOkHttp(final SessionManager sessionManager){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();

        return okHttpClient ;

    }






}
