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
	/* 传入报修类别对象，进行报修类别的添加业务 */
	public String AddRepairClass(RepairClass repairClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新报修类别 */
			String sqlString = "insert into RepairClass(repairClassName) values (";
			sqlString += "'" + repairClass.getRepairClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "报修类别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修类别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除报修类别 */
	public String DeleteRepairClass(int repairClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RepairClass where repairClassId=" + repairClassId;
			db.executeUpdate(sqlString);
			result = "报修类别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修类别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据维修类别id获取到报修类别 */
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
	/* 更新报修类别 */
	public String UpdateRepairClass(RepairClass repairClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RepairClass set ";
			sql += "repairClassName='" + repairClass.getRepairClassName() + "'";
			sql += " where repairClassId=" + repairClass.getRepairClassId();
			db.executeUpdate(sql);
			result = "报修类别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修类别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
