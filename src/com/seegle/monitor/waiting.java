package com.seegle.monitor;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class waiting extends Activity{
	
	static private int openfileDialogId = 0;  
	
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filedialogitem);

		
		 // ���õ�����ťʱ���ļ��Ի���  
		findViewById(R.id.filedialogitem_img).setOnClickListener(new OnClickListener() {   
            public void onClick(View arg0) {  
                showDialog(openfileDialogId);  
            }  
        });  
	
	}
	 @Override  
    protected Dialog onCreateDialog(int id) {  
        if(id==openfileDialogId){  
            Map<String, Integer> images = new HashMap<String, Integer>();  
            // ���漸�����ø��ļ����͵�ͼ�꣬ ��Ҫ���Ȱ�ͼ����ӵ���Դ�ļ���  
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // ��Ŀ¼ͼ��  
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_root);    //������һ���ͼ��  
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_root);   //�ļ���ͼ��  
            images.put("wav", R.drawable.filedialog_root);   //wav�ļ�ͼ��  
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
            Dialog dialog = OpenFileDialog.createDialog(id, this, "���ļ�", new CallbackBundle() {  
                @Override  
                public void callback(Bundle bundle) {  
                    String filepath = bundle.getString("path");  
                    setTitle(filepath); // ���ļ�·����ʾ�ڱ�����  
                }  
            },   
            ".wav;",  
            images);  
            return dialog;  
        }  
        return null;  
    } 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
