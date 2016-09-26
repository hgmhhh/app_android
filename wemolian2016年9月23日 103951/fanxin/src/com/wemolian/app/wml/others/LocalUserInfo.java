package com.wemolian.app.wml.others;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;

public class LocalUserInfo {

    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "local_userinfo";
    private static SharedPreferences mSharedPreferences;
    private static LocalUserInfo mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    private LocalUserInfo(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取instance实例
     * 
     * @param cxt
     * @return
     */
    public static LocalUserInfo getInstance(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new LocalUserInfo(cxt);
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    //
    public void setUserInfo(String str_name, String str_value) {

        editor.putString(str_name, str_value);
        editor.commit();
    }
    
    
    public void setUserInfo(String str_name, int int_value) {
    	String str_value = null;
    	if(int_value >= 0){
    		str_value = String.valueOf(int_value);
    	}else{
    		str_value = "0";
    	}
    	editor.putString(str_name, str_value);
    	editor.commit();
    }

    public String getUserInfo(String str_name) {

        return mSharedPreferences.getString(str_name, "");

    }

}
