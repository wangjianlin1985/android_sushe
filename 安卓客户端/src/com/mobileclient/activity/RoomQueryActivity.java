package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Room;
import com.mobileclient.domain.House;
import com.mobileclient.service.HouseService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class RoomQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明所在宿舍下拉框
	private Spinner spinner_houseObj;
	private ArrayAdapter<String> houseObj_adapter;
	private static  String[] houseObj_ShowText  = null;
	private List<House> houseList = null; 
	/*宿舍楼管理业务逻辑层*/
	private HouseService houseService = new HouseService();
	// 声明房间名称输入框
	private EditText ET_roomName;
	/*查询过滤条件保存到这个对象中*/
	private Room queryConditionRoom = new Room();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.room_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置房间信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_houseObj = (Spinner) findViewById(R.id.Spinner_houseObj);
		// 获取所有的宿舍楼
		try {
			houseList = houseService.QueryHouse(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int houseCount = houseList.size();
		houseObj_ShowText = new String[houseCount+1];
		houseObj_ShowText[0] = "不限制";
		for(int i=1;i<=houseCount;i++) { 
			houseObj_ShowText[i] = houseList.get(i-1).getHouseName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		houseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, houseObj_ShowText);
		// 设置所在宿舍下拉列表的风格
		houseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_houseObj.setAdapter(houseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_houseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRoom.setHouseObj(houseList.get(arg2-1).getHouseId()); 
				else
					queryConditionRoom.setHouseObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_houseObj.setVisibility(View.VISIBLE);
		ET_roomName = (EditText) findViewById(R.id.ET_roomName);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionRoom.setRoomName(ET_roomName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionRoom", queryConditionRoom);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
