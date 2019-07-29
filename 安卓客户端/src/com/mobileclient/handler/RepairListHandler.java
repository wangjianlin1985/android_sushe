package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Repair;
public class RepairListHandler extends DefaultHandler {
	private List<Repair> repairList = null;
	private Repair repair;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (repair != null) { 
            String valueString = new String(ch, start, length); 
            if ("repairId".equals(tempString)) 
            	repair.setRepairId(new Integer(valueString).intValue());
            else if ("repaiClassObj".equals(tempString)) 
            	repair.setRepaiClassObj(new Integer(valueString).intValue());
            else if ("repaitTitle".equals(tempString)) 
            	repair.setRepaitTitle(valueString); 
            else if ("repairContent".equals(tempString)) 
            	repair.setRepairContent(valueString); 
            else if ("studentObj".equals(tempString)) 
            	repair.setStudentObj(valueString); 
            else if ("handleResult".equals(tempString)) 
            	repair.setHandleResult(valueString); 
            else if ("repairStateObj".equals(tempString)) 
            	repair.setRepairStateObj(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Repair".equals(localName)&&repair!=null){
			repairList.add(repair);
			repair = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		repairList = new ArrayList<Repair>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Repair".equals(localName)) {
            repair = new Repair(); 
        }
        tempString = localName; 
	}

	public List<Repair> getRepairList() {
		return this.repairList;
	}
}
