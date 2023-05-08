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

    /*�������Ҫ��ѯ������: ���ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*�������Ҫ��ѯ������: ���ԭ��*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }

    /*�������Ҫ��ѯ������: ���ʱ��*/
    private String lateComeTime;
    public void setLateComeTime(String lateComeTime) {
        this.lateComeTime = lateComeTime;
    }
    public String getLateComeTime() {
        return this.lateComeTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StudentDAO studentDAO;
    @Resource LateComeDAO lateComeDAO;

    /*��������LateCome����*/
    private LateCome lateCome;
    public void setLateCome(LateCome lateCome) {
        this.lateCome = lateCome;
    }
    public LateCome getLateCome() {
        return this.lateCome;
    }

    /*��ת�����LateCome��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*���LateCome��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNo(lateCome.getStudentObj().getStudentNo());
            lateCome.setStudentObj(studentObj);
            lateComeDAO.AddLateCome(lateCome);
            ctx.put("message",  java.net.URLEncoder.encode("LateCome��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateCome���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLateCome��Ϣ*/
    public String QueryLateCome() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj, reason, lateComeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        lateComeDAO.CalculateTotalPageAndRecordNumber(studentObj, reason, lateComeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = lateComeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryLateComeOutputToExcel() { 
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj,reason,lateComeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LateCome��Ϣ��¼"; 
        String[] headers = { "����¼id","���ѧ��","���ԭ��","���ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LateCome.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯLateCome��Ϣ*/
    public String FrontQueryLateCome() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(lateComeTime == null) lateComeTime = "";
        List<LateCome> lateComeList = lateComeDAO.QueryLateComeInfo(studentObj, reason, lateComeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        lateComeDAO.CalculateTotalPageAndRecordNumber(studentObj, reason, lateComeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = lateComeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�LateCome��Ϣ*/
    public String ModifyLateComeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������lateComeId��ȡLateCome����*/
        LateCome lateCome = lateComeDAO.GetLateComeByLateComeId(lateComeId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("lateCome",  lateCome);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LateCome��Ϣ*/
    public String FrontShowLateComeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������lateComeId��ȡLateCome����*/
        LateCome lateCome = lateComeDAO.GetLateComeByLateComeId(lateComeId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("lateCome",  lateCome);
        return "front_show_view";
    }

    /*�����޸�LateCome��Ϣ*/
    public String ModifyLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNo(lateCome.getStudentObj().getStudentNo());
            lateCome.setStudentObj(studentObj);
            lateComeDAO.UpdateLateCome(lateCome);
            ctx.put("message",  java.net.URLEncoder.encode("LateCome��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateCome��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LateCome��Ϣ*/
    public String DeleteLateCome() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            lateComeDAO.DeleteLateCome(lateComeId);
            ctx.put("message",  java.net.URLEncoder.encode("LateComeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LateComeɾ��ʧ��!"));
            return "error";
        }
    }

}
