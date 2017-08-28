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

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getSourceCode() {
                return sourceCode;
            }

            public void setSourceCode(String sourceCode) {
                this.sourceCode = sourceCode;
            }

            public String getDefaultContact() {
                return defaultContact;
            }

            public void setDefaultContact(String defaultContact) {
                this.defaultContact = defaultContact;
            }

            public String getDefaultAPContact() {
                return defaultAPContact;
            }

            public void setDefaultAPContact(String defaultAPContact) {
                this.defaultAPContact = defaultAPContact;
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

            public String getCurrencyCode() {
                return currencyCode;
            }

            public void setCurrencyCode(String currencyCode) {
                this.currencyCode = currencyCode;
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

            public String getDefaultPrice() {
                return defaultPrice;
            }

            public void setDefaultPrice(String defaultPrice) {
                this.defaultPrice = defaultPrice;
            }

            public String getPricingMethod() {
                return pricingMethod;
            }

            public void setPricingMethod(String pricingMethod) {
                this.pricingMethod = pricingMethod;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public String getCreditCardCode() {
                return creditCardCode;
            }

            public void setCreditCardCode(String creditCardCode) {
                this.creditCardCode = creditCardCode;
            }

            public int getCreditLimit() {
                return creditLimit;
            }

            public void setCreditLimit(int creditLimit) {
                this.creditLimit = creditLimit;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public int getPricingPercent() {
                return pricingPercent;
            }

            public void setPricingPercent(int pricingPercent) {
                this.pricingPercent = pricingPercent;
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

            public boolean isIsProspect() {
                return isProspect;
            }

            public void setIsProspect(boolean isProspect) {
                this.isProspect = isProspect;
            }

            public boolean isIsAllowBackOrder() {
                return isAllowBackOrder;
            }

            public void setIsAllowBackOrder(boolean isAllowBackOrder) {
                this.isAllowBackOrder = isAllowBackOrder;
            }

            public boolean isIsWebAccess() {
                return isWebAccess;
            }

            public void setIsWebAccess(boolean isWebAccess) {
                this.isWebAccess = isWebAccess;
            }

            public String getPricingLevel() {
                return pricingLevel;
            }

            public void setPricingLevel(String pricingLevel) {
                this.pricingLevel = pricingLevel;
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

            public String getTaxNumber() {
                return taxNumber;
            }

            public void setTaxNumber(String taxNumber) {
                this.taxNumber = taxNumber;
            }

            public String getTaxCode() {
                return taxCode;
            }

            public void setTaxCode(String taxCode) {
                this.taxCode = taxCode;
            }

            public String getRecallDate() {
                return recallDate;
            }

            public void setRecallDate(String recallDate) {
                this.recallDate = recallDate;
            }

            public double getRank() {
                return rank;
            }

            public void setRank(double rank) {
                this.rank = rank;
            }

            public int getCreditRating() {
                return creditRating;
            }

            public void setCreditRating(int creditRating) {
                this.creditRating = creditRating;
            }

            public boolean isIsFromProspect() {
                return isFromProspect;
            }

            public void setIsFromProspect(boolean isFromProspect) {
                this.isFromProspect = isFromProspect;
            }

            public String getLastRankCalculated() {
                return lastRankCalculated;
            }

            public void setLastRankCalculated(String lastRankCalculated) {
                this.lastRankCalculated = lastRankCalculated;
            }

            public boolean isIsRankUserOverriden() {
                return isRankUserOverriden;
            }

            public void setIsRankUserOverriden(boolean isRankUserOverriden) {
                this.isRankUserOverriden = isRankUserOverriden;
            }

            public String getAssignedTo() {
                return assignedTo;
            }

            public void setAssignedTo(String assignedTo) {
                this.assignedTo = assignedTo;
            }

            public String getDefaultShipToCode() {
                return defaultShipToCode;
            }

            public void setDefaultShipToCode(String defaultShipToCode) {
                this.defaultShipToCode = defaultShipToCode;
            }

            public String getDiscountType() {
                return discountType;
            }

            public void setDiscountType(String discountType) {
                this.discountType = discountType;
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

            public int getTotalCredits() {
                return totalCredits;
            }

            public void setTotalCredits(int totalCredits) {
                this.totalCredits = totalCredits;
            }

            public boolean isIsRTShipping() {
                return isRTShipping;
            }

            public void setIsRTShipping(boolean isRTShipping) {
                this.isRTShipping = isRTShipping;
            }

            public boolean isOver13Checked() {
                return over13Checked;
            }

            public void setOver13Checked(boolean over13Checked) {
                this.over13Checked = over13Checked;
            }

            public String getCustomerTypeCode() {
                return customerTypeCode;
            }

            public void setCustomerTypeCode(String customerTypeCode) {
                this.customerTypeCode = customerTypeCode;
            }

            public boolean isIsSaveCCDetails() {
                return isSaveCCDetails;
            }

            public void setIsSaveCCDetails(boolean isSaveCCDetails) {
                this.isSaveCCDetails = isSaveCCDetails;
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
