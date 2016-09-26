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

import java.security.acl.Group;
import java.util.List;
import java.util.Map;

import com.wemolian.app.db.DbOpenHelper;
import com.wemolian.app.db.ContactsDao;
import com.wemolian.app.db.GroupsDao;
import com.wemolian.app.db.UserDao;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.domain.User;
import com.wemolian.app.wml.others.TopUser;
import com.wemolian.app.wml.others.TopUserDao;
import com.wemolian.applib.model.DefaultHXSDKModel;

import android.content.Context;


public class WeMoLianHXSDKModel extends DefaultHXSDKModel{

    public WeMoLianHXSDKModel(Context ctx) {
        super(ctx);
        // TODO Auto-generated constructor stub
    }

    // demo will use HuanXin roster
    public boolean getUseHXRoster() {
        // TODO Auto-generated method stub
        return true;
    }
    
    
    // demo will switch on debug mode
    public boolean isDebugMode(){
        return true;
    }
    
    //save userInfo
    public void saveUser(User user){
    	UserDao dao = new UserDao(context);
    	dao.saveUser(user);
    }
    //get userInfo
    public User getUser(String extUse){
    	UserDao dao = new UserDao(context);
    	return dao.getUser(extUse);
    }
    //delete userInfo
    public void delUser(String userExt){
    	UserDao dao = new UserDao(context);
    	dao.deleteUser(userExt);
    }
    
    public boolean saveContactList(List<Contacts> contactList) {
        // TODO Auto-generated method stub
        ContactsDao dao = new ContactsDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public Map<String, Contacts> getContactList() {
        // TODO Auto-generated method stub
        ContactsDao dao = new ContactsDao(context);
        return dao.getContactList();
    }
    public Contacts getContact(Contacts user) {
    	// TODO Auto-generated method stub
    	ContactsDao dao = new ContactsDao(context);
    	return dao.getContact(user);
    }
    
    public boolean saveGroupsList(List<Groups> groupsList) {
        // TODO Auto-generated method stub
        GroupsDao dao = new GroupsDao(context);
        dao.saveGroups(groupsList);
        return true;
    }

    public Map<String, Groups> getGroupsList() {
        // TODO Auto-generated method stub
    	GroupsDao dao = new GroupsDao(context);
    	return dao.getGroupList();
    }
    
    
    
    public Groups getGroup(String groupId){
    	GroupsDao dao = new GroupsDao(context);
    	return dao.getGroup(groupId);
    }
    
    
    public void updateGroup(Groups group){
    	GroupsDao dao = new GroupsDao(context);
//    	dao.saveMembersToGroup(group);
    	dao.updateGroup(group);
    }
    
    public void delGroup(String groupId){
    	GroupsDao dao = new GroupsDao(context);
    	dao.delGroup(groupId);
    }
    
    
    public Map<String, TopUser> getTopUserList() {
        // TODO Auto-generated method stub
        TopUserDao dao = new TopUserDao(context);
        return dao.getTopUserList();
    }
    public boolean saveTopUserList(List<TopUser> contactList) {
        // TODO Auto-generated method stub
        TopUserDao dao = new TopUserDao(context);
        dao.saveTopUserList(contactList);
        return true;
    }
     
     
    public void closeDB() {
        // TODO Auto-generated method stub
        DbOpenHelper.getInstance(context).closeDB();
    }
    
    @Override
    public String getAppProcessName() {
        // TODO Auto-generated method stub
        return "com.wemolian.app";
    }

    
}
