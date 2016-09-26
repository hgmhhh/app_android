package com.wemolian.app.wml.friends.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wemolian.app.R;
import com.wemolian.app.domain.Friends;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.wml.others.LoadUserAvatar;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class AllUserAdapter extends BaseAdapter {
    List<Friends> list;
    Context context;
    private LoadUserAvatar avatarLoader;

    @SuppressLint("SdCardPath")
    public AllUserAdapter(List<Friends> list, Context context) {
        this.context = context;
        this.list = list;
        avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_all_user, null);

            holder.img_face = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);

//            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            resetViewHolder(holder);
        }

        Friends friend = list.get(position);


        String avatar = friend.getHeadImg();
        
        holder.tv_name.setText(friend.getUserCName());
        holder.tv_distance.setText(friend.getUserRegion());

        showUserAvatar(holder.img_face, avatar);
        convertView.setTag(friend);
        return convertView;
    }

    private void showUserAvatar(ImageView iamgeView, String avatar) {
        final String url_avatar = Constant.URL_Avatar + avatar;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
            Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
                    new ImageDownloadedCallBack() {

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

    @SuppressLint("SimpleDateFormat")
    private String getTime(String get_time, String last_time) {
        String backStr = "";

        String dateStart = last_time;
        String dateStop = get_time;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            // 毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays != 0) {
                if (diffDays < 30) {
                    backStr = String.valueOf(diffDays) + "天前";
                } else {
                    backStr = "很久以前";
                }

            } else if (diffHours != 0) {
                backStr = String.valueOf(diffHours) + "小时前";

            } else if (diffMinutes != 0) {
                backStr = String.valueOf(diffMinutes) + "分钟前";

            } else {

                backStr = "刚刚";

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return backStr;

    }

    class ViewHolder {

        ImageView img_face;

        TextView tv_distance;

        TextView tv_name;

    }

    protected void resetViewHolder(ViewHolder p_ViewHolder) {

        p_ViewHolder.tv_distance.setText(null);

        p_ViewHolder.img_face.setImageResource(R.drawable.default_useravatar);
        p_ViewHolder.tv_name.setText(null);

    }

}
