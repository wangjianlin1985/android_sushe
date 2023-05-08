package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RepairClassDAO;
import com.mobileserver.domain.RepairClass;

import org.json.JSONStringer;

public class RepairClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���챨�����ҵ������*/
	private RepairClassDAO repairClassDAO = new RepairClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public RepairClassServlet() {
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
			/*��ȡ��ѯ�������Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�б�������ѯ*/
			List<RepairClass> repairClassList = repairClassDAO.QueryRepairClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<RepairClasss>").append("\r\n");
			for (int i = 0; i < repairClassList.size(); i++) {
				sb.append("	<RepairClass>").append("\r\n")
				.append("		<repairClassId>")
				.append(repairClassList.get(i).getRepairClassId())
				.append("</repairClassId>").append("\r\n")
				.append("		<repairClassName>")
				.append(repairClassList.get(i).getRepairClassName())
				.append("</repairClassName>").append("\r\n")
				.append("	</RepairClass>").append("\r\n");
			}
			sb.append("</RepairClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(RepairClass repairClass: repairClassList) {
				  stringer.object();
			  stringer.key("repairClassId").value(repairClass.getRepairClassId());
			  stringer.key("repairClassName").value(repairClass.getRepairClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӱ�����𣺻�ȡ�������������������浽�½��ı��������� */ 
			RepairClass repairClass = new RepairClass();
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			repairClass.setRepairClassId(repairClassId);
			String repairClassName = new String(request.getParameter("repairClassName").getBytes("iso-8859-1"), "UTF-8");
			repairClass.setRepairClassName(repairClassName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = repairClassDAO.AddRepairClass(repairClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������𣺻�ȡ��������ά�����id*/
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = repairClassDAO.DeleteRepairClass(repairClassId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���±������֮ǰ�ȸ���repairClassId��ѯĳ���������*/
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			RepairClass repairClass = repairClassDAO.GetRepairClass(repairClassId);

			// �ͻ��˲�ѯ�ı��������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("repairClassId").value(repairClass.getRepairClassId());
			  stringer.key("repairClassName").value(repairClass.getRepairClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���±�����𣺻�ȡ�������������������浽�½��ı��������� */ 
			RepairClass repairClass = new RepairClass();
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			repairClass.setRepairClassId(repairClassId);
			String repairClassName = new String(request.getParameter("repairClassName").getBytes("iso-8859-1"), "UTF-8");
			repairClass.setRepairClassName(repairClassName);

			/* ����ҵ���ִ�и��²��� */
			String result = repairClassDAO.UpdateRepairClass(repairClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
