package com.wemolian.app.utils;

import android.widget.Toast;

import com.wemolian.app.entity.SysConfig;

/**
 * 检测二维码工具类
 * @author zhangyun
 *
 */
public class CheckQRCode {
	public static final int ERROR_QRCODE = 0;
	public static final int ERROR_APP_NAME = 1;
	public static final int ERROR_QRCODE_TAG = 2;
	public static final int ERROR_QRCODE_END = 3;
	public static final int SUCCESS_CHECK = -1;
	
	
	/**wemolian##split##userQRCode##split##externalUse##split##9dee6dcda130411a4bb2fe5e60175621##split##tag##split##addFriend##split##end*/

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static int checkQRCodeWhenAddFriend(String[] msg){
		if(msg.length < 7){
			return ERROR_QRCODE;
		}
		if(!(SysConfig.APP_NAME.equals(msg[0]))){
			return ERROR_APP_NAME;
		}
		if(!(SysConfig.QRCODE_TAG.equals(msg[1]))){
			return ERROR_QRCODE_TAG;
		}
		if(!(SysConfig.QRCODE_END.equals(msg[6]))){
			return ERROR_QRCODE_END;
		}
		else
			{
			return SUCCESS_CHECK;
			}
	}
}
