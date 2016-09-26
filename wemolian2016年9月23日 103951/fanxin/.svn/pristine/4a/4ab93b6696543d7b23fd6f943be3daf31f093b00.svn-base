package com.wemolian.app.wml.circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.video.util.Bimp;
import com.wemolian.app.video.util.ImageBucket;
import com.wemolian.app.video.util.ImageItem;
import com.wemolian.app.video.util.PublicWay;
import com.wemolian.app.wml.LoginActivity;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddCircleFriendActivity extends BaseActivity implements
		OnClickListener {
	private static final int TAKE_PICTURE = 0x000001;
	private boolean addCircleFriend = false;
	// private ImageView backImg;
	private Button circleFriendSending;
	private ImageView iv_add;
	private ImageView iv_del;
	private PopupWindow pop = null;
	private View view;
	private LinearLayout ll_popup;
	private GridAdapter adapter;
	private GridView noScrollgridview;
	private View parentView;
	public static Bitmap bimap ;
	private LinearLayout ll_partner;
	String userEName;
	String userExternalUse;
	ProgressDialog dialog;
	List<BaseActivity> baseActivityList = new LinkedList<BaseActivity>();
	/**
	 * 发表的文字
	 */
	private EditText circleFriendDetail;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		baseActivityList.add(AlbumActivity);
		boolean isFirst =getIntent().getBooleanExtra("isFirst", false);
		String s = PublicWay.activityEditText;
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.jy_drltsz_btn_addperson);
		if(isFirst){
			PublicWay.activityList.clear();
			PublicWay.activityEditText = null;
			Bimp.tempSelectBitmap.clear();
		}else{
			PublicWay.activityList.add(this);
		}
		parentView = getLayoutInflater().inflate(R.layout.circle_friend_add, null);
		setContentView(parentView);
		setStatus(findViewById(R.id.title));
		dialog = new ProgressDialog(AddCircleFriendActivity.this);
		// 控件初始化
		initView();
		// 加载数据
		initData();
		// 设置监听
		initListener();

	}

	private void initData() {
		if(PublicWay.activityEditText != null){
			circleFriendDetail.setText(PublicWay.activityEditText);
			//将光标移动到文字末尾
			circleFriendDetail.setSelection(PublicWay.activityEditText.length());
			addCircleFriend = true;
		}
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(circleFriendDetail.getText().toString().trim() != null){
					PublicWay.activityEditText = circleFriendDetail.getText().toString().trim();
				}
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(circleFriendDetail.getText().toString().trim() != null){
					PublicWay.activityEditText = circleFriendDetail.getText().toString().trim();
				}
				Intent intent = new Intent(AddCircleFriendActivity.this,
						AlbumActivity.class);
				intent.putExtra("activity", "AddCircleFriendActivity");
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(PublicWay.activityEditText != null){
					PublicWay.activityEditText = null;
				}
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		circleFriendDetail.addTextChangedListener(new TextChange());
		
		
		
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				/**
				 * 将软键盘隐藏
				 */
				InputMethodManager imm = (InputMethodManager)  
				         getSystemService(Context.INPUT_METHOD_SERVICE);  
				         imm.hideSoftInputFromWindow(parentView.getApplicationWindowToken(), 0);  
				
				
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("success", "jump to select images");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(AddCircleFriendActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					//点击选择的图片，进入图片预览界面
					Intent intent = new Intent(AddCircleFriendActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("activity", "AddCircleFriendActivity");
					intent.putExtra("ID", arg2);
					startActivity(intent);
					finish();
				}
			}
		});
	}

	private void initListener() {
		// TODO Auto-generated method stub
		// backImg.setOnClickListener(this);
		ll_partner.setOnClickListener(this);
		circleFriendSending.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!addCircleFriend){
					Toast.makeText(AddCircleFriendActivity.this, "随便说点什么吧！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				dialog.setMessage("正在发表动态...");
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.show();
				Map<String, String> map = new HashMap<String, String>();
				map.put("fileStatus", SysConfig.ADD_FILE_CIRCLE_FRIEND);
				map.put("externalUse", userExternalUse);
				map.put("imagesText", circleFriendDetail.getText().toString().trim());
				//发表动态
				LoadDataFromServer task = new LoadDataFromServer(AddCircleFriendActivity.this, Constant.URL_ADD_FRIEND_CIRCLE, map);
				task.getData(new DataCallBack() {
					
					@Override
					public void onDataCallBack(JSONObject resData) {
						// TODO Auto-generated method stub
						if(resData != null && resData.getBooleanValue("success")){
							JSONObject data = resData.getJSONObject("data");
							if(data != null){
								String code = data.getString("code");
								if("200".equals(code)){
									final String circleFriendsId = data.getJSONObject("data").getString("circleFriendsId");
									runOnUiThread(new Runnable() {
										public void run() {
											uploadImages(circleFriendsId);
										}
									});
									//上传文件
									
								}else if("400".equals(code)){
									
									Toast.makeText(AddCircleFriendActivity.this, "添加数据失败", Toast.LENGTH_LONG).show();
								}else{
									
									Toast.makeText(AddCircleFriendActivity.this, "获取状态码失败", Toast.LENGTH_LONG).show();
								}
							}else{
								
								Toast.makeText(AddCircleFriendActivity.this, "解析数据失败", Toast.LENGTH_LONG).show();
							}
						}else{
							Toast.makeText(AddCircleFriendActivity.this, "请求失败", Toast.LENGTH_LONG).show();
						}
					}
				});
				
				
				
//				Toast.makeText(AddFriendCircleActivity.this, "发表动态功能正在开发中……",
//						Toast.LENGTH_SHORT).show();
			}
		});
		/**
		 * 添加图片
		 */
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AddCircleFriendActivity.this, "添加图片功能正在开发中……",
						Toast.LENGTH_SHORT).show();
			}
		});
		/**
		 * 删除选中的图片
		 */
		iv_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AddCircleFriendActivity.this, "发表动态功能正在开发中……",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@SuppressWarnings("deprecation")
	private void initView() {
		
		
		userEName = LocalUserInfo.getInstance(AddCircleFriendActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME);
		userExternalUse = LocalUserInfo.getInstance(AddCircleFriendActivity.this).getUserInfo(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		ll_partner = (LinearLayout) findViewById(R.id.ll_partner);
		// backImg = (ImageView)findViewById(R.id.img_add_friend_circle_back);
		circleFriendSending = (Button) findViewById(R.id.btn_add_friend_circle_sending);
		iv_add = (ImageView) findViewById(R.id.iv_add);
		iv_del = (ImageView) findViewById(R.id.iv_del);
		pop = new PopupWindow(AddCircleFriendActivity.this);
		
		view = getLayoutInflater().inflate(R.layout.item_circle_choose_img, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		circleFriendDetail = (EditText) findViewById(R.id.edt_add_friend_circle_detail);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview = (GridView) findViewById(R.id.gv_addfriend_imglist);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		noScrollgridview.setAdapter(adapter);
	}

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// case R.id.img_add_friend_circle_back :
		//
		// finish();
		// break;
		//点击的是非editText控件之外的地方时，隐藏软键盘
	    case R.id.ll_partner:  
	         InputMethodManager imm = (InputMethodManager)  
	         getSystemService(Context.INPUT_METHOD_SERVICE);  
	         imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	        break;  
		case R.id.btn_add_friend_circle_sending:
			
			break;
		}
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}
	
	public void back(View view) {
		PublicWay.activityList.clear();
		finish();
//		startActivity(new Intent(AddFriendCircleActivity.this, CircleFriendsActivity.class));
	}

	
	/**
	 * 将选中的文件上传   
	 * @param circleFriendId  朋友圈的Id，由第一次请求后，返回
	 */
	private void uploadImages(String circleFriendId){
		
		Map<String, String> map = new HashMap<String, String>();
		
		List<ImageItem> list = Bimp.tempSelectBitmap;
		String imgs = null;
		for (int i=0;i<list.size();i++) {
			if(i == 0){
				imgs = list.get(i).getImagePath();
			}else{
				imgs += "," + list.get(i).getImagePath();
			}
		}
		
		map.put("fileStatus", "circleFriendsPath");
		map.put("userEName", userEName);
		map.put("externalUse", userExternalUse);
		map.put("circleFriendsId", circleFriendId);
		map.put("files", imgs);
		LoadDataFromServer task = new LoadDataFromServer(AddCircleFriendActivity.this, Constant.URL_UPLOADFILE, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				// TODO Auto-generated method stub
				if(resData != null){
					boolean success = resData.getBoolean("success");
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						String code = data.getString("code");
						if("200".equals(code)){
							Toast.makeText(AddCircleFriendActivity.this, "发表成功", Toast.LENGTH_LONG).show();
							dialog.dismiss();
							
//							//发表成功，跳转到朋友圈列表
							Intent intent = new Intent(AddCircleFriendActivity.this, CircleFriendsActivity.class);
							startActivity(intent);
							finish();
						}else if("400".equals(code)){
							Toast.makeText(AddCircleFriendActivity.this, "发表失败", Toast.LENGTH_LONG).show();
							
						}else{
							
						}
					}
				}else{
					
				}
			}
		});
	}
	
	
	
	
	
	
	
	/***
	 * GridAdapter 
	 */
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.jy_drltsz_btn_addperson));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
	
	
	class TextChange implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence cs, int start, int before,
				int count) {

			if(circleFriendDetail.getText().length() > 0){
				addCircleFriend = true;
			}
			
		}

	}
	
	
	// 隐藏软键盘
		public boolean onTouchEvent(MotionEvent event) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				System.out.println("down");
				if (AddCircleFriendActivity.this.getCurrentFocus() != null) {
					if (AddCircleFriendActivity.this.getCurrentFocus().getWindowToken() != null) {
						imm.hideSoftInputFromWindow(AddCircleFriendActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}
				}
			}
			return super.onTouchEvent(event);
		}
	
}
