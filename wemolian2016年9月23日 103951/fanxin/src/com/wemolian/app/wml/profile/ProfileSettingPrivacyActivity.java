package com.wemolian.app.wml.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 隐私设置
 * @author zhangyun
 *
 */
public class ProfileSettingPrivacyActivity extends BaseActivity {

	LinearLayout ll_whoCanScan;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_profile_privacy);
		setStatus(findViewById(R.id.title));
		ll_whoCanScan = (LinearLayout) findViewById(R.id.ll_who_can_scan);
		ll_whoCanScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileSettingPrivacyActivity.this, WhoCanSeeActivity.class));
			}
		});
	}
}
