package com.wemolian.app.wml;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.R.string;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.wemolian.app.wml.others.LocalUserInfo;

public class AddFriendsTwoActivity extends BaseActivity {

	// 查询好友的输入框
	private EditText et_search;
	// 查询好友的点击处
	private ImageButton ib_search;
	// 查询到的联系人的信息布局
	private RelativeLayout rl_friens_msg;
	// 查询到的联系人的头像
	private ImageView iv_headimg;
	// 查询到的联系人的昵称/手机号码/帐号
	private TextView tv_friend_name;
	// 添加联系人的点击标签
	private TextView tv_addfriend;
	// 查找到的好友的对外使用字段
	private TextView tv_friend_externaluse;
	// 用户不存在标签
	private RelativeLayout rl_search_no_friend;
	// 用于加载用户头像
	private LoadUserAvatar avatarLoader;
	boolean isFriend = false;
	ProgressDialog addDialog;
	String friendHxId = "";
	// 好友的对外使用字段
	String friendExternalUse = "";
	String headImg = "";
	String nick = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriends_two);
		setStatus(findViewById(R.id.title));
		addDialog = new ProgressDialog(AddFriendsTwoActivity.this);
		// 初始化
		initView();
		initData();

	}

	private void initView() {
		// 查询好友的输入框
		et_search = (EditText) findViewById(R.id.et_search);
		// 查询好友的点击处
		ib_search = (ImageButton) findViewById(R.id.ib_search);
		rl_friens_msg = (RelativeLayout) findViewById(R.id.rl_friens_msg);
		// 查询到的联系人的头像
		iv_headimg = (ImageView) findViewById(R.id.iv_headimg);
		// 查询到的联系人的昵称/手机号码/帐号
		tv_friend_name = (TextView) findViewById(R.id.tv_friend_name);
		// 添加联系人的点击标签
		tv_addfriend = (TextView) findViewById(R.id.tv_addfriend);
		// 用户不存在标签
		rl_search_no_friend = (RelativeLayout) findViewById(R.id.rl_search_no_friend);
		// 查询到的联系人的对外使用字段
		tv_friend_externaluse = (TextView) findViewById(R.id.tv_friend_externaluse);
		// 用于加载用户头像
		avatarLoader = new LoadUserAvatar(this, "/sdcard/wemolian/");
		// EditText EditText01 = (EditText)
		// textEntryView.findViewById(R.id.EditText01);
	}

	private void initData() {
		ib_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String s = et_search.getText().toString().trim();
				if (et_search.getText().toString().trim().length() == 0) {
					Toast.makeText(AddFriendsTwoActivity.this, "请输入好友信息",
							Toast.LENGTH_SHORT).show();
				} else {
					rl_search_no_friend.setVisibility(View.GONE);
					rl_friens_msg.setVisibility(View.GONE);
					/**
					 * 调用方法，查询好友信息
					 */
					searchUser(et_search.getText().toString().trim());
				}
			}
		});
		tv_addfriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isFriend) {
					Intent intent = new Intent();
					intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID,
							friendHxId);
					intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME,
							headImg);
					intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME,
							nick);
					intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE,
							"friend");
					intent.setClass(AddFriendsTwoActivity.this,
							ChatActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.putExtra("friendHxId", friendHxId);
					intent.putExtra("friendExternalUse", friendExternalUse);
					intent.putExtra(
							"userExternalUse",
							LocalUserInfo.getInstance(
									AddFriendsTwoActivity.this).getUserInfo(
									LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE));
					intent.setClass(AddFriendsTwoActivity.this,
							AddFriendsFinalActivity.class);
					startActivity(intent);

				}

			}
		});
	}

	private void searchUser(String keyWorld) {
		final ProgressDialog dialog = new ProgressDialog(
				AddFriendsTwoActivity.this);
		addDialog.setMessage("正在查找联系人...");
		addDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		addDialog.show();
		Map<String, String> map = new HashMap<String, String>();

		String externalUse = LocalUserInfo.getInstance(getApplicationContext())
				.getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		map.put("keyWorld", keyWorld);
		map.put("userExternalUse", externalUse);

		LoadDataFromServer task = new LoadDataFromServer(
				AddFriendsTwoActivity.this, Constant.URL_SEARCH_USER, map);

		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject resData) {

				try {
					if (resData.getBoolean("success")
							&& resData.getJSONObject("data") != null) {
						JSONObject data = resData.getJSONObject("data");
						String code = data.getString("code");
						if ("200".equals(code)) {
							JSONArray jsonArray = data.getJSONArray("data");
							addDialog.dismiss();
							if (jsonArray != null && jsonArray.size() > 0) {
								JSONObject json = (JSONObject) jsonArray.get(0);

								/**
								 * {"userEName":"123","userCName":"测试",
								 * "hxuserId":"null",
								 * "hxpassword":"null","userAddress"
								 * :"云南","userSex":"男",
								 * "userRegion":"云南昆明","bankCard"
								 * :null,"registDate":null,
								 * "phoneNum":"0","password"
								 * :"null","oldPassword":"null", "externalUse":
								 * "f49bb95edc33f815ff7efc6541fda529",
								 * "addressPoint"
								 * :null,"isOnline":1,"state":1,"permission"
								 * :null, "id":324,"qrcode":null,"remark1":null,
								 * "remark5":null,
								 * "remark2":null,"remark3":null,
								 * "remark4":null,"intTimeStamp":"null",
								 * "headImg"
								 * :"fd920dd56ec0a54b9d4501cec6ebefea.png"
								 * ,"randomNum":"null",
								 * "vip":null,"autograph":"我是送到武汉动啊我"
								 * ,"email":null}
								 */
								String hxuserId = json.getString("hxuserId");
								String externalUse = json
										.getString("externalUse");
								String phoneNum = json.getString("phoneNum");
								String userCName = json.getString("userCName");
								String userEName = json.getString("userEName");
								String imgName = json.getString("headImg");
								String friendName = null;
								if (userCName != null) {
									friendName = userCName;
								} else if (phoneNum != null) {
									friendName = phoneNum;
								} else {
									friendName = userEName;
								}

								friendExternalUse = externalUse;
								friendHxId = hxuserId;
								showUserHeadImg(iv_headimg, imgName);
								// tv_friend_externaluse.setText(hxuserId);
								tv_friend_externaluse.setText(externalUse);
								tv_friend_name.setText(friendName);

								if (WeMoLianApplication.getInstance()
										.getContactList().containsKey(hxuserId)) {
									isFriend = true;
									headImg = imgName;
									nick = friendName;
									tv_addfriend.setText("发送消息");
								}

								rl_friens_msg.setVisibility(View.VISIBLE);
							}

						} else if ("201".equals(code)) {
							addDialog.dismiss();
							rl_search_no_friend.setVisibility(View.VISIBLE);
						} else if ("400".equals(code)) {
							addDialog.dismiss();
							Toast.makeText(AddFriendsTwoActivity.this,
									"查找好友失败！", Toast.LENGTH_SHORT).show();
						} else {
							addDialog.dismiss();
							Toast.makeText(AddFriendsTwoActivity.this,
									"服务器繁忙请重试...", Toast.LENGTH_SHORT).show();
						}
					} else {
						addDialog.dismiss();
					}

				} catch (JSONException e) {
					addDialog.dismiss();
					Toast.makeText(AddFriendsTwoActivity.this, "数据解析错误...",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 加载用户头像
	 * 
	 * @param iamgeView
	 * @param imgName
	 */
	private void showUserHeadImg(ImageView iamgeView, String imgName) {
		final String url_avatar = Constant.URL_Avatar + imgName;
		iamgeView.setTag(url_avatar);
		if (url_avatar != null && !url_avatar.equals("")) {
			Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
					new ImageDownloadedCallBack() {

						@Override
						public void onImageDownloaded(ImageView imageView,
								Bitmap bitmap) {
							if (imageView.getTag() == url_avatar) {
								imageView.setImageBitmap(bitmap);

							}
						}

					});
			if (bitmap != null)
				iamgeView.setImageBitmap(bitmap);

		}
	}

	public void back(View view) {
		finish();
	}

	// 隐藏软键盘
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (AddFriendsTwoActivity.this.getCurrentFocus() != null) {
				if (AddFriendsTwoActivity.this.getCurrentFocus()
						.getWindowToken() != null) {
					imm.hideSoftInputFromWindow(AddFriendsTwoActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}
}