 
package com.wemolian.app.wml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.activity.SplashActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.lock.ChooseLockActivity;
import com.wemolian.app.utils.DataCleanManager;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.profile.NoDisturbActivity;
import com.wemolian.app.wml.profile.ProfileSettingAboutActivity;
import com.wemolian.app.wml.profile.ProfileSettingPrivacyActivity;
import com.wemolian.app.wml.profile.ProfileSettingRemindActivity;
import com.wemolian.applib.controller.HXSDKHelper;
 

/**
 * 设置界面
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

    /**
     * 设置新消息通知布局
     */
    private RelativeLayout rl_switch_notification;
    /**
     * 设置声音布局
     */
    private RelativeLayout rl_switch_sound;
    /**
     * 设置震动布局
     */
    private RelativeLayout rl_switch_vibrate;
    /**
     * 设置扬声器布局
     */
    private RelativeLayout rl_switch_speaker;

    /**
     * 打开新消息通知imageView
     */
    private ImageView iv_switch_open_notification;
    /**
     * 关闭新消息通知imageview
     */
    private ImageView iv_switch_close_notification;
    /**
     * 打开声音提示imageview
     */
    private ImageView iv_switch_open_sound;
    /**
     * 关闭声音提示imageview
     */
    private ImageView iv_switch_close_sound;
    /**
     * 打开消息震动提示
     */
    private ImageView iv_switch_open_vibrate;
    /**
     * 关闭消息震动提示
     */
    private ImageView iv_switch_close_vibrate;
    /**
     * 打开扬声器播放语音
     */
    private ImageView iv_switch_open_speaker;
    /**
     * 关闭扬声器播放语音
     */
    private ImageView iv_switch_close_speaker;

    /**
     * 消息提示设置
     */
    private RelativeLayout rl_message_setting;
    /**
     * 消息提醒设置布局
     */
    private LinearLayout ll_message_setting;
    /**
     * 帐号安全设置
     */
    private RelativeLayout rl_safe_setting;
    /**
     * 关于
     */
    private RelativeLayout rl_setting_about;
    /**
     * 勿扰模式
     */
    private RelativeLayout rl_no_disturb;
    /**
     * 隐私
     */
    private RelativeLayout rl_privacy;
    
    /**
     * 退出按钮
     */
    private Button logoutBtn;

    private EMChatOptions chatOptions;
 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting_activity);
        setStatus(findViewById(R.id.title));
      
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        rl_switch_notification = (RelativeLayout) this.findViewById(R.id.rl_switch_notification);
        rl_switch_sound = (RelativeLayout) this.findViewById(R.id.rl_switch_sound);
        rl_switch_vibrate = (RelativeLayout) this.findViewById(R.id.rl_switch_vibrate);
        rl_switch_speaker = (RelativeLayout) this.findViewById(R.id.rl_switch_speaker);
        rl_message_setting = (RelativeLayout) this.findViewById(R.id.rl_message_setting);
        ll_message_setting = (LinearLayout) this.findViewById(R.id.ll_message_setting);
        rl_safe_setting = (RelativeLayout) this.findViewById(R.id.rl_safe);
        rl_setting_about = (RelativeLayout) this.findViewById(R.id.rl_setting_about);
        rl_no_disturb = (RelativeLayout) this.findViewById(R.id.rl_no_disturb);
        rl_privacy = (RelativeLayout) this.findViewById(R.id.rl_privacy);
        
        iv_switch_open_notification = (ImageView) this.findViewById(R.id.iv_switch_open_notification);
        iv_switch_close_notification = (ImageView) this.findViewById(R.id.iv_switch_close_notification);
        iv_switch_open_sound = (ImageView) this.findViewById(R.id.iv_switch_open_sound);
        iv_switch_close_sound = (ImageView) this.findViewById(R.id.iv_switch_close_sound);
        iv_switch_open_vibrate = (ImageView) this.findViewById(R.id.iv_switch_open_vibrate);
        iv_switch_close_vibrate = (ImageView) this.findViewById(R.id.iv_switch_close_vibrate);
        iv_switch_open_speaker = (ImageView) this.findViewById(R.id.iv_switch_open_speaker);
        iv_switch_close_speaker = (ImageView) this.findViewById(R.id.iv_switch_close_speaker);
        logoutBtn = (Button) this.findViewById(R.id.btn_logout);     
        rl_switch_notification.setOnClickListener(this);
        rl_switch_sound.setOnClickListener(this);
        rl_switch_vibrate.setOnClickListener(this);
        rl_switch_speaker.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
      
        

        chatOptions = EMChatManager.getInstance().getChatOptions();
        if (chatOptions.getNotificationEnable()) {
            iv_switch_open_notification.setVisibility(View.VISIBLE);
            iv_switch_close_notification.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_notification.setVisibility(View.INVISIBLE);
            iv_switch_close_notification.setVisibility(View.VISIBLE);
        }
        if (chatOptions.getNoticedBySound()) {
            iv_switch_open_sound.setVisibility(View.VISIBLE);
            iv_switch_close_sound.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_sound.setVisibility(View.INVISIBLE);
            iv_switch_close_sound.setVisibility(View.VISIBLE);
        }
        if (chatOptions.getNoticedByVibrate()) {
            iv_switch_open_vibrate.setVisibility(View.VISIBLE);
            iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
            iv_switch_close_vibrate.setVisibility(View.VISIBLE);
        }

        if (chatOptions.getUseSpeaker()) {
            iv_switch_open_speaker.setVisibility(View.VISIBLE);
            iv_switch_close_speaker.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_speaker.setVisibility(View.INVISIBLE);
            iv_switch_close_speaker.setVisibility(View.VISIBLE);
        }
        
        
        /**
         * 帐号安全设置跳转
         */
        rl_safe_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//                startActivity(new Intent(getActivity(), SettingActivity.class));
				startActivity(new Intent(getApplicationContext(),ChooseLockActivity.class));		
				
			
			}
		});
        

        /**
         * 默认设置不显示
         */
        ll_message_setting.setVisibility(View.GONE);
        /**
         * 监听 消息提醒的点击事件
         */
        rl_message_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(showTag == 0){
//					ll_message_setting.setVisibility(View.VISIBLE);
//					showTag =1;
//				}else{
//					ll_message_setting.setVisibility(View.GONE);
//					showTag = 0;
//				}
				startActivity(new Intent(SettingActivity.this, ProfileSettingRemindActivity.class));
				
			}
		});
        
        /**
         * 点击关于时的跳转
         */
        rl_setting_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingActivity.this, ProfileSettingAboutActivity.class));
				finish();
			}
		});
        
        /**
         * 勿扰模式
         */
        rl_no_disturb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingActivity.this, NoDisturbActivity.class));
			}
		});
        
        /**
         * 隐私设置
         */
        rl_privacy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingActivity.this, ProfileSettingPrivacyActivity.class));
			}
		});
    }

    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.rl_switch_notification:
            if (iv_switch_open_notification.getVisibility() == View.VISIBLE) {
                iv_switch_open_notification.setVisibility(View.INVISIBLE);
                iv_switch_close_notification.setVisibility(View.VISIBLE);
                rl_switch_sound.setVisibility(View.GONE);
                rl_switch_vibrate.setVisibility(View.GONE);
               
                chatOptions.setNotificationEnable(false);
                EMChatManager.getInstance().setChatOptions(chatOptions);

                HXSDKHelper.getInstance().getModel().setSettingMsgNotification(false);
            } else {
                iv_switch_open_notification.setVisibility(View.VISIBLE);
                iv_switch_close_notification.setVisibility(View.INVISIBLE);
                rl_switch_sound.setVisibility(View.VISIBLE);
                rl_switch_vibrate.setVisibility(View.VISIBLE);
               
                chatOptions.setNotificationEnable(true);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgNotification(true);
            }
            break;
        case R.id.rl_switch_sound:
            if (iv_switch_open_sound.getVisibility() == View.VISIBLE) {
                iv_switch_open_sound.setVisibility(View.INVISIBLE);
                iv_switch_close_sound.setVisibility(View.VISIBLE);
                chatOptions.setNoticeBySound(false);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
            } else {
                iv_switch_open_sound.setVisibility(View.VISIBLE);
                iv_switch_close_sound.setVisibility(View.INVISIBLE);
                chatOptions.setNoticeBySound(true);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
            }
            break;
        case R.id.rl_switch_vibrate:
            if (iv_switch_open_vibrate.getVisibility() == View.VISIBLE) {
                iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
                iv_switch_close_vibrate.setVisibility(View.VISIBLE);
                chatOptions.setNoticedByVibrate(false);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(false);
            } else {
                iv_switch_open_vibrate.setVisibility(View.VISIBLE);
                iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
                chatOptions.setNoticedByVibrate(true);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
            }
            break;
        case R.id.rl_switch_speaker:
            if (iv_switch_open_speaker.getVisibility() == View.VISIBLE) {
                iv_switch_open_speaker.setVisibility(View.INVISIBLE);
                iv_switch_close_speaker.setVisibility(View.VISIBLE);
                chatOptions.setUseSpeaker(false);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgSpeaker(false);
            } else {
                iv_switch_open_speaker.setVisibility(View.VISIBLE);
                iv_switch_close_speaker.setVisibility(View.INVISIBLE);
                chatOptions.setUseSpeaker(true);
                EMChatManager.getInstance().setChatOptions(chatOptions);
                HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
            }
            break;
        case R.id.btn_logout: //退出登陆
            logout();
            break;
     
        default:
            break;
        }

    }

    
    
    
    void logout() {
        final ProgressDialog pd = new ProgressDialog(SettingActivity.this);
        pd.setMessage("正在退出登陆..");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        final Map<String, String> map = new HashMap<String, String>();
        String externalUse = LocalUserInfo.getInstance(SettingActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
        String userEName = LocalUserInfo.getInstance(SettingActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME);
        map.put("externalUse", externalUse);
        map.put("userEName", userEName);
        WeMoLianApplication.getInstance().logout(new EMCallBack() {
            
            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                      LoadDataFromServer task = new LoadDataFromServer(
                    		  SettingActivity.this, Constant.URL_PRELOGIN, map);
                      task.getData(new DataCallBack() {
						
						@Override
						public void onDataCallBack(JSONObject data) {
							System.out.println(data);
						}
					});
                      
                      DataCleanManager.cleanInternalCache(getApplicationContext());
                      pd.dismiss();
                      finish();
                      // 重新显示登陆页面
                      startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                      destoryActivity("mainActivity");
                      
                    }
                });
            }
            
            @Override
            public void onProgress(int progress, String status) {
                
            }
            
            @Override
            public void onError(int code, String message) {
                
            }
        });
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        
        super.onSaveInstanceState(outState);
        
    }
    
    public void back(View view){
        
        finish();
    }

    
}
