package com.syh.handlerdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class SecondActivity extends Activity {

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			System.out.println("mainThread---->" + Thread.currentThread());
		};
	};

	private MyThread myThread = new MyThread();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main);
		myThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myThread.handler.sendEmptyMessage(1);
		mHandler.sendEmptyMessage(1);
	}

	class MyThread extends Thread {

		public Handler handler;

		public void run() {
			Looper.prepare();

			handler = new Handler() {

				public void handleMessage(Message msg) {
					System.out
							.println("currentThread" + Thread.currentThread());
				}

			};

			Looper.loop();
		}
	}

}
