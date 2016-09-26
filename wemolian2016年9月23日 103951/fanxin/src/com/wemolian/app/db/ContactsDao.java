 
package com.wemolian.app.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;

@SuppressLint("DefaultLocale")
public class ContactsDao {
//	public static final String TABLE_NAME = "uers";
//	public static final String COLUMN_NAME_ID = "username";
//	public static final String COLUMN_NAME_NICK = "nick";
//    public static final String COLUMN_NAME_SEX = "sex";
//    public static final String COLUMN_NAME_AVATAR = "avatar";
//    public static final String COLUMN_NAME_SIGN = "sign";
//    public static final String COLUMN_NAME_TEL = "tel";
//    public static final String COLUMN_NAME_FXID = "fxid";
//    public static final String COLUMN_NAME_REGION = "region";
//    public static final String COLUMN_NAME_BEIZHU = "beizhu";
//	public static final String COLUMN_NAME_IS_STRANGER = "is_stranger";
//	int unreadMsgCount
//	String header
//	String externalUse
//	String  hxid
//	String userCName
//	int newsNotDis
//	String userRemark
//	int blackList
//	int developmentMe
//	String label
//	String headImg
//	String chatTop
//	public static final String TABLE_NAME = "contacts";
//	//type : friends  groups 等
//	public static final String COLUMN_NAME_TYPE = "type";
//	public static final String COLUMN_NAME_EXTERNALUSE = "externalUse";
//	public static final String COLUMN_NAME_HXID = "hxid";
//	public static final String COLUMN_NAME_USERCNAME = "userCName";
//	public static final String COLUMN_NAME_NEWSNOTDIS = "newsNotDis";
//	public static final String COLUMN_NAME_USERREMARK = "userRemark";
//	public static final String COLUMN_NAME_BLACKLIST = "blackList";
//	public static final String COLUMN_NAME_DEVELOPMENTME = "developmentMe";
//	public static final String COLUMN_NAME_LABEL = "label";
//	public static final String COLUMN_NAME_IMGNAME = "imgName";
//	public static final String COLUMN_NAME_HEADER = "header";
//	public static final String COLUMN_NAME_CHATTOP = "chatTop";

	private DbOpenHelper dbHelper;

