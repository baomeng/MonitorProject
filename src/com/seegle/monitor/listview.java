package com.seegle.monitor;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class listview extends Activity{
	
	private ListView listView;
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biaoge);
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));

		listView.setOnItemClickListener(new ListView.OnItemClickListener()  
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(listview.this, String.format("%d", arg2), Toast.LENGTH_LONG).show();
			}
		});  
	}
	
	private List<String> getData(){
		List<String> data = new ArrayList<String>();
		data.add("测试数据1");
		data.add("测试数据2");
		data.add("测试数据3");
		data.add("测试数据4");
		return data;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
