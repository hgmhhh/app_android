package com.wemolian.app.entity;

/**
 * 系统常量
 * @author zhangyun
 *
 */
public class SysConfig {

	/**app名称*/
	public static final String APP_NAME = "wemolian";
	/**二维码标识*/
	public static final String QRCODE_TAG = "userQRCode";
	/**二维码结束标识*/
	public static final String QRCODE_END = "end";
	/**添加朋友标识*/
	public static final String QRCODE_ADDFRIEND = "addFriend";
	/**圈吧上传文件标识*/
	public static final String ADD_FILE_CIRCLE_FRIEND = "circleFriendsPath";
	/**免吧上传文件标识*/
	public static final String ADD_FILE_CIRCLE_MIANMIAN = "circleMianMianPath";
	/**商吧上传文件标识*/
	public static final String ADD_FILE_CIRCLE_WESHOP = "circleWeShopPath";
	/**换吧上传文件标识*/
	public static final String ADD_FILE_CIRCLE_CHANGE = "changebarPath";
	/**标识*/
	public static final String TAG = "tag";
	/**截取用户之间信息*/
	public static final String SPLIT_STR = "lckjsplitwemolian";
	/**截取用户信息*/
	public static final String SPLIT_STR_USER = "##split##";
	/**截取经纬度*/
	public static final String SPLIT_POINT = "pointsplit";
	
	/**type的标识为朋友*/
	public static final String STR_TYPE_FRIEND = "friend";
	/**type的标识为群组*/
	public static final String STR_TYPE_GROUP = "group";
	
	/**
	 * 本地头像文件存储位置
	 */
	public static final String HEADIMG_PATH = "/sdcard/wemolian/headimg/";
	/**
	 * 本地图片文件存储位置
	 */
	public static final String IMG_PATH = "/sdcard/wemolian/img/";
	/**
	 * 本地文件存储位置  包括音频文件和视频文件
	 */
	public static final String FILE_PATH = "/sdcard/wemolian/file/";

	/**
	 * 消息免打扰为 true
	 */
	public static final String NEW_NOT_DIS_TRUE = "1";
	/**
	 * 消息免打扰为false
	 */
	public static final String NEW_NOT_DIS_FALSE = "0";
	
	/**
	 * 生成用户二维码的默认添加字符
	 */
	public static final String USER_QECODE_ENCODE = APP_NAME + SPLIT_STR_USER + QRCODE_TAG + SPLIT_STR_USER;
}
