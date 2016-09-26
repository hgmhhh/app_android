/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wemolian.app;

import java.util.Map;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

 





























import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.lock.pattern.App;
import com.wemolian.app.lock.view.LockPatternUtils;
import com.wemolian.app.receiver.VoiceCallReceiver;
import com.wemolian.app.utils.CommonUtils;
import com.wemolian.app.wml.ChatActivity;
import com.wemolian.app.wml.MainActivity;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.TopUser;
import com.wemolian.applib.controller.HXSDKHelper;
import com.wemolian.applib.model.HXSDKModel;
 

/**
 * Demo UI HX SDK helper class which subclass HXSDKHelper
 * 
 * @author easemob
 * 
 */
public class WeMoLianHXSDKHelper extends HXSDKHelper {
	
	
	
	
    /**
     * contact list in cache
     */
    private Map<String, Contacts> contactList;
    private Map<String, TopUser> topUserList;
    private Map<String, Groups> groupsList;
    private Groups group;
  
    @Override
    protected void initHXOptions() {
        super.initHXOptions();
        // you can also get EMChatOptions to set related SDK options
        // EMChatOptions options = EMChatManager.getInstance().getChatOptions();
    }

    @Override
    protected OnMessageNotifyListener getMessageNotifyListener() {
        // 取消注释，app在后台，有新消息来时，状态栏的消息提示换成自己写的
        return new OnMessageNotifyListener() {

            @Override
            public String onNewMessageNotify(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = CommonUtils.getMessageDigest(message,
                        appContext);
                if (message.getType() == Type.TXT)
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
//                return message.getFrom() + ": " + ticker;
                return "来消息啦: " + ticker;
            }

            @Override
            public String onLatestMessageNotify(EMMessage message,
                    int fromUsersNum, int messageNum) {
                return null;
//                return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public String onSetNotificationTitle(EMMessage message) {
                // 修改标题,这里使用默认
                return null;
            }

            @Override
            public int onSetSmallIcon(EMMessage message) {
                // 设置小图标
                return 0;
            }
        };
    }

    @Override
    protected OnNotificationClickListener getNotificationClickListener() {
        return new OnNotificationClickListener() {

            @Override
            public Intent onNotificationClick(EMMessage message) {
                Intent intent = new Intent(appContext, ChatActivity.class);
                ChatType chatType = message.getChatType();
                if (chatType == ChatType.Chat) { // 单聊信息
                    intent.putExtra("userId", message.getFrom());
                    intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                } else { // 群聊信息
                         // message.getTo()为群聊id
                    intent.putExtra("groupId", message.getTo());
                    intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
                }
                return intent;
            }
        };
    }

    @Override
    protected void onConnectionConflict() {
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("conflict", true);
        appContext.startActivity(intent);
    }

    @Override
    protected void onCurrentAccountRemoved() {
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance()
                .getIncomingVoiceCallBroadcastAction());
        appContext.registerReceiver(new VoiceCallReceiver(), callFilter);
    }

    @Override
    protected HXSDKModel createModel() {
        return new WeMoLianHXSDKModel(appContext);
    }

    /**
     * get demo HX SDK Model
     */
    public WeMoLianHXSDKModel getModel() {
        return (WeMoLianHXSDKModel) hxModel;
    }

    /**
     * 获取内存中好友user list
     * 
     * @return
     */
    public Map<String, Contacts> getContactList() {
        if (getHXId() != null && contactList == null) {
            contactList = ((WeMoLianHXSDKModel) getModel()).getContactList();
        }

        return contactList;
    }

    /**
     * 保存用户信息到内存
     * @param user
     */
    public void saveUser(User user){
    	if(user != null){
    		((WeMoLianHXSDKModel)getModel()).saveUser(user);
    	}
    	
    }
    
    /**
     * 从内存中  获取登录的用户信息
     * @param user
     * @return
     */
    public User getUser(String extUse){
    	if(extUse  != null){
    		return ((WeMoLianHXSDKModel)getModel()).getUser(extUse);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 删除登录的用户信息
     * @param userExt
     */
    public void delUser(String userExt){
    	if(userExt != null){
    		((WeMoLianHXSDKModel)getModel()).delUser(userExt);
    	}
    }
    
    /**
     * 获取内存中置顶好友 t
     * 
     * @return
     */
    public Map<String, TopUser> getTopUserList() {
        if (getHXId() != null && topUserList == null) {
            topUserList = ((WeMoLianHXSDKModel) getModel()).getTopUserList();
        }

        return topUserList;
    }

   

    

    /**
     * 设置置顶好友到内存中
     * 
     * @param contactList
     */
    public void setTopUserList(Map<String, TopUser> topUserList) {
        this.topUserList = topUserList;
    }

    /**
     * 设置好友user list到内存中
     * 
     * @param contactList
     */
    public void setContactList(Map<String, Contacts> contactList) {
        this.contactList = contactList;
    }

    
    /**
     * 从内存中获取用户群列表
     * @return
     */
    public Map<String, Groups> getGroupsList() {
        if (getHXId() != null && groupsList == null) {
        	groupsList = ((WeMoLianHXSDKModel) getModel()).getGroupsList();
        }

        return groupsList;
    }
    /**
     * 从内存中获取用户群列表
     * @return
     */
    public Map<String, Groups> getGroupsList(int tag) {
    	switch (tag) {
    	//tag为0，表示为正常
		case 0:
			if (getHXId() != null && groupsList == null) {
	        	groupsList = ((WeMoLianHXSDKModel) getModel()).getGroupsList();
	        }
			break;
			//tag为1，表示，需要重新查询群信息
		case 1:
	        groupsList = ((WeMoLianHXSDKModel) getModel()).getGroupsList();
			break;
		default :
			groupsList = ((WeMoLianHXSDKModel) getModel()).getGroupsList();
			break;
		}

        return groupsList;
    }
    
    /**
     * 从内存中获取群信息
     * @param groupId
     * @return groups对象
     */
    public Groups getGroup(String groupId){
    	if(groupId != null){
    		group = ((WeMoLianHXSDKModel) getModel()).getGroup(groupId);
    	}
    	return group;
    }
    /**
     * 从内存中删除群   群id需要传参
     * @param groupId
     */
    public void delGroup(String groupId){
    	if(groupId != null){
    		((WeMoLianHXSDKModel) getModel()).delGroup(groupId);
    	}
    }
    
    
    /**
     * 根据ExternalUse获取好友信息
     * @param user
     * @return
     */
    public Contacts getContact(Contacts user){
    	Contacts u = new Contacts();
    	
    	if(user != null && user.getExternalUse() != null){
    		 u = ((WeMoLianHXSDKModel) getModel()).getContact(user);
    	}
    	return u;
    }
    
    /**
     * 更新群信息
     * @param group
     */
    public void updateGroup(Groups group){
    	if(group != null && group.getGroupId() != null){
    		((WeMoLianHXSDKModel) getModel()).updateGroup(group);
    	}
    }
    
    /**
     * 设置好友user list到内存中
     * 
     * @param contactList
     */
    public void setGroupsList(Map<String, Groups> groupsList) {
        this.groupsList = groupsList;
    }
    
    @Override
    public void logout(final EMCallBack callback) {
        super.logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                setContactList(null);
                getModel().closeDB();
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

        });
    }

}
