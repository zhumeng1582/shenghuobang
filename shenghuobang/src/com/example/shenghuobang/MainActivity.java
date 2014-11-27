package com.example.shenghuobang;

import com.example.shenghuobang.Charge.ChargeActivity;
import com.example.shenghuobang.Password.PasswordActivity;
import com.example.shenghuobang.Unforget.UnforgetActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
	
	TabHost tabHost;
	private TextView main_tab_unread_tv;
	private RelativeLayout main_tab_charge, main_tab_unforget,main_tab_password,main_tab_settings;
	private LinearLayout main_layout_charge,main_layout_unforget,main_layout_password,main_layout_settings;
	private ImageView  img_tab_charge ,img_tab_unforget,img_tab_password,img_tab_settings ;
	private TextView text_tab_charge ,text_tab_unforget,text_tab_password,text_tab_settings;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
        	
		initTab();
		init();   
	}
        
	private void init() {
    	//����ײ�
    	main_tab_charge = (RelativeLayout) findViewById(R.id.main_tab_charge);
    	main_tab_unforget = (RelativeLayout) findViewById(R.id.main_tab_unforget);
    	main_tab_password=(RelativeLayout) findViewById(R.id.main_tab_password);
    	main_tab_settings=(RelativeLayout) findViewById(R.id.main_tab_settings);
        
    	//�ײ�ͼƬ����                
    	img_tab_charge = (ImageView) findViewById(R.id.img_tab_charge) ;
    	img_tab_unforget = (ImageView) findViewById(R.id.img_tab_unforget) ;
    	img_tab_password = (ImageView) findViewById(R.id.img_tab_password) ;
    	img_tab_settings = (ImageView) findViewById(R.id.img_tab_settings) ;
    	
    	//�ײ�����
    	text_tab_charge = (TextView) findViewById(R.id.tab_charge_text) ;
    	text_tab_unforget = (TextView) findViewById(R.id.tab_unforget_text) ;
    	text_tab_password = (TextView) findViewById(R.id.tab_password_text) ;
    	text_tab_settings = (TextView) findViewById(R.id.tab_settings_text) ;
    	main_layout_charge=(LinearLayout) findViewById(R.id.main_layout_charge);
    	main_layout_unforget=(LinearLayout) findViewById(R.id.main_layout_unforget);
    	main_layout_password=(LinearLayout) findViewById(R.id.main_layout_password);
    	main_layout_settings=(LinearLayout) findViewById(R.id.main_layout_settings);
 
        //ͼƬ����
        img_tab_charge.setImageResource(R.drawable.ico_writeing_press);
        img_tab_unforget.setImageResource(R.drawable.ico_unforget_norm);
        img_tab_password.setImageResource(R.drawable.ico_password_norm);
        img_tab_settings.setImageResource(R.drawable.ico_setting_norm);
        //���ֵ���
        text_tab_charge.setTextColor(getResources().getColor(R.color.menu_color));
        text_tab_unforget.setTextColor(getResources().getColor(R.color.grey));
        text_tab_password.setTextColor(getResources().getColor(R.color.grey));
        text_tab_settings.setTextColor(getResources().getColor(R.color.grey));
        

        main_tab_charge.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		tabHost.setCurrentTabByTag("first");
 
                //ͼƬ����
                img_tab_charge.setImageResource(R.drawable.ico_writeing_press);
                img_tab_unforget.setImageResource(R.drawable.ico_unforget_norm);
                img_tab_password.setImageResource(R.drawable.ico_password_norm);
                img_tab_settings.setImageResource(R.drawable.ico_setting_norm);
                //���ֵ���
                text_tab_charge.setTextColor(getResources().getColor(R.color.menu_color));
                text_tab_unforget.setTextColor(getResources().getColor(R.color.grey));
                text_tab_password.setTextColor(getResources().getColor(R.color.grey));
                text_tab_settings.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        
        main_tab_unforget.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		tabHost.setCurrentTabByTag("second");
                            	
                //ͼƬ����
                img_tab_charge.setImageResource(R.drawable.ico_writeing_norm);
                img_tab_unforget.setImageResource(R.drawable.ico_unforget_press);
                img_tab_password.setImageResource(R.drawable.ico_password_norm);
                img_tab_settings.setImageResource(R.drawable.ico_setting_norm);
                //���ֵ���
                text_tab_charge.setTextColor(getResources().getColor(R.color.grey));
                text_tab_unforget.setTextColor(getResources().getColor(R.color.menu_color));
                text_tab_password.setTextColor(getResources().getColor(R.color.grey));
                text_tab_settings.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        
        main_tab_password.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		tabHost.setCurrentTabByTag("third");
        		//ͼƬ����
        		img_tab_charge.setImageResource(R.drawable.ico_writeing_norm);
                img_tab_unforget.setImageResource(R.drawable.ico_unforget_norm);
                img_tab_password.setImageResource(R.drawable.ico_password_press);
                img_tab_settings.setImageResource(R.drawable.ico_setting_norm);
                //���ֵ���
                text_tab_charge.setTextColor(getResources().getColor(R.color.grey));
                text_tab_unforget.setTextColor(getResources().getColor(R.color.grey));
                text_tab_password.setTextColor(getResources().getColor(R.color.menu_color));
                text_tab_settings.setTextColor(getResources().getColor(R.color.grey));
            }
        });
            
        main_tab_settings.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		// TODO Auto-generated method stub
        		tabHost.setCurrentTabByTag("four");
        		//ͼƬ����
		        img_tab_charge.setImageResource(R.drawable.ico_writeing_norm);
		        img_tab_unforget.setImageResource(R.drawable.ico_unforget_norm);
		        img_tab_password.setImageResource(R.drawable.ico_password_norm);
		        img_tab_settings.setImageResource(R.drawable.ico_setting_press);
		        //���ֵ���
		        text_tab_charge.setTextColor(getResources().getColor(R.color.grey));
		        text_tab_unforget.setTextColor(getResources().getColor(R.color.grey));
		        text_tab_password.setTextColor(getResources().getColor(R.color.grey));
		        text_tab_settings.setTextColor(getResources().getColor(R.color.menu_color));
        	}
        });
    }
    private void initTab(){
    	tabHost=getTabHost();
    	tabHost.addTab(tabHost.newTabSpec("first").setIndicator("first").setContent(
    		new Intent(this, ChargeActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("second").setIndicator("second").setContent(
    		new Intent(this, UnforgetActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("third").setIndicator("third").setContent(
    		new Intent(this, PasswordActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("four").setIndicator("four").setContent(
    		new Intent(this, SettingActivity.class)));
    }
}