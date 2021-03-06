package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.activity.WMLAlertDialog;
import com.wemolian.app.db.GroupsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.utils.ToArrayList;
import com.wemolian.app.utils.UserToContacts;
import com.wemolian.app.widget.ExpandGridView;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.TopUser;
import com.wemolian.app.wml.others.TopUserDao;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.NetUtils;

@SuppressLint({ "SimpleDateFormat", "SdCardPath", "ClickableViewAccessibility",
		"InflateParams" })
public class ChatRoomSettingActivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_groupname;
	// 成员总数

	private TextView tv_m_total;
	// 成员总数
	int m_total = 0;
	// 成员列表
	private ExpandGridView gridview;
	// 修改群名称、置顶、、、、
	private RelativeLayout re_change_groupname;
	private RelativeLayout rl_switch_chattotop;
	private RelativeLayout rl_switch_block_groupmsg;
	private RelativeLayout re_clear;

	// 状态变化
	private ImageView iv_switch_chattotop;
	private ImageView iv_switch_unchattotop;
	private ImageView iv_switch_block_groupmsg;
	private ImageView iv_switch_unblock_groupmsg;
	// 删除并退出

	private Button exitBtn;

	
	// 群名称
	private String group_name;
	// 是否是管理员
	boolean is_admin = false;
	List<Contacts> members = new ArrayList<Contacts>();
	String longClickUsername = null;

	private String groupId;
	private String groupHxId;
	/**
	 * 用户的对外使用字段
	 */
	private String userId;
	private String userHxId;
	private Groups group;
	private GridAdapter adapter;

	public static ChatRoomSettingActivity instance;
	private ProgressDialog progressDialog;
	private JSONObject jsonObject;

	// 置顶列表
	private Map<String, TopUser> topMap = new HashMap<String, TopUser>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_groupchatsetting_activity);
		setStatus(findViewById(R.id.title));
		instance = this;

		topMap = WeMoLianApplication.getInstance().getTopUserList();
		initView();
		initData();
		updateGroup();
	}

	private void initView() {
		progressDialog = new ProgressDialog(ChatRoomSettingActivity.this);
		tv_groupname = (TextView) findViewById(R.id.tv_groupname);

		tv_m_total = (TextView) findViewById(R.id.tv_m_total);

		gridview = (ExpandGridView) findViewById(R.id.gridview);

		re_change_groupname = (RelativeLayout) findViewById(R.id.re_change_groupname);
		rl_switch_chattotop = (RelativeLayout) findViewById(R.id.rl_switch_chattotop);
		rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
		re_clear = (RelativeLayout) findViewById(R.id.re_clear);

		iv_switch_chattotop = (ImageView) findViewById(R.id.iv_switch_chattotop);
		iv_switch_unchattotop = (ImageView) findViewById(R.id.iv_switch_unchattotop);
		iv_switch_block_groupmsg = (ImageView) findViewById(R.id.iv_switch_block_groupmsg);
		iv_switch_unblock_groupmsg = (ImageView) findViewById(R.id.iv_switch_unblock_groupmsg);
		exitBtn = (Button) findViewById(R.id.btn_exit_grp);
		
	}

	private void initData() {
		/**
		 * 用户的环信id
		 */
		userHxId = LocalUserInfo.getInstance(ChatRoomSettingActivity.this)
				.getUserInfo(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
		// 获取传过来的groupid
		groupHxId = getIntent().getStringExtra("groupHxId");
		groupId = getIntent().getStringExtra("groupId");
		userId = LocalUserInfo.getInstance(ChatRoomSettingActivity.this)
				.getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		// 获取本地该群数据
		group = WeMoLianApplication.getInstance().getGroup(groupId);
		if (group == null) {
			// 去网络中查找该群
			// group = EMGroupManager.getInstance()
			// .getGroupFromServer(groupId);
			// 到服务器查询群信息
			loadGroupFromService();

		}
		// 获取封装的群名（里面封装了显示的群名和群组成员的信息）
		// String group_name_temp = group.getGroupName();
		// 转化成json，然后解析
		// jsonObject = JSONObject.parseObject(group_name_temp);
		// 获取显示的群名
		group_name = group.getGroupcname();
		// 获取群成员信息
		String groupUsers = group.getGroupUserHeadImgs();
		List<Contacts> userStrs = null;
		if(groupUsers != null){
//			userStrs = userStr.split(",");
			userStrs = SplitString.splitToContacts(groupUsers);
			m_total = userStrs.size();
		}
		
		
		
		
		tv_groupname.setText(group_name);
		
		tv_m_total.setText("(" + String.valueOf(m_total) + ")");
		// 解析群组成员信息
//		for (int i = 0; i < m_total; i++) {
//			Contacts user = new Contacts();
//			user.setLabel(strs[i].split("#")[0]);
//			user.setImgName(strs[i].split("#")[1]);
//			// user.setUsername(json.getString("hxid"));
//			// // user.setAvatar(json.getString("avatar"));
//			// user.setNick(json.getString("nick"));
//			members.add(user);
//		}
		
		for(int i = 0;i<userStrs.size(); i++){
//			String userExternalUse = userStrs.get(i).getExternalUse();
//			String userHxId = userStrs.get(i).getHxid();
//			String userCName = userStrs.get(i).getUserCName();
//			String userImgName = userStrs.get(i).getImgName();
//					
//			Contacts user = new Contacts();
//			user.setExternalUse(userExternalUse);
//			user = WeMoLianApplication.getInstance().getContact(user);
//			if(user == null){
//				 //如果为登录帐号
//				 if(userExternalUse.equals(userId)){
//					User u = WeMoLianApplication.getInstance().getUserInfo(userId);
//					 
//					members.add(UserToContacts.userToContacts(u));
//				 }
//			}else{
				
				members.add(userStrs.get(i));
//			}
			
		}
		
		// 显示群组成员头像和昵称
		showMembers(members);
		// 判断是否是群主，是群主有删成员的权限，并显示减号按钮
		if (userId.equals(group.getOwner())) {
			is_admin = true;
		}

		re_change_groupname.setOnClickListener(this);
		rl_switch_chattotop.setOnClickListener(this);
		rl_switch_block_groupmsg.setOnClickListener(this);

		re_clear.setOnClickListener(this);

		exitBtn.setOnClickListener(this);

	}

	/**
	 * 拉取群信息 --当群信息在本地数据库不存在时，才会拉取
	 */
	private void loadGroupFromService() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("grouphxId", groupHxId);
		map.put("groupId", groupId);
		map.put("groupuserId", userId);
		LoadDataFromServer task = new LoadDataFromServer(
				ChatRoomSettingActivity.this, Constant.URL_FIND_GROUP, map);

		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject resData) {
				if (resData != null && resData.getBoolean("success")) {
					JSONObject data = resData.getJSONObject("data");
					if (data != null && ("200".equals(data.getString("code")))) {
						JSONObject groups = data.getJSONObject("groups");
						// 将群信息保存到本地db
						saveGroup(groups);
					} else if (data != null
							&& ("401".equals(data.getString("code")))) {
						Toast.makeText(ChatRoomSettingActivity.this,
								"该群已经被解散...", Toast.LENGTH_SHORT).show();
						setResult(100);
						finish();
						return;
					}
				} else {
					Toast.makeText(ChatRoomSettingActivity.this, "该群已经被解散...",
							Toast.LENGTH_SHORT).show();
					setResult(100);
					finish();
					return;
				}
			}

		});

		// Toast.makeText(ChatRoomSettingActivity.this, "该群已经被解散...",
		// Toast.LENGTH_SHORT).show();
		// setResult(100);
		// finish();
		// return;

	}

	/**
	 * 将单个群信息保存到本地db
	 */
	private void saveGroup(JSONObject json) {
		String groupename = json.getString("groupename");
		String groupUserHeadImgs = json.getString("groupUserHeadImgs");
		String groupqrcode = json.getString("groupqrcode");
		String groupmembers = json.getString("groupmembers");
		String groupcname = json.getString("groupcname");
		String groupHxId = json.getString("grouphxId");
		String groupId = json.getString("groupId");
		String owner = json.getString("groupuserId");
		int top = json.getInteger("top");
		String groupNewNotDis = json.getString("newsNotDis");

		group.setGroupcname(groupcname);
		group.setGroupename(groupename);
		group.setGroupmembers(groupmembers);
		group.setGroupqrcode(groupqrcode);
		group.setGroupUserHeadImgs(groupUserHeadImgs);
		group.setTop(top);
		group.setGroupHxId(groupHxId);
		group.setGroupId(groupId);
		group.setOwner(owner);
		group.setNewNotDis(groupNewNotDis);

		Map<String, Groups> map = new HashMap<String, Groups>();
		map.put(group.getGroupename(), group);
		// 存入内存
		WeMoLianApplication.getInstance().setGroupsList(map);
		// 存入db
		GroupsDao dao = new GroupsDao(ChatRoomSettingActivity.this);
		dao.saveGroup(group);
	}

	// 显示群成员头像昵称的gridview
	@SuppressLint("ClickableViewAccessibility")
	private void showMembers(List<Contacts> members) {
		adapter = new GridAdapter(this, members);
		gridview.setAdapter(adapter);

		// 设置OnTouchListener,为了让群主方便地推出删除模》
		gridview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (adapter.isInDeleteMode) {
						adapter.isInDeleteMode = false;
						adapter.notifyDataSetChanged();
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_block_groupmsg: // 屏蔽群组
			if (iv_switch_block_groupmsg.getVisibility() == View.VISIBLE) {
				System.out.println("change to unblock group msg");
				try {
					EMGroupManager.getInstance().unblockGroupMessage(groupId);
					iv_switch_block_groupmsg.setVisibility(View.INVISIBLE);
					iv_switch_unblock_groupmsg.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
					// todo: 显示错误给用户
				}
			} else {
				System.out.println("change to block group msg");
				try {
					EMGroupManager.getInstance().blockGroupMessage(groupId);
					iv_switch_block_groupmsg.setVisibility(View.VISIBLE);
					iv_switch_unblock_groupmsg.setVisibility(View.INVISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
					// todo: 显示错误给用户
				}
			}
			break;

		case R.id.re_clear: // 清空聊天记录
			progressDialog.setMessage("正在清空群消息...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
			// 按照你们要求必须有个提示，防止记录太少，删得太快，不提示
			clearGroupHistory();
			break;
		case R.id.re_change_groupname:
			showNameAlert();
			break;
		case R.id.rl_switch_chattotop:
			// 当前状态是已经置顶,点击后取消置顶
            if (iv_switch_chattotop.getVisibility() == View.VISIBLE) {

                iv_switch_chattotop.setVisibility(View.INVISIBLE);
                iv_switch_unchattotop.setVisibility(View.VISIBLE);

                if (topMap.containsKey(userId)) {

                    topMap.remove(userId);
                    TopUserDao topUserDao = new TopUserDao(
                    		ChatRoomSettingActivity.this);

                    topUserDao.deleteTopUser(userId);

                }

            } else {

                // 当前状态是未置顶点击后置顶

                iv_switch_chattotop.setVisibility(View.VISIBLE);
                iv_switch_unchattotop.setVisibility(View.INVISIBLE);

                if (!topMap.containsKey(userId)) {
                    TopUser topUser = new TopUser();
                    topUser.setTime(System.currentTimeMillis());
                    // 1---表示是群组0----个人
                    topUser.setType(0);
                    topUser.setUserName(userId);
                    Map<String, TopUser> map = new HashMap<String, TopUser>();
                    map.put(userId, topUser);
                    topMap.putAll(map);
                    TopUserDao topUserDao = new TopUserDao(
                            ChatRoomSettingActivity.this);
                    topUserDao.saveTopUser(topUser);

                }

            }

			break;

			//退出群聊
		case R.id.btn_exit_grp:
			User u = WeMoLianApplication.getInstance().getUserInfo(userId);
			deleteMembersFromGroup(UserToContacts.userToContacts(u));
			break;

		default:
			break;
		}

	}

	/**
	 * 清空群聊天记录
	 */
	public void clearGroupHistory() {

		EMChatManager.getInstance().clearConversation(group.getGroupId());
		progressDialog.dismiss();

	}

	/**
	 * 群组成员gridadapter
	 * 
	 * @author admin_new
	 * 
	 */
	private class GridAdapter extends BaseAdapter {

		public boolean isInDeleteMode;
		private List<Contacts> objects;
		Context context;
		private LoadUserAvatar avatarLoader;

		public GridAdapter(Context context, List<Contacts> objects) {

			this.objects = objects;
			this.context = context;
			isInDeleteMode = false;
			avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
		}

		@Override
		public View getView(final int position, View convertView,
				final ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.social_chatsetting_gridview_item, null);
			}
			ImageView iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			TextView tv_username = (TextView) convertView
					.findViewById(R.id.tv_username);
			TextView tv_user_hxId = (TextView) convertView.findViewById(R.id.tv_user_hxid);
			TextView tv_user_externaluse  = (TextView) convertView.findViewById(R.id.tv_user_externaluse);
			ImageView badge_delete = (ImageView) convertView
					.findViewById(R.id.badge_delete);

			// 最后一个item，减人按钮

			if (position == getCount() - 1 && is_admin) {
				tv_username.setText("");
				badge_delete.setVisibility(View.GONE);
				iv_avatar.setImageResource(R.drawable.icon_btn_deleteperson);

				if (isInDeleteMode) {
					// 正处于删除模式下，隐藏删除按钮
					convertView.setVisibility(View.GONE);
				} else {

					convertView.setVisibility(View.VISIBLE);
				}

				iv_avatar.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						isInDeleteMode = true;
						notifyDataSetChanged();
					}

				});

			} else if ((is_admin && position == getCount() - 2)
					|| (!is_admin && position == getCount() - 1)) { // 添加群组成员按钮
				tv_username.setText("");
				badge_delete.setVisibility(View.GONE);
				iv_avatar.setImageResource(R.drawable.jy_drltsz_btn_addperson);
				// 正处于删除模式下,隐藏添加按钮
				if (isInDeleteMode) {
					convertView.setVisibility(View.GONE);
				} else {
					convertView.setVisibility(View.VISIBLE);
				}
				iv_avatar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						// 进入选人页面
						startActivity((new Intent(ChatRoomSettingActivity.this,
								CreatChatRoomActivity.class).putExtra(
								"groupId", groupId)
								.putExtra("userId", userId)
								.putExtra("groupHxId", groupHxId)));

					}
				});
			}

			else { // 普通item，显示群组成员

				final Contacts user = objects.get(position);
				// final String useravatar = user.getAvatar();
				final String useravatar = user.getImgName();
				String name = "";
				if(user.getLabel() != null){
					name = user.getLabel();
				}else if(user.getUserRemark() != null){
					name = user.getUserRemark();
				}else if(user.getUserCName() != null){
					name = user.getUserCName();
				}else{
					name = "未命名";
				}
				
				
				tv_username.setText(name);
				tv_user_externaluse.setText(user.getExternalUse());
				tv_user_hxId.setText(user.getHxid());
				iv_avatar.setImageResource(R.drawable.default_useravatar);
				iv_avatar.setTag(useravatar);
				if (useravatar != null && !useravatar.equals("")) {
					Bitmap bitmap = avatarLoader.loadImage(iv_avatar,
							useravatar, new ImageDownloadedCallBack() {

								@Override
								public void onImageDownloaded(
										ImageView imageView, Bitmap bitmap) {
									if (imageView.getTag() == useravatar) {
										imageView.setImageBitmap(bitmap);

									}
								}

							});

					if (bitmap != null) {

						iv_avatar.setImageBitmap(bitmap);

					}

				}

				// 群组成员的头像都用默认头像，需由开发者自己去设置头像
				if (isInDeleteMode) {
					// 如果是删除模式下，显示减人图标
					convertView.findViewById(R.id.badge_delete).setVisibility(
							View.VISIBLE);
				} else {
					convertView.findViewById(R.id.badge_delete).setVisibility(
							View.INVISIBLE);
				}
				iv_avatar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isInDeleteMode) {
							String s = EMChatManager.getInstance().getCurrentUser();
							// 如果是删除自己，return
							if (EMChatManager.getInstance().getCurrentUser()
									.equals(user.getExternalUse())) {
								startActivity(new Intent(
										ChatRoomSettingActivity.this,
										WMLAlertDialog.class).putExtra("msg",
										"不能删除自己"));
								return;
							}
							if (!NetUtils.hasNetwork(getApplicationContext())) {
								Toast.makeText(
										getApplicationContext(),
										getString(R.string.network_unavailable),
										Toast.LENGTH_SHORT).show();
								return;
							}

							deleteMembersFromGroup(user);
						} else {
							Toast.makeText(ChatRoomSettingActivity.this, "该功能正在开发中", Toast.LENGTH_SHORT).show();
							// 正常情况下点击user，可以进入用户详情或者聊天页面等等
							// startActivity(new
							// Intent(GroupDetailsActivity.this,
							// ChatActivity.class).putExtra("userId",
							// user.getUsername()));

						}
					}

				});

			}
			return convertView;
		}

		@Override
		public int getCount() {
			if (is_admin) {
				return objects.size() + 2;
			} else {

				return objects.size() + 1;

			}

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return objects.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}

	protected void updateGroup() {

		if (group != null) {
			System.out.println("group msg is blocked:" + group.getNewNotDis());
			// 设置初始屏蔽初始状态
			if (SysConfig.NEW_NOT_DIS_TRUE.equals(group.getNewNotDis())) {
				iv_switch_block_groupmsg.setVisibility(View.VISIBLE);
				iv_switch_unblock_groupmsg.setVisibility(View.INVISIBLE);
			} else {
				iv_switch_block_groupmsg.setVisibility(View.INVISIBLE);
				iv_switch_unblock_groupmsg.setVisibility(View.VISIBLE);
			}
			// 设置置顶的初始状态

			if (topMap.containsKey(group.getGroupId())) {

				// 当前状态是已经置顶

				iv_switch_chattotop.setVisibility(View.VISIBLE);
				iv_switch_unchattotop.setVisibility(View.INVISIBLE);

			} else {
				// 当前状态是未置顶
				iv_switch_chattotop.setVisibility(View.INVISIBLE);
				iv_switch_unchattotop.setVisibility(View.VISIBLE);

			}
		}

	}

	private void showNameAlert() {

		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.social_alertdialog);
		// 设置能弹出输入法
		dlg.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		// 为确认按钮添加事件,执行退出应用操作
		Button ok = (Button) window.findViewById(R.id.btn_ok);
		final EditText ed_name = (EditText) window.findViewById(R.id.ed_name);

		ok.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				
				final String newName = ed_name.getText().toString().trim();

				if (TextUtils.isEmpty(newName)) {
					return;
				}

				runOnUiThread(new Runnable() {
					public void run() {
						progressDialog.setMessage("正在更新群名称...");
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.show();
						/**
						 * 更新群信息
						 */
						updateGroupToService(newName);
						
						dlg.cancel();
						
					}
				});
			}

		});
		// 关闭alert对话框架
		Button cancel = (Button) window.findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dlg.cancel();
			}
		});

	}

	/**
	 * 向服务器更新群数据
	 */
	private void updateGroupToService(final String newName) {
		String url = Constant.URL_MEMBER_UPDATE_GROUP;
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("userId", userId);
		map.put("groupcname", newName);
		map.put("grouphxId", groupHxId);
		map.put("groupId", groupId);
		
//		map.put("membername", "");
//		map.put("top", "");
//		map.put("newsNotDis", "");
		//如果是群主
		if(is_admin){
			url = Constant.URL_OWNER_UPDATE_GROUP;
		}

		
		
		LoadDataFromServer task = new LoadDataFromServer(ChatRoomSettingActivity.this, url, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				
				if(resData != null && resData.getBooleanValue("success")){
					JSONObject data = resData.getJSONObject("data");
					String code = data.getString("code");
					if("200".equals(code)){
						tv_groupname.setText(newName);
						group_name = newName;
						Groups g = new Groups();
						g.setGroupId(groupId);
						g.setGroupcname(newName);
						WeMoLianApplication.getInstance().updateGroup(g);
						Toast.makeText(ChatRoomSettingActivity.this, "更新群信息成功", Toast.LENGTH_SHORT).show();
					}else if("401".equals(code)){
						Toast.makeText(ChatRoomSettingActivity.this, "不是群主，不能更新数据", Toast.LENGTH_SHORT).show();

					}else if("400".equals(code)){
						Toast.makeText(ChatRoomSettingActivity.this, "更新群信息失败", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(ChatRoomSettingActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}
		});
		
		
	}

	/**
	 * 删除群成员
	 * 
	 * @param username
	 */
	protected void deleteMembersFromGroup(final Contacts user) {
		final ProgressDialog deleteDialog = new ProgressDialog(
				ChatRoomSettingActivity.this);
		Map<String, String> map = new HashMap<String, String>();
		String groupUserHeadImgs = group.getGroupUserHeadImgs();
		String groupCName = group.getGroupcname();
		List<Contacts> members = SplitString.splitToContacts(groupUserHeadImgs);
		String[] groupCNames = groupCName.split("、");
		//将String数组转换为list集合
		List<String> cnameList = ToArrayList.StringsToArrayList(groupCNames);
		int size = cnameList.size();
		int where = 0;
		String groupCName_temp = null;
		//groupCName > 1 表示以群成员的cname作为群名称  只有以群成员的cname作为群名称的时候，才能更改群名字
		if(groupCNames.length > 1){
			if(members.size() > cnameList.size()){
				for(int i= 0;i<cnameList.size();i++){
					for(int j = 0 ;j < members.size(); j++){
						if((cnameList.get(i)).equals(members.get(j).getUserCName())){
							members.remove(j);
							break;
						}
					}
					if((cnameList.get(i)).equals(user.getUserCName())){
						where = i;
					}
					
				}
				cnameList.set(where, members.get(0).getUserCName());
			
			}else{
				for(int i= 0;i<cnameList.size();i++){
					if(cnameList.get(i).equals(user.getUserCName())){
						cnameList.remove(i);
					}
				}
			}
			
			for (int i = 0; i < cnameList.size(); i++) {
				if(i == 0){
					groupCName_temp = cnameList.get(i);
				}else{
					groupCName_temp += "、" + cnameList.get(i);
				}
			}
			
		}else{
			//没有根据顿号截取到群名称，说明群名称已经被重新命名过
			groupCName_temp = group.getGroupcname();
		}
		
		
		/**
		 * groupHxId
		 * userId
		 * groupMembers
		 * groupCName
		 * groupId
		 */
		map.put("groupHxId", groupHxId);
		map.put("userId", userId);
		map.put("groupMember", user.getExternalUse());
		map.put("groupId", groupId);
		map.put("groupCName", groupCName_temp);
		
		
		
		// 当删除的是自己的时候,意味着就是退群。群主退群是要解散群的，所以要有判断
		if (userId.equals(user.getExternalUse())) {
			deleteDialog.setMessage("正在退出...");
			deleteDialog.setCanceledOnTouchOutside(false);
			deleteDialog.show();

			removeToService("removeUser",null, map,deleteDialog);

		}
		// 群主删群成员操作
		else {
			deleteDialog.setMessage("正在移除...");
			deleteDialog.setCanceledOnTouchOutside(false);
			deleteDialog.show();
			removeToService("removeMember",user, map,deleteDialog);


		}

	}

	
	
	/**
	 * 发送请求删除群成员、退群等
	 * @param map  所需要的参数
	 * @param tag  标识
	 *       
	 */
	public void removeToService(final String tag,final Contacts user,Map<String, String> map,final ProgressDialog deleteDialog){
		LoadDataFromServer task = new LoadDataFromServer(ChatRoomSettingActivity.this, Constant.URL_REMOVE_GROUP_MEMBERS, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if(resData!= null && resData.getBooleanValue("success")){
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						if("200".equals(code)){
							//用户自己退出  或者是 群主解散群  ---> 二者都需要将db中，群的信息删除
							if("removeUser".equals(tag)){
								WeMoLianApplication.getInstance().delGroup(groupId);
								//退出后，需要将本地的群数据更新
								Toast.makeText(ChatRoomSettingActivity.this, "退出成功",
										Toast.LENGTH_LONG).show();
								startActivity(new Intent(getApplicationContext(),ChatRoomActivity.class));
	
								//群主移除群成员
							}else if("removeMember".equals(tag)){
								//在db中，移除群成员信息
								removeMember(user);
							}
							// 非群主退出

						}else if("401".equals(code)){
							Toast.makeText(ChatRoomSettingActivity.this, "删除的成员在该群中不存在", Toast.LENGTH_SHORT).show();

						}else if("400".equals(code)){
							Toast.makeText(ChatRoomSettingActivity.this, "移除失败", Toast.LENGTH_SHORT).show();

						}else if("502".equals(code)){
							Toast.makeText(ChatRoomSettingActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();

						}else if("408".equals(code)){
							Toast.makeText(ChatRoomSettingActivity.this, "传入的参数为空(没有参数传到服务器)", Toast.LENGTH_SHORT).show();

						}else{
							Toast.makeText(ChatRoomSettingActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
						}
						
						
						
					}else{
						Toast.makeText(ChatRoomSettingActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
					}
					
					
					
				}else{
					Toast.makeText(ChatRoomSettingActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
				}
				deleteDialog.dismiss();
			}

			
		});
		
	}
	
	public void removeMember(Contacts user) {
		String groupUserHeadImgs = group.getGroupUserHeadImgs();
		String groupMembers = group.getGroupmembers();
		String newgroupUserHeadImgs = null;
		String newgroupMembers = null;
		
		
		//在db中，删除被移除的成员的数据
		List<String> members = SplitString.splitMembers(groupMembers);
		for (int i = 0; i < members.size(); i++) {
			if(members.get(i).equals(user.getExternalUse())){
				members.remove(i);
			}
			if(i == 0){
				newgroupMembers = members.get(i);
			}else{
				newgroupMembers += SysConfig.SPLIT_STR + members.get(i);
			}
		}
		//将移除了的那条信息排除后，更新到db中
		for (int i = 0; i < members.size(); i++) {
			if(i == 0){
				newgroupMembers = members.get(i);
			}else{
				newgroupMembers += SysConfig.SPLIT_STR + members.get(i);
			}
		}
		List<Contacts> memberImg = SplitString.splitToContacts(groupUserHeadImgs);
		for (int i = 0; i < memberImg.size(); i++) {
			Contacts c = memberImg.get(i);
			if (c.getExternalUse().equals(user.getExternalUse())) {
				memberImg.remove(i);
			}
			if(i == 0){
				newgroupUserHeadImgs = c.getHxid() + SysConfig.SPLIT_STR + c.getExternalUse() 
						+  SysConfig.SPLIT_STR + c.getUserCName() + SysConfig.SPLIT_STR 
						+  c.getImgName();
			}else{
				newgroupUserHeadImgs = SysConfig.SPLIT_STR_USER + c.getHxid() + SysConfig.SPLIT_STR + c.getExternalUse() 
						+  SysConfig.SPLIT_STR + c.getUserCName() + SysConfig.SPLIT_STR 
						+  c.getImgName();
			}
		}
		//将移除的信息排除后，更新到db中  
		for (int i = 0; i < memberImg.size(); i++) {
			Contacts c = memberImg.get(i);
			if(i == 0){
				newgroupUserHeadImgs = c.getHxid() + SysConfig.SPLIT_STR + c.getExternalUse() 
						+  SysConfig.SPLIT_STR + c.getUserCName() + SysConfig.SPLIT_STR 
						+  c.getImgName();
			}else{
				newgroupUserHeadImgs = SysConfig.SPLIT_STR_USER + c.getHxid() + SysConfig.SPLIT_STR + c.getExternalUse() 
						+  SysConfig.SPLIT_STR + c.getUserCName() + SysConfig.SPLIT_STR 
						+  c.getImgName();
			}
		}
		Groups g = new Groups();
		g.setGroupUserHeadImgs(newgroupUserHeadImgs);
		g.setGroupmembers(newgroupMembers);
		g.setGroupId(groupId);
		WeMoLianApplication.getInstance().updateGroup(g);
		Toast.makeText(ChatRoomSettingActivity.this, "移除成功",
				Toast.LENGTH_LONG).show();
//		startActivity(new Intent(getApplicationContext(),ChatRoomActivity.class));
	}
	
	
	public void back(View view) {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

}
