package com.apps.dount.model;

import java.io.Serializable;

public class SizeModel implements Serializable {
    private String id;
    private String type_id;
    private String product_id;
    private String photo;
    private String title;
    private String price;
    private OfferModel offer;
    private boolean isselected;

    public String getId() {
        return id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public OfferModel getOffer() {
        return offer;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
