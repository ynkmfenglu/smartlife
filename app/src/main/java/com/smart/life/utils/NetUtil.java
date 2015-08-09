package com.smart.life.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.smart.life.common.RequestConstant;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	//��get��ʽ�������磬������Ӧ�Ľ��
	public static String httpGet(RequestConstant nrc) {
		String result=null;
		try {
			//����HttpGet����
			HttpGet httpRequest=new HttpGet(RequestConstant.requestUrl);

			//����HttpParams������������HTTP����
			HttpParams httpParams=new BasicHttpParams();

			//����һ��HttpClientʵ��
			HttpClient httpClient=new DefaultHttpClient(httpParams);

			//�������󲢵ȴ�
			HttpResponse httpResponse=httpClient.execute(httpRequest);

			//���״̬��Ϊ200��OK��
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//����Ӧ����
				result=EntityUtils.toString(httpResponse.getEntity());
			}else{
				result="�������"+httpResponse.getStatusLine().toString();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	//post����ʽ
	public static String httpPost(RequestConstant nrc){
		String result=null;
		try {
			//����HttpParams������������HTTP����
			HttpParams httpParams=new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);

			//����һ��HttpClientʵ��
			HttpClient httpClient=new DefaultHttpClient(httpParams);

			//����HttpPost����
			HttpPost httpRequest=new HttpPost(RequestConstant.requestUrl);

			//��������Ĳ���
			Map<String, Object> map = RequestConstant.map;

			JSONObject data = new JSONObject();
			try {
				//���û���д���˺ź������ŵ�JSONObject��
				for(Map.Entry<String, Object> entry : map.entrySet()){
					data.put(entry.getKey(), (String) entry.getValue());
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			//�������������������
			httpRequest.setEntity(new StringEntity(data.toString()));

			//�������󲢵ȴ���Ӧ
			HttpResponse httpResponse=httpClient.execute(httpRequest);

			//Logger.e( "NetUtil Code ��" + RequestConstant.requestUrl);
			//״̬��Ϊ200������ɹ���
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//����Ӧ����
				result=EntityUtils.toString(httpResponse.getEntity());
			}else{
				result="�������"+httpResponse.getStatusLine().toString();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isCheckNet(Context context){
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		if(info==null){
			//û������
			return false;
		}else{
			//��������
			//String type=info.getTypeName();
			return true;
		}
	}
}
