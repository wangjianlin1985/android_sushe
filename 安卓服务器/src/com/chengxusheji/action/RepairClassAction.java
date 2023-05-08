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
import com.chengxusheji.dao.RepairClassDAO;
import com.chengxusheji.domain.RepairClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RepairClassAction extends BaseAction {

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

    private int repairClassId;
    public void setRepairClassId(int repairClassId) {
        this.repairClassId = repairClassId;
    }
    public int getRepairClassId() {
        return repairClassId;
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

    /*��������RepairClass����*/
    private RepairClass repairClass;
    public void setRepairClass(RepairClass repairClass) {
        this.repairClass = repairClass;
    }
    public RepairClass getRepairClass() {
        return this.repairClass;
    }

    /*��ת�����RepairClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���RepairClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRepairClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairClassDAO.AddRepairClass(repairClass);
            ctx.put("message",  java.net.URLEncoder.encode("RepairClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRepairClass��Ϣ*/
    public String QueryRepairClass() {
        if(currentPage == 0) currentPage = 1;
        List<RepairClass> repairClassList = repairClassDAO.QueryRepairClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = repairClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairClassList",  repairClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRepairClassOutputToExcel() { 
        List<RepairClass> repairClassList = repairClassDAO.QueryRepairClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RepairClass��Ϣ��¼"; 
        String[] headers = { "ά�����id","ά���������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairClassList.size();i++) {
        	RepairClass repairClass = repairClassList.get(i); 
        	dataset.add(new String[]{repairClass.getRepairClassId() + "",repairClass.getRepairClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RepairClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRepairClass��Ϣ*/
    public String FrontQueryRepairClass() {
        if(currentPage == 0) currentPage = 1;
        List<RepairClass> repairClassList = repairClassDAO.QueryRepairClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = repairClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairClassList",  repairClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�RepairClass��Ϣ*/
    public String ModifyRepairClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairClassId��ȡRepairClass����*/
        RepairClass repairClass = repairClassDAO.GetRepairClassByRepairClassId(repairClassId);

        ctx.put("repairClass",  repairClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�RepairClass��Ϣ*/
    public String FrontShowRepairClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairClassId��ȡRepairClass����*/
        RepairClass repairClass = repairClassDAO.GetRepairClassByRepairClassId(repairClassId);

        ctx.put("repairClass",  repairClass);
        return "front_show_view";
    }

    /*�����޸�RepairClass��Ϣ*/
    public String ModifyRepairClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairClassDAO.UpdateRepairClass(repairClass);
            ctx.put("message",  java.net.URLEncoder.encode("RepairClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��RepairClass��Ϣ*/
    public String DeleteRepairClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            repairClassDAO.DeleteRepairClass(repairClassId);
            ctx.put("message",  java.net.URLEncoder.encode("RepairClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairClassɾ��ʧ��!"));
            return "error";
        }
    }

}
