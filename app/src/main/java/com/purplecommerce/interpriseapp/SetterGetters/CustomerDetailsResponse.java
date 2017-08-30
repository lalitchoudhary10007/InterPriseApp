package com.purplecommerce.interpriseapp.SetterGetters;

import java.util.List;

/**
 * Created by purplecommerce on 22/08/17.
 */

public class CustomerDetailsResponse {


    /**
     * data : {"type":"customer","id":"CUST-001003","attributes":{"counter":1004,"customerCode":"CUST-001003","customerName":"John Greenwood","address":"Rom Skatepark\r\nUpper Rainham Road","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","country":"United Kingdom","telephone":"01708 999999","fax":"01708 472011","email":"CUST-001003-info@seventies.co.uk","website":"www.romskatepark.com","notes":"","sourceCode":"Internet","defaultContact":"CCTC-001294","defaultAPContact":"CCTC-001294","shippingMethodGroup":"WEBSHIP","shippingMethod":"Standard Delivery Charge","warehouseCode":"MAIN","classCode":"CCLS-000223","glClassCode":"Default","currencyCode":"GBP","paymentTermGroup":"Pro-forma","paymentTermCode":"Credit Card","defaultPrice":"Wholesale","pricingMethod":"None","discount":0,"creditCardCode":"CCARD-000430","creditLimit":0,"credit":0,"pricingPercent":0,"isActive":true,"isCreditHold":false,"isProspect":false,"isAllowBackOrder":true,"isWebAccess":false,"pricingLevel":"TRAD01","salesRepGroupCode":"REP-000004","commission":"Sales Rep","commissionPercent":0,"taxNumber":"","taxCode":"UK 20 Std Rate Sales","recallDate":"2013-11-04T11:32:41.557","rank":-1.9927,"creditRating":0,"isFromProspect":false,"lastRankCalculated":"2016-06-13T12:17:00","isRankUserOverriden":false,"assignedTo":"JonT","defaultShipToCode":"SHIP-001232","discountType":"Overall","userCreated":"jane","dateCreated":"2013-11-04T11:37:03.303","userModified":"API","dateModified":"2017-08-21T12:10:56.64","totalCredits":0,"isRTShipping":false,"over13Checked":false,"customerTypeCode":"Cycle Shop - Proforma","isSaveCCDetails":true,"customFields":[]}}
     * links : {}
     */

