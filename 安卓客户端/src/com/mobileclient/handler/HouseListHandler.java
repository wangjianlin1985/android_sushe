package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.House;
public class HouseListHandler extends DefaultHandler {
	private List<House> houseList = null;
	private House house;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (house != null) { 
            String valueString = new String(ch, start, length); 
            if ("houseId".equals(tempString)) 
            	house.setHouseId(new Integer(valueString).intValue());
            else if ("houseName".equals(tempString)) 
            	house.setHouseName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("House".equals(localName)&&house!=null){
			houseList.add(house);
			house = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		houseList = new ArrayList<House>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("House".equals(localName)) {
            house = new House(); 
        }
        tempString = localName; 
	}

	public List<House> getHouseList() {
		return this.houseList;
	}
}
