package com.wemolian.app.wml;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import com.ab.view.sliding.AbSlidingPlayView;
import com.wemolian.app.R;
import com.wemolian.app.adapter.MyGridAdapter;
import com.wemolian.app.widget.MyGridView;

/**
 * @author 张云
 * 
 */
public class FragmentFunc extends Fragment {
	@Bind(R.id.mAbSlidingPlayView)
	protected AbSlidingPlayView mSlidingPlayView;
	@Bind(R.id.gridview)
	protected MyGridView gridview;
	public LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_func, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean("isConflict", false))
			return;
		gridview.setAdapter(new MyGridAdapter(getActivity()));
		makeDate();

	}

	private void makeDate() {
		List<Integer> list = new ArrayList<>();
		list.add(R.drawable.ic_test_0);
		list.add(R.drawable.ic_test_1);
		list.add(R.drawable.ic_test_2);
		list.add(R.drawable.ic_test_3);
		list.add(R.drawable.ic_test_4);
		list.add(R.drawable.ic_test_5);
		list.add(R.drawable.ic_test_6);
		initPayView(list);
	}

	/**
	 * 轮播图测试数据
	 */
	private void initPayView(List<Integer> list) {
		mInflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < list.size(); i++) {
			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			TextView mPlayText = (TextView) mPlayView
					.findViewById(R.id.mPlayText);
			mPlayText.setText("测试" + i);
			mPlayImage.setImageResource(list.get(i));
			// TicketApplication.getInstance().display(mPlayImage,
			// list.get(i).getString("image"));
			mSlidingPlayView.addView(mPlayView);
		}
		mSlidingPlayView.setNavHorizontalGravity(Gravity.RIGHT);
		mSlidingPlayView.startPlay();
	}
	//点击事件
	@OnItemClick(R.id.gridview) void select(int position){
		switch (position) {
		case 0:
			//进入新家生活
			break;
		default:
			break;
		}
	}

}
