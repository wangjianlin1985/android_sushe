package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Room;
public class RoomListHandler extends DefaultHandler {
	private List<Room> roomList = null;
	private Room room;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (room != null) { 
            String valueString = new String(ch, start, length); 
            if ("roomId".equals(tempString)) 
            	room.setRoomId(new Integer(valueString).intValue());
            else if ("houseObj".equals(tempString)) 
            	room.setHouseObj(new Integer(valueString).intValue());
            else if ("roomName".equals(tempString)) 
            	room.setRoomName(valueString); 
            else if ("bedNum".equals(tempString)) 
            	room.setBedNum(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Room".equals(localName)&&room!=null){
			roomList.add(room);
			room = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		roomList = new ArrayList<Room>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Room".equals(localName)) {
            room = new Room(); 
        }
        tempString = localName; 
	}

	public List<Room> getRoomList() {
		return this.roomList;
	}
}
