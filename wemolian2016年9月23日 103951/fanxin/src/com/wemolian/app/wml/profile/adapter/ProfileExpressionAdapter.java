package com.wemolian.app.wml.profile.adapter;

import java.util.List;

import com.wemolian.app.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 个人表情的适配器
 * @author 张云
 *
 */
public class ProfileExpressionAdapter extends BaseAdapter {

   	List list;
    Context context;
	
    @SuppressLint("SdCardPath")
    public ProfileExpressionAdapter(List list, Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.profile_expression_adapter, null);
		}
		ImageView profileImageViewExpressionIcon = (ImageView) convertView.findViewById(R.id.profile_iv_expression_icon);
		TextView profileTextViewExpressionText = (TextView) convertView.findViewById(R.id.profile_tv_expression_text);
		Button profileButtonExpressionDownLoad = (Button) convertView.findViewById(R.id.profile_btn_expression_download);
		profileTextViewExpressionText.setText(list.get(position).toString());
		return convertView;
	}

}
