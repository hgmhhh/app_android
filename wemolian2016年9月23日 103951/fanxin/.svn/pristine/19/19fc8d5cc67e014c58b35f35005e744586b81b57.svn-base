package com.wemolian.app.wml.friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.JsonUtil;
import com.wemolian.app.utils.LocaltionManager;
import com.wemolian.app.wml.entity.Point;
import com.wemolian.app.wml.friends.adapter.AllUserAdapter;
import com.wemolian.app.wml.friends.adapter.NearbyUserAdapter;
import com.wemolian.app.wml.others.AutoListView;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.AutoListView.OnLoadListener;
import com.wemolian.app.wml.others.AutoListView.OnRefreshListener;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.wml.profile.ProfileCollectionActivity;
import com.wemolian.app.wml.profile.ProfileCollectionDetailActivity;

/**
 * 附近的人
 * 
 * @author zhangyun
 *
 */
public class NearbyUserActivity extends BaseActivity implements
		OnRefreshListener, OnLoadListener, OnClickListener {

	private NearbyUserAdapter nearbyUserAdapter;
	private LocationManager locationManager;
	private List<Friends> list = new ArrayList<Friends>();

	int last, top;
	PopupWindow pop;
	AutoListView autoListView;
	NearbyUserAdapter adapter;
	boolean firstLoad = true;
	String userId;
	String lonAndlat;
	
	int oldPage = 2;
	int page = 1;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			List<Friends> result = (List<Friends>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				oldPage = 0;
				autoListView.onRefreshComplete();
				// list.clear();
				// list.addAll(result);
				break;
			case AutoListView.LOAD:
				autoListView.onLoadComplete();
				// list.addAll(result);
				break;
			}

			if (result == null) {
				autoListView.setResultSize(0);
			} else {
				autoListView.setResultSize(result.size());
			}
			WeMoLianApplication.last_time = "0";
			WeMoLianApplication.page = page;
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_user);
		setStatus(findViewById(R.id.title));
		autoListView = (AutoListView) findViewById(R.id.lv_list);
		userId = LocalUserInfo.getInstance(this).getUserInfo(
				LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		Map<String, Object> map = LocaltionManager.getLocaltion(this);
		lonAndlat = map.get("lat") + SysConfig.SPLIT_POINT + (Double) map.get("lon");
		getData(AutoListView.LOAD);
		
		
		getLocation();
		initListener();
	}

	private void getData(final int state) {
		if (firstLoad) {
			list.clear();
		}
		if (AutoListView.REFRESH == state) {
			list.clear();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("externalUser", userId);
		map.put("lonAndlat", lonAndlat);
		LoadDataFromServer task = new LoadDataFromServer(this,
				Constant.URL_GET_NEARBY_USER, map);
		task.getData(new DataCallBack() {

			@Override
			public void onDataCallBack(JSONObject resData) {
				if (resData != null && resData.getBooleanValue("success")) {
					JSONObject data = resData.getJSONObject("data");
					if (data != null) {
						Integer code = data.getInteger("code");
						if (code == 200) {
							JSONArray jsonArray = data.getJSONArray("data");
							if (data != null && jsonArray.size() > 0) {
								List<Friends> resList = new ArrayList<Friends>();
								for (Object obj : jsonArray) {
									Friends f = new Friends();
									JSONObject friendObj = ((JSONObject)obj).getJSONObject("nearFriends"); 
									String distance = ((JSONObject)obj).getString("distance");
									f = JsonUtil
											.jsonToFriends(friendObj);
									f.setDistance(distance);
									
									resList.add(f);
								}
								list.addAll(resList);
								// if(!firstLoad){
								Message msg = handler.obtainMessage();
								msg.what = state;
								msg.obj = resList;
								handler.sendMessage(msg);
								initData();
								firstLoad = false;
							} 
						}
						if(code == 203){
							Message msg = handler.obtainMessage();
							msg.what = state;
							msg.obj = null;
							handler.sendMessage(msg);
						}
					}
				}
			}

		});
	}

	private void initData() {
		// loadData(AutoListView.REFRESH);
		adapter = new NearbyUserAdapter(NearbyUserActivity.this,list);
		autoListView.setAdapter(adapter);
		autoListView.setOnRefreshListener(this);
		autoListView.setOnLoadListener(this);
		scrollToBottom();

	}

	int i = Constant.PAGE_SIZE;

	/**
	 * 加载数据后，焦点置于底部 -->上一次加载处
	 */
	private void scrollToBottom() {
		autoListView.post(new Runnable() {
			@Override
			public void run() {
				if (!firstLoad) {
					autoListView.setSelectionFromTop(last, top);
				}
			}
		});
	}

	private void initListener() {

		autoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Friends friend = (Friends) view.getTag();
				if (friend != null) {
					startActivity(new Intent(NearbyUserActivity.this,
							AddFriendsFromAllUserActivity.class).putExtra(
							"userInfo", friend));

				} else {
					Toast.makeText(NearbyUserActivity.this, "获取用户信息出错",
							Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	/**
	 * 显示地理位置经度和纬度信息
	 * 
	 * @param location
	 */
	private void getLocation() {

		// tv_showLocaltion.setText(locationStr);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void loadData(final int what) {
		 
    	runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				getData(what);
			}
		});
    }
    
   

    @Override
    public void onLoad() {
    	page = page + 1;
		last = autoListView.getFirstVisiblePosition();
		View v = autoListView.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
        loadData(AutoListView.LOAD);
        
    }


    @Override
    public void onRefresh() {
    	page=1;
    	list.clear();
    	firstLoad = true;
        loadData(AutoListView.REFRESH);
    }

}
