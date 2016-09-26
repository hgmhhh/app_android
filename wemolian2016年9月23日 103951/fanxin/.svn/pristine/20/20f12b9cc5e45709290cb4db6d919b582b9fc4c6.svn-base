package com.wemolian.app;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;

public class TestLoadContacts {
	public List<Contacts> list;
	private Context context;
	private JSONObject contactData;
	private JSONObject jsonObject;

	public TestLoadContacts(Context context) {
		this.context = context;
	}

	public void getContacts() {
		list = new ArrayList<Contacts>();
		contactData = new JSONObject();
		String mimetype = "";
		int oldrid = -1;
		int contactId = -1;

		Uri uri = ContactsContract.Data.CONTENT_URI; // 联系人Uri；
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, Data.RAW_CONTACT_ID);
		int numm = 0;
		while (cursor.moveToNext()) {
			contactId = cursor.getInt(cursor
					.getColumnIndex(Data.RAW_CONTACT_ID));
			if (oldrid != contactId) {
				jsonObject = new JSONObject();
				contactData.put("contact" + numm, jsonObject);
				numm++;
				oldrid = contactId;
			}

		}
	}
}
