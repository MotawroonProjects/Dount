package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String id;
    private String name;
    private String code;
    private String type;
    private String barcode_symbology;
    private String brand_id;
    private String category_id;
    private String unit_id;
    private String purchase_unit_id;
    private String sale_unit_id;
    private String cost;
    private String price;
    private String qty;
    private String alert_quantity;
    private String promotion;
    private String promotion_price;
    private String starting_date;
    private String last_date;
    private String tax_id;
    private String branch_id;
    private String tax_method;
    private String image;
    private String file;
    private String is_variant;
    private String is_batch;
    private String is_diffPrice;
    private String featured;
    private String product_details;
    private String is_active;
    private String is_sale;
    private String times;
    private String created_at;
    private String updated_at;
    private boolean is_favorite;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getBarcode_symbology() {
        return barcode_symbology;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public String getPurchase_unit_id() {
        return purchase_unit_id;
    }

    public String getSale_unit_id() {
        return sale_unit_id;
    }

    public String getCost() {
        return cost;
    }

    public String getPrice() {
        return price;
    }

    public String getQty() {
        return qty;
    }

    public String getAlert_quantity() {
        return alert_quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getPromotion_price() {
        return promotion_price;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public String getLast_date() {
        return last_date;
    }

    public String getTax_id() {
        return tax_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public String getTax_method() {
        return tax_method;
    }

    public String getImage() {
        return image;
    }

    public String getFile() {
        return file;
    }

    public String getIs_variant() {
        return is_variant;
    }

    public String getIs_batch() {
        return is_batch;
    }

    public String getIs_diffPrice() {
        return is_diffPrice;
    }

    public String getFeatured() {
        return featured;
    }

    public String getProduct_details() {
        return product_details;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getIs_sale() {
        return is_sale;
    }

    public String getTimes() {
        return times;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }
}
