package com.example.shenghuobang.Unforget;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.LoginActivity;
import com.example.shenghuobang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Vibrator;


public class AlarmActivity extends Activity {
	//private static final ;
	private Vibrator vibrator;
	
	private SharedPreferences mySharedPreferences; 
	
	private Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        bundle = getIntent().getExtras();
        
        mySharedPreferences= getSharedPreferences(CommonValue.AppName, Activity.MODE_PRIVATE); 
		
        if(mySharedPreferences.getBoolean(CommonValue.APPLY_ENABLE_VIBRATE, false))
        {
	        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
	        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启   
	        vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1   
        }
        AddNotification();
        finish();
        
    }
    
    public void AddNotification(){ 

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
        
        Notification n = new Notification(); 
        int icon = R.drawable.ic_launcher; 
        
        int ID = bundle.getInt("id", 0);
        String tickerText =bundle.getString("unforget"); 

        long when = System.currentTimeMillis(); 

        n.icon = icon; 
        n.tickerText = tickerText; 
        n.when = when; 
        
        n.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止 
         
        Intent it = new Intent(this,LoginActivity.class);  
        /*********************
         *获得PendingIntent  
         *FLAG_CANCEL_CURRENT:
         *      如果当前系统中已经存在一个相同的PendingIntent对象，
         *      那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象。 
         *FLAG_NO_CREATE:
         *      如果当前系统中不存在相同的PendingIntent对象，
         *      系统将不会创建该PendingIntent对象而是直接返回null。 
         *FLAG_ONE_SHOT:
         *      该PendingIntent只作用一次，
         *      如果该PendingIntent对象已经触发过一次，
         *      那么下次再获取该PendingIntent并且再触发时，
         *      系统将会返回一个SendIntentException，在使用这个标志的时候一定要注意哦。 
         *FLAG_UPDATE_CURRENT:
         *      如果系统中已存在该PendingIntent对象，
         *      那么系统将保留该PendingIntent对象，
         *      但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据，
         *      例如更新Intent中的Extras。这个非常有用，
         *      例如之前提到的，我们需要在每次更新之后更新Intent中的Extras数据，
         *      达到在不同时机传递给MainActivity不同的参数，实现不同的效果。 
         *********************/ 
          
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT); 
        n.setLatestEventInfo(this,"易记帮", n.tickerText, pi); 
        nm.notify(ID,n); 
    } 
}
