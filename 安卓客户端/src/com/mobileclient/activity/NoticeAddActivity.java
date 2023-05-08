package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class NoticeAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明公告标题输入框
	private EditText ET_noticeTitle;
	// 声明公告内容输入框
	private EditText ET_noticeContent;
	// 声明公告时间输入框
	private EditText ET_noticeTime;
	protected String carmera_path;
	/*要保存的公告信息信息*/
	Notice notice = new Notice();
	/*公告信息管理业务逻辑层*/
	private NoticeService noticeService = new NoticeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加公告信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_noticeTitle = (EditText) findViewById(R.id.ET_noticeTitle);
		ET_noticeContent = (EditText) findViewById(R.id.ET_noticeContent);
		ET_noticeTime = (EditText) findViewById(R.id.ET_noticeTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加公告信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取公告标题*/ 
					if(ET_noticeTitle.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "公告标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_noticeTitle.setFocusable(true);
						ET_noticeTitle.requestFocus();
						return;	
					}
					notice.setNoticeTitle(ET_noticeTitle.getText().toString());
					/*验证获取公告内容*/ 
					if(ET_noticeContent.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "公告内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_noticeContent.setFocusable(true);
						ET_noticeContent.requestFocus();
						return;	
					}
					notice.setNoticeContent(ET_noticeContent.getText().toString());
					/*验证获取公告时间*/ 
					if(ET_noticeTime.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "公告时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_noticeTime.setFocusable(true);
						ET_noticeTime.requestFocus();
						return;	
					}
					notice.setNoticeTime(ET_noticeTime.getText().toString());
					/*调用业务逻辑层上传公告信息信息*/
					NoticeAddActivity.this.setTitle("正在上传公告信息信息，稍等...");
					String result = noticeService.AddNotice(notice);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
