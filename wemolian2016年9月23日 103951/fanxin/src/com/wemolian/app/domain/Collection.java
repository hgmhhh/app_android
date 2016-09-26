package com.wemolian.app.domain;

import java.io.Serializable;

/**
 * 收藏信息的实体类
 * @author zhangyun
 *
 */
public class Collection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收藏类型
	 * 1  --  网址，网页等
	 * 2  --  纯文本
	 * 3  --  图文信息
	 */
	private int  type;
	/**
	 * 收藏的内容
	 * type 为1 时，内容为网址
	 * type 为2 时，内容问文本描述
	 * type 为3 时，内容为图片地址+文字描述
	 */
	private String contact;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Collection [type=" + type + ", contact=" + contact + "]";
	}
	
	
}
