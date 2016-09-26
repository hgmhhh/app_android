package com.wemolian.app.wml.entity;

import java.util.List;

public class ServiceData {

	private String code;

	private List<Data> data;

	private String message;

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return this.data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
