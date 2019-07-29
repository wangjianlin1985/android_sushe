package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
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
public class StudentAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明学号输入框
	private EditText ET_studentNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明所在班级输入框
	private EditText ET_className;
	// 声明姓名输入框
	private EditText ET_studentName;
	// 声明性别输入框
	private EditText ET_sex;
	// 出版出生日期控件
	private DatePicker dp_birthday;
	// 声明照片图片框控件
	private ImageView iv_studentPhoto;
	private Button btn_studentPhoto;
	protected int REQ_CODE_SELECT_IMAGE_studentPhoto = 1;
	private int REQ_CODE_CAMERA_studentPhoto = 2;
	// 声明联系方式输入框
	private EditText ET_lxfs;
	// 声明所在房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null;
	/*所在房间管理业务逻辑层*/
	private RoomService roomService = new RoomService();
	// 声明附加信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的学生信息信息*/
	Student student = new Student();
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加学生信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_studentNo = (EditText) findViewById(R.id.ET_studentNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_className = (EditText) findViewById(R.id.ET_className);
		ET_studentName = (EditText) findViewById(R.id.ET_studentName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_studentPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StudentAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_studentPhoto);
			}
		});
		btn_studentPhoto = (Button) findViewById(R.id.btn_studentPhoto);
		btn_studentPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_studentPhoto);  
			}
		});
		ET_lxfs = (EditText) findViewById(R.id.ET_lxfs);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的所在房间
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount];
		for(int i=0;i<roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i).getRoomName();
		}
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				student.setRoomObj(roomList.get(arg2).getRoomId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加学生信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取学号*/
					if(ET_studentNo.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "学号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentNo.setFocusable(true);
						ET_studentNo.requestFocus();
						return;
					}
					student.setStudentNo(ET_studentNo.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					student.setPassword(ET_password.getText().toString());
					/*验证获取所在班级*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "所在班级输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					student.setClassName(ET_className.getText().toString());
					/*验证获取姓名*/ 
					if(ET_studentName.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentName.setFocusable(true);
						ET_studentName.requestFocus();
						return;	
					}
					student.setStudentName(ET_studentName.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					student.setSex(ET_sex.getText().toString());
					/*获取出生日期*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					student.setBirthday(new Timestamp(birthday.getTime()));
					if(student.getStudentPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						StudentAddActivity.this.setTitle("正在上传图片，稍等...");
						String studentPhoto = HttpUtil.uploadFile(student.getStudentPhoto());
						StudentAddActivity.this.setTitle("图片上传完毕！");
						student.setStudentPhoto(studentPhoto);
					} else {
						student.setStudentPhoto("upload/noimage.jpg");
					}
					/*验证获取联系方式*/ 
					if(ET_lxfs.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "联系方式输入不能为空!", Toast.LENGTH_LONG).show();
						ET_lxfs.setFocusable(true);
						ET_lxfs.requestFocus();
						return;	
					}
					student.setLxfs(ET_lxfs.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					student.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传学生信息信息*/
					StudentAddActivity.this.setTitle("正在上传学生信息信息，稍等...");
					String result = studentService.AddStudent(student);
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
		if (requestCode == REQ_CODE_CAMERA_studentPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_studentPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_studentPhoto.setImageBitmap(booImageBm);
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.student.setStudentPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_studentPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_studentPhoto.setImageBitmap(bm); 
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			student.setStudentPhoto(filename); 
		}
	}
}
