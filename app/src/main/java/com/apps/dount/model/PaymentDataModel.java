package com.apps.dount.model;

import java.io.Serializable;

public class PaymentDataModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable{
      private String url;

        public String getUrl() {
            return url;
        }
    }
}
