package com.apps.dount.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private User user;
        private User data;

        private String access_token;
        private String token_type;
        private String expires_in;
        private String firebase_token;

        public User getUser() {
            return user;
        }

        public User getData() {
            return data;
        }

        public String getAccess_token() {
            return access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }

        public class User implements Serializable {
            private String id;
            private String customer_group_id;
            private String user_id;
            private String name;
            private String photo;
            private String company_name;
            private String email;
            private String phone_number;
            private String tax_no;
            private String address;
            private String city;
            private String state;
            private String postal_code;
            private String country;
            private String deposit;
            private String expense;
            private String code;
            private String purchase_gifts;
            private String register_by;
            private String share_gifts;
            private String is_active;
            private String created_at;
            private String updated_at;
            private String total;

            public String getId() {
                return id;
            }

            public String getCustomer_group_id() {
                return customer_group_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getName() {
                return name;
            }

            public String getPhoto() {
                return photo;
            }

            public String getCompany_name() {
                return company_name;
            }

            public String getEmail() {
                return email;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public String getTax_no() {
                return tax_no;
            }

            public String getAddress() {
                return address;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public String getPostal_code() {
                return postal_code;
            }

            public String getCountry() {
                return country;
            }

            public String getDeposit() {
                return deposit;
            }

            public String getExpense() {
                return expense;
            }

            public String getCode() {
                return code;
            }

            public String getPurchase_gifts() {
                return purchase_gifts;
            }

            public String getRegister_by() {
                return register_by;
            }

            public String getShare_gifts() {
                return share_gifts;
            }

            public String getIs_active() {
                return is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public String getTotal() {
                return total;
            }
        }
    }

}
