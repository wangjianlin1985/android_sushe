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
import com.chengxusheji.domain.RepairState;

@Service @Transactional
public class RepairStateDAO {

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
    public void AddRepairState(RepairState repairState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(repairState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairState> QueryRepairStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairState repairState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List repairStateList = q.list();
    	return (ArrayList<RepairState>) repairStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairState> QueryRepairStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairState repairState where 1=1";
    	Query q = s.createQuery(hql);
    	List repairStateList = q.list();
    	return (ArrayList<RepairState>) repairStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairState> QueryAllRepairStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From RepairState";
        Query q = s.createQuery(hql);
        List repairStateList = q.list();
        return (ArrayList<RepairState>) repairStateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From RepairState repairState where 1=1";
        Query q = s.createQuery(hql);
        List repairStateList = q.list();
        recordNumber = repairStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RepairState GetRepairStateByRepairStateId(int repairStateId) {
        Session s = factory.getCurrentSession();
        RepairState repairState = (RepairState)s.get(RepairState.class, repairStateId);
        return repairState;
    }

    /*更新RepairState信息*/
    public void UpdateRepairState(RepairState repairState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(repairState);
    }

    /*删除RepairState信息*/
    public void DeleteRepairState (int repairStateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object repairState = s.load(RepairState.class, repairStateId);
        s.delete(repairState);
    }

}
