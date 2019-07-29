package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Repair;
import com.mobileserver.util.DB;

public class RepairDAO {

	public List<Repair> QueryRepair(int repaiClassObj,String repaitTitle,String studentObj,int repairStateObj) {
		List<Repair> repairList = new ArrayList<Repair>();
		DB db = new DB();
		String sql = "select * from Repair where 1=1";
		if (repaiClassObj != 0)
			sql += " and repaiClassObj=" + repaiClassObj;
		if (!repaitTitle.equals(""))
			sql += " and repaitTitle like '%" + repaitTitle + "%'";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (repairStateObj != 0)
			sql += " and repairStateObj=" + repairStateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Repair repair = new Repair();
				repair.setRepairId(rs.getInt("repairId"));
				repair.setRepaiClassObj(rs.getInt("repaiClassObj"));
				repair.setRepaitTitle(rs.getString("repaitTitle"));
				repair.setRepairContent(rs.getString("repairContent"));
				repair.setStudentObj(rs.getString("studentObj"));
				repair.setHandleResult(rs.getString("handleResult"));
				repair.setRepairStateObj(rs.getInt("repairStateObj"));
				repairList.add(repair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repairList;
	}
	/* 传入报修信息对象，进行报修信息的添加业务 */
	public String AddRepair(Repair repair) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新报修信息 */
			String sqlString = "insert into Repair(repaiClassObj,repaitTitle,repairContent,studentObj,handleResult,repairStateObj) values (";
			sqlString += repair.getRepaiClassObj() + ",";
			sqlString += "'" + repair.getRepaitTitle() + "',";
			sqlString += "'" + repair.getRepairContent() + "',";
			sqlString += "'" + repair.getStudentObj() + "',";
			sqlString += "'" + repair.getHandleResult() + "',";
			sqlString += repair.getRepairStateObj() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "报修信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除报修信息 */
	public String DeleteRepair(int repairId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Repair where repairId=" + repairId;
			db.executeUpdate(sqlString);
			result = "报修信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据报修id获取到报修信息 */
	public Repair GetRepair(int repairId) {
		Repair repair = null;
		DB db = new DB();
		String sql = "select * from Repair where repairId=" + repairId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				repair = new Repair();
				repair.setRepairId(rs.getInt("repairId"));
				repair.setRepaiClassObj(rs.getInt("repaiClassObj"));
				repair.setRepaitTitle(rs.getString("repaitTitle"));
				repair.setRepairContent(rs.getString("repairContent"));
				repair.setStudentObj(rs.getString("studentObj"));
				repair.setHandleResult(rs.getString("handleResult"));
				repair.setRepairStateObj(rs.getInt("repairStateObj"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return repair;
	}
	/* 更新报修信息 */
	public String UpdateRepair(Repair repair) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Repair set ";
			sql += "repaiClassObj=" + repair.getRepaiClassObj() + ",";
			sql += "repaitTitle='" + repair.getRepaitTitle() + "',";
			sql += "repairContent='" + repair.getRepairContent() + "',";
			sql += "studentObj='" + repair.getStudentObj() + "',";
			sql += "handleResult='" + repair.getHandleResult() + "',";
			sql += "repairStateObj=" + repair.getRepairStateObj();
			sql += " where repairId=" + repair.getRepairId();
			db.executeUpdate(sql);
			result = "报修信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "报修信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
