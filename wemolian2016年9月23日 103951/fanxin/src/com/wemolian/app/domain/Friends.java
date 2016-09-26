package com.wemolian.app.domain;

import java.io.Serializable;


/**
 * 朋友
 * @author Administrator
 * 需要实现序列化接口，在activity间，方便将整个对象传递
 */
public class Friends implements Serializable {
	
	
	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;
	/**用户帐号*/
	String userEName;
	/**用户昵称*/
    String userCName;
    /**环信Id*/
    String hxuserId;
    /**用户地址*/
    String userAddress;
    /**用户性别*/
    String userSex;
    /**用户地区*/
    String userRegion;
    /**注册时间*/
    String registDate;
    /**电话号码*/
    String phoneNum;
    /**用户的对外使用字段*/
    String externalUse;
    /**用户的经纬度*/
    String addressPoint;
    /**用户在线标识*/
    String isOnline;
    /**用户权限*/
    String permission;
    /**用户头像*/
    String headImg;
    /**用户的二维码*/
    String qrcode;
    /**是否为vip会员*/
    String vip;
    /**用户的个性签名*/
    String autograph;
    /**用户的邮件地址*/
    String email;
    /**距离*/
    String distance;
    
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getUserEName() {
		return userEName;
	}
	public void setUserEName(String userEName) {
		this.userEName = userEName;
	}
	public String getUserCName() {
		return userCName;
	}
	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}
	public String getHxuserId() {
		return hxuserId;
	}
	public void setHxuserId(String hxuserId) {
		this.hxuserId = hxuserId;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserRegion() {
		return userRegion;
	}
	public void setUserRegion(String userRegion) {
		this.userRegion = userRegion;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getExternalUse() {
		return externalUse;
	}
	public void setExternalUse(String externalUse) {
		this.externalUse = externalUse;
	}
	public String getAddressPoint() {
		return addressPoint;
	}
	public void setAddressPoint(String addressPoint) {
		this.addressPoint = addressPoint;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Friends [userEName=" + userEName + ", userCName=" + userCName
				+ ", hxuserId=" + hxuserId + ", userAddress=" + userAddress
				+ ", userSex=" + userSex + ", userRegion=" + userRegion
				+ ", registDate=" + registDate + ", phoneNum=" + phoneNum
				+ ", externalUse=" + externalUse + ", addressPoint="
				+ addressPoint + ", isOnline=" + isOnline + ", permission="
				+ permission + ", headImg=" + headImg + ", qrcode=" + qrcode
				+ ", vip=" + vip + ", autograph=" + autograph + ", email="
				+ email + ", distance=" + distance + "]";
	}
    
	
    
}
