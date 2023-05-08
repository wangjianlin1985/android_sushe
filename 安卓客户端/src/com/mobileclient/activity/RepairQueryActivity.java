package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Repair;
import com.mobileclient.domain.RepairClass;
import com.mobileclient.service.RepairClassService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.RepairState;
import com.mobileclient.service.RepairStateService;

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
public class RepairQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明报修类别下拉框
	private Spinner spinner_repaiClassObj;
	private ArrayAdapter<String> repaiClassObj_adapter;
	private static  String[] repaiClassObj_ShowText  = null;
	private List<RepairClass> repairClassList = null; 
	/*报修类别管理业务逻辑层*/
	private RepairClassService repairClassService = new RepairClassService();
	// 声明故障简述输入框
	private EditText ET_repaitTitle;
	// 声明上报学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null; 
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明维修状态下拉框
	private Spinner spinner_repairStateObj;
	private ArrayAdapter<String> repairStateObj_adapter;
	private static  String[] repairStateObj_ShowText  = null;
	private List<RepairState> repairStateList = null; 
	/*维修状态管理业务逻辑层*/
	private RepairStateService repairStateService = new RepairStateService();
	/*查询过滤条件保存到这个对象中*/
	private Repair queryConditionRepair = new Repair();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.repair_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置报修信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_repaiClassObj = (Spinner) findViewById(R.id.Spinner_repaiClassObj);
		// 获取所有的报修类别
		try {
			repairClassList = repairClassService.QueryRepairClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int repairClassCount = repairClassList.size();
		repaiClassObj_ShowText = new String[repairClassCount+1];
		repaiClassObj_ShowText[0] = "不限制";
		for(int i=1;i<=repairClassCount;i++) { 
			repaiClassObj_ShowText[i] = repairClassList.get(i-1).getRepairClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		repaiClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, repaiClassObj_ShowText);
		// 设置报修类别下拉列表的风格
		repaiClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_repaiClassObj.setAdapter(repaiClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_repaiClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRepair.setRepaiClassObj(repairClassList.get(arg2-1).getRepairClassId()); 
				else
					queryConditionRepair.setRepaiClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_repaiClassObj.setVisibility(View.VISIBLE);
		ET_repaitTitle = (EditText) findViewById(R.id.ET_repaitTitle);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的学生信息
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount+1];
		studentObj_ShowText[0] = "不限制";
		for(int i=1;i<=studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i-1).getStudentName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置上报学生下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRepair.setStudentObj(studentList.get(arg2-1).getStudentNo()); 
				else
					queryConditionRepair.setStudentObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		spinner_repairStateObj = (Spinner) findViewById(R.id.Spinner_repairStateObj);
		// 获取所有的维修状态
		try {
			repairStateList = repairStateService.QueryRepairState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int repairStateCount = repairStateList.size();
		repairStateObj_ShowText = new String[repairStateCount+1];
		repairStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=repairStateCount;i++) { 
			repairStateObj_ShowText[i] = repairStateList.get(i-1).getRepairStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		repairStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, repairStateObj_ShowText);
		// 设置维修状态下拉列表的风格
		repairStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_repairStateObj.setAdapter(repairStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_repairStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRepair.setRepairStateObj(repairStateList.get(arg2-1).getRepairStateId()); 
				else
					queryConditionRepair.setRepairStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_repairStateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionRepair.setRepaitTitle(ET_repaitTitle.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionRepair", queryConditionRepair);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