	public ContactsDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<Contacts> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(LocalDBKey.CONTACTS_TABLE_NAME, null, null);
			for (Contacts contacts : contactList) {
				ContentValues values = new ContentValues();
				values.put(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE, contacts.getType());
				values.put(LocalDBKey.CONTACTS_COLUMN_NAME_BLACKLIST, contacts.getBlackList());
				values.put(LocalDBKey.CONTACTS_COLUMN_NAME_NEWSNOTDIS, contacts.getNewsNotDis());
				values.put(LocalDBKey.CONTACTS_COLUMN_NAME_AUTOGRAPH, contacts.getAutograph());
				values.put(LocalDBKey.CONTACTS_COLUMN_NAME_DEVELOPMENTME, contacts.getDevelopmentMe());
				if (contacts.getChatTop() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_CHATTOP, contacts.getChatTop());
				}
				if (contacts.getExternalUse() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE, contacts.getExternalUse());
				}
				if (contacts.getHeader() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_HEADER, contacts.getHeader());
				}
				if (contacts.getImgName() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, contacts.getImgName());
				}
				if (contacts.getHxid() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, contacts.getHxid());
				}
				if (contacts.getLabel() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL, contacts.getLabel());
				}
				if (contacts.getUserCName() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, contacts.getUserCName());
				}
				if (contacts.getUserRemark() != null) {
					values.put(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK, contacts.getUserRemark());
				}
				db.replace(LocalDBKey.CONTACTS_TABLE_NAME, null, values);
			}
		}
	}

	public Contacts getContact(Contacts user){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			/**
			 * [blackList, chatTop, developmentMe, header, img, hxid, label, newsNotDis, type, userCName, userRemark, externalUse]
			 */
			Cursor cursor = db.rawQuery("select * from " + LocalDBKey.CONTACTS_TABLE_NAME 
					+" where " +LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE 
					+ " = '" + user.getExternalUse() +"'" , null);
			if(cursor == null || cursor.getCount() == 0){
				return null;
			}
			cursor.moveToFirst();
			int blackList = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_BLACKLIST));
			String chatTop = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_CHATTOP));
			int developmentMe = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_DEVELOPMENTME));
			String externalUse = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE));
			String header = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_HEADER));
			String imgName = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME));
			String hxId = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_HXID));
			String label = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL));
			int newsNotDis = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_NEWSNOTDIS));
			String type = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE));
			String userCName = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME));
			String userRemark = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK));
			Contacts contacts = new Contacts();
			contacts.setBlackList(blackList);
			contacts.setChatTop(chatTop);
			contacts.setDevelopmentMe(developmentMe);
			contacts.setExternalUse(externalUse);
			contacts.setHeader(header);
			contacts.setImgName(imgName);
			contacts.setHxid(hxId);
			contacts.setNewsNotDis(newsNotDis);
			contacts.setType(type);
			contacts.setLabel(label);
			contacts.setUserCName(userCName);
			contacts.setUserRemark(userRemark);
			cursor.close();
			return contacts;
		}else{
			return null;
		}
		
	}
	/**
	 * 获取好友list
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public Map<String, Contacts> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, Contacts> contactsMap = new HashMap<String, Contacts>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + LocalDBKey.CONTACTS_TABLE_NAME /* + " desc" */, null);
			while (cursor.moveToNext()) {
				int blackList = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_BLACKLIST));
				String chatTop = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_CHATTOP));
				int developmentMe = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_DEVELOPMENTME));
				String externalUse = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE));
				String header = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_HEADER));
				String imgName = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME));
				String hxId = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_HXID));
				String label = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL));
				int newsNotDis = cursor.getInt(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_NEWSNOTDIS));
				String type = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE));
				String userCName = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME));
				String userRemark = cursor.getString(cursor.getColumnIndex(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK));
				Contacts contacts = new Contacts();
				contacts.setBlackList(blackList);
				contacts.setChatTop(chatTop);
				contacts.setDevelopmentMe(developmentMe);
				contacts.setExternalUse(externalUse);
				contacts.setHeader(header);
				contacts.setImgName(imgName);
				contacts.setHxid(hxId);
				contacts.setNewsNotDis(newsNotDis);
				contacts.setType(type);
				contacts.setLabel(label);
				contacts.setUserCName(userCName);
				contacts.setUserRemark(userRemark);
				String headerName = null;
				
				if(!TextUtils.isEmpty(contacts.getLabel())){
					headerName = contacts.getLabel();
				}else if(!TextUtils.isEmpty(contacts.getUserRemark())){
					headerName = contacts.getUserRemark();
				}else if(!TextUtils.isEmpty(contacts.getNick())){
					headerName = contacts.getNick();
				}
				else if (!TextUtils.isEmpty(contacts.getUserCName())) {
					headerName = contacts.getUserCName();
				} else {
					headerName = contacts.getUsername();
				}
				
				
				if (type.equals(Constant.NEW_FRIENDS_USERNAME) || type.equals(Constant.GROUP_USERNAME) || headerName == null) {
					contacts.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					contacts.setHeader("#");
				} else {
					contacts.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
							.get(0).target.substring(0, 1).toUpperCase());
					char headerI = contacts.getHeader().toLowerCase().charAt(0);
					if (headerI < 'a' || headerI > 'z') {
						contacts.setHeader("#");
					}
				}
				contactsMap.put(type, contacts);
			}
			cursor.close();
		}
		return contactsMap;
	}
	
	/**
	 * 删除一个联系人
	 * @param username
	 */
	public void deleteContact(String externalUse){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(LocalDBKey.CONTACTS_TABLE_NAME, LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE + " = ?", new String[]{externalUse});
		}
	}
	
	/**
	 * 保存一个联系人
	 * @param user
	 */
	public void saveContact(Contacts contacts){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE, contacts.getType());
		values.put(LocalDBKey.CONTACTS_COLUMN_NAME_BLACKLIST, contacts.getBlackList());
		values.put(LocalDBKey.CONTACTS_COLUMN_NAME_NEWSNOTDIS, contacts.getNewsNotDis());
		values.put(LocalDBKey.CONTACTS_COLUMN_NAME_DEVELOPMENTME, contacts.getDevelopmentMe());
		if (contacts.getChatTop() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_CHATTOP, contacts.getChatTop());
		}
		if (contacts.getExternalUse() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE, contacts.getExternalUse());
		}
		if (contacts.getHeader() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_HEADER, contacts.getHeader());
		}
		if (contacts.getImgName() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, contacts.getImgName());
		}
		if (contacts.getHxid() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, contacts.getHxid());
		}
		if (contacts.getLabel() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL, contacts.getLabel());
		}
		if (contacts.getUserCName() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, contacts.getUserCName());
		}
		if (contacts.getUserRemark() != null) {
			values.put(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK, contacts.getUserRemark());
		}
		if(db.isOpen()){
			db.replace(LocalDBKey.CONTACTS_TABLE_NAME, null, values);
		}
	}
}
