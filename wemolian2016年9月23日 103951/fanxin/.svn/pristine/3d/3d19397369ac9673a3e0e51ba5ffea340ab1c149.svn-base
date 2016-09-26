package com.wemolian.app.wml.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.wemolian.app.wml.profile.adapter.WhoCanSeeAdapter;

/**
 * 谁能看我的个人空间页面
 * @author zhangyun
 *
 */
public class WhoCanSeeActivity extends BaseActivity {

	LinearLayout ll_page1,ll_page2;
	LinearLayout ll_allCanScan,ll_wemolianFriendCanscan,ll_onlyMySelf,ll_customChoose,ll_scanBlackList;
	CheckBox cb_allCanScan,cb_wemolianFriendCanscan,cb_onlyMySelf;
	Button btn_ok,btn_esc;
	TextView tv_title;
	ListView listView;
	private List<Contacts> contactList = new ArrayList<Contacts>();
	int page = 1;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_who_can_scan);
		setStatus(findViewById(R.id.title));
		initView();
		initData();
		initListener();
	}
	

	private void initData() {
		ll_page1.setVisibility(View.VISIBLE);
		ll_page2.setVisibility(View.GONE);
	}


	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		ll_page1 = (LinearLayout) findViewById(R.id.ll_page1);
		ll_page2 = (LinearLayout) findViewById(R.id.ll_page2);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_esc = (Button) findViewById(R.id.btn_esc);
		listView = (ListView) findViewById(R.id.list);
		ll_allCanScan = (LinearLayout) findViewById(R.id.ll_all_can_scan);
		ll_wemolianFriendCanscan = (LinearLayout) findViewById(R.id.ll_wemolian_friend_canscan);
		ll_onlyMySelf = (LinearLayout) findViewById(R.id.ll_only_myself);
		ll_customChoose = (LinearLayout) findViewById(R.id.ll_custom_choose);
		ll_scanBlackList = (LinearLayout) findViewById(R.id.ll_scan_black_list);
		cb_allCanScan = (CheckBox) findViewById(R.id.cb_all_can_scan);
		cb_wemolianFriendCanscan = (CheckBox) findViewById(R.id.cb_wemolian_friend_canscan);
		cb_onlyMySelf = (CheckBox) findViewById(R.id.cb_only_myself);
		
	}

	private void initListener() {
		/**
		 * 所有人可见布局点击事件
		 */
		ll_allCanScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean tag = cb_allCanScan.isChecked();
				if(tag){
					cb_allCanScan.setChecked(false);
				}else{
					cb_allCanScan.setChecked(true);
				}
			}
		});
		/**
		 * 微默联好友可见布局点击事件
		 */
		ll_wemolianFriendCanscan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean tag = cb_wemolianFriendCanscan.isChecked();
				if(tag){
					cb_wemolianFriendCanscan.setChecked(false);
				}else{
					cb_wemolianFriendCanscan.setChecked(true);
				}
			}
		});
		/**
		 * 仅自己可见布局点击事件
		 */
		ll_onlyMySelf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean tag = cb_onlyMySelf.isChecked();
				if(tag){
					cb_onlyMySelf.setChecked(false);
				}else{
					cb_onlyMySelf.setChecked(true);
				}
			}
		});
		/**
		 * 用户自定义布局点击事件
		 */
		ll_customChoose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {//friends
				getContactList();
				List<Contacts> list = contactList;
				WhoCanSeeAdapter whoAdapter = new WhoCanSeeAdapter(WhoCanSeeActivity.this,btn_ok, contactList);
				listView.setAdapter(whoAdapter);
				whoAdapter.notifyDataSetChanged();
				page = 2;
				tv_title.setText("谁不能看我的空间");
				ll_page1.setVisibility(View.GONE);
				ll_page2.setVisibility(View.VISIBLE);
			}
		});
		/**
		 * 禁止访问黑名单布局点击事件
		 */
		ll_scanBlackList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getContactList();
				WhoCanSeeAdapter whoAdapter = new WhoCanSeeAdapter(WhoCanSeeActivity.this,btn_ok, contactList);
				listView.setAdapter(whoAdapter);
				whoAdapter.notifyDataSetChanged();
				page = 2;
				tv_title.setText("禁止黑名单");
				ll_page1.setVisibility(View.GONE);
				ll_page2.setVisibility(View.VISIBLE);
			}
		});
		
		/**
		 * 确定按钮。点击确定后，将提交数据
		 */
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<String> list = WhoCanSeeAdapter.blackUserList;
				String s = null;
				for (String str : list) {
					s = "," + str;
				}
				Toast.makeText(WhoCanSeeActivity.this, "选择的数据为："+s, Toast.LENGTH_LONG).show();
			}
		});
		/**
		 * 取消
		 */
		btn_esc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back(null);
			}
		});
	}
	
	
	
	 /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
        contactList.clear();
        // 获取本地好友列表
        Map<String, Contacts> user = WeMoLianApplication.getInstance().getContactList();
        Iterator<Entry<String, Contacts>> iterator = user.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Contacts> entry = iterator.next();
            if (!((Constant.NEW_FRIENDS_USERNAME).equals(entry.getKey()))
                    && !(Constant.GROUP_USERNAME).equals(entry.getKey())
//                    && !blackList.contains(entry.getKey())
                    )
                contactList.add(entry.getValue());
        }
  
 
        // 对list进行排序
        Collections.sort(contactList, new PinyinComparator() {
        });

 
    }
	@Override
	public void back(View view) {
		switch (page) {
		case 1:
			super.back(view);
			break;
		case 2:
			ll_page2.setVisibility(View.GONE);
			ll_page1.setVisibility(View.VISIBLE);
			page = 1;
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 重写按下返回键的作用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			back(null);
            return true;  
        } else  
            return super.onKeyDown(keyCode, event);  
	}
	
	@SuppressLint("DefaultLocale")
    public class PinyinComparator implements Comparator<Contacts> {

        @SuppressLint("DefaultLocale")
        @Override
        public int compare(Contacts o1, Contacts o2) {
            // TODO Auto-generated method stub
            String py1 = o1.getHeader();
            String py2 = o2.getHeader();
            // 判断是否为空""
            if (isEmpty(py1) && isEmpty(py2))
                return 0;
            if (isEmpty(py1))
                return -1;
            if (isEmpty(py2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
                str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
            } catch (Exception e) {
                System.out.println("某个str为\" \" 空");
            }
            return str1.compareTo(str2);
        }
        private boolean isEmpty(String str) {
            return "".equals(str.trim());
        }
        }
	
	
	
//	/**
//	 * 谁能看我的个人空间的适配器，
//	 * 此适配器可适用于禁止黑名单
//	 * @author zhangyun
//	 *
//	 */
//	public class WhoCanSeeAdapter extends BaseAdapter {
//
//		public List<String> blackUserList =new ArrayList<String>();
//		List<Contacts> list;
//		Context context;
//		private LoadUserAvatar avatarLoader;
//		
//		@SuppressLint("SdCardPath")
//		public WhoCanSeeAdapter(Context context,List<Contacts> list){
//			this.context = context;
//			this.list = list;
//			avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
//		}
//		
//		
//		@Override
//		public int getCount() {
//			return list == null ? 0: list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if(convertView == null){
//				convertView = LayoutInflater.from(context).inflate(R.layout.item_who_can_scan, null);
//			}
//			
//			final Contacts user = list.get(position);
//			ImageView iv_avatar = (ImageView) convertView
//	                .findViewById(R.id.iv_avatar);
//
//	        TextView nameTextview = (TextView) convertView
//	                .findViewById(R.id.tv_name);
//	        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
//	        View view_temp = (View) convertView.findViewById(R.id.view_temp);
//	        CheckBox cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
//	
//	        cb_choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					Toast.makeText(context, user.getExternalUse(), Toast.LENGTH_LONG).show();
//					if(isChecked){
//						blackUserList.add("1");
//					}else{
//						blackUserList.remove(0);
//					}
//				}
//			});
//	        
//	        
//	        // 设置nick，demo里不涉及到完整user，用username代替nick显示
//	        String header = user.getHeader();
//	        String headImg = user.getImgName();
//	        String usernick = "";
//	        String label = user.getLabel();
//	        String userRemark = user.getUserRemark();
//	        String userCName = user.getUserCName();
//	        if(label != null){
//	        	usernick = label;
//	        }else if(userRemark != null){
//	        	usernick = userRemark;
//	        }else if(userCName != null){
//	        	usernick = userCName;
//	        }else{
//	        	usernick = user.getNick();
//	        }
//	        
//	        
//
//	        nameTextview.setText(usernick);
//	        iv_avatar.setImageResource(R.drawable.default_useravatar);
//	        showUserAvatar(iv_avatar, headImg);
//			return convertView;
//		}
//
//		
//		private void showUserAvatar(ImageView iamgeView, String avatar) {
//	        if(avatar==null||avatar.equals("")) return;
//	        final String url_avatar = Constant.URL_Avatar + avatar;
//	        iamgeView.setTag(url_avatar);
//	        if (url_avatar != null && !url_avatar.equals("")) {
//	            Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
//	                    new ImageDownloadedCallBack() {
//
//	                        @Override
//	                        public void onImageDownloaded(ImageView imageView,
//	                                Bitmap bitmap) {
//	                            if (imageView.getTag() == url_avatar) {
//	                                imageView.setImageBitmap(bitmap);
//
//	                            }
//	                        }
//
//	                    });
//	            if (bitmap != null)
//	                iamgeView.setImageBitmap(bitmap);
//
//	        }
//	    }
//	}
}
