package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class FilterModel implements Serializable {
    private List<String> departments;

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }


}
