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
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.LateCome;

@Service @Transactional
public class LateComeDAO {

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
    public void AddLateCome(LateCome lateCome) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(lateCome);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LateCome> QueryLateComeInfo(Student studentObj,String reason,String lateComeTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LateCome lateCome where 1=1";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and lateCome.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(!reason.equals("")) hql = hql + " and lateCome.reason like '%" + reason + "%'";
    	if(!lateComeTime.equals("")) hql = hql + " and lateCome.lateComeTime like '%" + lateComeTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List lateComeList = q.list();
    	return (ArrayList<LateCome>) lateComeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LateCome> QueryLateComeInfo(Student studentObj,String reason,String lateComeTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LateCome lateCome where 1=1";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and lateCome.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(!reason.equals("")) hql = hql + " and lateCome.reason like '%" + reason + "%'";
    	if(!lateComeTime.equals("")) hql = hql + " and lateCome.lateComeTime like '%" + lateComeTime + "%'";
    	Query q = s.createQuery(hql);
    	List lateComeList = q.list();
    	return (ArrayList<LateCome>) lateComeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LateCome> QueryAllLateComeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LateCome";
        Query q = s.createQuery(hql);
        List lateComeList = q.list();
        return (ArrayList<LateCome>) lateComeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentObj,String reason,String lateComeTime) {
        Session s = factory.getCurrentSession();
        String hql = "From LateCome lateCome where 1=1";
        if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and lateCome.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
        if(!reason.equals("")) hql = hql + " and lateCome.reason like '%" + reason + "%'";
        if(!lateComeTime.equals("")) hql = hql + " and lateCome.lateComeTime like '%" + lateComeTime + "%'";
        Query q = s.createQuery(hql);
        List lateComeList = q.list();
        recordNumber = lateComeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LateCome GetLateComeByLateComeId(int lateComeId) {
        Session s = factory.getCurrentSession();
        LateCome lateCome = (LateCome)s.get(LateCome.class, lateComeId);
        return lateCome;
    }

    /*更新LateCome信息*/
    public void UpdateLateCome(LateCome lateCome) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(lateCome);
    }

    /*删除LateCome信息*/
    public void DeleteLateCome (int lateComeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object lateCome = s.load(LateCome.class, lateComeId);
        s.delete(lateCome);
    }

}
