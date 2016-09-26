package com.wemolian.app.wml.profile;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Collection;
import com.wemolian.app.wml.profile.adapter.ProfileCollectionAdapter;

/**
 * 个人收藏活动界面
 * @author zhangyun
 *
 */
public class ProfileCollectionActivity extends BaseActivity {

	ListView lv_collection;
	ProfileCollectionAdapter profileCollectionAdapter;
	List<Collection> list = new ArrayList<Collection>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_collection);
		setStatus(findViewById(R.id.title));
		lv_collection = (ListView) findViewById(R.id.lv_collection);
		for (int i = 0; i < 10; i++) {
			Collection coll = new Collection();
			if(i == 0 || i == 2 || i == 4 || i == 6){
				coll.setContact("网址");
				coll.setType(0);
			}else if(i == 1 || i == 3 || i == 5 || i == 7){
				coll.setContact("纯文本");
				coll.setType(1);
			}else{
				coll.setContact("图文");
				coll.setType(2);
			}
			
			list.add(coll);
		}
		profileCollectionAdapter = new ProfileCollectionAdapter(this, list);
		lv_collection.setAdapter(profileCollectionAdapter);
		profileCollectionAdapter.notifyDataSetChanged();
		
		
		lv_collection.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ProfileCollectionActivity.this, ProfileCollectionDetailActivity.class);
//				Intent intent=new Intent(); 
				Bundle extras=new Bundle(); 
				extras.putSerializable("collection", list.get(position)); 
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
	}
}
