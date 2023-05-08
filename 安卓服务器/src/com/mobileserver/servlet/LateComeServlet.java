package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LateComeDAO;
import com.mobileserver.domain.LateCome;

import org.json.JSONStringer;

public class LateComeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���������Ϣҵ������*/
	private LateComeDAO lateComeDAO = new LateComeDAO();

	/*Ĭ�Ϲ��캯��*/
	public LateComeServlet() {
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
			/*��ȡ��ѯ�����Ϣ�Ĳ�����Ϣ*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String reason = request.getParameter("reason");
			reason = reason == null ? "" : new String(request.getParameter(
					"reason").getBytes("iso-8859-1"), "UTF-8");
			String lateComeTime = request.getParameter("lateComeTime");
			lateComeTime = lateComeTime == null ? "" : new String(request.getParameter(
					"lateComeTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�������Ϣ��ѯ*/
			List<LateCome> lateComeList = lateComeDAO.QueryLateCome(studentObj,reason,lateComeTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LateComes>").append("\r\n");
			for (int i = 0; i < lateComeList.size(); i++) {
				sb.append("	<LateCome>").append("\r\n")
				.append("		<lateComeId>")
				.append(lateComeList.get(i).getLateComeId())
				.append("</lateComeId>").append("\r\n")
				.append("		<studentObj>")
				.append(lateComeList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<reason>")
				.append(lateComeList.get(i).getReason())
				.append("</reason>").append("\r\n")
				.append("		<lateComeTime>")
				.append(lateComeList.get(i).getLateComeTime())
				.append("</lateComeTime>").append("\r\n")
				.append("	</LateCome>").append("\r\n");
			}
			sb.append("</LateComes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LateCome lateCome: lateComeList) {
				  stringer.object();
			  stringer.key("lateComeId").value(lateCome.getLateComeId());
			  stringer.key("studentObj").value(lateCome.getStudentObj());
			  stringer.key("reason").value(lateCome.getReason());
			  stringer.key("lateComeTime").value(lateCome.getLateComeTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��������Ϣ����ȡ�����Ϣ�������������浽�½��������Ϣ���� */ 
			LateCome lateCome = new LateCome();
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			lateCome.setLateComeId(lateComeId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setStudentObj(studentObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setReason(reason);
			String lateComeTime = new String(request.getParameter("lateComeTime").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setLateComeTime(lateComeTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = lateComeDAO.AddLateCome(lateCome);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������Ϣ����ȡ�����Ϣ������¼id*/
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = lateComeDAO.DeleteLateCome(lateComeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���������Ϣ֮ǰ�ȸ���lateComeId��ѯĳ�������Ϣ*/
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			LateCome lateCome = lateComeDAO.GetLateCome(lateComeId);

			// �ͻ��˲�ѯ�������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("lateComeId").value(lateCome.getLateComeId());
			  stringer.key("studentObj").value(lateCome.getStudentObj());
			  stringer.key("reason").value(lateCome.getReason());
			  stringer.key("lateComeTime").value(lateCome.getLateComeTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���������Ϣ����ȡ�����Ϣ�������������浽�½��������Ϣ���� */ 
			LateCome lateCome = new LateCome();
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			lateCome.setLateComeId(lateComeId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setStudentObj(studentObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setReason(reason);
			String lateComeTime = new String(request.getParameter("lateComeTime").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setLateComeTime(lateComeTime);

			/* ����ҵ���ִ�и��²��� */
			String result = lateComeDAO.UpdateLateCome(lateCome);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
