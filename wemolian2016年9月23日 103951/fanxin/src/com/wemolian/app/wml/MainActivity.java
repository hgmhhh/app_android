package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMNotifier;
import com.easemob.chat.GroupChangeListener;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.GroupsDao;
import com.wemolian.app.db.InviteMessgeDao;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.InviteMessage;
import com.wemolian.app.domain.InviteMessage.InviteMesageStatus;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.lock.pattern.GuideGesturePasswordActivity;
import com.wemolian.app.lock.pattern.UnlockGesturePasswordActivity;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class MainActivity extends BaseActivity {

	boolean runOneTime = true;

	private boolean haveLocked = false;
	// 未读消息textview
	private TextView unreadLabel;
	// 未读通讯录textview
	TextView unreadAddressLable;
	protected static final String TAG = "MainActivity";

	private Fragment[] fragments;
	//消息
	public FragmentCoversation homefragment;
	//通讯录
	private FragmentFriends contactlistfragment;
	//发现
	private FragmentFind findfragment;
	//我的
	private FragmentProfile profilefragment;
	//功能
	private FragmentFunc funcfragment;
	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;
	private NewMessageBroadcastReceiver msgReceiver;
	private android.app.AlertDialog.Builder conflictBuilder;
	private android.app.AlertDialog.Builder accountRemovedBuilder;
	private boolean isConflictDialogShow;
	private boolean isAccountRemovedDialogShow;
	// 账号在别处登录
	public boolean isConflict = false;
	// 账号被移除
	private boolean isCurrentAccountRemoved = false;

	private InviteMessgeDao inviteMessgeDao;
	private ContactsDao contactsDao;

	private ImageView iv_add;
	private ImageView iv_search;

	/**
	 * 检查当前用户是否被删除
	 */
	public boolean getCurrentAccountRemoved() {
		return isCurrentAccountRemoved;
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED,
						false)) {
			// 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
			// 三个fragment里加的判断同理
			WeMoLianApplication.getInstance().logout(null);
			finish();
			startActivity(new Intent(this, LoginActivity.class));
			return;
		} else if (savedInstanceState != null
				&& savedInstanceState.getBoolean("isConflict", false)) {
			// 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
			// 三个fragment里加的判断同理
			finish();
			startActivity(new Intent(this, LoginActivity.class));
			return;
		}

		setContentView(R.layout.activity_main_temp);
		
		setStatus(findViewById(R.id.title));
		initView();

		if (getIntent().getBooleanExtra("conflict", false)
				&& !isConflictDialogShow) {
			showConflictDialog();
		} else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false)
				&& !isAccountRemovedDialogShow) {
			showAccountRemovedDialog();
		}

		iv_add = (ImageView) this.findViewById(R.id.iv_add);
		// iv_search = (ImageView) this.findViewById(R.id.iv_search);
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				 Toast.makeText(MainActivity.this, "进入主页面",
//				 Toast.LENGTH_SHORT).show();

				AddPopWindow addPopWindow = new AddPopWindow(MainActivity.this);
				addPopWindow.showPopupWindow(iv_add);
			}

		});

		if (!WeMoLianApplication.getInstance().getLockPatternUtils()
				.savedPatternExists()) {
			// startActivity(new Intent(this,
			// GuideGesturePasswordActivity.class));
			// finish();
			return;
		} else {
			String unlock = getIntent().getStringExtra("locked");
			if ("unlock".equals(unlock)) {
				return;
			} else {
				// Toast.makeText(MainActivity.this, "有手势密码",
				// Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this,
						UnlockGesturePasswordActivity.class).putExtra(
						"activity", "main"));
				finish();
			}
		}

		// iv_search.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		//
		// });

		// 全部在线人数 主页面标题中的文字点击效果
		// TextView tv_online = (TextView) this.findViewById(R.id.tv_online);
		// tv_online.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// startActivity(new Intent(MainActivity.this,
		// LasterLoginUserActivity.class));
		// }
		//
		// });

	}

	private void initView() {
		unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
		unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);

		homefragment = new FragmentCoversation();
		contactlistfragment = new FragmentFriends();
		findfragment = new FragmentFind();
		profilefragment = new FragmentProfile();
		funcfragment = new FragmentFunc();
		fragments = new Fragment[] { homefragment, contactlistfragment,
				funcfragment, findfragment, profilefragment };
		imagebuttons = new ImageView[5];
		imagebuttons[0] = (ImageView) findViewById(R.id.ib_weimolian);
		imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
		imagebuttons[2] = (ImageView) findViewById(R.id.ib_func);
		imagebuttons[3] = (ImageView) findViewById(R.id.ib_find);
		imagebuttons[4] = (ImageView) findViewById(R.id.ib_profile);

		imagebuttons[0].setSelected(true);
		textviews = new TextView[5];
		textviews[0] = (TextView) findViewById(R.id.tv_weimolian);
		textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
		textviews[2] = (TextView) findViewById(R.id.tv_func);
		textviews[3] = (TextView) findViewById(R.id.tv_find);
		textviews[4] = (TextView) findViewById(R.id.tv_profile);
		textviews[0].setTextColor(0xFF45C01A);
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homefragment)
				.add(R.id.fragment_container, contactlistfragment)
				.add(R.id.fragment_container, funcfragment)
				.add(R.id.fragment_container, findfragment)
				.add(R.id.fragment_container, profilefragment)
				.hide(contactlistfragment).hide(profilefragment)
				.hide(funcfragment).hide(findfragment).show(homefragment)
				.commit();
		inviteMessgeDao = new InviteMessgeDao(this);
		contactsDao = new ContactsDao(this);

		// 注册一个接收消息的BroadcastReceiver
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(3);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

		// 注册一个透传消息的BroadcastReceiver
		IntentFilter cmdMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getCmdMessageBroadcastAction());
		cmdMessageIntentFilter.setPriority(3);
		registerReceiver(cmdMessageReceiver, cmdMessageIntentFilter);

		// 注册一个离线消息的BroadcastReceiver
		// IntentFilter offlineMessageIntentFilter = new
		// IntentFilter(EMChatManager.getInstance()
		// .getOfflineMessageBroadcastAction());
		// registerReceiver(offlineMessageReceiver, offlineMessageIntentFilter);

		// setContactListener监听联系人的变化等
		EMContactManager.getInstance().setContactListener(
				new MyContactListener());
		// 注册一个监听连接状态的listener
		EMChatManager.getInstance().addConnectionListener(
				new MyConnectionListener());
		// 注册群聊相关的listener
		EMGroupManager.getInstance().addGroupChangeListener(
				new MyGroupChangeListener());
		// 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
		EMChat.getInstance().setAppInited();

	}

	/**
	 * 点击菜单切换页面
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.re_wemolian:
			index = 0;
			break;
		case R.id.re_contact_list:
			index = 1;
			break;
		case R.id.re_func:
			index = 2;
			break;
		case R.id.re_find:
			index = 3;
			break;
		case R.id.re_profile:
			index = 4;

		}

		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		imagebuttons[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		imagebuttons[index].setSelected(true);
		textviews[currentTabIndex].setTextColor(0xFF999999);
		textviews[index].setTextColor(0xFF45C01A);
		currentTabIndex = index;
	}

	/**
	 * 滑动切换菜单页面
	 */
	PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	/**
	 * 显示帐号在别处登录dialog
	 */
	private void showConflictDialog() {
		isConflictDialogShow = true;
		WeMoLianApplication.getInstance().logout(null);
		String st = getResources().getString(R.string.Logoff_notification);
		if (!MainActivity.this.isFinishing()) {
			// clear up global variables
			try {
				if (conflictBuilder == null)
					conflictBuilder = new android.app.AlertDialog.Builder(
							MainActivity.this);
				conflictBuilder.setTitle(st);
				conflictBuilder.setMessage(R.string.connect_conflict);
				conflictBuilder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								conflictBuilder = null;
								finish();
								startActivity(new Intent(MainActivity.this,
										LoginActivity.class));
							}
						});
				conflictBuilder.setCancelable(false);
				conflictBuilder.create().show();
				isConflict = true;
			} catch (Exception e) {
				EMLog.e(TAG,
						"---------color conflictBuilder error" + e.getMessage());
			}

		}

	}

	/**
	 * 帐号被移除的dialog
	 */
	private void showAccountRemovedDialog() {
		isAccountRemovedDialogShow = true;
		WeMoLianApplication.getInstance().logout(null);
		String st5 = getResources().getString(R.string.Remove_the_notification);
		if (!MainActivity.this.isFinishing()) {
			// clear up global variables
			try {
				if (accountRemovedBuilder == null)
					accountRemovedBuilder = new android.app.AlertDialog.Builder(
							MainActivity.this);
				accountRemovedBuilder.setTitle(st5);
				accountRemovedBuilder.setMessage(R.string.em_user_remove);
				accountRemovedBuilder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								accountRemovedBuilder = null;
								finish();
								startActivity(new Intent(MainActivity.this,
										LoginActivity.class));
							}
						});
				accountRemovedBuilder.setCancelable(false);
				accountRemovedBuilder.create().show();
				isCurrentAccountRemoved = true;
			} catch (Exception e) {
				EMLog.e(TAG,
						"---------color userRemovedBuilder error"
								+ e.getMessage());
			}

		}

	}

	/**
	 * 新消息广播接收者
	 * 
	 * 
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看

			String from = intent.getStringExtra("from");
			// 消息id
			String msgId = intent.getStringExtra("msgid");
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			try {
				String fromNick = message
						.getStringAttribute(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL);
			} catch (EaseMobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 2014-10-22 修复在某些机器上，在聊天页面对方发消息过来时不立即显示内容的bug
			if (ChatActivity.activityInstance != null) {
				if (message.getChatType() == ChatType.GroupChat) {
					if (message.getTo().equals(
							ChatActivity.activityInstance.getToChatUsername()))
						return;
				} else {
					if (from.equals(ChatActivity.activityInstance
							.getToChatUsername()))
						return;
				}
			}

			// 注销广播接收者，否则在ChatActivity中会收到这个广播
			abortBroadcast();

			notifyNewMessage(message);

			// 刷新bottom bar消息未读数
			updateUnreadLabel();
			if (currentTabIndex == 0) {
				// 当前页面如果为聊天历史页面，刷新此页面
				if (homefragment != null) {
					homefragment.refresh();
				}
			}

		}
	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();

			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");

			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);

				if (msg != null) {

					// 2014-11-5 修复在某些机器上，在聊天页面对方发送已读回执时不立即显示已读的bug
					if (ChatActivity.activityInstance != null) {
						if (msg.getChatType() == ChatType.Chat) {
							if (from.equals(ChatActivity.activityInstance
									.getToChatUsername()))
								return;
						}
					}

					msg.isAcked = true;
				}
			}

		}
	};

	/**
	 * 透传消息BroadcastReceiver
	 */
	private BroadcastReceiver cmdMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();
			// EMLog.d(TAG, "收到透传消息");
			// // 获取cmd message对象
			//
			// EMMessage message = intent.getParcelableExtra("message");
			// // 获取消息body
			// CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
			// String action = cmdMsgBody.action;// 获取自定义action
			//
			// // 获取扩展属性 此处省略
			// // message.getStringAttribute("");
			// EMLog.d(TAG,
			// String.format("透传消息：action:%s,message:%s", action,
			// message.toString()));
			// String st9 = getResources().getString(
			// R.string.receive_the_passthrough);
			// Toast.makeText(MainActivity.this, st9 + action,
			// Toast.LENGTH_SHORT)
			// .show();
		}
	};

	/**
	 * 离线消息BroadcastReceiver sdk 登录后，服务器会推送离线消息到client，这个receiver，是通知UI
	 * 有哪些人发来了离线消息 UI 可以做相应的操作，比如下载用户信息
	 */
	// private BroadcastReceiver offlineMessageReceiver = new
	// BroadcastReceiver() {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// String[] users = intent.getStringArrayExtra("fromuser");
	// String[] groups = intent.getStringArrayExtra("fromgroup");
	// if (users != null) {
	// for (String user : users) {
	// System.out.println("收到user离线消息：" + user);
	// }
	// }
	// if (groups != null) {
	// for (String group : groups) {
	// System.out.println("收到group离线消息：" + group);
	// }
	// }
	// }
	// };

	/***
	 * 好友变化listener
	 * 
	 */
	private class MyContactListener implements EMContactListener {

		@Override
		public void onContactAdded(List<String> usernameList) {
			// edit by zhangyun 2016.07.22

			runOnUiThread(new Runnable() {
				public void run() {
					refreshFriendsList();

				}
			});

			// 刷新ui
			if (currentTabIndex == 1)
				contactlistfragment.refresh();

		}

		@Override
		public void onContactDeleted(final List<String> usernameList) {
			// 被删除
			Map<String, Contacts> localUsers = WeMoLianApplication
					.getInstance().getContactList();
			for (String username : usernameList) {
				localUsers.remove(username);
				contactsDao.deleteContact(username);
				inviteMessgeDao.deleteMessage(username);
			}
			runOnUiThread(new Runnable() {
				public void run() {
					// 如果正在与此用户的聊天页面
					String st10 = getResources().getString(
							R.string.have_you_removed);
					if (ChatActivity.activityInstance != null
							&& usernameList
									.contains(ChatActivity.activityInstance
											.getToChatUsername())) {
						Toast.makeText(
								MainActivity.this,
								ChatActivity.activityInstance
										.getToChatUsername() + st10,
								Toast.LENGTH_SHORT).show();
						ChatActivity.activityInstance.finish();
					}
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 1)
						contactlistfragment.refresh();
					else if (currentTabIndex == 0)
						homefragment.refresh();
				}
			});

		}

		@Override
		public void onContactInvited(final String username, final String reason) {

			// 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不需要重复提醒
			List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

			for (InviteMessage inviteMessage : msgs) {
				if (inviteMessage.getGroupId() == null
						&& inviteMessage.getFrom().equals(username)) {
					inviteMessgeDao.deleteMessage(username);
				}
			}
			// 自己封装的javabean
			InviteMessage msg = new InviteMessage();
			msg.setFrom(username);
			msg.setTime(System.currentTimeMillis());
			msg.setReason(reason);
			Log.d(TAG, username + "请求加你为好友,reason: " + reason);
			Log.d("13", username + "请求加你为好友,reason: " + reason);
			// 设置相应status
			msg.setStatus(InviteMesageStatus.BEINVITEED);
			notifyNewIviteMessage(msg);

		}

		/**
		 * 好友同意邀请
		 */
		@Override
		public void onContactAgreed(final String username) {

			List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
			for (InviteMessage inviteMessage : msgs) {
				if (inviteMessage.getFrom().equals(username)) {
					return;
				}
			}
			runOnUiThread(new Runnable() {
				public void run() {

					addFriendToList(username);
				}
			});

		}

		@Override
		public void onContactRefused(String username) {
			// 参考同意，被邀请实现此功能,demo未实现
			Log.d(username, username + "拒绝了你的好友请求");
		}

	}

	/**
	 * 保存提示新消息
	 * 
	 * @param msg
	 */
	private void notifyNewIviteMessage(InviteMessage msg) {
		saveInviteMsg(msg);
		// 提示有新消息
		EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();

		// 刷新bottom bar消息未读数
		updateUnreadAddressLable();
		// 刷新好友页面ui
		if (currentTabIndex == 1)
			contactlistfragment.refresh();
	}

	/**
	 * 保存邀请等msg
	 * 
	 * @param msg
	 */
	private void saveInviteMsg(InviteMessage msg) {
		// 保存msg
		inviteMessgeDao.saveMessage(msg);
		// 未读数加1
		Contacts user = WeMoLianApplication.getInstance().getContactList()
				.get(Constant.NEW_FRIENDS_USERNAME);
		if (user.getUnreadMsgCount() == 0)
			user.setUnreadMsgCount(user.getUnreadMsgCount() + 1);
	}

	/**
	 * set head
	 * 
	 * @param username
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	Contacts setUserHead(String username) {
		Contacts user = new Contacts();
		user.setUsername(username);
		String headerName = null;

		if (!TextUtils.isEmpty(user.getNick())) {
			headerName = user.getNick();
		} else {
			headerName = user.getUsername();
		}
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
		return user;
	}

	/**
	 * 连接监听listener
	 * 
	 */
	private class MyConnectionListener implements EMConnectionListener {

		@Override
		public void onConnected() {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					homefragment.errorItem.setVisibility(View.GONE);
				}

			});
		}

		@Override
		public void onDisconnected(final int error) {
			final String st1 = getResources().getString(
					R.string.Less_than_chat_server_connection);
			final String st2 = getResources().getString(
					R.string.the_current_network);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (error == EMError.USER_REMOVED) {
						// 显示帐号已经被移除
						showAccountRemovedDialog();
					} else if (error == EMError.CONNECTION_CONFLICT) {
						// 显示帐号在其他设备登陆dialog
						showConflictDialog();
					} else {
						homefragment.errorItem.setVisibility(View.VISIBLE);
						if (NetUtils.hasNetwork(MainActivity.this))
							homefragment.errorText.setText(st1);
						else
							homefragment.errorText.setText(st2);

					}
				}

			});
		}
	}

	/**
	 * MyGroupChangeListener 添加群
	 */
	private class MyGroupChangeListener implements GroupChangeListener {

		@Override
		public void onInvitationReceived(String groupId, String groupName,
				String inviter, String reason) {

			// 被邀请
			String st3 = getResources().getString(
					R.string.Invite_you_to_join_a_group_chat);
			Contacts user = WeMoLianApplication.getInstance().getContactList()
					.get(inviter);
			if (user != null) {
				EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
				msg.setChatType(ChatType.GroupChat);
				msg.setFrom(inviter);
				msg.setTo(groupId);
				msg.setMsgId(UUID.randomUUID().toString());
				msg.addBody(new TextMessageBody(user.getNick() + st3));
				msg.setAttribute(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME,
						user.getImgName());
				String userLabel = (user.getLabel() != null) ? user.getLabel()
						: (user.getUserRemark() != null ? user.getUserRemark()
								: user.getUserCName());
				msg.setAttribute(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL,
						userLabel);
				// 保存邀请消息
				EMChatManager.getInstance().saveMessage(msg);
				// 提醒新消息
				EMNotifier.getInstance(getApplicationContext())
						.notifyOnNewMsg();
			}
			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 0)
						homefragment.refresh();
					// if (CommonUtils.getTopActivity(MainActivity.this).equals(
					// GroupsActivity.class.getName())) {
					// GroupsActivity.instance.onResume();
					// }
				}
			});

		}

		@Override
		public void onInvitationAccpted(String groupId, String inviter,
				String reason) {

		}

		@Override
		public void onInvitationDeclined(String groupId, String invitee,
				String reason) {

		}

		@Override
		public void onUserRemoved(String groupId, String groupName) {
			// 提示用户被T了，demo省略此步骤
			// 刷新ui
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						updateUnreadLabel();
						if (currentTabIndex == 0)
							homefragment.refresh();
						// if (CommonUtils.getTopActivity(MainActivity.this)
						// .equals(GroupsActivity.class.getName())) {
						// GroupsActivity.instance.onResume();
						// }
					} catch (Exception e) {
						EMLog.e(TAG, "refresh exception " + e.getMessage());
					}
				}
			});
		}

		@Override
		public void onGroupDestroy(String groupId, String groupName) {
			// 群被解散
			// 提示用户群被解散,demo省略
			// 刷新ui
			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					if (currentTabIndex == 0)
						homefragment.refresh();
					// if (CommonUtils.getTopActivity(MainActivity.this).equals(
					// GroupsActivity.class.getName())) {
					// GroupsActivity.instance.onResume();
					// }
				}
			});

		}

		@Override
		public void onApplicationReceived(String groupId, String groupName,
				String applyer, String reason) {
			// 用户申请加入群聊
			InviteMessage msg = new InviteMessage();
			msg.setFrom(applyer);
			msg.setTime(System.currentTimeMillis());
			msg.setGroupId(groupId);
			msg.setGroupName(groupName);
			msg.setReason(reason);
			Log.d(TAG, applyer + " 申请加入群聊：" + groupName);
			msg.setStatus(InviteMesageStatus.BEAPPLYED);
			notifyNewIviteMessage(msg);
		}

		@Override
		public void onApplicationAccept(String groupId, String groupName,
				String accepter) {
			String st4 = getResources().getString(
					R.string.Agreed_to_your_group_chat_application);
			// 加群申请被同意
			EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
			msg.setChatType(ChatType.GroupChat);
			msg.setFrom(accepter);
			msg.setTo(groupId);
			msg.setMsgId(UUID.randomUUID().toString());
			msg.addBody(new TextMessageBody(accepter + st4));
			// 保存同意消息
			EMChatManager.getInstance().saveMessage(msg);
			// 提醒新消息
			EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();

			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 0)
						homefragment.refresh();
					// if (CommonUtils.getTopActivity(MainActivity.this).equals(
					// GroupsActivity.class.getName())) {
					// GroupsActivity.instance.onResume();
					// }
				}
			});
		}

		@Override
		public void onApplicationDeclined(String groupId, String groupName,
				String decliner, String reason) {
			// 加群申请被拒绝，demo未实现
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isConflict || !isCurrentAccountRemoved) {
			// initView();
			updateUnreadLabel();
			updateUnreadAddressLable();
			EMChatManager.getInstance().activityResumed();
		}
		Log.d("onResume", "onUserLeaveHint");

		if (!WeMoLianApplication.getInstance().getLockPatternUtils()
				.savedPatternExists()) {
			// startActivity(new Intent(this,
			// GuideGesturePasswordActivity.class));
			// finish();
			return;
		} else if (haveLocked) {
			// Toast.makeText(MainActivity.this, "有手势密码",
			// Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, UnlockGesturePasswordActivity.class)
					.putExtra("activity", "main"));
			finish();
		} else {
			String unlock = getIntent().getStringExtra("locked");
			if ("unlock".equals(unlock)) {
				return;
			} else {
				// Toast.makeText(MainActivity.this, "有手势密码",
				// Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this,
						UnlockGesturePasswordActivity.class).putExtra(
						"activity", "main"));
				finish();
			}
		}
	}

	// /**
	// * 程序离开时，执行
	// */
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// DemoApplication.getInstance().getLockPatternUtils().savedPatternExists();
	// }
	//
	/**
	 * 按home键触发
	 */
	@Override
	protected void onUserLeaveHint() {
		Log.d("aeon", "onUserLeaveHint");
		if (WeMoLianApplication.getInstance().getLockPatternUtils()
				.savedPatternExists()) {
			haveLocked = true;
		}
		super.onUserLeaveHint();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isConflict", isConflict);
		outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (getIntent().getBooleanExtra("conflict", false)
				&& !isConflictDialogShow) {
			showConflictDialog();
		} else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false)
				&& !isAccountRemovedDialogShow) {
			showAccountRemovedDialog();
		}
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		int count = getUnreadMsgCountTotal();
		if (count > 0) {
			unreadLabel.setText(String.valueOf(count));
			unreadLabel.setVisibility(View.VISIBLE);
		} else {
			unreadLabel.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 获取未读申请与通知消息
	 * 
	 * @return
	 */
	public int getUnreadAddressCountTotal() {
		int unreadAddressCountTotal = 0;
		if (WeMoLianApplication.getInstance().getContactList()
				.get(Constant.NEW_FRIENDS_USERNAME) != null)
			unreadAddressCountTotal = WeMoLianApplication.getInstance()
					.getContactList().get(Constant.NEW_FRIENDS_USERNAME)
					.getUnreadMsgCount();
		return unreadAddressCountTotal;
	}

	/**
	 * 刷新申请与通知消息数
	 */
	public void updateUnreadAddressLable() {
		runOnUiThread(new Runnable() {
			public void run() {
				int count = getUnreadAddressCountTotal();
				if (count > 0) {
					unreadAddressLable.setText(String.valueOf(count));
					unreadAddressLable.setVisibility(View.VISIBLE);
				} else {
					unreadAddressLable.setVisibility(View.INVISIBLE);
				}
			}
		});

	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		return unreadMsgCountTotal;
	}

	public void refreshFriendsList() {
		List<String> usernames = new ArrayList<String>();
		try {
			usernames = EMContactManager.getInstance().getContactUserNames();
		} catch (EaseMobException e1) {
			e1.printStackTrace();
		}
		if (usernames != null && usernames.size() > 0) {
			String totaluser = usernames.get(0);
			for (int i = 1; i < usernames.size(); i++) {
				final String split = "66split88";
				totaluser += split + usernames.get(i);
			}
			totaluser = totaluser.replace(Constant.NEW_FRIENDS_USERNAME, "");
			totaluser = totaluser.replace(Constant.GROUP_USERNAME, "");

			initUserData();

		}

	}

	private void initUserData() {
		if (runOneTime) {
			Map<String, String> map = new HashMap<String, String>();

			// map.put("uids", totaluser);
			String userEName = LocalUserInfo.getInstance(MainActivity.this)
					.getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME);
			map.put("code", "200");
			map.put("userEName", userEName);
			/**
			 * 拉取好友信息 和群聊信息
			 * */
			LoadDataFromServer task = new LoadDataFromServer(MainActivity.this,
					Constant.URL_INITUSERDATA, map);
			task.getData(new DataCallBack() {

				@Override
				public void onDataCallBack(JSONObject resData) {
					

					try {
						/**
						 * 数据返回成功
						 * 并且data中的数据不为空
						 */
						if(resData.getBooleanValue("success") && resData.getJSONObject("data") != null){

							JSONObject data = resData.getJSONObject("data");
							int code = data.getInteger("code");
							//200表示既有群列表也有好友列表
							if (code == 200) {
								//获取好友列表
								JSONArray friendsJosnArray = data
										.getJSONArray("friends");
								//获取群聊列表
								JSONArray groupsJosnArray = data
										.getJSONArray("groups");
								
								

								//保存群信息
								saveGroups(groupsJosnArray);
								//保存好友信息
								saveFriends(friendsJosnArray);

							//201 表示只有好友列表，没有群列表
							}else if(code == 201){
								JSONArray friendsJosnArray = data
										.getJSONArray("friends");
								//保存好友信息
								saveFriends(friendsJosnArray);
							//202 表示只有群列表，没有好友列表
							}else if(code == 202){
								JSONArray groupsJosnArray = data
										.getJSONArray("groups");
								saveGroups(groupsJosnArray);
							}
							//404 表示既没有好友列表，也没有群列表
							else if (code == 203) {

								saveFriends(null);
								Toast.makeText(MainActivity.this,
										"获取好友列表失败,请重试...", Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(MainActivity.this,
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
						Toast.makeText(MainActivity.this, "数据解析错误...",
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				
					
					
//					// 数据请求成功
//					if (resData.getBoolean("success")) {
//						JSONObject data = resData.getJSONObject("data");
//						int code = data.getInteger("code");
//						if (code == 200) {
//							// 获取好友列表
//							JSONArray friendsJosnArray = data
//									.getJSONArray("friends");
//							// 获取群聊列表
//							JSONArray groupsJosnArray = data
//									.getJSONArray("groups");
//							saveFriends(friendsJosnArray);
//							saveGroups(groupsJosnArray);
//							// 201 表示只有好友列表，没有群列表
//						} else if (code == 201) {
//
//							saveFriends(data.getJSONArray("friends"));
//
//							// 202 表示只有群列表，没有好友列表
//						} else if (code == 202) {
//							
//						}
//						// 404 表示既没有好友列表，也没有群列表
//						else if (code == 404) {
//							Toast.makeText(MainActivity.this,
//									"获取好友列表失败,请重试...", Toast.LENGTH_SHORT)
//									.show();
//						} else {
//							Toast.makeText(MainActivity.this, "服务器繁忙请重试...",
//									Toast.LENGTH_SHORT).show();
//						}
//
//					} else {
//						Toast.makeText(MainActivity.this, "数据请求失败",
//								Toast.LENGTH_SHORT).show();
//					}
					
					
					
				}

			});
			runOneTime = false;
		} else {
			Toast.makeText(MainActivity.this, "好友没有变化",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 处理群信息
	 * @param groupsJosnArray
	 */
	private void saveGroups(JSONArray josnArray) {

		Map<String, Groups> map = new HashMap<String, Groups>();
		if (josnArray != null) {
			for (int i = 0; i < josnArray.size(); i++) {
				JSONObject json = josnArray.getJSONObject(i);
				try {
					/**
					 * [{"groupename":"123zzikt","groupUserHeadImgs":
					 * "d89ee76994c6f23abdcbebcb3e489807.png,b33d945112aaa65b5d5e30353f2d9383.png,
					 * 6a40d7114aaf25189b5d182d8542b590.png","groupqrcode":"4565445646456",
					 * "groupmembers":"824a6836c7feea99be6dcc86e8323c53,
					 * d92b8b9262484d8c6601f9728f6edddf,00c97b7c0ee2ebf8da7a88b10310c754",
					 * "top":0,"groupcname":"test123、test789、test456"}]
					 */
					
					String groupename = json.getString("groupename");
					String groupUserHeadImgs = json.getString("groupUserHeadImgs");
					String groupqrcode = json.getString("groupqrcode");
					String groupmembers = json.getString("groupmembers");
					String groupcname = json.getString("groupcname");
					int top = json.getInteger("top");
					String owner = json.getString("groupuserId");
					
					Groups groups = new Groups();
					groups.setGroupcname(groupcname);
					groups.setGroupename(groupename);
					groups.setGroupmembers(groupmembers);
					groups.setGroupqrcode(groupqrcode);
					groups.setGroupUserHeadImgs(groupUserHeadImgs);
					groups.setTop(top);
					groups.setOwner(owner);
					

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
		map.put(Constant.GROUP_USERNAME, groups);
		
		
		// 存入内存
		WeMoLianApplication.getInstance().setGroupsList(map);
		// 存入db
		GroupsDao dao = new GroupsDao(MainActivity.this);
		List<Groups> groupsList = new ArrayList<Groups>(map.values());
		dao.saveGroups(groupsList);
		
	
	}

	/**
	 * 处理好友信息
	 * 
	 * @param josnArray
	 */
	private void saveFriends(JSONArray josnArray) {

		Map<String, Contacts> map = new HashMap<String, Contacts>();

		if (josnArray != null) {
			for (int i = 0; i < josnArray.size(); i++) {
				JSONObject json = (JSONObject) josnArray.getJSONObject(i);
				try {
					String userSex = json
							.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
					String userCName = json
							.getString(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
					String img = json
							.getString(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
					String externalUse = json.getString("friendId");
					String hxId = json
							.getString(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
					String phoneNum = json
							.getString(LocalDBKey.USER_COLUMN_NAME_PHONENUM);
					String isOnline = json
							.getString(LocalDBKey.USER_COLUMN_NAME_ISONLINE);
					int state = json
							.getIntValue(LocalDBKey.USER_COLUMN_NAME_STATE);

					Contacts contact = new Contacts();
					contact.setSex(userSex);
					contact.setUserCName(userCName);
					contact.setNick(userCName);
					contact.setImgName(img);
					contact.setExternalUse(externalUse);
					contact.setHxid(hxId);
					contact.setType(SysConfig.STR_TYPE_FRIEND);
					String nick = "";
					if (contact.getLabel() != null) {
						nick = contact.getLabel();
					} else if (contact.getUserRemark() != null) {
						nick = contact.getUserRemark();
					} else if (contact.getUserCName() != null) {
						nick = contact.getUserCName();
					} else if (contact.getNick() != null) {
						nick = contact.getNick();
					} else {
						nick = contact.getExternalUse();
					}

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
		newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setSex("");
		newFriends.setUserCName("");
		newFriends.setNick("");
		newFriends.setImgName("");
		newFriends.setExternalUse("");
		newFriends.setHxid("");
		newFriends.setType(SysConfig.STR_TYPE_FRIEND);
		map.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		Contacts groupUser = new Contacts();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(Constant.GROUP_USERNAME);
		groupUser.setType(SysConfig.STR_TYPE_GROUP);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		groupUser.setNick(strChat);
		// groupUser.setBeizhu("");
		// groupUser.setFxid("");
		// groupUser.setHeader("");
		// groupUser.setRegion("");
		// groupUser.setSex("");
		// groupUser.setTel("");
		// groupUser.setSign("");
		// groupUser.setAvatar("");
		map.put(Constant.GROUP_USERNAME, groupUser);

		// 存入内存
		WeMoLianApplication.getInstance().setContactList(map);
		// 存入db
		ContactsDao dao = new ContactsDao(MainActivity.this);
		List<Contacts> user = new ArrayList<Contacts>(map.values());
		dao.saveContactList(user);

	}

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param username
	 * @param user
	 */
	@SuppressLint("DefaultLocale")
	protected void setUserHearder(String username, Contacts contacts) {
		String headerName = null;
		// if (!TextUtils.isEmpty(user.getNick())) {
		// headerName = user.getNick();
		// } else {
		// headerName = user.getUsername();
		// }
		if (!TextUtils.isEmpty(contacts.getLabel())) {
			headerName = contacts.getLabel();
		} else if (!TextUtils.isEmpty(contacts.getUserRemark())) {
			headerName = contacts.getUserRemark();
		} else if (!TextUtils.isEmpty(contacts.getNick())) {
			headerName = contacts.getNick();
		} else if (!TextUtils.isEmpty(contacts.getUserCName())) {
			headerName = contacts.getUserCName();
		} else {
			headerName = contacts.getUsername();
		}
		headerName = headerName.trim();
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
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
	 * 同意好友的邀请后，拉取好友的信息到本地数据库
	 * 
	 * @param hxid
	 */
	private void addFriendToList(final String hxid) {
		Map<String, String> map_uf = new HashMap<String, String>();
		map_uf.put("hxuserId", hxid);
		LoadDataFromServer task = new LoadDataFromServer(null,
				Constant.URL_GET_USERMES, map_uf);
		task.getData(new DataCallBack() {
			@Override
			public void onDataCallBack(JSONObject resData) {
				// 数据请求成功
				if (resData != null && resData.getBoolean("success")) {
					JSONObject data = resData.getJSONObject("data");
					String code = data.getString("code");
					if ("200".equals(code)) {
						JSONObject json = data.getJSONObject("data");
						if (json != null) {
							String userSex = json
									.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
							String userCName = json
									.getString(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
							String img = json
									.getString(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
							String externalUse = json.getString("friendId");
							String hxId = json
									.getString(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
							// String phoneNum =
							// json.getString(LocalDBKey.USER_COLUMN_NAME_PHONENUM);
							// String isOnline =
							// json.getString(LocalDBKey.USER_COLUMN_NAME_ISONLINE);
							// int state =
							// json.getIntValue(LocalDBKey.USER_COLUMN_NAME_STATE);
							String label = json
									.getString(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL);
							String userRemark = json
									.getString(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK);

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
							String nick = "";
							if (contact.getLabel() != null) {
								nick = contact.getLabel();
							} else if (contact.getUserRemark() != null) {
								nick = contact.getUserRemark();
							} else if (contact.getUserCName() != null) {
								nick = contact.getUserCName();
							} else if (contact.getNick() != null) {
								nick = contact.getNick();
							} else {
								nick = contact.getExternalUse();
							}
							setUserHearder(nick, contact);

							Map<String, Contacts> userlist = WeMoLianApplication
									.getInstance().getContactList();
							Map<String, Contacts> map_temp = new HashMap<String, Contacts>();
							map_temp.put(hxid, contact);
							userlist.putAll(map_temp);

							/**
							 * map.put(Constant.NEW_FRIENDS_USERNAME,
							 * newFriends); // 存入内存
							 * DemoApplication.getInstance()
							 * .setContactList(map); // 存入db ContactsDao dao =
							 * new ContactsDao(LoginActivity.this);
							 * List<Contacts> contacts = new
							 * ArrayList<Contacts>(map.values());
							 * dao.saveContactList(contacts);
							 */
							// 存入内存
							WeMoLianApplication.getInstance().setContactList(
									userlist);
							// 存入db
							ContactsDao dao = new ContactsDao(MainActivity.this);

							dao.saveContact(contact);

							// 自己封装的javabean
							InviteMessage msg = new InviteMessage();
							msg.setFrom(hxid);
							msg.setTime(System.currentTimeMillis());

							String reason_temp = nick
									+ "66split88"
									+ img
									+ "66split88"
									+ String.valueOf(System.currentTimeMillis())
									+ "66split88" + "已经同意请求";
							msg.setReason(reason_temp);

							msg.setStatus(InviteMesageStatus.BEAGREED);
							Contacts userTemp = WeMoLianApplication
									.getInstance().getContactList()
									.get(Constant.NEW_FRIENDS_USERNAME);
							if (userTemp != null
									&& userTemp.getUnreadMsgCount() == 0) {
								userTemp.setUnreadMsgCount(userTemp
										.getUnreadMsgCount() + 1);
							}
							notifyNewIviteMessage(msg);

						} else {
							Toast.makeText(MainActivity.this, "获取用户信息出错",
									Toast.LENGTH_SHORT).show();
						}
					} else if ("400".equals(code)) {
						Toast.makeText(MainActivity.this, "用户不可用",
								Toast.LENGTH_SHORT).show();
					} else if ("404".equals(code)) {
						Toast.makeText(MainActivity.this, "用户不存在",
								Toast.LENGTH_SHORT).show();
					} else {

					}

					// 数据请求失败
				} else {
					Toast.makeText(MainActivity.this, "拉取好友信息失败",
							Toast.LENGTH_SHORT).show();
				}

			}

		});

	}

	/**
	 * 服务与反馈
	 * @param v
	 */
    public void onClickFootView(View v){
    	//Toast.makeText(this, "服务与反馈功能正在开发中……", Toast.LENGTH_SHORT).show();
 
    	
    	startActivity(new Intent(MainActivity.this,ServiceFeedbackActivity.class));
    	
    }
	
	
	
	
	
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				moveTaskToBack(false);
				finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(ackMessageReceiver);
		unregisterReceiver(cmdMessageReceiver);
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}
}
