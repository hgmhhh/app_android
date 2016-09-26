package com.wemolian.app.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将传入的对象转换为list集合
 * 
 * @author zhangyun
 *
 */
public class ToArrayList {
	private List<String> list;
	private String[] strs;

	public ToArrayList(List<String> list, String[] strs) {
		this.list = list;
		this.strs = strs;
	}
	
	/**
	 * 将String数组转换为list集合
	 * @param strs
	 * @return
	 */
	public static List<String> StringsToArrayList(String[] strs){
		if(strs == null){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;
	}

}
