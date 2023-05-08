package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.RepairState;
import com.mobileserver.util.DB;

public class RepairStateDAO {

	public List<RepairState> QueryRepairState() {
		List<RepairState> repairStateList = new ArrayList<RepairState>();
		DB db = new DB();
		String sql = "select * from RepairState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				RepairState repairState = new RepairState();
				repairState.setRepairStateId(rs.getInt("repairStateId"));
				repairState.setRepairStateName(rs.getString("repairStateName"));
				repairStateList.add(repairState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repairStateList;
	}
	/* ����ά��״̬���󣬽���ά��״̬�����ҵ�� */
	public String AddRepairState(RepairState repairState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ά��״̬ */
			String sqlString = "insert into RepairState(repairStateName) values (";
			sqlString += "'" + repairState.getRepairStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ά��״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ά��״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ά��״̬ */
	public String DeleteRepairState(int repairStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RepairState where repairStateId=" + repairStateId;
			db.executeUpdate(sqlString);
			result = "ά��״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ά��״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����״̬id��ȡ��ά��״̬ */
	public RepairState GetRepairState(int repairStateId) {
		RepairState repairState = null;
		DB db = new DB();
		String sql = "select * from RepairState where repairStateId=" + repairStateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				repairState = new RepairState();
				repairState.setRepairStateId(rs.getInt("repairStateId"));
				repairState.setRepairStateName(rs.getString("repairStateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repairState;
	}
	/* ����ά��״̬ */
	public String UpdateRepairState(RepairState repairState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RepairState set ";
			sql += "repairStateName='" + repairState.getRepairStateName() + "'";
			sql += " where repairStateId=" + repairState.getRepairStateId();
			db.executeUpdate(sql);
			result = "ά��״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ά��״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
