package com.wemolian.app.domain;

import java.util.Date;

import com.easemob.chat.EMContact;


/**
 * 用户好友的信息
 * 用户的群信息
 * @author zhangyun 2016.07.05
 *
 */
public class Contacts extends EMContact {
	
	/**
	 * 类型
	 * 群聊：groups
	 * 联系人：friends
	 * 当为新朋友时，为item_new—_friend
	 * 为群组时，为item_groups
	 */
	private String type;
	/**
	 * 未读信息条数
	 */
	private int unreadMsgCount;
	/**
	 * 
	 */
	private String header;
	/**
	 * 对外使用字段
	 */
	private String externalUse;
	/**
	 * 环信Id
	 */
	private String  hxid;
	/**
	 * 用户的昵称
	 */
	private String userCName;
	/**
	 * 是否消息免打扰
	 */
	private int newsNotDis;
	/**
	 * 备注（对应好友的备注）
	 */
	private String userRemark;
	/**
	 * 是否为黑名单中的用户
	 */
	private int blackList;
	/**
	 * 是否看他的朋友圈、相册(是/否:1/0)
	 */
	private int developmentMe;
	/**
	 * 标签/备注
	 */
	private String label;
	
	/**
	 * 图片地址
	 */
	private String imgName;
	/**
	 * 消息置顶
	 */
	private String chatTop;
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 用户个性签名
	 */
	private String autograph;
	
	
	
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}
	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getExternalUse() {
		return externalUse;
	}
	public void setExternalUse(String externalUse) {
		this.externalUse = externalUse;
	}
	public String getHxid() {
		return hxid;
	}
	public void setHxid(String hxid) {
		this.hxid = hxid;
	}
	public String getUserCName() {
		return userCName;
	}
	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}
	public int getNewsNotDis() {
		return newsNotDis;
	}
	public void setNewsNotDis(int newsNotDis) {
		this.newsNotDis = newsNotDis;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public int getBlackList() {
		return blackList;
	}
	public void setBlackList(int blackList) {
		this.blackList = blackList;
	}
	public int getDevelopmentMe() {
		return developmentMe;
	}
	public void setDevelopmentMe(int developmentMe) {
		this.developmentMe = developmentMe;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getChatTop() {
		return chatTop;
	}
	public void setChatTop(String chatTop) {
		this.chatTop = chatTop;
	}
	
	/**
	 * 获取好友名 -->有备注的时候，显示的是备注名，没有备注时，显示的是好友的cname
	 * @param Contacts
	 * @return 好友名
	 */
	public String getUserLabel(Contacts con){
		if(con == null){
			return "未知的好友";
		}
		String returnStr = null;
		if(con.getLabel() != null){
			returnStr = con.getLabel();
		}else if(con.getUserRemark() != null){
			returnStr = con.getUserRemark();
		}else if(con.getNick() != null){
			returnStr = con.getNick();
		}else if(con.getUserCName() != null){
			returnStr = con.getUserCName();
		}else{
			returnStr = "好友未命名";
		}
		return returnStr;
	}
	
	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

@Override
	public String toString() {
		return "Contacts [type=" + type + ", unreadMsgCount=" + unreadMsgCount
				+ ", header=" + header + ", externalUse=" + externalUse
				+ ", hxid=" + hxid + ", userCName=" + userCName
				+ ", newsNotDis=" + newsNotDis + ", userRemark=" + userRemark
				+ ", blackList=" + blackList + ", developmentMe="
				+ developmentMe + ", label=" + label + ", imgName=" + imgName
				+ ", chatTop=" + chatTop + ", sex=" + sex + ", autograph="
				+ autograph + "]";
	}
	
	


	
	
	
	
	
	
}
