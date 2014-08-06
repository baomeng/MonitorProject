package com.seegle.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seegle.monitor.R;

public class TestAdapter extends ActionBarActivity {
	
	@SuppressLint("NewApi")
	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;		
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
//		System.out.println("w" + bitmap.getWidth());
//		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	
	public boolean fileIsExists(String videoPath) {
		File f = new File(videoPath);
		if (!f.exists()) {
			return false;
		}
		return true;
	}
	
	List<MediaInfo> mVideolistSD = new ArrayList<MediaInfo>();
	private List<MediaInfo> getVideoFromSD() {

		String[] projection = new String[] { 
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE, 
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA };

		Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);

		if (cursor != null) {
			cursor.moveToFirst();

			int nameIndex = cursor.getColumnIndex(MediaStore.Video.Media.TITLE);
			int durationIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
			int pathIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);

			int VideoSum = cursor.getCount();
			for (int counter = 0; counter < VideoSum; counter++) {
				MediaInfo data = new MediaInfo();
				data.mName = cursor.getString(nameIndex); //
				data.mDuration = cursor.getInt(durationIndex); //
				data.mPath = cursor.getString(pathIndex); //
				mVideolistSD.add(data);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return mVideolistSD;
	}
	
	public List<HashMap<String, Object>> getListViewVideoData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		int i;
		for (i = 0; i < mVideolistSD.size(); i++) {
			
			map = new HashMap<String, Object>();
			map.put("title", mVideolistSD.get(i).mName);
			
			int iDuration = mVideolistSD.get(i).mDuration;
			String strDuration = Util.millisTimeToDotFormat(iDuration, false, false);
			map.put("duration", strDuration);

			map.put("path",  mVideolistSD.get(i).mPath);
			
			if (fileIsExists(mVideolistSD.get(i).mPath))
				map.put("img",getVideoThumbnail(mVideolistSD.get(i).mPath, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
			
			list.add(map);
			
		}
		return list;
	}
	
	private boolean mLimited;
	private List<HashMap<String, Object>> data;
	ListView mListView;
	private BaseListAdapter mListViewAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.listviewt);
		
		mListView = (ListView) findViewById(R.id.listView1);
		
		getVideoFromSD();
		data = getListViewVideoData();
		
		mListViewAdapter = new BaseListAdapter(this);
		mListView.setAdapter(mListViewAdapter);
		
		setTitle("视频列表");
	}

    static class ViewHolder
    {
    	public String    path;
        public ImageView img;
        public TextView  title;
        public TextView  duration;
        public Button    bt;
    }
    
    private class BaseListAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater inflater;
        
        public BaseListAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }
        
    	public void setLimit(int limit) {
    	}
    	
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
        	return position;
        }

        @Override
        public long getItemId(int position) {
        	return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.test, null);
               
                viewHolder.title = (TextView) convertView.findViewById(R.id.textView1);
                viewHolder.duration = (TextView) convertView.findViewById(R.id.textView2);
                viewHolder.img = (ImageView) convertView.findViewById(R.id.imageView1);
                viewHolder.bt = (Button) convertView.findViewById(R.id.button1);
                
                convertView.setTag(viewHolder);
                
            } else {
            	
                viewHolder = (ViewHolder) convertView.getTag();
                
            }

            viewHolder.title.setText((CharSequence) data.get(position).get("title"));
            viewHolder.duration.setText((CharSequence) data.get(position).get("duration"));
            viewHolder.img.setImageBitmap((Bitmap) data.get(position).get("img"));  
            viewHolder.path = (String) data.get(position).get("path"); 
            
            final String path;
            path = viewHolder.path;
            viewHolder.bt.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                	                	
        			if (fileIsExists(path)) {
    					Intent intent = new Intent(TestAdapter.this, MusicPlayActivity.class);
    					Bundle bundle = new Bundle();
    					bundle.putString("activity1", path);
    					intent.putExtras(bundle);
    					startActivity(intent);
        			}
                }
            });
                
            return convertView;
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_expandablelistitem, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_expandable_limit:
//			mLimited = !mLimited;
//			item.setChecked(mLimited);
//			mListViewAdapter.setLimit(mLimited ? 2 : 0);
			Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
			return true;
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}