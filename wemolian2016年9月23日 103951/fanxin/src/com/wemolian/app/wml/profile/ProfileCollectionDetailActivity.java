package com.wemolian.app.wml.profile;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Collection;
import com.wemolian.app.wml.profile.adapter.ProfileCollectionDetailAdapter;

/**
 * 收藏项目的详细信息
 * @author zhangyun
 *
 */
public class ProfileCollectionDetailActivity extends BaseActivity {

	ListView lv_collectionDetailImages;
	ProfileCollectionDetailAdapter detailAdapter;
	List<String> collList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_collection_detail);
		setStatus(findViewById(R.id.title));
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Collection coll = (Collection) bundle.get("collection");
			System.out.println(coll);
		}
		
		
		lv_collectionDetailImages = (ListView) findViewById(R.id.lv_collection_detail_images);
		detailAdapter = new ProfileCollectionDetailAdapter(this, collList);
		lv_collectionDetailImages.setAdapter(detailAdapter);
		detailAdapter.notifyDataSetChanged();
	}
}
