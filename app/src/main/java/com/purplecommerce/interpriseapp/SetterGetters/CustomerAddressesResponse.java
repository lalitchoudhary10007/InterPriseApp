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

            private int counter;
            private String customerCode;
            private String shipToCode;
            private String shipToName;
            private String classCode;
            private String glClassCode;
            private String address;
            private String city;
            private String postalCode;
            private String county;
            private String country;
            private String telephone;
            private String fax;
            private String email;
            private String webSite;
            private String taxCode;
            private String shippingMethodGroup;
            private String shippingMethod;
            private String warehouseCode;
            private boolean isAllowBackOrder;
            private boolean isActive;
            private boolean isCreditHold;
            private String salesRepGroupCode;
            private String commission;
            private int commissionPercent;
            private int creditLimit;
            private int pricingPercent;
            private String pricingLevel;
            private String paymentTermGroup;
            private String paymentTermCode;
            private String openTime;
            private String closeTime;
            private String specialInstructions;
            private boolean isBookTimeDateAndBay;
            private String residenceType;
            private String userCreated;
            private String dateCreated;
            private String userModified;
            private String dateModified;
            private List<?> customFields;

            public int getCounter() {
                return counter;
            }

            public void setCounter(int counter) {
                this.counter = counter;
            }

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

            public String getClassCode() {
                return classCode;
            }

            public void setClassCode(String classCode) {
                this.classCode = classCode;
            }

            public String getGlClassCode() {
                return glClassCode;
            }

            public void setGlClassCode(String glClassCode) {
                this.glClassCode = glClassCode;
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

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getTaxCode() {
                return taxCode;
            }

            public void setTaxCode(String taxCode) {
                this.taxCode = taxCode;
            }

            public String getShippingMethodGroup() {
                return shippingMethodGroup;
            }

            public void setShippingMethodGroup(String shippingMethodGroup) {
                this.shippingMethodGroup = shippingMethodGroup;
            }

            public String getShippingMethod() {
                return shippingMethod;
            }

            public void setShippingMethod(String shippingMethod) {
                this.shippingMethod = shippingMethod;
            }

            public String getWarehouseCode() {
                return warehouseCode;
            }

            public void setWarehouseCode(String warehouseCode) {
                this.warehouseCode = warehouseCode;
            }

            public boolean isIsAllowBackOrder() {
                return isAllowBackOrder;
            }

            public void setIsAllowBackOrder(boolean isAllowBackOrder) {
                this.isAllowBackOrder = isAllowBackOrder;
            }

            public boolean isIsActive() {
                return isActive;
            }

            public void setIsActive(boolean isActive) {
                this.isActive = isActive;
            }

            public boolean isIsCreditHold() {
                return isCreditHold;
            }

            public void setIsCreditHold(boolean isCreditHold) {
                this.isCreditHold = isCreditHold;
            }

            public String getSalesRepGroupCode() {
                return salesRepGroupCode;
            }

            public void setSalesRepGroupCode(String salesRepGroupCode) {
                this.salesRepGroupCode = salesRepGroupCode;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public int getCommissionPercent() {
                return commissionPercent;
            }

            public void setCommissionPercent(int commissionPercent) {
                this.commissionPercent = commissionPercent;
            }

            public int getCreditLimit() {
                return creditLimit;
            }

            public void setCreditLimit(int creditLimit) {
                this.creditLimit = creditLimit;
            }

            public int getPricingPercent() {
                return pricingPercent;
            }

            public void setPricingPercent(int pricingPercent) {
                this.pricingPercent = pricingPercent;
            }

            public String getPricingLevel() {
                return pricingLevel;
            }

            public void setPricingLevel(String pricingLevel) {
                this.pricingLevel = pricingLevel;
            }

            public String getPaymentTermGroup() {
                return paymentTermGroup;
            }

            public void setPaymentTermGroup(String paymentTermGroup) {
                this.paymentTermGroup = paymentTermGroup;
            }

            public String getPaymentTermCode() {
                return paymentTermCode;
            }

            public void setPaymentTermCode(String paymentTermCode) {
                this.paymentTermCode = paymentTermCode;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(String closeTime) {
                this.closeTime = closeTime;
            }

            public String getSpecialInstructions() {
                return specialInstructions;
            }

            public void setSpecialInstructions(String specialInstructions) {
                this.specialInstructions = specialInstructions;
            }

            public boolean isIsBookTimeDateAndBay() {
                return isBookTimeDateAndBay;
            }

            public void setIsBookTimeDateAndBay(boolean isBookTimeDateAndBay) {
                this.isBookTimeDateAndBay = isBookTimeDateAndBay;
            }

            public String getResidenceType() {
                return residenceType;
            }

            public void setResidenceType(String residenceType) {
                this.residenceType = residenceType;
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

            public String getUserModified() {
                return userModified;
            }

            public void setUserModified(String userModified) {
                this.userModified = userModified;
            }

            public String getDateModified() {
                return dateModified;
            }

            public void setDateModified(String dateModified) {
                this.dateModified = dateModified;
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
