package com.wemolian.app.wml.circle.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemolian.app.R;

/**
 * 微商圈适配器
 * @author 张云
 *
 */
public class CircleWeShopAdapter extends BaseAdapter {
	   	List list;
	    Context context;
	    int commentTag = 0;

	    @SuppressLint("SdCardPath")
	    public CircleWeShopAdapter(List list, Context context) {
	        this.context = context;
	        this.list = list;
//	        this.circleFriendsCommentGroup = circleFriendsCommentGroup; 
	        //        avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
	    }


		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//	        if(convertView==null){  
//	            convertView=LayoutInflater.from(context).inflate(R.layout.circle_mianmian_list_adapter, null);  
//	        }
			ImageView userIcon = null;
			TextView tag = null;
			TextView userCName = null;
			ImageButton btnShare = null;
			ImageView mianmianImage1 = null;
			ImageView mianmianImage2 = null;
			ImageView mianmianImage3 = null;
			ImageView mianmianImage4 = null;
			ImageView mianmianImage5 = null;
			ImageView mianmianImage6 = null;
			ImageView mianmianImage7 = null;
			ImageView mianmianImage8 = null;
			ImageView mianmianImage9 = null;
			TextView time = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(R.layout.circle_weshop_list_adapter,
						null);
			}
//			userIcon       = (ImageView) convertView.findViewById(R.id.circle_iv_usericon);
			userCName      = (TextView) convertView.findViewById(R.id.circle_tv_weshop_usercname);
			tag            = (TextView) convertView.findViewById(R.id.circle_tv_circle_weshop_tag);
//			btnShare       = (ImageButton) convertView.findViewById(R.id.circle_ib_friends_share);
//			mianmianImage1 = (ImageView) convertView.findViewById(R.id.circle_friends_image1);
//			mianmianImage2 = (ImageView) convertView.findViewById(R.id.circle_friends_image2);
//			mianmianImage3 = (ImageView) convertView.findViewById(R.id.circle_friends_image3);
//			mianmianImage4 = (ImageView) convertView.findViewById(R.id.circle_friends_image4);
//			mianmianImage5 = (ImageView) convertView.findViewById(R.id.circle_friends_image5);
//			mianmianImage6 = (ImageView) convertView.findViewById(R.id.circle_friends_image6);
//			mianmianImage7 = (ImageView) convertView.findViewById(R.id.circle_friends_image7);
//			mianmianImage8 = (ImageView) convertView.findViewById(R.id.circle_friends_image8);
//			mianmianImage9 = (ImageView) convertView.findViewById(R.id.circle_friends_image9);
			time           = (TextView) convertView.findViewById(R.id.circle_tv_time);
			ImageView comment = (ImageView) convertView.findViewById(R.id.circle_iv_comment_icon);
			userCName.setText(list.get(position).toString());
			String str = "<font color='blue'>123</font>大碗大碗大碗等等等等等等达维多娃吊袜带达维多娃打完达维多娃大碗大碗吊袜带哇达维多娃大碗大碗的阿文的哇啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊";
			tag.setText(Html.fromHtml(str));
			SimpleDateFormat simpleDate = new SimpleDateFormat("yy年MM月dd日    HH:mm:ss");
			Date    now    =   new    Date(System.currentTimeMillis());//获取当前时间       
			String    date    =    simpleDate.format(now);   
			time.setText(date.toString());
			if(position == 1){
//				mianmianImage9.setVisibility(View.GONE);
//				mianmianImage8.setVisibility(View.GONE);
//				mianmianImage7.setVisibility(View.GONE);
				
			}
			
			
			
			
			
			
			return convertView;
		}
		
}
