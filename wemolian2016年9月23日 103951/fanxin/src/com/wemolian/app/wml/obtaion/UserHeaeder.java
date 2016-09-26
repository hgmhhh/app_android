package com.wemolian.app.wml.obtaion;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;


/**
 * hearder属性类
 * @author zhangyun
 *
 */
public class UserHeaeder {
	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param userCName
	 * @param contacts
	 */
	@SuppressLint("DefaultLocale")
	public static void setUserHearder(String userCName, Contacts contacts) {
		String headerName = null;
		if(!TextUtils.isEmpty(contacts.getLabel())){
			headerName = contacts.getLabel();
		}else if(!TextUtils.isEmpty(contacts.getUserRemark())){
			headerName = contacts.getUserRemark();
		}else if(!TextUtils.isEmpty(contacts.getNick())){
			headerName = contacts.getNick();
		}
		else if (!TextUtils.isEmpty(contacts.getUserCName())) {
			headerName = contacts.getUserCName();
		} else {
			headerName = contacts.getUsername();
		}
		headerName = headerName.trim();
		if ((Constant.NEW_FRIENDS_USERNAME).equals(userCName)) {
			contacts.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			contacts.setHeader("#");
		} else {
			contacts.setHeader(HanziToPinyin.getInstance()
					.get(headerName.substring(0, 1)).get(0).target.substring(0,
					1).toUpperCase());
			char header = contacts.getHeader().toLowerCase().charAt(0);
			if (header < 'a' || header > 'z') {
				contacts.setHeader("#");
			}
		}
	}

}
