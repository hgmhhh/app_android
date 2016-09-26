package com.wemolian.app.wml.circle.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wemolian.app.R;
import com.wemolian.app.wml.circle.CircleMianMianDetailActivity;
import com.wemolian.app.wml.others.CircleGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 免免圈适配器
 * @author 张云
 *
 */
public class CircleMianMianAdapter extends BaseAdapter {

    List list;
    Context context;
//    private LoadUserAvatar avatarLoader;

    @SuppressLint("SdCardPath")
    public CircleMianMianAdapter(List list, Context context) {
        this.context = context;
        this.list = list;
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView==null){  
//            convertView=LayoutInflater.from(context).inflate(R.layout.circle_mianmian_list_adapter, null);  
//        }
		ImageView userIcon = null;
		TextView userCName = null;
		TextView tag = null;
		ImageButton btnShare = null;
		TextView content = null;
		GridView gv_circle_images = null;
		TextView time = null;
		TextView receiveTag = null;
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.circle_mianmian_list_adapter,
					null);
		}
		userIcon       = (ImageView) convertView.findViewById(R.id.circle_iv_usericon);
		userCName      = (TextView) convertView.findViewById(R.id.circle_tv_mianmian_usercname);
		tag            = (TextView) convertView.findViewById(R.id.circle_tv_circle_mianmian_tag);
		btnShare       = (ImageButton) convertView.findViewById(R.id.circle_ib_mianmian_share);
		time           = (TextView) convertView.findViewById(R.id.circle_tv_time);
		receiveTag     = (TextView) convertView.findViewById(R.id.circle_tv_receive_tag);
		content = (TextView) convertView.findViewById(R.id.circle_mianmian_content);
		gv_circle_images = (CircleGridView) convertView.findViewById(R.id.gv_circle_images);
		
		List<String> images = new ArrayList<String>();
		images.add("1");
		images.add("1");
		images.add("1");
		images.add("1");
		images.add("1");
		images.add("1");
		ImageAdapter adapter = new ImageAdapter(context, images);
		gv_circle_images.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		userCName.setText(list.get(position).toString());
		String str = "免费物品";
		tag.setText(str);
		SimpleDateFormat simpleDate = new SimpleDateFormat("yy年MM月dd日    HH:mm:ss");
		Date    now    =   new    Date(System.currentTimeMillis());//获取当前时间       
		String    date    =    simpleDate.format(now);   
		time.setText(date.toString());
		content.setText("测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！测试物品！");
		if(position == 1){
			receiveTag.setText("已领取");
			receiveTag.setBackground(convertView.getResources().getDrawable(R.drawable.circle_mianmian_receive_received_bg));
		}
		/**
		 * 点击相对应的item，跳转到相对因item的详细界面以及领取界面
		 */
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CircleMianMianDetailActivity.class);
				
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public class ImageAdapter extends BaseAdapter {

		private List<String> imagesName;
		public ImageAdapter(Context c, List<String> imagesName) {
			this.imagesName = imagesName;
		}

		@Override
		public int getCount() {
			return imagesName.size();
		}

		@Override
		public Object getItem(int position) {
//			return imagesName.get(position);
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 * 存放列表项控件句柄
		 */
		private class ViewHolder {
			public ImageView imageView;
			public ToggleButton toggleButton;
			public Button choosetoggle;
			public TextView textView;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			final int where =position;
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.plugin_camera_select_imageview, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.image_view);
			viewHolder.toggleButton = (ToggleButton) convertView
					.findViewById(R.id.toggle_button);
			viewHolder.choosetoggle = (Button) convertView
					.findViewById(R.id.choosedbt);
			convertView.setTag(viewHolder);
//			viewHolder.imageView.setBackgroundResource(R.drawable.logo_wemolian);
			viewHolder.imageView.setBackground(convertView.getResources().getDrawable(R.drawable.logo_wemolian));
//			showUserAvatar(viewHolder.imageView,
//					imagesPath + imagesName.get(position));
			viewHolder.toggleButton.setVisibility(View.GONE);
			viewHolder.choosetoggle.setVisibility(View.GONE);
			
			
			return convertView;
		}

	}

}
