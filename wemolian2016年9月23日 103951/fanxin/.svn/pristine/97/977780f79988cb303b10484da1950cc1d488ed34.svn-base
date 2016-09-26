package com.wemolian.app.wml.circle;


import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.wml.circle.adapter.CircleMianMianAdapter;
import com.wemolian.app.wml.others.AutoListView;
import com.wemolian.app.wml.others.AutoListView.OnLoadListener;
import com.wemolian.app.wml.others.AutoListView.OnRefreshListener;
import com.wemolian.app.wml.others.HorizontalListView;

/**
 * 免免圈的活动窗口
 * 动态加载免免圈中的数据
 * 将加载的数据传送到页面中显示
 * @author 张云
 *
 */
public class CircleMianMianActivity extends BaseActivity implements OnRefreshListener,OnLoadListener {
	
	
	private static final int LOADBYKEY = 2;
	AutoListView autoListView;
	CircleMianMianAdapter circleMianMianAdapter;
	private ListView listView;
	private ImageView iv_AddCircleMianmian;
	private ImageButton ib_circleMianmianLarge;
	private ImageButton ib_circleMianmianLargePressed;
	private LinearLayout ll_circleMianmianClassNormal;
	private LinearLayout ll_circleMianmianClassPressed;
	private HorizontalListView   hlv_circleMianmianClass;
	private GridAdapter gridAdapter;
	/**
	 *被选中的条目 
	 */
	private String selectItem = null;
	/**
	 * 是否是点击的监听事件
	 */
	private boolean isOnClick;
	
	List list= new ArrayList();
	/**
	 * 分类列表
	 */
	List<String> classList = new ArrayList<String>();
	String time ="0";
	int page =  0;
	
	/**
	 * 查询列表的名称
	 */
	private String selectName = null;
	
	
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
            case LOADBYKEY:
            	autoListView.onLoadComplete();
            	list.addAll(result);
            	break;
            }
             
            autoListView.setResultSize(result.size());
            WeMoLianApplication.last_time=time;
            WeMoLianApplication.page=page; 
            circleMianMianAdapter.notifyDataSetChanged();
            
        };
    };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_mianmian_head);
		setStatus(findViewById(R.id.title));
		Toast.makeText(CircleMianMianActivity.this, "进入免免圈", Toast.LENGTH_LONG).show();
		autoListView = (AutoListView) findViewById(R.id.circle_mianmian_listview);
		// 默认软键盘不弹出 
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        list = new ArrayList();
        for(int i = 0;i < 10; i++){
        	list.add("测试"+i);
        }
        circleMianMianAdapter = new CircleMianMianAdapter(list, this);
