package com.wemolian.app.zxing.android;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.CheckQRCode;
import com.wemolian.app.utils.JsonUtil;
import com.wemolian.app.wml.friends.AddFriendsFromAllUserActivity;
import com.wemolian.app.wml.friends.AllUserActivity;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.wemolian.app.zxing.camera.CameraManager;
import com.wemolian.app.zxing.view.ViewfinderView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 * 
 */
public final class CaptureActivity extends BaseActivity implements
		SurfaceHolder.Callback {

	private static final String TAG = CaptureActivity.class.getSimpleName();

	// 相机控制
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private IntentSource source;
	private Collection<BarcodeFormat> decodeFormats;
	private Map<DecodeHintType, ?> decodeHints;
	private String characterSet;
	// 电量控制
	private InactivityTimer inactivityTimer;
	// 声音、震动控制
	private BeepManager beepManager;

	private ImageView imageButton_back;

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	/**
	 * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// 保持Activity处于唤醒状态
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.qrcode_capture);
		setStatus(findViewById(R.id.title));
		hasSurface = false;

		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);

		imageButton_back = (ImageView) findViewById(R.id.capture_imageview_back);
		imageButton_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		// CameraManager必须在这里初始化，而不是在onCreate()中。
		// 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
		// 当扫描框的尺寸不正确时会出现bug
		cameraManager = new CameraManager(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);

		handler = null;

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			// activity在paused时但不会stopped,因此surface仍旧存在；
			// surfaceCreated()不会调用，因此在这里初始化camera
			initCamera(surfaceHolder);
		} else {
			// 重置callback，等待surfaceCreated()来初始化camera
			surfaceHolder.addCallback(this);
		}

		beepManager.updatePrefs();
		inactivityTimer.onResume();

		source = IntentSource.NONE;
		decodeFormats = null;
		characterSet = null;
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		beepManager.close();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * 扫描成功，处理反馈信息
	 * 
	 * @param rawResult
	 * @param barcode
	 * @param scaleFactor
	 */
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		inactivityTimer.onActivity();

		boolean fromLiveScan = barcode != null;
		//这里处理解码完成后的结果，此处将参数回传到Activity处理
//		if (fromLiveScan) {
//			beepManager.playBeepSoundAndVibrate();
//
////			Toast.makeText(this, "扫描成功", Toast.LENGTH_SHORT).show();
//			Intent intent = getIntent();
//			intent.putExtra("codedContent", rawResult.getText());
//			intent.putExtra("codedBitmap", barcode);
//			setResult(RESULT_OK, intent);
//			finish();
//		}
		if (fromLiveScan) {
			beepManager.playBeepSoundAndVibrate();

			//处理扫描结果
			getScanResult(rawResult.getText());
//			Intent intent = new Intent(this, cls);
//			intent.putExtra("codedContent", rawResult.getText());
//			intent.putExtra("codedBitmap", barcode);
////			setResult(RESULT_OK, intent);
//			startActivity(intent);
			finish();
		}

	}

	public boolean isRightQRCode = false;
	/**
	 * 处理扫描二维码的结果
	 * @param text
	 */
	private void getScanResult(String text) {
		if(text == null){
			Toast.makeText(this, "扫描二维码出错", Toast.LENGTH_SHORT).show();
			return;
		}
		/**wemolian##split##userQRCode##split##externalUse##split##9dee6dcda130411a4bb2fe5e60175621##split##tag##split##addFriend##split##end*/
		String[] msg = text.split(SysConfig.SPLIT_STR_USER);
		int code = CheckQRCode.checkQRCodeWhenAddFriend(msg);
		
		switch (code) {
		case CheckQRCode.ERROR_APP_NAME:
			Toast.makeText(this, "无法识别非本app生成的二维码", Toast.LENGTH_SHORT).show();
			break;
		case CheckQRCode.ERROR_QRCODE:
			Toast.makeText(this, "无法识别的二维码", Toast.LENGTH_SHORT).show();
			break;
		case CheckQRCode.ERROR_QRCODE_END:
			Toast.makeText(this, "无效的二维码结束标识", Toast.LENGTH_SHORT).show();
			break;
		case CheckQRCode.ERROR_QRCODE_TAG:
			Toast.makeText(this, "无效的二维码标识", Toast.LENGTH_SHORT).show();
			break;
		case CheckQRCode.SUCCESS_CHECK:
			isRightQRCode = true;
			break;

		}
		if(isRightQRCode){
			String externalUse = msg[3];
			getDataFromService(externalUse);
			/**
			 * 二维码正确时，到服务器查询数据，并跳转到新的界面
			 */
//			Toast.makeText(this, "扫描成功:" + text, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 从服务器查询数据
	 * @param externalUse
	 */
	private void getDataFromService(String externalUse) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("externalUse", externalUse);
		LoadDataFromServer task = new LoadDataFromServer(this, Constant.URL_FIND_USER_BY_EXTERNALUSE, map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if (resData != null && resData.getBooleanValue("success")) {
					JSONObject data = resData.getJSONObject("data");
					if(data != null){
						int code = data.getIntValue("code");
						if(code == 200){
							JSONObject obj = data.getJSONObject("data");
							Friends friend = JsonUtil.jsonToFriends((JSONObject) obj);
							startActivity(
									new Intent(CaptureActivity.this,AddFriendsFromAllUserActivity.class)
									.putExtra("userInfo", friend));
							finish();
						}else if(code ==  400){
							Toast.makeText(CaptureActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CaptureActivity.this, "解析数据出错", Toast.LENGTH_SHORT).show();
							
						}
					}
				}
			}
		});
	}

	/**
	 * 初始化Camera
	 * 
	 * @param surfaceHolder
	 */
	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			// 打开Camera硬件设备
			cameraManager.openDriver(surfaceHolder);
			// 创建一个handler来打开预览，并抛出一个运行时异常
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats,
						decodeHints, characterSet, cameraManager);
			}
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializing camera", e);
			displayFrameworkBugMessageAndExit();
		}
	}

	/**
	 * 显示底层错误信息并退出应用
	 */
	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage("Sorry, the Android camera encountered a problem. You may need to restart the device.");
		builder.setPositiveButton("OK", new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}
}
