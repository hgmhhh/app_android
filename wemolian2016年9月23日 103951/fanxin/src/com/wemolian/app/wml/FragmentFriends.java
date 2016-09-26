package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.widget.Sidebar;
import com.wemolian.app.wml.friends.AllUserActivity;
import com.wemolian.app.wml.friends.NearbyUserActivity;
import com.wemolian.app.wml.others.ContactAdapter;

/**
 * 联系人列表页
 * 
 */
@SuppressLint("InflateParams")
public class FragmentFriends extends Fragment {
    private ContactAdapter adapter;
    private List<Contacts> contactList;
    private ListView listView;
    private boolean hidden;
    private Sidebar sidebar;
 
    private List<String> blackList;
    private TextView tv_unread;
    private TextView tv_total;
    private LayoutInflater infalter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_contactlist, container, false);
        
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
        if (savedInstanceState != null
                && savedInstanceState.getBoolean("isConflict", false))
            return;
        
        listView = (ListView) getView().findViewById(R.id.list);
       
        // 黑名单列表
        blackList = EMContactManager.getInstance().getBlackListUsernames();
        contactList = new ArrayList<Contacts>();
        // 获取设置contactlist
        getContactList();
        infalter=LayoutInflater.from(getActivity());
        View headView = infalter.inflate(R.layout.item_contact_list_header,
                null);
        listView.addHeaderView(headView);
        View footerView = infalter.inflate(R.layout.item_contact_list_footer,
                null);
        listView.addFooterView(footerView);
        sidebar = (Sidebar) getView().findViewById(R.id.sidebar);
        sidebar.setListView(listView);
        tv_unread = (TextView) headView.findViewById(R.id.tv_unread);
        if(((MainActivity)getActivity()).unreadAddressLable.getVisibility()==View.VISIBLE){
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(((MainActivity)getActivity()).unreadAddressLable.getText());
            
        }else{
            tv_unread.setVisibility(View.GONE);
        }
        
        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        // 设置adapter
        adapter = new ContactAdapter(getActivity(), R.layout.item_contact_list,
                contactList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                if(position!=0&&position!=contactList.size()+1){
                    
                	/**
                	 * Contacts [type=friend, unreadMsgCount=0, header=#, 
                	 * externalUse=dd985b2a0d46289f0f2f5b955e3763e0, 
                	 * hxid=f49bb95edc33f815ff7efc6541fda529, userCName=111, 
                	 * newsNotDis=0, userRemark=null, blackList=0, developmentMe=0, 
                	 * label=null, imgName=file\img\headImg\fd920dd56ec0a54b9d4501cec6ebefea.png, 
                	 * chatTop=null]
                	 */
                	Contacts user=contactList.get(position-1);
                    String hxUserId = user.getHxid();
                    String usernick = "";
                    String label = user.getLabel();
                    String userRemark = user.getUserRemark();
                    String userCName = user.getUserCName();
                    if(label != null){
                    	usernick = label;
                    }else if(userRemark != null){
                    	usernick = userRemark;
                    }else if(userCName != null){
                    	usernick = userCName;
                    }else{
                    	usernick = user.getNick();
                    }
                    startActivity(new Intent(getActivity(), UserInfoActivity.class)
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_HXID, hxUserId )
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_USERCNAME, user.getUserCName())
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME, user.getImgName())
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_SEX, user.getSex())
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_AUTOGRAPH, user.getAutograph())
                    .putExtra(LocalDBKey.CONTACTS_COLUMN_NAME_LABEL, usernick));
//                    .putExtra("hxid", username ).putExtra("nick", user.getNick() ).putExtra("avatar", user.getAvatar() ).putExtra("sex", user.getSex() ));
                }
               

            }
        });
       
        tv_total.setText(String.valueOf(contactList.size())+"位联系人");
      
        RelativeLayout re_newfriends=(RelativeLayout) headView.findViewById(R.id.re_newfriends);
        RelativeLayout re_chatroom=(RelativeLayout) headView.findViewById(R.id.re_chatroom);
        /**
         * 新的朋友
         */
        re_newfriends.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NewFriendsActivity.class)); 
                
            }
            
        });
        
        /**
         * 世界的朋友
         */
        RelativeLayout tv_online = (RelativeLayout) headView.findViewById(R.id.id_contact_world_friends_image_btn);
        tv_online.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//            	Toast.makeText(getActivity(), "点击了世界的朋友", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(),
                        AllUserActivity.class));
            }

        });
        
        /**
         * 我的好友
         */
        RelativeLayout tv_myfriends = (RelativeLayout) headView.findViewById(R.id.id_contact_my_friends_image_btn);
        tv_myfriends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	Toast.makeText(getActivity(), "我的好友功能正在开发中……", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getActivity(),
//                        LasterLoginUserActivity.class));
            }

        });
        
        /**
         * 群聊
         */
        re_chatroom.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChatRoomActivity.class).putExtra("defaultGet", true)); 
            }
            
        });
        
        /**
         * 附近的人
         */
        RelativeLayout rl_nearbyUser = (RelativeLayout) headView.findViewById(R.id.rl_nearby_user);
        rl_nearbyUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), NearbyUserActivity.class));
			}

		});
        
    }
    
 
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    
    
    // 刷新ui
    public void refresh() {
        try {
            // 可能会在子线程中调到这方法
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getContactList();
                    adapter.notifyDataSetChanged();
                    tv_total.setText(String.valueOf(contactList.size())+"位联系人");
                    if(((MainActivity)getActivity()).unreadAddressLable.getVisibility()==View.VISIBLE){
                        tv_unread.setVisibility(View.VISIBLE);
                        tv_unread.setText(((MainActivity)getActivity()).unreadAddressLable.getText());
                        
                    }else{
                        tv_unread.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
        contactList.clear();
        WeMoLianApplication s = WeMoLianApplication.getInstance();
        	
        
        // 获取本地好友列表
        Map<String, Contacts> user = WeMoLianApplication.getInstance().getContactList();
        Iterator<Entry<String, Contacts>> iterator = user.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Contacts> entry = iterator.next();
            if (!((Constant.NEW_FRIENDS_USERNAME).equals(entry.getKey()))
                    && !(Constant.GROUP_USERNAME).equals(entry.getKey())
                    && !blackList.contains(entry.getKey()))
                contactList.add(entry.getValue());
        }
  
 
        // 对list进行排序
        Collections.sort(contactList, new PinyinComparator() {
        });

 
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


    
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }

    }
    
    
}
