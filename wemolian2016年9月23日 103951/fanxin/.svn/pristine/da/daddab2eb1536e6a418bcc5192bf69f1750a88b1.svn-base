package com.wemolian.app.wml.profile;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.wml.circle.adapter.CircleWeShopAdapter;
import com.wemolian.app.wml.others.AutoListView;
import com.wemolian.app.wml.others.AutoListView.OnLoadListener;
import com.wemolian.app.wml.others.AutoListView.OnRefreshListener;
import com.wemolian.app.wml.profile.adapter.ProfileExpressionAdapter;

/**
 * 个人信息中的表情活动类
 * @author 张云
 *
 */
public class ProfileExpressionActivity extends BaseActivity  implements OnRefreshListener,OnLoadListener {
	ProfileExpressionAdapter profileExpressionAdapter;
	List list = new ArrayList();
	AutoListView autoListView;
	private int page;
	String time ="0";
	
	
	
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
    	
        public void handleMessage(Message msg) {
        	@SuppressWarnings("unchecked")
        	List result = (List)msg.obj;
            switch (msg.what) {
            case AutoListView.REFRESH:
                autoListView.onRefreshComplete();
                list.clear();
                list.add("result");
                break;
            case AutoListView.LOAD:
                autoListView.onLoadComplete();
                list.addAll(result);
                break;
            }
             
            autoListView.setResultSize(result.size());
            WeMoLianApplication.last_time=time;
            WeMoLianApplication.page=page; 
            profileExpressionAdapter.notifyDataSetChanged();
        };
    };
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.profile_expression_activity);
		setStatus(findViewById(R.id.title));
		initView();
		
		for(int i=0;i<3;i++){
			list.add("表情"+1);
		}
	  profileExpressionAdapter = new ProfileExpressionAdapter(list, this);
      autoListView.setAdapter(profileExpressionAdapter);
      autoListView.setOnRefreshListener(this);
      autoListView.setOnLoadListener(this);
		
		
	}
	private void initView() {
		/**
		 * 初始化列表
		 */
		autoListView = (AutoListView) findViewById(R.id.profile_expression_listview);
	}
	
	
	int times = 0;
    private void loadData(final int what) {
    	 
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = what;
                list.clear();
            	for (int i = 0; i < 3; i++) {
            		times = times++;
            		list.add("添加"+times);
				}
            	msg.obj = list;
            	handler.sendMessage(msg);
            }
        }).start();
    }
	
	@Override
	public void onLoad() {
		loadData(AutoListView.LOAD);
		page=page+1;
	}
	@Override
	public void onRefresh() {
		Toast.makeText(ProfileExpressionActivity.this, "刷新数据", Toast.LENGTH_SHORT).show();
        loadData(AutoListView.REFRESH);
        page=0;
        Toast.makeText(ProfileExpressionActivity.this, "刷新数据成功", Toast.LENGTH_SHORT).show();		
	}
}
