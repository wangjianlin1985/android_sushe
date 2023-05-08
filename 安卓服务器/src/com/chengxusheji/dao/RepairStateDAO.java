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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddRepairState(RepairState repairState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(repairState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairState> QueryRepairStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairState repairState where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RepairState GetRepairStateByRepairStateId(int repairStateId) {
        Session s = factory.getCurrentSession();
        RepairState repairState = (RepairState)s.get(RepairState.class, repairStateId);
        return repairState;
    }

    /*����RepairState��Ϣ*/
    public void UpdateRepairState(RepairState repairState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(repairState);
    }

    /*ɾ��RepairState��Ϣ*/
    public void DeleteRepairState (int repairStateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object repairState = s.load(RepairState.class, repairStateId);
        s.delete(repairState);
    }

}
