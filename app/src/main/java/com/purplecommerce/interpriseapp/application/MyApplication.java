package com.purplecommerce.interpriseapp.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("interprise.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}






