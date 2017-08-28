package com.purplecommerce.interpriseapp.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 28/08/17.
 */

public class ItemsInventoryTable extends RealmObject {

    private String ItemCode ;
    private String ItemName ;
    private String ItemType ;
    private String ItemStatus ;
    private byte[] ItemPhoto ;
    private String ItemManufactureCode ;
    private String ItemlastUpdate ;

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }


    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String itemStatus) {
        ItemStatus = itemStatus;
    }


    public byte[] getItemPhoto() {
        return ItemPhoto;
    }

    public void setItemPhoto(byte[] itemPhoto) {
        ItemPhoto = itemPhoto;
    }

    public String getItemManufactureCode() {
        return ItemManufactureCode;
    }

    public void setItemManufactureCode(String itemManufactureCode) {
        ItemManufactureCode = itemManufactureCode;
    }


    public String getItemlastUpdate() {
        return ItemlastUpdate;
    }

    public void setItemlastUpdate(String itemlastUpdate) {
        ItemlastUpdate = itemlastUpdate;
    }
}
