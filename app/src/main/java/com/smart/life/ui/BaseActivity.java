package com.smart.life.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.smart.life.common.RequestConstant;
import com.smart.life.threads.ThreadPool;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.NetUtil;

/**
 * 如果要请求网络的话，就继承BaseActivity，其余的不用
 * @author hp
 */
public abstract class BaseActivity extends Activity {
	
	private RequestConstant nrc;
	private Handler handler;
	public static final int SUCCESS = 10001; 
	public static final int FAIL = 10002; 
	public static final int ERROR = 10003; 

	abstract void init();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
	}
	
	class RunnableTask implements Runnable{
		
		private RequestConstant nrc;
		private Handler handler;
		
		public RunnableTask(RequestConstant nrc , Handler handler) {
			this.nrc = nrc;
			this.handler = handler;
		}

		
		public void run() {
			String res=null;
            if(NetUtil.isCheckNet(getApplicationContext())){
            	if(nrc.getType()== HttpRequests.HttpRequestType.POST){//Post请求
            		res = NetUtil.httpPost(nrc);
				}else if(nrc.getType()== HttpRequests.HttpRequestType.GET){//get请求
				    res = NetUtil.httpGet(nrc);
				}
				Message message = Message.obtain();
				message.obj = res;
				message.what = SUCCESS;
				handler.sendMessage(message);
				
			}else{
				Message message = Message.obtain();
				message.what = ERROR;
				handler.sendMessage(message);
			}
		}
	}
	
	class BaseHandler extends Handler{

		private Netcallback callBack;
		
		
		public BaseHandler(Netcallback callBack) {
			this.callBack = callBack;
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case SUCCESS:// 网络请求成功后回调
				callBack.preccess(msg.obj, true);
				
				break;
				
			case FAIL:// 网络请求失败后回调
				
			case ERROR:
				callBack.preccess(msg.obj, false);
				break;

			default:
				break;
			}
			
			super.handleMessage(msg);
		}
		
	}
	
	protected void getServer(Netcallback callBack ,RequestConstant nrc){
		
		Handler handler = new BaseHandler(callBack);
		RunnableTask task = new RunnableTask(nrc, handler); 
		ThreadPool.getInstance().addTask(task);
		
	}
}
