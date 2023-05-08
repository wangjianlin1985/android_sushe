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
    public void AddRepairClass(RepairClass repairClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(repairClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RepairClass> QueryRepairClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RepairClass repairClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RepairClass GetRepairClassByRepairClassId(int repairClassId) {
        Session s = factory.getCurrentSession();
        RepairClass repairClass = (RepairClass)s.get(RepairClass.class, repairClassId);
        return repairClass;
    }

    /*����RepairClass��Ϣ*/
    public void UpdateRepairClass(RepairClass repairClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(repairClass);
    }

    /*ɾ��RepairClass��Ϣ*/
    public void DeleteRepairClass (int repairClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object repairClass = s.load(RepairClass.class, repairClassId);
        s.delete(repairClass);
    }

}
