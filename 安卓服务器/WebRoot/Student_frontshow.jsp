<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Room" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Room信息
    List<Room> roomList = (List<Room>)request.getAttribute("roomList");
    Student student = (Student)request.getAttribute("student");

%>
<HTML><HEAD><TITLE>查看学生信息</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>学号:</td>
    <td width=70%><%=student.getStudentNo() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=student.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>所在班级:</td>
    <td width=70%><%=student.getClassName() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=student.getStudentName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=student.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
        <% java.text.DateFormat birthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthdaySDF.format(student.getBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>照片:</td>
    <td width=70%><img src="<%=basePath %><%=student.getStudentPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>联系方式:</td>
    <td width=70%><%=student.getLxfs() %></td>
  </tr>

  <tr>
    <td width=30%>所在房间:</td>
    <td width=70%>
      <%=student.getRoomObj().getRoomName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><%=student.getMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
