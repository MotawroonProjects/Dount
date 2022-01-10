package com.apps.dount.model;

import java.io.Serializable;

public class SettingDataModel extends StatusResponse implements Serializable {
    private SettingModel data;

    public SettingModel getData() {
        return data;
    }


}
