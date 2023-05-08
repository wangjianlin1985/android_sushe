package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Notice;
import com.mobileserver.util.DB;

public class NoticeDAO {

	public List<Notice> QueryNotice(String noticeTitle) {
		List<Notice> noticeList = new ArrayList<Notice>();
		DB db = new DB();
		String sql = "select * from Notice where 1=1";
		if (!noticeTitle.equals(""))
			sql += " and noticeTitle like '%" + noticeTitle + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Notice notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setNoticeTitle(rs.getString("noticeTitle"));
				notice.setNoticeContent(rs.getString("noticeContent"));
				notice.setNoticeTime(rs.getString("noticeTime"));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return noticeList;
	}
	/* ���빫����Ϣ���󣬽��й�����Ϣ�����ҵ�� */
	public String AddNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¹�����Ϣ */
			String sqlString = "insert into Notice(noticeTitle,noticeContent,noticeTime) values (";
			sqlString += "'" + notice.getNoticeTitle() + "',";
			sqlString += "'" + notice.getNoticeContent() + "',";
			sqlString += "'" + notice.getNoticeTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteNotice(int noticeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Notice where noticeId=" + noticeId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݹ���id��ȡ��������Ϣ */
	public Notice GetNotice(int noticeId) {
		Notice notice = null;
		DB db = new DB();
		String sql = "select * from Notice where noticeId=" + noticeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setNoticeTitle(rs.getString("noticeTitle"));
				notice.setNoticeContent(rs.getString("noticeContent"));
				notice.setNoticeTime(rs.getString("noticeTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return notice;
	}
	/* ���¹�����Ϣ */
	public String UpdateNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Notice set ";
			sql += "noticeTitle='" + notice.getNoticeTitle() + "',";
			sql += "noticeContent='" + notice.getNoticeContent() + "',";
			sql += "noticeTime='" + notice.getNoticeTime() + "'";
			sql += " where noticeId=" + notice.getNoticeId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
