package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

/**
 * 更新用户地区
 * @author zhangyun
 *
 */
public class UpdateRegionActivity extends BaseActivity {
	
	public static final int REGISTER_UPDATE_REGION = 1;
	public static final int PROFILE_UPDATE_REGION =2 ;
	
	private int activityTag = 0;
	private Spinner spinnerCountry;
	private Spinner spinnerProvince;
	private Spinner spinnerCity;
	private List<String> data_list;
	private Button btnUpdateRegionOk;
	private String country;
	private String region;
	private String province = null;
	private String city = null;
	private String updateProvince = null;
	private String updateCity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region_update);
		setStatus(findViewById(R.id.title));
		initView();
		initData();
		initListener();
		
		
		
		
		
//		spinnerProvince.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
//			
//			@Override
//			public void onChildViewRemoved(View parent, View child) {
//				if(child != null){
//					int id = child.getId();
//				}
//			}
//			
//			@Override
//			public void onChildViewAdded(View parent, View child) {
//				if(child != null){
//					int id = child.getId();
//				}
//			}
//		});
		
		
		
	}
	private void initData() {
		String activity = getIntent().getStringExtra("activity");
//		if("RegisterUpdate".equals(activity)){
//			activityTag = REGISTER_UPDATE_REGION;
//		}else{
//			activityTag = PROFILE_UPDATE_REGION;
//		}
		String[] s= null;
		if (region == null) {
			region = "云南 昆明";
		}
		s = region.split(" ");
		province = s[0];
		city = s[1];
		
		spinnerProvince.setSelection(getProvince(province));
	}
	private void initView() {
		spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
		spinnerProvince = (Spinner) findViewById(R.id.spinner_province);
		spinnerCity = (Spinner) findViewById(R.id.spinner_city);
		btnUpdateRegionOk = (Button) findViewById(R.id.btn_update_region_ok);
		country = getIntent().getStringExtra("country");
		region = getIntent().getStringExtra("region");
	}
	
	private void initListener() {
		spinnerProvince.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String name = spinnerProvince.getSelectedItem().toString();
				int city = getCityList(name);
				 ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( 
			                UpdateRegionActivity.this, city, 
			                android.R.layout.simple_spinner_item);
				 spinnerCity.getHeight();
				 spinnerCity.setAdapter(adapter);
				 updateProvince = name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		spinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				updateCity = spinnerCity.getSelectedItem().toString();
				if(!(updateProvince.equals(province)) || !(updateCity.equals(city)) ){
//					Toast.makeText(UpdateRegionActivity.this, 
//							"用户地区更新为 ：" + updateProvince + " " + updateCity ,
//							Toast.LENGTH_LONG).show();
					btnUpdateRegionOk.setVisibility(View.VISIBLE);
					
				}else{
					btnUpdateRegionOk.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		btnUpdateRegionOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String newRegion = updateProvince + " " + updateCity;
				//更新用户地区
//				Toast.makeText(UpdateRegionActivity.this, "更新用户地区为：" + newRegion , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", newRegion);
                //设置返回数据
                UpdateRegionActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                UpdateRegionActivity.this.finish();
			}
		});
	}

	
	

	/**
	 * 根据传入的省份名字，得到省份对应的string-array的那么值
	 * @param name
	 * @return
	 */
	protected int getCityList(String name) {
		int province = 0;
		switch (name) {
		case "北京":
			province = R.array.beijing;
			break;
		case "天津":
			province = R.array.tianjing;
			break;
		case "河北":
			province = R.array.hebei;
			break;
		case "山西":
			province = R.array.shanxi;
			break;
		case "内蒙古":
			province = R.array.neimenggu;
			break;
		case "辽宁":
			province = R.array.liaoning;
			break;
		case "吉林":
			province = R.array.jilin;
			break;
		case "黑龙江":
			province = R.array.heilongjiang;
			break;
		case "上海":
			province = R.array.shanghai;
			break;
		case "江苏":
			province = R.array.jiangsu;
			break;
		case "浙江":
			province = R.array.zhejiang;
			break;
		case "安徽":
			province = R.array.anhui;
			break;
		case "福建":
			province = R.array.fujian;
			break;
		case "江西":
			province = R.array.jiangxi;
			break;
		case "山东":
			province = R.array.shandong;
			break;
		case "河南":
			province = R.array.henan;
			break;
		case "湖北":
			province = R.array.hubei;
			break;
		case "湖南":
			province = R.array.hunan;
			break;
		case "广东":
			province = R.array.guangdong;
			break;
		case "广西":
			province = R.array.guangxi;
			break;
		case "海南":
			province = R.array.hainan;
			break;
		case "重庆":
			province = R.array.chongqing;
			break;
		case "四川":
			province = R.array.beijing;
			break;
		case "贵州":
			province = R.array.guizhou;
			break;
		case "云南":
			province = R.array.yunnan;
			break;
		case "西藏":
			province = R.array.xizang;
			break;
		case "陕西":
			province = R.array.shanxi1;
			break;
		case "甘肃":
			province = R.array.gansu;
			break;
		case "青海":
			province = R.array.qinghai;
			break;
		case "宁夏":
			province = R.array.ningxia;
			break;
		case "新疆":
			province = R.array.xinjiang;
			break;
		case "香港":
			province = R.array.xianggang;
			break;
		case "澳门":
			province = R.array.aomen;
			break;
		case "台湾":
			province = R.array.taiwan;
			break;
		}
		return province;
	}
	/**
	 * @param name
	 * @return
	 */
	protected int getProvince(String name) {
		int postion = 0;
		switch (name) {
		case "北京":
			postion = 0;
			break;
		case "天津":
			postion = 2;
			break;
		case "河北":
			postion = 3;
			break;
		case "山西":
			postion = 4;
			break;
		case "内蒙古":
			postion = 5;
			break;
		case "辽宁":
			postion = 6;
			break;
		case "吉林":
			postion = 7;
			break;
		case "黑龙江":
			postion = 8;
			break;
		case "上海":
			postion = 9;
			break;
		case "江苏":
			postion = 10;
			break;
		case "浙江":
			postion = 11;
			break;
		case "安徽":
			postion = 12;
			break;
		case "福建":
			postion = 13;
			break;
		case "江西":
			postion = 14;
			break;
		case "山东":
			postion = 15;
			break;
		case "河南":
			postion = 16;
			break;
		case "湖北":
			postion = 17;
			break;
		case "湖南":
			postion = 18;
			break;
		case "广东":
			postion = 19;
			break;
		case "广西":
			postion = 20;
			break;
		case "海南":
			postion = 21;
			break;
		case "重庆":
			postion = 22;
			break;
		case "四川":
			postion = 23;
			break;
		case "贵州":
			postion = 24;
			break;
		case "云南":
			postion = 25;
			break;
		case "西藏":
			postion = 26;
			break;
		case "陕西":
			postion = 27;
			break;
		case "甘肃":
			postion = 28;
			break;
		case "青海":
			postion = 29;
			break;
		case "宁夏":
			postion = 30;
			break;
		case "新疆":
			postion = 31;
			break;
		case "香港":
			postion = 32;
			break;
		case "澳门":
			postion = 33;
			break;
		case "台湾":
			postion = 34;
			break;
		}
		return postion - 1;
	}
}