//        listView.setAdapter(circleMianMianAdapter);
        autoListView.setAdapter(circleMianMianAdapter);
        autoListView.setOnRefreshListener(this);
        autoListView.setOnLoadListener(this);
        iv_AddCircleMianmian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CircleMianMianActivity.this, AddCircleMianMianActivity.class);
				intent.putExtra("isFirst", true);
				startActivity(intent);
			}
		});
        ib_circleMianmianLarge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hlv_circleMianmianClass.setVisibility(View.VISIBLE);
				ib_circleMianmianLarge.setVisibility(View.GONE);
				ll_circleMianmianClassNormal.setVisibility(View.GONE);
				ib_circleMianmianLargePressed.setVisibility(View.VISIBLE);
				ll_circleMianmianClassPressed.setVisibility(View.VISIBLE);
			}
		});
        ib_circleMianmianLargePressed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hlv_circleMianmianClass.setVisibility(View.GONE);
				ib_circleMianmianLarge.setVisibility(View.VISIBLE);
				ll_circleMianmianClassNormal.setVisibility(View.VISIBLE);
				ib_circleMianmianLargePressed.setVisibility(View.GONE);
				ll_circleMianmianClassPressed.setVisibility(View.GONE);
			}
		});
        hlv_circleMianmianClass.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_className  = (TextView) view.findViewById(R.id.tv_circle_mianmian_list);
				selectItem = tv_className.getText().toString();
				isOnClick = true;
				selectName = selectItem;
				list.clear();
				for (int i = 0; i < 10; i++) {
					list.add(selectItem + i);
				}
				gridAdapter.notifyDataSetChanged();
				loadData(LOADBYKEY);
			}
		});
	}

	
	
	
	
	private void initView() {
		listView =(ListView) findViewById(R.id.circle_mianmian_listview);	
		iv_AddCircleMianmian = (ImageView) findViewById(R.id.iv_addcircle_mianmian);
		ib_circleMianmianLarge = (ImageButton) findViewById(R.id.ib_circle_mianmian_large);
		ib_circleMianmianLargePressed = (ImageButton) findViewById(R.id.ib_circle_mianmian_large_pressed);
		ll_circleMianmianClassNormal = (LinearLayout) findViewById(R.id.ll_circle_mianmian_class_normal);
		ll_circleMianmianClassPressed = (LinearLayout) findViewById(R.id.ll_circle_mianmian_class_pressed);
		hlv_circleMianmianClass = (HorizontalListView) findViewById(R.id.hlv_circle_mianmian_class);
		classList.add(" 为您推荐");
		classList.add("美妆");
		classList.add("食品");
		classList.add("服装");
		classList.add("手机数码");
		classList.add("鞋靴");
		classList.add("珠宝配饰");
		classList.add("母婴");
		classList.add("户外运动");
		classList.add("家居");
		classList.add("其他");
		gridAdapter = new GridAdapter(CircleMianMianActivity.this, classList);
		hlv_circleMianmianClass.setAdapter(gridAdapter);
		gridAdapter.notifyDataSetChanged();
		
	}


	int times = 0;
    private void loadData(final int what) {
    	 
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = what;
//                list.clear();
                if(selectName == null){
                	selectName = "初始查询";
                }
            	for (int i = 0; i < 3; i++) {
            		times = times++;
            		list.add(selectName+times);
				}
            	msg.obj = list;
            	handler.sendMessage(msg);
            }
        }).start();
    }

	@Override
	public void onLoad() {
//		Toast.makeText(CircleMianMianActivity.this, "加载中……", Toast.LENGTH_LONG).show();
		loadData(AutoListView.LOAD);
		page=page+1;
//		circleMianMianAdapter.notifyDataSetChanged();
	}
	
	
	@Override
	public void onRefresh() {
//		circleMianMianAdapter.notifyDataSetChanged();
		Toast.makeText(CircleMianMianActivity.this, "刷新数据", Toast.LENGTH_SHORT).show();
        loadData(AutoListView.REFRESH);
        page=0;
        Toast.makeText(CircleMianMianActivity.this, "刷新数据成功", Toast.LENGTH_SHORT).show();
	}
	
	
	
	//隐藏软键盘
	public boolean onTouchEvent(MotionEvent event) {
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    // TODO Auto-generated method stub
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        System.out.println("down");
	        if (CircleMianMianActivity.this.getCurrentFocus() != null) {
	            if (CircleMianMianActivity.this.getCurrentFocus().getWindowToken() != null) {
	                imm.hideSoftInputFromWindow(CircleMianMianActivity.this.getCurrentFocus().getWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	            }
	        }
	    }
	    return super.onTouchEvent(event);
	}

	
	
	class GridAdapter extends BaseAdapter{

		Context context;
		List<String> list;
		public GridAdapter(Context context,List<String> list) {
			this.context = context;
			this.list = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_circle_mianmian_list,null);
//			}
			final TextView cirCleMianmianText = (TextView) convertView.findViewById(R.id.tv_circle_mianmian_list);
			cirCleMianmianText.setText(list.get(position));
			System.out.println("1--->"+selectItem);
			System.out.println("2--->"+position);
			
			if(selectItem != null &&  selectItem.equals(cirCleMianmianText.getText().toString()) && isOnClick){
				cirCleMianmianText.setTextColor(0XFFF4A0BA);
				cirCleMianmianText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
				cirCleMianmianText.getPaint().setAntiAlias(true);//抗锯齿
//				Toast.makeText(getApplicationContext(), list.get(position), Toast.LENGTH_SHORT).show();
			}
			return convertView;
		}
		
		
	}
}
