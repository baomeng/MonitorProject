package com.seegle.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.seegle.monitor.MyListView.OnRefreshListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class VideoPlayActivity extends BaseActivity {

	List<MediaInfo> mVideolistSD = new ArrayList<MediaInfo>();

	private List<MediaInfo> getVideoDataFromSD() {

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
			
			map.put("path", mVideolistSD.get(i).mPath);
			map.put("title", "名称：" + mVideolistSD.get(i).mName);
			
			int iDuration = mVideolistSD.get(i).mDuration;
			String strDuration = Util.millisTimeToDotFormat(iDuration, false, false);
			map.put("info", "时长：" + strDuration);
			
			if (fileIsExists(mVideolistSD.get(i).mPath))
				map.put("img",getVideoThumbnail(mVideolistSD.get(i).mPath, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
			
			list.add(map);
			
		}
		return list;
	}

	private List<HashMap<String, Object>> data;
	MyListView mListView;
	private Button button;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thumbnail);

		mListView = (MyListView) findViewById(R.id.listView1);
		
		getVideoDataFromSD();
		data = getListViewVideoData();
		
//		mListView.setAdapter(new BaseListAdapter(this));
		
		List<HashMap<String, Object>> mListData = getListViewVideoData();
		SimpleAdapter adapter = new SimpleAdapter(this, mListData,
				R.layout.imageview, new String[] { "title", "info", "img" },
				new int[] { R.id.textView1, R.id.textView2, R.id.imageView1 });

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
				// Toast.makeText(AndroidTestActivity.this, String.format("%d",
				// arg2), Toast.LENGTH_LONG).show();

				ListView listView = (ListView) arg0;
				HashMap<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				Object obj = map.get("path");
				// Toast.makeText(VideoPlayActivity.this,
				// obj.toString(),Toast.LENGTH_LONG).show();

				if (fileIsExists(obj.toString())) {
					Intent intent = new Intent(VideoPlayActivity.this,
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
				Intent intent = new Intent(VideoPlayActivity.this, login.class);
				startActivity(intent);
			}
		});

	}

    private class BaseListAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater inflater;
        
        public BaseListAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }
        
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.imageview, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
                viewHolder.title = (TextView) convertView.findViewById(R.id.textView1);
                viewHolder.info = (TextView) convertView.findViewById(R.id.textView2);
                
                convertView.setTag(viewHolder);
                
            } else {
            	
                viewHolder = (ViewHolder) convertView.getTag();
                
            }
            
//            System.out.println("viewHolder = " + viewHolder);
            
            System.out.println("viewHolder.imageView = " + (Bitmap) getListViewVideoData().get(position).get("img"));
            System.out.println("viewHolder.title = " + (CharSequence) getListViewVideoData().get(position).get("title"));
            System.out.println("viewHolder.info = " + (CharSequence) getListViewVideoData().get(position).get("info"));
            
            viewHolder.imageView.setImageBitmap((Bitmap) data.get(position).get("img"));           
            viewHolder.title.setText((CharSequence) data.get(position).get("title"));
            viewHolder.info.setText((CharSequence) data.get(position).get("info"));
            
            return convertView;
        }
       
        class ViewHolder {
            ImageView imageView;
            TextView  title;
            TextView  info;
        }

//        private void showInfo() {
//            new AlertDialog.Builder(TestBaseAdapter.this).setTitle("my listview").setMessage("introduce....").
//            setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // TODO Auto-generated method stub
//                    
//                }
//            }).show();
//        }
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

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
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
}