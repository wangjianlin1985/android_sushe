package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Room;
import com.mobileclient.util.HttpUtil;

/*房间信息管理业务逻辑层*/
public class RoomService {
	/* 添加房间信息 */
	public String AddRoom(Room room) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", room.getRoomId() + "");
		params.put("houseObj", room.getHouseObj() + "");
		params.put("roomName", room.getRoomName());
		params.put("bedNum", room.getBedNum() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房间信息 */
	public List<Room> QueryRoom(Room queryConditionRoom) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RoomServlet?action=query";
		if(queryConditionRoom != null) {
			urlString += "&houseObj=" + queryConditionRoom.getHouseObj();
			urlString += "&roomName=" + URLEncoder.encode(queryConditionRoom.getRoomName(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RoomListHandler roomListHander = new RoomListHandler();
		xr.setContentHandler(roomListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Room> roomList = roomListHander.getRoomList();
		return roomList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Room> roomList = new ArrayList<Room>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Room room = new Room();
				room.setRoomId(object.getInt("roomId"));
				room.setHouseObj(object.getInt("houseObj"));
				room.setRoomName(object.getString("roomName"));
				room.setBedNum(object.getInt("bedNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}

	/* 更新房间信息 */
	public String UpdateRoom(Room room) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", room.getRoomId() + "");
		params.put("houseObj", room.getHouseObj() + "");
		params.put("roomName", room.getRoomName());
		params.put("bedNum", room.getBedNum() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房间信息 */
	public String DeleteRoom(int roomId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房间信息信息删除失败!";
		}
	}

	/* 根据房间id获取房间信息对象 */
	public Room GetRoom(int roomId)  {
		List<Room> roomList = new ArrayList<Room>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Room room = new Room();
				room.setRoomId(object.getInt("roomId"));
				room.setHouseObj(object.getInt("houseObj"));
				room.setRoomName(object.getString("roomName"));
				room.setBedNum(object.getInt("bedNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = roomList.size();
		if(size>0) return roomList.get(0); 
		else return null; 
	}
}
