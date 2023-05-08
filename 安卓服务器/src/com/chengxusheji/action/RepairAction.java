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
import com.chengxusheji.dao.RepairDAO;
import com.chengxusheji.domain.Repair;
import com.chengxusheji.dao.RepairClassDAO;
import com.chengxusheji.domain.RepairClass;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.RepairStateDAO;
import com.chengxusheji.domain.RepairState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RepairAction extends BaseAction {

    /*界面层需要查询的属性: 报修类别*/
    private RepairClass repaiClassObj;
    public void setRepaiClassObj(RepairClass repaiClassObj) {
        this.repaiClassObj = repaiClassObj;
    }
    public RepairClass getRepaiClassObj() {
        return this.repaiClassObj;
    }

    /*界面层需要查询的属性: 故障简述*/
    private String repaitTitle;
    public void setRepaitTitle(String repaitTitle) {
        this.repaitTitle = repaitTitle;
    }
    public String getRepaitTitle() {
        return this.repaitTitle;
    }

    /*界面层需要查询的属性: 上报学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*界面层需要查询的属性: 维修状态*/
    private RepairState repairStateObj;
    public void setRepairStateObj(RepairState repairStateObj) {
        this.repairStateObj = repairStateObj;
    }
    public RepairState getRepairStateObj() {
        return this.repairStateObj;
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

    private int repairId;
    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }
    public int getRepairId() {
        return repairId;
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
    @Resource RepairClassDAO repairClassDAO;
    @Resource StudentDAO studentDAO;
    @Resource RepairStateDAO repairStateDAO;
    @Resource RepairDAO repairDAO;

    /*待操作的Repair对象*/
    private Repair repair;
    public void setRepair(Repair repair) {
        this.repair = repair;
    }
    public Repair getRepair() {
        return this.repair;
    }

    /*跳转到添加Repair视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的RepairClass信息*/
        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的RepairState信息*/
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        return "add_view";
    }

    /*添加Repair信息*/
    @SuppressWarnings("deprecation")
    public String AddRepair() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RepairClass repaiClassObj = repairClassDAO.GetRepairClassByRepairClassId(repair.getRepaiClassObj().getRepairClassId());
            repair.setRepaiClassObj(repaiClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(repair.getStudentObj().getStudentNo());
            repair.setStudentObj(studentObj);
            RepairState repairStateObj = repairStateDAO.GetRepairStateByRepairStateId(repair.getRepairStateObj().getRepairStateId());
            repair.setRepairStateObj(repairStateObj);
            repairDAO.AddRepair(repair);
            ctx.put("message",  java.net.URLEncoder.encode("Repair添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repair添加失败!"));
            return "error";
        }
    }

    /*查询Repair信息*/
    public String QueryRepair() {
        if(currentPage == 0) currentPage = 1;
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj, repaitTitle, studentObj, repairStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        repairDAO.CalculateTotalPageAndRecordNumber(repaiClassObj, repaitTitle, studentObj, repairStateObj);
        /*获取到总的页码数目*/
        totalPage = repairDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = repairDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairList",  repairList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("repaiClassObj", repaiClassObj);
        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        ctx.put("repaitTitle", repaitTitle);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("repairStateObj", repairStateObj);
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryRepairOutputToExcel() { 
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj,repaitTitle,studentObj,repairStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Repair信息记录"; 
        String[] headers = { "报修id","报修类别","故障简述","上报学生","维修状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairList.size();i++) {
        	Repair repair = repairList.get(i); 
        	dataset.add(new String[]{repair.getRepairId() + "",repair.getRepaiClassObj().getRepairClassName(),
repair.getRepaitTitle(),repair.getStudentObj().getStudentName(),
repair.getRepairStateObj().getRepairStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"Repair.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Repair信息*/
    public String FrontQueryRepair() {
        if(currentPage == 0) currentPage = 1;
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj, repaitTitle, studentObj, repairStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        repairDAO.CalculateTotalPageAndRecordNumber(repaiClassObj, repaitTitle, studentObj, repairStateObj);
        /*获取到总的页码数目*/
        totalPage = repairDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = repairDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairList",  repairList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("repaiClassObj", repaiClassObj);
        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        ctx.put("repaitTitle", repaitTitle);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("repairStateObj", repairStateObj);
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        return "front_query_view";
    }

    /*查询要修改的Repair信息*/
    public String ModifyRepairQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键repairId获取Repair对象*/
        Repair repair = repairDAO.GetRepairByRepairId(repairId);

        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        ctx.put("repair",  repair);
        return "modify_view";
    }

    /*查询要修改的Repair信息*/
    public String FrontShowRepairQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键repairId获取Repair对象*/
        Repair repair = repairDAO.GetRepairByRepairId(repairId);

        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        ctx.put("repair",  repair);
        return "front_show_view";
    }

    /*更新修改Repair信息*/
    public String ModifyRepair() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RepairClass repaiClassObj = repairClassDAO.GetRepairClassByRepairClassId(repair.getRepaiClassObj().getRepairClassId());
            repair.setRepaiClassObj(repaiClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(repair.getStudentObj().getStudentNo());
            repair.setStudentObj(studentObj);
            RepairState repairStateObj = repairStateDAO.GetRepairStateByRepairStateId(repair.getRepairStateObj().getRepairStateId());
            repair.setRepairStateObj(repairStateObj);
            repairDAO.UpdateRepair(repair);
            ctx.put("message",  java.net.URLEncoder.encode("Repair信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repair信息更新失败!"));
            return "error";
       }
   }

    /*删除Repair信息*/
    public String DeleteRepair() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            repairDAO.DeleteRepair(repairId);
            ctx.put("message",  java.net.URLEncoder.encode("Repair删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repair删除失败!"));
            return "error";
        }
    }

}
