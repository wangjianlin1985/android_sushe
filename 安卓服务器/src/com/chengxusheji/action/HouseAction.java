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

    private int houseId;
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
    public int getHouseId() {
        return houseId;
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
    @Resource HouseDAO houseDAO;

    /*��������House����*/
    private House house;
    public void setHouse(House house) {
        this.house = house;
    }
    public House getHouse() {
        return this.house;
    }

    /*��ת�����House��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���House��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHouse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            houseDAO.AddHouse(house);
            ctx.put("message",  java.net.URLEncoder.encode("House��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("House���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHouse��Ϣ*/
    public String QueryHouse() {
        if(currentPage == 0) currentPage = 1;
        List<House> houseList = houseDAO.QueryHouseInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        houseDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = houseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = houseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("houseList",  houseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHouseOutputToExcel() { 
        List<House> houseList = houseDAO.QueryHouseInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "House��Ϣ��¼"; 
        String[] headers = { "����id","����¥����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"House.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHouse��Ϣ*/
    public String FrontQueryHouse() {
        if(currentPage == 0) currentPage = 1;
        List<House> houseList = houseDAO.QueryHouseInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        houseDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = houseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = houseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("houseList",  houseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�House��Ϣ*/
    public String ModifyHouseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������houseId��ȡHouse����*/
        House house = houseDAO.GetHouseByHouseId(houseId);

        ctx.put("house",  house);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�House��Ϣ*/
    public String FrontShowHouseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������houseId��ȡHouse����*/
        House house = houseDAO.GetHouseByHouseId(houseId);

        ctx.put("house",  house);
        return "front_show_view";
    }

    /*�����޸�House��Ϣ*/
    public String ModifyHouse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            houseDAO.UpdateHouse(house);
            ctx.put("message",  java.net.URLEncoder.encode("House��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("House��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��House��Ϣ*/
    public String DeleteHouse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            houseDAO.DeleteHouse(houseId);
            ctx.put("message",  java.net.URLEncoder.encode("Houseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Houseɾ��ʧ��!"));
            return "error";
        }
    }

}
