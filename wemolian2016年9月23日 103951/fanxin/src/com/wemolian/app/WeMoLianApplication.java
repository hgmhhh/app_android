package com.wemolian.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.easemob.EMCallBack;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.lock.view.LockPatternUtils;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.TopUser;

public class WeMoLianApplication extends Application {
    public static String last_time ="0";
    public List<JSONObject> list= new ArrayList<JSONObject>();    
    public static int page = 0;
	public static Context applicationContext;
	private static WeMoLianApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	
	/**
	 * 当前用户userename,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static WeMoLianHXSDKHelper hxSDKHelper = new WeMoLianHXSDKHelper();

	
	
	
	
	
	
	//设备锁(手势)功能添加时添加的信息 
	//add by zhangyun 2016年7月1日 21:29:31start  
	private LockPatternUtils mLockPatternUtils;



	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}
	//add by zhangyun 2016年7月1日 21:29:31 end
	
	
	
	
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLockPatternUtils = new LockPatternUtils(this);
        applicationContext = this;
        instance = this;

        /**
         * this function will initialize the HuanXin SDK
         * 
         * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
         * 
         * 环信初始化SDK帮助函数
         * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
         * 
         * for example:
         * 例子：
         * 
         * public class DemoHXSDKHelper extends HXSDKHelper
         * 
         * HXHelper = new DemoHXSDKHelper();
         * if(HXHelper.onInit(context)){
         *     // do HuanXin related work
         * }
         */
        hxSDKHelper.onInit(applicationContext);    
        getTime();
        
	}
    private  void getTime(){
        String hxid=getUserName();
        if(hxid==null) return;
        Map<String,String > map=new HashMap<String,String >();
        map.put("hxid",hxid );
        LoadDataFromServer task=new LoadDataFromServer(getApplicationContext(), Constant.URL_UPDATETIME, map);
        task.getData(null);
        
    }
	public static WeMoLianApplication getInstance() {
		return instance;
	}
	
	/**
	 * 向内存中存入用户数据
	 * @param user
	 */
	public void saveUserInfo(User user){
		hxSDKHelper.saveUser(user);
	}
	
	/**
	 * 获取内存中的用户信息
	 * @param user
	 * @return
	 */
	public User getUserInfo(String extUse){
		return hxSDKHelper.getUser(extUse);
	}
	
	/**
	 * 删除登录用户，在内存中的数据
	 * @param userExt
	 */
	public void delUserInfo(String userExt){
		hxSDKHelper.delUser(userExt);
	}
 
	/**
	 * 获取内存中好友user list
	 *
	 * @return
	 */
	public Map<String, Contacts> getContactList() {
	    return hxSDKHelper.getContactList();
	}
	
	
	/**
     * 获取内存中置顶好友user list
     *
     * @return
     */
	
	public Map<String, TopUser> getTopUserList() {
        return hxSDKHelper.getTopUserList();
    }
	 
   
	/**
     * 设置好友user list到内存中
     *
     * @param contactList
     */
    public void setContactList(Map<String, Contacts> contactList) {
        hxSDKHelper.setContactList(contactList);
    }
    
	/**
     * 设置用户群 list到内存中
     *
     * @param contactList
     */
    public void setGroupsList(Map<String, Groups> groupsList) {
        hxSDKHelper.setGroupsList(groupsList);
    }
    
    /**
     * 获取群聊列表
     *
     */
    public Map<String, Groups> getGroupsList() {
        return hxSDKHelper.getGroupsList(0);
    }
    /**
     * 删除群聊后，重新获取群聊列表
     */
    public Map<String, Groups> getGroupsList(int tag) {
        return hxSDKHelper.getGroupsList(tag);
    }
    
    
    /**
     * 获取单个群聊信息
     */
	public Groups getGroup(String groupId){
		return hxSDKHelper.getGroup(groupId);
	}
	
	/**
	 * 删除单个群
	 * @param groupId
	 */
	public void delGroup(String groupId){
		hxSDKHelper.delGroup(groupId);
	}
	/**
	 * 将新加的群成员，添加到db
	 * @param group
	 */
	public void updateGroup(Groups group){
		hxSDKHelper.updateGroup(group);
	}
    
	public Contacts getContact(Contacts user){
		return hxSDKHelper.getContact(user);
	}
    
	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getUserName() {
	    return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 *
	 * @param user
	 */
	public void setUserName(String username) {
	    hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
	    hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据 --一个参数时不用添加退出登录的id，但是需要重写方法
	 */
	public void logout(final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
		hxSDKHelper.logout(emCallBack);
	}
	/**
	 * 退出登录,清空数据
	 * userId 用户的Id-- 即对外使用字段externalUse 
	 */
	public void logout(final EMCallBack emCallBack,final String userId) {
		// 先调用sdk logout，在清理app中自己的数据
	    hxSDKHelper.logout(emCallBack);
	}
}
