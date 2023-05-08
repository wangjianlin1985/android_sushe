package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class RepairStateEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明状态idTextView
	private TextView TV_repairStateId;
	// 声明状态名称输入框
	private EditText ET_repairStateName;
	protected String carmera_path;
	/*要保存的维修状态信息*/
	RepairState repairState = new RepairState();
	/*维修状态管理业务逻辑层*/
	private RepairStateService repairStateService = new RepairStateService();

	private int repairStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.repairstate_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑维修状态信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_repairStateId = (TextView) findViewById(R.id.TV_repairStateId);
		ET_repairStateName = (EditText) findViewById(R.id.ET_repairStateName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		repairStateId = extras.getInt("repairStateId");
		/*单击修改维修状态按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取状态名称*/ 
					if(ET_repairStateName.getText().toString().equals("")) {
						Toast.makeText(RepairStateEditActivity.this, "状态名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_repairStateName.setFocusable(true);
						ET_repairStateName.requestFocus();
						return;	
					}
					repairState.setRepairStateName(ET_repairStateName.getText().toString());
					/*调用业务逻辑层上传维修状态信息*/
					RepairStateEditActivity.this.setTitle("正在更新维修状态信息，稍等...");
					String result = repairStateService.UpdateRepairState(repairState);
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
	    repairState = repairStateService.GetRepairState(repairStateId);
		this.TV_repairStateId.setText(repairStateId+"");
		this.ET_repairStateName.setText(repairState.getRepairStateName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
