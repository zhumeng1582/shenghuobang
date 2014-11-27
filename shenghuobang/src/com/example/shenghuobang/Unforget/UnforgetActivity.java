package com.example.shenghuobang.Unforget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;

import sqliteDataBase.Model.Unforget;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class UnforgetActivity extends Activity {
	
	private Button btnAddUnForget;
	private GridView gridViewUnforget;
	private sqliteDataBase.Bll.Unforget bllUnforget;
	private List<Unforget> listUnforget;
	private String pathName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unforget);
		
		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath();  
		
		btnAddUnForget = (Button) findViewById(R.id.btnAddUnForget);
		
		btnAddUnForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("mode", false);
                intent.setClass(UnforgetActivity.this, AddUnforgetActivity.class);
                startActivityForResult(intent, 1);
			}
		});
		
		gridViewUnforget = (GridView) findViewById(R.id.gridViewUnforget);
		
		
		
		showGridViewData(); 
        
		gridViewUnforget.setOnItemClickListener(new OnItemClickListener() 
        { 
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
            	
            	Log.i("tag", "��ť����"); 
            	
                Unforget unforget = listUnforget.get(position);
                 
                
                Log.i("tag", "�����ݳɹ�");	
                Intent intent = new Intent();
                intent.putExtra("id", unforget.getId());
				intent.putExtra("year", unforget.getYear());
				intent.putExtra("month", unforget.getMonth());
				intent.putExtra("day", unforget.getDay());
				intent.putExtra("hour", unforget.getHour());
				intent.putExtra("minute", unforget.getMinute());
				intent.putExtra("second", unforget.getSecond());
				intent.putExtra("name", unforget.getName());
				intent.putExtra("soundFileName", unforget.getSoundFileName());
				
                intent.setClass(UnforgetActivity.this, UpdateUnforgetActivity.class);
                startActivityForResult(intent, 1);
            } 
        }); 
		
		gridViewUnforget.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				
				 final Unforget unforget = listUnforget.get(position);
				 
				AlertDialog.Builder builder = new AlertDialog.Builder(UnforgetActivity.this);
				
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("��ȷ��Ҫɾ����");
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						FileOper fileOper = new FileOper();
						File file = new File(pathName +"/"+ unforget.getSoundFileName());
						fileOper.deleteFile(file);
						bllUnforget.delete(unforget.getId());
					}
				});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(UnforgetActivity.this, "��ѡ����ȡ��" , Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show(); 
				
				return true;
			}
		});
	}
	
	private void showGridViewData(){
		listUnforget = new ArrayList<Unforget>();
		
		Cursor cursor = bllUnforget.query();
		if(cursor.getCount()!=0)
		{
			while(cursor.moveToNext()){
				int id;
				int year,month,day;
				int hour,minute,second;
				String name;
				String soundFileName;
				
				int idIndex = cursor.getColumnIndex("id");
	    		int yearIndex = cursor.getColumnIndex("year");
	    		int monthIndex = cursor.getColumnIndex("month");
	    		int dayIndex = cursor.getColumnIndex("day");
	    		int hourIndex = cursor.getColumnIndex("hour");
	    		int minuteIndex = cursor.getColumnIndex("minute");
	    		int secondIndex = cursor.getColumnIndex("second");
	    		int nameIndex = cursor.getColumnIndex("name");
	    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
	    		
	    		id = cursor.getInt(idIndex);
	    		year = cursor.getInt(yearIndex);
	    		month = cursor.getInt(monthIndex);
	    		day = cursor.getInt(dayIndex);
	    		hour = cursor.getInt(hourIndex);
	    		minute = cursor.getInt(minuteIndex);
	    		second = cursor.getInt(secondIndex);
	    		name = cursor.getString(nameIndex);
	    		soundFileName = cursor.getString(soundFileNameIndex);
	    		
	    		Log.i("tag", "��ʾ��"+id);
	    		
	    		Unforget modelUnforget = new Unforget(id,year, month, day, hour, minute, second, name, soundFileName);
	    		listUnforget.add(modelUnforget);
			}
			gridViewUnforget.setAdapter(new UnforgetAdapter(UnforgetActivity.this,listUnforget)); 
			
		}
		else{
			Log.i("tag", "û�б�������");
			TextView emptyView = new TextView(UnforgetActivity.this);  
            emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
            emptyView.setText("û�б��������������ӱ�������");  
            emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            emptyView.setVisibility(View.GONE);  
            ((ViewGroup)gridViewUnforget.getParent()).addView(emptyView);  
            gridViewUnforget.setEmptyView(emptyView);
		}
		
		
	}
	
	@Override 
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode,resultCode,data);   
		if(resultCode==1){  
			showGridViewData();
		}
	}  
	
	
}