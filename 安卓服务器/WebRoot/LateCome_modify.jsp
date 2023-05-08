<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.LateCome" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Student��Ϣ
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    LateCome lateCome = (LateCome)request.getAttribute("lateCome");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸������Ϣ</TITLE>
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
    var reason = document.getElementById("lateCome.reason").value;
    if(reason=="") {
        alert('���������ԭ��!');
        return false;
    }
    var lateComeTime = document.getElementById("lateCome.lateComeTime").value;
    if(lateComeTime=="") {
        alert('���������ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="LateCome/LateCome_ModifyLateCome.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����¼id:</td>
    <td width=70%><input id="lateCome.lateComeId" name="lateCome.lateComeId" type="text" value="<%=lateCome.getLateComeId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���ѧ��:</td>
    <td width=70%>
      <select name="lateCome.studentObj.studentNo">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNo().equals(lateCome.getStudentObj().getStudentNo()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNo() %>' <%=selected %>><%=student.getStudentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ԭ��:</td>
    <td width=70%><textarea id="lateCome.reason" name="lateCome.reason" rows=5 cols=50><%=lateCome.getReason() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
    <td width=70%><input id="lateCome.lateComeTime" name="lateCome.lateComeTime" type="text" size="20" value='<%=lateCome.getLateComeTime() %>'/></td>
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
