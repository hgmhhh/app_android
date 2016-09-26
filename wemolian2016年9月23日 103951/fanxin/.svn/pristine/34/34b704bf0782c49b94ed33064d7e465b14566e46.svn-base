package com.wemolian.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.entity.SysConfig;


/**
 * 截取字符串工具类
 * 
 * 
 * @author zhangyun
 *
 */
public class SplitString {
	
	/**
	 * 截取群组成员字符串
	 * @param groupMembers
	 */
	public static List<String> splitMembers(String groupMembers){
		if(groupMembers == null ){
			return null;
		}
		List<String> list = new ArrayList<String>();
		String[] members = groupMembers.split(SysConfig.SPLIT_STR);
		for (String member : members) {
			list.add(member);
		}
		
		return list;
	}
	
	/**
	 * 根据 ##split## 截取字符串，并以list<String>类型返回
	 * @param imagesName
	 * @return
	 */
	public static List<String> splitToString(String imagesName){
		if(imagesName == null){
			return null;
		}
		List<String> list = new ArrayList<String>();
		String[] images = imagesName.split(SysConfig.SPLIT_STR_USER);
		for (String img : images) {
			list.add(img);
		}
		return list;
	}
	
	/**
	 * 	 * 
	 * 824a6836c7feea99be6dcc86e8323c53  0
	 * ##split##
	 * 824a6836c7feea99be6dcc86e8323c53  1 
	 * ##split##
	 * test123                           2
	 * ##split##
	 * d89ee76994c6f23abdcbebcb3e489807.png 3
	 * 从用户头像信息中截取用户头像
	 * @param userHeadImg
	 * @return
	 */
	public static String splitImgs(String userHeadImg){//lckjsplitwemolian
		String[] users = userHeadImg.split(SysConfig.SPLIT_STR);
        String s = "";
        for (int i = 0; i < users.length; i++) {
        	if(i == 0 ){
        		int len = users[i].split(SysConfig.SPLIT_STR_USER).length;
        		s = users[i].split(SysConfig.SPLIT_STR_USER)[len - 1];
        	}else{
        		int len = users[i].split(SysConfig.SPLIT_STR_USER).length;
        		s += SysConfig.SPLIT_STR + users[i].split(SysConfig.SPLIT_STR_USER)[len - 1];
        	}
		}
        return s;
	}
	
	/**
	 * 824a6836c7feea99be6dcc86e8323c53  0
	 * ##split##
	 * 824a6836c7feea99be6dcc86e8323c53  1 
	 * ##split##
	 * test123                           2
	 * ##split##
	 * d89ee76994c6f23abdcbebcb3e489807.png 3
	 * 
	 * 用户A的环信id##split##用户A的对外使用字段##split##用户A的cname##split##用户A的头像文件
	 * 将字符串截取后，以Contacts对象返回
	 * @return List<Contacts>
	 */
	public static List<Contacts> splitToContacts(String userHeadImg) {
		String[] users = userHeadImg.split(SysConfig.SPLIT_STR);
		List<Contacts> list = new ArrayList<Contacts>();
		for (int i = 0; i < users.length; i++) {
			String[] con = users[i].split(SysConfig.SPLIT_STR_USER);
			Contacts contact = new Contacts();
			contact.setHxid(con[0]);
			contact.setExternalUse(con[1]);
			contact.setUserCName(con[2]);
			contact.setImgName(con[3]);
			list.add(contact);
		}
		return list;
	}
	
	
	

}
