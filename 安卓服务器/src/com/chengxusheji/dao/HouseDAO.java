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
import com.chengxusheji.domain.House;

@Service @Transactional
public class HouseDAO {

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
    public void AddHouse(House house) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(house);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<House> QueryHouseInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From House house where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List houseList = q.list();
    	return (ArrayList<House>) houseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<House> QueryHouseInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From House house where 1=1";
    	Query q = s.createQuery(hql);
    	List houseList = q.list();
    	return (ArrayList<House>) houseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<House> QueryAllHouseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From House";
        Query q = s.createQuery(hql);
        List houseList = q.list();
        return (ArrayList<House>) houseList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From House house where 1=1";
        Query q = s.createQuery(hql);
        List houseList = q.list();
        recordNumber = houseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public House GetHouseByHouseId(int houseId) {
        Session s = factory.getCurrentSession();
        House house = (House)s.get(House.class, houseId);
        return house;
    }

    /*����House��Ϣ*/
    public void UpdateHouse(House house) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(house);
    }

    /*ɾ��House��Ϣ*/
    public void DeleteHouse (int houseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object house = s.load(House.class, houseId);
        s.delete(house);
    }

}
