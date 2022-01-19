package com.apps.dount.model;

import java.io.Serializable;

public class OfferModel implements Serializable {
    private String id;
    private String product_id;
    private String type_id;
    private String size_id;
    private String price_before;
    private String price_after;
    private String value;

    public String getProduct_id() {
        return product_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getSize_id() {
        return size_id;
    }

    public String getPrice_before() {
        return price_before;
    }

    public String getPrice_after() {
        return price_after;
    }

    public String getValue() {
        return value;
    }
}
