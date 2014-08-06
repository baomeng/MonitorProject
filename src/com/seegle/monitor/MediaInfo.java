package com.seegle.monitor;

import android.widget.ImageView;

public class MediaInfo {
    public String    mName;      // ��
    public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public int       mDuration;  // ʱ��
    public String    mPath;      // ·��
    public String    mthumbPath; //
    
	public String getMthumbPath() {
		return mthumbPath;
	}

	public void setMthumbPath(String mthumbPath) {
		this.mthumbPath = mthumbPath;
	}

	private String letter;

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	
}
