package com.seegle.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class WelComeActivity extends Activity{
	
	private static final long mDelayMillis = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Window win = getWindow();
		// 设置闪屏为全屏显示
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);				
//		ImageView img = (ImageView)findViewById(R.id.logo_text);
//		ImageView logo_img = (ImageView)findViewById(R.id.spaceshipImage);

		// 延时3s显示登录界面
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(WelComeActivity.this,
						login.class);
				startActivity(mainIntent);
				finish();
			}
		}, mDelayMillis);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
