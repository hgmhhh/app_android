package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import android.R.bool;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.wml.others.ChatRoomAdapter;

@SuppressLint("InflateParams")
public class ChatRoomActivity extends BaseActivity {
	private ListView groupListView;
	protected List<Groups> grouplist;
	private ChatRoomAdapter groupAdapter;
	private Collection<Groups> groupsMap;
	TextView tv_total;
	public static ChatRoomActivity instance;
	private String groupChat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mychatroom);
		setStatus(findViewById(R.id.title));
		
		instance = this;
		grouplist = new ArrayList<Groups>();
		//是否是登录后（群信息没有更改过，包括添加群成员删除群成员以及新建群组等）
		boolean defaultGet = getIntent().getBooleanExtra("defaultGet", false);
		groupChat = getResources().getString(R.string.group_chat);
		// grouplist = EMGroupManager.getInstance().getAllGroups();
		
//		if(defaultGet){
			groupsMap = WeMoLianApplication.getInstance().getGroupsList(1).values();
			
//		}else{
//			groupsMap = WeMoLianApplication.getInstance().getGroupsList(1).values();
//			
//		}
		intiData();
		groupListView = (ListView) findViewById(R.id.groupListView);
		View headerView = LayoutInflater.from(this).inflate(
				R.layout.item_mychatroom_header, null);
		View footerView = LayoutInflater.from(this).inflate(
				R.layout.item_mychatroom_footer, null);
		tv_total = (TextView) footerView.findViewById(R.id.tv_total);
		tv_total.setText(String.valueOf(grouplist.size()) + "个群聊");
		groupAdapter = new ChatRoomAdapter(this, grouplist);
		groupListView.addHeaderView(headerView);
		groupListView.addFooterView(footerView);
		groupListView.setAdapter(groupAdapter);

		final ImageView iv_add = (ImageView) this.findViewById(R.id.iv_add);
		// ImageView iv_search = (ImageView) this.findViewById(R.id.iv_search);
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPopWindow addPopWindow = new AddPopWindow(
						ChatRoomActivity.this);
				addPopWindow.showPopupWindow(iv_add);
			}

		});
		// iv_search.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		//
		// });
	}

	private void intiData() {
		int i = 0;
		for (Groups group : groupsMap) {
			if (group != null && !groupChat.equals(group.getGroupcname())) {
				grouplist.add(group);
				
				i++;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// grouplist = EMGroupManager.getInstance().getAllGroups();
		// groupAdapter = new MyChatRoomAdapter(this, 1, grouplist);
		// groupListView.setAdapter(groupAdapter);
		// groupAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	/**
	 * 返回
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}
}
