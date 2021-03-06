package com.wemolian.app.wml;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.Base64Util;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.wemolian.app.zxing.encode.CodeCreator;

/**
 * 显示二维码
 * @author zhangyun
 *
 */
public class ShowQRCodeActivity extends BaseActivity {
	/**
	 *  图片宽度的一半
	 */
	private static final int IMAGE_HALFWIDTH = 40;
	private LoadUserAvatar avatarLoader;
	private String headImg,userCName,region,externalUse;
	private TextView tv_userCName;
	private TextView tv_region;
	private ImageView iv_headImg;
	private ImageView iv_qrcode;
	Bitmap bitmap;
	Bitmap headImgBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode_show);
		setStatus(findViewById(R.id.title));
		avatarLoader = new LoadUserAvatar(this, "/sdcard/wemolian/");
		initView();
		initData();
	}
	
	private void initData() {
		String msg = SysConfig.USER_QECODE_ENCODE + "externalUse" 
				    +SysConfig.SPLIT_STR_USER + externalUse 
				    + SysConfig.SPLIT_STR_USER + SysConfig.TAG
				    + SysConfig.SPLIT_STR_USER + SysConfig.QRCODE_ADDFRIEND
				    + SysConfig.SPLIT_STR_USER + SysConfig.QRCODE_END;
//		String createStr = Base64Util.base64Encode(msg);
		
		//构建图片对象
				//构造需要插入的图片对象
//		headImgBitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.logo_wemolian)).getBitmap();
				// 缩放图片
				Matrix matrix = new Matrix();
				float sx = (float)2*IMAGE_HALFWIDTH/headImgBitmap.getWidth();
				float sy = (float)2*IMAGE_HALFWIDTH/headImgBitmap.getHeight();
				matrix.setScale(sx, sy);
				// 重新构造一个80*80的图片
				headImgBitmap = Bitmap.createBitmap(headImgBitmap, 0, 0, headImgBitmap.getWidth(), headImgBitmap.getHeight(),
						matrix, false);
				
		//生成二维码
		try {
//			bitmap = CodeCreator.createQRCode(msg);
			bitmap = CodeCreator.createQRCode(msg, headImgBitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv_userCName.setText(userCName);
		tv_region.setText(region);
		iv_qrcode.setImageBitmap(bitmap);
	}

	private void initView() {
		headImg = getIntent().getStringExtra("headImg");
    	userCName = getIntent().getStringExtra("userCName");
    	externalUse = getIntent().getStringExtra("externalUse");
    	region = getIntent().getStringExtra("region");
    	tv_userCName = (TextView) findViewById(R.id.tv_usercname);
    	tv_region = (TextView) findViewById(R.id.tv_region);
    	iv_headImg = (ImageView) findViewById(R.id.iv_headimg);
    	iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);
    	headImgBitmap = showUserAvatar(iv_headImg, headImg);
	}
	
	
	private Bitmap showUserAvatar(ImageView iamgeView, String avatar) {
        final String url_avatar = Constant.URL_Avatar + avatar;
        Bitmap bitmap = null;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
        	bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
                    new ImageDownloadedCallBack() {

                        @Override
                        public void onImageDownloaded(ImageView imageView,
                                Bitmap bitmap) {
                            if (imageView.getTag() == url_avatar) {
                                imageView.setImageBitmap(bitmap);

                            }
                        }

                    });
            if (bitmap != null)
                iamgeView.setImageBitmap(bitmap);

        }
        return bitmap;
    }
}
