package com.wemolian.app.wml.circle;


import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.video.util.Bimp;
import com.wemolian.app.video.util.PublicWay;
import com.wemolian.app.wml.circle.adapter.FolderAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author zhangyun
 */
public class ImageFile extends BaseActivity {

	private FolderAdapter folderAdapter;
	private Button bt_cancel;
	private Context mContext;
	private String activity;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_image_file);
		setStatus(findViewById(R.id.title));
		PublicWay.activityList.add(this);
		mContext = this;
		activity = getIntent().getStringExtra("activity");
		bt_cancel = (Button) findViewById(R.id.btn_cancel);
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView textView = (TextView) findViewById(R.id.tv_title);
		textView.setText("选择相册");
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
//			Bimp.tempSelectBitmap.clear();
//			Intent intent = new Intent();
//			intent.setClass(mContext, AddFriendCircleActivity.class);
//			startActivity(intent);
			finish();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (activity) {
			case "AddCircleFriendActivity":
				Intent intent = new Intent();
				intent.setClass(mContext, AddCircleFriendActivity.class);
				startActivity(intent);
				break;
			case "AddCircleMianMianActivity":
				Intent intent1 = new Intent();
				intent1.setClass(mContext, AddCircleMianMianActivity.class);
				startActivity(intent1);
				break;
			}
			
			
		}
		
		return true;
	}

}
