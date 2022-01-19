package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class TypeModel implements Serializable {
    private String product_id;
    private String id;
    private String title;
    private String sub_title;
    private String desc;
    private String price;
    private OfferModel offer;
    private boolean isselected;
    private List<SizeModel> sizes;

    public String getId() {
        return product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public OfferModel getOffer() {
        return offer;
    }

    public List<SizeModel> getSizes() {
        return sizes;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
