package com.purplecommerce.interpriseapp.SetterGetters;

import java.util.List;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class CustomersChangeLogResponse {


    /**
     * data : [{"type":"changelog","id":"28","attributes":{"resource":"Customer","action":"INSERT","data":"CUST-001414","dateCreated":"2017-07-20T14:43:06","userCreated":"Admin"}},{"type":"changelog","id":"32","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001414","dateCreated":"2017-07-20T14:43:08","userCreated":"Admin"}},{"type":"changelog","id":"36","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001414","dateCreated":"2017-07-20T14:43:25","userCreated":"Admin"}},{"type":"changelog","id":"39","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001414","dateCreated":"2017-07-20T14:43:32","userCreated":"Admin"}},{"type":"changelog","id":"43","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001414","dateCreated":"2017-07-20T14:43:47","userCreated":"Admin"}},{"type":"changelog","id":"121","attributes":{"resource":"Customer","action":"INSERT","data":"CUST-001415","dateCreated":"2017-07-20T15:43:51","userCreated":"Admin"}},{"type":"changelog","id":"125","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001415","dateCreated":"2017-07-20T15:43:52","userCreated":"Admin"}},{"type":"changelog","id":"138","attributes":{"resource":"Customer","action":"INSERT","data":"CUST-001416","dateCreated":"2017-07-20T15:52:02","userCreated":"Admin"}},{"type":"changelog","id":"142","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-001416","dateCreated":"2017-07-20T15:52:03","userCreated":"Admin"}},{"type":"changelog","id":"157","attributes":{"resource":"Customer","action":"UPDATE","data":"CUST-000655","dateCreated":"2017-07-20T16:20:03","userCreated":"Admin"}}]
     * links : {"self":"http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14","next":"http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14&page[number]=2"}
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
         * self : http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14
         * next : http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14&page[number]=2
         */

        private String self;
        private String next;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }
    }

    public static class DataBean {
        /**
         * type : changelog
         * id : 28
         * attributes : {"resource":"Customer","action":"INSERT","data":"CUST-001414","dateCreated":"2017-07-20T14:43:06","userCreated":"Admin"}
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
             * resource : Customer
             * action : INSERT
             * data : CUST-001414
             * dateCreated : 2017-07-20T14:43:06
             * userCreated : Admin
             */

            private String resource;
            private String action;
            private String data;
            private String dateCreated;
            private String userCreated;

            public String getResource() {
                return resource;
            }

            public void setResource(String resource) {
                this.resource = resource;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getDateCreated() {
                return dateCreated;
            }

            public void setDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
            }

            public String getUserCreated() {
                return userCreated;
            }

            public void setUserCreated(String userCreated) {
                this.userCreated = userCreated;
            }
        }
    }
}
