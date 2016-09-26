package com.wemolian.app.wml.circle.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;











import com.alibaba.fastjson.JSONObject;
import com.baidu.platform.comapi.map.s;
import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.utils.DateCalculate;
import com.wemolian.app.utils.JsonUtil;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.wml.circle.CircleFriendsShowImagesActivity;
import com.wemolian.app.wml.entity.Comment;
import com.wemolian.app.wml.entity.Data;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 朋友圈适配器
 * 
 * @author zhangyun
 *
 */
public class CircleFriendsAdapter extends BaseAdapter {

	//数据是否加载结束，默认为false
	public static boolean loadEnd = false;
	public static Map<String, Object> imageDataMap = new  HashMap<String, Object>();
	ImageButton ib_btnShare = null;
	private LoadUserAvatar avatarLoader;
	List<JSONObject> list;
	Context context;
	int commentTag = 0;
	public String imagesPath;
	public List<String> imagesName;
	ImageView iv_userIcon;
	TextView tv_tag;
	TextView tv_userCName;
	public GridView gv_circle_images;
	GridAdapter gridAdapter;
	TextView tv_time;
	String userCName;
	//评论按钮
	ImageView comment;
	ListView lv_commentList;
	ImageView userIcon;

	//评论适配器
	CircleFriendCommentAdapter circleFriendCommentAdapter;
	
	//图片列表  用于传到下一个activity
	String imagesNameStr;
	@SuppressLint("SdCardPath")
	public CircleFriendsAdapter(List<JSONObject> list, Context context) {
		loadEnd = false;
		this.context = context;
		this.list = list;
		avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.circle_friends_list_adapter, null);
		}
		// 初始化控件
		initView(convertView);
		Data data = JsonUtil.jsonObjectToData(list.get(position));
		imageDataMap.put(String.valueOf(data.getId()), data);
		imagesPath = data.getImagePath();
		if (data.getImagesName() != null) {
			imagesName = SplitString.splitToString(data.getImagesName());
			imagesNameStr = data.getImagesName();
		}
		// 加载图片
		gridAdapter = new GridAdapter(context, imagesName,data.getId());
		//加载评论列表
		if(data.getCommentList() != null && data.getCommentList().size() > 0){
			circleFriendCommentAdapter = new CircleFriendCommentAdapter(context, data.getCommentList());
			lv_commentList.setAdapter(circleFriendCommentAdapter);
			ViewGroup.LayoutParams params = lv_commentList.getLayoutParams();
			// 设置高度
//			params.height = gv_circle_images.getHeight() * data.getCommentList().size(); 
			circleFriendCommentAdapter.notifyDataSetChanged();
			
		}
		String externalUse = data.getExternalUse();
		String headImg;
		String useEst = LocalUserInfo.getInstance(context).getUserInfo(
				LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		// 列表数据为登录的用户的数据时
		if (externalUse != null && externalUse.equals(useEst)) {
			userCName = LocalUserInfo.getInstance(context).getUserInfo(
					LocalDBKey.USER_COLUMN_NAME_USERCNAME);
			headImg = LocalUserInfo.getInstance(context).getUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
			showUserAvatar(userIcon, headImg);
			// 列表数据为好友的数据时
		} else {
			Contacts con_temp = new Contacts();
			con_temp.setExternalUse(externalUse);
			Contacts con = WeMoLianApplication.getInstance().getContact(
					con_temp);
			if (con != null) {
				userCName = con.getUserLabel(con);
				headImg = con.getImgName();
				showUserAvatar(userIcon, headImg);
			}
		}
		tv_userCName.setText(userCName);
//		tv_userCName.setText("id:" + data.getId());
		tv_tag.setText(data.getImagesText());
		String date = data.getAddDate();				
		tv_time.setText(DateCalculate.calculate(date).getTime());
		if(imagesName != null && imagesName.size() > 0){
			gv_circle_images.setAdapter(gridAdapter);
			if(imagesName.size() >= 4 && imagesName.size() <= 6){
				ViewGroup.LayoutParams params = gv_circle_images.getLayoutParams();  
			       // 设置高度  
			       params.height = gv_circle_images.getHeight() * 2; 
			}else if(imagesName.size() >= 6 && imagesName.size() <= 9){
				ViewGroup.LayoutParams params = gv_circle_images.getLayoutParams(); 
				int hei = gv_circle_images.getHeight();
				// 设置高度  
				params.height = gv_circle_images.getHeight() * 3; 
			}
		}else{
			gv_circle_images.setVisibility(View.GONE);
		}
		
		
		gridAdapter.notifyDataSetChanged();
		//所有的list都加载完成
		if((position + 1) == list.size()){
			loadEnd = true;
		}
		//评论按钮
		comment.setTag(data);
		return convertView;
	}


	/**
	 * 初始化控件
	 */
	private void initView(View convertView) {
		imagesName = new ArrayList<String>();
		userIcon = (ImageView)
		 convertView.findViewById(R.id.circle_iv_usericon);
		tv_userCName = (TextView) convertView
				.findViewById(R.id.circle_tv_friends_usercname);
		tv_tag = (TextView) convertView
				.findViewById(R.id.circle_tv_circle_friends_tag);
		// btnShare = (ImageButton)
		// convertView.findViewById(R.id.circle_ib_friends_share);

		tv_time = (TextView) convertView.findViewById(R.id.circle_tv_time);
		comment = (ImageView) convertView
				.findViewById(R.id.circle_iv_comment_icon);
		
		gv_circle_images = (GridView) convertView.findViewById(R.id.gv_circle_images);
		lv_commentList = (ListView) convertView.findViewById(R.id.lv_circle_friend_comment);
		
	}


	/**
	 * 圈子图片加载类
	 * @author Administrator
	 *
	 */
	public class GridAdapter extends BaseAdapter {

		private List<String> imagesName;
		private int id;
		public GridAdapter(Context c, List<String> imagesName,int id) {
			this.imagesName = imagesName;
			this.id = id;
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
			showUserAvatar(viewHolder.imageView,
					imagesPath + imagesName.get(position));
			viewHolder.toggleButton.setVisibility(View.GONE);
			viewHolder.choosetoggle.setVisibility(View.GONE);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, CircleFriendsShowImagesActivity.class);
					intent.putExtra("id", id);
					intent.putExtra("position", where);
					context.startActivity(intent);
				}
			});
			
			return convertView;
		}

	}

	// 加载图片
	private void showUserAvatar(ImageView iamgeView, final String avatar) {
		if (avatar == null || avatar.equals(""))
			return;
		final String url_avatar = Constant.URL_Avatar + avatar;
		iamgeView.setTag(url_avatar);
		if (url_avatar != null && !url_avatar.equals("")) {
			Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
					new ImageDownloadedCallBack() {

						@Override
						public void onImageDownloaded(ImageView imageView,
								Bitmap bitmap) {
							if (imageView.getTag() == url_avatar) {
								imageView.setImageBitmap(bitmap);

							}
						}

					});
			if (bitmap != null){
				iamgeView.setImageBitmap(bitmap);
			}

		}
	}
	
	

}
