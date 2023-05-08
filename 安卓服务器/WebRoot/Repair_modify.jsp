<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Repair" %>
<%@ page import="com.chengxusheji.domain.RepairClass" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.RepairState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的RepairClass信息
    List<RepairClass> repairClassList = (List<RepairClass>)request.getAttribute("repairClassList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //获取所有的RepairState信息
    List<RepairState> repairStateList = (List<RepairState>)request.getAttribute("repairStateList");
    Repair repair = (Repair)request.getAttribute("repair");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改报修信息</TITLE>
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
/*验证表单*/
function checkForm() {
    var repaitTitle = document.getElementById("repair.repaitTitle").value;
    if(repaitTitle=="") {
        alert('请输入故障简述!');
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
    <TD align="left" vAlign=top ><s:form action="Repair/Repair_ModifyRepair.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>报修id:</td>
    <td width=70%><input id="repair.repairId" name="repair.repairId" type="text" value="<%=repair.getRepairId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>报修类别:</td>
    <td width=70%>
      <select name="repair.repaiClassObj.repairClassId">
      <%
        for(RepairClass repairClass:repairClassList) {
          String selected = "";
          if(repairClass.getRepairClassId() == repair.getRepaiClassObj().getRepairClassId())
            selected = "selected";
      %>
          <option value='<%=repairClass.getRepairClassId() %>' <%=selected %>><%=repairClass.getRepairClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>故障简述:</td>
    <td width=70%><input id="repair.repaitTitle" name="repair.repaitTitle" type="text" size="60" value='<%=repair.getRepaitTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>故障详述:</td>
    <td width=70%><textarea id="repair.repairContent" name="repair.repairContent" rows=5 cols=50><%=repair.getRepairContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>上报学生:</td>
    <td width=70%>
      <select name="repair.studentObj.studentNo">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNo().equals(repair.getStudentObj().getStudentNo()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNo() %>' <%=selected %>><%=student.getStudentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>处理结果:</td>
    <td width=70%><textarea id="repair.handleResult" name="repair.handleResult" rows=5 cols=50><%=repair.getHandleResult() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>维修状态:</td>
    <td width=70%>
      <select name="repair.repairStateObj.repairStateId">
      <%
        for(RepairState repairState:repairStateList) {
          String selected = "";
          if(repairState.getRepairStateId() == repair.getRepairStateObj().getRepairStateId())
            selected = "selected";
      %>
          <option value='<%=repairState.getRepairStateId() %>' <%=selected %>><%=repairState.getRepairStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
