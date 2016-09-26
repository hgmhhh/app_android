package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.view.sliding.AbSlidingPlayView;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 在开屏页结束后，跳转到登录和注册的选择界面
 * 
 * @author zhangyun
 * 
 */
public class ChooseActivity extends BaseActivity {
	private AbSlidingPlayView mSlidingPlayView;
	public LayoutInflater mInflater;

	Button btn_login, btn_register;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_login_temp);
		mSlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mAbSlidingPlayView);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ChooseActivity.this,
						LoginActivity.class));
				finish();
			}
		});
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ChooseActivity.this,
						RegisterActivity.class)
						.putExtra("activity", "register")

				);
			}
		});
		makeDate();
	}

	private void makeDate() {
		List<Integer> list = new ArrayList<>();
		list.add(R.drawable.logo_wemolian);
		list.add(R.drawable.logo_wemolian);
		list.add(R.drawable.logo_wemolian);
		initPayView(list);
	}

	/**
	 * 轮播图测试数据
	 */
	private void initPayView(List<Integer> list) {
		mInflater = LayoutInflater.from(this);
		for (int i = 0; i < list.size(); i++) {
			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			TextView mPlayText = (TextView) mPlayView
					.findViewById(R.id.mPlayText);
			mPlayText.setText("");
			mPlayImage.setImageResource(list.get(i));
			// TicketApplication.getInstance().display(mPlayImage,
			// list.get(i).getString("image"));
			mSlidingPlayView.addView(mPlayView);
		}
		mSlidingPlayView.setNavHorizontalGravity(Gravity.RIGHT);
		mSlidingPlayView.startPlay();
	}

}
