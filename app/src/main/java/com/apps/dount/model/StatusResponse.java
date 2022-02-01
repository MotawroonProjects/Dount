package com.apps.dount.model;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    protected int code;

    public int getStatus() {
        return code;
    }

}
