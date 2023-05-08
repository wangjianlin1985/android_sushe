package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.House;
import com.mobileserver.util.DB;

public class HouseDAO {

	public List<House> QueryHouse() {
		List<House> houseList = new ArrayList<House>();
		DB db = new DB();
		String sql = "select * from House where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				House house = new House();
				house.setHouseId(rs.getInt("houseId"));
				house.setHouseName(rs.getString("houseName"));
				houseList.add(house);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return houseList;
	}
	/* ��������¥���󣬽�������¥�����ҵ�� */
	public String AddHouse(House house) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в���������¥ */
			String sqlString = "insert into House(houseName) values (";
			sqlString += "'" + house.getHouseName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����¥��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����¥���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������¥ */
	public String DeleteHouse(int houseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from House where houseId=" + houseId;
			db.executeUpdate(sqlString);
			result = "����¥ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����¥ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ������¥ */
	public House GetHouse(int houseId) {
		House house = null;
		DB db = new DB();
		String sql = "select * from House where houseId=" + houseId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				house = new House();
				house.setHouseId(rs.getInt("houseId"));
				house.setHouseName(rs.getString("houseName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return house;
	}
	/* ��������¥ */
	public String UpdateHouse(House house) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update House set ";
			sql += "houseName='" + house.getHouseName() + "'";
			sql += " where houseId=" + house.getHouseId();
			db.executeUpdate(sql);
			result = "����¥���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����¥����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
