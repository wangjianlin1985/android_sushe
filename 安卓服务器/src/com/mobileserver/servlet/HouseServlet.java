package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HouseDAO;
import com.mobileserver.domain.House;

import org.json.JSONStringer;

public class HouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������¥ҵ������*/
	private HouseDAO houseDAO = new HouseDAO();

	/*Ĭ�Ϲ��캯��*/
	public HouseServlet() {
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
			/*��ȡ��ѯ����¥�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ������¥��ѯ*/
			List<House> houseList = houseDAO.QueryHouse();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Houses>").append("\r\n");
			for (int i = 0; i < houseList.size(); i++) {
				sb.append("	<House>").append("\r\n")
				.append("		<houseId>")
				.append(houseList.get(i).getHouseId())
				.append("</houseId>").append("\r\n")
				.append("		<houseName>")
				.append(houseList.get(i).getHouseName())
				.append("</houseName>").append("\r\n")
				.append("	</House>").append("\r\n");
			}
			sb.append("</Houses>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(House house: houseList) {
				  stringer.object();
			  stringer.key("houseId").value(house.getHouseId());
			  stringer.key("houseName").value(house.getHouseName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �������¥����ȡ����¥�������������浽�½�������¥���� */ 
			House house = new House();
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			house.setHouseId(houseId);
			String houseName = new String(request.getParameter("houseName").getBytes("iso-8859-1"), "UTF-8");
			house.setHouseName(houseName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = houseDAO.AddHouse(house);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������¥����ȡ����¥������id*/
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = houseDAO.DeleteHouse(houseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������¥֮ǰ�ȸ���houseId��ѯĳ������¥*/
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			House house = houseDAO.GetHouse(houseId);

			// �ͻ��˲�ѯ������¥���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("houseId").value(house.getHouseId());
			  stringer.key("houseName").value(house.getHouseName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ��������¥����ȡ����¥�������������浽�½�������¥���� */ 
			House house = new House();
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			house.setHouseId(houseId);
			String houseName = new String(request.getParameter("houseName").getBytes("iso-8859-1"), "UTF-8");
			house.setHouseName(houseName);

			/* ����ҵ���ִ�и��²��� */
			String result = houseDAO.UpdateHouse(house);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
