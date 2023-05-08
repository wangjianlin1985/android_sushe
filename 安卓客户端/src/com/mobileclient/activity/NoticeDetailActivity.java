package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
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
public class NoticeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明公告id控件
	private TextView TV_noticeId;
	// 声明公告标题控件
	private TextView TV_noticeTitle;
	// 声明公告内容控件
	private TextView TV_noticeContent;
	// 声明公告时间控件
	private TextView TV_noticeTime;
	/* 要保存的公告信息信息 */
	Notice notice = new Notice(); 
	/* 公告信息管理业务逻辑层 */
	private NoticeService noticeService = new NoticeService();
	private int noticeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看公告信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_noticeId = (TextView) findViewById(R.id.TV_noticeId);
		TV_noticeTitle = (TextView) findViewById(R.id.TV_noticeTitle);
		TV_noticeContent = (TextView) findViewById(R.id.TV_noticeContent);
		TV_noticeTime = (TextView) findViewById(R.id.TV_noticeTime);
		Bundle extras = this.getIntent().getExtras();
		noticeId = extras.getInt("noticeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NoticeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    notice = noticeService.GetNotice(noticeId); 
		this.TV_noticeId.setText(notice.getNoticeId() + "");
		this.TV_noticeTitle.setText(notice.getNoticeTitle());
		this.TV_noticeContent.setText(notice.getNoticeContent());
		this.TV_noticeTime.setText(notice.getNoticeTime());
	} 
}
