package com.apps.dount.model;

import java.io.Serializable;
import java.util.List;

public class BranchModel implements Serializable {
  private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
