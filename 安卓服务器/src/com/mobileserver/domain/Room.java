package com.mobileserver.domain;

public class Room {
    /*����id*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*��������*/
    private int houseObj;
    public int getHouseObj() {
        return houseObj;
    }
    public void setHouseObj(int houseObj) {
        this.houseObj = houseObj;
    }

    /*��������*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*��λ��*/
    private int bedNum;
    public int getBedNum() {
        return bedNum;
    }
    public void setBedNum(int bedNum) {
        this.bedNum = bedNum;
    }

}