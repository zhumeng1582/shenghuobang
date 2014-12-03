package com.example.shenghuobang.Unforget;

import com.example.shenghuobang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class AlarmActivity extends Activity {
	private static final int ID = 1213; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        //显示对话框
//        new AlertDialog.Builder(AlarmActivity.this).
//            setTitle("闹钟").//设置标题
//            setMessage("时间到了！").//设置内容
//            setPositiveButton("知道了", new OnClickListener(){//设置按钮
//                public void onClick(DialogInterface dialog, int which) {
//                    AlarmActivity.this.finish();//关闭Activity
//                }
//            }).create().show();
        AddNotification();
        finish();
        
    }
    
    public void AddNotification(){ 
        
        
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
        
        Notification n = new Notification(); 
        
        int icon = R.drawable.ic_launcher; 
        
        String tickerText ="生活帮"; 
       
        long when = System.currentTimeMillis(); 
          
        n.icon = icon; 
        n.tickerText = tickerText; 
        n.when = when; 
        
        n.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止 
         
        Intent it = new Intent(this,AlarmActivity.class);  
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
         
        //设置事件信息，显示在拉开的里面 
        n.setLatestEventInfo(this,"生活帮", "备忘提醒……", pi); 
      
        nm.notify(ID,n); 
    } 
    
    

}
