package com.wemolian.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.wml.entity.Comment;
import com.wemolian.app.wml.entity.Data;


/**
 * json工具类
 * @author zhangyun
 *
 */
public class JsonUtil {

	/**
	 * "commentList": [],
			"Remark5": 1470813974843,
			"imagePath": "file/img/circleFriendImg/",
			"externalUse": "824a6836c7feea99be6dcc86e8323c53",
			"imagesText": "tesssss",
			"imagesName": "f74c8fec41c277bfd99ad1a3fe2b2330.png##split##49d94b614e22995397894cf71df1deb8.png##split##d7be2a46f08ffbd4c80ab7abd8052b96.png##split##479831994364b2e78749f13063ab5a80.png##split##e9d9d0bf96c9363199505866dd791560.png##split##81c117220b63d605900ab6e466e621c1.png##split##534fcf1f1ad3564906d99ec0b31f4dc0.png##split##138a7de2f32d011cc480886aa2b35ba0.png##split##cd222b707a11b50fdc097ef3aa16076d.png",
			"Id": 122,
			"circleid": "49c3c7488749872ee053887b6953b577",
			"state": 1,
			"addDate": "2016-08-10 15:26:12"
		}
	 */
	/**
	 * 将jsonObject类型数据，转换为Data实体类
	 * @param json
	 * @return
	 */
	public static Data jsonObjectToData(JSONObject json){
		Data data = new Data();
		
		JSONArray list = json.getJSONArray("commentList");
		int Remark5 = json.getIntValue("Remark5");
		String imagePath = json.getString("imagePath");
		String externalUse = json.getString("externalUse");
		String imagesText = json.getString("imagesText");
		String imagesName = json.getString("imagesName");
		int Id = json.getIntValue("Id");
		String circleid = json.getString("circleid");
		int state = json.getIntValue("state");
		String addDate = json.getString("addDate");
		
		

		//将jsonarray转换为Comment对象
		data.setCommentList(jsonArrayToComment(list));
		data.setRemark5(Remark5);
		data.setImagePath(imagePath);
		data.setExternalUse(externalUse);
		data.setImagesText(imagesText);
		data.setImagesName(imagesName);
		data.setId(Id);
		data.setCircleid(circleid);
		data.setState(state);
		data.setAddDate(addDate);
		
		
		return data;
	}

	private static List<Comment> jsonArrayToComment(JSONArray list) {
		if(list == null){
			return null;
		}
		List<Comment> reList = new ArrayList<Comment>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = list.getJSONObject(i);
			Comment comment = JSONObject.toJavaObject(json, Comment.class);
			reList.add(comment);
		}
		return reList;
	}
	
	
	public static Friends jsonToFriends(JSONObject json) {
		Friends friends = new Friends();
		String addressPoint = json.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
		String autograph = json.getString(LocalDBKey.USER_COLUMN_NAME_AUTOGRAPH);
		String email = json.getString(LocalDBKey.USER_COLUMN_NAME_EMAIL);
		String externalUse = json.getString(LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		String headImg = json.getString(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
		String hxuserId = json.getString(LocalDBKey.USER_COLUMN_NAME_HXUSERID);
		String isOnline = json.getString(LocalDBKey.USER_COLUMN_NAME_ISONLINE);
		String permission = json.getString(LocalDBKey.USER_COLUMN_NAME_PERMISSION);
		String phoneNum = json.getString(LocalDBKey.USER_COLUMN_NAME_PHONENUM);
		String qrcode = json.getString(LocalDBKey.USER_COLUMN_NAME_QRCODE);
		String registDate = json.getString(LocalDBKey.USER_COLUMN_NAME_REGISTDATE);
		String userAddress = json.getString(LocalDBKey.USER_COLUMN_NAME_USERADDRESS);
		String userCName = json.getString(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
		String userEName = json.getString(LocalDBKey.USER_COLUMN_NAME_USERENAME);
		String userRegion = json.getString(LocalDBKey.USER_COLUMN_NAME_USERREGION);
		String userSex = json.getString(LocalDBKey.USER_COLUMN_NAME_USERSEX);
		String vip = json.getString(LocalDBKey.USER_COLUMN_NAME_VIP);
		
		
		friends.setAddressPoint(addressPoint);
		friends.setAutograph(autograph);
		friends.setEmail(email);
		friends.setExternalUse(externalUse);
		friends.setHeadImg(headImg);
		friends.setHxuserId(hxuserId);
		friends.setIsOnline(isOnline);
		friends.setPermission(permission);
		friends.setPhoneNum(phoneNum);
		friends.setQrcode(qrcode);
		friends.setRegistDate(registDate);
		friends.setUserAddress(userAddress);
		friends.setUserCName(userCName);
		friends.setUserEName(userEName);
		friends.setUserRegion(userRegion);
		friends.setUserSex(userSex);
		friends.setVip(vip);
		return friends;
		
	}
}
