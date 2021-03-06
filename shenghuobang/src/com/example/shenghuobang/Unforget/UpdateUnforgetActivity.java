package com.example.shenghuobang.Unforget;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.example.shenghuobang.MediaPlayerPlay;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Unforget;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateUnforgetActivity extends Activity {
	
	private Button btnAddUnforgetCancel;
	private Button btnAddUnforgetOK;
	
	private LinearLayout llUnforgetDate;
	private LinearLayout llUnforgetTime;
	
	private TextView tvUnforgetYear;
	private TextView tvUnforgetMonth;
	private TextView tvUnforgetDay;
	
	private TextView tvUnforgetHour;
	private TextView tvUnforgetMinute;
	
	private EditText edUnforgetName;
	
	private Button btnAddUnforgetAudio;
	private String pathName = null; 
	private String fileName = "NULL";
	
	private Intent intent;
	private MediaRecorder mRecorder;
	private int unforgetId;
	private AlarmManager alarmManager=null;
	
	private sqliteDataBase.Bll.Unforget bllUnforget;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_unforget);
		
		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		

		intent = getIntent();
		
		unforgetId = intent.getIntExtra("id", 0);

		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		edUnforgetName = (EditText) findViewById(R.id.etForgetName);
		edUnforgetName.setText(intent.getStringExtra("name"));
		
		tvUnforgetYear = (TextView) findViewById(R.id.tvUnforgetYear);
		tvUnforgetYear.setText(String.valueOf(intent.getIntExtra("year", 2014))+"年");
		
		tvUnforgetMonth = (TextView) findViewById(R.id.tvUnforgetMonth);
		tvUnforgetMonth.setText(String.format("%02d",intent.getIntExtra("month", 11))+"月");
		
		tvUnforgetDay = (TextView) findViewById(R.id.tvUnforgetDay);
		tvUnforgetDay.setText(String.format("%02d",intent.getIntExtra("day", 23))+"日");
		
		tvUnforgetHour = (TextView) findViewById(R.id.tvUnforgetHour);
		
		tvUnforgetHour.setText(String.format("%02d",intent.getIntExtra("hour", 19))+"时");
		tvUnforgetMinute = (TextView) findViewById(R.id.tvUnforgetMinute);
		
		String soundFileName = intent.getStringExtra("soundFileName");
		
		tvUnforgetMinute.setText(String.format("%02d",intent.getIntExtra("minute", 10))+"分");
		
		
		btnAddUnforgetAudio = (Button)findViewById(R.id.btnAddUnforgetAudio);
		
		if(soundFileName.equals("NULL")){
			btnAddUnforgetAudio.setEnabled(false);
			btnAddUnforgetAudio.setText("没有录音");
		}else {
			fileName = soundFileName;
			btnAddUnforgetAudio.setText("播放");
		}
		btnAddUnforgetAudio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pathName = 	Environment.getExternalStorageDirectory().getAbsolutePath();  
				File file = new File(pathName +File.separator+ fileName);
				if(!file.exists()){
            		Log.e("LOG_TAG","文件不存在");  
            		return;
            	}
				
				MediaPlayerPlay mediaPlayPlay = new MediaPlayerPlay(pathName +File.separator+ fileName);
				mediaPlayPlay.Start();
				btnAddUnforgetAudio.setText("正在播放");
				
				mediaPlayPlay.SetOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer arg0) {
						btnAddUnforgetAudio.setText("播放");
					}
				});
			}
		});
		
		
		btnAddUnforgetCancel = (Button) findViewById(R.id.btnAddUnforgetCancel);
		btnAddUnforgetCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		btnAddUnforgetOK = (Button) findViewById(R.id.btnAddUnforgetOK);
		btnAddUnforgetOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String unforgetName = edUnforgetName.getText().toString();
				if(unforgetName.equals("")){
					
					Toast.makeText(getApplicationContext(), "备忘名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Calendar c_cur = Calendar.getInstance();//获取日期对象  
				Calendar c_set = getDateTimeFromTextView();//获取日期对象    
				
				
                if(c_cur.getTimeInMillis()> c_set.getTimeInMillis()){  
                	Toast.makeText(getApplicationContext(), "备忘时间小于系统时间", Toast.LENGTH_SHORT).show();
    				return;
                } 
				
				Unforget modelUnforget = new Unforget(unforgetId,c_set.get(Calendar.YEAR), c_set.get(Calendar.MONTH)+1, c_set.get(Calendar.DAY_OF_MONTH),
						c_set.get(Calendar.HOUR_OF_DAY), c_set.get(Calendar.MINUTE), c_set.get(Calendar.SECOND), unforgetName, fileName);
				bllUnforget.update(modelUnforget);
				
                Intent intent = new Intent(UpdateUnforgetActivity.this, AlarmReceiver.class);    //创建Intent对象
                PendingIntent pi = PendingIntent.getBroadcast(UpdateUnforgetActivity.this, modelUnforget.getId(), intent, 0);    //创建PendingIntent
                alarmManager.cancel(pi); 
                
                alarmManager.set(AlarmManager.RTC_WAKEUP, c_set.getTimeInMillis(), pi);
                
                
				finish();
				
			}
		});
		
		
		
		llUnforgetDate = (LinearLayout) findViewById(R.id.llUnforgetDate);
		llUnforgetDate.setOnClickListener(new OnClickListener() {
			
			String strYear = tvUnforgetYear.getText().toString().substring(0, 4);
			int intYear = Integer.parseInt(strYear);
			
			
			String strMonth = tvUnforgetMonth.getText().toString().substring(0, 2);
			int intMonth = Integer.parseInt(strMonth);

			
			String strDay = tvUnforgetDay.getText().toString().substring(0, 2);
			int intDay = Integer.parseInt(strDay);
			
			@Override
			public void onClick(View arg0) {
					new DatePickerDialog(UpdateUnforgetActivity.this,new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
							tvUnforgetYear.setText(String.valueOf(year)+"年");
							tvUnforgetMonth.setText(String.format("%02d",month+1)+"月");
							tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"日");
							
						}
					},intYear,intMonth-1,intDay).show();
				}
		});
		llUnforgetTime = (LinearLayout) findViewById(R.id.llUnforgetTime);
		llUnforgetTime.setOnClickListener(new OnClickListener() {
			
			String strHour = tvUnforgetHour.getText().toString().substring(0, 2);
			int intHour = Integer.parseInt(strHour);
			
			String strMinute = tvUnforgetMinute.getText().toString().substring(0, 2);
			int intMinute = Integer.parseInt(strMinute);
			
			@Override
			public void onClick(View arg0) {
				
					
					new TimePickerDialog(UpdateUnforgetActivity.this, new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker arg0, int hour, int minute) {
							tvUnforgetHour.setText(String.format("%02d",hour)+"时");
							tvUnforgetMinute.setText(String.format("%02d",minute)+"分");
							
						}
					}, intHour,intMinute, true).show();
			}
		});
	}
	private Calendar getDateTimeFromTextView(){
		Calendar c_set = Calendar.getInstance();
		String strYear = tvUnforgetYear.getText().toString().substring(0, 4);
		int intYear;
		intYear=Integer.parseInt(strYear);
		
		String strMonth = tvUnforgetMonth.getText().toString().substring(0, 2);
		int intMonth;
		intMonth=Integer.parseInt(strMonth);
		
		String strDay = tvUnforgetDay.getText().toString().substring(0, 2);
		int intDay;
		intDay=Integer.parseInt(strDay);
		
		String strHour = tvUnforgetHour.getText().toString().substring(0, 2);
		int intHour;
		intHour=Integer.parseInt(strHour);
		
		String strMinute = tvUnforgetMinute.getText().toString().substring(0, 2);
		int intMinute;
		intMinute=Integer.parseInt(strMinute);
		
		c_set.set(Calendar.YEAR, intYear);
		c_set.set(Calendar.MONTH, intMonth-1);
		c_set.set(Calendar.DAY_OF_MONTH, intDay);
		c_set.set(Calendar.HOUR_OF_DAY, intHour);        //设置闹钟小时数
		c_set.set(Calendar.MINUTE, intMinute);            //设置闹钟的分钟数
		c_set.set(Calendar.SECOND, 0);                //设置闹钟的秒数
		c_set.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
		
		return c_set;
	}
}
