package com.gltype.nourriture.db.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gltype.nourriture.db.UserSQLiteOpenHelper;
import com.gltype.nourriture.model.User;

public class UserDao {
	private UserSQLiteOpenHelper dbHelper;
	public  UserDao(Context contex) {
		dbHelper = new UserSQLiteOpenHelper(contex);
	}
	
	public void add(String token,int role){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into user (token,role) values (?,?)", new Object[]{token,role});
		db.close();
	}
	
	public User find(){
		User user = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select token, role, id from user", null);
		if(cursor.moveToNext()){
			String token = cursor.getString(cursor.getColumnIndex("token"));
			int role = cursor.getInt(cursor.getColumnIndex("role"));
			
			user = new User(token,role);		
		}
		cursor.close();
		db.close();
		return user;
	}
	public void delete (String token){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from user where token = ?", new Object[]{token});
		db.close();
	}

}
