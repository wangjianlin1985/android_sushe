package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.RepairClass;
import com.mobileserver.util.DB;

public class RepairClassDAO {

	public List<RepairClass> QueryRepairClass() {
		List<RepairClass> repairClassList = new ArrayList<RepairClass>();
		DB db = new DB();
		String sql = "select * from RepairClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				RepairClass repairClass = new RepairClass();
				repairClass.setRepairClassId(rs.getInt("repairClassId"));
				repairClass.setRepairClassName(rs.getString("repairClassName"));
				repairClassList.add(repairClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repairClassList;
	}
	/* ���뱨�������󣬽��б����������ҵ�� */
	public String AddRepairClass(RepairClass repairClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����±������ */
			String sqlString = "insert into RepairClass(repairClassName) values (";
			sqlString += "'" + repairClass.getRepairClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������� */
	public String DeleteRepairClass(int repairClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RepairClass where repairClassId=" + repairClassId;
			db.executeUpdate(sqlString);
			result = "�������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ά�����id��ȡ��������� */
	public RepairClass GetRepairClass(int repairClassId) {
		RepairClass repairClass = null;
		DB db = new DB();
		String sql = "select * from RepairClass where repairClassId=" + repairClassId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				repairClass = new RepairClass();
				repairClass.setRepairClassId(rs.getInt("repairClassId"));
				repairClass.setRepairClassName(rs.getString("repairClassName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repairClass;
	}
	/* ���±������ */
	public String UpdateRepairClass(RepairClass repairClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RepairClass set ";
			sql += "repairClassName='" + repairClass.getRepairClassName() + "'";
			sql += " where repairClassId=" + repairClass.getRepairClassId();
			db.executeUpdate(sql);
			result = "���������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
