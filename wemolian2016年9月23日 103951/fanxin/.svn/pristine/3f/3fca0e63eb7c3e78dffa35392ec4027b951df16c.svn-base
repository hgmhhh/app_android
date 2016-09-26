package com.wemolian.app.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easemob.util.HanziToPinyin;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * 用户信息dao接口
 * 
 * @author Administrator
 *
 */
@SuppressLint("DefaultLocale")
public class UserDao {

//	public static final String TABLE_NAME = "user";
//	public static final String COLUMN_NAME_USERENAME = "userEName";
//	public static final String COLUMN_NAME_USERCNAME = "userCName";
//	public static final String COLUMN_NAME_HXUSERID = "hxuserId";
//	public static final String COLUMN_NAME_HXPASSWORD = "hxpassword";
//	public static final String COLUMN_NAME_USERADDRESS = "userAddress";
//	public static final String COLUMN_NAME_USERSEX = "userSex";
//	public static final String COLUMN_NAME_USERREGION = "userRegion";
//	public static final String COLUMN_NAME_BANKCARD = "bankCard";
//	public static final String COLUMN_NAME_REGISTDATE = "registDate";
//	public static final String COLUMN_NAME_PHONENUM = "phoneNum";
//	public static final String COLUMN_NAME_PASSWORD = "password";
//	public static final String COLUMN_NAME_OLDPASSWORD = "oldPassword";
//	public static final String COLUMN_NAME_EXTERNALUSE = "externalUse";
//	public static final String COLUMN_NAME_ADDRESSPOINT = "addressPoint";
//	public static final String COLUMN_NAME_ISONLINE = "isOnline";
//	public static final String COLUMN_NAME_STATE = "state";
//	public static final String COLUMN_NAME_PERMISSION = "permission";
//	public static final String COLUMN_NAME_ID = "id";
//	public static final String COLUMN_NAME_HEADIMG = "headImg";
//	public static final String COLUMN_NAME_QRCODE = "qrcode";
//	public static final String COLUMN_NAME_EMAIL = "email";
//	public static final String COLUMN_NAME_AUTOGRAPH = "autograph";
//	public static final String COLUMN_NAME_VIP = "vip";
//	public static final String COLUMN_NAME_INTTIMESTAMP = "intTimeStamp";
//	public static final String COLUMN_NAME_RANDOMNUM = "randomNum";

	private DbOpenHelper dbHelper;

	public UserDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	@SuppressLint("SimpleDateFormat")
	public void saveUser(User user) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(LocalDBKey.USER_TABLE_NAME, null, null);
			ContentValues values = new ContentValues();
			
