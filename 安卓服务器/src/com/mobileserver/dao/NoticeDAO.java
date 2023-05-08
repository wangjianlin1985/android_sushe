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
	/* 传入公告信息对象，进行公告信息的添加业务 */
	public String AddNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新公告信息 */
			String sqlString = "insert into Notice(noticeTitle,noticeContent,noticeTime) values (";
			sqlString += "'" + notice.getNoticeTitle() + "',";
			sqlString += "'" + notice.getNoticeContent() + "',";
			sqlString += "'" + notice.getNoticeTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "公告信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公告信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除公告信息 */
	public String DeleteNotice(int noticeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Notice where noticeId=" + noticeId;
			db.executeUpdate(sqlString);
			result = "公告信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公告信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据公告id获取到公告信息 */
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
	/* 更新公告信息 */
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
			result = "公告信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公告信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
