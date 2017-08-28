package com.purplecommerce.interpriseapp.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.Table;

/**
 * Created by apporioinfolabs on 08-02-2017.
 */
public class CustomersDBManager {

    public static Realm myRealm;
    public static Context con ;
    public static int AddOrUpdateDB = 0 ;

    public CustomersDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getInstance(con);
    }


    public void SaveCustomers(String custcode , String custname ,  String custphone
            , String custemail , String custdefaultprice , String custdefaultpricinglevel , String custdefaultshipto , String custcountry , String custdefaultcontact){

        if(AlreadySavedOrNot(custcode)){
            UpdateUserData(custcode , custdefaultprice , custdefaultpricinglevel , custdefaultshipto);
        }else {
            /////  create new row in database
            SaveUserData(custcode, custname , custphone , custemail , custdefaultprice , custdefaultpricinglevel ,
                    custdefaultshipto , custcountry , custdefaultcontact );
        }

    }

    private void SaveUserData(String code , String name, String phone,  String email , String defaultprice ,
                              String pricinglevel , String defaultshipto , String country , String defaultcontact
                                      ) {
        AddOrUpdateDB = 1;
        myRealm.beginTransaction();
        CustomersTable pd = myRealm.createObject(CustomersTable.class);
        pd.setCustomerCode(code);
        pd.setCustomerName(name);
        pd.setCustomerPhone(phone);
        pd.setCustomerEmail(email);
        pd.setCustomerDefaultPrice(defaultprice);
        pd.setCustomerDefaultPricingLevel(pricinglevel);
        pd.setCustomerdefaultshipto(defaultshipto);
        pd.setCustomerCountry(country);
        pd.setCustomerDefaultContact(defaultcontact);
        myRealm.commitTransaction();

    }



    private  boolean AlreadySavedOrNot(String CustomerCode){
         boolean value  = false;
        if( myRealm.where(CustomersTable.class)
                .equalTo("CustomerCode", ""+CustomerCode)

                .count()==0)
        {
            value = false ;
        }else {
            value = true ;
        }
        Log.e("valuesscheck",""+value);
        return  value ;

    }

    private   void UpdateUserData(String custcode , String defaultprice , String defaultpricinglevel , String defaultshipto) {
     //   Log.e("hfdhg", "nounits   ---" + productid);

        AddOrUpdateDB = 0 ;

        CustomersTable tobechangedelement =
                myRealm.where(CustomersTable.class)
                        .equalTo("CustomerCode", ""+custcode)
                        .findFirst();

       // Log.e("change",""+productid);
        myRealm.beginTransaction();
       // tobechangedelement.setCustomerCode(custcode);
        tobechangedelement.setCustomerDefaultPrice(defaultprice);
        tobechangedelement.setCustomerDefaultPricingLevel(defaultpricinglevel);
        tobechangedelement.setCustomerdefaultshipto(defaultshipto);
        myRealm.commitTransaction();
       }


    public RealmResults<CustomersTable> getAllrows(){
        RealmResults<CustomersTable> results = myRealm.where(CustomersTable.class).findAll();
        return  results ;
    }

    public boolean CustomerTableisEmpty(){
        RealmResults<CustomersTable> results = myRealm.where(CustomersTable.class).findAll();

        if (results.isEmpty()){
            return true ;
        }else {
            return false ;
        }

    }

    public int getCustomersCount(){

        RealmResults<CustomersTable> results = myRealm.where(CustomersTable.class).findAll();

        return results.size();
    }


    public RealmResults<CustomersTable> getCustomerAccordingCUSTID(String custid){

        RealmResults<CustomersTable> customer = null;


        if(myRealm.where(CustomersTable.class)
                .contains("CustomerCode", ""+custid)

                .count()==0)
        {
            Toast.makeText(con, "Customer Code Not Exist !!", Toast.LENGTH_SHORT).show();
        }else {
            customer =
                    myRealm.where(CustomersTable.class)
                            .contains("CustomerCode", ""+custid , RealmQuery.CASE_INSENSITIVE)
                            .findAll();
            return customer ;
        }

        return customer ;

    }



    public  void ClearCustomersTable(){
        myRealm.beginTransaction();
        myRealm.allObjects(CustomersTable.class).clear();
        myRealm.commitTransaction();

    }


}
