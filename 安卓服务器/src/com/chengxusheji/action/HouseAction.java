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
import com.chengxusheji.dao.HouseDAO;
import com.chengxusheji.domain.House;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HouseAction extends BaseAction {

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

    private int houseId;
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
    public int getHouseId() {
        return houseId;
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

    /*待操作的House对象*/
    private House house;
    public void setHouse(House house) {
        this.house = house;
    }
    public House getHouse() {
        return this.house;
    }

    /*跳转到添加House视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加House信息*/
    @SuppressWarnings("deprecation")
    public String AddHouse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            houseDAO.AddHouse(house);
            ctx.put("message",  java.net.URLEncoder.encode("House添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("House添加失败!"));
            return "error";
        }
    }

    /*查询House信息*/
    public String QueryHouse() {
        if(currentPage == 0) currentPage = 1;
        List<House> houseList = houseDAO.QueryHouseInfo(currentPage);
        /*计算总的页数和总的记录数*/
        houseDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = houseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = houseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("houseList",  houseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryHouseOutputToExcel() { 
        List<House> houseList = houseDAO.QueryHouseInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "House信息记录"; 
        String[] headers = { "宿舍id","宿舍楼名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<houseList.size();i++) {
        	House house = houseList.get(i); 
        	dataset.add(new String[]{house.getHouseId() + "",house.getHouseName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"House.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询House信息*/
    public String FrontQueryHouse() {
        if(currentPage == 0) currentPage = 1;
        List<House> houseList = houseDAO.QueryHouseInfo(currentPage);
        /*计算总的页数和总的记录数*/
        houseDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = houseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = houseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("houseList",  houseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的House信息*/
    public String ModifyHouseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键houseId获取House对象*/
        House house = houseDAO.GetHouseByHouseId(houseId);

        ctx.put("house",  house);
        return "modify_view";
    }

    /*查询要修改的House信息*/
    public String FrontShowHouseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键houseId获取House对象*/
        House house = houseDAO.GetHouseByHouseId(houseId);

        ctx.put("house",  house);
        return "front_show_view";
    }

    /*更新修改House信息*/
    public String ModifyHouse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            houseDAO.UpdateHouse(house);
            ctx.put("message",  java.net.URLEncoder.encode("House信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("House信息更新失败!"));
            return "error";
       }
   }

    /*删除House信息*/
    public String DeleteHouse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            houseDAO.DeleteHouse(houseId);
            ctx.put("message",  java.net.URLEncoder.encode("House删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("House删除失败!"));
            return "error";
        }
    }

}
