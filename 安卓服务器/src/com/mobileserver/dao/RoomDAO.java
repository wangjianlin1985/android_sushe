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
	/* 传入房间信息对象，进行房间信息的添加业务 */
	public String AddRoom(Room room) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房间信息 */
			String sqlString = "insert into Room(houseObj,roomName,bedNum) values (";
			sqlString += room.getHouseObj() + ",";
			sqlString += "'" + room.getRoomName() + "',";
			sqlString += room.getBedNum();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "房间信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房间信息 */
	public String DeleteRoom(int roomId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Room where roomId=" + roomId;
			db.executeUpdate(sqlString);
			result = "房间信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据房间id获取到房间信息 */
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
	/* 更新房间信息 */
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
			result = "房间信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
