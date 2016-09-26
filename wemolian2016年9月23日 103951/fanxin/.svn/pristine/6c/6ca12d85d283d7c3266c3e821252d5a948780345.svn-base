package com.wemolian.app.wml.friends;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.InviteMessgeDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.InviteMessage;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadLocalContactsAdapter;
import com.wemolian.app.wml.others.NewFriendsAdapter;

/**
 * 
 * 加载本地联系人的活动界面
 * @author zhangyun
 *
 */
public class LocalContactsActivity extends BaseActivity {

	LoadLocalContactsAdapter loadLocalContactsAdapter;
	ListView listView;
	JSONArray contactData;
	JSONObject jsonObject;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_phone_contacts);
		setStatus(findViewById(R.id.title));
		listView = (ListView) findViewById(R.id.listview);
		loadData();
	}
	
	
	
	/**
	 * 获取本地联系人列表 --> 电话联系人列表
	 * 
	 * @return
	 */
	public void loadData() {

		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI

		// contactData = new JSONObject();
		contactData = new JSONArray();
		String mimetype = "";
		int oldrid = -1;
		int contactId = -1;
		Cursor cursor = getContentResolver().query(Data.CONTENT_URI, null,
				null, null, Data.RAW_CONTACT_ID);
		int numm = 0;
		while (cursor.moveToNext()) {
			contactId = cursor.getInt(cursor
					.getColumnIndex(Data.RAW_CONTACT_ID));
			if (oldrid != contactId) {
				jsonObject = new JSONObject();
				contactData.add(jsonObject);
				// contactData.put("contact" + numm, jsonObject);
				numm++;
				oldrid = contactId;
			}

			// 取得mimetype类型
			mimetype = cursor.getString(cursor.getColumnIndex(Data.MIMETYPE));
			// 获得通讯录中每个联系人的ID
			// 获得通讯录中联系人的名字
			if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// String display_name =
				// cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
				String firstName = cursor.getString(cursor
						.getColumnIndex(StructuredName.FAMILY_NAME));
				jsonObject.put("firstName", firstName);
				String middleName = cursor.getString(cursor
						.getColumnIndex(StructuredName.MIDDLE_NAME));
				jsonObject.put("middleName", middleName);
				String lastname = cursor.getString(cursor
						.getColumnIndex(StructuredName.GIVEN_NAME));
				jsonObject.put("lastname", lastname);
			}
			// 获取电话信息
			if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出电话类型
				int phoneType = cursor
						.getInt(cursor.getColumnIndex(Phone.TYPE));
				// 手机
				if (phoneType == Phone.TYPE_MOBILE) {
					String mobile = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					mobile = mobile.replace("-", "");
					mobile = mobile.replace(" ", "");
					mobile = mobile.replace("+86", "");

					jsonObject.put("mobile", mobile);

				}
			}
			// 获取备注信息
			if (Note.CONTENT_ITEM_TYPE.equals(mimetype)) {
				String remark = cursor.getString(cursor
						.getColumnIndex(Note.NOTE));
				jsonObject.put("remark", remark);
			}
			// 获取昵称信息
			if (Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
				String nickName = cursor.getString(cursor
						.getColumnIndex(Nickname.NAME));
				jsonObject.put("nickName", nickName);
			}
		}
		cursor.close();
		Log.i("contactData", contactData.toString());
		System.out.println("contactData" + contactData.size());
		JSONArray array = new JSONArray();
		for (Object con : contactData) {
			JSONObject con1 = (JSONObject) con;
			String s = con1.getString("mobile");

			if (con1.getString("mobile") != null) {
				array.add(con1);
			}
		}
		System.out.println("array" + array.size());


		/**
		 * 加载本地联系人
		 */
		 // 设置adapter
		loadLocalContactsAdapter = new LoadLocalContactsAdapter(this, array);
		 listView.setAdapter(loadLocalContactsAdapter);
		 Contacts userTemp = WeMoLianApplication.getInstance().getContactList()
		 .get(Constant.NEW_FRIENDS_USERNAME);
		 if (userTemp != null && userTemp.getUnreadMsgCount() != 0) {
		 userTemp.setUnreadMsgCount(0);
		 }
		 WeMoLianApplication.getInstance().getContactList()
		 .get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);
		 loadLocalContactsAdapter.notifyDataSetChanged();
	}
}
