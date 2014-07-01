package com.seegle.monitor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class login extends BaseActivity{
	
	private   Toast          mToast             = null;
	protected Button         mBtnlogin         	= null;
//	private   ImageButton    userSelectImgBtn   = null;
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	    final ClearEditText username = (ClearEditText) findViewById(R.id.username);  
	    final ClearEditText password = (ClearEditText) findViewById(R.id.password);  
	    
	    // test
	    username.append("aa");
	    password.append("aa");
	    
	   ((Button) findViewById(R.id.login)).setOnClickListener(new OnClickListener() {  
	              
            @Override  
            public void onClick(View v) {  
                if(TextUtils.isEmpty(username.getText())){  
                    //设置晃动  
                    username.setShakeAnimation();  
                    //设置提示  
                    showToast("用户名不能为空");  
                    return;  
                }  
                  
                if(TextUtils.isEmpty(password.getText())){  
                    password.setShakeAnimation();  
                    showToast("密码不能为空");  
                    return;  
                } 
                
                Intent intent1 = new Intent(login.this, MainActivity.class);
				startActivity(intent1);
            }  
	     });
	   
//	  ((ImageButton) findViewById(R.id.userAccountSelect)).setOnClickListener(new OnClickListener(){
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			
//		}
//	  
//	  });
	  
	  
	}
	
	 /** 
     * 显示Toast消息 
     * @param msg 
     */  
    private void showToast(String msg){  
        if(mToast == null){  
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);  
        }else{  
            mToast.setText(msg);  
        }  
        mToast.show();  
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
