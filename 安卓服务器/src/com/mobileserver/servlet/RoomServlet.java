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

	/*���췿����Ϣҵ������*/
	private RoomDAO roomDAO = new RoomDAO();

	/*Ĭ�Ϲ��캯��*/
	public RoomServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			int houseObj = 0;
			if (request.getParameter("houseObj") != null)
				houseObj = Integer.parseInt(request.getParameter("houseObj"));
			String roomName = request.getParameter("roomName");
			roomName = roomName == null ? "" : new String(request.getParameter(
					"roomName").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�з�����Ϣ��ѯ*/
			List<Room> roomList = roomDAO.QueryRoom(houseObj,roomName);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			int houseObj = Integer.parseInt(request.getParameter("houseObj"));
			room.setHouseObj(houseObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			int bedNum = Integer.parseInt(request.getParameter("bedNum"));
			room.setBedNum(bedNum);

			/* ����ҵ���ִ����Ӳ��� */
			String result = roomDAO.AddRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ķ���id*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = roomDAO.DeleteRoom(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·�����Ϣ֮ǰ�ȸ���roomId��ѯĳ��������Ϣ*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			Room room = roomDAO.GetRoom(roomId);

			// �ͻ��˲�ѯ�ķ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			int houseObj = Integer.parseInt(request.getParameter("houseObj"));
			room.setHouseObj(houseObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			int bedNum = Integer.parseInt(request.getParameter("bedNum"));
			room.setBedNum(bedNum);

			/* ����ҵ���ִ�и��²��� */
			String result = roomDAO.UpdateRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
