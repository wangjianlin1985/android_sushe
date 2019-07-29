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

	/*构造晚归信息业务层对象*/
	private LateComeDAO lateComeDAO = new LateComeDAO();

	/*默认构造函数*/
	public LateComeServlet() {
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
			/*获取查询晚归信息的参数信息*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String reason = request.getParameter("reason");
			reason = reason == null ? "" : new String(request.getParameter(
					"reason").getBytes("iso-8859-1"), "UTF-8");
			String lateComeTime = request.getParameter("lateComeTime");
			lateComeTime = lateComeTime == null ? "" : new String(request.getParameter(
					"lateComeTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行晚归信息查询*/
			List<LateCome> lateComeList = lateComeDAO.QueryLateCome(studentObj,reason,lateComeTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加晚归信息：获取晚归信息参数，参数保存到新建的晚归信息对象 */ 
			LateCome lateCome = new LateCome();
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			lateCome.setLateComeId(lateComeId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setStudentObj(studentObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setReason(reason);
			String lateComeTime = new String(request.getParameter("lateComeTime").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setLateComeTime(lateComeTime);

			/* 调用业务层执行添加操作 */
			String result = lateComeDAO.AddLateCome(lateCome);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除晚归信息：获取晚归信息的晚归记录id*/
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = lateComeDAO.DeleteLateCome(lateComeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新晚归信息之前先根据lateComeId查询某个晚归信息*/
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			LateCome lateCome = lateComeDAO.GetLateCome(lateComeId);

			// 客户端查询的晚归信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新晚归信息：获取晚归信息参数，参数保存到新建的晚归信息对象 */ 
			LateCome lateCome = new LateCome();
			int lateComeId = Integer.parseInt(request.getParameter("lateComeId"));
			lateCome.setLateComeId(lateComeId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setStudentObj(studentObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setReason(reason);
			String lateComeTime = new String(request.getParameter("lateComeTime").getBytes("iso-8859-1"), "UTF-8");
			lateCome.setLateComeTime(lateComeTime);

			/* 调用业务层执行更新操作 */
			String result = lateComeDAO.UpdateLateCome(lateCome);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
