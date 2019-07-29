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
import com.chengxusheji.dao.LateComeDAO;
import com.chengxusheji.domain.LateCome;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LateComeAction extends BaseAction {

    /*界面层需要查询的属性: 晚归学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*界面层需要查询的属性: 晚归原因*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }

    /*界面层需要查询的属性: 晚归时间*/
    private String lateComeTime;
    public void setLateComeTime(String lateComeTime) {
        this.lateComeTime = lateComeTime;
    }
    public String getLateComeTime() {
        return this.lateComeTime;
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

    private int lateComeId;
    public void setLateComeId(int lateComeId) {
        this.lateComeId = lateComeId;
    }
    public int getLateComeId() {
        return lateComeId;
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
    @Resource StudentDAO studentDAO;
    @Resource LateComeDAO lateComeDAO;

    /*待操作的LateCome对象*/
    private LateCome lateCome;
    public void setLateCome(LateCome lateCome) {
        this.lateCome = lateCome;
    }
    public LateCome getLateCome() {
        return this.lateCome;
    }

    /*跳转到添加LateCome视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*添加LateCome信息*/
    @SuppressWarnings("deprecation")
    public String AddLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNo(lateCome.getStudentObj().getStudentNo());
            lateCome.setStudentObj(studentObj);
            lateComeDAO.AddLateCome(lateCome);
            ctx.put("message",  java.net.URLEncoder.encode("LateCome添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateCome添加失败!"));
            return "error";
        }
    }

    /*查询LateCome信息*/
    public String QueryLateCome() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj, reason, lateComeTime, currentPage);
        /*计算总的页数和总的记录数*/
        lateComeDAO.CalculateTotalPageAndRecordNumber(studentObj, reason, lateComeTime);
        /*获取到总的页码数目*/
        totalPage = lateComeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = lateComeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("lateComeList",  lateComeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("reason", reason);
        ctx.put("lateComeTime", lateComeTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLateComeOutputToExcel() { 
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj,reason,lateComeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LateCome信息记录"; 
        String[] headers = { "晚归记录id","晚归学生","晚归原因","晚归时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<lateComeList.size();i++) {
        	LateCome lateCome = lateComeList.get(i); 
        	dataset.add(new String[]{lateCome.getLateComeId() + "",lateCome.getStudentObj().getStudentName(),
lateCome.getReason(),lateCome.getLateComeTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LateCome.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LateCome信息*/
    public String FrontQueryLateCome() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj, reason, lateComeTime, currentPage);
        /*计算总的页数和总的记录数*/
        lateComeDAO.CalculateTotalPageAndRecordNumber(studentObj, reason, lateComeTime);
        /*获取到总的页码数目*/
        totalPage = lateComeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = lateComeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("lateComeList",  lateComeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("reason", reason);
        ctx.put("lateComeTime", lateComeTime);
        return "front_query_view";
    }

    /*查询要修改的LateCome信息*/
    public String ModifyLateComeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键lateComeId获取LateCome对象*/
        LateCome lateCome = lateComeDAO.GetLateComeByLateComeId(lateComeId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("lateCome",  lateCome);
        return "modify_view";
    }

    /*查询要修改的LateCome信息*/
    public String FrontShowLateComeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键lateComeId获取LateCome对象*/
        LateCome lateCome = lateComeDAO.GetLateComeByLateComeId(lateComeId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("lateCome",  lateCome);
        return "front_show_view";
    }

    /*更新修改LateCome信息*/
    public String ModifyLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNo(lateCome.getStudentObj().getStudentNo());
            lateCome.setStudentObj(studentObj);
            lateComeDAO.UpdateLateCome(lateCome);
            ctx.put("message",  java.net.URLEncoder.encode("LateCome信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateCome信息更新失败!"));
            return "error";
       }
   }

    /*删除LateCome信息*/
    public String DeleteLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            lateComeDAO.DeleteLateCome(lateComeId);
            ctx.put("message",  java.net.URLEncoder.encode("LateCome删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateCome删除失败!"));
            return "error";
        }
    }

}
