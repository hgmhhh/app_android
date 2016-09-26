package com.wemolian.app.lock.number;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;


/**
 * 数字锁
 * @author zhangyun
 *
 */
public class NumberLockActivity extends BaseActivity {

	ImageView iv_numberLock1;
	ImageView iv_numberLock2;
	ImageView iv_numberLock3;
	ImageView iv_numberLock4;
	EditText et_inputNumber;
	String number_temp;
	TextView tv_inputNumberTitle;
	public int INPUT_TIME = 1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.lock_number_create);
		setStatus(findViewById(R.id.title));
		initView();
		
	}

	private void initView() {
		iv_numberLock1 = (ImageView) findViewById(R.id.iv_number_lock1);
		iv_numberLock2 = (ImageView) findViewById(R.id.iv_number_lock2);
		iv_numberLock3 = (ImageView) findViewById(R.id.iv_number_lock3);
		iv_numberLock4 = (ImageView) findViewById(R.id.iv_number_lock4);
		et_inputNumber = (EditText) findViewById(R.id.et_input_number);
		tv_inputNumberTitle = (TextView) findViewById(R.id.tv_input_number_title);
		/***
		 * 默认不显示输入的红点
		 */
		iv_numberLock1.setVisibility(View.GONE);
		iv_numberLock2.setVisibility(View.GONE);
		iv_numberLock3.setVisibility(View.GONE);
		iv_numberLock4.setVisibility(View.GONE);
		et_inputNumber.setText("");
		et_inputNumber.addTextChangedListener(new TextChange());
	}
	
	
	
	class TextChange implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence cs, int start, int before,
				int count) {

			switch (et_inputNumber.getText().length()) {
			case 0:
				iv_numberLock1.setVisibility(View.GONE);
				iv_numberLock2.setVisibility(View.GONE);
				iv_numberLock3.setVisibility(View.GONE);
				iv_numberLock4.setVisibility(View.GONE);
				break;
			case 1:
				iv_numberLock1.setVisibility(View.VISIBLE);
				iv_numberLock2.setVisibility(View.GONE);
				iv_numberLock3.setVisibility(View.GONE);
				iv_numberLock4.setVisibility(View.GONE);
				break;
			case 2:
				iv_numberLock1.setVisibility(View.VISIBLE);
				iv_numberLock2.setVisibility(View.VISIBLE);
				iv_numberLock3.setVisibility(View.GONE);
				iv_numberLock4.setVisibility(View.GONE);
				break;
			case 3:
				iv_numberLock1.setVisibility(View.VISIBLE);
				iv_numberLock2.setVisibility(View.VISIBLE);
				iv_numberLock3.setVisibility(View.VISIBLE);
				iv_numberLock4.setVisibility(View.GONE);
				break;
			case 4:
				iv_numberLock1.setVisibility(View.VISIBLE);
				iv_numberLock2.setVisibility(View.VISIBLE);
				iv_numberLock3.setVisibility(View.VISIBLE);
				iv_numberLock4.setVisibility(View.VISIBLE);
				if(INPUT_TIME == 1){
					reInputNumber();
				}else if(INPUT_TIME == 2){
					setLock();
				}
				break;
			}
			
			
			
		}

	}



	/**
	 * 再次输入密码
	 */
	public void reInputNumber() {
		number_temp = et_inputNumber.getText().toString();
		tv_inputNumberTitle.setText("请再次输入密码");
		et_inputNumber.setText("");
		INPUT_TIME = 2;
		iv_numberLock1.setVisibility(View.GONE);
		iv_numberLock2.setVisibility(View.GONE);
		iv_numberLock3.setVisibility(View.GONE);
		iv_numberLock4.setVisibility(View.GONE);
		
	}

	public void setLock() {
		String s = number_temp;
		String s1 = et_inputNumber.getText().toString();
		Toast.makeText(this, "第一次:" + s + "第二次:" + s1, Toast.LENGTH_SHORT).show();
	}
}
