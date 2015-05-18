package com.syh.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class ThirdActivity extends Activity {

	private HandlerThread mThread;

	private Handler mHandler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main);
		// 解决了handler调用线程的looper的时候(初始化),looper未初始化的问题（handlerthread
		// 加锁了，确保线程运行时looper不为空）
		mThread = new HandlerThread("handler thread");
		mThread.start();

		mHandler = new Handler(mThread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				System.out.println("currentThread----->"
						+ Thread.currentThread());
			}
		};

		mHandler.sendEmptyMessage(1);
	}
}
