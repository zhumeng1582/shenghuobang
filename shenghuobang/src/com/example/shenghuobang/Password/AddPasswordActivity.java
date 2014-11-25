package com.example.shenghuobang.Password;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AddPasswordActivity extends Activity {
	
	private Button btnAddPasswordCancel;
	private Button btnAddPasswordOK;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.acitvity_add_password);
		
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
				Toast.makeText(AddPasswordActivity.this, "功能为实现", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

}
