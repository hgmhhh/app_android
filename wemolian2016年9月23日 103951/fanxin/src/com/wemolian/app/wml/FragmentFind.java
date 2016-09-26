package com.wemolian.app.wml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;

import com.wemolian.app.R;
import com.wemolian.app.wml.circle.CircleFriendsActivity;
import com.wemolian.app.wml.circle.CircleMianMianActivity;
import com.wemolian.app.wml.circle.CircleWeShopActivity;

/**
 * @author 张云 发现
 * 
 */
public class FragmentFind extends Fragment {

	private LayoutInflater infalter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*
		 * //免免圈 RelativeLayout reMianMianCircle = (RelativeLayout)
		 * getView().findViewById( R.id.re_find_mianmian_circle);
		 * reMianMianCircle.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { startActivity(new
		 * Intent(getActivity(), CircleMianMianActivity.class)); }
		 * 
		 * }); //朋友圈 RelativeLayout reFriendsCircle = (RelativeLayout)
		 * getView().findViewById( R.id.re_find_friend_circle);
		 * reFriendsCircle.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { startActivity(new
		 * Intent(getActivity(), CircleFriendsActivity.class)); }
		 * 
		 * }); //微商圈 RelativeLayout reWeShopCircle = (RelativeLayout)
		 * getView().findViewById( R.id.re_find_weshop_circle);
		 * reWeShopCircle.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { startActivity(new
		 * Intent(getActivity(),CircleWeShopActivity.class)); }
		 * 
		 * });
		 */
	}

}
