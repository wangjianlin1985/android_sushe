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
	/* ´«ÈëÎ¬ÐÞ×´Ì¬¶ÔÏó£¬½øÐÐÎ¬ÐÞ×´Ì¬µÄÌí¼ÓÒµÎñ */
	public String AddRepairState(RepairState repairState) {
		DB db = new DB();
		String result = "";
		try {
			/* ¹¹½¨sqlÖ´ÐÐ²åÈëÐÂÎ¬ÐÞ×´Ì¬ */
			String sqlString = "insert into RepairState(repairStateName) values (";
			sqlString += "'" + repairState.getRepairStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "Î¬ÐÞ×´Ì¬Ìí¼Ó³É¹¦!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Î¬ÐÞ×´Ì¬Ìí¼ÓÊ§°Ü";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* É¾³ýÎ¬ÐÞ×´Ì¬ */
	public String DeleteRepairState(int repairStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RepairState where repairStateId=" + repairStateId;
			db.executeUpdate(sqlString);
			result = "Î¬ÐÞ×´Ì¬É¾³ý³É¹¦!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Î¬ÐÞ×´Ì¬É¾³ýÊ§°Ü";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ¸ù¾Ý×´Ì¬id»ñÈ¡µ½Î¬ÐÞ×´Ì¬ */
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
	/* ¸üÐÂÎ¬ÐÞ×´Ì¬ */
	public String UpdateRepairState(RepairState repairState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RepairState set ";
			sql += "repairStateName='" + repairState.getRepairStateName() + "'";
			sql += " where repairStateId=" + repairState.getRepairStateId();
			db.executeUpdate(sql);
			result = "Î¬ÐÞ×´Ì¬¸üÐÂ³É¹¦!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Î¬ÐÞ×´Ì¬¸üÐÂÊ§°Ü";
		} finally {
			db.all_close();
		}
		return result;
	}
}
