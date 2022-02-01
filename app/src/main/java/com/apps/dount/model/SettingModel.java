package com.apps.dount.model;

import java.io.Serializable;

public class SettingModel implements Serializable {
    private int id;
    private String site_title;
    private String site_logo;
    private String currency;
    private String staff_access;
    private String date_format;
    private String developed_by;
    private String invoice_format;
    private int state;
    private String theme;
    private String created_at;
    private String updated_at;
    private String currency_position;
    private String terms;
    private String about;
    private String tax_for;
    private int tax_percentage;

    public int getId() {
        return id;
    }

    public String getSite_title() {
        return site_title;
    }

    public String getSite_logo() {
        return site_logo;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStaff_access() {
        return staff_access;
    }

    public String getDate_format() {
        return date_format;
    }

    public String getDeveloped_by() {
        return developed_by;
    }

    public String getInvoice_format() {
        return invoice_format;
    }

    public int getState() {
        return state;
    }

    public String getTheme() {
        return theme;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCurrency_position() {
        return currency_position;
    }

    public String getTerms() {
        return terms;
    }

    public String getAbout() {
        return about;
    }

    public String getTax_for() {
        return tax_for;
    }

    public int getTax_percentage() {
        return tax_percentage;
    }
}
