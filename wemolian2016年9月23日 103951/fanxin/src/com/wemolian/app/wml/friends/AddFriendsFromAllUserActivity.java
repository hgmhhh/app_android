package com.wemolian.app.wml.friends;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.AddFriendsFinalActivity;
import com.wemolian.app.wml.ChatActivity;
import com.wemolian.app.wml.RegisterUpdateActivity;
import com.wemolian.app.wml.UserInfoActivity;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;


/**
 * 从世界的朋友、附近的人中，添加好友
 * @author Administrator
 *
 */
public class AddFriendsFromAllUserActivity extends BaseActivity {

	InputMethodManager manager;
	private LoadUserAvatar avatarLoader;
	LinearLayout ll_user_cname;
	Button btn_sendmsg;
	ImageView iv_headImg;
	ImageView iv_sex;
	TextView tv_name;
	TextView tv_user_cname,tv_user_ename;
	TextView tv_autograph;
	Friends friend;
	RelativeLayout rl_update_label;
	boolean is_friend = false;
	Contacts con;
	
	/**屏幕宽度*/
	int screenWidth;
	View parentView;
	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_userinfo);
		parentView = getLayoutInflater().inflate(R.layout.activity_userinfo, null);
		setStatus(findViewById(R.id.title));
		avatarLoader = new LoadUserAvatar(this, "/sdcard/wemolian/");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//	    Friends friend = (Friends)getIntent().getParcelableExtra("userInfo");
		friend = (Friends) getIntent().getExtras().get("userInfo");
		if(friend == null){
			Toast.makeText(this, "获取用户信息出错", Toast.LENGTH_SHORT).show();
			return;
		}
		btn_sendmsg = (Button) this.findViewById(R.id.btn_sendmsg);
		iv_headImg = (ImageView) this.findViewById(R.id.iv_avatar);
		iv_sex = (ImageView) this.findViewById(R.id.iv_sex);
		tv_name = (TextView) this.findViewById(R.id.tv_name);
		tv_user_cname = (TextView) this.findViewById(R.id.tv_user_cname);
		tv_user_ename = (TextView) this.findViewById(R.id.tv_user_ename);
		tv_autograph = (TextView) this.findViewById(R.id.tv_autograph);
		ll_user_cname = (LinearLayout) this.findViewById(R.id.ll_user_cname);
		rl_update_label = (RelativeLayout) this.findViewById(R.id.rl_update_label);
		ll_user_cname.setVisibility(View.GONE);
		
		rl_update_label.setVisibility(View.GONE);
		btn_sendmsg.setText("添加好友");
		tv_user_ename.setText(friend.getUserEName());
		tv_name.setText(friend.getUserCName());
		tv_autograph.setText(friend.getAutograph());
		if (("1").equals(friend.getUserSex()) || "男".equals(friend.getUserSex())) {
            iv_sex.setImageResource(R.drawable.ic_sex_male);
        } else if ("2".equals(friend.getUserSex()) || "女".equals(friend.getUserSex())) {
            iv_sex.setImageResource(R.drawable.ic_sex_female);
        } else {
            iv_sex.setVisibility(View.GONE);
        }
		 if (WeMoLianApplication.getInstance().getContactList()
                 .containsKey(friend.getHxuserId())) {
			is_friend = true;
			con = new Contacts();

			con.setExternalUse(friend.getExternalUse());
			con = WeMoLianApplication.getInstance().getContact(con);
			
			tv_name.setText(con.getLabel());
			tv_user_cname.setText(con.getUserCName());
			rl_update_label.setVisibility(View.VISIBLE);
			rl_update_label.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					updateFriendLabel();
				}
			});
			tv_name.setText(con.getNick() == null ? con.getUserCName() : con.getNick());
			tv_user_cname.setText(con.getUserCName());
			ll_user_cname.setVisibility(View.VISIBLE);
			btn_sendmsg.setText("发消息");
         }
		showUserAvatar(iv_headImg, friend.getHeadImg());
		
		btn_sendmsg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(friend.getHxuserId().equals(LocalUserInfo.getInstance(getApplicationContext()).getUserInfo("hxid"))){
                    Toast.makeText(AddFriendsFromAllUserActivity.this, "不能和自己聊天。。", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (is_friend) {
                	
                	
                    Intent intent = new Intent();
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, con.getHxid());
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, con.getImgName());
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, con.getNick() == null ? con.getUserCName() : con.getNick() );
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE, "friend");
                    
                    intent.setClass(AddFriendsFromAllUserActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, friend.getHxuserId());
                     intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, friend.getHeadImg());
                     intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, friend.getUserCName());

                    intent.setClass(AddFriendsFromAllUserActivity.this,
                            AddFriendsFinalActivity.class);
                    startActivity(intent);

                }
            }

        });
	}
	
	
	private void updateFriendLabel() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.pop_update_label);
        final TextView et_new_label = (TextView) window.findViewById(R.id.et_new_label);
        et_new_label.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        	}
        });
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_esc = (TextView) window.findViewById(R.id.tv_esc);
        tv_esc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        TextView tv_ok = (TextView) window.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(AddFriendsFromAllUserActivity.this, "用户备注更新为："+ et_new_label.getText().toString(), Toast.LENGTH_SHORT).show();
                dlg.cancel();
            }
        });
        LinearLayout ll_pop_parent = (LinearLayout) window.findViewById(R.id.ll_pop_parent);
        ll_pop_parent.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
        				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        		dlg.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
        				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        		hideKeyboard();
        	}
        });
        dlg.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
       		         WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			}
		});
    }
	
	
	
	private void showUserAvatar(ImageView iamgeView, String avatar) {
        final String url_avatar = Constant.URL_Avatar + avatar;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
            Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
                    new ImageDownloadedCallBack() {

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
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
	
}
