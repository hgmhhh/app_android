/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wemolian.app.wml;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

 
























import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.wemolian.app.WeMoLianApplication;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.utils.Md5Util;
import com.wemolian.app.utils.TimeUtil;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;
import com.easemob.exceptions.EaseMobException;
 

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
    private EditText et_phoneNum;
    private EditText et_password;
    private EditText et_passwordReInput;
    
    /**验证码输入框*/
    private EditText et_shortmessageInput;
    private Button btn_register;
    private TextView tv_xieyi;
    private TextView tv_title;
    String phone;
    private Button iv_getVerifyCode;
    String serviceVerifyCode;
    /**
     * 验证码是否正确  -- 服务器校验，然后返回结果
     */
    boolean checkVerifyCode = false;
    
    
    private LinearLayout ll_register_page1;
    private LinearLayout ll_register_page2;
    private LinearLayout ll_country_region;
    private RelativeLayout rl_country_region;
    ProgressDialog dialog;
    
    String activity;
    int page = 1;
    private String imageName;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatus(findViewById(R.id.title));
        initView();
        initData();
        initListener();
    }
    
    private void initData() {
    	//忘记密码
    	if("forgetPwd".equals(activity)){
    		ll_country_region.setVisibility(View.GONE);
    		rl_country_region.setVisibility(View.GONE);
    		tv_title.setText("忘记密码");
    	}
    	ll_register_page1.setVisibility(View.VISIBLE);
    	ll_register_page2.setVisibility(View.GONE);
    	btn_register.setText("下一步");
    	String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
                + "\"" + "注册" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + "《微默联软件许可及服务协议》"
                + "</font>" + "</u>";
        tv_xieyi.setText(Html.fromHtml(xieyi));
    	}
    private void initView() {
    	activity = getIntent().getStringExtra("activity");
    	  dialog = new ProgressDialog(RegisterActivity.this);
    	  ll_country_region = (LinearLayout) findViewById(R.id.ll_country_region);
    	  rl_country_region = (RelativeLayout) findViewById(R.id.rl_country_region);
    	  ll_register_page1 = (LinearLayout) findViewById(R.id.ll_regist_page1);
    	  ll_register_page2 = (LinearLayout) findViewById(R.id.ll_regist_page2);
          et_phoneNum = (EditText) findViewById(R.id.et_usertel);
          et_password = (EditText) findViewById(R.id.et_password);
          tv_title = (TextView) findViewById(R.id.tv_title);
          et_shortmessageInput = (EditText) findViewById(R.id.et_shortmessage_input);
          et_passwordReInput = (EditText) findViewById(R.id.et_password_reinput);
          // 监听多个输入框
//          et_phoneNum.addTextChangedListener(new TextChange());
          et_password.addTextChangedListener(new TextChange());
          btn_register = (Button) findViewById(R.id.btn_register);
          tv_xieyi = (TextView) findViewById(R.id.tv_xieyi);
          iv_getVerifyCode = (Button) findViewById(R.id.iv_get_verify_code);
    	}
  private void initListener() {
	  /**
       * 获取验证码按钮点击事件
       */
      iv_getVerifyCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Map<String, String> map = new HashMap<String, String>();
				phone = et_phoneNum.getText().toString().trim();
				if(!isMobileNO(phone)){
					Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
				TimeUtil t = new TimeUtil(iv_getVerifyCode, "重新获取");
		    	t.RunTimer();
				map.put("phone", phone);
				/**
				 * 向服务器请求数据，由服务器向手机发送一个验证码
				 */
				LoadDataFromServer task = new LoadDataFromServer(RegisterActivity.this, Constant.URL_GET_VERIFY_CODE, map);
				task.getData(new DataCallBack() {
					
					@Override
					public void onDataCallBack(JSONObject resData) {
						/**
						 * {"success":true,"message":null,
						 * "data":{"message":"消息发送成功",
						 * "phone":"14787811148",
						 * "data":"","code":"200",
						 * "validateNumber":108067},"code":0}
						 */
						if(resData != null && resData.getBooleanValue("success")){
							JSONObject data = resData.getJSONObject("data");
							if(data != null){
								int code = data.getIntValue("code");
								if(code == 200  ){
									serviceVerifyCode = data.getString("validateNumber");
								}
							}
						}
					}
				});
			}
		});
      
      btn_register.setOnClickListener(new OnClickListener() {

          @SuppressLint("SdCardPath")
          @Override
          public void onClick(View v) {
        	  if(page == 1){
        		  if(!(et_shortmessageInput.getText().toString().trim().length() > 0)){
        			  Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
        		  }
        		  /**
        		   * 校验验证码
        		   */
        		  else if(checkVerifyCode()){
        			  page =2;
        			  ll_register_page2.setVisibility(View.VISIBLE);
        			  ll_register_page1.setVisibility(View.GONE);
        			  if("forgetPwd".equals(activity)){
        				  btn_register.setText("完成");
        				  tv_title.setText("重置密码");
        			  }else{
        				  btn_register.setText("注册");
        				  tv_title.setText("设置密码");
        			  }
        		  }else{
//        			  dialog.setMessage("验证码错误");
//        			  dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        			  dialog.show();
        			  Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
        		  }
        		  return;
        	  }
        	  
        	  else if(page == 2 ){
        		  String str1 = et_passwordReInput.getText().toString().trim();
        		  String str2 = et_password.getText().toString().trim();
        		  if(!(str1.equals(str2))){
        			  Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
        			  return;
        		  }
        	  }
        	  /**
        	   * 重置密码
        	   */
              if("forgetPwd".equals(activity)){
            	  replacePwd();
              }
              /**
               * 注册用户
               */
              else{
            	   registerNewUser();
               }
          }


      });
	}
  /**
   * 重置密码
   */
  private void replacePwd() {
	  dialog.setMessage("正在修改密码……");
      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      dialog.show();

      final String password = et_password.getText().toString().trim();
      final String userEName = et_phoneNum.getText().toString().trim();
      Map<String, String> map = new HashMap<String, String>();
      map.put("userEName", userEName);
      map.put("password", password);
      map.put("inputNumber", serviceVerifyCode);
      LoadDataFromServer registerTask = new LoadDataFromServer(
      		RegisterActivity.this, Constant.URL_UPDATE_PASSWORD, map);

      registerTask.getData(new DataCallBack() {

          @SuppressLint("ShowToast")
          @Override
          public void onDataCallBack(JSONObject registData) {
              try {
              	//返回数据成功
              	if(registData.getBoolean("success")){
              		JSONObject data = registData.getJSONObject("data");
              		Log.i("data",data.toString());
                      int code = data.getInteger("code");
						/**
						 * 200 修改成功 
						 * 400 修改失败 
						 * 401 输入的验证吗错误 
						 * 403 输入的验证空 
						 * 408 用户不存在
						 */
                      if (code == 200) {
                      	dialog.dismiss();
                      	Toast.makeText(RegisterActivity.this, R.string.Updatepassword_successfully, Toast.LENGTH_SHORT).show();
                      	
                          Intent intent=new Intent();  
                          intent.setClass(RegisterActivity.this, LoginActivity.class);//从一个activity跳转到另一个activity  
                          startActivity(intent);
                          finish();
                      } else if (code == 400 || code == 401 || code == 403 || code == 408) {
                          dialog.dismiss();
                          Toast.makeText(RegisterActivity.this,
                                  data.getString("message"), Toast.LENGTH_SHORT)
                                  .show();
                      }else {
                          dialog.dismiss();
                          Toast.makeText(RegisterActivity.this,
                                  "服务器繁忙请重试...", Toast.LENGTH_SHORT)
                                  .show();
                      }

                  
              	}else{
              		Toast.makeText(RegisterActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
              	}
              	
              	
              	
              } catch (JSONException e) {
                  dialog.dismiss();
                  Toast.makeText(RegisterActivity.this, "数据解析错误...",
                          Toast.LENGTH_SHORT).show();
                  e.printStackTrace();
              }

          }

      });
  }

  /**
   * 注册新用户
   */
  private void registerNewUser() {
	  dialog.setMessage("正在注册...");
      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      dialog.show();

      final String password = et_password.getText().toString().trim();
      final String userEName = et_phoneNum.getText().toString().trim();
      Map<String, String> map = new HashMap<String, String>();
      if ((new File("/sdcard/wemolian/" + imageName)).exists()) {
          map.put("file", "/sdcard/wemolian/" + imageName);
          map.put("image", imageName);
      } else {

          map.put("image", "false");
      }
      map.put("userEName", userEName);
//      map.put("password", Md5Util.md5(password));
      map.put("password", password);
//      LoadDataFromServer registerTask = new LoadDataFromServer(
//              RegisterActivity.this, Constant.URL_Register_Tel, map);
      LoadDataFromServer registerTask = new LoadDataFromServer(
      		RegisterActivity.this, Constant.URL_REGISTER, map);

      registerTask.getData(new DataCallBack() {

          @SuppressLint("ShowToast")
          @Override
          public void onDataCallBack(JSONObject registData) {
              try {
              	//返回数据成功
              	if(registData.getBoolean("success")){
              		JSONObject data = registData.getJSONObject("data");
              		Log.i("data",data.toString());
                      int code = data.getInteger("code");
                      if (code == 200) {
                      	//注册成功
                      	
//                        String hxid = data.getString("hxid");
//                        register(hxid, password);
                      	dialog.dismiss();
                      	Toast.makeText(RegisterActivity.this, R.string.Registered_successfully, Toast.LENGTH_SHORT).show();
//                      	startActivity(new Intent(RegisterActivity.this,
//          						RegisterUpdateActivity.class));
                      	
                          Intent intent=new Intent();  
                          intent.setClass(RegisterActivity.this, RegisterUpdateActivity.class);//从一个activity跳转到另一个activity  
                          intent.putExtra("userEName", userEName);//给intent添加额外数据，key为“userEName”,key值为用户名  
                          intent.putExtra("password", password);//给intent添加额外数据，key为“password”,key值为用户密码
                          startActivity(intent); 
                          finish();
                      } else if (code == 400) {
                          dialog.dismiss();
                          Toast.makeText(RegisterActivity.this,
                                  data.getString("message"), Toast.LENGTH_SHORT)
                                  .show();
                      } else {
                          dialog.dismiss();
                          Toast.makeText(RegisterActivity.this,
                                  "服务器繁忙请重试...", Toast.LENGTH_SHORT)
                                  .show();
                      }

                  
              	}else{
              		//服务器注册失败
              		Toast.makeText(RegisterActivity.this, "服务器注册失败", Toast.LENGTH_SHORT).show();
              	}
              	
              	
              	
              } catch (JSONException e) {
                  dialog.dismiss();
                  Toast.makeText(RegisterActivity.this, "数据解析错误...",
                          Toast.LENGTH_SHORT).show();
                  e.printStackTrace();
              }

          }

      });
  }
  	/**
  	 * 校验验证码
  	 * @return true 正确
  	 *         false 错误
  	 */
  	private boolean checkVerifyCode() {
  		String inputVerifyCode = et_shortmessageInput.getText().toString().trim();
  		if(inputVerifyCode.equals(serviceVerifyCode)){
  			return true;
  		}else{
  			return false;
  		}
//  		return true;
	}
  
/**
   * 判断手机号码是否正确
   * @param mobiles
   * @return
   */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:

                startPhotoZoom(
                        Uri.fromFile(new File("/sdcard/wemolian/", imageName)),
                        480);
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 480);
                break;


            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @SuppressLint("SdCardPath")
    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File("/sdcard/wemolian/", imageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {

        	
        	
//            boolean Sign1 = et_userCName.getText().length() > 0;
            boolean Sign2 = et_phoneNum.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;

            if (Sign2 & Sign3) {
//                btn_register.setTextColor(0xFF929292);
                btn_register.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_register.setTextColor(0xFFFFFFFF);
                btn_register.setEnabled(false);
            }
        }

    }


    public void back(View view) {
    	if (page == 2) {
			ll_register_page1.setVisibility(View.VISIBLE);
			ll_register_page2.setVisibility(View.GONE);
			btn_register.setText("下一步");
			page =1;
		}else{
			finish();
		}
    }
    
  //隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("down");
            if (RegisterActivity.this.getCurrentFocus() != null) {
                if (RegisterActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


}
