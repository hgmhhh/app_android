package com.wemolian.app.wml.circle;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.wml.circle.adapter.CircleFriendsAdapter;
import com.wemolian.app.wml.circle.adapter.CircleWeShopAdapter;
import com.wemolian.app.wml.others.AutoListView;
import com.wemolian.app.wml.others.AutoListView.OnLoadListener;
import com.wemolian.app.wml.others.AutoListView.OnRefreshListener;

/**
 * 微商圈
 * @author 张云
 *
 */
public class CircleWeShopActivity extends BaseActivity implements OnRefreshListener,OnLoadListener {

	AutoListView autoListView;
	CircleWeShopAdapter circleWeShopAdapter;
	private LinearLayout circleWeShopCommentGroup;
	private TextView text;
	private ImageView circleIvCommentGroupIcon;
	
	List list= new ArrayList();
	String time ="0";
	int page =  0;
	
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
            circleWeShopAdapter.notifyDataSetChanged();
        };
    };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_weshop_head);
		setStatus(findViewById(R.id.title));
		// 默认软键盘不弹出 
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                  
        initView();
        list = new ArrayList();
        for(int i = 0;i < 4; i++){
        	list.add("测试"+i);
        }
        circleWeShopAdapter = new CircleWeShopAdapter(list, this);
//        listView.setAdapter(circleMianMianAdapter);
        autoListView.setAdapter(circleWeShopAdapter);
        autoListView.setOnRefreshListener(this);
        autoListView.setOnLoadListener(this);
        
        
	}

	



	private void initView() {
		autoListView = (AutoListView) findViewById(R.id.circle_weshop_listview);
		circleWeShopCommentGroup = (LinearLayout) findViewById(R.id.circle_weshop_comment_group);
		//默认隐藏布局
		circleWeShopCommentGroup.setVisibility(View.GONE);
		
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
//		Toast.makeText(CircleWeShopActivity.this, "加载中……", Toast.LENGTH_LONG).show();
		loadData(AutoListView.LOAD);
		page=page+1;
//		circleMianMianAdapter.notifyDataSetChanged();
	}
	
	
	@Override
	public void onRefresh() {
//		circleWeShopAdapter.notifyDataSetChanged();
		Toast.makeText(CircleWeShopActivity.this, "刷新数据", Toast.LENGTH_SHORT).show();
        loadData(AutoListView.REFRESH);
        page=0;
        Toast.makeText(CircleWeShopActivity.this, "刷新数据成功", Toast.LENGTH_SHORT).show();
	}
	
	
	
	//隐藏软键盘
	public boolean onTouchEvent(MotionEvent event) {
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    // TODO Auto-generated method stub
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        System.out.println("down");
	        if (CircleWeShopActivity.this.getCurrentFocus() != null) {
	            if (CircleWeShopActivity.this.getCurrentFocus().getWindowToken() != null) {
	                imm.hideSoftInputFromWindow(CircleWeShopActivity.this.getCurrentFocus().getWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	            }
	        }
	    }
	    return super.onTouchEvent(event);
	}
	
	/**
	 * 显示评论框
	 */
	int commentGroupTag = 0;
	public void showWeshopCommentGroup(View v) {
		circleWeShopCommentGroup = (LinearLayout) findViewById(R.id.circle_weshop_comment_group);
		Toast.makeText(CircleWeShopActivity.this, "点击了评论按钮" + commentGroupTag, Toast.LENGTH_LONG).show();
		if( commentGroupTag == 0){
			circleWeShopCommentGroup.setVisibility(View.VISIBLE);
			commentGroupTag =1;
		}else if(commentGroupTag == 1){
			circleWeShopCommentGroup.setVisibility(View.GONE);
			commentGroupTag =0;
		}
		
	}
	
	
	/**
	 * 评论的 发送按钮
	 * @param v
	 */
	public void sendComment(View v){
		int i = R.id.circle_weshop_btn_send_comment;
		int j =v.getId();
		text = (TextView) findViewById(R.id.circle_weshop_et_sendmessage);
		Toast.makeText(CircleWeShopActivity.this, "发送评论数据", Toast.LENGTH_SHORT).show();
		Toast.makeText(CircleWeShopActivity.this, text.getText().toString()+","+j +"," + i, Toast.LENGTH_LONG).show();
		
	}
}
