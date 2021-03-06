package com.wemolian.app.wml;

import java.util.HashMap;
import java.util.Map;
 















import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.friends.AddFriendsFromAllUserActivity;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.util.HanziToPinyin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends BaseActivity {
    private LoadUserAvatar avatarLoader;
    boolean is_friend = false;
    RelativeLayout rl_update_label;
    Button btn_sendmsg;
    ImageView iv_headImg;
    ImageView iv_sex;
    TextView tv_name;
    TextView tv_user_cname;
    TextView tv_autograph;
    InputMethodManager manager;
    String userCName;
    String hxid,userNick,headImg,sex,autograph;
    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setStatus(findViewById(R.id.title));
        avatarLoader = new LoadUserAvatar(this, "/sdcard/wemolian/");
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        btn_sendmsg = (Button) this.findViewById(R.id.btn_sendmsg);
        iv_headImg = (ImageView) this.findViewById(R.id.iv_avatar);
        iv_sex = (ImageView) this.findViewById(R.id.iv_sex);
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_user_cname = (TextView) this.findViewById(R.id.tv_user_cname);
        rl_update_label = (RelativeLayout) this.findViewById(R.id.rl_update_label);
        tv_autograph = (TextView) this.findViewById(R.id.tv_autograph);
        userCName  = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME);
        userNick = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL);
        headImg = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME);
        sex = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_SEX);
        autograph = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_AUTOGRAPH);
        if(autograph == null || autograph.length() == 0){
        	autograph = "该用户什么都没说";
        }
        tv_autograph.setText(autograph);
        hxid = this.getIntent().getStringExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID);
        if (userCName != null && headImg != null && sex != null && hxid != null) {
        	if(userNick != null){
        		tv_name.setText(userNick);
        	}else{
        		tv_name.setText(userCName);
        	}
			tv_user_cname.setText(userCName);
            if (sex.equals("1") || "男".equals(sex)) {
                iv_sex.setImageResource(R.drawable.ic_sex_male);
            } else if (sex.equals("2") || "女".equals(sex)) {
                iv_sex.setImageResource(R.drawable.ic_sex_female);
            } else {
                iv_sex.setVisibility(View.GONE);
            }
            if (WeMoLianApplication.getInstance().getContactList()
                    .containsKey(hxid)) {
                is_friend = true;
                btn_sendmsg.setText("发消息");
            }

            showUserAvatar(iv_headImg, headImg);
        }

        rl_update_label.setVisibility(View.VISIBLE);
		rl_update_label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateFriendLabel();
			}
		});
        btn_sendmsg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(hxid.equals(LocalUserInfo.getInstance(getApplicationContext()).getUserInfo("hxid"))){
                    Toast.makeText(UserInfoActivity.this, "不能和自己聊天。。", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (is_friend) {
                	String nick = "";
                	if(userNick != null){
                		nick = userNick;
                	}else{
                		nick = userCName;
                	}
                    Intent intent = new Intent();
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, hxid);
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, headImg);
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, nick);
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_TYPE, "friend");
                    
                    intent.setClass(UserInfoActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, hxid);
                     intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, headImg);
                     intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, userCName);

                    intent.setClass(UserInfoActivity.this,
                            AddFriendsFinalActivity.class);
                    startActivity(intent);

                }
            }

        });
        
        Button btn_new= (Button) this.findViewById(R.id.btn_new);
        btn_new.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                     if(hxid.equals(LocalUserInfo.getInstance(getApplicationContext()).getUserInfo("hxid"))){
                         Toast.makeText(UserInfoActivity.this, "不能和自己聊天。。", Toast.LENGTH_SHORT).show();
                         return ;
                     }
                    Intent intent = new Intent();
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, hxid);
           
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, userCName);
                    intent.putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, headImg);
                    intent.setClass(UserInfoActivity.this, ChatActivity.class);
                    startActivity(intent);
             
            }

        });
       refresh();
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
            	Toast.makeText(UserInfoActivity.this, "用户备注更新为："+ et_new_label.getText().toString(), Toast.LENGTH_SHORT).show();
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
	
    private void showUserAvatar(ImageView iamgeView, String imgName) {
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
   
   @SuppressLint("DefaultLocale")
   protected void setUserHearder(String username, Contacts contacts) {
       String headerName = null;
//       if (!TextUtils.isEmpty(user.getNick())) {
//           headerName = user.getNick();
//       } else {
//           headerName = user.getUsername();
//       }
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
    
    
    
    private void refresh(){
        Map<String, String> map = new HashMap<String, String>();

        map.put("uid", hxid);

        LoadDataFromServer task = new LoadDataFromServer(
                UserInfoActivity.this, Constant.URL_SEARCH_USER, map);

        task.getData(new DataCallBack() {

            @Override
            public void onDataCallBack(JSONObject data) {
                try {
                    
                    int code = data.getInteger("code");
                    if (code == 1) {

                        JSONObject json = data.getJSONObject("user");
                        String hxid = json.getString("hxid");
                        String fxid = json.getString("fxid");
                        String nick = json.getString("nick");
                        String avatar = json.getString("avatar");
                        String sex = json.getString("sex");
                        String region = json.getString("region");
                        String sign = json.getString("sign");
                        String tel = json.getString("tel");

                        Contacts user = new Contacts();
//                        user.setFxid(fxid);
//                        user.setUsername(hxid);
//                        user.setBeizhu("");
//                        user.setNick(nick);
//                        user.setRegion(region);
//                        user.setSex(sex);
//                        user.setTel(tel);
//                        user.setSign(sign);
//                        user.setAvatar(avatar);
                        setUserHearder(hxid, user);
                        
                       
                        ContactsDao dao = new ContactsDao(UserInfoActivity.this);
                        
                        /**2016年7月8日 10:26:17 注释*/
//                        dao.saveContact(user);
//                        DemoApplication.getInstance().getContactList().put(hxid, user);
                        
                    } 

                } catch (JSONException e) {
                     
                    e.printStackTrace();
                }
            }
        });
    }
    
}
