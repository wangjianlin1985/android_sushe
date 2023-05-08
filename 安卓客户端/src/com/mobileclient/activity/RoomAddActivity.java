package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.House;
import com.mobileclient.service.HouseService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class RoomAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所在宿舍下拉框
	private Spinner spinner_houseObj;
	private ArrayAdapter<String> houseObj_adapter;
	private static  String[] houseObj_ShowText  = null;
	private List<House> houseList = null;
	/*所在宿舍管理业务逻辑层*/
	private HouseService houseService = new HouseService();
	// 声明房间名称输入框
	private EditText ET_roomName;
	// 声明床位数输入框
	private EditText ET_bedNum;
	protected String carmera_path;
	/*要保存的房间信息信息*/
	Room room = new Room();
	/*房间信息管理业务逻辑层*/
	private RoomService roomService = new RoomService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.room_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加房间信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_houseObj = (Spinner) findViewById(R.id.Spinner_houseObj);
		// 获取所有的所在宿舍
		try {
			houseList = houseService.QueryHouse(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int houseCount = houseList.size();
		houseObj_ShowText = new String[houseCount];
		for(int i=0;i<houseCount;i++) { 
			houseObj_ShowText[i] = houseList.get(i).getHouseName();
		}
		// 将可选内容与ArrayAdapter连接起来
		houseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, houseObj_ShowText);
		// 设置下拉列表的风格
		houseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_houseObj.setAdapter(houseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_houseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				room.setHouseObj(houseList.get(arg2).getHouseId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_houseObj.setVisibility(View.VISIBLE);
		ET_roomName = (EditText) findViewById(R.id.ET_roomName);
		ET_bedNum = (EditText) findViewById(R.id.ET_bedNum);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加房间信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取房间名称*/ 
					if(ET_roomName.getText().toString().equals("")) {
						Toast.makeText(RoomAddActivity.this, "房间名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomName.setFocusable(true);
						ET_roomName.requestFocus();
						return;	
					}
					room.setRoomName(ET_roomName.getText().toString());
					/*验证获取床位数*/ 
					if(ET_bedNum.getText().toString().equals("")) {
						Toast.makeText(RoomAddActivity.this, "床位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_bedNum.setFocusable(true);
						ET_bedNum.requestFocus();
						return;	
					}
					room.setBedNum(Integer.parseInt(ET_bedNum.getText().toString()));
					/*调用业务逻辑层上传房间信息信息*/
					RoomAddActivity.this.setTitle("正在上传房间信息信息，稍等...");
					String result = roomService.AddRoom(room);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
