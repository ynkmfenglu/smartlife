package com.smart.life.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.smart.life.Parser.HomeMsgParser;
import com.smart.life.R;
import com.smart.life.adapter.HomeMsgAdapter;
import com.smart.life.adapter.HomeTopAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.HomeMsg;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.library.PullToRefreshBase;
import com.smart.life.library.PullToRefreshListView;
import com.smart.life.library.extras.SoundPullEventListener;
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.NetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenglu on 2015/8/3.
 */
public class HomeActivity extends Activity {
    private HomeMsgAdapter homemsgAdapter;
    private PullToRefreshListView mPullRefreshListView;
    public static ArrayList<HomeMsg> current_msgs;
    private AppContext appContext;
    private ListView actualListView;
    ImageView topicImg;
    HomeTopAdapter mPageAdaper;
    private ArrayList<ImageView> mNewsImages;
    private ViewPager mNewsViewPager;
    private ArrayList<ImageView> dotImgViewList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.homemsg_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉Pulling Down
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉的时候数据重置
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask(0, HomeActivity.this).execute();
            }

            // 上拉Pulling Up
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                new GetDataTask(1, HomeActivity.this).execute();
            }
        });

        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
            Toast.makeText(HomeActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        actualListView = mPullRefreshListView.getRefreshableView();
        View headview = LayoutInflater.from(this).inflate(R.layout.main_topic, null);
        getTopView(headview);
        actualListView.addHeaderView(headview);

        appContext = (AppContext) getApplicationContext();
        current_msgs = appContext.getHomeMsgs();
        HttpRequests req = new HttpRequests();
        req.nrc = new RequestConstant();
        // post请求
        req.nrc.setType(HttpRequests.HttpRequestType.POST);
        RequestConstant.requestUrl = UrlConstant.HOMEURL;
        RequestConstant.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        if ((current_msgs != null) && (current_msgs.size() != 0)) {
            map.put("date", current_msgs.get(0).getDate());
        } else {
            map.put("date", "");
        }
        map.put("type", "down");
        RequestConstant.map = map;
        req.context = this;
        req.getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
            if (res != null) {
                try {
                    HomeMsgParser homemsgParer = new HomeMsgParser();
                    ArrayList<HomeMsg> homemsgs = homemsgParer.getHomemsgs(res);
                    if (homemsgs != null && !homemsgs.isEmpty()) {
                        if (current_msgs != null) {
                            current_msgs.addAll(0, homemsgs);
                        } else {
                            current_msgs = homemsgs;
                            appContext.setHomeMsgs(current_msgs);
                        }
                    }

                    homemsgAdapter = new HomeMsgAdapter(HomeActivity.this, current_msgs);
                    actualListView.setAdapter(homemsgAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
        }, req.nrc);

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        actualListView.setAdapter(homemsgAdapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, ArrayList<HomeMsg>> {
        private int updown;
        private Context context;

        public GetDataTask(int refresh_type, Context context){
            this.updown = refresh_type;
            this.context = context;
        }
        @Override
        protected ArrayList<HomeMsg> doInBackground(Void... params) {
            // Simulates a background job.
            ArrayList<HomeMsg> homemsgs = null;
            try {
                RequestConstant nrc = new RequestConstant();
                nrc.setType(HttpRequests.HttpRequestType.POST);
                RequestConstant.requestUrl = UrlConstant.HOMEURL;
                Map<String, Object> map = new HashMap<String, Object>();
                int idx = 0;

                if (updown == 0) {//down
                    if ((current_msgs != null) && (current_msgs.size() != 0)) {
                        map.put("date", current_msgs.get(0).getDate());
                    } else {
                        map.put("date", "");
                    }
                    map.put("type", "down");
                    idx = 0;
                }else {
                    if ((current_msgs != null) && (current_msgs.size() != 0)) {
                        idx = current_msgs.size();
                        map.put("date", current_msgs.get(idx - 1).getDate());
                    } else {
                        map.put("date", "");
                    }
                    map.put("type", "up");
                }
                RequestConstant.map = map;
                String res = null;
                if(NetUtil.isCheckNet(this.context)){
                    res = NetUtil.httpPost(nrc);
                    HomeMsgParser homemsgParer = new HomeMsgParser();
                    homemsgs = homemsgParer.getHomemsgs(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return homemsgs;
        }

        @Override
        protected void onPostExecute(ArrayList<HomeMsg> result) {
            if (result != null && !result.isEmpty()) {
                if (current_msgs != null) {
                    current_msgs.addAll(result);
                } else {
                    current_msgs = result;
                    appContext.setHomeMsgs(current_msgs);
                }
            }
            homemsgAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(current_msgs);
        }
    }

    private void InitDotImg(View parentView, int count) {
        ViewGroup group = (ViewGroup) parentView.findViewById(R.id.viewGroup);
        dotImgViewList = new ArrayList<ImageView>();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(parentView.getContext());
            group.addView(imageView);
            LinearLayout.LayoutParams para;
            para = (LinearLayout.LayoutParams)imageView.getLayoutParams();

            if (i == 0)
                para.leftMargin = 0;
            else
                para.leftMargin = 10;
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            imageView.setLayoutParams(para);
            imageView.setImageResource(R.drawable.page);
            dotImgViewList.add(imageView);
        }
    }

    private void getTopView(View parentView) {
        mNewsViewPager = (ViewPager) parentView.findViewById(R.id.vPager);
        mNewsImages = new ArrayList<ImageView>();
        for (int i = 0; i < 5; i++) {
            topicImg = new ImageView(this);
            topicImg.setBackgroundColor(Color.WHITE);
            topicImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            topicImg.setImageResource(R.drawable.ic_phone);
            mNewsImages.add(topicImg);
        }
        mPageAdaper = new HomeTopAdapter(mNewsImages);
        mNewsViewPager.setAdapter(mPageAdaper);
        mNewsViewPager.setCurrentItem(0);

        InitDotImg(parentView, mNewsImages.size());
        dotImgViewList.get(0).setImageDrawable(getResources().getDrawable(R.drawable.page_now));

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (dotImgViewList.size() > 1) {
                    if (position == 0) {
                        dotImgViewList.get(position).setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                        dotImgViewList.get(position + 1).setImageDrawable(getResources().getDrawable(R.drawable.page));
                    } else if (position == dotImgViewList.size() - 1) {
                        dotImgViewList.get(position).setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                        dotImgViewList.get(position - 1).setImageDrawable(getResources().getDrawable(R.drawable.page));
                    } else {
                        dotImgViewList.get(position - 1).setImageDrawable(getResources().getDrawable(R.drawable.page));
                        dotImgViewList.get(position).setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                        dotImgViewList.get(position + 1).setImageDrawable(getResources().getDrawable(R.drawable.page));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

        };
        mNewsViewPager.setOnPageChangeListener(pageChangeListener);
    }
}
