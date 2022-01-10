package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class CartDataModel implements Serializable {
    private List<ItemCartModel> details;
    private int user_id;
    private String address ;
    private double latitude;
    private double longitude;
    private String pay;
    private double sub_total;
    private double shipping;
    private double total;


    public List<ItemCartModel> getDetails() {
        return details;
    }

    public void setDetails(List<ItemCartModel> details) {
        this.details = details;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
