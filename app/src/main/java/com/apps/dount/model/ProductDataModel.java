package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel extends StatusResponse implements Serializable {
    private List<ProductModel> data;
    private List<ProductModel> products;

    public List<ProductModel> getData() {
        return data;
    }

    public List<ProductModel> getProducts() {
        return products;
    }
}
