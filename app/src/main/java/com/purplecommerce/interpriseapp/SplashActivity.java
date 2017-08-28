package com.purplecommerce.interpriseapp;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;

import java.sql.Blob;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2500;
    SessionManager sm ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext())
                .name("InterPriseApp")
                .schemaVersion(1)
                .build());


        sm = new SessionManager(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                startActivity(new Intent(SplashActivity.this , ApiAuthenticationActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);




    }









}
