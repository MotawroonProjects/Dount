package com.apps.dount.model;

import java.io.Serializable;

public class ItemCartModel implements Serializable {
    private String product_code;
    private String product_id;
    private int qty;
    private String product_batch_id;
    private String sale_unit;
    private double net_unit_price;
    private double discount;
    private double tax_rate;
    private double tax;
    private double subtotal;
    private String image;
    private String title;

    public String getId() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_batch_id() {
        return product_batch_id;
    }

    public void setProduct_batch_id(String product_batch_id) {
        this.product_batch_id = product_batch_id;
    }

    public String getSale_unit() {
        return sale_unit;
    }

    public void setSale_unit(String sale_unit) {
        this.sale_unit = sale_unit;
    }

    public double getNet_unit_price() {
        return net_unit_price;
    }

    public void setNet_unit_price(double net_unit_price) {
        this.net_unit_price = net_unit_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
