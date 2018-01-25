package com.purplecommerce.interpriseapp.SetterGetters;

/**
 * Created by purplecommerce on 28/08/17.
 */

public class ItemInventoryDetailsResponse {


    /**
     * data : {"type":"inventoryitem","id":"ITEM-000615","attributes":{"counter":574,"itemCode":"ITEM-000615","itemName":"PRFE001-000-000","itemType":"Stock","status":"A","leadTime":0,"isCommissionable":true,"isSpecialOrder":false,"isSupplementaryUnitsReq":false,"isUseNetMassOrWeight":false,"standardCost":8.47,"standardCostRate":0,"costingMethod":"Standard Cost","currentCost":8.41,"currentCostRate":8.41,"currentCostDate":"2016-08-09T00:00:00","averageCost":8.493888,"averageCostDate":"2011-02-21T12:03:25.64","averageCostRate":8.493888,"landedCostPercent":0,"purchaseTaxOption":"Supplier","salesTaxOption":"Item","salesTaxCode":"UK 20 Std Rate Sales","salesTaxCountryCode":"United Kingdom","salesTaxTaxType":"AR","classCode":"STOC01","glClassCode":"DEFAULT-STOCK","minLevel":1,"maxLevel":99999999,"commCustom":"Sales Rep","trackingOption":"None","photo":"","isPrinted":true,"printCount":5,"manufacturerCode":"Federal","xmlPackage":"product.simpleproduct.xml.config","published":0,"isOnSale":0,"isFeatured":0,"isExclusive":0,"showBuyButton":0,"isCallToOrder":0,"hidePriceUntilCart":0,"requiresRegistration":0,"minOrderQuantity":0,"checkOutOption":0,"checkOutOptionAddMessage":0,"displayColumns":4,"pageSize":0,"userCreated":"ADMIN","dateCreated":"2011-02-21T12:05:20.497","userModified":"Roger","dateModified":"2017-06-29T13:33:14.08","batchTraceabilityTypeID":1,"batchTraceabilityTypeCode":"Not Applicable","customFields":[]}}
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
         * type : inventoryitem
         * id : ITEM-000615
         * attributes : {"counter":574,"itemCode":"ITEM-000615","itemName":"PRFE001-000-000","itemType":"Stock","status":"A","leadTime":0,"isCommissionable":true,"isSpecialOrder":false,"isSupplementaryUnitsReq":false,"isUseNetMassOrWeight":false,"standardCost":8.47,"standardCostRate":0,"costingMethod":"Standard Cost","currentCost":8.41,"currentCostRate":8.41,"currentCostDate":"2016-08-09T00:00:00","averageCost":8.493888,"averageCostDate":"2011-02-21T12:03:25.64","averageCostRate":8.493888,"landedCostPercent":0,"purchaseTaxOption":"Supplier","salesTaxOption":"Item","salesTaxCode":"UK 20 Std Rate Sales","salesTaxCountryCode":"United Kingdom","salesTaxTaxType":"AR","classCode":"STOC01","glClassCode":"DEFAULT-STOCK","minLevel":1,"maxLevel":99999999,"commCustom":"Sales Rep","trackingOption":"None","photo":"","isPrinted":true,"printCount":5,"manufacturerCode":"Federal","xmlPackage":"product.simpleproduct.xml.config","published":0,"isOnSale":0,"isFeatured":0,"isExclusive":0,"showBuyButton":0,"isCallToOrder":0,"hidePriceUntilCart":0,"requiresRegistration":0,"minOrderQuantity":0,"checkOutOption":0,"checkOutOptionAddMessage":0,"displayColumns":4,"pageSize":0,"userCreated":"ADMIN","dateCreated":"2011-02-21T12:05:20.497","userModified":"Roger","dateModified":"2017-06-29T13:33:14.08","batchTraceabilityTypeID":1,"batchTraceabilityTypeCode":"Not Applicable","customFields":[]}
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
             * counter : 574
             * itemCode : ITEM-000615
             * itemName : PRFE001-000-000
             * itemType : Stock
             * status : A
             * leadTime : 0
             * isCommissionable : true
             * isSpecialOrder : false
             * isSupplementaryUnitsReq : false
             * isUseNetMassOrWeight : false
             * standardCost : 8.47
             * standardCostRate : 0
             * costingMethod : Standard Cost
             * currentCost : 8.41
             * currentCostRate : 8.41
             * currentCostDate : 2016-08-09T00:00:00
             * averageCost : 8.493888
             * averageCostDate : 2011-02-21T12:03:25.64
             * averageCostRate : 8.493888
             * landedCostPercent : 0
             * purchaseTaxOption : Supplier
             * salesTaxOption : Item
             * salesTaxCode : UK 20 Std Rate Sales
             * salesTaxCountryCode : United Kingdom
             * salesTaxTaxType : AR
             * classCode : STOC01
             * glClassCode : DEFAULT-STOCK
             * minLevel : 1
             * maxLevel : 99999999
             * commCustom : Sales Rep
             * trackingOption : None
             * photo :* photo
             * isPrinted : true
             * printCount : 5
             * manufacturerCode : Federal
             * xmlPackage : product.simpleproduct.xml.config
             * published : 0
             * isOnSale : 0
             * isFeatured : 0
             * isExclusive : 0
             * showBuyButton : 0
             * isCallToOrder : 0
             * hidePriceUntilCart : 0
             * requiresRegistration : 0
             * minOrderQuantity : 0
             * checkOutOption : 0
             * checkOutOptionAddMessage : 0
             * displayColumns : 4
             * pageSize : 0
             * userCreated : ADMIN
             * dateCreated : 2011-02-21T12:05:20.497
             * userModified : Roger
             * dateModified : 2017-06-29T13:33:14.08
             * batchTraceabilityTypeID : 1
             * batchTraceabilityTypeCode : Not Applicable
             * customFields : []
             */

            private int counter;
            private String itemCode;
            private String itemName;
            private String itemType;
            private String manufacturerCode;
            private String dateCreated ;
            private String status;

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            private String photo ;
            private int leadTime;

            public int getCounter() {
                return counter;
            }

            public void setCounter(int counter) {
                this.counter = counter;
            }

            public String getItemCode() {
                return itemCode;
            }

            public void setItemCode(String itemCode) {
                this.itemCode = itemCode;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }



            public String getManufacturerCode() {
                return manufacturerCode;
            }

            public void setManufacturerCode(String manufacturerCode) {
                this.manufacturerCode = manufacturerCode;
            }

            public String getDateCreated() {
                return dateCreated;
            }

            public void setDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
            }
        }
    }

    public static class LinksBean {
    }
}
