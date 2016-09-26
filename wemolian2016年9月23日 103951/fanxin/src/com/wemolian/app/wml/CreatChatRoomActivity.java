package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.GroupsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.easemob.exceptions.EaseMobException;

@SuppressLint({ "InflateParams", "SdCardPath" })
public class CreatChatRoomActivity extends BaseActivity {
	private ImageView iv_search;
	private TextView tv_checked;
	private ListView listView;
	/** 是否为一个新建的群组 */
	protected boolean isCreatingNewGroup;
	/** 是否为单选 */
	private boolean isSignleChecked;
	private PickContactAdapter contactAdapter;
	/** group中一开始就有的成员 */
	private List<String> exitingMembers = new ArrayList<String>();
	// 可滑动的显示选中用户的View
	private LinearLayout menuLinerLayout;

	// 选中用户总数,右上角显示
	int total = 0;
	private String userId = null;
	private String groupId = null;
	private String groupHxId;
	private ProgressDialog progressDialog;
	private String groupName;
	// 添加的列表
	private List<String> addList = new ArrayList<String>();
	private String localUserExt;
	private Groups group;
	String groupmembers = "";

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatroom);
		setStatus(findViewById(R.id.title));
		// hxid = LocalUserInfo.getInstance(CreatChatRoomActivity.this)
		// .getUserInfo("hxid");
		localUserExt = LocalUserInfo.getInstance(CreatChatRoomActivity.this)
				.getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		progressDialog = new ProgressDialog(this);
		groupId = getIntent().getStringExtra("groupId");
		userId = getIntent().getStringExtra("userId");
		groupHxId = getIntent().getStringExtra("groupHxId");
		
		tv_checked = (TextView) this.findViewById(R.id.tv_checked);

		if (groupId != null) {

			isCreatingNewGroup = false;
			group = WeMoLianApplication.getInstance().getGroup(groupId);
			if (group != null) {
				
				
				
				exitingMembers = SplitString.splitMembers(group.getGroupmembers());
				
				
				groupName = group.getGroupcname();
			}

		} else if (userId != null) {

			isCreatingNewGroup = true;
			exitingMembers.add(userId);
			total = 1;
			addList.add(userId);
		} else {

			isCreatingNewGroup = true;
		}

		// 获取好友列表
		final List<Contacts> alluserList = new ArrayList<Contacts>();

		Map<String, Contacts> list = WeMoLianApplication.getInstance()
				.getContactList();
		Collection<Contacts> colls = WeMoLianApplication.getInstance()
				.getContactList().values();
		for (Contacts user : WeMoLianApplication.getInstance().getContactList()
				.values()) {
			String nickName = "";
			String userCName = user.getUserCName();
			String label = user.getLabel();
			String userRemark = user.getUserRemark();
			String nick = user.getNick();
			String userEName = user.getExternalUse();
			String type = user.getType();
			String username = user.getUsername();
			// if (!((Constant.NEW_FRIENDS_USERNAME).equals(user.getNick()))
			// & !(Constant.GROUP_USERNAME.equals(user.getNick())))
			// alluserList.add(user);
			if (!((Constant.NEW_NOTICE).equals(user.getNick()))
					& !(Constant.NEW_NOTICE.equals(user.getNick())))
				alluserList.add(user);
		}
		// 对list进行排序
		Collections.sort(alluserList, new PinyinComparator() {
		});

		listView = (ListView) findViewById(R.id.list);
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View headerView = layoutInflater.inflate(R.layout.item_chatroom_header,
				null);
		TextView tv_header = (TextView) headerView.findViewById(R.id.tv_header);
		tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(CreatChatRoomActivity.this,
						ChatRoomActivity.class));
				finish();
			}

		});
		menuLinerLayout = (LinearLayout) this
				.findViewById(R.id.linearLayoutMenu);

		final EditText et_search = (EditText) this.findViewById(R.id.et_search);

		et_search.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					String str_s = et_search.getText().toString().trim();
					List<Contacts> users_temp = new ArrayList<Contacts>();
					for (Contacts user : alluserList) {
						String usernick = user.getNick();
						Log.e("usernick--->>>", usernick);
						Log.e("str_s--->>>", str_s);

						if (usernick.contains(str_s)) {

							users_temp.add(user);
						}
						contactAdapter = new PickContactAdapter(
								CreatChatRoomActivity.this,
								R.layout.item_contactlist_listview_checkbox,
								users_temp);
						listView.setAdapter(contactAdapter);

					}

				} else {
					contactAdapter = new PickContactAdapter(
							CreatChatRoomActivity.this,
							R.layout.item_contactlist_listview_checkbox,
							alluserList);
					listView.setAdapter(contactAdapter);
				}

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {

			}
		});
		listView.addHeaderView(headerView);

		contactAdapter = new PickContactAdapter(this,
				R.layout.item_contactlist_listview_checkbox, alluserList);
		listView.setAdapter(contactAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
				checkBox.toggle();

			}
		});
		/**
		 * 确定添加群
		 */
		tv_checked.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				save();
			}

		});

		// iv_search = (ImageView) this.findViewById(R.id.iv_search);
		
		
		
		
	}

	// 即时显示被选中用户的头像和昵称。

	private void showCheckImage(Bitmap bitmap, Contacts glufine) {

		if (exitingMembers.contains(glufine.getExternalUse()) && groupId != null) {
			return;
		}
		if (addList.contains(glufine.getExternalUse())) {
			return;
		}
		total++;

		// 包含TextView的LinearLayout
		// 参数设置
		android.widget.LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
				108, 108, 1);
		View view = LayoutInflater.from(this).inflate(
				R.layout.item_chatroom_header_item, null);
		ImageView images = (ImageView) view.findViewById(R.id.iv_avatar);
		TextView userName = (TextView) view.findViewById(R.id.iv_username);
		menuLinerLayoutParames.setMargins(6, 6, 6, 6);

		// 设置id，方便后面删除
		view.setTag(glufine.getExternalUse());
		if (bitmap == null) {
			images.setImageResource(R.drawable.default_useravatar);
		} else {
			images.setImageBitmap(bitmap);
		}
		if(glufine.getLabel() != null){
			userName.setText(glufine.getLabel());

			userName.setText("");
		}else if(glufine.getUserRemark() != null){
			userName.setText(glufine.getUserRemark());

		}else if(glufine.getUserCName() != null){
			userName.setText(glufine.getUserCName());

		}else{
			userName.setText(glufine.getUsername());
		}
		menuLinerLayout.addView(view, menuLinerLayoutParames);
		tv_checked.setText("确定(" + total + ")");
		// if (total > 0) {
		// if (iv_search.getVisibility() == View.VISIBLE) {
		// iv_search.setVisibility(View.GONE);
		// }
		// }
		// addList.add(glufineid.getUsername());
		addList.add(glufine.getExternalUse());
	}

	private void deleteImage(Contacts glufine) {
		View view = (View) menuLinerLayout.findViewWithTag(glufine.getExternalUse());

		menuLinerLayout.removeView(view);
		total--;
		if(total <= 0){
			total = 0;
			tv_checked.setText("确定");

		}else{
			
			tv_checked.setText("确定(" + total + ")");
		}
		addList.remove(glufine.getExternalUse());
		// if (total < 1) {
		// if (iv_search.getVisibility() == View.GONE) {
		// iv_search.setVisibility(View.VISIBLE);
		// }
		// }

	}

	/**
	 * 确认选择的members
	 * 
	 * @param v
	 */
	public void save() {
		if (addList.size() == 0) {
			Toast.makeText(CreatChatRoomActivity.this, "请选择用户",
					Toast.LENGTH_LONG).show();
			return;
		}

		// 如果只有一个用户说明只是单聊,并且不是从群组加人
		if (addList.size() == 1 && isCreatingNewGroup) {
			String userId = addList.get(0);
			Contacts user = WeMoLianApplication.getInstance().getContactList()
					.get(userId);
			if (user != null) {
				String userNick = "";
				if (user.getLabel() != null) {
					userNick = user.getLabel();
				} else if (user.getUserRemark() != null) {
					userNick = user.getUserRemark();
				} else if (user.getUserCName() != null) {
					userNick = user.getUserCName();
				} else {
					userNick = "陌生人";
				}

				String userAvatar = user.getImgName();
				startActivity(new Intent(getApplicationContext(),
						ChatActivity.class)
						.putExtra("userId", userId)
						.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME,
								userNick)
						.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID,
								user.getHxid())
						.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME,
								userAvatar));

				finish();
			}

		} else {

			if (isCreatingNewGroup) {
				progressDialog.setMessage("正在创建群聊...");
			} else {
				progressDialog.setMessage("正在加人...");
			}
			progressDialog.show();
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					/**
					 * 调用方法，新建一个群聊 或是 添加一个群成员
					 */
					creatNewGroup(addList);

				}
			});

		}

	}

	/**
	 * 创建新群组
	 * 
	 * @param newmembers
	 */
	private void creatNewGroup(List<String> members) {
		/**
		 * 群主id，即登录的用户的对外使用字段
		 */
		String groupuserId = LocalUserInfo.getInstance(
				CreatChatRoomActivity.this).getUserInfo(
				LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		/**
		 * 群名称(中文)
		 */
		String groupcname = "";
		/**
		 * 群成员
		 */

		String userCName = LocalUserInfo
				.getInstance(CreatChatRoomActivity.this).getUserInfo(
						LocalDBKey.USER_COLUMN_NAME_USERCNAME);
		String userEName = LocalUserInfo
				.getInstance(CreatChatRoomActivity.this).getUserInfo(
						LocalDBKey.USER_COLUMN_NAME_USERENAME);
		String nick;

		if (userCName != null) {
			nick = userCName;
		} else {
			nick = userEName;
		}

		//新建一个群
		groupcname = nick;
		for (int i = 0; i < members.size(); i++) {
			Contacts user = WeMoLianApplication.getInstance()
					.getContactList().get(members.get(i));
			if (i == 0) {
				groupmembers = user.getExternalUse();
			} else {
				groupmembers += SysConfig.SPLIT_STR + user.getExternalUse();
			}
			
			if (user != null) {
				if (i < 4) {
					groupcname += "、" + user.getNick();
				} else if (i == 4) {
					groupcname += "...";
					break;
				}
			}
			
		}
		if (isCreatingNewGroup) {
			groupmembers = LocalUserInfo.getInstance(
					CreatChatRoomActivity.this).getUserInfo(
							LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE)+ SysConfig.SPLIT_STR + groupmembers;
			final Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("groupuserId", groupuserId);
			resMap.put("groupcname", groupcname);
			resMap.put("groupmembers", groupmembers);
			resMap.put("groupename", "test");

			Log.e("groupName----->>>>>", groupcname);
			Log.e("groupmembers----->>>>>", LocalUserInfo.getInstance(
					CreatChatRoomActivity.this).getUserInfo(
							LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE) + groupmembers);
			

			createNewGroupToService(resMap);

		} else {
			//群已经存在，在群里面新加群成员  或是减少群成员
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("groupHxId", groupHxId);
			map.put("groupMembers", groupmembers);
			map.put("groupUserId", groupuserId);
			//添加群成员
			addGroupUsers(map);
			Log.e("groupmembers----->>>>>", groupmembers);

		}

	}


	/**
	 * 添加好友进群 
	 */
	private void addGroupUsers(Map<String, String> map) {
//		LoadDataFromServer task = new LoadDataFromServer(this, "http://192.168.0.105:8080/wemolian-service/groups/addBatchGroupFriends.do", map);
		LoadDataFromServer task = new LoadDataFromServer(this, Constant.URL_ADD_GROUP_MEMBERS, map);
		
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if(resData != null && resData.getBoolean("success")){
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						if("200".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "添加群成员成功", Toast.LENGTH_SHORT).show();
							//将群成员信息添加到本地db
							saveGroupMembersToDb();

						}else if("400".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "添加群成员失败", Toast.LENGTH_SHORT).show();
						}else if("401".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "添加的好友已经是群组的成员了", Toast.LENGTH_SHORT).show();

						}else if("403".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "邀请进群的用户不存在", Toast.LENGTH_SHORT).show();

						}else if("404".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "传入的用户id为空", Toast.LENGTH_SHORT).show();

						}else if("408".equals(code)){
							Toast.makeText(CreatChatRoomActivity.this, "传入的参数为空(没有参数传到服务器)", Toast.LENGTH_SHORT).show();

						}else{
							Toast.makeText(CreatChatRoomActivity.this, "未知的状态码", Toast.LENGTH_SHORT).show();
						}
						
						
					}else{
						Toast.makeText(CreatChatRoomActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();

					}
					
					
				}else{
					Toast.makeText(CreatChatRoomActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
				}
				
				progressDialog.dismiss();
			}
			
			
		});



		
	
	}
	
	/**
	 * 将群成员信息添加到db
	 */
	private void saveGroupMembersToDb() {
		//file/img/headImg/0b174bafa78274e6992c7420311143d1.png]
		Groups group = WeMoLianApplication.getInstance().getGroup(groupId);
		
		String images = group.getGroupUserHeadImgs();
		String members = group.getGroupmembers();
		String[] users = groupmembers.split(SysConfig.SPLIT_STR);
		for(int i = 0;i < users.length;i++){
			Contacts user = new Contacts();
			user.setExternalUse(users[i]);
			Contacts con = WeMoLianApplication.getInstance().getContact(user);
			if(con == null){
				Toast.makeText(CreatChatRoomActivity.this, "保存群成员信息出错！请重新登录！", Toast.LENGTH_SHORT).show();
				return;
			}
			if(con.getImgName() != null){
				String[] str = con.getImgName().split("/");
				String userImage = str[str.length-1];
				String name = "";
				if(con.getLabel() != null){
					name = con.getLabel();
				}else if(con.getUserRemark() != null){
					name = con.getUserRemark();
				}else{
					name = con.getUserCName();
				}
				images += SysConfig.SPLIT_STR + con.getHxid() + SysConfig.SPLIT_STR_USER + con.getExternalUse() + SysConfig.SPLIT_STR_USER + name + SysConfig.SPLIT_STR_USER + userImage;
			}
			
			if(con.getExternalUse() != null){
				members += SysConfig.SPLIT_STR + con.getExternalUse();
			}
		}
		
		group.setGroupUserHeadImgs(images);
		group.setGroupmembers(members);
		
//		Groups g = WeMoLianApplication.getInstance().getGroup(group.getGroupId());
//		WeMoLianApplication.getInstance().saveMembersToGroup(group);
//		System.out.println("aaa");
//		Groups g1 = WeMoLianApplication.getInstance().getGroup(group.getGroupId());
		WeMoLianApplication.getInstance().updateGroup(group);
		startActivity(new Intent(getApplicationContext(),
				ChatActivity.class).putExtra("groupId", groupId)
				.putExtra("chatType", ChatActivity.CHATTYPE_GROUP)
				.putExtra("groupName", groupName)
				.putExtra("groupHxId", groupHxId)
				);
	}

	/**
	 * 发送请求，新建一个群聊
	 */
	public void createNewGroupToService(Map<String, String> map) {
		LoadDataFromServer task = new LoadDataFromServer(
				CreatChatRoomActivity.this, Constant.URL_CREATE_GROUP, map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject resData) {
				// 添加群聊成功
				if (resData != null && resData.getBooleanValue("success")) {
					JSONObject data = resData.getJSONObject("data");
					if (data != null) {
						String code = data.getString("code");
						if ("200".equals(code)) {
							progressDialog.dismiss();

							Map<String,Groups> dbmap = new HashMap<String, Groups>();
							if(data.getJSONArray("data") != null && data.getJSONArray("data").size() > 0){
								JSONObject json = (JSONObject) data.getJSONArray("data").get(0);
//								JSONObject json = data.getJSONObject("data");
								
								String groupHxId = json.getString("grouphxId");
								String groupCName = json.getString("groupcname");
								String groupQRCode = json.getString("groupqrcode");
								String groupId = json.getString("groupId");
								String groupUserId = json.getString("groupuserId");
								String groupename = json.getString("groupename");
								String groupUserHeadImgs = json.getString("userHeadImg");
								String groupmembers = json.getString("groupmembers");
								int top = json.getIntValue("top");

								
								Groups groups = new Groups();
								groups.setGroupcname(groupCName);
								groups.setGroupename(groupename);
								groups.setGroupmembers(groupmembers);
								groups.setGroupqrcode(groupQRCode);
								groups.setGroupUserHeadImgs(groupUserHeadImgs);
								groups.setTop(top);
								groups.setGroupHxId(groupHxId);
								groups.setGroupId(groupId);
								groups.setOwner(groupUserId);

								dbmap.put(groups.getGroupename(), groups);
								
								// 存入内存
								WeMoLianApplication.getInstance().setGroupsList(dbmap);
								// 存入db
								GroupsDao dao = new GroupsDao(CreatChatRoomActivity.this);
								dao.saveGroup(groups);
								
								startActivity(new Intent(getApplicationContext(),
										ChatActivity.class)
										.putExtra("groupHxId", groupHxId)
										.putExtra("chatType",
												ChatActivity.CHATTYPE_GROUP)
										.putExtra("groupName", groupCName)
										.putExtra("groupQRCode", groupQRCode)
										.putExtra("groupId", groupId)
										.putExtra("groupUserId", groupUserId));
								
							}else{
								Log.e("CreatChatRoomActivity", "analysis data error!");
								Toast.makeText(CreatChatRoomActivity.this, "解析数据出错", Toast.LENGTH_SHORT).show();
							}
//							

						} else if ("400".equals(code)) {
							Log.e("CreatChatRoomActivity", "service add group error");
							Toast.makeText(CreatChatRoomActivity.this,
									"服务器添加群聊失败", Toast.LENGTH_SHORT).show();

						} else {
							Log.e("CreatChatRoomActivity", "unknown callback code");
							Toast.makeText(CreatChatRoomActivity.this, "未知的状态码",
									Toast.LENGTH_SHORT).show();

						}

					} else {
						Log.e("CreatChatRoomActivity", "callback data is null");
						Toast.makeText(CreatChatRoomActivity.this, "数据为空",
								Toast.LENGTH_SHORT).show();

					}
				} else {
					Log.e("CreatChatRoomActivity", "add group error");
					Toast.makeText(CreatChatRoomActivity.this, "添加群聊失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}


	/**
	 * adapter
	 */
	private class PickContactAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private boolean[] isCheckedArray;
		private Bitmap[] bitmaps;
		private LoadUserAvatar avatarLoader;
		private List<Contacts> list = new ArrayList<Contacts>();
		private int res;

		public PickContactAdapter(Context context, int resource,
				List<Contacts> user) {

			layoutInflater = LayoutInflater.from(context);
			avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");

			this.res = resource;
			this.list = user;
			bitmaps = new Bitmap[list.size()];
			isCheckedArray = new boolean[list.size()];

		}

		public Bitmap getBitmap(int position) {
			return bitmaps[position];
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			convertView = layoutInflater.inflate(res, null);

			ImageView iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			TextView tvHeader = (TextView) convertView
					.findViewById(R.id.header);
			final Contacts user = list.get(position);

			final String avater = user.getImgName();
			// String name = user.getNick();
			String name = "";
			if (user.getNick() != null) {
				name = user.getNick();

			} else if (user.getLabel() != null) {
				name = user.getLabel();

			} else if (user.getUserRemark() != null) {
				name = user.getUserRemark();
			} else if (user.getUserCName() != null) {
				name = user.getUserCName();
			} else {
				name = "未知用户";
			}

			String header = user.getHeader();
			final String username = user.getUsername();
			tv_name.setText(name);
			iv_avatar.setImageResource(R.drawable.default_useravatar);
			iv_avatar.setTag(avater);
			Bitmap bitmap = null;
			if (avater != null && !avater.equals("")) {
				bitmap = avatarLoader.loadImage(iv_avatar, avater,
						new ImageDownloadedCallBack() {

							@Override
							public void onImageDownloaded(ImageView imageView,
									Bitmap bitmap) {
								if (imageView.getTag() == avater) {
									imageView.setImageBitmap(bitmap);

								}
							}

						});

				if (bitmap != null) {

					iv_avatar.setImageBitmap(bitmap);

				}

				bitmaps[position] = bitmap;

			}
			if (position == 0 || header != null
					&& !header.equals(getItem(position - 1))) {
				if ("".equals(header)) {
					tvHeader.setVisibility(View.GONE);
				} else {
					tvHeader.setVisibility(View.VISIBLE);
					tvHeader.setText(header);
				}
			} else {
				tvHeader.setVisibility(View.GONE);
			}

			// 选择框checkbox
			final CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.checkbox);

			if (exitingMembers != null && exitingMembers.contains(username)) {
				checkBox.setButtonDrawable(R.drawable.btn_check);
			} else {
				checkBox.setButtonDrawable(R.drawable.check_blue);
			}

			if (addList != null && addList.contains(username)) {
				checkBox.setChecked(true);
				isCheckedArray[position] = true;
			}
			if (checkBox != null) {
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// 群组中原来的成员一直设为选中状态
						if (exitingMembers.contains(username)) {
							isChecked = true;
							checkBox.setChecked(true);
						}
						isCheckedArray[position] = isChecked;
						// 如果是单选模式
						if (isSignleChecked && isChecked) {
							for (int i = 0; i < isCheckedArray.length; i++) {
								if (i != position) {
									isCheckedArray[i] = false;
								}
							}
							contactAdapter.notifyDataSetChanged();
						}

						if (isChecked) {
							// 选中用户显示在滑动栏显示
							showCheckImage(contactAdapter.getBitmap(position),
									list.get(position));

						} else {
							// 用户显示在滑动栏删除
							deleteImage(list.get(position));

						}

					}
				});
				// 群组中原来的成员一直设为选中状态
				if (exitingMembers.contains(username)) {
					checkBox.setChecked(true);
					isCheckedArray[position] = true;
				} else {
					checkBox.setChecked(isCheckedArray[position]);
				}

			}
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public String getItem(int position) {
			if (position < 0) {
				return "";
			}

			String header = list.get(position).getHeader();

			return header;

		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}

	public void back(View view) {
		finish();
	}

	@SuppressLint("DefaultLocale")
	public class PinyinComparator implements Comparator<Contacts> {

		@SuppressLint("DefaultLocale")
		@Override
		public int compare(Contacts o1, Contacts o2) {
			// TODO Auto-generated method stub
			String py1 = o1.getHeader();
			String py2 = o2.getHeader();
			// 判断是否为空""
			if (isEmpty(py1) && isEmpty(py2))
				return 0;
			if (isEmpty(py1))
				return -1;
			if (isEmpty(py2))
				return 1;
			String str1 = "";
			String str2 = "";
			try {
				str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
				str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
			} catch (Exception e) {
				System.out.println("某个str为\" \" 空");
			}
			return str1.compareTo(str2);
		}

		private boolean isEmpty(String str) {
			return "".equals(str.trim());
		}
	}

}
