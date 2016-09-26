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
 * 收藏项目的详细信息中，有图片时的适配器
 * @author zhangyun
 *
 */
public class ProfileCollectionDetailAdapter extends BaseAdapter {

	Context context;
	List<String> list;
	
	
	public ProfileCollectionDetailAdapter(Context context,List<String> list){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_collection_detail_images,null);
		}
		
		
		return convertView;
	}

}
