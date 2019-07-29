package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.LateCome;
import com.mobileclient.service.LateComeService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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

public class LateComeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明晚归记录idTextView
	private TextView TV_lateComeId;
	// 声明晚归学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*晚归学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明晚归原因输入框
	private EditText ET_reason;
	// 声明晚归时间输入框
	private EditText ET_lateComeTime;
	protected String carmera_path;
	/*要保存的晚归信息信息*/
	LateCome lateCome = new LateCome();
	/*晚归信息管理业务逻辑层*/
	private LateComeService lateComeService = new LateComeService();

	private int lateComeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.latecome_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑晚归信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_lateComeId = (TextView) findViewById(R.id.TV_lateComeId);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的晚归学生
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
				lateCome.setStudentObj(studentList.get(arg2).getStudentNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		ET_reason = (EditText) findViewById(R.id.ET_reason);
		ET_lateComeTime = (EditText) findViewById(R.id.ET_lateComeTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		lateComeId = extras.getInt("lateComeId");
		/*单击修改晚归信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取晚归原因*/ 
					if(ET_reason.getText().toString().equals("")) {
						Toast.makeText(LateComeEditActivity.this, "晚归原因输入不能为空!", Toast.LENGTH_LONG).show();
						ET_reason.setFocusable(true);
						ET_reason.requestFocus();
						return;	
					}
					lateCome.setReason(ET_reason.getText().toString());
					/*验证获取晚归时间*/ 
					if(ET_lateComeTime.getText().toString().equals("")) {
						Toast.makeText(LateComeEditActivity.this, "晚归时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_lateComeTime.setFocusable(true);
						ET_lateComeTime.requestFocus();
						return;	
					}
					lateCome.setLateComeTime(ET_lateComeTime.getText().toString());
					/*调用业务逻辑层上传晚归信息信息*/
					LateComeEditActivity.this.setTitle("正在更新晚归信息信息，稍等...");
					String result = lateComeService.UpdateLateCome(lateCome);
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
	    lateCome = lateComeService.GetLateCome(lateComeId);
		this.TV_lateComeId.setText(lateComeId+"");
		for (int i = 0; i < studentList.size(); i++) {
			if (lateCome.getStudentObj().equals(studentList.get(i).getStudentNo())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		this.ET_reason.setText(lateCome.getReason());
		this.ET_lateComeTime.setText(lateCome.getLateComeTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
