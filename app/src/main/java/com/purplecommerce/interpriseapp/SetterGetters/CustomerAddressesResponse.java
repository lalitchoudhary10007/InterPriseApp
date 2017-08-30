package com.purplecommerce.interpriseapp.SetterGetters;

import java.util.List;

/**
 * Created by purplecommerce on 25/08/17.
 */

public class CustomerAddressesResponse {


    /**
     * data : [{"type":"customershipto","id":"SHIP-001232","attributes":{"counter":1234,"customerCode":"CUST-001003","shipToCode":"SHIP-001232","shipToName":"John Greenwood","classCode":"SHPCLS-000223","glClassCode":"Default","address":"Rom Skatepark Upper Rainham Road","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","country":"United Kingdom","telephone":"01708 999999","fax":"01708 472011","email":"CUST-001003-info@seventies.co.uk","webSite":"www.romskatepark.com","taxCode":"UK 20 Std Rate Sales","shippingMethodGroup":"WEBSHIP","shippingMethod":"Standard Delivery Charge","warehouseCode":"MAIN","isAllowBackOrder":false,"isActive":true,"isCreditHold":false,"salesRepGroupCode":"REP-000004","commission":"Sales Rep","commissionPercent":0,"creditLimit":0,"pricingPercent":0,"pricingLevel":"0","paymentTermGroup":"Pro-forma","paymentTermCode":"Payment Due on Order","openTime":"2006-04-26T08:00:00","closeTime":"2006-04-26T17:00:00","specialInstructions":"None","isBookTimeDateAndBay":false,"residenceType":"Unknown","userCreated":"jane","dateCreated":"2013-11-04T11:37:03.32","userModified":"API","dateModified":"2017-08-22T18:55:44.367","customFields":[]}}]
     * links : {"self":"http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/customer/shipto?customercode=CUST-001003&page[number]=1&page[size]=2000"}
     */

    private LinksBean links;
    private List<DataBean> data;

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class LinksBean {
        /**
         * self : http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/customer/shipto?customercode=CUST-001003&page[number]=1&page[size]=2000
         */

        private String self;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }
    }

    public static class DataBean {
        /**
         * type : customershipto
         * id : SHIP-001232
         * attributes : {"counter":1234,"customerCode":"CUST-001003","shipToCode":"SHIP-001232","shipToName":"John Greenwood","classCode":"SHPCLS-000223","glClassCode":"Default","address":"Rom Skatepark Upper Rainham Road","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","country":"United Kingdom","telephone":"01708 999999","fax":"01708 472011","email":"CUST-001003-info@seventies.co.uk","webSite":"www.romskatepark.com","taxCode":"UK 20 Std Rate Sales","shippingMethodGroup":"WEBSHIP","shippingMethod":"Standard Delivery Charge","warehouseCode":"MAIN","isAllowBackOrder":false,"isActive":true,"isCreditHold":false,"salesRepGroupCode":"REP-000004","commission":"Sales Rep","commissionPercent":0,"creditLimit":0,"pricingPercent":0,"pricingLevel":"0","paymentTermGroup":"Pro-forma","paymentTermCode":"Payment Due on Order","openTime":"2006-04-26T08:00:00","closeTime":"2006-04-26T17:00:00","specialInstructions":"None","isBookTimeDateAndBay":false,"residenceType":"Unknown","userCreated":"jane","dateCreated":"2013-11-04T11:37:03.32","userModified":"API","dateModified":"2017-08-22T18:55:44.367","customFields":[]}
         */

        private String type;
        private String id;
        private AttributesBean attributes;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBean {
            /**
             * counter : 1234
             * customerCode : CUST-001003
             * shipToCode : SHIP-001232
             * shipToName : John Greenwood
             * classCode : SHPCLS-000223
             * glClassCode : Default
             * address : Rom Skatepark Upper Rainham Road
             * city : Hornchurch
             * postalCode : RM12 4ES
             * county : Essex
             * country : United Kingdom
             * telephone : 01708 999999
             * fax : 01708 472011
             * email : CUST-001003-info@seventies.co.uk
             * webSite : www.romskatepark.com
             * taxCode : UK 20 Std Rate Sales
             * shippingMethodGroup : WEBSHIP
             * shippingMethod : Standard Delivery Charge
             * warehouseCode : MAIN
             * isAllowBackOrder : false
             * isActive : true
             * isCreditHold : false
             * salesRepGroupCode : REP-000004
             * commission : Sales Rep
             * commissionPercent : 0
             * creditLimit : 0
             * pricingPercent : 0
             * pricingLevel : 0
             * paymentTermGroup : Pro-forma
             * paymentTermCode : Payment Due on Order
             * openTime : 2006-04-26T08:00:00
             * closeTime : 2006-04-26T17:00:00
             * specialInstructions : None
             * isBookTimeDateAndBay : false
             * residenceType : Unknown
             * userCreated : jane
             * dateCreated : 2013-11-04T11:37:03.32
             * userModified : API
             * dateModified : 2017-08-22T18:55:44.367
             * customFields : []
             */


              private String customerCode;
              private String shipToCode;
              private String shipToName;

              private String address;
              private String city;
              private String postalCode;
              private String county;
              private String country;
              private String telephone;
              private String email;

            private List<?> customFields;


            public String getCustomerCode() {
                return customerCode;
            }

            public void setCustomerCode(String customerCode) {
                this.customerCode = customerCode;
            }

            public String getShipToCode() {
                return shipToCode;
            }

            public void setShipToCode(String shipToCode) {
                this.shipToCode = shipToCode;
            }

            public String getShipToName() {
                return shipToName;
            }

            public void setShipToName(String shipToName) {
                this.shipToName = shipToName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public List<?> getCustomFields() {
                return customFields;
            }

            public void setCustomFields(List<?> customFields) {
                this.customFields = customFields;
            }
        }
    }
}
