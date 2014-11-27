package sqliteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{  
    private static final int VERSION = 1;  
    private static final String SWORD="SWORD";
    private static final String DBNAME = "shenghuobang.db";

    public DatabaseHelper(Context context) {  
        super(context, DBNAME, null, VERSION);  
          
    }  
    
    //�������ݿ�  
    public void onCreate(SQLiteDatabase db) {  
        Log.i(SWORD,"create a Database");  
        //�������ݿ�sql���  
        String sqlTableCharge = "Create Table tableCharge(id Integer Primary Key AutoIncrement,year Integer,month Integer,day Integer,sum Integer,type Boolen,des Text)";  
        String sqlTablePassword = "Create Table tablePassword(id Integer Primary Key AutoIncrement,name Text,passWord Text,soundFileName Text)";  
        String sqlTableUnforget = "Create Table tableUnforget(id Integer Primary Key AutoIncrement,year Integer,month Integer,day Integer,hour Integer,minute Integer,second Integer,name Text,soundFileName Text)";
        //ִ�д������ݿ����  
        db.execSQL(sqlTableCharge);  
        db.execSQL(sqlTablePassword);  
        db.execSQL(sqlTableUnforget);  
    }  
    
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        //�����ɹ�����־�����ʾ  
        Log.i(SWORD,"update a Database");  
    }  
    public boolean deleteDatabase(Context context) {  
        return context.deleteDatabase(DBNAME);
    }   
}