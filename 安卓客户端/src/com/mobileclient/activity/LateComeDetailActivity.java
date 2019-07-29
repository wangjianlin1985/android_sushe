package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LateCome;
import com.mobileclient.service.LateComeService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class LateComeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明晚归记录id控件
	private TextView TV_lateComeId;
	// 声明晚归学生控件
	private TextView TV_studentObj;
	// 声明晚归原因控件
	private TextView TV_reason;
	// 声明晚归时间控件
	private TextView TV_lateComeTime;
	/* 要保存的晚归信息信息 */
	LateCome lateCome = new LateCome(); 
	/* 晚归信息管理业务逻辑层 */
	private LateComeService lateComeService = new LateComeService();
	private StudentService studentService = new StudentService();
	private int lateComeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.latecome_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看晚归信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_lateComeId = (TextView) findViewById(R.id.TV_lateComeId);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_reason = (TextView) findViewById(R.id.TV_reason);
		TV_lateComeTime = (TextView) findViewById(R.id.TV_lateComeTime);
		Bundle extras = this.getIntent().getExtras();
		lateComeId = extras.getInt("lateComeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LateComeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    lateCome = lateComeService.GetLateCome(lateComeId); 
		this.TV_lateComeId.setText(lateCome.getLateComeId() + "");
		Student studentObj = studentService.GetStudent(lateCome.getStudentObj());
		this.TV_studentObj.setText(studentObj.getStudentName());
		this.TV_reason.setText(lateCome.getReason());
		this.TV_lateComeTime.setText(lateCome.getLateComeTime());
	} 
}
