package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Room;
import com.mobileserver.util.DB;

public class RoomDAO {

	public List<Room> QueryRoom(int houseObj,String roomName) {
		List<Room> roomList = new ArrayList<Room>();
		DB db = new DB();
		String sql = "select * from Room where 1=1";
		if (houseObj != 0)
			sql += " and houseObj=" + houseObj;
		if (!roomName.equals(""))
			sql += " and roomName like '%" + roomName + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Room room = new Room();
				room.setRoomId(rs.getInt("roomId"));
				room.setHouseObj(rs.getInt("houseObj"));
				room.setRoomName(rs.getString("roomName"));
				room.setBedNum(rs.getInt("bedNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomList;
	}
	/* ���뷿����Ϣ���󣬽��з�����Ϣ�����ҵ�� */
	public String AddRoom(Room room) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·�����Ϣ */
			String sqlString = "insert into Room(houseObj,roomName,bedNum) values (";
			sqlString += room.getHouseObj() + ",";
			sqlString += "'" + room.getRoomName() + "',";
			sqlString += room.getBedNum();
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
	public String DeleteRoom(int roomId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Room where roomId=" + roomId;
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

	/* ���ݷ���id��ȡ��������Ϣ */
	public Room GetRoom(int roomId) {
		Room room = null;
		DB db = new DB();
		String sql = "select * from Room where roomId=" + roomId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				room = new Room();
				room.setRoomId(rs.getInt("roomId"));
				room.setHouseObj(rs.getInt("houseObj"));
				room.setRoomName(rs.getString("roomName"));
				room.setBedNum(rs.getInt("bedNum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return room;
	}
	/* ���·�����Ϣ */
	public String UpdateRoom(Room room) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Room set ";
			sql += "houseObj=" + room.getHouseObj() + ",";
			sql += "roomName='" + room.getRoomName() + "',";
			sql += "bedNum=" + room.getBedNum();
			sql += " where roomId=" + room.getRoomId();
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
