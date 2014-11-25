package com.example.shenghuobang.Password;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PasswordActivity extends Activity {
	
	private Button btnAddPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		btnAddPassword = (Button) findViewById(R.id.btnAddPassword);
		
		btnAddPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                intent.setClass(PasswordActivity.this, AddPasswordActivity.class);
                startActivityForResult(intent, 1);
			}
		});
	}
}
