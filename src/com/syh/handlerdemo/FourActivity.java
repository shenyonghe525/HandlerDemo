package com.syh.handlerdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 主线程给子线程发送消息
 * 
 * @author shenyonghe
 * 
 *         2015-5-18
 */
@SuppressLint("HandlerLeak")
public class FourActivity extends Activity implements OnClickListener {

	private boolean stop = false;

	// 主线程的handler
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (!stop) {
				System.out.println("<----mainHandler---->");
				// 主线程给子线程发消息
				Message message = new Message();
				threadHandler.sendMessageDelayed(message, 1000);
			}
		};
	};

	// 子线程的handler
	private Handler threadHandler;

	private Button mSendBtn, mStopBtn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.four);

		HandlerThread thread = new HandlerThread("HandlerThread");
		thread.start();

		// 子线程的handler关联HandlerThread
		threadHandler = new Handler(thread.getLooper()) {
			public void handleMessage(Message msg) {
				System.out.println("<----threadHandler---->");
				// 子线程给主线程发消息
				Message message = new Message();
				mHandler.sendMessageDelayed(message, 1000);
			};
		};

		mSendBtn = (Button) findViewById(R.id.send);
		mStopBtn = (Button) findViewById(R.id.stop);
		mSendBtn.setOnClickListener(this);
		mStopBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			stop = false;
			mHandler.sendEmptyMessage(1);
			break;

		case R.id.stop:
			stop = true;
			break;
		}
	}
}
