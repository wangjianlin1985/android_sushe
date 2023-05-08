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

	/*构造公告信息业务层对象*/
	private NoticeDAO noticeDAO = new NoticeDAO();

	/*默认构造函数*/
	public NoticeServlet() {
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
			/*获取查询公告信息的参数信息*/
			String noticeTitle = request.getParameter("noticeTitle");
			noticeTitle = noticeTitle == null ? "" : new String(request.getParameter(
					"noticeTitle").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行公告信息查询*/
			List<Notice> noticeList = noticeDAO.QueryNotice(noticeTitle);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加公告信息：获取公告信息参数，参数保存到新建的公告信息对象 */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String noticeTitle = new String(request.getParameter("noticeTitle").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTitle(noticeTitle);
			String noticeContent = new String(request.getParameter("noticeContent").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeContent(noticeContent);
			String noticeTime = new String(request.getParameter("noticeTime").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTime(noticeTime);

			/* 调用业务层执行添加操作 */
			String result = noticeDAO.AddNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除公告信息：获取公告信息的公告id*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = noticeDAO.DeleteNotice(noticeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新公告信息之前先根据noticeId查询某个公告信息*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			Notice notice = noticeDAO.GetNotice(noticeId);

			// 客户端查询的公告信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新公告信息：获取公告信息参数，参数保存到新建的公告信息对象 */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String noticeTitle = new String(request.getParameter("noticeTitle").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTitle(noticeTitle);
			String noticeContent = new String(request.getParameter("noticeContent").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeContent(noticeContent);
			String noticeTime = new String(request.getParameter("noticeTime").getBytes("iso-8859-1"), "UTF-8");
			notice.setNoticeTime(noticeTime);

			/* 调用业务层执行更新操作 */
			String result = noticeDAO.UpdateNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
