package com.wemolian.app.db;

import java.text.SimpleDateFormat;
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
import com.wemolian.app.R.string;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;

/**
 * groups dao接口
 * @author zhangyun
 *
 */
public class GroupsDao {

	private DbOpenHelper dbHelper;

	public GroupsDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存用户群信息
	 * 
	 * @param user
	 */
	@SuppressLint("SimpleDateFormat")
	public void saveGroups(List<Groups> groups) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(LocalDBKey.GROUP_TABLE_NAME, null, null);
			for (Groups group : groups) {
				ContentValues values = new ContentValues();
				values.put(LocalDBKey.GROUP_COLUMN_NAME_CNAME, group.getGroupcname());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_ENAME, group.getGroupename());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS, group.getGroupmembers());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_QROUPCODE, group.getGroupqrcode());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_TOP, group.getTop());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS, group.getGroupUserHeadImgs());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_HXID, group.getGroupHxId());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_ID, group.getGroupId());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_OWNER, group.getOwner());
				values.put(LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS, group.getNewNotDis());
				db.replace(LocalDBKey.GROUP_TABLE_NAME, null, values);
			}
			
		}
	}

	/**
	 * 获取用户群信息列表
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public Map<String, Groups> getGroupList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, Groups> groupsMap = new HashMap<String, Groups>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + LocalDBKey.GROUP_TABLE_NAME /* + " desc" */, null);
			while (cursor.moveToNext()) {
				String cname = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_CNAME));
				String ename = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_ENAME));
				String members = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS));
				String qrcode = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_QROUPCODE));
				int top = cursor.getInt(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_TOP));
				String userHeadImgs = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS));
				String hxid = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_HXID));
				String id = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_ID));
				String owner = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_OWNER));
				String newNotDis = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS));
				
				Groups groups = new Groups();
				groups.setGroupcname(cname);
				groups.setGroupename(ename);
				groups.setGroupmembers(members);
				groups.setGroupqrcode(qrcode);
				groups.setGroupUserHeadImgs(userHeadImgs);
				groups.setTop(top);
				groups.setGroupHxId(hxid);
				groups.setGroupId(id);
				groups.setOwner(owner);
				groups.setNewNotDis(newNotDis);
				
				if ((Constant.GROUP_USERNAME).equals(ename)) {
					groups.setHeader("");
				} else if (Character.isDigit(cname.charAt(0))) {
					groups.setHeader("#");
				} else {
					groups.setHeader(HanziToPinyin.getInstance().get(cname.substring(0, 1))
							.get(0).target.substring(0, 1).toUpperCase());
					char headerI = groups.getHeader().toLowerCase().charAt(0);
					if (headerI < 'a' || headerI > 'z') {
						groups.setHeader("#");
					}
				}
				groupsMap.put(ename, groups);
			}
			cursor.close();
			db.close();
		}
		return groupsMap;
	}
	
	
	/**
	 * 获取单个群信息
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public Groups getGroup(String groupId) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Groups groups = new Groups();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + LocalDBKey.GROUP_TABLE_NAME + " where " + LocalDBKey.GROUP_COLUMN_NAME_ID + " = '" + groupId + "'" , null);
			while (cursor.moveToNext()) {
				String cname = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_CNAME));
				String ename = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_ENAME));
				String members = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS));
				String qrcode = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_QROUPCODE));
				int top = cursor.getInt(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_TOP));
				String userHeadImgs = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS));
				String hxid = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_HXID));
				String id = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_ID));
				String owner = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_OWNER));
				String newNotDis = cursor.getString(cursor.getColumnIndex(LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS));
				
				
				groups.setGroupcname(cname);
				groups.setGroupename(ename);
				groups.setGroupmembers(members);
				groups.setGroupqrcode(qrcode);
				groups.setGroupUserHeadImgs(userHeadImgs);
				groups.setTop(top);
				groups.setGroupHxId(hxid);
				groups.setGroupId(id);
				groups.setOwner(owner);
				groups.setNewNotDis(newNotDis);
				
			}
			cursor.close();
		}
		return groups;
	}

	
	/**
	 * 保存一个群组
	 */
	public void saveGroup(Groups group){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LocalDBKey.GROUP_COLUMN_NAME_CNAME, group.getGroupcname());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_ENAME, group.getGroupename());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_HXID, group.getGroupHxId());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_ID, group.getGroupId());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS, group.getGroupmembers());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_OWNER, group.getOwner());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_QROUPCODE, group.getGroupqrcode());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_TOP, group.getTop());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS, group.getGroupUserHeadImgs());
		values.put(LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS, group.getNewNotDis());
		
		if(db.isOpen()){
			db.replace(LocalDBKey.GROUP_TABLE_NAME, null, values);
		}
	}
	
	/**
	 * 更新群组信息  --> 更新群组群成员
	 * @param group
	 */
	public void saveMembersToGroup(Groups group){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, Groups> groupsMap = new HashMap<String, Groups>();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			//db.update("tablename",values,"name=? AND age=?",new Object{"xiadong", 20});
			if(group != null){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS, group.getGroupmembers());
					values.put(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS, group.getGroupUserHeadImgs());
			}
			
			db.update(LocalDBKey.GROUP_TABLE_NAME, values, 
					LocalDBKey.GROUP_COLUMN_NAME_ID + "='" + group.getGroupId() +"'", null);
		}
	}
	
	/**
	 * 更新群信息
	 * @param group
	 */
	public void updateGroup(Groups group){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			if(group != null){
				if(group.getGroupcname() != null){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_CNAME, group.getGroupcname());
				}
				if(group.getNewNotDis() != null){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_NEWNOTDIS, group.getNewNotDis());
				}
				if(group.getTop() != 0){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_TOP, group.getTop());
				}
				if(group.getGroupmembers() != null){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_MEMBERS, group.getGroupmembers());
				}
				if(group.getGroupUserHeadImgs() != null){
					values.put(LocalDBKey.GROUP_COLUMN_NAME_USERHEADIMGS, group.getGroupUserHeadImgs());
				}
			}
			
			db.update(LocalDBKey.GROUP_TABLE_NAME, values, 
					LocalDBKey.GROUP_COLUMN_NAME_ID + "='" + group.getGroupId() +"'", null);
		}
	}
	
	
	/**
	 * 删除群组--所有
	 * 
	 * @param username
	 */
	public void deleteGroupsList(String str) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(LocalDBKey.GROUP_TABLE_NAME, null,null);
		}
	}

	/**
	 * 根据groupId删除群
	 * @param groupId
	 */
	public void delGroup(String groupId){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String[] args = {groupId};
		db.beginTransaction();
		try {
		db.delete(LocalDBKey.GROUP_TABLE_NAME, LocalDBKey.GROUP_COLUMN_NAME_ID + "=?", args);
		db.setTransactionSuccessful();// 设置事务标志为成功,在事务结束时才会提供事务,否则回滚事务
		} catch (Exception e) {
		      e.printStackTrace();
		} finally {
		      //如果没有成功回滚事务
		      db.endTransaction();
		}
		db.close();
	}

	
}
