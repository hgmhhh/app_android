package com.wemolian.app.lock.pattern;


import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.lock.view.LockPatternUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GuideGesturePasswordActivity extends BaseActivity {
	Button btnGesturepwdGuide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_hand_guide);
		setStatus(findViewById(R.id.title));
		//点击 创建按钮
		btnGesturepwdGuide = (Button) findViewById(R.id.gesturepwd_guide_btn);
		Log.i("show--","1");
		System.out.println("1");
		btnGesturepwdGuide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("2");
				Log.i("show--","2");
				WeMoLianApplication s = WeMoLianApplication.getInstance();
				
				LockPatternUtils s1 = WeMoLianApplication.getInstance().getLockPatternUtils();
				
				WeMoLianApplication.getInstance().getLockPatternUtils().clearLock();
				System.out.println("3");
				Log.i("show--","3");
				Intent intent = new Intent(GuideGesturePasswordActivity.this,
						CreateGesturePasswordActivity.class);
				System.out.println("4");
				Log.i("show--","4");
				// 打开新的Activity
				startActivity(intent);
				Log.i("show--","4");
				finish();
			}
		});
	}

}
