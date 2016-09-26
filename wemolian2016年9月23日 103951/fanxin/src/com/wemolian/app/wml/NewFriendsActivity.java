package com.wemolian.app.wml;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.InviteMessgeDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.InviteMessage;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.friends.LocalContactsActivity;
import com.wemolian.app.wml.others.LoadLocalContactsAdapter;
import com.wemolian.app.wml.others.NewFriendsAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 申请与通知
 * 
 */
public class NewFriendsActivity extends BaseActivity {
	private ListView listView;
	NewFriendsAdapter adapter;
	LoadLocalContactsAdapter contactAdapter;
	List<InviteMessage> msgs;
	InviteMessgeDao dao;
	
	LinearLayout ll_phoneContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newfriendsmsg);
		// DemoApplication.getInstance().addActivity(this);
		setStatus(findViewById(R.id.title));
		listView = (ListView) findViewById(R.id.listview);
		ll_phoneContacts = (LinearLayout) findViewById(R.id.ll_phone_contacts);
		TextView et_search = (TextView) findViewById(R.id.et_search);
		TextView tv_add = (TextView) findViewById(R.id.tv_add);
		et_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(NewFriendsActivity.this,
						AddFriendsTwoActivity.class));
			}

		});
		tv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				startActivity(new Intent(NewFriendsActivity.this,
//						AddFriendsOneActivity.class));
				startActivity(new Intent(NewFriendsActivity.this,
						AddFriendsTwoActivity.class));
			}

		});

		dao = new InviteMessgeDao(this);
		msgs = dao.getMessagesList();
		// 设置adapter
		adapter = new NewFriendsAdapter(this, msgs);
		listView.setAdapter(adapter);
		Contacts userTemp = WeMoLianApplication.getInstance().getContactList()
				.get(Constant.NEW_FRIENDS_USERNAME);
		if (userTemp != null && userTemp.getUnreadMsgCount() != 0) {
			userTemp.setUnreadMsgCount(0);
		}
		WeMoLianApplication.getInstance().getContactList()
				.get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);

//		 loadData();
		
		
		
		ll_phoneContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(NewFriendsActivity.this, LocalContactsActivity.class));
			}
		});

	}

	

	public void back(View v) {
		finish();
	}

}
