package com.mobileclient.domain;

import java.io.Serializable;

public class Notice implements Serializable {
    /*公告id*/
    private int noticeId;
    public int getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    /*公告标题*/
    private String noticeTitle;
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /*公告内容*/
    private String noticeContent;
    public String getNoticeContent() {
        return noticeContent;
    }
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /*公告时间*/
    private String noticeTime;
    public String getNoticeTime() {
        return noticeTime;
    }
    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

}