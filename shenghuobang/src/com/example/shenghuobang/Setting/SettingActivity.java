package com.example.shenghuobang.Setting;

import com.example.shenghuobang.R;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class SettingActivity extends PreferenceActivity {
	
	private CheckBoxPreference sound;       
    private CheckBoxPreference vibrate;   
    private CheckBoxPreference passwordEnable;  
    private PreferenceScreen changePassword;  
    private PreferenceScreen updateData; 
    private PreferenceScreen feedback; 
    private PreferenceScreen updateVersion; 
    
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting_preference);
		
//		sound = (CheckBoxPreference) findPreference("sound");  
//		vibrate = (CheckBoxPreference) findPreference("vibrate");  
//		passwordEnable = (CheckBoxPreference) findPreference("passwordEnable");  
//		
//		changePassword = (PreferenceScreen) findPreference("changePassword");  
//		updateData = (PreferenceScreen) findPreference("updateData");  
//		feedback = (PreferenceScreen) findPreference("feedback");  
//		updateVersion = (PreferenceScreen) findPreference("updateVersion");  
		
        
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
//		if(preference == updateData){
			Intent i = new Intent();
			i= new Intent(getApplicationContext(), UpdateDataActivity.class);
			startActivityForResult(i, 0);
//		}else if(preference == updateVersion) {
//			Intent i = new Intent();
//			i= new Intent(getApplicationContext(), UpdateVersionActivity.class);
//			startActivityForResult(i, 0);
//		}
		return true;
	}
	
	
}
