package com.wemolian.app.wml.circle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Circle;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.video.util.Bimp;
import com.wemolian.app.video.util.ImageItem;
import com.wemolian.app.video.util.PublicWay;
import com.wemolian.app.widget.photoview.PhotoView;
import com.wemolian.app.wml.circle.GalleryActivity.MyPageAdapter;
import com.wemolian.app.wml.circle.adapter.CircleFriendsAdapter;
import com.wemolian.app.wml.circle.zoom.ViewPagerFixed;
import com.wemolian.app.wml.entity.Data;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;


/**
 * 朋友圈预览动态的大图
 * @author zhangyun
 *
 */
public class CircleFriendsShowImagesActivity extends BaseActivity {
	
//	private ArrayList<View> listViews = null;
//	private LoadUserAvatar avatarLoader = new LoadUserAvatar(CircleFriendsShowImagesActivity.this, "/sdcard/wemolian/");
//	ViewPager iv_circleFriendImage;
//	private MyPageAdapter adapter;
//	
	
//	@Override 
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_circle_friends_show_images);
//		
//		
//	}
//	

	private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();
	private Intent intent;
	//顶部显示预览图片位置的textview
	private TextView positionTextView;
	//当前的位置
	private int location = 0;
	
	//从第几张图片进来的
	private int position;
	
	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	public int id;
	
	private Context mContext;

	RelativeLayout photo_relativeLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_friends_show_images);// 切屏到主界面
//		setStatus(findViewById(R.id.title));
		PublicWay.activityList.add(this);
		mContext = this;
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		position = intent.getIntExtra("position", 0);
		Bundle bundle = intent.getExtras();
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(R.id.vpf_showimage);
//		pager.setOnPageChangeListener(pageChangeListener);
		getImages();
		for (int i = 0; i < tempSelectBitmap.size(); i++) {
			initListViews( tempSelectBitmap.get(i).getBitmap() );
		}
		
		adapter = new MyPageAdapter(listViews,id);
		pager.setAdapter(adapter);
		pager.setPageMargin((int)getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
//		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
		
	}
	
	
	
	
	private void getImages() {
		Data data = (Data) CircleFriendsAdapter.imageDataMap.get(String.valueOf(id));
		
		String imagePath = data.getImagesName();
		List<String> images = SplitString.splitToString(data.getImagesName());
		for (String image : images) {
			ImageItem imageItem = new ImageItem();
//			imageItem.setImagePath(imagePath + image);
			imageItem.setImagePath("/sdcard/wemolian/" + image);
			tempSelectBitmap.add(imageItem);
		}
	}




	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};
	
	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}
	
	// 返回按钮添加的监听器
	private class BackListener implements OnClickListener {

		public void onClick(View v) {
			intent.setClass(CircleFriendsShowImagesActivity.this, ImageFile.class);
			startActivity(intent);
		}
	}
	

	// 完成按钮的监听
	private class GallerySendListener implements OnClickListener {
		public void onClick(View v) {
			finish();
			intent.setClass(mContext,AddCircleFriendActivity.class);
			startActivity(intent);
		}

	}


	
	
	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;
		private int selectedId;
		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}
		public MyPageAdapter(ArrayList<View> listViews,int selectedId) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
			this.selectedId = selectedId;
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
//			return POSITION_NONE;

			return selectedId;
			
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {

		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);
			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}
		
		

	}

	

	
	
	
}