			values.put(LocalDBKey.USER_COLUMN_NAME_ID, user.getId());
			values.put(LocalDBKey.USER_COLUMN_NAME_USERENAME, user.getUserEName());
			values.put(LocalDBKey.USER_COLUMN_NAME_USERCNAME, user.getUserCName());
			values.put(LocalDBKey.USER_COLUMN_NAME_HXUSERID, user.getHxuserId());
			values.put(LocalDBKey.USER_COLUMN_NAME_HXPASSWORD, user.getHxpassword());
			values.put(LocalDBKey.USER_COLUMN_NAME_USERADDRESS, user.getUserAddress());
			values.put(LocalDBKey.USER_COLUMN_NAME_USERSEX, user.getUserSex());
			values.put(LocalDBKey.USER_COLUMN_NAME_USERREGION, user.getUserRegion());
			values.put(LocalDBKey.USER_COLUMN_NAME_BANKCARD, user.getBankCard());
			values.put(LocalDBKey.USER_COLUMN_NAME_REGISTDATE,user.getRegistDate());
			values.put(LocalDBKey.USER_COLUMN_NAME_PHONENUM, user.getPhoneNum());
			values.put(LocalDBKey.USER_COLUMN_NAME_PASSWORD, user.getPassword());
			values.put(LocalDBKey.USER_COLUMN_NAME_OLDPASSWORD, user.getOldPassword());
			values.put(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE, user.getExternalUse());
			values.put(LocalDBKey.USER_COLUMN_NAME_ADDRESSPOINT, user.getAddressPoint());
			values.put(LocalDBKey.USER_COLUMN_NAME_ISONLINE, user.getIsOnline());
			values.put(LocalDBKey.USER_COLUMN_NAME_STATE, user.getState());
			values.put(LocalDBKey.USER_COLUMN_NAME_PERMISSION, user.getPermission());
			values.put(LocalDBKey.USER_COLUMN_NAME_HEADIMG, user.getHeadImg());
			values.put(LocalDBKey.USER_COLUMN_NAME_QRCODE, user.getQrcode());
			values.put(LocalDBKey.USER_COLUMN_NAME_EMAIL, user.getEmail());
			values.put(LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH, user.getAutograph());
			values.put(LocalDBKey.USER_COLUMN_NAME_VIP, user.getVip());
			values.put(LocalDBKey.USER_COLUMN_NAME_INTTIMESTAMP, "");
			values.put(LocalDBKey.USER_COLUMN_NAME_RANDOMNUM, "");
			db.replace(LocalDBKey.USER_TABLE_NAME, null, values);
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public User getUser(String extUse) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> userMap = new HashMap<String, User>();
		User user = new User();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select * from " + LocalDBKey.USER_TABLE_NAME +" where " + LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE + "='" + extUse + "'", null);
			if (cursor.moveToFirst()) {
				int id             = cursor.getInt(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_ID));
				String userEName   = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_USERENAME));
				String userCName   = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_USERCNAME));
				String hxuserId    = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_HXUSERID));
				String hxpassword  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_HXPASSWORD));
				String userAddress = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_USERADDRESS));
				String userSex     = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_USERSEX));
				String userRegion  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_USERREGION));
				String bankCard    = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_BANKCARD));
				String registDate    = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_REGISTDATE));
				String phoneNum       = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_PHONENUM));
				String password     = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_PASSWORD));
				String oldPassword  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_OLDPASSWORD));
				String externalUse  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE));
				String addressPoint= cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_ADDRESSPOINT));
				int isOnline       = cursor.getInt(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_ISONLINE));
				int state          = cursor.getInt(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_STATE));
				String permission  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_PERMISSION));
				String headImg     = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_HEADIMG));
				String qrcode      = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_QRCODE));
				String email       = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_EMAIL));
				String autograph   = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH));
				String vip         = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_VIP));
				String intTimeStamp  = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_INTTIMESTAMP));
				String randomNum   = cursor.getString(cursor.getColumnIndex(LocalDBKey.USER_COLUMN_NAME_RANDOMNUM));
				
				user.setId(id);
				user.setUserEName(userEName);
				user.setUserCName(userCName);
				user.setHxuserId(hxuserId);
				user.setHxpassword(hxpassword);
				user.setUserAddress(userAddress);
				user.setUserSex(userSex);
				user.setUserRegion(userRegion);
				user.setBankCard(bankCard);
				user.setPhoneNum(phoneNum);
				user.setPassword(oldPassword);
				user.setOldPassword(oldPassword);
				user.setExternalUse(externalUse);
				user.setAddressPoint(addressPoint);
				user.setIsOnline(isOnline);
				user.setState(state);
				user.setPermission(permission);
				user.setHeadImg(headImg);
				user.setQrcode(qrcode);
				user.setEmail(email);
				user.setAutograph(autograph);
				user.setVip(vip);
				
				String headerName = null;

				if (!TextUtils.isEmpty(user.getUserEName())) {
					headerName = user.getUserEName();
				} else if (!TextUtils.isEmpty(user.getUserCName())) {
					headerName = user.getUserCName();
				} else {
					headerName = user.getUserEName();
				}

//				userMap.put(userEName, user);
			}
			cursor.close();
		}
		return user;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param username
	 */
	public void deleteUser(String userExt) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(LocalDBKey.USER_TABLE_NAME, null,null);
		}
	}


}
