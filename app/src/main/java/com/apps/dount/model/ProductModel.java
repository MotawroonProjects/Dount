package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String product_id;
    private String type_id;
    private String size_id;
    private String price_before;
    private String value;
    private String price_after;
    private String sub_title;
    private String id;
    private String photo;
    private String title;
    private String category_id;
    private String desc;
    private String price;
    private String background;
    private String is_favorite;
    private OfferModel offer;
    private List<TypeModel> types;
    private List<WayModel> ways;
    private List<WrapModel> wrapping;

    public String getProduct_id() {
        return product_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getSize_id() {
        return size_id;
    }

    public String getPrice_before() {
        return price_before;
    }

    public String getValue() {
        return value;
    }

    public String getPrice_after() {
        return price_after;
    }

    public String getSub_title() {
        return sub_title;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public String getBackground() {
        return background;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public OfferModel getOffer() {
        return offer;
    }

    public List<TypeModel> getTypes() {
        return types;
    }

    public List<WayModel> getWays() {
        return ways;
    }

    public List<WrapModel> getWrapping() {
        return wrapping;
    }
}
