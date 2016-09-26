package com.wemolian.app.wml.circle;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

public class SendComment extends BaseActivity {

	TextView text;
	int i;
	int j;
	/**
	 * 评论的 发送按钮
	 * @param v
	 */
	public void sendComment(View v){
		switch (v.getId()) {
		case R.id.circle_weshop_btn_send_comment:
			i = R.id.circle_weshop_btn_send_comment;
			j =v.getId();
			text = (TextView) findViewById(R.id.circle_weshop_et_sendmessage);
			Toast.makeText(SendComment.this, "发送评论数据", Toast.LENGTH_SHORT).show();
			Toast.makeText(SendComment.this, text.getText().toString()+","+j +"," + i, Toast.LENGTH_LONG).show();
			break;

		case R.id.circle_friends_btn_send_comment:
			i = R.id.circle_friends_btn_send_comment;
			j =v.getId();
			text = (TextView) findViewById(R.id.circle_friends_et_sendmessage);
			Toast.makeText(SendComment.this, "发送评论数据", Toast.LENGTH_SHORT).show();
			Toast.makeText(SendComment.this, text.getText().toString()+","+
					j +"," + i, Toast.LENGTH_LONG).show();		
			break;
		}
	}
}
