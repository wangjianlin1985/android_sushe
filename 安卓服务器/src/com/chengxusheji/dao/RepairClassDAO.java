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

@Service @Transactional
public class RepairClassDAO {

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
    public void AddRepairClass(RepairClass repairClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(repairClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairClass> QueryRepairClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairClass repairClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List repairClassList = q.list();
    	return (ArrayList<RepairClass>) repairClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairClass> QueryRepairClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairClass repairClass where 1=1";
    	Query q = s.createQuery(hql);
    	List repairClassList = q.list();
    	return (ArrayList<RepairClass>) repairClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairClass> QueryAllRepairClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From RepairClass";
        Query q = s.createQuery(hql);
        List repairClassList = q.list();
        return (ArrayList<RepairClass>) repairClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From RepairClass repairClass where 1=1";
        Query q = s.createQuery(hql);
        List repairClassList = q.list();
        recordNumber = repairClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RepairClass GetRepairClassByRepairClassId(int repairClassId) {
        Session s = factory.getCurrentSession();
        RepairClass repairClass = (RepairClass)s.get(RepairClass.class, repairClassId);
        return repairClass;
    }

    /*更新RepairClass信息*/
    public void UpdateRepairClass(RepairClass repairClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(repairClass);
    }

    /*删除RepairClass信息*/
    public void DeleteRepairClass (int repairClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object repairClass = s.load(RepairClass.class, repairClassId);
        s.delete(repairClass);
    }

}
