package com.wemolian.app.wml.profile.adapter;

import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemolian.app.R;

/**
 * 个人相册的适配器
 * @author 张云
 *
 */
public class ProfileAlbumAdapter extends BaseAdapter {
   	List list;
    Context context;
	
    @SuppressLint("SdCardPath")
    public ProfileAlbumAdapter(List list, Context context) {
        this.context = context;
        this.list = list;
//        this.circleFriendsCommentGroup = circleFriendsCommentGroup; 
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
		if(convertView == null ){
			convertView = LayoutInflater.from(context).inflate(R.layout.profile_album_activity_adapter, null);
		}
		ImageView profileImageViewAlbumIcon = (ImageView) convertView.findViewById(R.id.profile_iv_album_icon);
		TextView profileTextViewAlbumTime = (TextView) convertView.findViewById(R.id.profile_tv_album_time);
		TextView profileTextViewAlbumText = (TextView) convertView.findViewById(R.id.profile_tv_album_text);
		profileTextViewAlbumText.setText(list.get(position).toString());
		profileTextViewAlbumTime.setText("今天");
		return convertView;
	}

}
