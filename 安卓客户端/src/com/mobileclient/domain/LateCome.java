package com.mobileclient.domain;

import java.io.Serializable;

public class LateCome implements Serializable {
    /*晚归记录id*/
    private int lateComeId;
    public int getLateComeId() {
        return lateComeId;
    }
    public void setLateComeId(int lateComeId) {
        this.lateComeId = lateComeId;
    }

    /*晚归学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*晚归原因*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*晚归时间*/
    private String lateComeTime;
    public String getLateComeTime() {
        return lateComeTime;
    }
    public void setLateComeTime(String lateComeTime) {
        this.lateComeTime = lateComeTime;
    }

}