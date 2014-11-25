package com.example.shenghuobang.Unforget;

import java.io.IOException;
import java.util.Calendar;

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

public class AddUnforgetActivity extends Activity {
	
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
	private EditText edSoundFileName;
	
	
	private Button btnAddUnforgetAudio;
	private String FileName = null; 
	
	private Intent intent;
	private MediaRecorder mRecorder;
	
	private sqliteDataBase.Bll.Unforget bllUnforget;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_unforget);
		
		FileName = Environment.getExternalStorageDirectory().getAbsolutePath();  
        FileName += "/audiorecordtest.3gp";  
		
		intent = getIntent();
		
		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		tvUnforgetYear = (TextView) findViewById(R.id.tvUnforgetYear);
		tvUnforgetMonth = (TextView) findViewById(R.id.tvUnforgetMonth);
		tvUnforgetDay = (TextView) findViewById(R.id.tvUnforgetDay);
		tvUnforgetHour = (TextView) findViewById(R.id.tvUnforgetHour);
		tvUnforgetMinute = (TextView) findViewById(R.id.tvUnforgetMinute);
		
		edUnforgetName = (EditText) findViewById(R.id.etForgetName);
		
		btnAddUnforgetAudio = (Button)findViewById(R.id.btnAddUnforgetAudio);
		
		btnAddUnforgetAudio.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction()== MotionEvent.ACTION_UP)//判断按钮释放被释放 
				{
					Toast.makeText(AddUnforgetActivity.this, "按钮释放", Toast.LENGTH_SHORT).show();
					mRecorder.stop();  
					mRecorder.release();  
					mRecorder = null;  
				}else if(event.getAction()== MotionEvent.ACTION_DOWN){
					Toast.makeText(AddUnforgetActivity.this, "按钮按下", Toast.LENGTH_SHORT).show();

					mRecorder = new MediaRecorder();  
		             mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		             mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
		             mRecorder.setOutputFile(FileName);  
		             mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
		             try {  
		                 mRecorder.prepare();  
		             } catch (IOException e) {  
		                 Log.e("LOG_TAG", "prepare() failed");  
		             }  
		             mRecorder.start();  
				}
				
				return false;
			}
		});
		
		
		btnAddUnforgetCancel = (Button) findViewById(R.id.btnAddUnforgetCancel);
		btnAddUnforgetCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				MediaPlayer mPlayer = new MediaPlayer();  
	            try{  
	                mPlayer.setDataSource(FileName);  
	                mPlayer.prepare();  
	                mPlayer.start();  
	            }catch(IOException e){  
	                Log.e("LOG_TAG","播放失败");  
	            } 
				//finish();
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
				
				Unforget modelUnforget = new Unforget(intYear, intMonth, intDay, intHour, intMinute, 0, unforgetName, unforgetName);
				bllUnforget.insert(modelUnforget);
				
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
					new DatePickerDialog(AddUnforgetActivity.this,new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
							tvUnforgetYear.setText(String.valueOf(year)+"年");
							tvUnforgetMonth.setText(String.format("%02d",month+1)+"月");
							tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"日");
							
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
					new TimePickerDialog(AddUnforgetActivity.this, new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker arg0, int hour, int minute) {
							tvUnforgetHour.setText(String.format("%02d",hour)+"时");
							tvUnforgetMinute.setText(String.format("%02d",minute)+"分");
							
						}
					}, localCalendar.get(4), localCalendar.get(5), true).show();
			}
		});
	}
}