    private DataBean data;
    private LinksBean links;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public static class DataBean {
        /**
         * type : customer
         * id : CUST-001003
         * attributes : {"counter":1004,"customerCode":"CUST-001003","customerName":"John Greenwood","address":"Rom Skatepark\r\nUpper Rainham Road","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","country":"United Kingdom","telephone":"01708 999999","fax":"01708 472011","email":"CUST-001003-info@seventies.co.uk","website":"www.romskatepark.com","notes":"","sourceCode":"Internet","defaultContact":"CCTC-001294","defaultAPContact":"CCTC-001294","shippingMethodGroup":"WEBSHIP","shippingMethod":"Standard Delivery Charge","warehouseCode":"MAIN","classCode":"CCLS-000223","glClassCode":"Default","currencyCode":"GBP","paymentTermGroup":"Pro-forma","paymentTermCode":"Credit Card","defaultPrice":"Wholesale","pricingMethod":"None","discount":0,"creditCardCode":"CCARD-000430","creditLimit":0,"credit":0,"pricingPercent":0,"isActive":true,"isCreditHold":false,"isProspect":false,"isAllowBackOrder":true,"isWebAccess":false,"pricingLevel":"TRAD01","salesRepGroupCode":"REP-000004","commission":"Sales Rep","commissionPercent":0,"taxNumber":"","taxCode":"UK 20 Std Rate Sales","recallDate":"2013-11-04T11:32:41.557","rank":-1.9927,"creditRating":0,"isFromProspect":false,"lastRankCalculated":"2016-06-13T12:17:00","isRankUserOverriden":false,"assignedTo":"JonT","defaultShipToCode":"SHIP-001232","discountType":"Overall","userCreated":"jane","dateCreated":"2013-11-04T11:37:03.303","userModified":"API","dateModified":"2017-08-21T12:10:56.64","totalCredits":0,"isRTShipping":false,"over13Checked":false,"customerTypeCode":"Cycle Shop - Proforma","isSaveCCDetails":true,"customFields":[]}
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
             * counter : 1004
             * customerCode : CUST-001003
             * customerName : John Greenwood
             * address : Rom Skatepark
             Upper Rainham Road
             * city : Hornchurch
             * postalCode : RM12 4ES
             * county : Essex
             * country : United Kingdom
             * telephone : 01708 999999
             * fax : 01708 472011
             * email : CUST-001003-info@seventies.co.uk
             * website : www.romskatepark.com
             * notes :
             * sourceCode : Internet
             * defaultContact : CCTC-001294
             * defaultAPContact : CCTC-001294
             * shippingMethodGroup : WEBSHIP
             * shippingMethod : Standard Delivery Charge
             * warehouseCode : MAIN
             * classCode : CCLS-000223
             * glClassCode : Default
             * currencyCode : GBP
             * paymentTermGroup : Pro-forma
             * paymentTermCode : Credit Card
             * defaultPrice : Wholesale
             * pricingMethod : None
             * discount : 0
             * creditCardCode : CCARD-000430
             * creditLimit : 0
             * credit : 0
             * pricingPercent : 0
             * isActive : true
             * isCreditHold : false
             * isProspect : false
             * isAllowBackOrder : true
             * isWebAccess : false
             * pricingLevel : TRAD01
             * salesRepGroupCode : REP-000004
             * commission : Sales Rep
             * commissionPercent : 0
             * taxNumber :
             * taxCode : UK 20 Std Rate Sales
             * recallDate : 2013-11-04T11:32:41.557
             * rank : -1.9927
             * creditRating : 0
             * isFromProspect : false
             * lastRankCalculated : 2016-06-13T12:17:00
             * isRankUserOverriden : false
             * assignedTo : JonT
             * defaultShipToCode : SHIP-001232
             * discountType : Overall
             * userCreated : jane
             * dateCreated : 2013-11-04T11:37:03.303
             * userModified : API
             * dateModified : 2017-08-21T12:10:56.64
             * totalCredits : 0
             * isRTShipping : false
             * over13Checked : false
             * customerTypeCode : Cycle Shop - Proforma
             * isSaveCCDetails : true
             * customFields : []
             */

            private int counter;
            private String customerCode;
            private String customerName;
            private String address;
            private String city;
            private String postalCode;
            private String county;
            private String country;
            private String telephone;
            private String fax;
            private String email;
            private String website;
            private String notes;
            private String sourceCode;
            private String defaultContact;
            private String defaultAPContact;
            private String shippingMethodGroup;
            private String shippingMethod;
            private String warehouseCode;
            private String classCode;
            private String glClassCode;
            private String currencyCode;
            private String paymentTermGroup;
            private String paymentTermCode;
            private String defaultPrice;
            private String pricingMethod;
            private int discount;
            private String creditCardCode;
            private int creditLimit;
            private int credit;
            private int pricingPercent;
            private boolean isActive;
            private boolean isCreditHold;
            private boolean isProspect;
            private boolean isAllowBackOrder;
            private boolean isWebAccess;
            private String pricingLevel;
            private String salesRepGroupCode;
            private String commission;
            private int commissionPercent;
            private String taxNumber;
            private String taxCode;
            private String recallDate;
            private double rank;
            private int creditRating;
            private boolean isFromProspect;
            private String lastRankCalculated;
            private boolean isRankUserOverriden;
            private String assignedTo;
            private String defaultShipToCode;
            private String discountType;
            private String userCreated;
            private String dateCreated;
            private String userModified;
            private String dateModified;
            private int totalCredits;
            private boolean isRTShipping;
            private boolean over13Checked;
            private String customerTypeCode;
            private boolean isSaveCCDetails;
            private List<?> customFields;


            public String getCustomerCode() {
                return customerCode;
            }

            public void setCustomerCode(String customerCode) {
                this.customerCode = customerCode;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
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

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }


            public String getDefaultContact() {
                return defaultContact;
            }

            public void setDefaultContact(String defaultContact) {
                this.defaultContact = defaultContact;
            }


            public String getDefaultPrice() {
                return defaultPrice;
            }

            public void setDefaultPrice(String defaultPrice) {
                this.defaultPrice = defaultPrice;
            }

            public String getDefaultShipToCode() {
                return defaultShipToCode;
            }

            public void setDefaultShipToCode(String defaultShipToCode) {
                this.defaultShipToCode = defaultShipToCode;
            }

            public String getUserCreated() {
                return userCreated;
            }

            public void setUserCreated(String userCreated) {
                this.userCreated = userCreated;
            }

            public String getDateCreated() {
                return dateCreated;
            }

            public void setDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
            }



            public List<?> getCustomFields() {
                return customFields;
            }

            public void setCustomFields(List<?> customFields) {
                this.customFields = customFields;
            }
        }
    }

    public static class LinksBean {
    }
}
