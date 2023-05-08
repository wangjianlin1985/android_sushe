package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.House;
import com.mobileclient.util.HttpUtil;

/*宿舍楼管理业务逻辑层*/
public class HouseService {
	/* 添加宿舍楼 */
	public String AddHouse(House house) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("houseId", house.getHouseId() + "");
		params.put("houseName", house.getHouseName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HouseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询宿舍楼 */
	public List<House> QueryHouse(House queryConditionHouse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HouseServlet?action=query";
		if(queryConditionHouse != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HouseListHandler houseListHander = new HouseListHandler();
		xr.setContentHandler(houseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<House> houseList = houseListHander.getHouseList();
		return houseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<House> houseList = new ArrayList<House>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				House house = new House();
				house.setHouseId(object.getInt("houseId"));
				house.setHouseName(object.getString("houseName"));
				houseList.add(house);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return houseList;
	}

	/* 更新宿舍楼 */
	public String UpdateHouse(House house) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("houseId", house.getHouseId() + "");
		params.put("houseName", house.getHouseName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HouseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除宿舍楼 */
	public String DeleteHouse(int houseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("houseId", houseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HouseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "宿舍楼信息删除失败!";
		}
	}

	/* 根据宿舍id获取宿舍楼对象 */
	public House GetHouse(int houseId)  {
		List<House> houseList = new ArrayList<House>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("houseId", houseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HouseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				House house = new House();
				house.setHouseId(object.getInt("houseId"));
				house.setHouseName(object.getString("houseName"));
				houseList.add(house);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = houseList.size();
		if(size>0) return houseList.get(0); 
		else return null; 
	}
}
