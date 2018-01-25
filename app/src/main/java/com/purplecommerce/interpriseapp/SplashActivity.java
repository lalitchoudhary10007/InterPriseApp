package com.purplecommerce.interpriseapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.ShowRoomSalesModule.OrderItemsActivity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2500;
    SessionManager sm ;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext())
//                .name("InterPriseApp")
//                .schemaVersion(1)
//                .build());


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

                if (Build.VERSION.SDK_INT < 23) {
                    //Do not need to check the permission
                    startActivity(new Intent(SplashActivity.this , ApiAuthenticationActivity.class));
                    finish();
                } else {
                    if (checkAndRequestPermissions()) {
                        //If you have already permitted the permission
                        startActivity(new Intent(SplashActivity.this , ApiAuthenticationActivity.class));
                        finish();

                    } else {

                        checkAndRequestPermissions();

                    }

                }

            }
        }, SPLASH_TIME_OUT);




    }



    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,

                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(SplashActivity.this , ApiAuthenticationActivity.class));
                    finish();
                    //Permission Granted Successfully. Write working code here.
                } else {
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    //You did not accept the request can not use the functionality.
                }
                break;
        }

    }



}
