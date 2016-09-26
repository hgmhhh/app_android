package com.wemolian.app.domain;

import java.util.Date;

import com.easemob.chat.EMContact;

/**
 * 用户信息实体类
 * @author zhangyun 2016.07.09
 *
 */
public class User extends EMContact {
	/**
	 * 用户名
	 */
	String userEName;
	/**
	 * 昵称
	 */
	String userCName;
	/**
	 * 环信id
	 */
	String hxuserId;
	/**
	 * 环信密码
	 */
	String hxpassword;
	/**
	 * 用户地址
	 */
	String userAddress;
	
	/**
	 * 用户性别
	 */
	String userSex;
	/**
	 * 用户地区
	 */
	String userRegion;
	/**
	 * 用户银行卡卡号
	 */
	String bankCard;
	/**
	 * 用户注册时间
	 */
	String registDate;
	/**
	 * 用户电话号码
	 */
	String phoneNum;
	/**
	 * 用户密码
	 */
	String password;
	String oldPassword;
	/**
	 * 唯一字段
	 */
	String externalUse;
	/**
	 * 地址经纬度
	 */
	String addressPoint;
	/**
	 * 在线标识
	 */
	int isOnline;
	/**
	 * 用户状态
	 * 封禁、正常、等
	 */
	int state;
	/**
	 * 用户权限
	 */
	String permission;
	/**
	 * 用户id
	 */
	int id;
	/**
	 * 用户头像文件
	 */
	String headImg;
	/**
	 * 用户二维码文件
	 */
	String qrcode;
	/**
	 * 用户邮件地址
	 */
	String email;
	/**
	 * 用户个性签名
	 */
	String autograph;
	/**
	 * 用户vip标识
	 */
	String vip;
	Date intTimeStamp;
	String randomNum;
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
	public String getHxpassword() {
		return hxpassword;
	}
	public void setHxpassword(String hxpassword) {
		this.hxpassword = hxpassword;
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
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public Date getIntTimeStamp() {
		return intTimeStamp;
	}
	public void setIntTimeStamp(Date intTimeStamp) {
		this.intTimeStamp = intTimeStamp;
	}
	public String getRandomNum() {
		return randomNum;
	}
	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}
	
	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Contacts)) {
			return false;
		}
		return getUsername().equals(((Contacts) o).getUsername());
	}
	
	
	
	@Override
	public String toString() {
		return "User [userEName=" + userEName + ", userCName=" + userCName
				+ ", hxuserId=" + hxuserId + ", hxpassword=" + hxpassword
				+ ", userAddress=" + userAddress + ", userSex=" + userSex
				+ ", userRegion=" + userRegion + ", bankCard=" + bankCard
				+ ", registDate=" + registDate + ", phoneNum=" + phoneNum
				+ ", password=" + password + ", oldPassword=" + oldPassword
				+ ", externalUse=" + externalUse + ", addressPoint="
				+ addressPoint + ", isOnline=" + isOnline + ", state=" + state
				+ ", permission=" + permission + ", id=" + id + ", headImg="
				+ headImg + ", qrcode=" + qrcode + ", email=" + email
				+ ", autograph=" + autograph + ", vip=" + vip
				+ ", intTimeStamp=" + intTimeStamp + ", randomNum=" + randomNum
				+ "]";
	}
	
	
	
	
	

}
