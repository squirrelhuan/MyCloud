package service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class dbopenhelper extends SQLiteOpenHelper {

	public dbopenhelper(Context context) {
		super(context, "user.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
       db.execSQL("CREATE TABLE user(userid integer primary key autoincrement,name varchar(20)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE user ADD name VARCHAR(12) NULL");
	}

}
