package com.example.shenghuobang;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;

public class StartPageActivity extends Activity {
	
	private MessageHandler mHandler = new MessageHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);
		
		new Thread()
        {
            @Override
            public void run()
            {
            	try {
					Thread.sleep(1000);
					Message msg = new Message();
		        	msg.what =0;
		        	mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
            }
        }.start();
	}

	public class MessageHandler extends Handler {
    	
    	public MessageHandler() {      
        }  
    	public MessageHandler(Looper looper) {   
    		super(looper);   
        }   

        @Override   
        public void handleMessage(Message msg) {
        	switch (msg.what){
        		case 0:
	        		Intent intent = new Intent();
	                intent.setClass(StartPageActivity.this, MainActivity.class);
	                startActivity(intent);
	                finish();
	                break;
        		default:break;
        	}
        }
    }  
}
