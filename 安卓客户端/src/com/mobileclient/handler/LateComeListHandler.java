package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LateCome;
public class LateComeListHandler extends DefaultHandler {
	private List<LateCome> lateComeList = null;
	private LateCome lateCome;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (lateCome != null) { 
            String valueString = new String(ch, start, length); 
            if ("lateComeId".equals(tempString)) 
            	lateCome.setLateComeId(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	lateCome.setStudentObj(valueString); 
            else if ("reason".equals(tempString)) 
            	lateCome.setReason(valueString); 
            else if ("lateComeTime".equals(tempString)) 
            	lateCome.setLateComeTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LateCome".equals(localName)&&lateCome!=null){
			lateComeList.add(lateCome);
			lateCome = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		lateComeList = new ArrayList<LateCome>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LateCome".equals(localName)) {
            lateCome = new LateCome(); 
        }
        tempString = localName; 
	}

	public List<LateCome> getLateComeList() {
		return this.lateComeList;
	}
}
