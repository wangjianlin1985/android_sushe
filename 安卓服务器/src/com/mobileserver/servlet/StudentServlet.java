package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StudentDAO;
import com.mobileserver.domain.Student;

import org.json.JSONStringer;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造学生信息业务层对象*/
	private StudentDAO studentDAO = new StudentDAO();

	/*默认构造函数*/
	public StudentServlet() {
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
			/*获取查询学生信息的参数信息*/
			String studentNo = request.getParameter("studentNo");
			studentNo = studentNo == null ? "" : new String(request.getParameter(
					"studentNo").getBytes("iso-8859-1"), "UTF-8");
			String className = request.getParameter("className");
			className = className == null ? "" : new String(request.getParameter(
					"className").getBytes("iso-8859-1"), "UTF-8");
			String studentName = request.getParameter("studentName");
			studentName = studentName == null ? "" : new String(request.getParameter(
					"studentName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));

			/*调用业务逻辑层执行学生信息查询*/
			List<Student> studentList = studentDAO.QueryStudent(studentNo,className,studentName,birthday,roomObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Students>").append("\r\n");
			for (int i = 0; i < studentList.size(); i++) {
				sb.append("	<Student>").append("\r\n")
				.append("		<studentNo>")
				.append(studentList.get(i).getStudentNo())
				.append("</studentNo>").append("\r\n")
				.append("		<password>")
				.append(studentList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<className>")
				.append(studentList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("		<studentName>")
				.append(studentList.get(i).getStudentName())
				.append("</studentName>").append("\r\n")
				.append("		<sex>")
				.append(studentList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<birthday>")
				.append(studentList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<studentPhoto>")
				.append(studentList.get(i).getStudentPhoto())
				.append("</studentPhoto>").append("\r\n")
				.append("		<lxfs>")
				.append(studentList.get(i).getLxfs())
				.append("</lxfs>").append("\r\n")
				.append("		<roomObj>")
				.append(studentList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<memo>")
				.append(studentList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</Student>").append("\r\n");
			}
			sb.append("</Students>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Student student: studentList) {
				  stringer.object();
			  stringer.key("studentNo").value(student.getStudentNo());
			  stringer.key("password").value(student.getPassword());
			  stringer.key("className").value(student.getClassName());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
			  stringer.key("lxfs").value(student.getLxfs());
			  stringer.key("roomObj").value(student.getRoomObj());
			  stringer.key("memo").value(student.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
			Student student = new Student();
			String studentNo = new String(request.getParameter("studentNo").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNo(studentNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			student.setPassword(password);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			student.setClassName(className);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);
			String lxfs = new String(request.getParameter("lxfs").getBytes("iso-8859-1"), "UTF-8");
			student.setLxfs(lxfs);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			student.setRoomObj(roomObj);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			student.setMemo(memo);

			/* 调用业务层执行添加操作 */
			String result = studentDAO.AddStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除学生信息：获取学生信息的学号*/
			String studentNo = new String(request.getParameter("studentNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = studentDAO.DeleteStudent(studentNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新学生信息之前先根据studentNo查询某个学生信息*/
			String studentNo = new String(request.getParameter("studentNo").getBytes("iso-8859-1"), "UTF-8");
			Student student = studentDAO.GetStudent(studentNo);

			// 客户端查询的学生信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("studentNo").value(student.getStudentNo());
			  stringer.key("password").value(student.getPassword());
			  stringer.key("className").value(student.getClassName());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
			  stringer.key("lxfs").value(student.getLxfs());
			  stringer.key("roomObj").value(student.getRoomObj());
			  stringer.key("memo").value(student.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
			Student student = new Student();
			String studentNo = new String(request.getParameter("studentNo").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNo(studentNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			student.setPassword(password);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			student.setClassName(className);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);
			String lxfs = new String(request.getParameter("lxfs").getBytes("iso-8859-1"), "UTF-8");
			student.setLxfs(lxfs);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			student.setRoomObj(roomObj);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			student.setMemo(memo);

			/* 调用业务层执行更新操作 */
			String result = studentDAO.UpdateStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
