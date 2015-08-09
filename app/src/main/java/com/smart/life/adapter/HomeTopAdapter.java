package com.smart.life.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by fenglu on 2015/8/5.
 */
public class HomeTopAdapter extends PagerAdapter {
    ArrayList<ImageView> viewLists;

    public HomeTopAdapter(ArrayList<ImageView> lists) {
        viewLists = lists;
    }

    @Override
    public int getCount() {                                                                 //»ñµÃsize
        // TODO Auto-generated method stub
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView(viewLists.get(position));
    }

    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(viewLists.get(position), 0);

        return viewLists.get(position);
    }
}
