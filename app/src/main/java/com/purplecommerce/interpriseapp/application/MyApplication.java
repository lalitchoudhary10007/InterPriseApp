package com.purplecommerce.interpriseapp.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // Realm.init(this);
    }
}






