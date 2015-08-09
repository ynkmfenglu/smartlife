package com.smart.life.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.smart.life.common.RequestConstant;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.threads.ThreadPool;

/**
 * Created by fenglu on 2015/7/27.
 */
public class HttpRequests {
    public RequestConstant nrc;
    public Context context;
    public static final int SUCCESS = 10001;
    public static final int FAIL = 10002;
    public static final int ERROR = 10003;

    public enum HttpRequestType{
        GET,POST;
    }

    class RunnableTask implements Runnable{

        public RequestConstant nrc;
        private Handler handler;

        public RunnableTask(RequestConstant nrc , Handler handler) {
            this.nrc = nrc;
            this.handler = handler;
        }

        public void run() {
            String res=null;
            if(NetUtil.isCheckNet(context)){
                if(nrc.getType()==HttpRequestType.POST){//Post方法
                    res = NetUtil.httpPost(nrc);
                }else if(nrc.getType()==HttpRequestType.GET){//get方法
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
                case SUCCESS:// 请求成功
                    callBack.preccess(msg.obj, true);
                    break;
                case FAIL:// 请求失败
                case ERROR:
                    callBack.preccess(msg.obj, false);
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }

    }

    public void getServer(Netcallback callBack ,RequestConstant nrc){
        Handler handler = new BaseHandler(callBack);
        RunnableTask task = new RunnableTask(nrc, handler);
        ThreadPool.getInstance().addTask(task);
    }
}
