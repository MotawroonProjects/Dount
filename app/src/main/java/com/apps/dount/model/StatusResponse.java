package com.apps.dount.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    @SerializedName(value = "status",alternate = "code")
    protected int status;
    protected Object message;

    public int getStatus() {
        return status;
    }

    public Object getMessage() {
        return message;
    }
}
