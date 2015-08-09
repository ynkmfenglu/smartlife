package com.smart.life.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import com.smart.life.threads.ThreadPool;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class ImageUtils {

	private final static int CAPACITY = 10;

	//��Ӧ�ú�ӲӦ�ö�����������ͼƬ�ģ����ӲӦ�ô洢�ռ������Ļ����ٷŵ���Ӧ���У������Ӧ�ó��ڲ��õĻ����ͻ�����һ����ʼ�Զ�ɾ��

	// ������
	static ConcurrentHashMap<String, SoftReference<Bitmap>> mSoftCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
			CAPACITY);
	// ӲӦ��
	static HashMap<String, Bitmap> mHardCache = new LinkedHashMap<String, Bitmap>(
			CAPACITY, 0.75f, true) {
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(
				java.util.Map.Entry<String, Bitmap> eldest) {
			// ������ӲӦ��������������Ӧ����
			if (mHardCache.size() > CAPACITY) {
				mSoftCache.put(eldest.getKey(), new SoftReference<Bitmap>(
						eldest.getValue()));
				return true;
			}
			return false;
		};
	};

	// �õ�������SD��·��
	public static String getSDImgUrl(String imgUrl) {
		String imgSDUrl = Environment.getExternalStorageDirectory()
				+ "/pictures/";
		int index = imgUrl.lastIndexOf(".");
		//�ļ���׺��
		String lastName = imgUrl.substring(index);

		//final String sdImgUrl  = (imgSDUrl == null) ? md5(imgUrl).concat(lastName) : imgSDUrl + md5(imgUrl).concat(lastName);
		String sdImgUrl=null;
		//����
		if(!(imgSDUrl==null)){
			sdImgUrl= imgSDUrl + md5(imgUrl).concat(lastName);
		}
		return sdImgUrl;
	}

	// ��ȡͼƬ�ĺ��������ȼ���1.�ȴӻ������ã�2.��SD�����ã�3.���������ã�
	public static Bitmap loadImage( final String imgUrl,  final int position,
			final ImageCallBack callBack) {
		// http://localhost:8080/music/abc.jpg
		if (imgUrl == null || imgUrl.lastIndexOf(".") < 0
				|| "".equals(imgUrl.trim())) {
			return null;
		}
		System.out.println("imgUrl"+imgUrl);
		//�ӻ�������
		Bitmap bitmap1 = getBitmapFromCache(ImageUtils.getSDImgUrl(imgUrl));
		if (bitmap1 != null) {
			System.out.println("bitmap1"+bitmap1);
			return bitmap1;
		} else {
			//��SD������
			Bitmap bitmap2 = getBitmapFromSDcard(ImageUtils.getSDImgUrl(imgUrl));
			if (bitmap2 != null) {
				System.out.println("bitmap2"+bitmap2);
				return bitmap2;
			} else {
				
				 final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							super.handleMessage(msg);
							Bitmap bitmap3 = (Bitmap) msg.obj;
							System.out.println("�ӿڻص�����bitmap3"+bitmap3);
							if (bitmap3 != null) {
								// �ӿڻص�����
								callBack.getBitmap(bitmap3, position);
							}
						}
					}; 
					
					
				 Runnable runnable = new Runnable() {
					 
				 InputStream in=null;
					public void run() {
						// TODO Auto-generated method stub
						try {
							URL url = new URL(imgUrl);
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(5000);
							conn.connect();
							in = conn.getInputStream();
							Bitmap bitmap = BitmapFactory.decodeStream(in);
						
							if (bitmap != null) {
								// 1.��Bitmap��handler���� ��ȥ
								Message msg = new Message();
								msg.obj = bitmap;
								handler.sendMessage(msg);
								// 2.��Bitmap���뻺����
								ImageUtils
										.saveBitmapInCacheFromInternet(bitmap, imgUrl);
								// 3.��Bitmap����SD����
								ImageUtils.saveBitmapInSDcardFromInternet(bitmap,
										imgUrl);
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try {
								in.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				// �����߳�
				ThreadPool.getInstance().addTask(runnable);
			}
		}
		return null;
	}

	// �ӻ�������ͼƬ
	public static Bitmap getBitmapFromCache(String imgUrl) {

		synchronized (mHardCache) {
			// ���ӲӦ�û��пռ䣬�ȴ�ӲӦ������
			if (mHardCache.size() <= 10) {
				synchronized (imgUrl) {
					if (imgUrl != null) {
						Bitmap bitmap = mHardCache.get(imgUrl);
						if (bitmap != null) {
							return bitmap;
						}
					}
				}
			} else {// ӲӦ������������Ӧ����

				// ��ȡ�����е������ö����ֵ
				SoftReference<Bitmap> reference = mSoftCache.get(imgUrl);

				if (reference != null) {
					Bitmap bitmap = reference.get();
					if (bitmap != null) {
						return bitmap;
					} else {
						// ���bitmapΪ�յĻ� ���Ǳ�ϵͳ���յ���
						mSoftCache.remove(imgUrl);
					}
				}
			}
		}
		return null;
	}

	//��SD������ͼƬ
	public static Bitmap getBitmapFromSDcard(String imgUrl) {
		File file = new File(imgUrl);

		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			if (bitmap != null) {
				return bitmap;
			}
		}
		return null;
	}

	
	// ������������ͼƬ�����̣߳���handler��bitmap����
	public static Bitmap getBitmapFromInternet(final String imgUrl,
			final int position, final ImageCallBack callBack) {
		return null;
	}

	
	
	// �Ѵ����������ص�ͼƬ�����ڻ�����
	public static void saveBitmapInCacheFromInternet(Bitmap bitmap,
			String imgUrl) {
		// ���ӲӦ�ÿռ����������Զ��ص�������Ӧ����
		synchronized (mHardCache) {
			if (bitmap != null) {
				mHardCache.put(imgUrl, bitmap);
			}
		}
	}

	// �Ѵ����������ص�ͼƬ������SD����
	public static void saveBitmapInSDcardFromInternet(Bitmap bitmap,
			String imgUrl) throws IOException {
		File f = new File(imgUrl);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imgUrl);
			boolean result = bitmap.compress(CompressFormat.JPEG, 100, fos);
			if (result) {
				fos.flush();
				fos.close();
			} else {
				if (f.exists()) {
					f.delete();
				}
			}
		}
	}

	/**
	 * ͼƬ�ص��ӿ�
	 * 
	 * @author Administrator
	 */
	public interface ImageCallBack {
		void getBitmap(Bitmap bitmap, int position);
	}

	/**
	 * ��ָ��byte����ת����16�����ַ���
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * md5 ����
	 * 
	 * @param paramString
	 * @return
	 */
	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}
	}

}
