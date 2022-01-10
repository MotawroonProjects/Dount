package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class SliderDataModel extends StatusResponse implements Serializable {
    private List<SliderModel> data;

    public List<SliderModel> getData() {
        return data;
    }

    public static class SliderModel implements Serializable {
        private String id;
        private String photo;

        public String getId() {
            return id;
        }

        public String getPhoto() {
            return photo;
        }
    }
}
