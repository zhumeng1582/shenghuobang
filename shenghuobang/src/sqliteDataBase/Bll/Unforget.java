package sqliteDataBase.Bll;

import sqliteDataBase.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Unforget {
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public Unforget(Context context){
		helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
	}
	
	public int insert(sqliteDataBase.Model.Unforget modelUnforget){
		ContentValues values = new ContentValues(); 
		values.put("year", modelUnforget.getYear());
		values.put("month",modelUnforget.getMonth());
		values.put("day", modelUnforget.getDay());
		values.put("hour", modelUnforget.getHour());
		values.put("minute", modelUnforget.getMinute());
		values.put("second", modelUnforget.getSecond());
		values.put("name", modelUnforget.getName());
		values.put("soundFileName", modelUnforget.getSoundFileName());
		
		db.insert("tableUnforget", null, values); 
		return 0;
	}
	
	public int update(sqliteDataBase.Model.Unforget modelUnforget){
		db.execSQL("update tableUnforget set year=?,month=?,day=?,hour=?,minute=?,second=?,name=?,soundFileName=? where id=?;",
                new Object[] {modelUnforget.getYear(),modelUnforget.getMonth(),modelUnforget.getDay(),modelUnforget.getHour(),modelUnforget.getMinute(),0,modelUnforget.getName(),modelUnforget.getSoundFileName(),modelUnforget.getId() });
		return 0;
	}
	public Cursor query()
    {
        Cursor c=db.query("tableUnforget", null, null, null, null, null, "year asc,month asc,day asc,hour asc,minute asc");
        return c;
    }

	
	public int delete(int id){
		Log.i("tag", "ɾ�����ݣ�"+id);
		db.delete("tableUnforget", "id=?",new String[]{String.valueOf(id)});
		return 0;
	}
	public int getMaxId(){
		int maxid=0;
		Cursor cr = db.rawQuery("select last_insert_rowid() from tableUnforget;", null);  
		if(cr.moveToFirst())  
			maxid  = cr.getInt(0); 
		return maxid;
	}
	public int getCount(){
		int count=0;
		Cursor c = db.rawQuery("select count(*) from tableUnforget ",null); 
		if(c.moveToNext()){
			count = c.getInt(0);
		}
		return count;
		   
	}
}
