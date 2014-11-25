package com.example.shenghuobang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class InputPasswordActivity extends Activity {
	
	private EditText etPassword;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		  
		setContentView(R.layout.activity_password_input);
		
		etPassword = (EditText) findViewById(R.id.etPassword);
		
		etPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
				//Toast.makeText(getApplicationContext(), "onTextChanged", Toast.LENGTH_LONG).show(); 
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
				//Toast.makeText(getApplicationContext(), "beforeTextChanged", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				//Toast.makeText(getApplicationContext(), "afterTextChanged", Toast.LENGTH_LONG).show();
				String password = etPassword.getText().toString();
				if(password.length()!=4)
					return;
				if(password.equals("1234")){
					Intent intent = new Intent();
	                intent.setClass(InputPasswordActivity.this, StartPageActivity.class);
	                startActivity(intent);
	                finish();
				}else{
					Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_LONG).show();
					etPassword.setText("");
				}
			}
		});
		
	}
}
