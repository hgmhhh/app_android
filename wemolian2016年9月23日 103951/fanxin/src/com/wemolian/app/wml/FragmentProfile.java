package com.wemolian.app.wml;

import java.util.zip.Inflater;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.LocalDBKey;
import com.wemolian.app.wml.circle.AddCircleFriendActivity;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LocalUserInfo;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.wemolian.app.wml.profile.ProfileAlbumActivity;
import com.wemolian.app.wml.profile.ProfileExpressionActivity;
import com.wemolian.app.wml.profile.ProfileMoneyActivity;
import com.wemolian.app.wml.profile.ProfileCollectionActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class FragmentProfile extends Fragment {

    private LoadUserAvatar avatarLoader;
    private String avatar = "";
    private ImageView iv_avatar,iv_Profile_MyQRCode;
    private TextView tv_name;
    TextView tv_user_ename;
    String userEName;
    String nick;
    View qrCode_view;
    LayoutInflater inflater;
    ViewGroup container;
    private PopupWindow pop = null;
    View v1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	this.inflater = inflater;
    	this.container = container;
    	qrCode_view = inflater.inflate(R.layout.item_qrcode_show,null);
    	v1 = inflater.inflate(R.layout.fragment_profile, null);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatarLoader = new LoadUserAvatar(getActivity(), "/sdcard/wemolian/");
        
        /**
         * 我的二维码
         */
        iv_Profile_MyQRCode = (ImageView) getView().findViewById(R.id.iv_profile_my_qrcode);
        iv_Profile_MyQRCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userEName = LocalUserInfo.getInstance(getActivity()).getUserInfo(
		                LocalDBKey.USER_COLUMN_NAME_USERENAME);
		        String userCName = LocalUserInfo.getInstance(getActivity()).getUserInfo(
		        		LocalDBKey.USER_COLUMN_NAME_USERCNAME);
		        String externalUse = LocalUserInfo.getInstance(getActivity()).getUserInfo(
		        		LocalDBKey.USER_COLUMN_NAME_EXTERNALUSE);
		        String userRegion = LocalUserInfo.getInstance(getActivity())
		        		.getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERREGION);
		        

				startActivity(new Intent(getActivity(),
                        ShowQRCodeActivity.class)
            	.putExtra("headImg", avatar)
            	.putExtra("userCName", userCName)
            	.putExtra("externalUse", externalUse)
            	.putExtra("region", userRegion));
			}
		});
        
        RelativeLayout re_myinfo = (RelativeLayout) getView().findViewById(
                R.id.re_myinfo);
        re_myinfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),
                        MyUserInfoActivity.class));
            }

        });
        /**
         * 设置
         */
        RelativeLayout reSetting = (RelativeLayout) getView().findViewById(
                R.id.re_setting);
        reSetting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	BaseActivity.addDestoryActivity(getActivity(), "mainActivity");
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }

        });
        /**
         * 表情
         */
        RelativeLayout reExpression = (RelativeLayout) getView().findViewById(
                R.id.re_expression);
        reExpression.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileExpressionActivity.class));
            }

        });
        /**
         * 钱庄
         */
        RelativeLayout reMoney = (RelativeLayout) getView().findViewById(
                R.id.re_money);
        reMoney.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileMoneyActivity.class));
            }

        });
        /**
         * 相册
         */
        RelativeLayout reAlbum = (RelativeLayout) getView().findViewById(
                R.id.re_album);
        reAlbum.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileAlbumActivity.class));
            }

        });
        /**
         * 收藏
         */
        RelativeLayout reCollection = (RelativeLayout) getView().findViewById(R.id.re_collection);
        reCollection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),ProfileCollectionActivity.class));
			}
		});
        nick = LocalUserInfo.getInstance(getActivity()).getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERCNAME);
        userEName = LocalUserInfo.getInstance(getActivity()).getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME);
        String fxId = LocalUserInfo.getInstance(getActivity()).getUserInfo("fxId");

//        avatar = LocalUserInfo.getInstance(getActivity()).getUserInfo("headImg");
        avatar = LocalUserInfo.getInstance(getActivity()).getUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
//        avatar = LocalUserInfo.getInstance(getActivity()).getUserInfo(LocalDBKey.CONTACTS_COLUMN_NAME_IMGNAME);
//        avatar1 = LocalUserInfo.getInstance(getActivity()).getUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
        iv_avatar = (ImageView) re_myinfo.findViewById(R.id.iv_avatar);
        tv_name = (TextView) re_myinfo.findViewById(R.id.tv_name);
        tv_user_ename = (TextView) re_myinfo.findViewById(R.id.tv_user_ename);
        tv_name.setText(nick);
        if (userEName == null) {
        	tv_user_ename.setText("微默联号：未设置");
        } else {
        	tv_user_ename.setText("微默联号:" + userEName);
        }
        if(avatar != null && avatar != ""){
        	showUserAvatar(iv_avatar, avatar);
        }
    }

    private void showUserAvatar(ImageView iamgeView, String avatar) {
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

    @Override
    public void onResume() {
        super.onResume();
        String vatar_temp = LocalUserInfo.getInstance(getActivity())
                .getUserInfo(LocalDBKey.USER_COLUMN_NAME_HEADIMG);
        if (!vatar_temp.equals(avatar)) {
            showUserAvatar(iv_avatar, avatar);
        }

        String nick_temp = LocalUserInfo.getInstance(getActivity())
                .getUserInfo("nick");
        String fxid_temp = LocalUserInfo.getInstance(getActivity())
                .getUserInfo(LocalDBKey.USER_COLUMN_NAME_USERENAME);
        if (!nick_temp.equals(nick)) {
            tv_name.setText(nick_temp);
        }
        if (!fxid_temp.equals(userEName)) {
            if (fxid_temp.equals("0")) {
                tv_user_ename.setText("微默联号：未设置");
            } else {
                tv_user_ename.setText("微默联号:" + fxid_temp);
            }
        }
    }

}
