package com.wemolian.app.wml.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.util.HanziToPinyin;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.db.InviteMessgeDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.InviteMessage;
import com.wemolian.app.domain.InviteMessage.InviteMesageStatus;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 加载本地联系人列表的适配器
 * 
 * @author Administrator
 *
 */
@SuppressLint("ViewHolder")
public class LoadLocalContactsAdapter extends BaseAdapter {
	Context context;
	JSONArray json;
	int total = 0;

	@SuppressLint("SdCardPath")
	public LoadLocalContactsAdapter(Context context, JSONArray jsonArray) {
		this.context = context;
		this.json = jsonArray;
		total = json.size();
	}

	@Override
	public int getCount() {
		return json.size();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		return (JSONObject) json.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		boolean tag = true;
		/**
		 * {"company":"lckj","homeEmail":"zy19930321@163.com",
		 * "homeNum":"1 478-781-1147","homeStreet":"yunnankunmin",
		 * "jobTitle":"java"
		 * ,"lastname":"123","mobile":"1 478-781-1148","remark":"gege"}]
		 */
		final ViewHolder holder;

		holder = new ViewHolder();
		final JSONObject contact = getItem(total - 1 - position);

		String userName = "";

		String remark = contact.getString("remark");
		String firstName = contact.getString("firstName");
		String lastname = contact.getString("lastname");
		String mobile = contact.getString("mobile");
		if (firstName != null && lastname != null) {
			if (firstName.equals(lastname) || firstName == lastname) {
				userName = firstName;
			} else {
				userName = firstName + lastname;
			}
		} else if (firstName != null) {
			userName = firstName;
		} else if (lastname != null) {
			userName = lastname;
		} else if (remark != null) {
			userName = remark;
		} else {
			userName = "未设置";
			tag = false;
		}
		convertView = View.inflate(context, R.layout.item_newfriendsmsag, null);
		holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
		holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.tv_reason = (TextView) convertView.findViewById(R.id.tv_reason);
		holder.tv_added = (TextView) convertView.findViewById(R.id.tv_added);
		holder.btn_add = (Button) convertView.findViewById(R.id.btn_add);
		holder.iv_avatar.setVisibility(View.GONE);
		holder.tv_name.setText(userName + ":" + mobile);
		holder.tv_reason.setVisibility(View.GONE);
		holder.tv_added.setVisibility(View.GONE);
		holder.btn_add.setText("邀请好友");
		holder.btn_add.setBackgroundResource(R.drawable.f4a0ba_bg);
		holder.btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "从联系人添加好用的功能正在开发中！", Toast.LENGTH_LONG)
						.show();
			}

		});

		// showUserAvatar(holder.iv_avatar,avatar);
		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_avatar;
		TextView tv_name;
		TextView tv_reason;
		TextView tv_added;
		Button btn_add;

	}

	private void addFriendToList(final String hxid) {
		Map<String, String> map_uf = new HashMap<String, String>();
		map_uf.put("hxid", hxid);
		LoadDataFromServer task = new LoadDataFromServer(null,
				Constant.URL_GET_USERMES, map_uf);
		task.getData(new DataCallBack() {
			public void onDataCallBack(JSONObject data) {
				try {

					int code = data.getInteger("code");
					if (code == 1) {

						JSONObject json = data.getJSONObject("user");
						if (json != null && json.size() != 0) {

						}
						String nick = json.getString("nick");
						String avatar = json.getString("avatar");

						String hxid = json.getString("hxid");
						String fxid = json.getString("fxid");
						String region = json.getString("region");
						String sex = json.getString("sex");
						String sign = json.getString("sign");
						String tel = json.getString("tel");
						Contacts user = new Contacts();

						user.setUsername(hxid);
						user.setNick(nick);
						// user.setAvatar(avatar);
						// user.setFxid(fxid);
						// user.setRegion(region);
						// user.setSex(sex);
						// user.setSign(sign);
						// user.setTel(tel);
						// setUserHearder(hxid,user);
						Map<String, Contacts> userlist = WeMoLianApplication
								.getInstance().getContactList();
						Map<String, Contacts> map_temp = new HashMap<String, Contacts>();
						map_temp.put(hxid, user);
						userlist.putAll(map_temp);
						// 存入内存
						WeMoLianApplication.getInstance().setContactList(userlist);
						// 存入db
						ContactsDao dao = new ContactsDao(context);

						dao.saveContact(user);

					}

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		});

	}

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param username
	 * @param user
	 */
	@SuppressLint("DefaultLocale")
	protected void setUserHearder(String username, Contacts user) {
		String headerName = null;
		if (!TextUtils.isEmpty(user.getNick())) {
			headerName = user.getNick();
		} else {
			headerName = user.getUsername();
		}
		headerName = headerName.trim();
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
			user.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			user.setHeader("#");
		} else {
			user.setHeader(HanziToPinyin.getInstance()
					.get(headerName.substring(0, 1)).get(0).target.substring(0,
					1).toUpperCase());
			char header = user.getHeader().toLowerCase().charAt(0);
			if (header < 'a' || header > 'z') {
				user.setHeader("#");
			}
		}
	}
}
