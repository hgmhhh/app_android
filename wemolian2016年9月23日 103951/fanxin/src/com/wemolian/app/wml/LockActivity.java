package com.wemolian.app.wml;

import android.os.Bundle;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

public class LockActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock_hand);
		setStatus(findViewById(R.id.title));
	}
}
