package com.seegle.monitor;

import android.app.TabActivity;  
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.TabHost;
import android.widget.TextView;
import com.seegle.monitor.R;
import android.widget.CompoundButton.OnCheckedChangeListener;  
import android.widget.RadioButton;

public class MainActivity extends TabActivity implements OnCheckedChangeListener {
	/** Called when the activity is first created. */
	private TabHost mTabHost;
	private TextView main_tab_new_message;

	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		this.mAIntent = new Intent(this, AudioPlayActivity.class);
		this.mBIntent = new Intent(this, VideoPlayActivity.class);
		this.mCIntent = new Intent(this, TestAdapter.class);
		this.mDIntent = new Intent(this, SettingActivity.class);

		// main_tab_new_message = (TextView)
		// findViewById(R.id.main_tab_new_message);
		// main_tab_new_message.setVisibility(View.VISIBLE);
		// main_tab_new_message.setText("10");
		//
		// tabHost = this.getTabHost();
		// TabHost.TabSpec spec;
		// Intent intent;
		//
		// intent = new Intent().setClass(this, AudioPlayActivity.class);
		// spec =
		// tabHost.newTabSpec("音频").setIndicator("音频").setContent(intent);
		// tabHost.addTab(spec);
		//
		// intent = new Intent().setClass(this, VideoPlayActivity.class);
		// spec =
		// tabHost.newTabSpec("视频").setIndicator("视频").setContent(intent);
		// tabHost.addTab(spec);
		//
		// intent = new Intent().setClass(this, TestAdapter.class);
		// spec = tabHost.newTabSpec("我的通知").setIndicator("我的通知")
		// .setContent(intent);
		// tabHost.addTab(spec);
		//
		// intent = new Intent().setClass(this,
		// ExpandableListItemActivity.class);
		// spec =
		// tabHost.newTabSpec("设置").setIndicator("设置").setContent(intent);
		// tabHost.addTab(spec);
		// tabHost.setCurrentTab(1);

		((RadioButton) findViewById(R.id.radio_button0))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button1))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button2))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button3))
				.setOnCheckedChangeListener(this);

		setupIntent();

		// RadioGroup radioGroup=(RadioGroup)
		// this.findViewById(R.id.main_tab_group);
		// radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// // TODO Auto-generated method stub
		// switch (checkedId) {
		// case R.id.main_tab_addExam:
		// tabHost.setCurrentTabByTag("音频");
		// break;
		// case R.id.main_tab_myExam:
		// tabHost.setCurrentTabByTag("视频");
		// break;
		// case R.id.main_tab_message:
		// tabHost.setCurrentTabByTag("我的通知");
		// break;
		// case R.id.main_tab_settings:
		// tabHost.setCurrentTabByTag("设置");
		// break;
		// default:
		// tabHost.setCurrentTabByTag("视频");
		// break;
		// }
		// }
		// });
	}

	@Override  
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
		if(isChecked){  
			switch (buttonView.getId()) {
			case R.id.radio_button0:
				this.mTabHost.setCurrentTabByTag("A_TAB");
				break;
			case R.id.radio_button1:
				this.mTabHost.setCurrentTabByTag("B_TAB");
				break;
			case R.id.radio_button2:
				this.mTabHost.setCurrentTabByTag("C_TAB");
				break;
			case R.id.radio_button3:
				this.mTabHost.setCurrentTabByTag("D_TAB");
				break;
			}
		}

	}
	
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec("A_TAB", R.string.str_audio, R.drawable.bg_checkbox_icon_menu_0, this.mAIntent));
		localTabHost.addTab(buildTabSpec("B_TAB", R.string.str_video, R.drawable.bg_checkbox_icon_menu_1, this.mBIntent));
		localTabHost.addTab(buildTabSpec("C_TAB", R.string.str_msg, R.drawable.bg_checkbox_icon_menu_2, this.mCIntent));
		localTabHost.addTab(buildTabSpec("D_TAB", R.string.str_set, R.drawable.bg_checkbox_icon_menu_3, this.mDIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}


}
