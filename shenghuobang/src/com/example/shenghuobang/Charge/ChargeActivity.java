package com.example.shenghuobang.Charge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shenghuobang.MonPickerDialog;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Charge;
import sqliteDataBase.Model.ChargeStatistic;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChargeActivity extends ListActivity {
	
	private ImageView imageAddCharge;
	private TextView tvInMonthSum;
	private TextView tvOutMonthSum;
	private TextView tvYear;
	private TextView tvMonth;
	
	private LinearLayout llTimeSelector;
	
	private ChargeAdapter adapter;
	private ArrayAdapter arrayAdapter;
	
	private sqliteDataBase.Bll.Charge bllCharge;
 
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge);

		bllCharge = new sqliteDataBase.Bll.Charge(ChargeActivity.this);
		
		llTimeSelector = (LinearLayout) findViewById(R.id.llTimeSelector);
		
		tvInMonthSum = (TextView) findViewById(R.id.tvInMonthSum);
		tvOutMonthSum = (TextView) findViewById(R.id.tvOutMonthSum);

		Date curDate = new Date(System.currentTimeMillis());//获取当前时间    
		
		imageAddCharge = (ImageView) findViewById(R.id.imageAddCharge);
		
		
		tvYear = (TextView) findViewById(R.id.tvYear);
		int year = curDate.getYear()+1900;
		
		tvYear.setText(String.valueOf(year)+"年");
		
		tvMonth = (TextView) findViewById(R.id.tvMonth);
		int month = curDate.getMonth()+1;
		tvMonth.setText(String.valueOf(month)+"月");

		imageAddCharge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                intent.setClass(ChargeActivity.this, AddChargeActivity.class);
                startActivityForResult(intent, 1);

			}
		});
		
		llTimeSelector.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				final Calendar localCalendar = Calendar.getInstance();
				new MonPickerDialog(ChargeActivity.this,dateListener, localCalendar.get(1), localCalendar.get(2),localCalendar.get(5)).show();
			}
		});
		
		
		
		setListViewData();
	}
	DatePickerDialog.OnDateSetListener dateListener =  new DatePickerDialog.OnDateSetListener() { 
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
			
			tvYear.setText(String.valueOf(year)+"年");
			tvMonth.setText(String.format("%02d",month+1)+"月");
			setListViewData();
		}     
	}; 

	private void setListViewData(){
		
		String strYear = tvYear.getText().toString().substring(0, 4);
		int intYear;
		intYear=Integer.parseInt(strYear);
		
		String strMonth = tvMonth.getText().toString().substring(0, 2);
		int intMonth;
		intMonth=Integer.parseInt(strMonth);
		
		Cursor cursor = bllCharge.queryByMonth(intYear,intMonth);
		
		if(cursor.getCount()==0){
			arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getNullData());
			setListAdapter(arrayAdapter);
		}
		else{
			adapter = new ChargeAdapter(this, getData(intYear,intMonth));
			setListAdapter(adapter);
		}
	}
	private void updateListViewData(){
		setListViewData();
	}
	
	private List<ChargeStatistic> getData(int year,int month) {
		
		
		List<ChargeStatistic> list = new ArrayList<ChargeStatistic>();
		int inMonthSum=0;
		int outMonthSum=0;
		for(int day=1;day<=31;day++){

			Cursor cursor = bllCharge.queryByDay(year, month, day);
			if(cursor.getCount()==0)
				continue;
			
			int inDaySum=0;
			int outDaySum=0;
			while(cursor.moveToNext()){
	    		int typeIndex = cursor.getColumnIndex("type");
	    		int sumIndex = cursor.getColumnIndex("sum");
	    		int sum = cursor.getInt(sumIndex);
	    		int type = cursor.getInt(typeIndex);
	    		
	    		if(type==0){
	    			inDaySum += sum;
	    		}
	    		else{
	    			outDaySum +=sum;
	    		}
	    	}
			inMonthSum += inDaySum;
			outMonthSum += outDaySum;
			list.add(new ChargeStatistic(year, month, day, inDaySum, outDaySum));
    		cursor.close();
		}
		tvInMonthSum.setText("收入："+ inMonthSum);
		
		tvOutMonthSum.setText("支出："+ outMonthSum);
		return list;
	}
	private List<String> getNullData(){
		List<String> list = new ArrayList<String>();
		list.add("没有记录");
		return list;
	}

	@Override   
    protected void onListItemClick(ListView l, View v, int position, long id) {  
		super.onListItemClick(l, v, position, id);  
    }  
	
	@Override 
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode,resultCode,data);   
		if(resultCode==1){  
			updateListViewData();
			//Toast.makeText(getApplicationContext(), "由添加界面返回", Toast.LENGTH_SHORT).show();
		}
	}  
}