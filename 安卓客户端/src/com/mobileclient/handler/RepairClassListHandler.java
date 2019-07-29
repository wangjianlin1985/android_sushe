package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.RepairClass;
public class RepairClassListHandler extends DefaultHandler {
	private List<RepairClass> repairClassList = null;
	private RepairClass repairClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (repairClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("repairClassId".equals(tempString)) 
            	repairClass.setRepairClassId(new Integer(valueString).intValue());
            else if ("repairClassName".equals(tempString)) 
            	repairClass.setRepairClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("RepairClass".equals(localName)&&repairClass!=null){
			repairClassList.add(repairClass);
			repairClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		repairClassList = new ArrayList<RepairClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("RepairClass".equals(localName)) {
            repairClass = new RepairClass(); 
        }
        tempString = localName; 
	}

	public List<RepairClass> getRepairClassList() {
		return this.repairClassList;
	}
}
