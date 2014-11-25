package sqliteDataBase.Bll;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import sqliteDataBase.DatabaseHelper;

public class Charge {
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public Charge(Context context){
		
		helper = new DatabaseHelper(context);
		//helper.deleteDatabase(context);
		
        db = helper.getWritableDatabase();
	}
	
	public int insert(sqliteDataBase.Model.Charge modelCharge){
		ContentValues values = new ContentValues(); 
		values.put("year", modelCharge.getYear());
		values.put("month", modelCharge.getMonth());
		values.put("day", modelCharge.getDay());
		values.put("sum", modelCharge.getSum());
		values.put("type", modelCharge.getType());
		values.put("des", modelCharge.getDes());
		
		db.insert("tableCharge", null, values); 
		return 0;
	}
	
	public int update(sqliteDataBase.Model.Charge modelCharge){
		db.execSQL("update tableCharge set sum=?,type=?,des=? where year=? and month=? and day=?",
                new Object[] { modelCharge.getSum(),modelCharge.getType(),modelCharge.getDes(),modelCharge.getYear(),modelCharge.getMonth(),modelCharge.getDay()});
		return 0;
	}
	public Cursor query()
    {
        Cursor c=db.query("tableCharge", null, null, null, null, null, null);
        return c;
    }
	public Cursor queryByMonth(int year, int month)
    {
		String strYear = String.valueOf(year);
		String strMonth = String.valueOf(month);
		Cursor c=db.rawQuery("select * from tableCharge where year=? and month=?",new String[]{strYear,strMonth});
        return c;
    }
	

	public Cursor queryByDay(int year, int month, int day) {
		String strYear = String.valueOf(year);
		String strMonth = String.valueOf(month);
		String strDay = String.valueOf(day);
		Cursor c=db.rawQuery("select * from tableCharge where year=? and month=? and day=?",new String[]{strYear,strMonth,strDay});
        return c;
	}
	public int delete(int id){
		db.delete("tableCharge", "id=?",new String[]{String.valueOf(id)});
		return 0;
	}
}
