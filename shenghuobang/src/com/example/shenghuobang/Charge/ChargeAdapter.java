package com.example.shenghuobang.Charge;
import java.util.List;

import com.example.shenghuobang.R;


import sqliteDataBase.Bll.Charge;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ChargeAdapter extends BaseAdapter {
	private List<sqliteDataBase.Model.ChargeStatistic> arrays = null;
	private Context mContext;
	private Button curDel_btn;
	private float x,ux;
	public ChargeAdapter(Context mContext, List<sqliteDataBase.Model.ChargeStatistic> arrays) {
		this.mContext = mContext;
		this.arrays = arrays;
	}
	
	public int getCount() {
		return this.arrays.size();
	}
	public Object getItem(int position) {
		return null;
	}
	public void setArrarys(List<sqliteDataBase.Model.ChargeStatistic> arrays){		
		this.arrays = arrays;
	}
	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.view_list_view, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.lvContent);
			viewHolder.btnDel = (Button) view.findViewById(R.id.del);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		//为每一个view项设置触控监听
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag();
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {//当按下时处理
					v.setBackgroundResource(R.drawable.textview_press);
					x = event.getX();
					if (curDel_btn != null) {
						curDel_btn.setVisibility(View.GONE);
					}
				}else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
					//设置背景为未选中正常状态
					v.setBackgroundResource(R.drawable.textview_norm);
					//获取松开时的x坐标
					
					ux = event.getX();
					//判断当前项中按钮控件不为空时
					if ((holder.btnDel != null)&&(Math.abs(x - ux) > 20)) {
						holder.btnDel.setVisibility(View.VISIBLE);
						curDel_btn = holder.btnDel;
						return true;
					}
					
				}else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态
					v.setBackgroundResource(R.drawable.textview_press);
				} else {
					v.setBackgroundResource(R.drawable.textview_norm);
					
				}
				return false;
			}
			
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if ((curDel_btn != null)&&curDel_btn.getVisibility()==View.VISIBLE) {
					curDel_btn.setVisibility(View.GONE);
					return;
				}
				
				
				sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = arrays.get(position);
				//Toast.makeText(getApplicationContext(), "year", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("isUpdate", true);
				intent.putExtra("year", modelChargeStatistic.getYear());
				intent.putExtra("month", modelChargeStatistic.getMonth());
				intent.putExtra("day", modelChargeStatistic.getDay());
		        intent.setClass(mContext, AddChargeActivity.class);
		        
		        mContext.startActivity(intent);
			}
		});
		
		//为删除按钮添加监听事件，实现点击删除按钮时删除该项
		viewHolder.btnDel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(curDel_btn!=null)
					curDel_btn.setVisibility(View.GONE);
				sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = arrays.get(position);
				sqliteDataBase.Bll.Charge bllCharge = new Charge(mContext);
				
				bllCharge.delete(modelChargeStatistic.getYear(),modelChargeStatistic.getMonth(),modelChargeStatistic.getDay());
				
				arrays.remove(position);
				
				notifyDataSetChanged();
			}
		});
				
		sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = this.arrays.get(position);
		viewHolder.tvTitle.setText("支出："+modelChargeStatistic.getOutSum()+"    收入："+modelChargeStatistic.getInSum()+"  时间："+modelChargeStatistic.getDataStr());
		return view;
	}
	final static class ViewHolder {
		TextView tvTitle;
		Button btnDel;
	}
}