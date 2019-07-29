package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LateCome;
import com.mobileserver.util.DB;

public class LateComeDAO {

	public List<LateCome> QueryLateCome(String studentObj,String reason,String lateComeTime) {
		List<LateCome> lateComeList = new ArrayList<LateCome>();
		DB db = new DB();
		String sql = "select * from LateCome where 1=1";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (!reason.equals(""))
			sql += " and reason like '%" + reason + "%'";
		if (!lateComeTime.equals(""))
			sql += " and lateComeTime like '%" + lateComeTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LateCome lateCome = new LateCome();
				lateCome.setLateComeId(rs.getInt("lateComeId"));
				lateCome.setStudentObj(rs.getString("studentObj"));
				lateCome.setReason(rs.getString("reason"));
				lateCome.setLateComeTime(rs.getString("lateComeTime"));
				lateComeList.add(lateCome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return lateComeList;
	}
	/* 传入晚归信息对象，进行晚归信息的添加业务 */
	public String AddLateCome(LateCome lateCome) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新晚归信息 */
			String sqlString = "insert into LateCome(studentObj,reason,lateComeTime) values (";
			sqlString += "'" + lateCome.getStudentObj() + "',";
			sqlString += "'" + lateCome.getReason() + "',";
			sqlString += "'" + lateCome.getLateComeTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "晚归信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "晚归信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除晚归信息 */
	public String DeleteLateCome(int lateComeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LateCome where lateComeId=" + lateComeId;
			db.executeUpdate(sqlString);
			result = "晚归信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "晚归信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据晚归记录id获取到晚归信息 */
	public LateCome GetLateCome(int lateComeId) {
		LateCome lateCome = null;
		DB db = new DB();
		String sql = "select * from LateCome where lateComeId=" + lateComeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				lateCome = new LateCome();
				lateCome.setLateComeId(rs.getInt("lateComeId"));
				lateCome.setStudentObj(rs.getString("studentObj"));
				lateCome.setReason(rs.getString("reason"));
				lateCome.setLateComeTime(rs.getString("lateComeTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return lateCome;
	}
	/* 更新晚归信息 */
	public String UpdateLateCome(LateCome lateCome) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LateCome set ";
			sql += "studentObj='" + lateCome.getStudentObj() + "',";
			sql += "reason='" + lateCome.getReason() + "',";
			sql += "lateComeTime='" + lateCome.getLateComeTime() + "'";
			sql += " where lateComeId=" + lateCome.getLateComeId();
			db.executeUpdate(sql);
			result = "晚归信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "晚归信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
