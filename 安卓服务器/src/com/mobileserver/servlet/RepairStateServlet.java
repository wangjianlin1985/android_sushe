package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RepairStateDAO;
import com.mobileserver.domain.RepairState;

import org.json.JSONStringer;

public class RepairStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ά��״̬ҵ������*/
	private RepairStateDAO repairStateDAO = new RepairStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public RepairStateServlet() {
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
			/*��ȡ��ѯά��״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ά��״̬��ѯ*/
			List<RepairState> repairStateList = repairStateDAO.QueryRepairState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<RepairStates>").append("\r\n");
			for (int i = 0; i < repairStateList.size(); i++) {
				sb.append("	<RepairState>").append("\r\n")
				.append("		<repairStateId>")
				.append(repairStateList.get(i).getRepairStateId())
				.append("</repairStateId>").append("\r\n")
				.append("		<repairStateName>")
				.append(repairStateList.get(i).getRepairStateName())
				.append("</repairStateName>").append("\r\n")
				.append("	</RepairState>").append("\r\n");
			}
			sb.append("</RepairStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(RepairState repairState: repairStateList) {
				  stringer.object();
			  stringer.key("repairStateId").value(repairState.getRepairStateId());
			  stringer.key("repairStateName").value(repairState.getRepairStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ά��״̬����ȡά��״̬�������������浽�½���ά��״̬���� */ 
			RepairState repairState = new RepairState();
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			repairState.setRepairStateId(repairStateId);
			String repairStateName = new String(request.getParameter("repairStateName").getBytes("iso-8859-1"), "UTF-8");
			repairState.setRepairStateName(repairStateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = repairStateDAO.AddRepairState(repairState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ά��״̬����ȡά��״̬��״̬id*/
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = repairStateDAO.DeleteRepairState(repairStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ά��״̬֮ǰ�ȸ���repairStateId��ѯĳ��ά��״̬*/
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			RepairState repairState = repairStateDAO.GetRepairState(repairStateId);

			// �ͻ��˲�ѯ��ά��״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("repairStateId").value(repairState.getRepairStateId());
			  stringer.key("repairStateName").value(repairState.getRepairStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ά��״̬����ȡά��״̬�������������浽�½���ά��״̬���� */ 
			RepairState repairState = new RepairState();
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			repairState.setRepairStateId(repairStateId);
			String repairStateName = new String(request.getParameter("repairStateName").getBytes("iso-8859-1"), "UTF-8");
			repairState.setRepairStateName(repairStateName);

			/* ����ҵ���ִ�и��²��� */
			String result = repairStateDAO.UpdateRepairState(repairState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
