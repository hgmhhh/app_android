package com.wemolian.app.wml.profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 勿扰模式页面
 * @author zhangyun
 *
 */
public class NoDisturbActivity extends BaseActivity {

	ImageView iv_switchNodisturb,iv_switchUnNodisturb;
	View v_distrud_tag;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_no_disturb);
		setStatus(findViewById(R.id.title));
		iv_switchNodisturb  = (ImageView) findViewById(R.id.iv_switch_nodisturb); 
		iv_switchUnNodisturb  = (ImageView) findViewById(R.id.iv_switch_unnodisturb); 
		v_distrud_tag = findViewById(R.id.v_distrud_tag);
//		iv_switchNodisturb.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				 
//			}
//		});
	}
	
	public void updateDistrub(View v){
		String tag = (String) v_distrud_tag.getTag();
		 if("close".equals(tag)){
			 iv_switchNodisturb.setVisibility(View.GONE);
			 iv_switchUnNodisturb.setVisibility(View.VISIBLE);
			 v_distrud_tag.setTag("open");
		 }if("open".equals(tag)){
			 iv_switchNodisturb.setVisibility(View.VISIBLE);
			 iv_switchUnNodisturb.setVisibility(View.GONE);
			 v_distrud_tag.setTag("close");
		 }
		 String tag1 = (String) v_distrud_tag.getTag();
	}
}
