package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LateCome {
    /*����¼id*/
    private int lateComeId;
    public int getLateComeId() {
        return lateComeId;
    }
    public void setLateComeId(int lateComeId) {
        this.lateComeId = lateComeId;
    }

    /*���ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*���ԭ��*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*���ʱ��*/
    private String lateComeTime;
    public String getLateComeTime() {
        return lateComeTime;
    }
    public void setLateComeTime(String lateComeTime) {
        this.lateComeTime = lateComeTime;
    }

}