package com.purplecommerce.interpriseapp.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by purplecommerce on 28/08/17.
 */

public class ItemsInventoryDBManager {


    public static Realm myRealm;
    public static Context con ;
    public static int AddOrUpdateDB = 0 ;


    public ItemsInventoryDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getInstance(con);
    }


    public void SaveNewItems(String code , String name ,  String type , String status , byte[] photo , String manufactureCode ,
                             String ItemLastUpdate){

        if(AlreadySavedOrNot(code)){
            UpdateUserData(code , status , manufactureCode , ItemLastUpdate);
        }else {
            /////  create new row in database
            SaveUserData(code , name , type , status , photo , manufactureCode , ItemLastUpdate);
        }

    }

    private void SaveUserData(String code, String s, String type, String stats, byte[] photo, String mcode, String itemLastUpdate) {

        AddOrUpdateDB = 1;
        myRealm.beginTransaction();
        ItemsInventoryTable pd = myRealm.createObject(ItemsInventoryTable.class);
        pd.setItemCode(code);
        pd.setItemName(s);
        pd.setItemType(type);
        pd.setItemStatus(stats);
        pd.setItemPhoto(photo);
        pd.setItemManufactureCode(mcode);
        pd.setItemlastUpdate(itemLastUpdate);
        myRealm.commitTransaction();

    }



    private  boolean AlreadySavedOrNot(String ItemCode){
        boolean value  = false;
        if(myRealm.where(ItemsInventoryTable.class)
                .equalTo("ItemCode", ""+ItemCode)

                .count()==0)
        {
            value = false ;
        }else {
            value = true ;
        }
        Log.e("valuesscheck",""+value);
        return  value ;

    }

    private void UpdateUserData(String itemcode, String status, String manufacture, String itemLastUpdate) {

        AddOrUpdateDB = 0 ;

        ItemsInventoryTable tobechangedelement =
                myRealm.where(ItemsInventoryTable.class)
                        .equalTo("ItemCode", ""+itemcode)
                        .findFirst();

        myRealm.beginTransaction();
        tobechangedelement.setItemStatus(status);
        tobechangedelement.setItemManufactureCode(manufacture);
        tobechangedelement.setItemlastUpdate(itemLastUpdate);
        myRealm.commitTransaction();
    }


    public RealmResults<ItemsInventoryTable> getAllItems(){
        RealmResults<ItemsInventoryTable> results = myRealm.where(ItemsInventoryTable.class).findAll();
        return  results ;
    }

    public boolean ItemsInventoryIsEmpty(){
        RealmResults<ItemsInventoryTable> results = myRealm.where(ItemsInventoryTable.class).findAll();

        if (results.isEmpty()){
            return true ;
        }else {
            return false ;
        }

    }

    public int GetItemsIventoryCount(){

        RealmResults<ItemsInventoryTable> results = myRealm.where(ItemsInventoryTable.class).findAll();

        return results.size();
    }


    public ItemsInventoryTable GetItemAccordingItemCode(String code){

        ItemsInventoryTable Item = null;


        if(myRealm.where(ItemsInventoryTable.class)
                .contains("ItemCode", ""+code)

                .count()==0)
        {
           // Toast.makeText(con, "Item Code Not Exist !!", Toast.LENGTH_SHORT).show();
        }else {
            Item =
                    myRealm.where(ItemsInventoryTable.class)
                            .contains("ItemCode", ""+code)
                            .findFirst();
            return Item ;
        }

        return Item ;

    }


    public RealmResults<ItemsInventoryTable> SearchItems(String itemcode){

        RealmResults<ItemsInventoryTable> items = null;


        if(myRealm.where(ItemsInventoryTable.class)
                .contains("ItemCode", ""+itemcode)

                .count()==0)
        {
            Toast.makeText(con, "Item Code Not Exist !!", Toast.LENGTH_SHORT).show();
        }else {
            items =
                    myRealm.where(ItemsInventoryTable.class)
                            .contains("ItemCode", ""+itemcode , RealmQuery.CASE_INSENSITIVE)
                            .findAll();
            return items ;
        }

        return items ;

    }




    public  void ClearChangeLogTable(){
        myRealm.beginTransaction();
        myRealm.allObjects(CustomersTable.class).clear();
        myRealm.commitTransaction();

    }



}
