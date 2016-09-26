package com.wemolian.app.wml.profile.adapter;

import java.util.List;

import com.wemolian.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 银行卡适配器
 * @author 张云
 *
 */
public class ProfileBankCardAdapter extends BaseAdapter {

	List<String> listBankCard;
	Context context;

	public ProfileBankCardAdapter(Context context,List<String> listBankCard){
		this.context = context;
		this.listBankCard = listBankCard;
	}
	
	@Override
	public int getCount() {
		return listBankCard == null ? 0:listBankCard.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_profile_mybank_card, null);
		}
		
		TextView tv_bankName = (TextView) convertView.findViewById(R.id.tv_bank_name);
		TextView tv_bankNameLarge = (TextView) convertView.findViewById(R.id.tv_bank_name_large);
		TextView tv_bankCardNumber = (TextView) convertView.findViewById(R.id.tv_bank_card_number);
		TextView tv_cardClass = (TextView) convertView.findViewById(R.id.tv_card_class);
//		LinearLayout ll_addNewBankCard = (LinearLayout) convertView.findViewById(R.id.ll_add_new_bank_card);
		tv_bankName.setText("平安银行");
		tv_bankNameLarge.setText("平安银行");
		tv_bankCardNumber.setText("6227 0000 0024 0000 012");
		tv_cardClass.setText("储蓄卡");

		return convertView;
	}
	
}
