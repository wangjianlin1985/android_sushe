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
import com.chengxusheji.dao.RepairStateDAO;
import com.chengxusheji.domain.RepairState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RepairStateAction extends BaseAction {

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

    private int repairStateId;
    public void setRepairStateId(int repairStateId) {
        this.repairStateId = repairStateId;
    }
    public int getRepairStateId() {
        return repairStateId;
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
    @Resource RepairStateDAO repairStateDAO;

    /*待操作的RepairState对象*/
    private RepairState repairState;
    public void setRepairState(RepairState repairState) {
        this.repairState = repairState;
    }
    public RepairState getRepairState() {
        return this.repairState;
    }

    /*跳转到添加RepairState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加RepairState信息*/
    @SuppressWarnings("deprecation")
    public String AddRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairStateDAO.AddRepairState(repairState);
            ctx.put("message",  java.net.URLEncoder.encode("RepairState添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairState添加失败!"));
            return "error";
        }
    }

    /*查询RepairState信息*/
    public String QueryRepairState() {
        if(currentPage == 0) currentPage = 1;
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        repairStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = repairStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = repairStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairStateList",  repairStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryRepairStateOutputToExcel() { 
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RepairState信息记录"; 
        String[] headers = { "状态id","状态名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairStateList.size();i++) {
        	RepairState repairState = repairStateList.get(i); 
        	dataset.add(new String[]{repairState.getRepairStateId() + "",repairState.getRepairStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RepairState.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询RepairState信息*/
    public String FrontQueryRepairState() {
        if(currentPage == 0) currentPage = 1;
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        repairStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = repairStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = repairStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairStateList",  repairStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的RepairState信息*/
    public String ModifyRepairStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键repairStateId获取RepairState对象*/
        RepairState repairState = repairStateDAO.GetRepairStateByRepairStateId(repairStateId);

        ctx.put("repairState",  repairState);
        return "modify_view";
    }

    /*查询要修改的RepairState信息*/
    public String FrontShowRepairStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键repairStateId获取RepairState对象*/
        RepairState repairState = repairStateDAO.GetRepairStateByRepairStateId(repairStateId);

        ctx.put("repairState",  repairState);
        return "front_show_view";
    }

    /*更新修改RepairState信息*/
    public String ModifyRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairStateDAO.UpdateRepairState(repairState);
            ctx.put("message",  java.net.URLEncoder.encode("RepairState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairState信息更新失败!"));
            return "error";
       }
   }

    /*删除RepairState信息*/
    public String DeleteRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            repairStateDAO.DeleteRepairState(repairStateId);
            ctx.put("message",  java.net.URLEncoder.encode("RepairState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairState删除失败!"));
            return "error";
        }
    }

}
