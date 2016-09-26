package com.wemolian.app.wml.others;

import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMGroup;
import com.wemolian.app.R;
import com.wemolian.app.domain.Groups;
import com.wemolian.app.entity.Constant;
import com.wemolian.app.entity.SysConfig;
import com.wemolian.app.utils.SplitString;
import com.wemolian.app.wml.ChatActivity;
import com.wemolian.app.wml.ChatRoomActivity;
import com.wemolian.app.wml.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "SdCardPath", "InflateParams" })
public class ChatRoomAdapter extends BaseAdapter {

    Context context;
    List<Groups> grouplist;
    private LayoutInflater inflater;
    private LoadUserAvatar avatarLoader;

    public ChatRoomAdapter(Context context, List<Groups> grouplist) {
        this.context = context;
        this.grouplist = grouplist;
        inflater = LayoutInflater.from(context);
        avatarLoader = new LoadUserAvatar(context, "/sdcard/wemolian/");
    }

    @Override
    public int getCount() {
        return grouplist.size();
    }

    @Override
    public Groups getItem(int position) {
        return grouplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        Groups group = grouplist.get(position);
//        String groupName_temp = group.getGroupName();
//        final String groupId = group.getGroupId();
        final String groupHxId = group.getGroupHxId();
        String membersHeadImg = group.getGroupUserHeadImgs();
        final String groupId = group.getGroupId();
 
        int membersNum =0;
        String[] imgs = null;
        if(membersHeadImg != null){
        	
        	 imgs = SplitString.splitImgs(membersHeadImg).split(SysConfig.SPLIT_STR);
        	 membersNum = imgs.length;
//            String[] str = membersHeadImg.split(",");
//            String s = "";
//            for (int i = 0; i < str.length; i++) {
//            	if(i == 0 ){
//            		s = str[i].split("#")[1];
//            	}else{
//            	s += "," + str[i].split("#")[1];
//            	}
//    		}
//            imgs = s.split(",");
//            membersNum = imgs.length;
        }else{
        	Toast.makeText(context, "获取群成员信息出错", Toast.LENGTH_SHORT).show();
        	
        }

        

        convertView = creatConvertView(membersNum);
        String groupCName = group.getGroupcname();
        if (membersNum == 1) {
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setText(groupCName);
            holder.iv_avatar1 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar1);
            showUserAvatar(holder.iv_avatar1, imgs[0]);
        } else if (membersNum == 2) {
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_avatar1 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar1);
            holder.tv_name.setText(groupCName);
            holder.iv_avatar2 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar2);
            showUserAvatar(holder.iv_avatar1, imgs[0]);
            showUserAvatar(holder.iv_avatar2, imgs[1]);
        } else if (membersNum == 3) {
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_avatar1 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar1);
            holder.tv_name.setText(groupCName);
            holder.iv_avatar2 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar2);
            holder.iv_avatar3 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar3);
            showUserAvatar(holder.iv_avatar3, imgs[2]);
            showUserAvatar(holder.iv_avatar1, imgs[0]);
            showUserAvatar(holder.iv_avatar2, imgs[1]);
        } else if (membersNum == 4) {
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_avatar1 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar1);
            holder.tv_name.setText(groupCName);
            holder.iv_avatar2 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar2);
            holder.iv_avatar3 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar3);
            holder.iv_avatar4 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar4);
            showUserAvatar(holder.iv_avatar4, imgs[3]);
            showUserAvatar(holder.iv_avatar3, imgs[2]);
            showUserAvatar(holder.iv_avatar1, imgs[0]);
            showUserAvatar(holder.iv_avatar2, imgs[1]);
        } else if (membersNum > 4) {
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_avatar1 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar1);
            holder.tv_name.setText(groupCName);
            holder.iv_avatar2 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar2);
            holder.iv_avatar3 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar3);
            holder.iv_avatar4 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar4);
            holder.iv_avatar5 = (ImageView) convertView
                    .findViewById(R.id.iv_avatar5);
            showUserAvatar(holder.iv_avatar5, imgs[4]);
            showUserAvatar(holder.iv_avatar4, imgs[3]);
            showUserAvatar(holder.iv_avatar3, imgs[2]);
            showUserAvatar(holder.iv_avatar1, imgs[0]);
            showUserAvatar(holder.iv_avatar2, imgs[1]);
        }
      final String  groupName_tem=groupCName;
        // 为了item变色在此处写监听
        RelativeLayout re_item = (RelativeLayout) convertView
                .findViewById(R.id.re_item);
        re_item.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 进入群聊
                Intent intent = new Intent(context, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
                intent.putExtra("groupHxId", groupHxId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupName", groupName_tem);
                context.startActivity(intent);
                ((ChatRoomActivity)context).finish();
            }

        });

        return convertView;
    }

    private static class ViewHolder {

        TextView tv_name;
        ImageView iv_avatar1;
        ImageView iv_avatar2;
        ImageView iv_avatar3;
        ImageView iv_avatar4;
        ImageView iv_avatar5;

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

    private View creatConvertView( int size) {
        View convertView;
        switch (size) {
        case 1:
            convertView = inflater.inflate(R.layout.item_chatroom_1, null,
                    false);

            break;
        case 2:
            convertView = inflater.inflate(R.layout.item_chatroom_2, null,
                    false);
            break;
        case 3:
            convertView = inflater.inflate(R.layout.item_chatroom_3, null,
                    false);
            break;
        case 4:
            convertView = inflater.inflate(R.layout.item_chatroom_4, null,
                    false);
            break;
        case 5:
            convertView = inflater.inflate(R.layout.item_chatroom_5, null,
                    false);
        default:
            convertView = inflater.inflate(R.layout.item_chatroom_5, null,
                    false);
            break;

        }
        return convertView;
    }

}
