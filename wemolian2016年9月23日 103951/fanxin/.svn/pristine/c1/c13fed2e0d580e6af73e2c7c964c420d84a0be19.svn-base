package com.wemolian.app.utils;

import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.User;

/**
 * 将User对象转换为Contacts对象
 * @author zhangyun
 *
 */
public class UserToContacts {
	private User user;
	private Contacts contacts;
	
	public UserToContacts(Contacts contacts,User user){
		this.user = user;
		this.contacts = contacts;
	}
	
	/**
	 * 将User对象转换为Contacts对象，并返回Contacts对象
	 * @return
	 */
	public static Contacts userToContacts(User user){
		Contacts c = new Contacts();
		if(user != null){
			c.setType("user");
			c.setExternalUse(user.getExternalUse());
			c.setHxid(user.getHxuserId());
			c.setImgName(user.getHeadImg());
			c.setLabel(user.getUserCName());
			c.setUserCName(user.getUserCName());
			c.setUsername(user.getUserEName());
		}
		return c;
	}
	
}
