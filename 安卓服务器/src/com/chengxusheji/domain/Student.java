package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Student {
    /*ѧ��*/
    private String studentNo;
    public String getStudentNo() {
        return studentNo;
    }
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*���ڰ༶*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*����*/
    private String studentName;
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*��������*/
    private Timestamp birthday;
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /*��Ƭ*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*��ϵ��ʽ*/
    private String lxfs;
    public String getLxfs() {
        return lxfs;
    }
    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    /*���ڷ���*/
    private Room roomObj;
    public Room getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(Room roomObj) {
        this.roomObj = roomObj;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}