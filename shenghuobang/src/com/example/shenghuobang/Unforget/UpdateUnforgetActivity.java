package com.example.shenghuobang.Unforget;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Unforget;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
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
	
	private sqliteDataBase.Bll.Unforget bllUnforget;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_unforget);
		
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath();  

		intent = getIntent();
		
		unforgetId = intent.getIntExtra("id", 0);

		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		edUnforgetName = (EditText) findViewById(R.id.etForgetName);
		edUnforgetName.setText(intent.getStringExtra("name"));
		
		tvUnforgetYear = (TextView) findViewById(R.id.tvUnforgetYear);
		tvUnforgetYear.setText(String.valueOf(intent.getIntExtra("year", 2014))+"��");
		
		tvUnforgetMonth = (TextView) findViewById(R.id.tvUnforgetMonth);
		tvUnforgetMonth.setText(String.format("%02d",intent.getIntExtra("month", 11))+"��");
		
		tvUnforgetDay = (TextView) findViewById(R.id.tvUnforgetDay);
		tvUnforgetDay.setText(String.format("%02d",intent.getIntExtra("day", 23))+"��");
		
		tvUnforgetHour = (TextView) findViewById(R.id.tvUnforgetHour);
		
		tvUnforgetHour.setText(String.format("%02d",intent.getIntExtra("hour", 19))+"ʱ");
		tvUnforgetMinute = (TextView) findViewById(R.id.tvUnforgetMinute);
		
		String soundFileName = intent.getStringExtra("soundFileName");
		
		tvUnforgetMinute.setText(String.format("%02d",intent.getIntExtra("minute", 10))+"��");
		
		
		btnAddUnforgetAudio = (Button)findViewById(R.id.btnAddUnforgetAudio);
		
		if(soundFileName.equals("NULL"))
			btnAddUnforgetAudio.setEnabled(false);
		else {
			fileName = soundFileName;
			btnAddUnforgetAudio.setText(soundFileName);
		}
		btnAddUnforgetAudio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MediaPlayer mPlayer = new MediaPlayer();  
	            try{  
	                mPlayer.setDataSource(pathName +"/"+ fileName);  
	                mPlayer.prepare();  
	                mPlayer.start();  
	            }catch(IOException e){  
	                Log.e("LOG_TAG","����ʧ��");  
	            } 
				
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
				
				String unforgetName = edUnforgetName.getText().toString();
				
				Unforget modelUnforget = new Unforget(unforgetId,intYear, intMonth, intDay, intHour, intMinute, 0, unforgetName, fileName);
				bllUnforget.update(modelUnforget);
				
				setResult(1,intent);  
				finish();
				
			}
		});
		
		
		
		llUnforgetDate = (LinearLayout) findViewById(R.id.llUnforgetDate);
		llUnforgetDate.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				
					final Calendar localCalendar = Calendar.getInstance();
					new DatePickerDialog(UpdateUnforgetActivity.this,new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
							tvUnforgetYear.setText(String.valueOf(year)+"��");
							tvUnforgetMonth.setText(String.format("%02d",month+1)+"��");
							tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"��");
							
						}
					},localCalendar.get(1),localCalendar.get(2),localCalendar.get(3)).show();
				}
		});
		llUnforgetTime = (LinearLayout) findViewById(R.id.llUnforgetTime);
		llUnforgetTime.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				
					final Calendar localCalendar = Calendar.getInstance();
					new TimePickerDialog(UpdateUnforgetActivity.this, new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker arg0, int hour, int minute) {
							tvUnforgetHour.setText(String.format("%02d",hour)+"ʱ");
							tvUnforgetMinute.setText(String.format("%02d",minute)+"��");
							
						}
					}, localCalendar.get(4), localCalendar.get(5), true).show();
			}
		});
	}
}