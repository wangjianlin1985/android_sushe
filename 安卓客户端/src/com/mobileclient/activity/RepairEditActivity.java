package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Repair;
import com.mobileclient.service.RepairService;
import com.mobileclient.domain.RepairClass;
import com.mobileclient.service.RepairClassService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.RepairState;
import com.mobileclient.service.RepairStateService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class RepairEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明报修idTextView
	private TextView TV_repairId;
	// 声明报修类别下拉框
	private Spinner spinner_repaiClassObj;
	private ArrayAdapter<String> repaiClassObj_adapter;
	private static  String[] repaiClassObj_ShowText  = null;
	private List<RepairClass> repairClassList = null;
	/*报修类别管理业务逻辑层*/
	private RepairClassService repairClassService = new RepairClassService();
	// 声明故障简述输入框
	private EditText ET_repaitTitle;
	// 声明故障详述输入框
	private EditText ET_repairContent;
	// 声明上报学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*上报学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明处理结果输入框
	private EditText ET_handleResult;
	// 声明维修状态下拉框
	private Spinner spinner_repairStateObj;
	private ArrayAdapter<String> repairStateObj_adapter;
	private static  String[] repairStateObj_ShowText  = null;
	private List<RepairState> repairStateList = null;
	/*维修状态管理业务逻辑层*/
	private RepairStateService repairStateService = new RepairStateService();
	protected String carmera_path;
	/*要保存的报修信息信息*/
	Repair repair = new Repair();
	/*报修信息管理业务逻辑层*/
	private RepairService repairService = new RepairService();

	private int repairId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.repair_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑报修信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_repairId = (TextView) findViewById(R.id.TV_repairId);
		spinner_repaiClassObj = (Spinner) findViewById(R.id.Spinner_repaiClassObj);
		// 获取所有的报修类别
		try {
			repairClassList = repairClassService.QueryRepairClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int repairClassCount = repairClassList.size();
		repaiClassObj_ShowText = new String[repairClassCount];
		for(int i=0;i<repairClassCount;i++) { 
			repaiClassObj_ShowText[i] = repairClassList.get(i).getRepairClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		repaiClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, repaiClassObj_ShowText);
		// 设置图书类别下拉列表的风格
		repaiClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_repaiClassObj.setAdapter(repaiClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_repaiClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				repair.setRepaiClassObj(repairClassList.get(arg2).getRepairClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_repaiClassObj.setVisibility(View.VISIBLE);
		ET_repaitTitle = (EditText) findViewById(R.id.ET_repaitTitle);
		ET_repairContent = (EditText) findViewById(R.id.ET_repairContent);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的上报学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i).getStudentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置图书类别下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				repair.setStudentObj(studentList.get(arg2).getStudentNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		ET_handleResult = (EditText) findViewById(R.id.ET_handleResult);
		spinner_repairStateObj = (Spinner) findViewById(R.id.Spinner_repairStateObj);
		// 获取所有的维修状态
		try {
			repairStateList = repairStateService.QueryRepairState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int repairStateCount = repairStateList.size();
		repairStateObj_ShowText = new String[repairStateCount];
		for(int i=0;i<repairStateCount;i++) { 
			repairStateObj_ShowText[i] = repairStateList.get(i).getRepairStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		repairStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, repairStateObj_ShowText);
		// 设置图书类别下拉列表的风格
		repairStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_repairStateObj.setAdapter(repairStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_repairStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				repair.setRepairStateObj(repairStateList.get(arg2).getRepairStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_repairStateObj.setVisibility(View.VISIBLE);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		repairId = extras.getInt("repairId");
		/*单击修改报修信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取故障简述*/ 
					if(ET_repaitTitle.getText().toString().equals("")) {
						Toast.makeText(RepairEditActivity.this, "故障简述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_repaitTitle.setFocusable(true);
						ET_repaitTitle.requestFocus();
						return;	
					}
					repair.setRepaitTitle(ET_repaitTitle.getText().toString());
					/*验证获取故障详述*/ 
					if(ET_repairContent.getText().toString().equals("")) {
						Toast.makeText(RepairEditActivity.this, "故障详述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_repairContent.setFocusable(true);
						ET_repairContent.requestFocus();
						return;	
					}
					repair.setRepairContent(ET_repairContent.getText().toString());
					/*验证获取处理结果*/ 
					if(ET_handleResult.getText().toString().equals("")) {
						Toast.makeText(RepairEditActivity.this, "处理结果输入不能为空!", Toast.LENGTH_LONG).show();
						ET_handleResult.setFocusable(true);
						ET_handleResult.requestFocus();
						return;	
					}
					repair.setHandleResult(ET_handleResult.getText().toString());
					/*调用业务逻辑层上传报修信息信息*/
					RepairEditActivity.this.setTitle("正在更新报修信息信息，稍等...");
					String result = repairService.UpdateRepair(repair);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    repair = repairService.GetRepair(repairId);
		this.TV_repairId.setText(repairId+"");
		for (int i = 0; i < repairClassList.size(); i++) {
			if (repair.getRepaiClassObj() == repairClassList.get(i).getRepairClassId()) {
				this.spinner_repaiClassObj.setSelection(i);
				break;
			}
		}
		this.ET_repaitTitle.setText(repair.getRepaitTitle());
		this.ET_repairContent.setText(repair.getRepairContent());
		for (int i = 0; i < studentList.size(); i++) {
			if (repair.getStudentObj().equals(studentList.get(i).getStudentNo())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		this.ET_handleResult.setText(repair.getHandleResult());
		for (int i = 0; i < repairStateList.size(); i++) {
			if (repair.getRepairStateObj() == repairStateList.get(i).getRepairStateId()) {
				this.spinner_repairStateObj.setSelection(i);
				break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
