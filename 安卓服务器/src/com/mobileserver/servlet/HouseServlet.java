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

	/*构造宿舍楼业务层对象*/
	private HouseDAO houseDAO = new HouseDAO();

	/*默认构造函数*/
	public HouseServlet() {
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
			/*获取查询宿舍楼的参数信息*/

			/*调用业务逻辑层执行宿舍楼查询*/
			List<House> houseList = houseDAO.QueryHouse();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加宿舍楼：获取宿舍楼参数，参数保存到新建的宿舍楼对象 */ 
			House house = new House();
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			house.setHouseId(houseId);
			String houseName = new String(request.getParameter("houseName").getBytes("iso-8859-1"), "UTF-8");
			house.setHouseName(houseName);

			/* 调用业务层执行添加操作 */
			String result = houseDAO.AddHouse(house);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除宿舍楼：获取宿舍楼的宿舍id*/
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			/*调用业务逻辑层执行删除操作*/
			String result = houseDAO.DeleteHouse(houseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新宿舍楼之前先根据houseId查询某个宿舍楼*/
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			House house = houseDAO.GetHouse(houseId);

			// 客户端查询的宿舍楼对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新宿舍楼：获取宿舍楼参数，参数保存到新建的宿舍楼对象 */ 
			House house = new House();
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			house.setHouseId(houseId);
			String houseName = new String(request.getParameter("houseName").getBytes("iso-8859-1"), "UTF-8");
			house.setHouseName(houseName);

			/* 调用业务层执行更新操作 */
			String result = houseDAO.UpdateHouse(house);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
