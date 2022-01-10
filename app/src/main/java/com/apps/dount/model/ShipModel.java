package com.apps.dount.model;

import java.io.Serializable;

public class ShipModel extends StatusResponse implements Serializable {
    private String shipping;

    public String getShipping() {
        return shipping;
    }
}
