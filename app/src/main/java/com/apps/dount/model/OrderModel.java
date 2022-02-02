package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String branch_id;
    private String customer_id;
    private String total_qty;
    private String total_discount;
    private String total_tax;
    private String total_price;
    private String grand_total;
    private String item;
    private String payment_type;
    private String receive_type;
    private String is_delivary;
    private String delivery_id;
    private String delivery_status;
    private String status;
    private String notes;
    private String address;
    private String latitude;
    private String longitude;
    private String created_at;
    private String updated_at;
    private BranchModel branch;
    private UserModel.Data.User customer;
    private List<Detials> details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(String total_discount) {
        this.total_discount = total_discount;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(String total_tax) {
        this.total_tax = total_tax;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getReceive_type() {
        return receive_type;
    }

    public void setReceive_type(String receive_type) {
        this.receive_type = receive_type;
    }

    public String getIs_delivary() {
        return is_delivary;
    }

    public void setIs_delivary(String is_delivary) {
        this.is_delivary = is_delivary;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public UserModel.Data.User getCustomer() {
        return customer;
    }

    public void setCustomer(UserModel.Data.User customer) {
        this.customer = customer;
    }

    public List<Detials> getDetails() {
        return details;
    }

    public void setDetails(List<Detials> details) {
        this.details = details;
    }

    public List<Detials> getDetials() {
        return details;
    }

    public class Detials implements Serializable {
        private String id;
        private String order_id;
        private String product_id;
        private String qty;
        private String tax;
        private String subtotal;
        private String created_at;
        private String updated_at;
        private ProductModel product;


        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getQty() {
            return qty;
        }

        public String getTax() {
            return tax;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public ProductModel getProduct() {
            return product;
        }
    }
}


