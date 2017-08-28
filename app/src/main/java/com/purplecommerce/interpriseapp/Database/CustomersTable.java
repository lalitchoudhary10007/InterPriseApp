package com.purplecommerce.interpriseapp.Database;

import io.realm.RealmObject;

/**
 * Created by apporioinfolabs on 08-02-2017.
 */
public class CustomersTable extends RealmObject {


    private String CustomerName ;
    private String CustomerCode ;
    private String CustomerPhone ;
    private String CustomerEmail ;
    private String CustomerDefaultPrice ;
    private String CustomerDefaultPricingLevel ;
    private String Customerdefaultshipto ;
    private String CustomerCountry ;
    private String CustomerDefaultContact ;


    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }



    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }


    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getCustomerDefaultPrice() {
        return CustomerDefaultPrice;
    }

    public void setCustomerDefaultPrice(String customerDefaultPrice) {
        CustomerDefaultPrice = customerDefaultPrice;
    }


    public String getCustomerDefaultPricingLevel() {
        return CustomerDefaultPricingLevel;
    }

    public void setCustomerDefaultPricingLevel(String customerDefaultPricingLevel) {
        CustomerDefaultPricingLevel = customerDefaultPricingLevel;
    }

    public String getCustomerdefaultshipto() {
        return Customerdefaultshipto;
    }

    public void setCustomerdefaultshipto(String customerdefaultshipto) {
        Customerdefaultshipto = customerdefaultshipto;
    }

    public String getCustomerDefaultContact() {
        return CustomerDefaultContact;
    }

    public void setCustomerDefaultContact(String customerDefaultContact) {
        CustomerDefaultContact = customerDefaultContact;
    }

    public String getCustomerCountry() {
        return CustomerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        CustomerCountry = customerCountry;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }




}
