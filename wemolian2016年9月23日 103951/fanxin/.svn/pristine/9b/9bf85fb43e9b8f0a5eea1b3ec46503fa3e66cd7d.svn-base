package com.wemolian.app.wml;

import com.easemob.chat.EMMessage;
import com.wemolian.app.R;
import com.wemolian.app.activity.BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class ServiceFeedbackActivity extends BaseActivity {
	InputMethodManager manager;
	
	LinearLayout ll_bg;
	LinearLayout ll_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_and_feedback);
		setStatus(findViewById(R.id.title));
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		
		
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		ll_content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideKeyboard();
			}
		});
		
	}


    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		super.back(view);
	}

}
