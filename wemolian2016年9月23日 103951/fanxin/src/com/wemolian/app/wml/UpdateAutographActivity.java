package com.wemolian.app.wml;

import java.util.HashMap;
import java.util.Map;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;

/**
 * 更新用户个性签名
 * @author zhangyun
 *putExtra("externalUse", externalUse)
            	.putExtra("autograph", autograph)
 */
public class UpdateAutographActivity extends BaseActivity {
	private String externalUse,autograph,userEName;
	private EditText et_autograph;
	private TextView btn_ok;
	
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_autograph);
		setStatus(findViewById(R.id.title));
		initView();
		intiData();
		initListener();
	}

	private void initListener() {
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_autograph.getText().toString().trim() != null 
						&& et_autograph.getText().toString().trim().length() > 0){
//					updateToService();
					Toast.makeText(UpdateAutographActivity.this, "更新个性签名", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(UpdateAutographActivity.this, "请输入个性签名", Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	private void intiData() {
		et_autograph.setHint(autograph);
	}

	private void initView() {
		externalUse = getIntent().getStringExtra("externalUse");
		autograph = getIntent().getStringExtra("autograph");
		userEName = getIntent().getStringExtra("userEName");
		et_autograph = (EditText) findViewById(R.id.et_autograph);
		btn_ok = (TextView) findViewById(R.id.tv_save);
		dialog = new ProgressDialog(UpdateAutographActivity.this);
	}
	
	/**
	 * 更新到服务器
	 */
	private void updateToService() {
		dialog.setMessage("正在更新个性签名……");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.show();
		Map<String, String> map = new HashMap<String,String>();
		map.put("Autograph", autograph);
		map.put("userEName", userEName);
		
		
		
		LoadDataFromServer task = new LoadDataFromServer(UpdateAutographActivity.this,Constant.URL_REGISTER_UPDATE,map);
		task.getData(new DataCallBack() {
			
			@Override
			public void onDataCallBack(JSONObject resData) {
				if(resData != null && resData.getBooleanValue("success")){
					JSONObject data = resData.getJSONObject("data");
					if(data != null ){
						String code = data.getString("code");
						if("200".equals(code)){
							Toast.makeText(UpdateAutographActivity.this, "更新个性签名成功", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							finish();
						}else if("400".equals(code)){
							Toast.makeText(UpdateAutographActivity.this, "更新个性签名失败", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(UpdateAutographActivity.this, "未知的状态码", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(UpdateAutographActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(UpdateAutographActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
