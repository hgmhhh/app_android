package com.wemolian.app.wml.profile;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.wml.profile.adapter.ProfileBankCardAdapter;

/**
 * 银行卡
 * @author zhangyun
 *
 */
public class ProfileBankCardActivity extends BaseActivity {

	ListView listView;
	ProfileBankCardAdapter bankAdapter;
	List<String> list = new ArrayList<String>();
	View ll_addNewBankCard;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_profile_mybank_card);
		setStatus(findViewById(R.id.title));
		listView = (ListView) findViewById(R.id.lv_profile_bankcard);
		ll_addNewBankCard = getLayoutInflater().from(this).inflate(R.layout.item_profile_add_new_bankcard, null);

		for (int i = 0; i < 5; i++) {
			list.add("123123");
		}
		bankAdapter = new ProfileBankCardAdapter(this,list);
		listView.addFooterView(ll_addNewBankCard);
		listView.setAdapter(bankAdapter);
		bankAdapter.notifyDataSetChanged();
		
		
		ll_addNewBankCard.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(ProfileBankCardActivity.this, ProfileAddNewCardActivity.class));
//			Toast.makeText(ProfileBankCardActivity.this, "添加银行卡", Toast.LENGTH_SHORT).show();
		}
	});
	}
}
