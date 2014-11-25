package sqliteDataBase.Model;

import java.util.Date;

public class ChargeStatistic {
	private int id;
	private int year, month, day;
	private int inSum;
	private int outSum;
	
	public ChargeStatistic(int id,int year,int month,int day,int inSum,int outSum){
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.inSum = inSum;
		this.outSum = outSum;
	}
	
	public ChargeStatistic(int year,int month,int day,int inSum,int outSum){
		this.year = year;
		this.month = month;
		this.day = day;
		 this.inSum = inSum;
		 this.outSum = outSum;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
//	public Date getDate(){
//		return date;
//	}
//	public void setDate(Date date){
//		this.date = date;
//	}
	public String getDataStr(){
		return String.format("%04d", year)+"��"+String.format("%02d", month)+"��"+String.format("%02d", day)+"��";
	}
	public int getInSum(){
		return inSum;
	}
	public void setInSum(int inSum){
		this.inSum = inSum;
	}
	public int getOutSum(){
		return outSum;
	}
	public void setOutSum(int outSum){
		this.outSum = outSum;
	}
}
