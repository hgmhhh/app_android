package com.wemolian.app.wml.entity;


/**
 * 经纬度
 * @author zhangyun
 *
 */
public class Point {

	/**
	 * 经度
	 */
	public float lat;
	/**
	 * 纬度
	 */
	public float lon;
	/**
	 * 头像文件/地址
	 */
	public String headImg;
	/**
	 * 用户中文名/昵称
	 */
	public String userCName;
	/**
	 * 距离
	 */
	public String distance;
	/**
	 * 对外使用字段
	 */
	public String externalUse;
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getUserCName() {
		return userCName;
	}
	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getExternalUse() {
		return externalUse;
	}
	public void setExternalUse(String externalUse) {
		this.externalUse = externalUse;
	}
	@Override
	public String toString() {
		return "Point [lat=" + lat + ", lon=" + lon + ", headImg=" + headImg
				+ ", userCName=" + userCName + ", distance=" + distance
				+ ", externalUse=" + externalUse + "]";
	}
	
	
	
}
