<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Repair" %>
<%@ page import="com.chengxusheji.domain.RepairClass" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.RepairState" %>
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

%>
<HTML><HEAD><TITLE>查看报修信息</TITLE>
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
    <td width=30%>报修id:</td>
    <td width=70%><%=repair.getRepairId() %></td>
  </tr>

  <tr>
    <td width=30%>报修类别:</td>
    <td width=70%>
      <%=repair.getRepaiClassObj().getRepairClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>故障简述:</td>
    <td width=70%><%=repair.getRepaitTitle() %></td>
  </tr>

  <tr>
    <td width=30%>故障详述:</td>
    <td width=70%><%=repair.getRepairContent() %></td>
  </tr>

  <tr>
    <td width=30%>上报学生:</td>
    <td width=70%>
      <%=repair.getStudentObj().getStudentName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>处理结果:</td>
    <td width=70%><%=repair.getHandleResult() %></td>
  </tr>

  <tr>
    <td width=30%>维修状态:</td>
    <td width=70%>
      <%=repair.getRepairStateObj().getRepairStateName() %>
    </td>
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
