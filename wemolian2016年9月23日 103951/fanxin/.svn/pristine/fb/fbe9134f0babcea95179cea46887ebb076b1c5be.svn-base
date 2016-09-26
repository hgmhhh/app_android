package com.wemolian.app.wml.profile;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;

/**
 * 关于页面
 * @author zhangyun
 *
 */
public class ProfileSettingAboutActivity extends BaseActivity {
	LinearLayout ll_setting_about_update_version;
	TextView tv_about_this_version;
	String version;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting_about);
		setStatus(findViewById(R.id.title));
		tv_about_this_version = (TextView) findViewById(R.id.tv_about_this_version);
		ll_setting_about_update_version = (LinearLayout) findViewById(R.id.ll_setting_about_update_version);
		getVersion();
		tv_about_this_version.setText(version);
		ll_setting_about_update_version.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileSettingAboutActivity.this, UpdateVersionActivity.class));
			}
		});
	}
	
	
	
	/**
	 * 获取当前应用程序的版本号
	 */
	private void getVersion() { 
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			version = packinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			System.out.println("获取版本号错误");
		}
	}
}
