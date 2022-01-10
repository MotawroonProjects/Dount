package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class DepartmentModel implements Serializable {
    private String id;
    private String photo;
    private String slider;
    private List<ProductModel> products;
    private String title;

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSlider() {
        return slider;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public String getTitle() {
        return title;
    }
}
