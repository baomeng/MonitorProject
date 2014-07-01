package com.seegle.monitor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageViewFromUrl extends FrameLayout {
	
	private ProgressBar mLoading;
	private ImageView mImage;
	private float mDensity = 1f;
	private float mTargetW, mTargetH;
	
	public ImageViewFromUrl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public ImageViewFromUrl(Context context) {
		this(context, null);
	}
	
	public Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private final Handler handler = new Handler();
	
	private void init(Context context){
		// init layout params
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		// loading progress bar
		mLoading = new ProgressBar(context);
		mLoading.setLayoutParams(params);
		mLoading.setProgress(android.R.attr.progressBarStyleSmall);
		// image view to display the bitmap
		mImage = new ImageView(context);
		mImage.setLayoutParams(params);
		
		removeAllViews();
		addView(mLoading);
		addView(mImage);
	}
	
	public void load(final String imageUrl, final int w, final int h) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				successHandle(softReference.get());
				return;
			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap orgBitmap = loadImageFromUrl(imageUrl);
					final Bitmap targetBitmap = changeBitmapSize(orgBitmap, w, h);
					imageCache.put(imageUrl, new SoftReference<Bitmap>(targetBitmap));
					handler.post(new Runnable() {
						public void run() {
							successHandle(targetBitmap);
							return;
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	protected Bitmap changeBitmapSize(Bitmap orgBitmap, int w, int h) {
		mTargetH = h * mDensity;
		mTargetW = w * mDensity;
		
		int orgWidth = orgBitmap.getWidth();
		int orgHeight = orgBitmap.getHeight();

		float scaleWidth = mTargetW / orgWidth;
		float scaleHeight = mTargetH / orgHeight;
		float scale = Math.min(scaleWidth, scaleHeight);
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap resizedBitmap = Bitmap.createBitmap(orgBitmap, 0, 0,
				orgWidth, orgHeight, matrix, true);
		
		return resizedBitmap;
	}

	private void successHandle(final Bitmap bitmap) {
		hide(mLoading);
		mImage.setImageBitmap(bitmap);
		show(mImage);
	}
	
	private Bitmap loadImageFromUrl(String imageUrl) {
		URL url = null;
		InputStream is = null;
		try {
			url = new URL(imageUrl);
			is = url.openStream();
			return BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
			//如果下载图片失败，应该返回一个本地默认的图片..
			//return default bitmap.
		}finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void hide(View v){
		if(v != null)
			v.setVisibility(View.GONE);
	}
	
	private void show(View v){
		if(v != null)
			v.setVisibility(View.VISIBLE);
	}
}
