package com.apps.dount.model;

import android.content.Context;
import android.widget.Toast;

import com.apps.dount.R;

import java.io.Serializable;
import java.util.List;

import io.reactivex.observers.TestObserver;

public class CartDataModel implements Serializable {
    private List<ItemCartModel> details;
    private String reference_no;
    private String branch_id="";

    private String customer_id;
    private double total_discount;
    private double total_tax;
    private double total_price;
    private double grand_total;
    private String is_delivary="";
    private String receive_type;
    private String notes;
    private String payment_type;
    private String address="";
    private double latitude;
    private double longitude;

    public List<ItemCartModel> getDetails() {
        return details;
    }

    public void setDetails(List<ItemCartModel> details) {
        this.details = details;
    }

    public String getReference_no() {
        return reference_no;
    }

    public String getBranch_id() {
        return branch_id;
    }




    public String getCustomer_id() {
        return customer_id;
    }



    public double getTotal_discount() {
        return total_discount;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public double getTotal_price() {
        return total_price;
    }




    public double getGrand_total() {
        return grand_total;
    }

    public String getIs_delivary() {
        return is_delivary;
    }

    public String getReceive_type() {
        return receive_type;
    }

    public String getNotes() {
        return notes;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }





    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }



    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }




    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public void setIs_delivary(String is_delivary) {
        this.is_delivary = is_delivary;
    }

    public void setReceive_type(String receive_type) {
        this.receive_type = receive_type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean isDataValid(Context context) {
        if (!branch_id.isEmpty()) {


            return true;
        } else {

            if (branch_id.isEmpty()) {
                Toast.makeText(context,context.getResources().getString(R.string.ch_branch),Toast.LENGTH_LONG).show();
            }


            return false;
        }
    }
}
