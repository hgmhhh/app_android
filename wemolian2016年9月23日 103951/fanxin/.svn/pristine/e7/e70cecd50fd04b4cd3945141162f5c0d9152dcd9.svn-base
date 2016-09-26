package com.wemolian.app.wml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.TestActivity;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.db.GroupsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.lock.pattern.GuideGesturePasswordActivity;
import com.wemolian.app.utils.LocaltionManager;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.HanziToPinyin;

/**
 * 登陆页面
 * 
 */
public class LoginActivity extends BaseActivity {
	private EditText et_usertel;
	private EditText et_password;
	private Button btn_login;
	private Button btn_qtlogin;
	ProgressDialog dialog;
	private String password;
	private String userEName;
	private static final int FORGET_PASSWORD = 0;
	/**
	 * 密码输入次数
	 */
	private int inputPwdTime = 0;
	/**
	 * 密码输入错误次数
	 */
//	private int errorPasswordTimes = 0;

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setStatus(findViewById(R.id.title));
		dialog = new ProgressDialog(LoginActivity.this);
		et_usertel = (EditText) findViewById(R.id.et_usertel);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_qtlogin = (Button) findViewById(R.id.btn_qtlogin);
		// 监听多个输入框

		et_usertel.addTextChangedListener(new TextChange());
		et_password.addTextChangedListener(new TextChange());

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if(errorPasswordTimes > 5){
//					Toast.makeText(LoginActivity.this, "密码输错次数超过五次，请稍后再试", Toast.LENGTH_LONG).show();
//					return;
//				}
				inputPwdTime ++;
				dialog.setMessage("正在登录...");
				dialog.setCancelable(true);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.show();

				password = et_password.getText().toString().trim();
				userEName = et_usertel.getText().toString().trim();
				Map<String, String> map = new HashMap<String, String>();

				map.put("userEName", userEName);
				map.put("password", password);

				// LoadDataFromServer task = new LoadDataFromServer(
				// LoginActivity.this, Constant.URL_Login, map);

				LoadDataFromServer task = new LoadDataFromServer(
						LoginActivity.this, Constant.URL_PRELOGIN, map);

				task.getData(new DataCallBack() {

					@Override
					public void onDataCallBack(JSONObject registerData) {

						try {
							//数据请求成功
							if(registerData.getBoolean("success")){
								JSONObject data = registerData.getJSONObject("data");
								int code = data.getInteger("code");
								if (code == 200) {
									//获取数据
									JSONObject toData = data.getJSONObject("data"); 
									//调用登录方法
									login(toData);
								} else if (code == 400) {
									dialog.dismiss();
									Toast.makeText(LoginActivity.this,
											"账号或密码错误...", Toast.LENGTH_SHORT)
											.show();
								} else {
									dialog.dismiss();
//									errorPasswordTimes ++;
									Toast.makeText(LoginActivity.this,
											"服务器繁忙请重试...", Toast.LENGTH_SHORT)
											.show();
								}
								//数据请求失败
							}else{
								Toast.makeText(LoginActivity.this, "登录失败！请重试！", Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							dialog.dismiss();
							Toast.makeText(LoginActivity.this, "数据解析错误...",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
				});

			}

		});
		btn_qtlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
				// startActivity(new Intent(LoginActivity.this,
				// GuideGesturePasswordActivity.class));
			}

		});
	}

	// EditText监听器
	class TextChange implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence cs, int start, int before,
				int count) {

			boolean Sign2 = et_usertel.getText().length() > 0;
			boolean Sign3 = et_password.getText().length() > 0;

			if (Sign2 & Sign3) {
//				btn_login.setTextColor(0xFFFFFFFF);
				btn_login.setEnabled(true);
			}
			// 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
			else {
//				btn_login.setTextColor(0xeeeeee);
				btn_login.setEnabled(false);
			}
		}

	}

	private void login(final JSONObject json) {

		try {
			final String userEName = json.getString("userEName");
			final String hxuserId = json.getString("hxuserId");
			final String hxpassword = json.getString("hxpassword");
			// String fxid = json.getString("fxid");
			// String tel = json.getString("tel");
			// String sex = json.getString("sex");
			// String sign = json.getString("sign");
			// String avatar = json.getString("avatar");
			// String region = json.getString("region");
			// 调用sdk登陆方法登陆聊天服务器
			EMChatManager.getInstance().login(hxuserId, hxpassword, new EMCallBack() {

				@Override
				public void onSuccess() {

					runOnUiThread(new Runnable() {
						public void run() {
							// 登陆成功，保存用户名密码
							WeMoLianApplication.getInstance().setUserName(hxuserId);
							WeMoLianApplication.getInstance().setPassword(hxpassword);
							Map<String, String> map = new HashMap<String, String>();
							map.put("userEName", userEName);
							map.put("password", password);
							map.put("hxuserId", hxuserId);
							map.put("hxpassword", hxpassword);
							map.put("code", "200");
							/**
							 * 调用二次登录方法，登录到服务器
							 */
							doLogin(map);
						}
					});
				
					

				}

				@Override
				public void onProgress(int progress, String status) {
				}

				@Override
				public void onError(final int code, final String message) {

					runOnUiThread(new Runnable() {
						public void run() {
							dialog.dismiss();
							Toast.makeText(getApplicationContext(),
									getString(R.string.Login_failed) + message,
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			});

		} catch (JSONException e1) {

			e1.printStackTrace();
		}

	}

	
	/**
	 * 二次登录到服务器
	 */
	private void doLogin(Map<String, String> map) {
		
		LoadDataFromServer task = new LoadDataFromServer(
				LoginActivity.this, Constant.URL_DOLOGIN, map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject registerData) {
				try {
					//二次登录成功
					if(registerData.getBoolean("success")){
						JSONObject data = registerData.getJSONObject("data");
						int code = data.getInteger("code");
						if(code == 200){
							doLoginSuccess(data.getJSONObject("data"));
//							Toast.makeText(getApplicationContext(), data.getString("message"), Toast.LENGTH_SHORT).show();
						}else if(code == 400){
							Log.i("error", "400");
							Toast.makeText(getApplicationContext(), data.getString("message"), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "未知异常！", Toast.LENGTH_SHORT).show();
						}
						
						//二次登录失败
					}else{
						Toast.makeText(getApplicationContext(), "登录失败！请重试！", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("error", "登录失败");
				}
			}
		} );
		
	
	}
	
	
	/**
	 * 二次登录成功
	 */
	private void doLoginSuccess(final JSONObject data){
		Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {
			public void run() {
				dialog.setMessage(getString(R.string.list_is_for));
			}
		});
		try {
			// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
			// ** manually load all local groups and
			// conversations in case we are auto login
			// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
			boolean updatenick = EMChatManager.getInstance()
					.updateCurrentUserNick(data.getString("userCName"));
			if (!updatenick) {
				Log.e("LoginActivity",
						"update current user nick fail");
			}
			EMGroupManager.getInstance().loadAllGroups();
			EMChatManager.getInstance().loadAllConversations();
			// 处理好友和群组
			runOnUiThread(new Runnable() {
				public void run() {
					processContactsAndGroups(data);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			// 取好友或者群聊失败，不让进入主页面
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
					WeMoLianApplication.getInstance().logout(null);
					Toast.makeText(getApplicationContext(),
							R.string.login_failure_failed,
							Toast.LENGTH_SHORT).show();
				}
			});
			return;
	}
	
}
	
	
	private void processContactsAndGroups(final JSONObject json) {
		// demo中简单的处理成每次登陆都去获取好友username，开发者自己根据情况而定

		try {
			/**
			 * 从环信拉取好友列表
			 */
			List<String> usernames = EMContactManager.getInstance()
					.getContactUserNames();
			/**
			 * 获取群组列表
			 */
			List<EMGroup> grouplist = EMGroupManager.getInstance().getGroupsFromServer();
			
			
			
			
			if ((usernames != null && usernames.size() > 0) || (grouplist != null && grouplist.size() > 0)) {
				String totaluser = usernames.get(0);
				for (int i = 1; i < usernames.size(); i++) {
					final String split = "66split88";
					totaluser += split + usernames.get(i);
				}
				totaluser = totaluser
						.replace(Constant.NEW_FRIENDS_USERNAME, "");
				totaluser = totaluser.replace(Constant.GROUP_USERNAME, "");
				Log.e("totaluser---->>>>>", totaluser);
				Map<String, String> map = new HashMap<String, String>();

				map.put("code", "200");
				map.put("userEName", userEName);

				/**
				 * 发送请求到服务器，拉取好友信息和群信息
				 */
				LoadDataFromServer task = new LoadDataFromServer(
						LoginActivity.this, Constant.URL_INITUSERDATA, map);

				task.getData(new DataCallBack() {

					@Override
					public void onDataCallBack(JSONObject resultData) {
						try {
							/**
							 * 数据返回成功
							 * 并且data中的数据不为空
							 */
							if(resultData.getBooleanValue("success") && resultData.getJSONObject("data") != null){

								JSONObject data = resultData.getJSONObject("data");
								int code = data.getInteger("code");
								//200表示既有群列表也有好友列表
								if (code == 200) {
									//获取好友列表
									JSONArray friendsJosnArray = data
											.getJSONArray("friends");
									//获取群聊列表
									JSONArray groupsJosnArray = data
											.getJSONArray("groups");
									
									
									// 保存自己的信息
									saveMyInfo(json);

									//保存群信息
									saveGroups(groupsJosnArray);
									//保存好友信息
									saveFriends(friendsJosnArray);

								//201 表示只有好友列表，没有群列表
								}else if(code == 201){
									JSONArray friendsJosnArray = data
											.getJSONArray("friends");
									saveMyInfo(json);
									//保存好友信息
									saveFriends(friendsJosnArray);
								//202 表示只有群列表，没有好友列表
								}else if(code == 202){
									JSONArray groupsJosnArray = data
											.getJSONArray("groups");
									saveMyInfo(json);
									saveGroups(groupsJosnArray);
								}
								//404 表示既没有好友列表，也没有群列表
								else if (code == 203) {
									dialog.dismiss();
									// 保存信息
									saveMyInfo(json);

									saveFriends(null);
									Toast.makeText(LoginActivity.this,
											"获取好友列表失败,请重试...", Toast.LENGTH_SHORT)
											.show();
								} else {
									dialog.dismiss();
									Toast.makeText(LoginActivity.this,
											"服务器繁忙请重试...", Toast.LENGTH_SHORT)
											.show();
								}

								/**
								 * 数据返回失败
								 */
							}else{
								Toast.makeText(getApplicationContext(), "请求服务器失败！", Toast.LENGTH_SHORT).show();
							}
							
						} catch (JSONException e) {
							dialog.dismiss();
							Toast.makeText(LoginActivity.this, "数据解析错误...",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
				});
			} else {
				// 保存信息
				saveMyInfo(json);
				saveGroups(null);
				saveFriends(null);
			}
		} catch (EaseMobException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	
	/**
	 * 保存群信息
	 * @param groupsJosnArray
	 */
	protected void saveGroups(JSONArray josnArray) {
		Map<String, Groups> map = new HashMap<String, Groups>();
		if (josnArray != null) {
			for (int i = 0; i < josnArray.size(); i++) {
				JSONObject json = josnArray.getJSONObject(i);
				try {
					
					String groupename = json.getString("groupename");
					String groupUserHeadImgs = json.getString("groupUserHeadImgs");
					String groupqrcode = json.getString("groupqrcode");
					String groupmembers = json.getString("groupmembers");
					String groupcname = json.getString("groupcname");
					String groupHxId = json.getString("grouphxId");
					String groupId = json.getString("groupId");
					String owner = json.getString("groupuserId");
					int top = json.getInteger("top");
					String newNotDis = json.getString("newNotDis");
					
					Groups groups = new Groups();
					groups.setGroupcname(groupcname);
					groups.setGroupename(groupename);
					groups.setGroupmembers(groupmembers);
					groups.setGroupqrcode(groupqrcode);
					groups.setGroupUserHeadImgs(groupUserHeadImgs);
					groups.setTop(top);
					groups.setGroupHxId(groupHxId);
					groups.setGroupId(groupId);
					groups.setOwner(owner);
					groups.setNewNotDis(newNotDis);
					
					map.put(groups.getGroupename(), groups);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		
		
		// 添加"群聊"
		Groups groups = new Groups();
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		String strGroup = getResources().getString(R.string.group_chat);
		groups.setGroupcname(strGroup);
		groups.setGroupename(strChat);
		groups.setGroupmembers("");
		groups.setGroupqrcode("");
		groups.setGroupUserHeadImgs("");
		groups.setTop(0);
		groups.setNewNotDis(SysConfig.NEW_NOT_DIS_FALSE);
		groups.setOwner(LocalUserInfo.getInstance(LoginActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE));
		map.put(Constant.GROUP_USERNAME, groups);
		
		
		// 存入内存
		WeMoLianApplication.getInstance().setGroupsList(map);
		// 存入db
		GroupsDao dao = new GroupsDao(LoginActivity.this);
		List<Groups> groupsList = new ArrayList<Groups>(map.values());
		dao.saveGroups(groupsList);
		
	}


	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param userCName
	 * @param contacts
	 */
	@SuppressLint("DefaultLocale")
	protected void setUserHearder(String userCName, Contacts contacts) {
		String headerName = null;
//		if (!TextUtils.isEmpty(contacts.getUserCName())) {
//			headerName = contacts.getUserCName();
//		} else {
//			headerName = contacts.getUsername();
//		}
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
		headerName = headerName.trim();
		if ((Constant.NEW_FRIENDS_USERNAME).equals(userCName)) {
			contacts.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			contacts.setHeader("#");
		} else {
			contacts.setHeader(HanziToPinyin.getInstance()
					.get(headerName.substring(0, 1)).get(0).target.substring(0,
					1).toUpperCase());
			char header = contacts.getHeader().toLowerCase().charAt(0);
			if (header < 'a' || header > 'z') {
				contacts.setHeader("#");
			}
		}
	}

	/**
	 * 保存用户自己的信息
	 * @param json
	 */
	private void saveMyInfo(JSONObject json) {

//		try {

			User user = JSONObject.toJavaObject(json, User.class); 
			
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ID, user.getId());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME, user.getUserEName());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERCNAME, user.getUserCName());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HXUSERID, user.getHxuserId());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HXPASSWORD, user.getHxpassword());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERADDRESS, user.getUserAddress());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERSEX, user.getUserSex());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_USERREGION, user.getUserRegion());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_BANKCARD, user.getBankCard());
//			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_REGISTDATE,new SimpleDateFormat().format(user.getRegistDate()));
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_REGISTDATE,user.getRegistDate());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PHONENUM, user.getPhoneNum());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PASSWORD, user.getPassword());
//			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PASSWORD, password);
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_OLDPASSWORD, user.getOldPassword());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE, user.getExternalUse());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ADDRESSPOINT, user.getAddressPoint());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_ISONLINE, user.getIsOnline());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_STATE, user.getState());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_PERMISSION, user.getPermission());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG, user.getHeadImg());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_QRCODE, user.getQrcode());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_EMAIL, user.getEmail());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH, user.getAutograph());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_VIP, user.getVip());
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_INTTIMESTAMP, "");
			LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(LocalDBKey.USER_COLUMN_NAME_RANDOMNUM, "");
			
			WeMoLianApplication.getInstance().saveUserInfo(user);
			
