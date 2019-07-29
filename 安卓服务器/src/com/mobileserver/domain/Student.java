package com.mobileserver.domain;

public class Student {
    /*学号*/
    private String studentNo;
    public String getStudentNo() {
        return studentNo;
    }
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*所在班级*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*姓名*/
    private String studentName;
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*照片*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*联系方式*/
    private String lxfs;
    public String getLxfs() {
        return lxfs;
    }
    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    /*所在房间*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}