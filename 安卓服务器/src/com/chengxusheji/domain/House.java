package com.chengxusheji.domain;

import java.sql.Timestamp;
public class House {
    /*����id*/
    private int houseId;
    public int getHouseId() {
        return houseId;
    }
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    /*����¥����*/
    private String houseName;
    public String getHouseName() {
        return houseName;
    }
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

}