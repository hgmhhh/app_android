package com.wemolian.app.wml.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 消息提醒设置
 * @author zhangyun
 *
 */
public class ProfileSettingRemindActivity extends BaseActivity {

	
	ImageView iv_switch_open_notification;
	ImageView iv_switch_close_notification;
	ImageView iv_switch_open_sound;
	ImageView iv_switch_close_sound;
	ImageView iv_switch_open_vibrate;
	ImageView iv_switch_close_vibrate;
	ImageView iv_switch_open_speaker;
	ImageView iv_switch_close_speaker;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_msg_remind);
		setStatus(findViewById(R.id.title));
		initView();
		initData();
		initListener();
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		iv_switch_open_notification = (ImageView) findViewById(R.id.iv_switch_open_notification);
		iv_switch_close_notification = (ImageView) findViewById(R.id.iv_switch_close_notification);
		iv_switch_open_sound = (ImageView) findViewById(R.id.iv_switch_open_sound);
		iv_switch_close_sound = (ImageView) findViewById(R.id.iv_switch_close_sound);
		iv_switch_open_vibrate = (ImageView) findViewById(R.id.iv_switch_open_vibrate);
		iv_switch_close_vibrate = (ImageView) findViewById(R.id.iv_switch_close_vibrate);
		iv_switch_open_speaker = (ImageView) findViewById(R.id.iv_switch_open_speaker);
		iv_switch_close_speaker = (ImageView) findViewById(R.id.iv_switch_close_speaker);
	}
	/**
	 * 控件赋值
	 */
	private void initData() {
		iv_switch_open_notification.setVisibility(View.GONE);
		iv_switch_close_notification.setVisibility(View.VISIBLE);
		iv_switch_open_sound.setVisibility(View.GONE);
		iv_switch_close_sound.setVisibility(View.VISIBLE);
		iv_switch_open_vibrate.setVisibility(View.GONE);
		iv_switch_close_vibrate.setVisibility(View.VISIBLE);
		iv_switch_open_speaker.setVisibility(View.GONE);
		iv_switch_close_speaker.setVisibility(View.VISIBLE);
		
	}
	/**
	 * 控件监听
	 */
	private void initListener() {
		iv_switch_open_notification.setOnClickListener(handler);
		iv_switch_close_notification.setOnClickListener(handler);
		iv_switch_open_sound.setOnClickListener(handler);
		iv_switch_close_sound.setOnClickListener(handler);
		iv_switch_open_vibrate.setOnClickListener(handler);
		iv_switch_close_vibrate.setOnClickListener(handler);
		iv_switch_open_speaker.setOnClickListener(handler);
		iv_switch_close_speaker.setOnClickListener(handler);
		
	}
	View.OnClickListener handler = new View.OnClickListener(){
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_switch_open_notification: 
				iv_switch_open_notification.setVisibility(View.GONE);
				iv_switch_close_notification.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_close_notification: 
				iv_switch_close_notification.setVisibility(View.GONE);
				iv_switch_open_notification.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_open_sound: 
				iv_switch_open_sound.setVisibility(View.GONE);
				iv_switch_close_sound.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_close_sound: 
				iv_switch_close_sound.setVisibility(View.GONE);
				iv_switch_open_sound.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_open_vibrate: 
				iv_switch_open_vibrate.setVisibility(View.GONE);
				iv_switch_close_vibrate.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_close_vibrate: 
				iv_switch_close_vibrate.setVisibility(View.GONE);
				iv_switch_open_vibrate.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_open_speaker: 
				iv_switch_open_speaker.setVisibility(View.GONE);
				iv_switch_close_speaker.setVisibility(View.VISIBLE);
				break;
			case R.id.iv_switch_close_speaker: 
				iv_switch_close_speaker.setVisibility(View.GONE);
				iv_switch_open_speaker.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
}
