package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Notice;
import com.mobileclient.util.HttpUtil;

/*公告信息管理业务逻辑层*/
public class NoticeService {
	/* 添加公告信息 */
	public String AddNotice(Notice notice) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", notice.getNoticeId() + "");
		params.put("noticeTitle", notice.getNoticeTitle());
		params.put("noticeContent", notice.getNoticeContent());
		params.put("noticeTime", notice.getNoticeTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询公告信息 */
	public List<Notice> QueryNotice(Notice queryConditionNotice) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NoticeServlet?action=query";
		if(queryConditionNotice != null) {
			urlString += "&noticeTitle=" + URLEncoder.encode(queryConditionNotice.getNoticeTitle(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NoticeListHandler noticeListHander = new NoticeListHandler();
		xr.setContentHandler(noticeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Notice> noticeList = noticeListHander.getNoticeList();
		return noticeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Notice> noticeList = new ArrayList<Notice>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Notice notice = new Notice();
				notice.setNoticeId(object.getInt("noticeId"));
				notice.setNoticeTitle(object.getString("noticeTitle"));
				notice.setNoticeContent(object.getString("noticeContent"));
				notice.setNoticeTime(object.getString("noticeTime"));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	/* 更新公告信息 */
	public String UpdateNotice(Notice notice) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", notice.getNoticeId() + "");
		params.put("noticeTitle", notice.getNoticeTitle());
		params.put("noticeContent", notice.getNoticeContent());
		params.put("noticeTime", notice.getNoticeTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除公告信息 */
	public String DeleteNotice(int noticeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", noticeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "公告信息信息删除失败!";
		}
	}

	/* 根据公告id获取公告信息对象 */
	public Notice GetNotice(int noticeId)  {
		List<Notice> noticeList = new ArrayList<Notice>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", noticeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Notice notice = new Notice();
				notice.setNoticeId(object.getInt("noticeId"));
				notice.setNoticeTitle(object.getString("noticeTitle"));
				notice.setNoticeContent(object.getString("noticeContent"));
				notice.setNoticeTime(object.getString("noticeTime"));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = noticeList.size();
		if(size>0) return noticeList.get(0); 
		else return null; 
	}
}
