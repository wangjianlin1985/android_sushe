package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Notice {
    /*����id*/
    private int noticeId;
    public int getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    /*�������*/
    private String noticeTitle;
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /*��������*/
    private String noticeContent;
    public String getNoticeContent() {
        return noticeContent;
    }
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /*����ʱ��*/
    private String noticeTime;
    public String getNoticeTime() {
        return noticeTime;
    }
    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

}