//		} catch (JSONException e) {
//
//			e.printStackTrace();
//			dialog.dismiss();
//			return;
//		}

	}

	/**
	 * 保存用户好友信息
	 * @param josnArray
	 */
	private void saveFriends(JSONArray josnArray) {

		Map<String, Contacts> map = new HashMap<String, Contacts>();

		if (josnArray != null) {
			for (int i = 0; i < josnArray.size(); i++) {
				JSONObject json = josnArray.getJSONObject(i);
				try {
//					Contacts contact = JSONObject.toJavaObject(json, Contacts.class);  
					
//					int unreadMsgCount = json.getInteger("unreadMsgCount");
					String userSex = json.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
					String userCName = json.getString(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
					String img = json.getString(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
					String externalUse = json.getString("friendId");
					String hxId = json.getString(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
//					String phoneNum = json.getString(LocalDBKey.USER_COLUMN_NAME_PHONENUM);
//					String isOnline = json.getString(LocalDBKey.USER_COLUMN_NAME_ISONLINE);
//					int state = json.getIntValue(LocalDBKey.USER_COLUMN_NAME_STATE);
					String label = json.getString(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL);
					String userRemark = json.getString(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK);
					String autograph = json.getString(LocalDBKey.CONTACTS_COLUMN_NAME_AUTOGRAPH);
					
					
					Contacts contact = new Contacts();
					contact.setSex(userSex);
					contact.setLabel(label);
					contact.setUserRemark(userRemark);
					contact.setUserCName(userCName);
					contact.setNick(userCName);
					contact.setImgName(img);
					contact.setExternalUse(externalUse);
					contact.setHxid(hxId);
					contact.setType(SysConfig.STR_TYPE_FRIEND);
					contact.setAutograph(autograph);
					
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
					contact.setLabel(nick);
					setUserHearder(nick, contact);
					map.put(contact.getExternalUse(), contact);

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
		newFriends.setSex("");
		newFriends.setType(SysConfig.STR_TYPE_FRIEND);
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
		newFriends.setAutograph("123");
		
		
		
		map.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 存入内存
		WeMoLianApplication.getInstance().setContactList(map);
		// 存入db
		ContactsDao dao = new ContactsDao(LoginActivity.this);
		List<Contacts> contacts = new ArrayList<Contacts>(map.values());
		dao.saveContactList(contacts);

		// 获取黑名单列表

		try {
			List<String> blackList = EMContactManager.getInstance()
					.getBlackListUsernamesFromServer();
			EMContactManager.getInstance().saveBlackList(blackList);

			// 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
//			EMGroupManager.getInstance().getGroupsFromServer();
			addContact("11223354");
//			if (!LoginActivity.this.isFinishing())
//				dialog.dismiss();
//			// 进入主页面
//			startActivity(new Intent(LoginActivity.this, MainActivity.class));
//			// startActivity(new Intent(LoginActivity.this,
//			// TestActivity.class));
//			finish();
			
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					/**
					 * 添加登录日志到服务器
					 */
					loginLog();
				}
			});
			
			
		} catch (EaseMobException e) {
			// TODO Auto-generated catch block
			dialog.dismiss();
			e.printStackTrace();
		}

	}

	/**
	 * 添加登录日志到服务器
	 */
	private void loginLog() {
		Map<String, String> map = new HashMap<String, String>();
		/**
		 * externalUse	用户的对外使用字段
			lastLoginDevice	用户的登录设备
			loginAddress	用户的登录地址
			loginAddressPoint	登录地址经纬度
			Operation	登录的最后操作
			inputPwdTime	密码输入次数
			errorPwdTime	密码输入错误次数
			errorReason	错误原因
		 */
		String externalUse = LocalUserInfo.getInstance(LoginActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		Map<String, Object> addressMap = LocaltionManager.getLocaltion(LoginActivity.this);
		map.put("externalUse",externalUse);
		map.put("lastLoginDevice",Build.MODEL);
		if(addressMap != null && addressMap.size() > 0){
			map.put("loginAddress",(String)addressMap.get("address"));
			map.put("loginAddressPoint", addressMap.get("lat") + SysConfig.SPLIT_POINT + addressMap.get("lon"));
		}
		map.put("Operation","login");
		map.put("inputPwdTime",String.valueOf(inputPwdTime));
		map.put("errorPwdTime","4");
		map.put("errorReason","密码输入错误");
		LoadDataFromServer task = new LoadDataFromServer(this, Constant.URL_LOGIN_LOG, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if(resData != null && resData.getBooleanValue("success")){
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						if (!LoginActivity.this.isFinishing())
							dialog.dismiss();
						// 进入主页面
						startActivity(new Intent(LoginActivity.this, MainActivity.class));
						// startActivity(new Intent(LoginActivity.this,
						// TestActivity.class));
						finish();
					}
					
				}else{
					Toast.makeText(LoginActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}
		});
	}


	/**
	 * 添加contact
	 * 
	 * @param view
	 */
	@SuppressLint("ShowToast")
	public void addContact(final String glufine_id) {
		// 11223354
		if (glufine_id == null || glufine_id.equals("")) {
			return;
		}

		if (WeMoLianApplication.getInstance().getUserName().equals(glufine_id)) {

			return;
		}

		if (WeMoLianApplication.getInstance().getContactList()
				.containsKey(glufine_id)) {

			return;
		}

		new Thread(new Runnable() {
			public void run() {

				try {
					// 在reason封装请求者的昵称/头像/时间等信息，在通知中显示

					String name = LocalUserInfo.getInstance(LoginActivity.this)
							.getUserInfo(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME);
					String avatar = LocalUserInfo.getInstance(
							LoginActivity.this).getUserInfo(LocalDBKey.CONTACTS_COLUMN_NAME_EXTERNALUSE);
//					String avatar = LocalUserInfo.getInstance(
//							LoginActivity.this).getUserInfo(LocalDBKey.CONTACTS_COLUMN_NAME_HXID);
					long time = System.currentTimeMillis();

					String reason = name + "66split88" + avatar + "66split88"
							+ String.valueOf(time) + "66split88" + "加你好友";
					EMContactManager.getInstance().addContact(glufine_id,
							reason);

				} catch (final Exception e) {

				}
			}
		}).start();
	}
 
	/**
	 * 忘记密码
	 * @param v
	 */
	public void forgetPassword(View v){
		startActivity(new Intent(this, RegisterActivity.class)
					.putExtra("activity", "forgetPwd"));
	}
	
	
	
	
	// 隐藏软键盘
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (LoginActivity.this.getCurrentFocus() != null) {
				if (LoginActivity.this.getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(LoginActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
        	startActivity(new Intent(this, ChooseActivity.class));
        	finish();
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                moveTaskToBack(false);
//                finish();
//
//            }
            return true;
        }
        return true;
    }
    
    @Override
    protected void onDestroy() {
        try{
            dialog.dismiss();
        }catch (Exception e) {
        	
        }
    	super.onDestroy();
    }
    
    @Override
    public void back(View view) {
//    	super.back(view);
    	startActivity(new Intent(this, ChooseActivity.class));
    	finish();
    }

}
