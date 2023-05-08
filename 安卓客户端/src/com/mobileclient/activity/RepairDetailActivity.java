package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Repair;
import com.mobileclient.service.RepairService;
import com.mobileclient.domain.RepairClass;
import com.mobileclient.service.RepairClassService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.RepairState;
import com.mobileclient.service.RepairStateService;
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
public class RepairDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明报修id控件
	private TextView TV_repairId;
	// 声明报修类别控件
	private TextView TV_repaiClassObj;
	// 声明故障简述控件
	private TextView TV_repaitTitle;
	// 声明故障详述控件
	private TextView TV_repairContent;
	// 声明上报学生控件
	private TextView TV_studentObj;
	// 声明处理结果控件
	private TextView TV_handleResult;
	// 声明维修状态控件
	private TextView TV_repairStateObj;
	/* 要保存的报修信息信息 */
	Repair repair = new Repair(); 
	/* 报修信息管理业务逻辑层 */
	private RepairService repairService = new RepairService();
	private RepairClassService repairClassService = new RepairClassService();
	private StudentService studentService = new StudentService();
	private RepairStateService repairStateService = new RepairStateService();
	private int repairId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.repair_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看报修信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_repairId = (TextView) findViewById(R.id.TV_repairId);
		TV_repaiClassObj = (TextView) findViewById(R.id.TV_repaiClassObj);
		TV_repaitTitle = (TextView) findViewById(R.id.TV_repaitTitle);
		TV_repairContent = (TextView) findViewById(R.id.TV_repairContent);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_handleResult = (TextView) findViewById(R.id.TV_handleResult);
		TV_repairStateObj = (TextView) findViewById(R.id.TV_repairStateObj);
		Bundle extras = this.getIntent().getExtras();
		repairId = extras.getInt("repairId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RepairDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    repair = repairService.GetRepair(repairId); 
		this.TV_repairId.setText(repair.getRepairId() + "");
		RepairClass repaiClassObj = repairClassService.GetRepairClass(repair.getRepaiClassObj());
		this.TV_repaiClassObj.setText(repaiClassObj.getRepairClassName());
		this.TV_repaitTitle.setText(repair.getRepaitTitle());
		this.TV_repairContent.setText(repair.getRepairContent());
		Student studentObj = studentService.GetStudent(repair.getStudentObj());
		this.TV_studentObj.setText(studentObj.getStudentName());
		this.TV_handleResult.setText(repair.getHandleResult());
		RepairState repairStateObj = repairStateService.GetRepairState(repair.getRepairStateObj());
		this.TV_repairStateObj.setText(repairStateObj.getRepairStateName());
	} 
}
