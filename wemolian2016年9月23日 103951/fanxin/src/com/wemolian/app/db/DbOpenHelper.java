/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wemolian.app.db;

import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.others.TopUserDao;
import com.wemolian.applib.controller.HXSDKHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static DbOpenHelper instance;
	 
	private static final String TOPUSER_TABLE_CREATE = "CREATE TABLE "
            + TopUserDao.TABLE_NAME + " ("
            + TopUserDao.COLUMN_NAME_TIME +" TEXT, "
            + TopUserDao.COLUMN_NAME_IS_GOUP +" TEXT, "
            + TopUserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

	private static final String CONTACTS_TABLE_CREATE = "CREATE TABLE "
	        + LocalDBKey.CONTACTS_TABLE_NAME + " ("
            + LocalDBKey.CONTACTS_COLUMN_NAME_BLACKLIST +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_CHATTOP +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_DEVELOPMENTME +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_HEADER +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_HXID +" TEXT, "
            + LocalDBKey.CONTACTS_COLUMN_NAME_LABEL +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_NEWSNOTDIS +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_TYPE +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_AUTOGRAPH +" TEXT, "            
            + LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE + " TEXT PRIMARY KEY);";
	
	private static final String USER_TABLE_CREATE = "CREATE TABLE "
			+ LocalDBKey.USER_TABLE_NAME + " ("
			+ LocalDBKey.USER_COLUMN_NAME_ADDRESSPOINT +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_BANKCARD +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_EMAIL +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_HEADIMG +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_HXPASSWORD +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_HXUSERID +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_ID +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_INTTIMESTAMP +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_ISONLINE +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_OLDPASSWORD +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_PASSWORD +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_PERMISSION +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_PHONENUM +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_QRCODE +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_RANDOMNUM +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_REGISTDATE +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_STATE +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_USERADDRESS +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_USERCNAME +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_USERENAME +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_USERREGION +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_USERSEX +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_VIP +" TEXT, "
			+ LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE + " TEXT PRIMARY KEY);";
	
	private static final String GROUP_TABLE_CREATE = "CREATE TABLE "
			+ LocalDBKey.GROUP_TABLE_NAME + " ("
			+ LocalDBKey.GROUP_COLUMN_NAME_CNAME +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_ENAME +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_HXID +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_MEMBERS +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_QROUPCODE +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_TOP +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_OWNER +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS +" TEXT, "            
			+ LocalDBKey.GROUP_COLUMN_NAME_ID + " TEXT PRIMARY KEY);";
    
	
	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessgeDao.TABLE_NAME + " ("
			+ InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";
			
			
	
	private DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
        return  HXSDKHelper.getInstance().getHXId() + "_glufine.db";
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CONTACTS_TABLE_CREATE);
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
		db.execSQL(TOPUSER_TABLE_CREATE);
		db.execSQL(GROUP_TABLE_CREATE);
		db.execSQL(USER_TABLE_CREATE);
		 
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}
	
}
