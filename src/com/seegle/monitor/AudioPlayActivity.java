package com.seegle.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.seegle.monitor.MyListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.SimpleAdapter.ViewBinder;

public class AudioPlayActivity extends BaseActivity {

	List<MediaInfo> mAudiolist = new ArrayList<MediaInfo>();

	private List<MediaInfo> getAudioData() {

		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.DATA };

		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		if (cursor != null) {
			cursor.moveToFirst();

			int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
			int durationIndex = cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION);
			int pathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

			int audioSum = cursor.getCount();
			for (int counter = 0; counter < audioSum; counter++) {
				MediaInfo data = new MediaInfo();
				data.mVideoName = cursor.getString(nameIndex); //
				data.mVideoDuration = cursor.getInt(durationIndex); //
				data.mVideoPath = cursor.getString(pathIndex); //
				mAudiolist.add(data);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return mAudiolist;
	}

	MyListView mListView;
	private Button button;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audioplay);

		getAudioData();

		mListView = (MyListView) findViewById(R.id.listView1);
		List<HashMap<String, Object>> mListData = getListData();
		SimpleAdapter adapter = new SimpleAdapter(this, mListData,
				R.layout.imageview, new String[] { "title", "info"},
				new int[] { R.id.textView1, R.id.textView2 });

		adapter.setViewBinder(new ViewBinder() {

			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// 判断是否为我们要处理的对象
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;

					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});

		mListView.setAdapter(adapter);
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {

						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {

						mListView.onRefreshComplete();

					}

				}.execute();

			}
		});

		mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				ListView listView = (ListView) arg0;
				HashMap<String, Object> Audio = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				Object obj = Audio.get("path");

				if (fileIsExists(obj.toString())) {
					Intent intent = new Intent(AudioPlayActivity.this,
							VodeoPlayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("activity1", obj.toString());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AudioPlayActivity.this, login.class);
				startActivity(intent);
			}
		});

	}

	class MyAdatper extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if (contentView == null) {
				contentView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.myexam_item, null);
			}

			return contentView;
		}

	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
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

	public List<HashMap<String, Object>> getListData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		int i;
		for (i = 0; i < mAudiolist.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("path", mAudiolist.get(i).mVideoPath);
			map.put("title", "名称：" + mAudiolist.get(i).mVideoName);
			int m = mAudiolist.get(i).mVideoDuration;
			String strDuration = Util.millisTimeToDotFormat(m, false, false);
			map.put("info", "时长：" + strDuration);
			list.add(map);
		}
		return list;
	}
}