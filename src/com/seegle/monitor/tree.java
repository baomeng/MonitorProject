package com.seegle.monitor;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class tree extends Activity{
	
	private AnalogClock listView;
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tree);
		
		listView = (AnalogClock) findViewById(R.id.analogClock1);
//		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));

//		listView.setOnItemClickListener(new ListView.OnItemClickListener()  
//		{
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				Toast.makeText(listview.this, String.format("%d", arg2), Toast.LENGTH_LONG).show();
//			}
//		});  
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
