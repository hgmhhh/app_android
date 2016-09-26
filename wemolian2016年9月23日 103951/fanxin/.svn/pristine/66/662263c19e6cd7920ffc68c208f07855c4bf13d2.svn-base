package com.wemolian.app.wml.obtaion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.LoginActivity;
import com.wemolian.app.wml.MainActivity;
import com.wemolian.app.wml.others.LocalUserInfo;

/**
 * 将信息存到数据库
 * @author zhangyun 2016.07.09
 *
 */
public class SaveInfo extends BaseActivity {

	/**
	 * 保存用户信息
	 * @param context
	 * @param json
	 * @param dialog
	 */
	public static void saveMyInfo(Context context,JSONObject json,ProgressDialog dialog){
		try {

			User user = JSONObject.toJavaObject(json, User.class); 
			
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ID, user.getId());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME, user.getUserEName());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERCNAME, user.getUserCName());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HXUSERID, user.getHxuserId());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HXPASSWORD, user.getHxpassword());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERADDRESS, user.getUserAddress());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERSEX, user.getUserSex());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERREGION, user.getUserRegion());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_BANKCARD, user.getBankCard());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_REGISTDATE,new SimpleDateFormat().format(user.getRegistDate()));
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PHONENUM, user.getPhoneNum());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PASSWORD, user.getPassword());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_OLDPASSWORD, user.getOldPassword());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE, user.getExternalUse());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ADDRESSPOINT, user.getAddressPoint());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ISONLINE, user.getIsOnline());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_STATE, user.getState());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PERMISSION, user.getPermission());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG, user.getHeadImg());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_QRCODE, user.getQrcode());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_EMAIL, user.getEmail());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH, user.getAutograph());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_VIP, user.getVip());
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_INTTIMESTAMP, "");
			LocalUserInfo.getInstance(context).setUserInfo(LocalDBKey.USER_COLUMN_NAME_RANDOMNUM, "");
			
		} catch (JSONException e) {

			e.printStackTrace();
			dialog.dismiss();
			return;
		}
	}
	
	
	/**
	 * 保存用户好友信息
	 * @param josnArray
	 */
	private void saveFriends(Context context,JSONArray josnArray,ProgressDialog dialog) {

		Map<String, Contacts> map = new HashMap<String, Contacts>();

		if (josnArray != null) {
			for (int i = 0; i < josnArray.size(); i++) {
				JSONObject json = josnArray.getJSONObject(i);
				try {
					Contacts contact = JSONObject.toJavaObject(json, Contacts.class);  
					String imgName = json.getString("headImg");
					contact.setType("friends");
					contact.setImgName(imgName);
					
//					int unreadMsgCount = json.getInteger("unreadMsgCount");
//					String header = json.getString("header");
//					String externalUse = json.getString("externalUse");
//					String  hxid = json.getString("hxid");
//					String userCName = json.getString("userCName");
//					int newsNotDis = json.getInteger("newsNotDis");
//					String userRemark = json.getString("userRemark");
//					int blackList = json.getInteger("blackList");
//					int developmentMe = json.getInteger("developmentMe");
//					String label = json.getString("label");
//					String chatTop = json.getString("chatTop");
//
//					Contacts constant = new Contacts();
//					constant.setExternalUse(externalUse);
//					constant.setHxid(hxid);
//					constant.setUserCName(userCName);
//					constant.setNewsNotDis(newsNotDis);
//					constant.setUserRemark(userRemark);
//					constant.setBlackList(blackList);
//					constant.setDevelopmentMe(developmentMe);
//					constant.setLabel(label);
//					constant.setHeadImg(headImg);
//					constant.setChatTop(chatTop);
					
					String nick = "";
					if(contact.getLabel() != null){
						nick = contact.getLabel();
					}else if(contact.getUserRemark() != null) {
						nick = contact.getUserRemark();
					}else if(contact.getUserCName() != null){
						nick = contact.getUserCName();
					}else if(contact.getNick() != null){
						nick = contact.getNick();
					}else{
						nick = contact.getExternalUse();
					}
//					UserHeaeder.setUserHearder(contact.getHxid(), contact);
					UserHeaeder.setUserHearder(nick, contact);
					map.put(contact.getHxid(), contact);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// 添加user"申请与通知"
		Contacts newFriends = new Contacts();
		newFriends.setUserCName(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);
		newFriends.setUserCName(strChat);
		newFriends.setHeader("");
		newFriends.setExternalUse("");
		newFriends.setHxid("");
		newFriends.setNewsNotDis(0);
		newFriends.setUserRemark("");
		newFriends.setBlackList(0);
		newFriends.setDevelopmentMe(0);
		newFriends.setLabel("");
		newFriends.setImgName("");
		newFriends.setChatTop("");
		
		map.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		Contacts groupUser = new Contacts();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(Constant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setUserCName(strChat);
		groupUser.setHeader("");
		groupUser.setExternalUse("");
		groupUser.setHxid("");
		groupUser.setNewsNotDis(0);
		groupUser.setUserRemark("");
		groupUser.setBlackList(0);
		groupUser.setDevelopmentMe(0);
		groupUser.setLabel("");
		groupUser.setImgName("");
		groupUser.setChatTop("");
		map.put(Constant.GROUP_USERNAME, groupUser);
	
		
		
		
		
		
		
		map.put(Constant.NEW_FRIENDS_USERNAME, groupUser);
		map.put(Constant.GROUP_USERNAME, groupUser);
		// 存入内存
		WeMoLianApplication.getInstance().setContactList(map);
		// 存入db
		ContactsDao dao = new ContactsDao(context);
		List<Contacts> user = new ArrayList<Contacts>(map.values());
		dao.saveContactList(user);

		// 获取黑名单列表

		try {
			List<String> blackList = EMContactManager.getInstance()
					.getBlackListUsernamesFromServer();
			EMContactManager.getInstance().saveBlackList(blackList);

			// 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
			EMGroupManager.getInstance().getGroupsFromServer();
//			addContact("11223354");
			if (!((Activity) context).isFinishing())
				dialog.dismiss();
			// 进入主页面
			startActivity(new Intent(context, MainActivity.class));
			// startActivity(new Intent(LoginActivity.this,
			// TestActivity.class));
			finish();
		} catch (EaseMobException e) {
			// TODO Auto-generated catch block
			dialog.dismiss();
			e.printStackTrace();
		}

	}

	
}
