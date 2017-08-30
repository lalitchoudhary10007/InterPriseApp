package com.purplecommerce.interpriseapp.SetterGetters;

import java.util.List;

/**
 * Created by purplecommerce on 28/08/17.
 */

public class CustomerContactsResponse {


    /**
     * data : [{"type":"crmcontactview","id":"1636","attributes":{"counter":1636,"contactCode":"CCTC-001294","entityCode":"CUST-001003","type":"CustomerContact","contactSalutationCode":"Mr","contactFirstName":"John","contactLastName":"Greenwood","jobRoleCode":"SYSJOB-000001","departmentCode":"SYSDEP-000003","assignedTo":"jane","address":"Rom Skatepark\r\nUpper Rainham Road","country":"United Kingdom","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","businessPhone":"01708 474429","businessFax":"01708 472011","homePhone":"01708 474429","mobile":"0744 333 0871","email1":"johncg71@gmail.com","timeZone":"(UTC) Dublin, Edinburgh, Lisbon, London","isAllowWebAccess":true,"username":"johncg71@gmail.com","password":"PHLtIqIMDSfRqW98rK2FxQ==","passwordIV":"86AgOWbJPggyXDemH09KOw==","passwordSalt":"JBz7oU4lTf1nMVlnmChkmg==","languageCode":"English - United Kingdom","emailRule":"AllEmails","isActive":true,"userCreated":"jane","dateCreated":"2013-11-04T11:37:03.32","userModified":"jane","dateModified":"2013-11-04T11:55:28.493","isOkToEmail":true,"isOkToFax":false,"isOkToCall":true,"webSiteCode":"WEB-000001","entityName":"John Greenwood","entityCountry":"United Kingdom","entityAddress":"Rom Skatepark\r\nUpper Rainham Road","entityCity":"Hornchurch","entityPostalCode":"RM12 4ES","entityCounty":"Essex","entityEmail":"CUST-001003-info@seventies.co.uk","entityPhone":"01708 999999","entityFax":"01708 472011","isProspect":false,"contactFullName":"Mr John Greenwood","assistantFullName":"","jobRole":"Shop Owner","department":"Accounts/Purchasing","webSite":"Seventies WebShop","assignedToName":"Jane","userNameCreated":"Jane","userNameModified":"Jane"}},{"type":"crmcontactview","id":"1637","attributes":{"counter":1637,"contactCode":"CCTC-001295","entityCode":"CUST-001003","type":"CustomerContact","contactFirstName":"Billy","contactLastName":"Mills","jobRoleCode":"SYSJOB-000002","departmentCode":"SYSDEP-000002","assignedTo":"jane","address":"Rom Skatepark\r\nUpper Rainham Road","country":"United Kingdom","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","businessPhone":"01708 474429","businessFax":"01708 472011","mobile":"0744 333 0871","email1":"rombmx@outlook.com","timeZone":"(UTC) Dublin, Edinburgh, Lisbon, London","isAllowWebAccess":false,"username":"rombmx@outlook.com","languageCode":"English - United Kingdom","isActive":true,"userCreated":"jane","dateCreated":"2013-11-04T11:37:03.32","userModified":"jane","dateModified":"2013-11-04T11:39:44.92","isOkToEmail":true,"isOkToFax":false,"isOkToCall":true,"entityName":"John Greenwood","entityCountry":"United Kingdom","entityAddress":"Rom Skatepark\r\nUpper Rainham Road","entityCity":"Hornchurch","entityPostalCode":"RM12 4ES","entityCounty":"Essex","entityEmail":"CUST-001003-info@seventies.co.uk","entityPhone":"01708 999999","entityFax":"01708 472011","isProspect":false,"contactFullName":"Billy Mills","assistantFullName":"","jobRole":"Shop Manager","department":"Purchasing","assignedToName":"Jane","userNameCreated":"Jane","userNameModified":"Jane"}},{"type":"crmcontactview","id":"2658","attributes":{"counter":2658,"contactCode":"CCTC-002191","entityCode":"CUST-001003","type":"CustomerContact","contactFirstName":"John","contactLastName":"Greenwood","assignedTo":"API","address":"Rom Skatepark Upper Rainham Road","country":"United Kingdom","city":"Hornchurch","postalCode":"RM12 4ES","businessPhone":"01708 474429","email1":"CUST-001003-info@seventies.co.uk","timeZone":"(UTC+00:00) Dublin, Edinburgh, Lisbon, London","isAllowWebAccess":false,"username":"CUST-001003-info@seventies.co.uk","languageCode":"English - United Kingdom","isActive":true,"userCreated":"API","dateCreated":"2017-08-08T07:01:31.74","userModified":"API","dateModified":"2017-08-08T07:01:31.74","isOkToEmail":false,"isOkToFax":false,"isOkToCall":false,"entityName":"John Greenwood","entityCountry":"United Kingdom","entityAddress":"Rom Skatepark\r\nUpper Rainham Road","entityCity":"Hornchurch","entityPostalCode":"RM12 4ES","entityCounty":"Essex","entityEmail":"CUST-001003-info@seventies.co.uk","entityPhone":"01708 999999","entityFax":"01708 472011","isProspect":false,"contactFullName":"John Greenwood","assistantFullName":"","assignedToName":"API","userNameCreated":"API","userNameModified":"API"}},{"type":"crmcontactview","id":"3051","attributes":{"counter":3051,"contactCode":"CCTC-002584","entityCode":"CUST-001003","type":"CustomerContact","contactFirstName":"John","contactLastName":"Greenwood","assignedTo":"API","address":"Rom Skatepark Upper Rainham Road","country":"United Kingdom","city":"Hornchurch","postalCode":"RM12 4ES","businessPhone":"01708 999999","email1":"CUST-001003-info@seventies.co.uk","timeZone":"(UTC+00:00) Dublin, Edinburgh, Lisbon, London","isAllowWebAccess":false,"username":"CUST-001003-info@seventies.co.uk","languageCode":"English - United Kingdom","isActive":true,"userCreated":"API","dateCreated":"2017-08-22T18:55:45.387","userModified":"API","dateModified":"2017-08-22T18:55:45.387","isOkToEmail":false,"isOkToFax":false,"isOkToCall":false,"entityName":"John Greenwood","entityCountry":"United Kingdom","entityAddress":"Rom Skatepark\r\nUpper Rainham Road","entityCity":"Hornchurch","entityPostalCode":"RM12 4ES","entityCounty":"Essex","entityEmail":"CUST-001003-info@seventies.co.uk","entityPhone":"01708 999999","entityFax":"01708 472011","isProspect":false,"contactFullName":"John Greenwood","assistantFullName":"","assignedToName":"API","userNameCreated":"API","userNameModified":"API"}}]
     * links : {"self":"http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/customer/contact?customercode=CUST-001003&page[number]=1&page[size]=2000"}
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
         * self : http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/customer/contact?customercode=CUST-001003&page[number]=1&page[size]=2000
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
         * type : crmcontactview
         * id : 1636
         * attributes : {"counter":1636,"contactCode":"CCTC-001294","entityCode":"CUST-001003","type":"CustomerContact","contactSalutationCode":"Mr","contactFirstName":"John","contactLastName":"Greenwood","jobRoleCode":"SYSJOB-000001","departmentCode":"SYSDEP-000003","assignedTo":"jane","address":"Rom Skatepark\r\nUpper Rainham Road","country":"United Kingdom","city":"Hornchurch","postalCode":"RM12 4ES","county":"Essex","businessPhone":"01708 474429","businessFax":"01708 472011","homePhone":"01708 474429","mobile":"0744 333 0871","email1":"johncg71@gmail.com","timeZone":"(UTC) Dublin, Edinburgh, Lisbon, London","isAllowWebAccess":true,"username":"johncg71@gmail.com","password":"PHLtIqIMDSfRqW98rK2FxQ==","passwordIV":"86AgOWbJPggyXDemH09KOw==","passwordSalt":"JBz7oU4lTf1nMVlnmChkmg==","languageCode":"English - United Kingdom","emailRule":"AllEmails","isActive":true,"userCreated":"jane","dateCreated":"2013-11-04T11:37:03.32","userModified":"jane","dateModified":"2013-11-04T11:55:28.493","isOkToEmail":true,"isOkToFax":false,"isOkToCall":true,"webSiteCode":"WEB-000001","entityName":"John Greenwood","entityCountry":"United Kingdom","entityAddress":"Rom Skatepark\r\nUpper Rainham Road","entityCity":"Hornchurch","entityPostalCode":"RM12 4ES","entityCounty":"Essex","entityEmail":"CUST-001003-info@seventies.co.uk","entityPhone":"01708 999999","entityFax":"01708 472011","isProspect":false,"contactFullName":"Mr John Greenwood","assistantFullName":"","jobRole":"Shop Owner","department":"Accounts/Purchasing","webSite":"Seventies WebShop","assignedToName":"Jane","userNameCreated":"Jane","userNameModified":"Jane"}
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
             * counter : 1636
             * contactCode : CCTC-001294
             * entityCode : CUST-001003
             * type : CustomerContact
             * contactSalutationCode : Mr
             * contactFirstName : John
             * contactLastName : Greenwood
             * jobRoleCode : SYSJOB-000001
             * departmentCode : SYSDEP-000003
             * assignedTo : jane
             * address : Rom Skatepark
             Upper Rainham Road
             * country : United Kingdom
             * city : Hornchurch
             * postalCode : RM12 4ES
             * county : Essex
             * businessPhone : 01708 474429
             * businessFax : 01708 472011
             * homePhone : 01708 474429
             * mobile : 0744 333 0871
             * email1 : johncg71@gmail.com
             * timeZone : (UTC) Dublin, Edinburgh, Lisbon, London
             * isAllowWebAccess : true
             * username : johncg71@gmail.com
             * password : PHLtIqIMDSfRqW98rK2FxQ==
             * passwordIV : 86AgOWbJPggyXDemH09KOw==
             * passwordSalt : JBz7oU4lTf1nMVlnmChkmg==
             * languageCode : English - United Kingdom
             * emailRule : AllEmails
             * isActive : true
             * userCreated : jane
             * dateCreated : 2013-11-04T11:37:03.32
             * userModified : jane
             * dateModified : 2013-11-04T11:55:28.493
             * isOkToEmail : true
             * isOkToFax : false
             * isOkToCall : true
             * webSiteCode : WEB-000001
             * entityName : John Greenwood
             * entityCountry : United Kingdom
             * entityAddress : Rom Skatepark
             Upper Rainham Road
             * entityCity : Hornchurch
             * entityPostalCode : RM12 4ES
             * entityCounty : Essex
             * entityEmail : CUST-001003-info@seventies.co.uk
             * entityPhone : 01708 999999
             * entityFax : 01708 472011
             * isProspect : false
             * contactFullName : Mr John Greenwood
             * assistantFullName :
             * jobRole : Shop Owner
             * department : Accounts/Purchasing
             * webSite : Seventies WebShop
             * assignedToName : Jane
             * userNameCreated : Jane
             * userNameModified : Jane
             */

              private String contactCode;

              private String contactFirstName;
              private String contactLastName;
              private String address;
              private String country;
              private String city;
              private String postalCode;
              private String county;
              private String businessPhone;

              private String email1;

            public String getContactCode() {
                return contactCode;
            }

            public void setContactCode(String contactCode) {
                this.contactCode = contactCode;
            }


            public String getContactFirstName() {
                return contactFirstName;
            }

            public void setContactFirstName(String contactFirstName) {
                this.contactFirstName = contactFirstName;
            }

            public String getContactLastName() {
                return contactLastName;
            }

            public void setContactLastName(String contactLastName) {
                this.contactLastName = contactLastName;
            }


            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
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

            public String getBusinessPhone() {
                return businessPhone;
            }

            public void setBusinessPhone(String businessPhone) {
                this.businessPhone = businessPhone;
            }

            public String getEmail1() {
                return email1;
            }

            public void setEmail1(String email1) {
                this.email1 = email1;
            }


        }
    }
}
