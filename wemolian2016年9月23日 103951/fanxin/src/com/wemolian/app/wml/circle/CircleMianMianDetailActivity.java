package com.wemolian.app.wml.circle;

import android.os.Bundle;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 免吧物品详细信息
 * @author zhangyun
 *
 */
public class CircleMianMianDetailActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_mianmian_detail_activity);
		setStatus(findViewById(R.id.title));
	}
}
