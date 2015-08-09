package com.smart.life.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.smart.life.R;
import com.smart.life.myview.ScrollSwipeRefreshLayout;

import android.support.v4.widget.SwipeRefreshLayout;
/**
 * Created by fenglu on 2015/8/4.
 */
public class CommunityActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    public WebView webview;
    private ScrollSwipeRefreshLayout swipeLayout;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_activity);

        swipeLayout = (ScrollSwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(R.color.holo_purple,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);

        webview = (WebView)findViewById(R.id.community_pub);
        swipeLayout.setViewGroup(webview);//���ü�����������view
        webview.loadUrl("http://www.sina.com.cn");
        //���javaScript֧��
        webview.getSettings().setJavaScriptEnabled(true);
        //ȡ��������
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //��������������
        webview.requestFocus();
        //������Ӽ����ڵ�ǰbrowser����Ӧ
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //���ý�����
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //���ؽ�����
                    swipeLayout.setRefreshing(false);
                } else {
                    if (!swipeLayout.isRefreshing())
                        swipeLayout.setRefreshing(true);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onRefresh() {
        //����ˢ��ҳ��
        webview.loadUrl(webview.getUrl());
    }
}
