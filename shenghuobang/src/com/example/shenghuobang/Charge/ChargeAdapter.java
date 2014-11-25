package com.example.shenghuobang.Charge;
import java.util.List;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Bll.Charge;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
		sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = this.arrays.get(position);
		viewHolder.tvTitle.setText("支出："+modelChargeStatistic.getOutSum()+"    收入："+modelChargeStatistic.getInSum()+"  时间："+modelChargeStatistic.getDataStr());
		return view;
	}
	final static class ViewHolder {
		TextView tvTitle;
		Button btnDel;
	}
}