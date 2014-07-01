package com.seegle.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class activity1 extends Activity{
	
	private static final int RESULT_CODE = 1;
	
	private   OnClickListener listener1 = null;
	protected Button          mBtn      = null;

	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		mBtn = (Button) findViewById(R.id.button1);
		
		listener1 = new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(activity1.this, activity2.class);
				intent1.putExtra("activity1", "#这是来自activity1的数据#");
				startActivityForResult(intent1, RESULT_CODE);
			}
		};
		mBtn.setOnClickListener(listener1);
		setTitle("Activity1"); // 设置标题
	}

	//@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_CODE) {
			if (resultCode == RESULT_CANCELED) {
				setTitle("取消");
			}
			else if (resultCode == RESULT_OK) {
				String tmp = null;
				Bundle extras = data.getExtras();
				if(extras != null){
					tmp = extras.getString("activity2");
				}
				if(tmp.isEmpty()){
					setTitle("Activity1");
				}else{
					setTitle(tmp);
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
