package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.House;
import com.mobileclient.service.HouseService;
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
public class HouseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明宿舍id控件
	private TextView TV_houseId;
	// 声明宿舍楼名称控件
	private TextView TV_houseName;
	/* 要保存的宿舍楼信息 */
	House house = new House(); 
	/* 宿舍楼管理业务逻辑层 */
	private HouseService houseService = new HouseService();
	private int houseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.house_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看宿舍楼详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_houseId = (TextView) findViewById(R.id.TV_houseId);
		TV_houseName = (TextView) findViewById(R.id.TV_houseName);
		Bundle extras = this.getIntent().getExtras();
		houseId = extras.getInt("houseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HouseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    house = houseService.GetHouse(houseId); 
		this.TV_houseId.setText(house.getHouseId() + "");
		this.TV_houseName.setText(house.getHouseName());
	} 
}
