<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Room" %>
<%@ page import="com.chengxusheji.domain.House" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的House信息
    List<House> houseList = (List<House>)request.getAttribute("houseList");
    Room room = (Room)request.getAttribute("room");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改房间信息</TITLE>
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
    var roomName = document.getElementById("room.roomName").value;
    if(roomName=="") {
        alert('请输入房间名称!');
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
    <TD align="left" vAlign=top ><s:form action="Room/Room_ModifyRoom.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>房间id:</td>
    <td width=70%><input id="room.roomId" name="room.roomId" type="text" value="<%=room.getRoomId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>所在宿舍:</td>
    <td width=70%>
      <select name="room.houseObj.houseId">
      <%
        for(House house:houseList) {
          String selected = "";
          if(house.getHouseId() == room.getHouseObj().getHouseId())
            selected = "selected";
      %>
          <option value='<%=house.getHouseId() %>' <%=selected %>><%=house.getHouseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>房间名称:</td>
    <td width=70%><input id="room.roomName" name="room.roomName" type="text" size="20" value='<%=room.getRoomName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>床位数:</td>
    <td width=70%><input id="room.bedNum" name="room.bedNum" type="text" size="8" value='<%=room.getBedNum() %>'/></td>
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
