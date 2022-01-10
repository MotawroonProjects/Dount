package com.apps.dount.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private int id;
        private String first_name;
        private String last_name;
        private String photo;
        private String phone;
        private String code;
        private int purchase_gifts;
        private String register_by;
        private int share_gifts;
        private int total;
        private String created_at;
        private String updated_at;
        private String firebase_token;

        public int getId() {
            return id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getPhone() {
            return phone;
        }

        public String getCode() {
            return code;
        }

        public int getPurchase_gifts() {
            return purchase_gifts;
        }

        public String getRegister_by() {
            return register_by;
        }

        public int getShare_gifts() {
            return share_gifts;
        }

        public int getTotal() {
            return total;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }
    }

}
