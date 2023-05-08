<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Repair" %>
<%@ page import="com.chengxusheji.domain.RepairClass" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.RepairState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�RepairClass��Ϣ
    List<RepairClass> repairClassList = (List<RepairClass>)request.getAttribute("repairClassList");
    //��ȡ���е�Student��Ϣ
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //��ȡ���е�RepairState��Ϣ
    List<RepairState> repairStateList = (List<RepairState>)request.getAttribute("repairStateList");
    Repair repair = (Repair)request.getAttribute("repair");

%>
<HTML><HEAD><TITLE>�鿴������Ϣ</TITLE>
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
    <td width=30%>����id:</td>
    <td width=70%><%=repair.getRepairId() %></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%>
      <%=repair.getRepaiClassObj().getRepairClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ϼ���:</td>
    <td width=70%><%=repair.getRepaitTitle() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=repair.getRepairContent() %></td>
  </tr>

  <tr>
    <td width=30%>�ϱ�ѧ��:</td>
    <td width=70%>
      <%=repair.getStudentObj().getStudentName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=repair.getHandleResult() %></td>
  </tr>

  <tr>
    <td width=30%>ά��״̬:</td>
    <td width=70%>
      <%=repair.getRepairStateObj().getRepairStateName() %>
    </td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
