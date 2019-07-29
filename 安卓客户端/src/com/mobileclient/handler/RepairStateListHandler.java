package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.RepairState;
public class RepairStateListHandler extends DefaultHandler {
	private List<RepairState> repairStateList = null;
	private RepairState repairState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (repairState != null) { 
            String valueString = new String(ch, start, length); 
            if ("repairStateId".equals(tempString)) 
            	repairState.setRepairStateId(new Integer(valueString).intValue());
            else if ("repairStateName".equals(tempString)) 
            	repairState.setRepairStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("RepairState".equals(localName)&&repairState!=null){
			repairStateList.add(repairState);
			repairState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		repairStateList = new ArrayList<RepairState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("RepairState".equals(localName)) {
            repairState = new RepairState(); 
        }
        tempString = localName; 
	}

	public List<RepairState> getRepairStateList() {
		return this.repairStateList;
	}
}
