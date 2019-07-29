package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.RoomDAO;
import com.chengxusheji.domain.Room;
import com.chengxusheji.dao.HouseDAO;
import com.chengxusheji.domain.House;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RoomAction extends BaseAction {

    /*界面层需要查询的属性: 所在宿舍*/
    private House houseObj;
    public void setHouseObj(House houseObj) {
        this.houseObj = houseObj;
    }
    public House getHouseObj() {
        return this.houseObj;
    }

    /*界面层需要查询的属性: 房间名称*/
    private String roomName;
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return this.roomName;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int roomId;
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource HouseDAO houseDAO;
    @Resource RoomDAO roomDAO;

    /*待操作的Room对象*/
    private Room room;
    public void setRoom(Room room) {
        this.room = room;
    }
    public Room getRoom() {
        return this.room;
    }

    /*跳转到添加Room视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的House信息*/
        List<House> houseList = houseDAO.QueryAllHouseInfo();
        ctx.put("houseList", houseList);
        return "add_view";
    }

    /*添加Room信息*/
    @SuppressWarnings("deprecation")
    public String AddRoom() {
        ActionContext ctx = ActionContext.getContext();
        try {
            House houseObj = houseDAO.GetHouseByHouseId(room.getHouseObj().getHouseId());
            room.setHouseObj(houseObj);
            roomDAO.AddRoom(room);
            ctx.put("message",  java.net.URLEncoder.encode("Room添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Room添加失败!"));
            return "error";
        }
    }

    /*查询Room信息*/
    public String QueryRoom() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(houseObj, roomName, currentPage);
        /*计算总的页数和总的记录数*/
        roomDAO.CalculateTotalPageAndRecordNumber(houseObj, roomName);
        /*获取到总的页码数目*/
        totalPage = roomDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomList",  roomList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("houseObj", houseObj);
        List<House> houseList = houseDAO.QueryAllHouseInfo();
        ctx.put("houseList", houseList);
        ctx.put("roomName", roomName);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryRoomOutputToExcel() { 
        if(roomName == null) roomName = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(houseObj,roomName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Room信息记录"; 
        String[] headers = { "房间id","所在宿舍","房间名称","床位数"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<roomList.size();i++) {
        	Room room = roomList.get(i); 
        	dataset.add(new String[]{room.getRoomId() + "",room.getHouseObj().getHouseName(),
room.getRoomName(),room.getBedNum() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Room.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Room信息*/
    public String FrontQueryRoom() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(houseObj, roomName, currentPage);
        /*计算总的页数和总的记录数*/
        roomDAO.CalculateTotalPageAndRecordNumber(houseObj, roomName);
        /*获取到总的页码数目*/
        totalPage = roomDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomList",  roomList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("houseObj", houseObj);
        List<House> houseList = houseDAO.QueryAllHouseInfo();
        ctx.put("houseList", houseList);
        ctx.put("roomName", roomName);
        return "front_query_view";
    }

    /*查询要修改的Room信息*/
    public String ModifyRoomQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomId获取Room对象*/
        Room room = roomDAO.GetRoomByRoomId(roomId);

        List<House> houseList = houseDAO.QueryAllHouseInfo();
        ctx.put("houseList", houseList);
        ctx.put("room",  room);
        return "modify_view";
    }

    /*查询要修改的Room信息*/
    public String FrontShowRoomQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomId获取Room对象*/
        Room room = roomDAO.GetRoomByRoomId(roomId);

        List<House> houseList = houseDAO.QueryAllHouseInfo();
        ctx.put("houseList", houseList);
        ctx.put("room",  room);
        return "front_show_view";
    }

    /*更新修改Room信息*/
    public String ModifyRoom() {
        ActionContext ctx = ActionContext.getContext();
        try {
            House houseObj = houseDAO.GetHouseByHouseId(room.getHouseObj().getHouseId());
            room.setHouseObj(houseObj);
            roomDAO.UpdateRoom(room);
            ctx.put("message",  java.net.URLEncoder.encode("Room信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Room信息更新失败!"));
            return "error";
       }
   }

    /*删除Room信息*/
    public String DeleteRoom() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomDAO.DeleteRoom(roomId);
            ctx.put("message",  java.net.URLEncoder.encode("Room删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Room删除失败!"));
            return "error";
        }
    }

}
