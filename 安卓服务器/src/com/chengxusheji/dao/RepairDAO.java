package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.RepairClass;
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.RepairState;
import com.chengxusheji.domain.Repair;

@Service @Transactional
public class RepairDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddRepair(Repair repair) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(repair);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Repair> QueryRepairInfo(RepairClass repaiClassObj,String repaitTitle,Student studentObj,RepairState repairStateObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Repair repair where 1=1";
    	if(null != repaiClassObj && repaiClassObj.getRepairClassId()!=0) hql += " and repair.repaiClassObj.repairClassId=" + repaiClassObj.getRepairClassId();
    	if(!repaitTitle.equals("")) hql = hql + " and repair.repaitTitle like '%" + repaitTitle + "%'";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and repair.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(null != repairStateObj && repairStateObj.getRepairStateId()!=0) hql += " and repair.repairStateObj.repairStateId=" + repairStateObj.getRepairStateId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List repairList = q.list();
    	return (ArrayList<Repair>) repairList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Repair> QueryRepairInfo(RepairClass repaiClassObj,String repaitTitle,Student studentObj,RepairState repairStateObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Repair repair where 1=1";
    	if(null != repaiClassObj && repaiClassObj.getRepairClassId()!=0) hql += " and repair.repaiClassObj.repairClassId=" + repaiClassObj.getRepairClassId();
    	if(!repaitTitle.equals("")) hql = hql + " and repair.repaitTitle like '%" + repaitTitle + "%'";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and repair.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(null != repairStateObj && repairStateObj.getRepairStateId()!=0) hql += " and repair.repairStateObj.repairStateId=" + repairStateObj.getRepairStateId();
    	Query q = s.createQuery(hql);
    	List repairList = q.list();
    	return (ArrayList<Repair>) repairList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Repair> QueryAllRepairInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Repair";
        Query q = s.createQuery(hql);
        List repairList = q.list();
        return (ArrayList<Repair>) repairList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(RepairClass repaiClassObj,String repaitTitle,Student studentObj,RepairState repairStateObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Repair repair where 1=1";
        if(null != repaiClassObj && repaiClassObj.getRepairClassId()!=0) hql += " and repair.repaiClassObj.repairClassId=" + repaiClassObj.getRepairClassId();
        if(!repaitTitle.equals("")) hql = hql + " and repair.repaitTitle like '%" + repaitTitle + "%'";
        if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and repair.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
        if(null != repairStateObj && repairStateObj.getRepairStateId()!=0) hql += " and repair.repairStateObj.repairStateId=" + repairStateObj.getRepairStateId();
        Query q = s.createQuery(hql);
        List repairList = q.list();
        recordNumber = repairList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Repair GetRepairByRepairId(int repairId) {
        Session s = factory.getCurrentSession();
        Repair repair = (Repair)s.get(Repair.class, repairId);
        return repair;
    }

    /*更新Repair信息*/
    public void UpdateRepair(Repair repair) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(repair);
    }

    /*删除Repair信息*/
    public void DeleteRepair (int repairId) throws Exception {
        Session s = factory.getCurrentSession();
        Object repair = s.load(Repair.class, repairId);
        s.delete(repair);
    }

}
