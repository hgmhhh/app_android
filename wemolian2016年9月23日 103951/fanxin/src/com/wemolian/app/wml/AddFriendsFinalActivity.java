package com.wemolian.app.wml;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMContactManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.activity.WMLAlertDialog;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddFriendsFinalActivity extends BaseActivity {
	
	
	String userExternalUse = "";
	String friendExternalUse = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriends_final);
		setStatus(findViewById(R.id.title));
		final String hxid = this.getIntent().getStringExtra("friendHxId");
		userExternalUse = this.getIntent().getStringExtra("userExternalUse");
		friendExternalUse = this.getIntent().getStringExtra("friendExternalUse");
		TextView tv_send = (TextView) this.findViewById(R.id.tv_send);
		final EditText et_reason = (EditText) this.findViewById(R.id.et_reason);

		tv_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addContact(hxid, et_reason.getText().toString().trim());
			}

		});
	}

	/**
	 * 添加contact
	 * 
	 * @param view
	 */
	@SuppressLint("ShowToast")
	public void addContact(final String friendHxId, final String myreason) {
		if (friendHxId == null || friendHxId.equals("")) {
			return;
		}

		if (WeMoLianApplication.getInstance().getUserName().equals(friendHxId)) {
			startActivity(new Intent(this, WMLAlertDialog.class).putExtra("msg",
					"不能添加自己"));
			return;
		}

		if (WeMoLianApplication.getInstance().getContactList()
				.containsKey(friendHxId)) {
			startActivity(new Intent(this, WMLAlertDialog.class).putExtra("msg",
					"此用户已是你的好友"));
			return;
		}

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在发送请求...");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userExternalUse);
		map.put("friendId", friendExternalUse);
		LoadDataFromServer task = new LoadDataFromServer(
				AddFriendsFinalActivity.this, Constant.URL_ADD_FRIEND_PRE, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if(resData.getBoolean("success")){
					//请求数据成功
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						//返回200  表示首次添加成功
						if("200".equals(code)){
							addToEasemob(progressDialog, friendHxId, myreason);
							//返回400 表示首次添加失败
						}else if("400".equals(code)){
							Toast.makeText(AddFriendsFinalActivity.this, "服务器添加失败！请重试！", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(AddFriendsFinalActivity.this, "未知的状态码", Toast.LENGTH_SHORT).show();
						}
						
						
					}else{
						Toast.makeText(AddFriendsFinalActivity.this, "请求返回的数据为空！", Toast.LENGTH_SHORT).show();
					}
				}else{
					
				}
			}
		});



	}

	/**
	 * 添加到环信
	 */
	public void addToEasemob(final ProgressDialog progressDialog,
			final String friendHxId, final String myreason) {
//		new Thread(new Runnable() {
//			public void run() {

				try {
					// 在reason封装请求者的昵称/头像/时间等信息，在通知中显示

					String userCName = LocalUserInfo.getInstance(
							AddFriendsFinalActivity.this).getUserInfo(
							LocalDBKey.USER_COLUMN_NAME_USERCNAME);
					String userEName = LocalUserInfo.getInstance(
							AddFriendsFinalActivity.this).getUserInfo(
							LocalDBKey.USER_COLUMN_NAME_USERENAME);
					String name = null;
					if (userCName == null) {
						name = userEName;
					} else {
						name = userCName;
					}
					String headImg = LocalUserInfo.getInstance(
							AddFriendsFinalActivity.this).getUserInfo(
							LocalDBKey.USER_COLUMN_NAME_HEADIMG);
					long time = System.currentTimeMillis();
					String myreason_temp = myreason;
					if (myreason == null || myreason.equals("")) {
						myreason_temp = "请求加你为好友";
					}
					String reason = name + "66split88" + headImg + "66split88"
							+ String.valueOf(time) + "66split88"
							+ myreason_temp;
					EMContactManager.getInstance().addContact(friendHxId,
							reason);
					runOnUiThread(new Runnable() {
						@SuppressLint("ShowToast")
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(AddFriendsFinalActivity.this,
									"发送请求成功,等待对方验证", Toast.LENGTH_SHORT).show();

							finish();
						}
					});

				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(AddFriendsFinalActivity.this,
									"请求添加好友失败:" + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					});
				}
//			}
//		}).start();
	}

	public void back(View view) {

		finish();
	}
}
