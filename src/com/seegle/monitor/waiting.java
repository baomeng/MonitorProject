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

		
		 // 设置单击按钮时打开文件对话框  
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
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹  
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标  
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_root);    //返回上一层的图标  
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_root);   //文件夹图标  
            images.put("wav", R.drawable.filedialog_root);   //wav文件图标  
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {  
                @Override  
                public void callback(Bundle bundle) {  
                    String filepath = bundle.getString("path");  
                    setTitle(filepath); // 把文件路径显示在标题上  
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
