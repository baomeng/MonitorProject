package com.seegle.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class activity2 extends Activity {
	
	protected Button               mBtn         	= null;
	OnClickListener listener1 = null;
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		
		mBtn = (Button) findViewById(R.id.button1);
		
		listener1 = new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("activity2", "#这是来自activity2的数据#");
				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		};
		
		mBtn.setOnClickListener(listener1);
		
		// 来自activity1的Intent
		String data = null;
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			data = extras.getString("activity1");
		}
		setTitle("现在是在activity1这里：" + data);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
