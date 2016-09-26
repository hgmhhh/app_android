package com.wemolian.app.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

public class TimeUtil {
	private int time = 60;

	private Timer timer;

	private TextView btnSure;

	private String btnText;

	public TimeUtil(TextView btnSure, String btnText) {
		super();
		this.btnSure = btnSure;
		this.btnText = btnText;
	}

	public void RunTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				time--;
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);

			}
		};

		timer.schedule(task, 100, 1000);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				if (time > 0) {
					btnSure.setEnabled(false);
					btnSure.setClickable(false);
					btnSure.setText( "重新获取("+time + ")");
				} else {

					timer.cancel();
					btnSure.setText(btnText);
					btnSure.setEnabled(true);
					btnSure.setClickable(true);
					
				}

				break;

			default:
				break;
			}

		};
	};

}
