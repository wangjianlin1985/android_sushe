package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.RepairState;
import com.mobileclient.util.HttpUtil;

/*维修状态管理业务逻辑层*/
public class RepairStateService {
	/* 添加维修状态 */
	public String AddRepairState(RepairState repairState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairStateId", repairState.getRepairStateId() + "");
		params.put("repairStateName", repairState.getRepairStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询维修状态 */
	public List<RepairState> QueryRepairState(RepairState queryConditionRepairState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RepairStateServlet?action=query";
		if(queryConditionRepairState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RepairStateListHandler repairStateListHander = new RepairStateListHandler();
		xr.setContentHandler(repairStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<RepairState> repairStateList = repairStateListHander.getRepairStateList();
		return repairStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<RepairState> repairStateList = new ArrayList<RepairState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RepairState repairState = new RepairState();
				repairState.setRepairStateId(object.getInt("repairStateId"));
				repairState.setRepairStateName(object.getString("repairStateName"));
				repairStateList.add(repairState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repairStateList;
	}

	/* 更新维修状态 */
	public String UpdateRepairState(RepairState repairState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairStateId", repairState.getRepairStateId() + "");
		params.put("repairStateName", repairState.getRepairStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除维修状态 */
	public String DeleteRepairState(int repairStateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairStateId", repairStateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "维修状态信息删除失败!";
		}
	}

	/* 根据状态id获取维修状态对象 */
	public RepairState GetRepairState(int repairStateId)  {
		List<RepairState> repairStateList = new ArrayList<RepairState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("repairStateId", repairStateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RepairStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RepairState repairState = new RepairState();
				repairState.setRepairStateId(object.getInt("repairStateId"));
				repairState.setRepairStateName(object.getString("repairStateName"));
				repairStateList.add(repairState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = repairStateList.size();
		if(size>0) return repairStateList.get(0); 
		else return null; 
	}
}
