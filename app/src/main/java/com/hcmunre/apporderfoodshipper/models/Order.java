package com.hcmunre.apporderfoodshipper.models;

public class Order {
    private String mOrderId;
    private String mOrderName;
    private String mQuantity;
    private String mPrice;

    public Order(String mOrderId, String mOrderName, String mQuantity, String mPrice) {
        this.mOrderId = mOrderId;
        this.mOrderName = mOrderName;
        this.mQuantity = mQuantity;
        this.mPrice = mPrice;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmOrderName() {
        return mOrderName;
    }

    public void setmOrderName(String mOrderName) {
        this.mOrderName = mOrderName;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
