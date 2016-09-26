package com.wemolian.app.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.WeMoLianHXSDKHelper;
import com.wemolian.app.R;
import com.wemolian.app.TestActivity;
import com.wemolian.app.lock.pattern.UnlockGesturePasswordActivity;
import com.wemolian.app.wml.AddFriendsTwoActivity;
import com.wemolian.app.wml.ChooseActivity;
import com.wemolian.app.wml.LockActivity;
import com.wemolian.app.wml.LoginActivity;
import com.wemolian.app.wml.MainActivity;
import com.wemolian.app.wml.RegisterActivity;
import com.wemolian.app.wml.RegisterUpdateActivity;
import com.wemolian.app.wml.circle.AddCircleFriendActivity;

/**
 * 开屏页
 *
 */
public class SplashActivity extends BaseActivity {
 
//	private TextView versionText;
	
	private static final int sleepTime = 2000;

	@Override
	protected void onCreate(Bundle arg0) {
	    final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		super.onCreate(arg0);
		initFile() ;
		 
//		versionText = (TextView) findViewById(R.id.tv_version);

//		versionText.setText(getVersion());

	}

	@Override
	protected void onStart() {
		super.onStart();

		new Thread(new Runnable() {
			public void run() {
				if (WeMoLianHXSDKHelper.getInstance().isLogined()) {
					// ** 免登陆情况 加载所有本地群和会话
					//不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
					//加上的话保证进了主页面会话和群组都已经load完毕
					long start = System.currentTimeMillis();
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					long costTime = System.currentTimeMillis() - start;
					//等待sleeptime时长
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					//
					//进入主页面
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}else {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						
					}
					startActivity(new Intent(SplashActivity.this, ChooseActivity.class));
//					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//					startActivity(new Intent(SplashActivity.this, AddFriendCircleActivity.class));
//					startActivity(new Intent(SplashActivity.this, AddFriendsTwoActivity.class));
//					startActivity(new Intent(SplashActivity.this, TestActivity.class));
//					Intent intent=new Intent();  
//                    intent.setClass(SplashActivity.this, RegisterUpdateActivity.class);//从一个activity跳转到另一个activity  
//                    intent.putExtra("userEName", "123");//给intent添加额外数据，key为“userEName”,key值为用户名  
//                    intent.putExtra("password", "123");
//                    startActivity(intent);
					finish();
				}
			}
		}).start();

	}
	
	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号错误";
		}
	}
	 @SuppressLint("SdCardPath")
     public void initFile() {

      File dir = new File("/sdcard/wemolian");

         if (!dir.exists()) {
             dir.mkdirs();
         }
     }
}
