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

	/*构造报修类别业务层对象*/
	private RepairClassDAO repairClassDAO = new RepairClassDAO();

	/*默认构造函数*/
	public RepairClassServlet() {
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
			/*获取查询报修类别的参数信息*/

			/*调用业务逻辑层执行报修类别查询*/
			List<RepairClass> repairClassList = repairClassDAO.QueryRepairClass();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加报修类别：获取报修类别参数，参数保存到新建的报修类别对象 */ 
			RepairClass repairClass = new RepairClass();
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			repairClass.setRepairClassId(repairClassId);
			String repairClassName = new String(request.getParameter("repairClassName").getBytes("iso-8859-1"), "UTF-8");
			repairClass.setRepairClassName(repairClassName);

			/* 调用业务层执行添加操作 */
			String result = repairClassDAO.AddRepairClass(repairClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除报修类别：获取报修类别的维修类别id*/
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			/*调用业务逻辑层执行删除操作*/
			String result = repairClassDAO.DeleteRepairClass(repairClassId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新报修类别之前先根据repairClassId查询某个报修类别*/
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			RepairClass repairClass = repairClassDAO.GetRepairClass(repairClassId);

			// 客户端查询的报修类别对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新报修类别：获取报修类别参数，参数保存到新建的报修类别对象 */ 
			RepairClass repairClass = new RepairClass();
			int repairClassId = Integer.parseInt(request.getParameter("repairClassId"));
			repairClass.setRepairClassId(repairClassId);
			String repairClassName = new String(request.getParameter("repairClassName").getBytes("iso-8859-1"), "UTF-8");
			repairClass.setRepairClassName(repairClassName);

			/* 调用业务层执行更新操作 */
			String result = repairClassDAO.UpdateRepairClass(repairClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
