package com.wemolian.app.wml.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.utils.TimeUtil;

/**
 * 添加新卡
 * @author zhangyun
 *
 */
public class ProfileAddNewCardActivity extends BaseActivity {

	LinearLayout ll_addFirst,ll_addSecond,ll_addThird;
	Button btn_nextFirst,btn_nextSecond;
	View parentView;
	TextView tv_iKnow,tv_title;
	CheckBox cb_addNewCard;
	TextView tv_verifCode;
	/**
	 * 同意选择框的选中状态，默认为false
	 */
	boolean cb_isChecked = false;
	
	/**
	 * 当点属于第几页，默认第一页
	 */
	int page = 1;
	
	/**屏幕宽度*/
	int screenWidth;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_add_new_bankcard);
		parentView = getLayoutInflater().inflate(R.layout.activity_add_new_bankcard, null);
		setStatus(findViewById(R.id.title));
	    DisplayMetrics  dm = new DisplayMetrics();    
	      //取得窗口属性    
	      getWindowManager().getDefaultDisplay().getMetrics(dm);    
	         
	      //窗口的宽度    
	      screenWidth = dm.widthPixels;    

		initView();
		initListener();
		
	}


	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("添加银行卡");
		ll_addFirst = (LinearLayout) findViewById(R.id.ll_profile_addnewcard_first);
		ll_addSecond = (LinearLayout) findViewById(R.id.ll_profile_addnewcard_second);
		ll_addThird = (LinearLayout) findViewById(R.id.ll_profile_addnewcard_third);
		ll_addFirst.setVisibility(View.VISIBLE);
		ll_addSecond.setVisibility(View.GONE);
		ll_addThird.setVisibility(View.GONE);
		btn_nextFirst = (Button) findViewById(R.id.btn_next_first);
		btn_nextSecond = (Button) findViewById(R.id.btn_next_second);
		cb_addNewCard = (CheckBox) findViewById(R.id.cb_addnew_card);
		tv_verifCode = (TextView) findViewById(R.id.tv_verif_code);
	}
	

	private void initListener() {
		btn_nextFirst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_title.setText("填写银行卡信息");
				ll_addFirst.setVisibility(View.GONE);
				ll_addSecond.setVisibility(View.VISIBLE);
				ll_addThird.setVisibility(View.GONE);
				/**
				 * 页面跳转到第二页
				 */
				page = 2;
			}
		});
		btn_nextSecond.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final PopupWindow pop = new PopupWindow(ProfileAddNewCardActivity.this);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        View conentView = inflater.inflate(R.layout.pop_addnewcard_alert, null);
		        
		        pop.setWidth(screenWidth * 4/5);
				pop.setHeight(LayoutParams.WRAP_CONTENT);
				pop.setBackgroundDrawable(new BitmapDrawable());
				pop.setFocusable(true);
				backgroundAlpha(0.5f);  
				pop.setOutsideTouchable(true);
				pop.setContentView(conentView);
				pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
				// 设置SelectPicPopupWindow弹出窗体动画效果
//				pop.setAnimationStyle(R.style.AnimationPreview);
			    pop.setOnDismissListener(new poponDismissListener());  
			    
			    tv_iKnow = (TextView) conentView.findViewById(R.id.tv_iknow);
			    tv_iKnow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						tv_title.setText("验证手机号");
						ll_addFirst.setVisibility(View.GONE);
						ll_addSecond.setVisibility(View.GONE);
						ll_addThird.setVisibility(View.VISIBLE);
						/**
						 * 页面跳转到第三页
						 */
						page = 3;
						pop.dismiss();
//						Toast.makeText(ProfileAddNewCardActivity.this, "我知道了", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
    /** 
     * 设置添加屏幕的背景透明度 
     * @param bgAlpha 
     */  
    public void backgroundAlpha(float bgAlpha)  
    {  
        WindowManager.LayoutParams lp = getWindow().getAttributes();  
        lp.alpha = bgAlpha; //0.0-1.0  
                getWindow().setAttributes(lp);  
    }  
    
    /**
     * 点击同意文字框的动作
     * @param v
     */
    public void agreeCheckBox(View v) {
    	cb_isChecked  = cb_addNewCard.isChecked();
    	
    	if(cb_isChecked){
    		cb_addNewCard.setChecked(false);
    		cb_isChecked = false;
    	}else{
    		cb_addNewCard.setChecked(true);
    		cb_isChecked = true;
    	}
	}
    
    /**
     * 获取验证码
     * @param v
     */
    public void getVerifCode(View v) {
    	TimeUtil t = new TimeUtil(tv_verifCode, "重新获取");
    	t.RunTimer();
	}
    
    /**
     * 验证手机号时，无法收到短信
     * @param v
     */
    public void noVerifCode(View v) {
    	final PopupWindow pop = new PopupWindow(ProfileAddNewCardActivity.this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.pop_addnew_card_vercode_alert, null);
        
        pop.setWidth(screenWidth * 4/5);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		backgroundAlpha(0.5f);  
		pop.setOutsideTouchable(true);
		pop.setContentView(conentView);
		pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		// 设置SelectPicPopupWindow弹出窗体动画效果
//		pop.setAnimationStyle(R.style.AnimationPreview);
	    pop.setOnDismissListener(new poponDismissListener());
	    tv_iKnow = (TextView) conentView.findViewById(R.id.tv_iknow_not_vercode);
	    tv_iKnow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pop.dismiss();
//				Toast.makeText(ProfileAddNewCardActivity.this, "我知道了", Toast.LENGTH_SHORT).show();
			}
		});
	}
    
    @Override
    public void back(View view) {
    	switch (page) {
		case 1:
			super.back(view);
			break;
		case 2:
			tv_title.setText("添加银行卡");
			ll_addFirst.setVisibility(View.VISIBLE);
			ll_addSecond.setVisibility(View.GONE);
			ll_addThird.setVisibility(View.GONE);
			page = 1;
			break;
		case 3:
			tv_title.setText("填写银行卡信息");
			ll_addFirst.setVisibility(View.GONE);
			ll_addSecond.setVisibility(View.VISIBLE);
			ll_addThird.setVisibility(View.GONE);
			page = 2;
			break;
		}
    }
    
    /**
	 * 重写按下返回键的作用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			back(null);
            return true;  
        } else  
            return super.onKeyDown(keyCode, event);  
	}
    
    
    
    
    
    
    /** 
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来 
     * @author cg 
     * 
     */  
    class poponDismissListener implements PopupWindow.OnDismissListener{  
  
        @Override  
        public void onDismiss() {  
            // TODO Auto-generated method stub  
            //Log.v("List_noteTypeActivity:", "我是关闭事件");  
            backgroundAlpha(1f);  
        }  
          
    }  
    
    
}
