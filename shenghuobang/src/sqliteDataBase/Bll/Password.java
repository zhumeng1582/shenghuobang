package sqliteDataBase.Bll;

import sqliteDataBase.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Password {
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public Password(Context context){
		helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
	}
	public int insert(sqliteDataBase.Model.Password modelPassword){
		ContentValues values = new ContentValues(); 
		values.put("name", modelPassword.getName());
		values.put("passWord",modelPassword.getPassWord());
		values.put("soundFileName", modelPassword.getSoundFileName());
		
		
		db.insert("tablePassword", null, values); 
		return 0;
	}
	public int update(sqliteDataBase.Model.Password modelPassword){
		db.execSQL("update tablePassword set name=?,passWord=?,soundFileName=? where id=?;",
                new Object[] {modelPassword.getName(),modelPassword.getPassWord(),modelPassword.getSoundFileName(),modelPassword.getId()});
		return 0;
	}
	public Cursor query()
    {
        Cursor c=db.query("tablePassword", null, null, null, null, null, null);
        return c;
    }

	
	public int delete(int id){
		Log.i("tag", "ɾ�����ݣ�"+id);
		db.delete("tablePassword", "id=?",new String[]{String.valueOf(id)});
		return 0;
	}
	public int getMaxId(){
		int maxid=0;
		Cursor cr = db.rawQuery("select last_insert_rowid() from tablePassword;", null);  
		if(cr.moveToFirst())  
			maxid  = cr.getInt(0); 
		return maxid;
	}

}