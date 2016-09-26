package com.wemolian.app.wml.friends.adapter;

import java.util.List;










import com.wemolian.app.R;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.entity.Point;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyUserAdapter extends BaseAdapter {

	private Context context;
	private List<Friends> list;
	private LoadUserAvatar avatarLoader;
	
	public NearbyUserAdapter(Context context,List<Friends> list){
		this.context = context;
		this.list = list;
		avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
	}
	
	@Override
	public int getCount() {
		return list == null? 0 : list.size();
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
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_nearby_user, null);
		}
		ImageView iv_head_img = (ImageView) convertView.findViewById(R.id.iv_head_img);
		TextView tv_user_cname = (TextView) convertView.findViewById(R.id.tv_user_cname);
		TextView tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
		Friends friend = list.get(position);
		if(friend != null ){
			if(friend.getHeadImg() != null){
				showUserAvatar(iv_head_img, friend.getHeadImg());
			}
			tv_user_cname.setText(friend.getUserCName());
			tv_distance.setText(friend.getDistance() + "米");
		}
		convertView.setTag(friend);
		return convertView;
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
