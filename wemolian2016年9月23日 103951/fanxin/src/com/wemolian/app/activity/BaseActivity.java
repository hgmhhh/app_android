package com.wemolian.app.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.Type;
import com.easemob.util.EasyUtils;
import com.wemolian.app.R;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.utils.CommonUtils;
import com.wemolian.app.wml.MainActivity;

public class BaseActivity extends FragmentActivity {
    private static final int notifiId = 11;
    protected NotificationManager notificationManager;

	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        setStatus();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
//        setActionBar(toolbar);
//		StatusBarCompat.compat(this, getResources().getColor(R.color.status_bar_color));
    }
	
	/**
	 * 检查用户是否登录，用户未登录时，跳转到登录界面
	 */
    private static void checkUserConstacts(Activity activity) {
    	if(WeMoLianApplication.getInstance().getContactList() == null){
    		Toast.makeText(activity, "用户未登录或获取好友失败", Toast.LENGTH_SHORT).show();
    		activity.finish();
    	}
	}

	/** * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值 */
    @SuppressLint("NewApi")
	public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        	 /**
             * 检查用户的好友是否为空
             */
//            checkUserConstacts(activity);
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            try {
            	// 设置根布局的参数
            	ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            	rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
			} catch (Exception e) {
				// 设置根布局的参数
//				SurfaceView rootView = (SurfaceView) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//				rootView.setFitsSystemWindows(true);
//				rootView.setClipToOutline(true);
//                rootView.setClipToPadding(true);
			}
            
        }
    }
	
    /** * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条 */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }
	
	
	/** 设置沉浸式状态栏 */
	@TargetApi(15)
	protected void setStatus(View titleView) {
		Activity ac = (Activity) titleView.getContext();
		setColor(ac, 0Xfff4a0ba);
		

	}
    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
        EMChatManager.getInstance().activityResumed();

    }
    
    
    @TargetApi(15)
	public void setScanPage(View titleView){
//		int currentapiVersion = android.os.Build.VERSION.SDK_INT; 
//		if (currentapiVersion >= android.os.Build.VERSION_CODES.KITKAT){   
//			Window window = this.getWindow();
//			WindowManager.LayoutParams layoutParams = window.getAttributes();
////			layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//			layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//			window.setAttributes(layoutParams);
//			if(titleView != null){
//				LayoutParams lp = titleView.getLayoutParams();
//				int barHeight = (int) getResources().getDimension(R.dimen.height_top_bar_api44);
//				if(lp.height != barHeight){
//					lp.height = barHeight;
//				}
//				titleView.setPadding(0, 40, 0, 0);
//			}
//			} 
    }
    

    @Override
    protected void onStart() {
        super.onStart();
        

    }

    /**
     * 当应用在前台时，如果当前消息不是属于当前会话，在状态栏提示一下 如果不需要，注释掉即可
     * 
     * @param message
     */
    protected void notifyNewMessage(EMMessage message) {
        // 如果是设置了不提醒只显示数目的群组(这个是app里保存这个数据的，demo里不做判断)
        // 以及设置了setShowNotificationInbackgroup:false(设为false后，后台时sdk也发送广播)
        if (!EasyUtils.isAppRunningForeground(this)) {
            return;
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true);
        //msg{from:cf4980102da946301261048bb7a1d20f, to:a14d324de20fff7885153d39ac27aba7 body:txt:"呀呀呀呀呀呀"
        String ticker = CommonUtils.getMessageDigest(message, this);
        if (message.getType() == Type.TXT)
            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
        String fromNick = null;
        
        	Map<String, Contacts> user = WeMoLianApplication.getInstance().getContactList();
        	Contacts friend = user.get(message.getFrom());
        	if(friend.getLabel() != null){
        		fromNick = friend.getLabel();
        	}else if(friend.getUserRemark() != null){
        		fromNick = friend.getUserRemark();
        	}else if(friend.getUserCName() != null){
        		fromNick = friend.getUserCName();
        	}
        	
        	
        if(fromNick ==null){
        	fromNick =message.getFrom();
        }
        
        
        // 设置状态栏提示
        mBuilder.setTicker(fromNick + ": " + ticker);

        // 必须设置pendingintent，否则在2.3的机器上会有bug
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notifiId,
                intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(pendingIntent);

        Notification notification = mBuilder.build();
        notificationManager.notify(notifiId, notification);
        notificationManager.cancel(notifiId);
    }

    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }
    
    
    private static Map<String,Activity> destoryMap = new HashMap<>();
    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
 
    public static void addDestoryActivity(Activity activity,String activityName) {
        destoryMap.put(activityName,activity);
    }
    /**
    *销毁指定Activity
    */
    public static void destoryActivity(String activityName) {
       Set<String> keySet=destoryMap.keySet();
        for (String key:keySet){
            destoryMap.get(key).finish();
            
        }
    }
}
