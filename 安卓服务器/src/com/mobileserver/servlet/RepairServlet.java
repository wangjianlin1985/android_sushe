package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RepairDAO;
import com.mobileserver.domain.Repair;

import org.json.JSONStringer;

public class RepairServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造报修信息业务层对象*/
	private RepairDAO repairDAO = new RepairDAO();

	/*默认构造函数*/
	public RepairServlet() {
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
			/*获取查询报修信息的参数信息*/
			int repaiClassObj = 0;
			if (request.getParameter("repaiClassObj") != null)
				repaiClassObj = Integer.parseInt(request.getParameter("repaiClassObj"));
			String repaitTitle = request.getParameter("repaitTitle");
			repaitTitle = repaitTitle == null ? "" : new String(request.getParameter(
					"repaitTitle").getBytes("iso-8859-1"), "UTF-8");
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			int repairStateObj = 0;
			if (request.getParameter("repairStateObj") != null)
				repairStateObj = Integer.parseInt(request.getParameter("repairStateObj"));

			/*调用业务逻辑层执行报修信息查询*/
			List<Repair> repairList = repairDAO.QueryRepair(repaiClassObj,repaitTitle,studentObj,repairStateObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Repairs>").append("\r\n");
			for (int i = 0; i < repairList.size(); i++) {
				sb.append("	<Repair>").append("\r\n")
				.append("		<repairId>")
				.append(repairList.get(i).getRepairId())
				.append("</repairId>").append("\r\n")
				.append("		<repaiClassObj>")
				.append(repairList.get(i).getRepaiClassObj())
				.append("</repaiClassObj>").append("\r\n")
				.append("		<repaitTitle>")
				.append(repairList.get(i).getRepaitTitle())
				.append("</repaitTitle>").append("\r\n")
				.append("		<repairContent>")
				.append(repairList.get(i).getRepairContent())
				.append("</repairContent>").append("\r\n")
				.append("		<studentObj>")
				.append(repairList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<handleResult>")
				.append(repairList.get(i).getHandleResult())
				.append("</handleResult>").append("\r\n")
				.append("		<repairStateObj>")
				.append(repairList.get(i).getRepairStateObj())
				.append("</repairStateObj>").append("\r\n")
				.append("	</Repair>").append("\r\n");
			}
			sb.append("</Repairs>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Repair repair: repairList) {
				  stringer.object();
			  stringer.key("repairId").value(repair.getRepairId());
			  stringer.key("repaiClassObj").value(repair.getRepaiClassObj());
			  stringer.key("repaitTitle").value(repair.getRepaitTitle());
			  stringer.key("repairContent").value(repair.getRepairContent());
			  stringer.key("studentObj").value(repair.getStudentObj());
			  stringer.key("handleResult").value(repair.getHandleResult());
			  stringer.key("repairStateObj").value(repair.getRepairStateObj());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加报修信息：获取报修信息参数，参数保存到新建的报修信息对象 */ 
			Repair repair = new Repair();
			int repairId = Integer.parseInt(request.getParameter("repairId"));
			repair.setRepairId(repairId);
			int repaiClassObj = Integer.parseInt(request.getParameter("repaiClassObj"));
			repair.setRepaiClassObj(repaiClassObj);
			String repaitTitle = new String(request.getParameter("repaitTitle").getBytes("iso-8859-1"), "UTF-8");
			repair.setRepaitTitle(repaitTitle);
			String repairContent = new String(request.getParameter("repairContent").getBytes("iso-8859-1"), "UTF-8");
			repair.setRepairContent(repairContent);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			repair.setStudentObj(studentObj);
			String handleResult = new String(request.getParameter("handleResult").getBytes("iso-8859-1"), "UTF-8");
			repair.setHandleResult(handleResult);
			int repairStateObj = Integer.parseInt(request.getParameter("repairStateObj"));
			repair.setRepairStateObj(repairStateObj);

			/* 调用业务层执行添加操作 */
			String result = repairDAO.AddRepair(repair);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除报修信息：获取报修信息的报修id*/
			int repairId = Integer.parseInt(request.getParameter("repairId"));
			/*调用业务逻辑层执行删除操作*/
			String result = repairDAO.DeleteRepair(repairId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新报修信息之前先根据repairId查询某个报修信息*/
			int repairId = Integer.parseInt(request.getParameter("repairId"));
			Repair repair = repairDAO.GetRepair(repairId);

			// 客户端查询的报修信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("repairId").value(repair.getRepairId());
			  stringer.key("repaiClassObj").value(repair.getRepaiClassObj());
			  stringer.key("repaitTitle").value(repair.getRepaitTitle());
			  stringer.key("repairContent").value(repair.getRepairContent());
			  stringer.key("studentObj").value(repair.getStudentObj());
			  stringer.key("handleResult").value(repair.getHandleResult());
			  stringer.key("repairStateObj").value(repair.getRepairStateObj());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新报修信息：获取报修信息参数，参数保存到新建的报修信息对象 */ 
			Repair repair = new Repair();
			int repairId = Integer.parseInt(request.getParameter("repairId"));
			repair.setRepairId(repairId);
			int repaiClassObj = Integer.parseInt(request.getParameter("repaiClassObj"));
			repair.setRepaiClassObj(repaiClassObj);
			String repaitTitle = new String(request.getParameter("repaitTitle").getBytes("iso-8859-1"), "UTF-8");
			repair.setRepaitTitle(repaitTitle);
			String repairContent = new String(request.getParameter("repairContent").getBytes("iso-8859-1"), "UTF-8");
			repair.setRepairContent(repairContent);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			repair.setStudentObj(studentObj);
			String handleResult = new String(request.getParameter("handleResult").getBytes("iso-8859-1"), "UTF-8");
			repair.setHandleResult(handleResult);
			int repairStateObj = Integer.parseInt(request.getParameter("repairStateObj"));
			repair.setRepairStateObj(repairStateObj);

			/* 调用业务层执行更新操作 */
			String result = repairDAO.UpdateRepair(repair);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
