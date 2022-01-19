package com.apps.dount.model;

import java.io.Serializable;

public class WayModel implements Serializable {
    private String product_id;
    private String title;
    private String price;
    private boolean isselected;

    public String getId() {
        return product_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
