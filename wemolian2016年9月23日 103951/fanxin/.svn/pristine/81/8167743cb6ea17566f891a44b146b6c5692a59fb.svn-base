package com.wemolian.app.wml.profile.adapter;

import java.util.List;

import com.wemolian.app.R;
import com.wemolian.app.domain.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 收藏页面listview的适配器
 * @author zhangyun
 *
 */
public class ProfileCollectionAdapter extends BaseAdapter {

	List<Collection> list;
	Context context;
	int type = 0;
	View convertView;
	public ProfileCollectionAdapter(Context context,List<Collection> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list == null ? 0 :list.size();
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
		type = list.get(position).getType();
			if(type == 0){
				
				convertView = LayoutInflater.from(context).inflate(R.layout.item_collection_text, null);
				
				
			}
			else if(type == 1){
				
				convertView = LayoutInflater.from(context).inflate(R.layout.item_collection_text_img, null);
			}
			else if(type == 2){
				
				convertView = LayoutInflater.from(context).inflate(R.layout.item_collection_web_url, null);
			}
			
		return convertView;
	}


}
