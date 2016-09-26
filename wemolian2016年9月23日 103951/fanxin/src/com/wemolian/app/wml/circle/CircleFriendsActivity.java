package com.wemolian.app.wml.circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.video.util.PublicWay;
import com.wemolian.app.wml.LoginActivity;
import com.wemolian.app.wml.MainActivity;
import com.wemolian.app.wml.circle.adapter.CircleFriendsAdapter;
import com.wemolian.app.wml.entity.Comment;
import com.wemolian.app.wml.entity.Data;
import com.wemolian.app.wml.others.AutoListView;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.AutoListView.OnLoadListener;
import com.wemolian.app.wml.others.AutoListView.OnRefreshListener;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.others.LocalUserInfo;

/**
 * 朋友圈
 * 
 * @author 张云
 *
 */
public class CircleFriendsActivity extends BaseActivity implements
		OnRefreshListener, OnLoadListener, OnClickListener {
	
	//上次加载到的item位置
	int last,top;
	View parentview,view;
	PopupWindow pop;
	private ProgressDialog dialog;
	AutoListView autoListView;
	CircleFriendsAdapter circleFriendsAdapter;
	private LinearLayout circleFriendsCommentGroup1;
	private ImageView circleIvCommentGroupIcon;
	private ImageView addFriendCircleImg;
	private String userId = LocalUserInfo.getInstance(this).getUserInfo(
			LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
	List list = new ArrayList();
	String time = "0";
	private int page = 1;
	private boolean firstLoad = true;
	private String commentCircleId;
	private String friendId;
	
	private Button btnSendComment;
	private TextView commentText;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			List result = (List) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				autoListView.onRefreshComplete();
//				list.clear();
//				list.addAll(result);
				break;
			case AutoListView.LOAD:
				autoListView.onLoadComplete();
//				list.addAll(result);
				break;
			}

			autoListView.setResultSize(result.size());
			WeMoLianApplication.last_time = time;
			WeMoLianApplication.page = page;
		};
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentview = getLayoutInflater().inflate(R.layout.circle_friends_head, null);
		setContentView(parentview);
		setStatus(findViewById(R.id.title));
		
		// 默认软键盘不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		initView();
		getFriendCircleData(AutoListView.LOAD);
