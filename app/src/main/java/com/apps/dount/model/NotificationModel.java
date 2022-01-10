package com.apps.dount.model;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    private int id;
    private String title;
    private String note;
    private int seen;
    private int user_id;
    private int rev_id;
    private int order_id;
    private String created_at;
    private String updated_at;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public int getSeen() {
        return seen;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRev_id() {
        return rev_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
