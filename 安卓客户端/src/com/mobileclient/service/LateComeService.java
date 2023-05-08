package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LateCome;
import com.mobileclient.util.HttpUtil;

/*晚归信息管理业务逻辑层*/
public class LateComeService {
	/* 添加晚归信息 */
	public String AddLateCome(LateCome lateCome) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lateComeId", lateCome.getLateComeId() + "");
		params.put("studentObj", lateCome.getStudentObj());
		params.put("reason", lateCome.getReason());
		params.put("lateComeTime", lateCome.getLateComeTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LateComeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询晚归信息 */
	public List<LateCome> QueryLateCome(LateCome queryConditionLateCome) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LateComeServlet?action=query";
		if(queryConditionLateCome != null) {
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionLateCome.getStudentObj(), "UTF-8") + "";
			urlString += "&reason=" + URLEncoder.encode(queryConditionLateCome.getReason(), "UTF-8") + "";
			urlString += "&lateComeTime=" + URLEncoder.encode(queryConditionLateCome.getLateComeTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LateComeListHandler lateComeListHander = new LateComeListHandler();
		xr.setContentHandler(lateComeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LateCome> lateComeList = lateComeListHander.getLateComeList();
		return lateComeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LateCome> lateComeList = new ArrayList<LateCome>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LateCome lateCome = new LateCome();
				lateCome.setLateComeId(object.getInt("lateComeId"));
				lateCome.setStudentObj(object.getString("studentObj"));
				lateCome.setReason(object.getString("reason"));
				lateCome.setLateComeTime(object.getString("lateComeTime"));
				lateComeList.add(lateCome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lateComeList;
	}

	/* 更新晚归信息 */
	public String UpdateLateCome(LateCome lateCome) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lateComeId", lateCome.getLateComeId() + "");
		params.put("studentObj", lateCome.getStudentObj());
		params.put("reason", lateCome.getReason());
		params.put("lateComeTime", lateCome.getLateComeTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LateComeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除晚归信息 */
	public String DeleteLateCome(int lateComeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lateComeId", lateComeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LateComeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "晚归信息信息删除失败!";
		}
	}

	/* 根据晚归记录id获取晚归信息对象 */
	public LateCome GetLateCome(int lateComeId)  {
		List<LateCome> lateComeList = new ArrayList<LateCome>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lateComeId", lateComeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LateComeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LateCome lateCome = new LateCome();
				lateCome.setLateComeId(object.getInt("lateComeId"));
				lateCome.setStudentObj(object.getString("studentObj"));
				lateCome.setReason(object.getString("reason"));
				lateCome.setLateComeTime(object.getString("lateComeTime"));
				lateComeList.add(lateCome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = lateComeList.size();
		if(size>0) return lateComeList.get(0); 
		else return null; 
	}
}
