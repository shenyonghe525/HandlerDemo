package com.syh.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView mAdImageView;
	private TextView mTitleView;
	private Button mChangeBtn, mStopBtn;

	private Handler mHandlerPost = new Handler();

	private Handler mHandlerSend = new Handler(new Handler.Callback() {

		// 截获消息，并处理，当返回true时消息将不会传递
		public boolean handleMessage(Message msg) {
			Toast.makeText(getBaseContext(), "Callback!", 1).show();
			return true;
		}
	}) {
		public void handleMessage(Message msg) {
			Toast.makeText(getBaseContext(), "normal!", 1).show();
		}
	};

	private int mImages[] = { R.drawable.image1, R.drawable.image2,
			R.drawable.image3 };

	private int mIndex;

	private MyRunnable myRunnable = new MyRunnable();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		mHandlerPost.postDelayed(myRunnable, 1000);
	}

	private void initViews() {
		mAdImageView = (ImageView) findViewById(R.id.imageView1);
		mTitleView = (TextView) findViewById(R.id.tv_info);
		mChangeBtn = (Button) findViewById(R.id.send);
		mStopBtn = (Button) findViewById(R.id.stop_btn);
		mChangeBtn.setOnClickListener(this);
		mStopBtn.setOnClickListener(this);
	}

	class MyRunnable implements Runnable {

		public void run() {
			mIndex++;
			mIndex = mIndex % 3;
			mAdImageView.setImageResource(mImages[mIndex]);
			mHandlerPost.postDelayed(myRunnable, 1000);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			new Thread(new Runnable() {

				public void run() {
					// Message message = new Message();
					Message message = mHandlerSend.obtainMessage();
					message.what = 1;
					message.arg1 = 90;
					message.sendToTarget();
					// mHandlerSend.sendMessage(message);
				}
			}).start();
			break;

		case R.id.stop_btn:
			mHandlerPost.removeCallbacks(myRunnable);
			break;
		}
	}

}
