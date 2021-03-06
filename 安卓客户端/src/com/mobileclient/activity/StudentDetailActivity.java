package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
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
public class StudentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学号控件
	private TextView TV_studentNo;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明所在班级控件
	private TextView TV_className;
	// 声明姓名控件
	private TextView TV_studentName;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明照片图片框
	private ImageView iv_studentPhoto;
	// 声明联系方式控件
	private TextView TV_lxfs;
	// 声明所在房间控件
	private TextView TV_roomObj;
	// 声明附加信息控件
	private TextView TV_memo;
	/* 要保存的学生信息信息 */
	Student student = new Student(); 
	/* 学生信息管理业务逻辑层 */
	private StudentService studentService = new StudentService();
	private RoomService roomService = new RoomService();
	private String studentNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_studentNo = (TextView) findViewById(R.id.TV_studentNo);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_className = (TextView) findViewById(R.id.TV_className);
		TV_studentName = (TextView) findViewById(R.id.TV_studentName);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto); 
		TV_lxfs = (TextView) findViewById(R.id.TV_lxfs);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		studentNo = extras.getString("studentNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    student = studentService.GetStudent(studentNo); 
		this.TV_studentNo.setText(student.getStudentNo());
		this.TV_password.setText(student.getPassword());
		this.TV_className.setText(student.getClassName());
		this.TV_studentName.setText(student.getStudentName());
		this.TV_sex.setText(student.getSex());
		Date birthday = new Date(student.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		byte[] studentPhoto_data = null;
		try {
			// 获取图片数据
			studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + student.getStudentPhoto());
			Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0,studentPhoto_data.length);
			this.iv_studentPhoto.setImageBitmap(studentPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_lxfs.setText(student.getLxfs());
		Room roomObj = roomService.GetRoom(student.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomName());
		this.TV_memo.setText(student.getMemo());
	} 
}
