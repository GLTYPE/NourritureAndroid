package com.gltype.nourriture.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

	
	public UserSQLiteOpenHelper(Context contex){
		super(contex, "user.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
			String createTable = "create table user (id integer primary key autoincrement, token varchar(80), role varchar(5))";
			arg0.execSQL(createTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
