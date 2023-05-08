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

    private int repairStateId;
    public void setRepairStateId(int repairStateId) {
        this.repairStateId = repairStateId;
    }
    public int getRepairStateId() {
        return repairStateId;
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
    @Resource RepairStateDAO repairStateDAO;

    /*��������RepairState����*/
    private RepairState repairState;
    public void setRepairState(RepairState repairState) {
        this.repairState = repairState;
    }
    public RepairState getRepairState() {
        return this.repairState;
    }

    /*��ת�����RepairState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���RepairState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairStateDAO.AddRepairState(repairState);
            ctx.put("message",  java.net.URLEncoder.encode("RepairState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRepairState��Ϣ*/
    public String QueryRepairState() {
        if(currentPage == 0) currentPage = 1;
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = repairStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairStateList",  repairStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRepairStateOutputToExcel() { 
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RepairState��Ϣ��¼"; 
        String[] headers = { "״̬id","״̬����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"RepairState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRepairState��Ϣ*/
    public String FrontQueryRepairState() {
        if(currentPage == 0) currentPage = 1;
        List<RepairState> repairStateList = repairStateDAO.QueryRepairStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        repairStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = repairStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = repairStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("repairStateList",  repairStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�RepairState��Ϣ*/
    public String ModifyRepairStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairStateId��ȡRepairState����*/
        RepairState repairState = repairStateDAO.GetRepairStateByRepairStateId(repairStateId);

        ctx.put("repairState",  repairState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�RepairState��Ϣ*/
    public String FrontShowRepairStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������repairStateId��ȡRepairState����*/
        RepairState repairState = repairStateDAO.GetRepairStateByRepairStateId(repairStateId);

        ctx.put("repairState",  repairState);
        return "front_show_view";
    }

    /*�����޸�RepairState��Ϣ*/
    public String ModifyRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            repairStateDAO.UpdateRepairState(repairState);
            ctx.put("message",  java.net.URLEncoder.encode("RepairState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��RepairState��Ϣ*/
    public String DeleteRepairState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            repairStateDAO.DeleteRepairState(repairStateId);
            ctx.put("message",  java.net.URLEncoder.encode("RepairStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RepairStateɾ��ʧ��!"));
            return "error";
        }
    }

}
