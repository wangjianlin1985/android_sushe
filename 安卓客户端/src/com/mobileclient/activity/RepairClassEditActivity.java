package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.RepairClass;
import com.mobileclient.service.RepairClassService;
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

public class RepairClassEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明维修类别idTextView
	private TextView TV_repairClassId;
	// 声明维修类别名称输入框
	private EditText ET_repairClassName;
	protected String carmera_path;
	/*要保存的报修类别信息*/
	RepairClass repairClass = new RepairClass();
	/*报修类别管理业务逻辑层*/
	private RepairClassService repairClassService = new RepairClassService();

	private int repairClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.repairclass_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑报修类别信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_repairClassId = (TextView) findViewById(R.id.TV_repairClassId);
		ET_repairClassName = (EditText) findViewById(R.id.ET_repairClassName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		repairClassId = extras.getInt("repairClassId");
		/*单击修改报修类别按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取维修类别名称*/ 
					if(ET_repairClassName.getText().toString().equals("")) {
						Toast.makeText(RepairClassEditActivity.this, "维修类别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_repairClassName.setFocusable(true);
						ET_repairClassName.requestFocus();
						return;	
					}
					repairClass.setRepairClassName(ET_repairClassName.getText().toString());
					/*调用业务逻辑层上传报修类别信息*/
					RepairClassEditActivity.this.setTitle("正在更新报修类别信息，稍等...");
					String result = repairClassService.UpdateRepairClass(repairClass);
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
	    repairClass = repairClassService.GetRepairClass(repairClassId);
		this.TV_repairClassId.setText(repairClassId+"");
		this.ET_repairClassName.setText(repairClass.getRepairClassName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
