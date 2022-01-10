package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String representative_id;
    private String latitude;
    private String longitude;
    private String pay;
    private String status;
    private String address;
    private String sub_total;
    private String shipping;
    private String total;
    private String created_at;
    private String updated_at;
    private String day;
    private String time;
    private List<Detials> details;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getRepresentative_id() {
        return representative_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPay() {
        return pay;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getSub_total() {
        return sub_total;
    }

    public String getShipping() {
        return shipping;
    }

    public String getTotal() {
        return total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public List<Detials> getDetials() {
        return details;
    }

    public class Detials implements Serializable {
        private String id;
        private String order_id;
        private String product_id;
        private String qty;
        private String desc;
        private String product_price;

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

        public String getDesc() {
            return desc;
        }

        public String getProduct_price() {
            return product_price;
        }
    }
}


