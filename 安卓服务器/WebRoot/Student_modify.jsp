<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Room" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Room��Ϣ
    List<Room> roomList = (List<Room>)request.getAttribute("roomList");
    Student student = (Student)request.getAttribute("student");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�ѧ����Ϣ</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var studentNo = document.getElementById("student.studentNo").value;
    if(studentNo=="") {
        alert('������ѧ��!');
        return false;
    }
    var password = document.getElementById("student.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var className = document.getElementById("student.className").value;
    if(className=="") {
        alert('���������ڰ༶!');
        return false;
    }
    var studentName = document.getElementById("student.studentName").value;
    if(studentName=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("student.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Student/Student_ModifyStudent.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>ѧ��:</td>
    <td width=70%><input id="student.studentNo" name="student.studentNo" type="text" value="<%=student.getStudentNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="student.password" name="student.password" type="text" size="30" value='<%=student.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ڰ༶:</td>
    <td width=70%><input id="student.className" name="student.className" type="text" size="20" value='<%=student.getClassName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="student.studentName" name="student.studentName" type="text" size="20" value='<%=student.getStudentName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="student.sex" name="student.sex" type="text" size="4" value='<%=student.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat birthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="student.birthday"  name="student.birthday" onclick="setDay(this);" value='<%=birthdaySDF.format(student.getBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>��Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=student.getStudentPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="student.studentPhoto" value="<%=student.getStudentPhoto() %>" />
    <input id="studentPhotoFile" name="studentPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>��ϵ��ʽ:</td>
    <td width=70%><input id="student.lxfs" name="student.lxfs" type="text" size="20" value='<%=student.getLxfs() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ڷ���:</td>
    <td width=70%>
      <select name="student.roomObj.roomId">
      <%
        for(Room room:roomList) {
          String selected = "";
          if(room.getRoomId() == student.getRoomObj().getRoomId())
            selected = "selected";
      %>
          <option value='<%=room.getRoomId() %>' <%=selected %>><%=room.getRoomName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="student.memo" name="student.memo" rows=5 cols=50><%=student.getMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
