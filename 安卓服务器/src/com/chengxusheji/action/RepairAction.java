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

    /*�������Ҫ��ѯ������: �������*/
    private RepairClass repaiClassObj;
    public void setRepaiClassObj(RepairClass repaiClassObj) {
        this.repaiClassObj = repaiClassObj;
    }
    public RepairClass getRepaiClassObj() {
        return this.repaiClassObj;
    }

    /*�������Ҫ��ѯ������: ���ϼ���*/
    private String repaitTitle;
    public void setRepaitTitle(String repaitTitle) {
        this.repaitTitle = repaitTitle;
    }
    public String getRepaitTitle() {
        return this.repaitTitle;
    }

    /*�������Ҫ��ѯ������: �ϱ�ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*�������Ҫ��ѯ������: ά��״̬*/
    private RepairState repairStateObj;
    public void setRepairStateObj(RepairState repairStateObj) {
        this.repairStateObj = repairStateObj;
    }
    public RepairState getRepairStateObj() {
        return this.repairStateObj;
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

    private int repairId;
    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }
    public int getRepairId() {
        return repairId;
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
    @Resource RepairClassDAO repairClassDAO;
    @Resource StudentDAO studentDAO;
    @Resource RepairStateDAO repairStateDAO;
    @Resource RepairDAO repairDAO;

    /*��������Repair����*/
    private Repair repair;
    public void setRepair(Repair repair) {
        this.repair = repair;
    }
    public Repair getRepair() {
        return this.repair;
    }

    /*��ת�����Repair��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�RepairClass��Ϣ*/
        List<RepairClass> repairClassList = repairClassDAO.QueryAllRepairClassInfo();
        ctx.put("repairClassList", repairClassList);
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�RepairState��Ϣ*/
        List<RepairState> repairStateList = repairStateDAO.QueryAllRepairStateInfo();
        ctx.put("repairStateList", repairStateList);
        return "add_view";
    }

    /*���Repair��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Repair��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repair���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRepair��Ϣ*/
    public String QueryRepair() {
        if(currentPage == 0) currentPage = 1;
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj, repaitTitle, studentObj, repairStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairDAO.CalculateTotalPageAndRecordNumber(repaiClassObj, repaitTitle, studentObj, repairStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryRepairOutputToExcel() { 
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj,repaitTitle,studentObj,repairStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Repair��Ϣ��¼"; 
        String[] headers = { "����id","�������","���ϼ���","�ϱ�ѧ��","ά��״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Repair.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRepair��Ϣ*/
    public String FrontQueryRepair() {
        if(currentPage == 0) currentPage = 1;
        if(repaitTitle == null) repaitTitle = "";
        List<Repair> repairList = repairDAO.QueryRepairInfo(repaiClassObj, repaitTitle, studentObj, repairStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairDAO.CalculateTotalPageAndRecordNumber(repaiClassObj, repaitTitle, studentObj, repairStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Repair��Ϣ*/
    public String ModifyRepairQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairId��ȡRepair����*/
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

    /*��ѯҪ�޸ĵ�Repair��Ϣ*/
    public String FrontShowRepairQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairId��ȡRepair����*/
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

    /*�����޸�Repair��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Repair��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repair��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Repair��Ϣ*/
    public String DeleteRepair() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            repairDAO.DeleteRepair(repairId);
            ctx.put("message",  java.net.URLEncoder.encode("Repairɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Repairɾ��ʧ��!"));
            return "error";
        }
    }

}
