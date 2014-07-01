package com.seegle.monitor;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
	private TabHost tabHost;
	private TextView main_tab_new_message;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
        main_tab_new_message.setVisibility(View.VISIBLE);
        main_tab_new_message.setText("10");
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, AudioPlayActivity.class);
        spec=tabHost.newTabSpec("��Ƶ").setIndicator("��Ƶ").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, VideoPlayActivity.class);
        spec=tabHost.newTabSpec("��Ƶ").setIndicator("��Ƶ").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, TestAdapter.class);
        spec=tabHost.newTabSpec("�ҵ�֪ͨ").setIndicator("�ҵ�֪ͨ").setContent(intent);
        tabHost.addTab(spec);
        
     
        intent=new Intent().setClass(this, SettingActivity.class);
        spec=tabHost.newTabSpec("����").setIndicator("����").setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(1);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_addExam://��ӿ���
					tabHost.setCurrentTabByTag("��Ƶ");
					break;
				case R.id.main_tab_myExam://�ҵĿ���
					tabHost.setCurrentTabByTag("��Ƶ");
					break;
				case R.id.main_tab_message://�ҵ�֪ͨ
					tabHost.setCurrentTabByTag("�ҵ�֪ͨ");
					break;
				case R.id.main_tab_settings://����
					tabHost.setCurrentTabByTag("����");
					break;
				default:
					//tabHost.setCurrentTabByTag("�ҵĿ���");
					break;
				}
			}
		});
    }
    
   
}
