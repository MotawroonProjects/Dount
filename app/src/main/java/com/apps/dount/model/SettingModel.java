package com.apps.dount.model;

import java.io.Serializable;

public class SettingModel implements Serializable {
    private String phone;
    private String gmail;
    private String whats;
    private String address;
    private double lat;
    private double longitude;
    private int price_per_kilo;
    private int gift_value;
    private int total_value;
    private int share_price;
    private String about_us;
    private String terms;
    private String privacy;

    public String getPhone() {
        return phone;
    }

    public String getGmail() {
        return gmail;
    }

    public String getWhats() {
        return whats;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getPrice_per_kilo() {
        return price_per_kilo;
    }

    public int getGift_value() {
        return gift_value;
    }

    public int getTotal_value() {
        return total_value;
    }

    public int getShare_price() {
        return share_price;
    }

    public String getAbout_us() {
        return about_us;
    }

    public String getTerms() {
        return terms;
    }

    public String getPrivacy() {
        return privacy;
    }
}
