package com.syh.handlerdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * android 更新UI常见的方式
 * 
 * @author shenyonghe 2015-5-18
 */
@SuppressLint("HandlerLeak") public class FiveActivity extends Activity implements OnClickListener {

	private TextView mInfoTv;
	private Button handlerPost, handlerSend, runOnUI, viewPost;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
             mInfoTv.setText("handler Send Msg!");
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.five);
		initViews();
	}

	private void initViews() {
		mInfoTv = (TextView) findViewById(R.id.tv_info);

		handlerPost = (Button) findViewById(R.id.hanlderpost);
		handlerSend = (Button) findViewById(R.id.handlersendmsg);
		runOnUI = (Button) findViewById(R.id.runOnUITrhread);
		viewPost = (Button) findViewById(R.id.viewpost);

		handlerPost.setOnClickListener(this);
		handlerSend.setOnClickListener(this);
		runOnUI.setOnClickListener(this);
		viewPost.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		MyThread myThread = new MyThread(v.getId());
		myThread.start();

	}

	class MyThread extends Thread {

		private int type;

		public MyThread(int id) {
			this.type = id;
		}

		public void run() {
			try {
				Thread.sleep(1000);

				switch (type) {
				case R.id.hanlderpost:
					mHandler.post(new Runnable() {

						public void run() {
							mInfoTv.setText("handler post");
						}
					});
					break;

				case R.id.handlersendmsg:					
                    mHandler.sendEmptyMessage(1);                   
					break;

				case R.id.runOnUITrhread:
                    runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							mInfoTv.setText("run On UI Thread!");
						}
					});
					break;

				case R.id.viewpost:
                    mInfoTv.post(new Runnable() {
						
						@Override
						public void run() {
							mInfoTv.setText("View Post!");
						}
					});
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
