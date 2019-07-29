package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LateCome {
    /*晚归记录id*/
    private int lateComeId;
    public int getLateComeId() {
        return lateComeId;
    }
    public void setLateComeId(int lateComeId) {
        this.lateComeId = lateComeId;
    }

    /*晚归学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
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