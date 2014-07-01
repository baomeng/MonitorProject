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
        surfaceView.getHolder().setFixedSize(176, 144);//���÷ֱ���
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//����surfaceview��ά���Լ��Ļ����������ǵȴ���Ļ����Ⱦ���潫�������͵��û���ǰ
        surfaceView.getHolder().addCallback(new SurceCallBack());//��surface�����״̬���м���
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
                Toast.makeText(VodeoPlayActivity.this, "sd��������", Toast.LENGTH_SHORT).show();
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
                mediaPlayer.reset();//����Ϊ��ʼ״̬
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//����������������
                mediaPlayer.setDisplay(surfaceView.getHolder());//����videoӰƬ��surfaceviewholder����
 //             mediaPlayer.setDataSource(file.getAbsolutePath());//����·��
                mediaPlayer.setDataSource(filename);//����·��
                mediaPlayer.prepare();//����
                mediaPlayer.start();//����
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
    }
    
    private final class SurceCallBack implements SurfaceHolder.Callback{
        /**
         * �����޸�
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
            // TODO Auto-generated method stub
            
        }

        /**
         * ���洴��
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
         * ��������
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