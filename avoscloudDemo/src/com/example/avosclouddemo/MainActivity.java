package com.example.avosclouddemo;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.sns.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	private SNSType type =SNSType.AVOSCloudSNSQQ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final SNSCallback myCallback = new SNSCallback() {
            @Override
            public void done(SNSBase object, SNSException e) {
                if (e == null) {
                	
                    setTitle("login ok " + type );
                    //SNS.associateWithAuthData(AVUser.getCurrentUser(), object.userInfo(), null);
                }else{
                	setTitle("login fail " + type );
                }
                
            }
        };

        // ����
        try {
			SNS.setupPlatform(type, "714053646", "714053646", "https://leancloud.cn/1.1/sns/goto/c0gvnylpf7btj7cb");
			
		} catch (AVException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
        SNS.loginWithCallback(this, type, myCallback);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SNS.onActivityResult(requestCode, resultCode, data, type);
    }

}