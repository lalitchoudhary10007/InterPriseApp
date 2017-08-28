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

            private int counter;
            private String contactCode;
            private String entityCode;
            private String type;
            private String contactSalutationCode;
            private String contactFirstName;
            private String contactLastName;
            private String jobRoleCode;
            private String departmentCode;
            private String assignedTo;
            private String address;
            private String country;
            private String city;
            private String postalCode;
            private String county;
            private String businessPhone;
            private String businessFax;
            private String homePhone;
            private String mobile;
            private String email1;
            private String timeZone;
            private boolean isAllowWebAccess;
            private String username;
            private String password;
            private String passwordIV;
            private String passwordSalt;
            private String languageCode;
            private String emailRule;
            private boolean isActive;
            private String userCreated;
            private String dateCreated;
            private String userModified;
            private String dateModified;
            private boolean isOkToEmail;
            private boolean isOkToFax;
            private boolean isOkToCall;
            private String webSiteCode;
            private String entityName;
            private String entityCountry;
            private String entityAddress;
            private String entityCity;
            private String entityPostalCode;
            private String entityCounty;
            private String entityEmail;
            private String entityPhone;
            private String entityFax;
            private boolean isProspect;
            private String contactFullName;
            private String assistantFullName;
            private String jobRole;
            private String department;
            private String webSite;
            private String assignedToName;
            private String userNameCreated;
            private String userNameModified;

            public int getCounter() {
                return counter;
            }

            public void setCounter(int counter) {
                this.counter = counter;
            }

            public String getContactCode() {
                return contactCode;
            }

            public void setContactCode(String contactCode) {
                this.contactCode = contactCode;
            }

            public String getEntityCode() {
                return entityCode;
            }

            public void setEntityCode(String entityCode) {
                this.entityCode = entityCode;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContactSalutationCode() {
                return contactSalutationCode;
            }

            public void setContactSalutationCode(String contactSalutationCode) {
                this.contactSalutationCode = contactSalutationCode;
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

            public String getJobRoleCode() {
                return jobRoleCode;
            }

            public void setJobRoleCode(String jobRoleCode) {
                this.jobRoleCode = jobRoleCode;
            }

            public String getDepartmentCode() {
                return departmentCode;
            }

            public void setDepartmentCode(String departmentCode) {
                this.departmentCode = departmentCode;
            }

            public String getAssignedTo() {
                return assignedTo;
            }

            public void setAssignedTo(String assignedTo) {
                this.assignedTo = assignedTo;
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

            public String getBusinessFax() {
                return businessFax;
            }

            public void setBusinessFax(String businessFax) {
                this.businessFax = businessFax;
            }

            public String getHomePhone() {
                return homePhone;
            }

            public void setHomePhone(String homePhone) {
                this.homePhone = homePhone;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getEmail1() {
                return email1;
            }

            public void setEmail1(String email1) {
                this.email1 = email1;
            }

            public String getTimeZone() {
                return timeZone;
            }

            public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
            }

            public boolean isIsAllowWebAccess() {
                return isAllowWebAccess;
            }

            public void setIsAllowWebAccess(boolean isAllowWebAccess) {
                this.isAllowWebAccess = isAllowWebAccess;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPasswordIV() {
                return passwordIV;
            }

            public void setPasswordIV(String passwordIV) {
                this.passwordIV = passwordIV;
            }

            public String getPasswordSalt() {
                return passwordSalt;
            }

            public void setPasswordSalt(String passwordSalt) {
                this.passwordSalt = passwordSalt;
            }

            public String getLanguageCode() {
                return languageCode;
            }

            public void setLanguageCode(String languageCode) {
                this.languageCode = languageCode;
            }

            public String getEmailRule() {
                return emailRule;
            }

            public void setEmailRule(String emailRule) {
                this.emailRule = emailRule;
            }

            public boolean isIsActive() {
                return isActive;
            }

            public void setIsActive(boolean isActive) {
                this.isActive = isActive;
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

            public boolean isIsOkToEmail() {
                return isOkToEmail;
            }

            public void setIsOkToEmail(boolean isOkToEmail) {
                this.isOkToEmail = isOkToEmail;
            }

            public boolean isIsOkToFax() {
                return isOkToFax;
            }

            public void setIsOkToFax(boolean isOkToFax) {
                this.isOkToFax = isOkToFax;
            }

            public boolean isIsOkToCall() {
                return isOkToCall;
            }

            public void setIsOkToCall(boolean isOkToCall) {
                this.isOkToCall = isOkToCall;
            }

            public String getWebSiteCode() {
                return webSiteCode;
            }

            public void setWebSiteCode(String webSiteCode) {
                this.webSiteCode = webSiteCode;
            }

            public String getEntityName() {
                return entityName;
            }

            public void setEntityName(String entityName) {
                this.entityName = entityName;
            }

            public String getEntityCountry() {
                return entityCountry;
            }

            public void setEntityCountry(String entityCountry) {
                this.entityCountry = entityCountry;
            }

            public String getEntityAddress() {
                return entityAddress;
            }

            public void setEntityAddress(String entityAddress) {
                this.entityAddress = entityAddress;
            }

            public String getEntityCity() {
                return entityCity;
            }

            public void setEntityCity(String entityCity) {
                this.entityCity = entityCity;
            }

            public String getEntityPostalCode() {
                return entityPostalCode;
            }

            public void setEntityPostalCode(String entityPostalCode) {
                this.entityPostalCode = entityPostalCode;
            }

            public String getEntityCounty() {
                return entityCounty;
            }

            public void setEntityCounty(String entityCounty) {
                this.entityCounty = entityCounty;
            }

            public String getEntityEmail() {
                return entityEmail;
            }

            public void setEntityEmail(String entityEmail) {
                this.entityEmail = entityEmail;
            }

            public String getEntityPhone() {
                return entityPhone;
            }

            public void setEntityPhone(String entityPhone) {
                this.entityPhone = entityPhone;
            }

            public String getEntityFax() {
                return entityFax;
            }

            public void setEntityFax(String entityFax) {
                this.entityFax = entityFax;
            }

            public boolean isIsProspect() {
                return isProspect;
            }

            public void setIsProspect(boolean isProspect) {
                this.isProspect = isProspect;
            }

            public String getContactFullName() {
                return contactFullName;
            }

            public void setContactFullName(String contactFullName) {
                this.contactFullName = contactFullName;
            }

            public String getAssistantFullName() {
                return assistantFullName;
            }

            public void setAssistantFullName(String assistantFullName) {
                this.assistantFullName = assistantFullName;
            }

            public String getJobRole() {
                return jobRole;
            }

            public void setJobRole(String jobRole) {
                this.jobRole = jobRole;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getAssignedToName() {
                return assignedToName;
            }

            public void setAssignedToName(String assignedToName) {
                this.assignedToName = assignedToName;
            }

            public String getUserNameCreated() {
                return userNameCreated;
            }

            public void setUserNameCreated(String userNameCreated) {
                this.userNameCreated = userNameCreated;
            }

            public String getUserNameModified() {
                return userNameModified;
            }

            public void setUserNameModified(String userNameModified) {
                this.userNameModified = userNameModified;
            }
        }
    }
}
