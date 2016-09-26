package com.wemolian.app.video.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 存放所有的list在最后退出时一起关闭
 *
 * @author zhangyun
 */
public class PublicWay {
	
	public static List<Activity> activityList = new ArrayList<Activity>();
	public static String activityEditText = null;
	public static int num = 9;
}
