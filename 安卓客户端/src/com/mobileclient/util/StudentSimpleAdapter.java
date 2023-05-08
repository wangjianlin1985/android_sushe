package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.RoomService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class StudentSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public StudentSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.student_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_studentNo = (TextView)convertView.findViewById(R.id.tv_studentNo);
	  holder.tv_className = (TextView)convertView.findViewById(R.id.tv_className);
	  holder.tv_studentName = (TextView)convertView.findViewById(R.id.tv_studentName);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_birthday = (TextView)convertView.findViewById(R.id.tv_birthday);
	  holder.iv_studentPhoto = (ImageView)convertView.findViewById(R.id.iv_studentPhoto);
	  holder.tv_lxfs = (TextView)convertView.findViewById(R.id.tv_lxfs);
	  holder.tv_roomObj = (TextView)convertView.findViewById(R.id.tv_roomObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_studentNo.setText("学号：" + mData.get(position).get("studentNo").toString());
	  holder.tv_className.setText("所在班级：" + mData.get(position).get("className").toString());
	  holder.tv_studentName.setText("姓名：" + mData.get(position).get("studentName").toString());
	  holder.tv_sex.setText("性别：" + mData.get(position).get("sex").toString());
	  try {holder.tv_birthday.setText("出生日期：" + mData.get(position).get("birthday").toString().substring(0, 10));} catch(Exception ex){}
	  holder.iv_studentPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener studentPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_studentPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("studentPhoto"),studentPhotoLoadListener);  
	  holder.tv_lxfs.setText("联系方式：" + mData.get(position).get("lxfs").toString());
	  holder.tv_roomObj.setText("所在房间：" + (new RoomService()).GetRoom(Integer.parseInt(mData.get(position).get("roomObj").toString())).getRoomName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_studentNo;
    	TextView tv_className;
    	TextView tv_studentName;
    	TextView tv_sex;
    	TextView tv_birthday;
    	ImageView iv_studentPhoto;
    	TextView tv_lxfs;
    	TextView tv_roomObj;
    }
} 
