package com.purplecommerce.interpriseapp.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 24/08/17.
 */

public class ChangeLogTable extends RealmObject {

    private String ChangeLogName ;
    private String ChangeLogLastUpdate ;
    private String ChangeLogEndUrl ;


    public String getChangeLogName() {
        return ChangeLogName;
    }

    public void setChangeLogName(String changeLogName) {
        ChangeLogName = changeLogName;
    }


    public String getChangeLogLastUpdate() {
        return ChangeLogLastUpdate;
    }

    public void setChangeLogLastUpdate(String changeLogLastUpdate) {
        ChangeLogLastUpdate = changeLogLastUpdate;
    }

    public String getChangeLogEndUrl() {
        return ChangeLogEndUrl;
    }

    public void setChangeLogEndUrl(String changeLogEndUrl) {
        ChangeLogEndUrl = changeLogEndUrl;
    }



}
