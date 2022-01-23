package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class BranchDataModel extends StatusResponse implements Serializable {
    private List<BranchModel> data;

    public List<BranchModel> getData() {
        return data;
    }

}
