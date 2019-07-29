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
import com.chengxusheji.domain.Room;

@Service @Transactional
public class RoomDAO {

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
    public void AddRoom(Room room) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(room);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Room> QueryRoomInfo(House houseObj,String roomName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Room room where 1=1";
    	if(null != houseObj && houseObj.getHouseId()!=0) hql += " and room.houseObj.houseId=" + houseObj.getHouseId();
    	if(!roomName.equals("")) hql = hql + " and room.roomName like '%" + roomName + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List roomList = q.list();
    	return (ArrayList<Room>) roomList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Room> QueryRoomInfo(House houseObj,String roomName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Room room where 1=1";
    	if(null != houseObj && houseObj.getHouseId()!=0) hql += " and room.houseObj.houseId=" + houseObj.getHouseId();
    	if(!roomName.equals("")) hql = hql + " and room.roomName like '%" + roomName + "%'";
    	Query q = s.createQuery(hql);
    	List roomList = q.list();
    	return (ArrayList<Room>) roomList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Room> QueryAllRoomInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Room";
        Query q = s.createQuery(hql);
        List roomList = q.list();
        return (ArrayList<Room>) roomList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(House houseObj,String roomName) {
        Session s = factory.getCurrentSession();
        String hql = "From Room room where 1=1";
        if(null != houseObj && houseObj.getHouseId()!=0) hql += " and room.houseObj.houseId=" + houseObj.getHouseId();
        if(!roomName.equals("")) hql = hql + " and room.roomName like '%" + roomName + "%'";
        Query q = s.createQuery(hql);
        List roomList = q.list();
        recordNumber = roomList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Room GetRoomByRoomId(int roomId) {
        Session s = factory.getCurrentSession();
        Room room = (Room)s.get(Room.class, roomId);
        return room;
    }

    /*更新Room信息*/
    public void UpdateRoom(Room room) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(room);
    }

    /*删除Room信息*/
    public void DeleteRoom (int roomId) throws Exception {
        Session s = factory.getCurrentSession();
        Object room = s.load(Room.class, roomId);
        s.delete(room);
    }

}
