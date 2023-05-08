package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RoomDAO;
import com.mobileserver.domain.Room;

import org.json.JSONStringer;

public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造房间信息业务层对象*/
	private RoomDAO roomDAO = new RoomDAO();

	/*默认构造函数*/
	public RoomServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询房间信息的参数信息*/
			int houseObj = 0;
			if (request.getParameter("houseObj") != null)
				houseObj = Integer.parseInt(request.getParameter("houseObj"));
			String roomName = request.getParameter("roomName");
			roomName = roomName == null ? "" : new String(request.getParameter(
					"roomName").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行房间信息查询*/
			List<Room> roomList = roomDAO.QueryRoom(houseObj,roomName);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Rooms>").append("\r\n");
			for (int i = 0; i < roomList.size(); i++) {
				sb.append("	<Room>").append("\r\n")
				.append("		<roomId>")
				.append(roomList.get(i).getRoomId())
				.append("</roomId>").append("\r\n")
				.append("		<houseObj>")
				.append(roomList.get(i).getHouseObj())
				.append("</houseObj>").append("\r\n")
				.append("		<roomName>")
				.append(roomList.get(i).getRoomName())
				.append("</roomName>").append("\r\n")
				.append("		<bedNum>")
				.append(roomList.get(i).getBedNum())
				.append("</bedNum>").append("\r\n")
				.append("	</Room>").append("\r\n");
			}
			sb.append("</Rooms>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Room room: roomList) {
				  stringer.object();
			  stringer.key("roomId").value(room.getRoomId());
			  stringer.key("houseObj").value(room.getHouseObj());
			  stringer.key("roomName").value(room.getRoomName());
			  stringer.key("bedNum").value(room.getBedNum());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			int houseObj = Integer.parseInt(request.getParameter("houseObj"));
			room.setHouseObj(houseObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			int bedNum = Integer.parseInt(request.getParameter("bedNum"));
			room.setBedNum(bedNum);

			/* 调用业务层执行添加操作 */
			String result = roomDAO.AddRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除房间信息：获取房间信息的房间id*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*调用业务逻辑层执行删除操作*/
			String result = roomDAO.DeleteRoom(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新房间信息之前先根据roomId查询某个房间信息*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			Room room = roomDAO.GetRoom(roomId);

			// 客户端查询的房间信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("roomId").value(room.getRoomId());
			  stringer.key("houseObj").value(room.getHouseObj());
			  stringer.key("roomName").value(room.getRoomName());
			  stringer.key("bedNum").value(room.getBedNum());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			int houseObj = Integer.parseInt(request.getParameter("houseObj"));
			room.setHouseObj(houseObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			int bedNum = Integer.parseInt(request.getParameter("bedNum"));
			room.setBedNum(bedNum);

			/* 调用业务层执行更新操作 */
			String result = roomDAO.UpdateRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
