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
	/* ���������Ϣ���󣬽��������Ϣ�����ҵ�� */
	public String AddLateCome(LateCome lateCome) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����������Ϣ */
			String sqlString = "insert into LateCome(studentObj,reason,lateComeTime) values (";
			sqlString += "'" + lateCome.getStudentObj() + "',";
			sqlString += "'" + lateCome.getReason() + "',";
			sqlString += "'" + lateCome.getLateComeTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�������Ϣ */
	public String DeleteLateCome(int lateComeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LateCome where lateComeId=" + lateComeId;
			db.executeUpdate(sqlString);
			result = "�����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������¼id��ȡ�������Ϣ */
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
	/* ���������Ϣ */
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
			result = "�����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
