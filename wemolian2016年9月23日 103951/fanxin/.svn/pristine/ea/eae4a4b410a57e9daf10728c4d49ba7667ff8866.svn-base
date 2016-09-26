package com.wemolian.app.lock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.lock.number.NumberLockActivity;
import com.wemolian.app.lock.pattern.UnlockGesturePasswordActivity;


/**
 * 设备锁入口
 * @author zhangyun 2016.07.15
 *
 */
public class ChooseLockActivity  extends BaseActivity{
	/**
	 * 数字锁
	 */
	private RelativeLayout rl_lock_num;
	/**
	 * 手势锁
	 */
	private RelativeLayout rl_lock_hand;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_choose_lock_type);
		setStatus(findViewById(R.id.title));
		initView();
	}



	/**
	 * 初始化
	 */
	private void initView() {
		rl_lock_num = (RelativeLayout) this.findViewById(R.id.rl_lock_num);
		rl_lock_hand = (RelativeLayout) this.findViewById(R.id.rl_lock_hand);
		
		rl_lock_num.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(getApplicationContext(),));
//				Toast.makeText(getApplicationContext(), "数字锁功能正在开发中……", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), NumberLockActivity.class));
			}
		});
		
		rl_lock_hand.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "选择了手势锁", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), UnlockGesturePasswordActivity.class));
			}
		});
	}
	
}
