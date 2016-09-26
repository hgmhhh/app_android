package com.wemolian.app.wml.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.wml.profile.adapter.ProfileBankCardAdapter;

/**
 * 个人钱庄的活动类
 * @author 张云
 *
 */
public class ProfileMoneyActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.profile_money_activity);
		setStatus(findViewById(R.id.title));
	}
	
	/**
	 * 获取积分功能
	 * @param v
	 */
	public void profileMoneyIntergral(View v){
		Toast.makeText(ProfileMoneyActivity.this, "获取积分功能正在开发中！", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 获取银行卡功能
	 * @param v
	 */
	public void profileMoneyBankCard(View v) {
		startActivity(new Intent(this, ProfileBankCardActivity.class));
		finish();
//		Toast.makeText(ProfileMoneyActivity.this, "获取银行卡功能正在开发中！", Toast.LENGTH_LONG).show();
	}
}
