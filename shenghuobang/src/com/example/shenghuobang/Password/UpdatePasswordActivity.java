package com.example.shenghuobang.Password;

import java.io.File;
import java.io.IOException;

import sqliteDataBase.Bll.Password;

import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePasswordActivity extends Activity {
	
	private Button btnAddPasswordCancel;
	private Button btnAddPasswordOK;
	
	private EditText etPasswordName;
	private EditText etPassword;
	private Button btnAddPasswordAudio;
	
	private String pathName = null; 
	private String fileName = "NULL";
	
	
	private sqliteDataBase.Model.Password modelPassword ;
	private sqliteDataBase.Bll.Password bllPassword;
	
	private Intent intent;
	private MediaRecorder mRecorder;
	private int passWordId;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.acitvity_add_password);
		
		bllPassword = new sqliteDataBase.Bll.Password(this);
		
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		
		intent = getIntent();
		
		passWordId = intent.getIntExtra("id", 0);
		
		Log.i("tag", "����ID��"+passWordId);
		
		
		etPasswordName = (EditText) findViewById(R.id.etPasswordName);
		etPasswordName.setText(intent.getStringExtra("name"));
		
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setText(intent.getStringExtra("passWord"));
		
		
	
		btnAddPasswordCancel = (Button) findViewById(R.id.btnAddPasswordCancel);
		btnAddPasswordCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnAddPasswordOK = (Button) findViewById(R.id.btnAddPasswordOK);
		btnAddPasswordOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String passwordName = etPasswordName.getText().toString();
				if(passwordName.equals("")){
					Toast.makeText(getApplicationContext(), "����������Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String password = etPassword.getText().toString();
				if(password.equals("")){
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				String soundFileName = fileName;
				
				modelPassword = new sqliteDataBase.Model.Password(passWordId,passwordName, password, soundFileName);
				Log.i("tag", "��������"+passwordName);
				bllPassword.update(modelPassword);
				setResult(1,getIntent());  
				finish();
				
				
			}
		});
	
		btnAddPasswordAudio = (Button) findViewById(R.id.btnAddPasswordAudio);
		String soundFileName = intent.getStringExtra("soundFileName");
		
		if(soundFileName.equals("NULL"))
			btnAddPasswordAudio.setEnabled(false);
		else {
			fileName = soundFileName;
			btnAddPasswordAudio.setText(soundFileName);
		}

		btnAddPasswordAudio.setOnClickListener(new OnClickListener() {
			
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
		
	}

}
