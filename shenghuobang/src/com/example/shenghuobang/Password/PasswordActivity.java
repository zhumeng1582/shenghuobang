package com.example.shenghuobang.Password;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sqliteDataBase.Model.Password;
import sqliteDataBase.Model.Unforget;

import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;
import com.example.shenghuobang.Unforget.UnforgetActivity;
import com.example.shenghuobang.Unforget.UnforgetAdapter;
import com.example.shenghuobang.Unforget.UpdateUnforgetActivity;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PasswordActivity extends Activity {
	
	private Button btnAddPassword;
	
	private GridView gridViewPassword;
	
	private sqliteDataBase.Bll.Password bllPassword;
	private List<sqliteDataBase.Model.Password> listPassword;
	
	private String pathName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		bllPassword = new sqliteDataBase.Bll.Password(this);
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		
		gridViewPassword = (GridView) findViewById(R.id.gridViewPassword);
		showGridViewData();
		
		gridViewPassword.setOnItemClickListener(new OnItemClickListener() { 
			
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
            	
            	Log.i("tag", "��ť����"); 
            	
                Password password = listPassword.get(position);
                 
                
                Log.i("tag", "�����ݳɹ�");	

				Intent intent = new Intent();
				intent .putExtra("id", password.getId());
				intent .putExtra("name", password.getName());
				intent .putExtra("passWord", password.getPassWord());
				intent.putExtra("soundFileName", password.getSoundFileName());
				
                intent.setClass(PasswordActivity.this, UpdatePasswordActivity.class);
                startActivityForResult(intent, 1);
            } 
        }); 
		
		gridViewPassword.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				
				 final Password password = listPassword.get(position);
				 
				AlertDialog.Builder builder = new AlertDialog.Builder(PasswordActivity.this);
				
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("��ȷ��Ҫɾ����");
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						FileOper fileOper = new FileOper();
						File file = new File(pathName +"/"+ password.getSoundFileName());
						fileOper.deleteFile(file);
						bllPassword.delete(password.getId());
					}
				});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(PasswordActivity.this, "��ѡ����ȡ��" , Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show(); 
				
				return true;
			}
		});
		
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
	
	private void showGridViewData(){
		listPassword = new ArrayList<sqliteDataBase.Model.Password>();
		
		Cursor cursor = bllPassword.query();
		if(cursor.getCount()!=0)
		{
			Log.i("tag", "�������ݣ�"+cursor.getCount());
			while(cursor.moveToNext()){
				int id;
				String name;
				String password;
				String soundFileName;
				
				int idIndex =  cursor.getColumnIndex("id");
	    		int nameIndex = cursor.getColumnIndex("name");
	    		int passwordIndex = cursor.getColumnIndex("passWord");
	    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
	    		
	    		id = cursor.getInt(idIndex);
	    		name = cursor.getString(nameIndex);
	    		password = cursor.getString(passwordIndex);
	    		soundFileName = cursor.getString(soundFileNameIndex);

	    		Log.i("tag", "������"+name);
	    		sqliteDataBase.Model.Password modelPassword = new Password(id,name, password, soundFileName);
	    		listPassword.add(modelPassword);
			}
			gridViewPassword.setAdapter(new PasswordAdapter(PasswordActivity.this,listPassword)); 
			
		}
		else{
			Log.i("tag", "û����������");
			TextView emptyView = new TextView(PasswordActivity.this);  
            emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
            emptyView.setText("û���������ݣ�����������������");  
            emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            emptyView.setVisibility(View.GONE);  
            ((ViewGroup)gridViewPassword.getParent()).addView(emptyView);  
            gridViewPassword.setEmptyView(emptyView);
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