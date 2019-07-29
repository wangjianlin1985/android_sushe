package com.mobileclient.domain;

import java.io.Serializable;

public class House implements Serializable {
    /*宿舍id*/
    private int houseId;
    public int getHouseId() {
        return houseId;
    }
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    /*宿舍楼名称*/
    private String houseName;
    public String getHouseName() {
        return houseName;
    }
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

}