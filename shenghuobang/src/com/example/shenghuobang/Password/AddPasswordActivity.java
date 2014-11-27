package com.example.shenghuobang.Password;

import java.io.File;
import java.io.IOException;

import sqliteDataBase.Bll.Password;

import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
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

public class AddPasswordActivity extends Activity {
	
	private Button btnAddPasswordCancel;
	private Button btnAddPasswordOK;
	
	private EditText etPasswordName;
	private EditText etPassword;
	private Button btnAddPasswordAudio;
	
	private String pathName = null; 
	private String fileName = "NULL";
	
	private MediaRecorder mRecorder;
	
	private sqliteDataBase.Model.Password modelPassword ;
	private sqliteDataBase.Bll.Password bllPassword;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.acitvity_add_password);
		
		bllPassword = new sqliteDataBase.Bll.Password(this);
		
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		
		etPasswordName = (EditText) findViewById(R.id.etPasswordName);
		etPassword = (EditText) findViewById(R.id.etPassword);
	
		btnAddPasswordCancel = (Button) findViewById(R.id.btnAddPasswordCancel);
		btnAddPasswordCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FileOper fileOper = new FileOper();
				File file = new File(pathName +"/"+ fileName);
				fileOper.deleteFile(file);
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
				
				modelPassword = new sqliteDataBase.Model.Password(passwordName, password, soundFileName);
				bllPassword.insert(modelPassword);
				
				
				setResult(1,getIntent());  
				finish();
				
				
			}
		});
//		
		btnAddPasswordAudio = (Button) findViewById(R.id.btnAddPasswordAudio);
		btnAddPasswordAudio.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				if(btnAddPasswordAudio.getText().toString().equals("����"))
					return false;
				
				if (event.getAction()== MotionEvent.ACTION_UP)//�жϰ�ť�ͷű��ͷ� 
				{
					btnAddPasswordAudio.setText("����");
					mRecorder.stop();  
					mRecorder.release();  
					mRecorder = null;  
					return true;
				}else if(event.getAction()== MotionEvent.ACTION_DOWN){
					
					btnAddPasswordAudio.setText("����¼��");
					
					long curDate = System.currentTimeMillis();
					fileName = String.valueOf(""+curDate+".3gp") ;
					
					mRecorder = new MediaRecorder();  
		            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
		            mRecorder.setOutputFile(pathName +"/"+ fileName);  
		            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
		            try {
		            	mRecorder.prepare();  
		            } catch (IOException e) {
		            	btnAddPasswordAudio.setText("¼��ʧ��");
		            	Log.e("LOG_TAG", "prepare() failed");  
		            }  
		            mRecorder.start();  
				}
				
				return false;
			}
		});
		
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