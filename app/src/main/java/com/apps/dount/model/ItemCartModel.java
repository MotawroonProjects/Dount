package com.apps.dount.model;

import java.io.Serializable;

public class ItemCartModel implements Serializable {
    private String product_id;
    private int qty;
    private String order_desc;
    private double product_price;
    private double way_price;
    private double wrap_price;
    private double total_price;
    private String title;
    private String image;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getWay_price() {
        return way_price;
    }

    public void setWay_price(double way_price) {
        this.way_price = way_price;
    }

    public double getWrap_price() {
        return wrap_price;
    }

    public void setWrap_price(double wrap_price) {
        this.wrap_price = wrap_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
