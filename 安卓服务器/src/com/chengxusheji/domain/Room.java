package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Room {
    /*房间id*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*所在宿舍*/
    private House houseObj;
    public House getHouseObj() {
        return houseObj;
    }
    public void setHouseObj(House houseObj) {
        this.houseObj = houseObj;
    }

    /*房间名称*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*床位数*/
    private int bedNum;
    public int getBedNum() {
        return bedNum;
    }
    public void setBedNum(int bedNum) {
        this.bedNum = bedNum;
    }

}