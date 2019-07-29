package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Student;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;

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
public class StudentQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明学号输入框
	private EditText ET_studentNo;
	// 声明所在班级输入框
	private EditText ET_className;
	// 声明姓名输入框
	private EditText ET_studentName;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明所在房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null; 
	/*房间信息管理业务逻辑层*/
	private RoomService roomService = new RoomService();
	/*查询过滤条件保存到这个对象中*/
	private Student queryConditionStudent = new Student();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.student_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置学生信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_studentNo = (EditText) findViewById(R.id.ET_studentNo);
		ET_className = (EditText) findViewById(R.id.ET_className);
		ET_studentName = (EditText) findViewById(R.id.ET_studentName);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的房间信息
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount+1];
		roomObj_ShowText[0] = "不限制";
		for(int i=1;i<=roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i-1).getRoomName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置所在房间下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStudent.setRoomObj(roomList.get(arg2-1).getRoomId()); 
				else
					queryConditionStudent.setRoomObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionStudent.setStudentNo(ET_studentNo.getText().toString());
					queryConditionStudent.setClassName(ET_className.getText().toString());
					queryConditionStudent.setStudentName(ET_studentName.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionStudent.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionStudent.setBirthday(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStudent", queryConditionStudent);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
