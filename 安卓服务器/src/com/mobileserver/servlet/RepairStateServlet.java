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

	/*构造维修状态业务层对象*/
	private RepairStateDAO repairStateDAO = new RepairStateDAO();

	/*默认构造函数*/
	public RepairStateServlet() {
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
			/*获取查询维修状态的参数信息*/

			/*调用业务逻辑层执行维修状态查询*/
			List<RepairState> repairStateList = repairStateDAO.QueryRepairState();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加维修状态：获取维修状态参数，参数保存到新建的维修状态对象 */ 
			RepairState repairState = new RepairState();
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			repairState.setRepairStateId(repairStateId);
			String repairStateName = new String(request.getParameter("repairStateName").getBytes("iso-8859-1"), "UTF-8");
			repairState.setRepairStateName(repairStateName);

			/* 调用业务层执行添加操作 */
			String result = repairStateDAO.AddRepairState(repairState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除维修状态：获取维修状态的状态id*/
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = repairStateDAO.DeleteRepairState(repairStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新维修状态之前先根据repairStateId查询某个维修状态*/
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			RepairState repairState = repairStateDAO.GetRepairState(repairStateId);

			// 客户端查询的维修状态对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新维修状态：获取维修状态参数，参数保存到新建的维修状态对象 */ 
			RepairState repairState = new RepairState();
			int repairStateId = Integer.parseInt(request.getParameter("repairStateId"));
			repairState.setRepairStateId(repairStateId);
			String repairStateName = new String(request.getParameter("repairStateName").getBytes("iso-8859-1"), "UTF-8");
			repairState.setRepairStateName(repairStateName);

			/* 调用业务层执行更新操作 */
			String result = repairStateDAO.UpdateRepairState(repairState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
