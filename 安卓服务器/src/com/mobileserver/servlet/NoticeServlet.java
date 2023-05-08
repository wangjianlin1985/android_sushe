package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NoticeDAO;
import com.mobileserver.domain.Notice;

import org.json.JSONStringer;

public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���칫����Ϣҵ������*/
	private NoticeDAO noticeDAO = new NoticeDAO();

	/*Ĭ�Ϲ��캯��*/
	public NoticeServlet() {
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
			String noticeTitle = request.getParameter("noticeTitle");
			noticeTitle = noticeTitle == null ? "" : new String(request.getParameter(
					"noticeTitle").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�й�����Ϣ��ѯ*/
			List<Notice> noticeList = noticeDAO.QueryNotice(noticeTitle);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Notices>").append("\r\n");
			for (int i = 0; i < noticeList.size(); i++) {
				sb.append("	<Notice>").append("\r\n")
				.append("		<noticeId>")
				.append(noticeList.get(i).getNoticeId())
				.append("</noticeId>").append("\r\n")
				.append("		<noticeTitle>")
				.append(noticeList.get(i).getNoticeTitle())
				.append("</noticeTitle>").append("\r\n")
				.append("		<noticeContent>")
				.append(noticeList.get(i).getNoticeContent())
				.append("</noticeContent>").append("\r\n")
				.append("		<noticeTime>")
				.append(noticeList.get(i).getNoticeTime())
				.append("</noticeTime>").append("\r\n")
				.append("	</Notice>").append("\r\n");
			}
			sb.append("</Notices>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Notice notice: noticeList) {
				  stringer.object();
			  stringer.key("noticeId").value(notice.getNoticeId());
			  stringer.key("noticeTitle").value(notice.getNoticeTitle());
			  stringer.key("noticeContent").value(notice.getNoticeContent());
			  stringer.key("noticeTime").value(notice.getNoticeTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӹ�����Ϣ����ȡ������Ϣ�������������浽�½��Ĺ�����Ϣ���� */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String noticeTitle = new String(request.getParameter("noticeTitle").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTitle(noticeTitle);
			String noticeContent = new String(request.getParameter("noticeContent").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeContent(noticeContent);
			String noticeTime = new String(request.getParameter("noticeTime").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTime(noticeTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = noticeDAO.AddNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�Ĺ���id*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = noticeDAO.DeleteNotice(noticeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¹�����Ϣ֮ǰ�ȸ���noticeId��ѯĳ��������Ϣ*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			Notice notice = noticeDAO.GetNotice(noticeId);

			// �ͻ��˲�ѯ�Ĺ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("noticeId").value(notice.getNoticeId());
			  stringer.key("noticeTitle").value(notice.getNoticeTitle());
			  stringer.key("noticeContent").value(notice.getNoticeContent());
			  stringer.key("noticeTime").value(notice.getNoticeTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¹�����Ϣ����ȡ������Ϣ�������������浽�½��Ĺ�����Ϣ���� */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String noticeTitle = new String(request.getParameter("noticeTitle").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTitle(noticeTitle);
			String noticeContent = new String(request.getParameter("noticeContent").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeContent(noticeContent);
			String noticeTime = new String(request.getParameter("noticeTime").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTime(noticeTime);

			/* ����ҵ���ִ�и��²��� */
			String result = noticeDAO.UpdateNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
