package com.purplecommerce.interpriseapp.SetterGetters;

import java.util.List;

/**
 * Created by purplecommerce on 25/08/17.
 */

public class ErrorResponse {


    private List<ErrorsBean> errors;

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }

    public static class ErrorsBean {
        /**
         * status : 404
         * title : Change log not found.
         */

        private String status;
        private String title;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
