package com.seegle.monitor;

import java.io.File;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class VodeoPlayActivity extends Activity {
	
    /** Called when the activity is first created. */
    private MediaPlayer mediaPlayer;
    private String filename;
    private SurfaceView surfaceView;
    private final static String TAG="VodeoPlayActivity";
    private int prosition=0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplay);
        
        surfaceView=(SurfaceView)this.findViewById(R.id.surfaceview);
        surfaceView.getHolder().setFixedSize(176, 144);//设置分辨率
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置surfaceview不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前
        surfaceView.getHolder().addCallback(new SurceCallBack());//对surface对象的状态进行监听
        mediaPlayer=new MediaPlayer();
        
        ButtonOnClikListiner buttonOnClikListinero=new ButtonOnClikListiner();
        ImageButton start=(ImageButton) this.findViewById(R.id.play);
        ImageButton pause=(ImageButton) this.findViewById(R.id.pause);
        ImageButton stop=(ImageButton) this.findViewById(R.id.stop);
        ImageButton replay=(ImageButton) this.findViewById(R.id.reset);
        
        start.setOnClickListener(buttonOnClikListinero);
        pause.setOnClickListener(buttonOnClikListinero);
        stop.setOnClickListener(buttonOnClikListinero);
        replay.setOnClickListener(buttonOnClikListinero);
        
		String data = null;
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			data = extras.getString("activity1");
		}
		filename = data;
		
//		play();
    }
    
    private final class ButtonOnClikListiner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(Environment.getExternalStorageState()==Environment.MEDIA_UNMOUNTED){
                Toast.makeText(VodeoPlayActivity.this, "sd卡不存在", Toast.LENGTH_SHORT).show();
                return;
            }
//            filename=filenamEditText.getText().toString();
            switch (v.getId()) {
            case R.id.play:
                play();
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
                break;
            case R.id.reset:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0);
                }else{
                    play();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                break;
            }
        }  
    }
    
    private void play() {
        try {
 //               File file=new File(Environment.getExternalStorageDirectory(),filename);
                mediaPlayer.reset();//重置为初始状态
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音乐流的类型
                mediaPlayer.setDisplay(surfaceView.getHolder());//设置video影片以surfaceviewholder播放
 //             mediaPlayer.setDataSource(file.getAbsolutePath());//设置路径
                mediaPlayer.setDataSource(filename);//设置路径
                mediaPlayer.prepare();//缓冲
                mediaPlayer.start();//播放
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
    }
    
    private final class SurceCallBack implements SurfaceHolder.Callback{
        /**
         * 画面修改
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
            // TODO Auto-generated method stub
            
        }

        /**
         * 画面创建
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
//            if(prosition>0&&filename!=null){
//                play();
//                mediaPlayer.seekTo(prosition);
//                prosition=0;
//            }
            if(filename!=null){
                play();
                prosition=0;
                mediaPlayer.seekTo(prosition);
            }
        }

        /**
         * 画面销毁
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if(mediaPlayer.isPlaying()){
                prosition=mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }
    }
}