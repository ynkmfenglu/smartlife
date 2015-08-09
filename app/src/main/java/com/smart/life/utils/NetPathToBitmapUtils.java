package com.smart.life.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetPathToBitmapUtils {
    private String netPath;

	public NetPathToBitmapUtils(String netPath) {
		super();
		this.netPath = netPath;
	}
     
	public void run() {
		// �������ַת��ΪBitMap
		URL picUrl; 
		try {
			picUrl = new URL(netPath);
			System.out.println("����ͼƬ��ַ"+picUrl);
		    Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
