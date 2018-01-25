package com.purplecommerce.interpriseapp.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class ChangeLogDBManager {

    public static Realm myRealm;
    public static Context con ;


    public ChangeLogDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getDefaultInstance();
    }


    public void SaveNewChangeLog(String name , String lastUpdate ,  String endpoint){

        if(AlreadySavedOrNot(name)){
            UpdateUserData(name , lastUpdate , endpoint);
        }else {
            /////  create new row in database
            SaveUserData(name , lastUpdate , endpoint);
        }

    }

    private void SaveUserData(String name, String lastupdate,  String endpoint ) {

        myRealm.beginTransaction();
        ChangeLogTable pd = myRealm.createObject(ChangeLogTable.class);
        pd.setChangeLogName(name);
        pd.setChangeLogLastUpdate(lastupdate);
        pd.setChangeLogEndUrl(endpoint);
        myRealm.commitTransaction();

    }



    private  boolean AlreadySavedOrNot(String logname){
        boolean value  = false;
        if(myRealm.where(ChangeLogTable.class)
                .equalTo("ChangeLogName", ""+logname)

                .count()==0)
        {
            value = false ;
        }else {
            value = true ;
        }
        Log.e("valuesscheck",""+value);
        return  value ;

    }

    private   void UpdateUserData(String logname , String lastupdate , String endpoint) {
        //   Log.e("hfdhg", "nounits   ---" + productid);
        ChangeLogTable tobechangedelement =
                myRealm.where(ChangeLogTable.class)
                        .equalTo("ChangeLogName", ""+logname)
                        .findFirst();

        // Log.e("change",""+productid);
        myRealm.beginTransaction();
        tobechangedelement.setChangeLogLastUpdate(lastupdate);
        tobechangedelement.setChangeLogEndUrl(endpoint);
        myRealm.commitTransaction();
    }


    public RealmResults<ChangeLogTable> getallchangelogs(){
        RealmResults<ChangeLogTable> results = myRealm.where(ChangeLogTable.class).findAll();
        return  results ;
    }

    public boolean ChangeLogisEmpty(){
        RealmResults<ChangeLogTable> results = myRealm.where(ChangeLogTable.class).findAll();

        if (results.isEmpty()){
            return true ;
        }else {
            return false ;
        }

    }

    public int GetChangeLogCount(){

        RealmResults<ChangeLogTable> results = myRealm.where(ChangeLogTable.class).findAll();

        return results.size();
    }


    public ChangeLogTable GetChangeLogAccordingName(String name){

        ChangeLogTable customer = null;


        if(myRealm.where(ChangeLogTable.class)
                .contains("ChangeLogName", ""+name)

                .count()==0)
        {
         //   Toast.makeText(con, "Change Log Code Not Exist !!", Toast.LENGTH_SHORT).show();
        }else {
            customer =
                    myRealm.where(ChangeLogTable.class)
                            .contains("ChangeLogName", ""+name)
                            .findFirst();
            return customer ;
        }

        return customer ;

    }



    public  void ClearChangeLogTable(){
        myRealm.beginTransaction();
        myRealm.delete(CustomersTable.class);
        myRealm.commitTransaction();

    }



}
