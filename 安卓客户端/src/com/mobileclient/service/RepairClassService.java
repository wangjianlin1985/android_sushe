package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.RepairClass;
import com.mobileclient.util.HttpUtil;

/*报修类别管理业务逻辑层*/
public class RepairClassService {
	/* 添加报修类别 */
	public String AddRepairClass(RepairClass repairClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairClassId", repairClass.getRepairClassId() + "");
		params.put("repairClassName", repairClass.getRepairClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询报修类别 */
	public List<RepairClass> QueryRepairClass(RepairClass queryConditionRepairClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RepairClassServlet?action=query";
		if(queryConditionRepairClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RepairClassListHandler repairClassListHander = new RepairClassListHandler();
		xr.setContentHandler(repairClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<RepairClass> repairClassList = repairClassListHander.getRepairClassList();
		return repairClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<RepairClass> repairClassList = new ArrayList<RepairClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RepairClass repairClass = new RepairClass();
				repairClass.setRepairClassId(object.getInt("repairClassId"));
				repairClass.setRepairClassName(object.getString("repairClassName"));
				repairClassList.add(repairClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repairClassList;
	}

	/* 更新报修类别 */
	public String UpdateRepairClass(RepairClass repairClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairClassId", repairClass.getRepairClassId() + "");
		params.put("repairClassName", repairClass.getRepairClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除报修类别 */
	public String DeleteRepairClass(int repairClassId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairClassId", repairClassId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "报修类别信息删除失败!";
		}
	}

	/* 根据维修类别id获取报修类别对象 */
	public RepairClass GetRepairClass(int repairClassId)  {
		List<RepairClass> repairClassList = new ArrayList<RepairClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairClassId", repairClassId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RepairClass repairClass = new RepairClass();
				repairClass.setRepairClassId(object.getInt("repairClassId"));
				repairClass.setRepairClassName(object.getString("repairClassName"));
				repairClassList.add(repairClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = repairClassList.size();
		if(size>0) return repairClassList.get(0); 
		else return null; 
	}
}