//		initData();
//		list = new ArrayList();
//		for (int i = 0; i < 4; i++) {
//			list.add("测试" + i);
//		}


	}

	private void initData() {
		if(list != null){
			
			circleFriendsAdapter = new CircleFriendsAdapter(list, this);
			boolean loadend = CircleFriendsAdapter.loadEnd;
			autoListView.setAdapter(circleFriendsAdapter);
			autoListView.setOnRefreshListener(this);
			autoListView.setOnLoadListener(this);
//		autoListView.setOnScrollListener(new ScrollListView());
			scrollToBottom();
			initListener();
		}
	}

	private void initListener() {
		addFriendCircleImg.setOnClickListener(this);
		btnSendComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				commentText = (TextView) view.findViewById(R.id.circle_friends_et_sendmessage);
				commentText.setBackgroundResource(R.drawable.input_bar_bg_active);
				int s = commentText.getText().toString().trim().length();
				
				if(commentText != null && s > 0  ){
					//发表评论数据
					sendComment();
				}else{
//				Toast.makeText(CircleFriendsActivity.this, 
//						commentCircleId + ":" +commentText.getText().toString().trim()
//						+",userId:" + userId
//						+ ",friendId" + friendId, Toast.LENGTH_SHORT).show();
					Toast.makeText(CircleFriendsActivity.this, "说点什么吧！", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void initView() {
		firstLoad = true;
		dialog = new ProgressDialog(CircleFriendsActivity.this);
		addFriendCircleImg = (ImageView) findViewById(R.id.img_friend_circle_add);
		autoListView = (AutoListView) findViewById(R.id.circle_friends_listview);
//		circleFriendsCommentGroup = (LinearLayout) findViewById(R.id.circle_friends_comment_group);
		// 默认隐藏布局
//		circleFriendsCommentGroup.setVisibility(View.GONE);
		view = getLayoutInflater().inflate(R.layout.item_comment_input, null);
		btnSendComment = (Button) view.findViewById(R.id.circle_friends_btn_send_comment);
		pop = new PopupWindow(CircleFriendsActivity.this);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
	}


	private void loadData(final int what) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				getFriendCircleData(what);
			}
		});
	}

	@Override
	public void onLoad() {
		page = page + 1;
		last = autoListView.getFirstVisiblePosition();
		View v = autoListView.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
		
//		Toast.makeText(getApplicationContext(), "加载中……" + page,
//		Toast.LENGTH_LONG).show();
		loadData(AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
//		List list = new ArrayList();
//		list.clear();
//		page = 1;
		loadData(AutoListView.REFRESH);
//		page = 0;
	}


	/**
	 * 设置点击评论按钮的功能效果
	 */
	int commentTag = 0;

	public void showFriendsCommentGroup(View v) {
		 
		Data data = (Data) v.getTag();
		commentCircleId = data.getCircleid();
		friendId = data.getExternalUse();
		circleIvCommentGroupIcon = (ImageView) findViewById(R.id.circle_iv_comment_icon);
		pop.showAtLocation(parentview, Gravity.BOTTOM, 0, 0);
//		Toast.makeText(getApplicationContext(), "点击了评论按钮", Toast.LENGTH_LONG)
//				.show();
//		if (commentTag == 0) {
//			// 默认隐藏布局
//			circleFriendsCommentGroup.setVisibility(View.VISIBLE);
//			commentTag = 1;
//		} else if (commentTag == 1) {
//			circleFriendsCommentGroup.setVisibility(View.GONE);
//			commentTag = 0;
//		}
	}
	
	/**
	 * 回复评论
	 * @param v
	 */
	public void replyComment(View v){
		pop.showAtLocation(parentview, Gravity.BOTTOM, 0, 0);
		Comment c = (Comment) v.getTag();
		commentCircleId = c.getCircleId();
		userId = LocalUserInfo.getInstance(CircleFriendsActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		friendId = c.getUserId();
//		sendComment();
	}

	/**
	 * 评论的 发送按钮
	 * 
	 * @param v
	 */
	public void sendComment() {
		Map<String, String> addCommonMap = new HashMap<String, String>();
		addCommonMap.put("circleId", commentCircleId);
		addCommonMap.put("UserId", userId);
		addCommonMap.put("friendId", friendId);
		addCommonMap.put("content", commentText.getText().toString().trim());
		/**
		 * 点击发表评论按钮后，隐藏输入框组件
		 */
		pop.dismiss();
		LoadDataFromServer task = new LoadDataFromServer(this,
				Constant.URL_CIRCLE_FRIEND_ADD_COMMENT, addCommonMap);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject sendingCommwntData) {
				if (sendingCommwntData.getBoolean("success")) {
					JSONObject data = sendingCommwntData.getJSONObject("data");
					int code = data.getInteger("code");
					if (code == 200) {
						Toast.makeText(CircleFriendsActivity.this,"评论成功" , Toast.LENGTH_SHORT).show();
						//隐藏布局
//						circleFriendsCommentGroup.setVisibility(View.GONE);
						commentTag = 0;
					}else if(code == 400){
						Toast.makeText(CircleFriendsActivity.this,"评论失败" , Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(CircleFriendsActivity.this,"未知的状态码" , Toast.LENGTH_SHORT).show();
						
					}
				}
			}

		});
		// LoadDataFromServer task = new
		// LoadDataFromServer(CircleFriendsActivity.this,
		// com.wemolian.app.entity.Constant.URL_FRIEND_CIRCLE_ADD_COMMENT,
		// addCommonMap);
	}

	/***
	 * 删除朋友圈评论
	 */
	private void deleteComment() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("circleId", "");
		LoadDataFromServer task = new LoadDataFromServer(
				CircleFriendsActivity.this,
				Constant.URL_FRIEND_CIRCLE_DELETE_COMMENT, map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject deleteCommentData) {
				// TODO Auto-generated method stub
				if (deleteCommentData.getBoolean("success")) {
					JSONObject data = deleteCommentData.getJSONObject("data");
					int code = data.getInteger("code");
					if (code == 200) {

					}
				}
			}

		});

	}

	/***
	 * 刷新朋友圈数据  - 适用于初始的加载朋友圈的数据
	 */
	private void getFriendCircleData(final int state) {
		if(firstLoad){
			dialog.setMessage("正在加载列表...");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
			list.clear();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("userId", userId);
		LoadDataFromServer task = new LoadDataFromServer(
				CircleFriendsActivity.this, Constant.URL_GET_FRIEND_CIRCLE, map);

		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject resData) {

				if (resData != null && resData.getBoolean("success")) {
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						if ("200".equals(code)) {
							JSONArray a = data.getJSONArray("data");
//							list.clear();
							list.addAll(data.getJSONArray("data"));
							if(!firstLoad){
								Message msg = handler.obtainMessage();
								msg.what = state;
								msg.obj = list;
								handler.sendMessage(msg);
//								autoListView.setAdapter(circleFriendsAdapter);
							}
							initData();
							dialog.dismiss();
							firstLoad = false;
						}else if("201".equals(code)){
							dialog.dismiss();
							Toast.makeText(CircleFriendsActivity.this, "未查询到数据", Toast.LENGTH_SHORT).show();
						}else if("400".equals(code)){
							dialog.dismiss();
							Toast.makeText(CircleFriendsActivity.this, "查询数据失败", Toast.LENGTH_SHORT).show();
							
						}
						
					}
				}
			}
		});
	}

	/****
	 * 删除朋友圈
	 */
	private void deleteFriendCircle() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("externalUse", "");
		LoadDataFromServer task = new LoadDataFromServer(
				CircleFriendsActivity.this, Constant.URL_DELETE_FRIEND_CIRCLE,
				map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject deleteFriendCircleData) {
				// TODO Auto-generated method stub
				if (deleteFriendCircleData.getBoolean("success")) {
					JSONObject data = deleteFriendCircleData
							.getJSONObject("data");
					int code = data.getInteger("code");
					if (code == 200) {

					}
				}
			}
		});

	}

	/***
	 * 修改朋友圈信息 imagesName －－图片的名字 imagesText－－图片的描述
	 */
	private void modifyFriendCircleInfo() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("externalUse", "");
		map.put("imagesName", "");
		map.put("imagesText", "");
		LoadDataFromServer task = new LoadDataFromServer(this,
				Constant.URL_UPDATE_FRIEND_CIRCLE_INFO, map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject updateFriendCircleInfo) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if (updateFriendCircleInfo.getBoolean("success")) {
					JSONObject data = updateFriendCircleInfo
							.getJSONObject("data");
					int code = data.getInteger("code");
					if (code == 200) {

					}
				}

			}
		});

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		/**
		 * 朋友圈右上角图标点击事件
		 */
		case R.id.img_friend_circle_add:
			Intent intent = new Intent(CircleFriendsActivity.this,
					AddCircleFriendActivity.class);
			intent.putExtra("isFirst", true);
			startActivity(intent);
			break;

		}
	}
	/**
	 * 加载数据后，焦点置于底部 -->上一次加载处
	 */
	private void scrollToBottom() {
	    autoListView.post(new Runnable() {
	        @Override
	        public void run() {
	        	if(!firstLoad){
	        		autoListView.setSelectionFromTop(last, top);
	        	}
	        }
	    });
	}
	
	
	
	class ScrollListView implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			switch (scrollState) {  
            case OnScrollListener.SCROLL_STATE_IDLE: //  
//                BtnCheck(currentPage);  
                // mBusy = false;  
                System.out.println("停止...");  
                break;  
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
//                BtnCheck(0);  
                // mBusy = true;  
                System.out.println("正在滑动...");  
                break;  
            case OnScrollListener.SCROLL_STATE_FLING:  
//                BtnCheck(0);  
                // mBusy = true;  
                System.out.println("开始滚动...");  
                break;  
            }  
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void back(View view) {
//		PublicWay.activityList.clear();
		
		finish();
//		startActivity(new Intent(CircleFriendsActivity.this, MainActivity.class));
	}
	
	// 隐藏软键盘
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (CircleFriendsActivity.this.getCurrentFocus() != null) {
				if (CircleFriendsActivity.this.getCurrentFocus()
						.getWindowToken() != null) {
					imm.hideSoftInputFromWindow(CircleFriendsActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	
	public void onTouch(View v){
		/**
		 * 将软键盘隐藏
		 */
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘  
	    pop.dismiss();
	}
}
