package com.wemolian.app.utils;

import java.io.File;

import internal.org.apache.http.entity.mime.content.FileBody;

public class MyFileBody extends FileBody {

	public MyFileBody(File arg0) {
		super(arg0);
	}

}
