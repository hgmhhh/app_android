package com.wemolian.app.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wemolian.app.entity.SysConfig;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


/**
 * 获取经纬度
 * @author zhangyun
 *
 */
public class LocaltionManager {

	public static Map<String, Object> getLocaltion(Context context){
		LocationManager locationManager;
		Map<String, Object> map = new HashMap<String, Object>();
		String locationProvider; 
		//获取地理位置管理器  
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        //获取所有可用的位置提供器  
        List<String> providers = locationManager.getProviders(true);  
        if(providers.contains(LocationManager.GPS_PROVIDER)){  
            //如果是GPS  
            locationProvider = LocationManager.GPS_PROVIDER;  
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){  
            //如果是Network  
            locationProvider = LocationManager.NETWORK_PROVIDER;  
        }else{  
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return null;
        }  
        //获取Location  
        Location location = locationManager.getLastKnownLocation(locationProvider);  
        if(location!=null){  
            //不为空,显示地理位置经纬度  
        	map.put("lat", location.getLatitude());
        	map.put("lon", location.getLongitude());
            List<Address> addList = null;  
            Geocoder ge = new Geocoder(context);  
            try {  
                addList = ge.getFromLocation(location.getLatitude(), location.getLongitude(), 1);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            String address = null;
            String region = null;
            if(addList!=null && addList.size()>0){  
                for(int i=0; i<addList.size(); i++){  
                    Address ad = addList.get(i);  
                    address = ad.getCountryName() + ad.getAdminArea() + ad.getLocality() + ad.getSubLocality() + ad.getThoroughfare();
                    region = ad.getAdminArea() + ad.getLocality();
                    
                } 
                map.put("region", region);
                map.put("address", address);
            } 
        }else{
        	Toast.makeText(context, "获取地点出错", Toast.LENGTH_SHORT).show();
        }
        //监视地理位置变化  
//        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
 
		return map;
	}
	
	/** 
     * LocationListern监听器 
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器 
     */  
      
    LocationListener locationListener =  new LocationListener() {  
          
        @Override  
        public void onStatusChanged(String provider, int status, Bundle arg2) {  
              
        }  
          
        @Override  
        public void onProviderEnabled(String provider) {  
              
        }  
          
        @Override  
        public void onProviderDisabled(String provider) {  
              
        }  
          
        @Override  
        public void onLocationChanged(Location location) {  
            //如果位置发生变化,重新显示  
//            showLocation(location);  
              
        }  
    }; 
}
