package com.wemolian.app.wml.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.db.InviteMessgeDao;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.InviteMessage;
import com.wemolian.app.domain.InviteMessage.InviteMesageStatus;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.easemob.util.HanziToPinyin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ViewHolder")
public class NewFriendsAdapter extends BaseAdapter {
	Context context;
	List<InviteMessage> msgs;
	private InviteMessgeDao messgeDao;
	int total = 0;
	private LoadUserAvatar avatarLoader;

	@SuppressLint("SdCardPath")
	public NewFriendsAdapter(Context context, List<InviteMessage> msgs) {
		this.context = context;
		this.msgs = msgs;
		messgeDao = new InviteMessgeDao(context);
		avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
		total = msgs.size();
	}

	@Override
	public int getCount() {
		return msgs.size();
	}

	@Override
	public InviteMessage getItem(int position) {
		// TODO Auto-generated method stub
		return msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;

		holder = new ViewHolder();
		final InviteMessage msg = getItem(total - 1 - position);
		int msg_id = msg.getId();
		String userUid = msg.getFrom();
		String reason_total = msg.getReason();
		String[] sourceStrArray = reason_total.split("66split88");
		// 先附初值
		String name = msg.getFrom();
		String avatar = msg.getFrom();
		String reason = "请求加好友";
		if (sourceStrArray.length == 4) {
			name = sourceStrArray[0];
			avatar = sourceStrArray[1];
			reason = sourceStrArray[3];
		}
		convertView = View.inflate(context, R.layout.item_newfriendsmsag, null);
		holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
		holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.tv_reason = (TextView) convertView.findViewById(R.id.tv_reason);
		holder.tv_added = (TextView) convertView.findViewById(R.id.tv_added);
		holder.btn_add = (Button) convertView.findViewById(R.id.btn_add);
		holder.tv_name.setText(name);
		holder.tv_reason.setText(reason);
		if (msg.getStatus() == InviteMesageStatus.AGREED
				|| msg.getStatus() == InviteMesageStatus.BEAGREED) {

			holder.tv_added.setVisibility(View.VISIBLE);
			holder.btn_add.setVisibility(View.GONE);
		} else {
			holder.tv_added.setVisibility(View.GONE);
			holder.btn_add.setVisibility(View.VISIBLE);
			holder.btn_add.setTag(msg);
			holder.btn_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					acceptInvitation(holder.btn_add, msg, holder.tv_added);
				}

			});

		}
		showUserAvatar(holder.iv_avatar, avatar);
		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_avatar;
		TextView tv_name;
		TextView tv_reason;
		TextView tv_added;
		Button btn_add;

	}

	private void showUserAvatar(ImageView iamgeView, String avatar) {
		if (avatar == null || avatar.equals("")) {
			return;
		}
		final String url_avatar = Constant.URL_Avatar + avatar;
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

	/**
	 * 同意好友请求或者群申请
	 * 
	 * @param button
	 * @param username
	 */
	private void acceptInvitation(final Button button, final InviteMessage msg,
			final TextView textview) {
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage("正在同意...");
		pd.setCanceledOnTouchOutside(false);
		pd.show();

		new Thread(new Runnable() {
			public void run() {
				// 调用sdk的同意方法
				try {
					if (msg.getGroupId() == null) {// 同意好友请求
						EMChatManager.getInstance().acceptInvitation(
								msg.getFrom());

					} else
						// 同意加群申请
						EMGroupManager.getInstance().acceptApplication(
								msg.getFrom(), msg.getGroupId());
					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							pd.dismiss();
							textview.setVisibility(View.VISIBLE);
							button.setEnabled(false);
							button.setVisibility(View.GONE);
							msg.setStatus(InviteMesageStatus.AGREED);
							// 更新db
							ContentValues values = new ContentValues();
							values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg
									.getStatus().ordinal());
							messgeDao.updateMessage(msg.getId(), values);

							// 巩固程序,即时将该好友存入好友列表
							

						}
					});
				} catch (final Exception e) {
					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							pd.dismiss();
							Toast.makeText(context, "同意失败: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
							
						}
					});

				}
			}
		}).start();
		addFriendToList(msg.getFrom());
	}

	
	/**
	 * 添加好友到服务器和本地db
	 * @param hxid
	 */
	private void addFriendToList(final String friendId) {
		Map<String, String> doMap = new HashMap<String, String>();
		doMap.put("userId",
				LocalUserInfo
						.getInstance(context)
						.getUserInfo(
								LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE));
		doMap.put("friendId", friendId);
		//code = 201 表示用户环信已添加，并且已经接受邀请
		doMap.put("code", "201");
		LoadDataFromServer task = new LoadDataFromServer(null,
				Constant.URL_ADD_FRIEND_DO, doMap);
		task.getData(new DataCallBack() {
			public void onDataCallBack(JSONObject resData) {
					if (resData.getBoolean("success")) {
						JSONObject data = resData
								.getJSONObject("data");
						if (data != null) {
							String code = data.getString("code");
							if ("200".equals(code)) {
								JSONObject json = data.getJSONObject("data");
								if (json == null && json.size() == 0) {
									return;
								}
								/**
								 * {"bankCard":"","externalUse":"cf4980102da946301261048bb7a1d20f",
								 * "headImg":"de67c54f93bc5bcfc48242738084da7d.png","hxpassword":"",
								 * "hxuserId":"cf4980102da946301261048bb7a1d20f","id":0,
								 * "intTimeStamp":"","isOnline":1,"oldPassword":"",
								 * "password":"029c2e4f6d72f1d9076d8d2277485c86",
								 * "randomNum":"","registDate":"2016-07-21 11:35:17",
								 * "state":1,"userCName":"测试741","userEName":"741","userRegion":"云南普洱","userSex":"男","vip":"0"}
								 */
								String userSex = json.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
								String userCName = json.getString(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
								String img = json.getString(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
								String externalUse = json.getString(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
								String hxId = json.getString(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
//								String phoneNum = json.getString(LocalDBKey.USER_COLUMN_NAME_PHONENUM);
//								String isOnline = json.getString(LocalDBKey.USER_COLUMN_NAME_ISONLINE);
//								int state = json.getIntValue(LocalDBKey.USER_COLUMN_NAME_STATE);
								String label = json.getString(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL);
								String userRemark = json.getString(LocalDBKey.CONTACTS_COLUMN_NAME_USERREMARK);
								
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

								
								

								Map<String, Contacts> userlist = WeMoLianApplication
										.getInstance().getContactList();
								Map<String, Contacts> map_temp = new HashMap<String, Contacts>();
								map_temp.put(hxId, contact);
								userlist.putAll(map_temp);
								// 存入内存
								WeMoLianApplication.getInstance().setContactList(userlist);
								// 存入db
								ContactsDao dao = new ContactsDao(context);

								dao.saveContact(contact);
								
								
								Toast.makeText(context, "添加好友成功！",
										Toast.LENGTH_SHORT).show();

							} else if ("400".equals(code)) {

								Toast.makeText(context, "添加好友失败！",
										Toast.LENGTH_SHORT).show();
							} else {

								Toast.makeText(context, "未知的状态码！",
										Toast.LENGTH_SHORT).show();
							}

						} else {
							Toast.makeText(context, "请求返回的数据为空！",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						// 请求失败
						Toast.makeText(context, "添加好友失败！",
								Toast.LENGTH_SHORT).show();
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
