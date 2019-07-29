package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Repair;
import com.mobileclient.util.HttpUtil;

/*报修信息管理业务逻辑层*/
public class RepairService {
	/* 添加报修信息 */
	public String AddRepair(Repair repair) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairId", repair.getRepairId() + "");
		params.put("repaiClassObj", repair.getRepaiClassObj() + "");
		params.put("repaitTitle", repair.getRepaitTitle());
		params.put("repairContent", repair.getRepairContent());
		params.put("studentObj", repair.getStudentObj());
		params.put("handleResult", repair.getHandleResult());
		params.put("repairStateObj", repair.getRepairStateObj() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询报修信息 */
	public List<Repair> QueryRepair(Repair queryConditionRepair) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RepairServlet?action=query";
		if(queryConditionRepair != null) {
			urlString += "&repaiClassObj=" + queryConditionRepair.getRepaiClassObj();
			urlString += "&repaitTitle=" + URLEncoder.encode(queryConditionRepair.getRepaitTitle(), "UTF-8") + "";
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionRepair.getStudentObj(), "UTF-8") + "";
			urlString += "&repairStateObj=" + queryConditionRepair.getRepairStateObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RepairListHandler repairListHander = new RepairListHandler();
		xr.setContentHandler(repairListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Repair> repairList = repairListHander.getRepairList();
		return repairList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Repair> repairList = new ArrayList<Repair>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Repair repair = new Repair();
				repair.setRepairId(object.getInt("repairId"));
				repair.setRepaiClassObj(object.getInt("repaiClassObj"));
				repair.setRepaitTitle(object.getString("repaitTitle"));
				repair.setRepairContent(object.getString("repairContent"));
				repair.setStudentObj(object.getString("studentObj"));
				repair.setHandleResult(object.getString("handleResult"));
				repair.setRepairStateObj(object.getInt("repairStateObj"));
				repairList.add(repair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repairList;
	}

	/* 更新报修信息 */
	public String UpdateRepair(Repair repair) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairId", repair.getRepairId() + "");
		params.put("repaiClassObj", repair.getRepaiClassObj() + "");
		params.put("repaitTitle", repair.getRepaitTitle());
		params.put("repairContent", repair.getRepairContent());
		params.put("studentObj", repair.getStudentObj());
		params.put("handleResult", repair.getHandleResult());
		params.put("repairStateObj", repair.getRepairStateObj() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除报修信息 */
	public String DeleteRepair(int repairId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairId", repairId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "报修信息信息删除失败!";
		}
	}

	/* 根据报修id获取报修信息对象 */
	public Repair GetRepair(int repairId)  {
		List<Repair> repairList = new ArrayList<Repair>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairId", repairId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Repair repair = new Repair();
				repair.setRepairId(object.getInt("repairId"));
				repair.setRepaiClassObj(object.getInt("repaiClassObj"));
				repair.setRepaitTitle(object.getString("repaitTitle"));
				repair.setRepairContent(object.getString("repairContent"));
				repair.setStudentObj(object.getString("studentObj"));
				repair.setHandleResult(object.getString("handleResult"));
				repair.setRepairStateObj(object.getInt("repairStateObj"));
				repairList.add(repair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = repairList.size();
		if(size>0) return repairList.get(0); 
		else return null; 
	}
}
