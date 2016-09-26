package com.wemolian.app.wml.profile.adapter;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wemolian.app.R;
import com.wemolian.app.domain.Contacts;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 谁能看我的个人空间的适配器，
 * 此适配器可适用于禁止黑名单
 * @author zhangyun
 *
 */
public class WhoCanSeeAdapter extends BaseAdapter {

	public static List<String> blackUserList = new ArrayList<String>();
	private Map<String, Integer> listMap = new HashMap<String, Integer>();
	List<Contacts> list;
	Context context;
	Button btn_ok;
	private LoadUserAvatar avatarLoader;
	
	@SuppressLint("SdCardPath")
	public WhoCanSeeAdapter(Context context,Button btn_ok,List<Contacts> list){
		this.context = context;
		this.list = list;
		this.btn_ok = btn_ok;
		avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
	}
	
	
	@Override
	public int getCount() {
		return list == null ? 0: list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_who_can_scan, null);
		}
		
		final Contacts user = list.get(position);
		ImageView iv_avatar = (ImageView) convertView
                .findViewById(R.id.iv_avatar);

        TextView nameTextview = (TextView) convertView
                .findViewById(R.id.tv_name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        View view_temp = (View) convertView.findViewById(R.id.view_temp);
        CheckBox cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);

        cb_choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String externalUse = user.getExternalUse();
				if(isChecked){
					blackUserList.add(user.getExternalUse());
				}else{
					blackUserList.remove(user.getExternalUse());
				}
				btn_ok.setText("确定(" + blackUserList.size() + ")");
			}
		});
        
        
        Log.e("cdh", "view_temp=" + view_temp);
        if (user == null)
            Log.d("ContactAdapter", position + "");
        // 设置nick，demo里不涉及到完整user，用username代替nick显示

        String header = user.getHeader();
        String headImg = user.getImgName();
        String usernick = "";
        String label = user.getLabel();
        String userRemark = user.getUserRemark();
        String userCName = user.getUserCName();
        if(label != null){
        	usernick = label;
        }else if(userRemark != null){
        	usernick = userRemark;
        }else if(userCName != null){
        	usernick = userCName;
        }else{
        	usernick = user.getNick();
        }
        
        

        nameTextview.setText(usernick);
        iv_avatar.setImageResource(R.drawable.default_useravatar);
        showUserAvatar(iv_avatar, headImg);
		return convertView;
	}

	
	private void showUserAvatar(ImageView iamgeView, String avatar) {
        if(avatar==null||avatar.equals("")) return;
        final String url_avatar = Constant.URL_Avatar + avatar;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
            Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
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
    }
}
