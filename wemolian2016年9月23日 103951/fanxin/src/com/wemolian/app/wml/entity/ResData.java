package com.wemolian.app.wml.entity;

/**
 * 服务器返回的数据列表实体类 serviceData为数据列表
 * @author zhangyun
 *
 */
public class ResData {

	/**
	 * 成功标识
	 */
	private boolean success;

	/**
	 * 服务器返回的文字描述
	 */
	private String message;

	/**
	 * 服务器返回的数据
	 */
	private Data data;

	/**
	 * 服务器返回的状态码
	 */
	private int code;

	@Override
	public String toString() {
		return "ResData [success=" + success + ", message=" + message
				+ ", serviceData=" + data + ", code=" + code + "]";
	}

	public Data getServiceData() {
		return data;
	}

	public void setServiceData(Data data) {
		this.data = data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}



	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
