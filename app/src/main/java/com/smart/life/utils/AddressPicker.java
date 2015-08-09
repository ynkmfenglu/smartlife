package com.smart.life.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.life.R;
import com.smart.life.myview.wheel.OnWheelChangedListener;
import com.smart.life.myview.wheel.WheelView;
import com.smart.life.myview.wheel.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 360 on 2015/8/8.
 */
public class AddressPicker implements OnWheelChangedListener {
    private JSONArray address_info;
    private JSONArray[] cur_array;
    private Activity activity;
    private LinearLayout addrLayout;
    private AlertDialog ad;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private WheelView mViewStreet;
    private WheelView mViewCommunity;
    private WheelView mViewAddr;
    private ArrayList<String> province_list;
    private ArrayList<String> city_list;
    private ArrayList<String> district_list;
    private ArrayList<String> street_list;
    private ArrayList<String> community_list;
    private ArrayList<String> addr_list;
    private String addr;

    public AlertDialog addrPickDialog(final TextView inputDate) {
        addrLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.addr_picker, null);
        setUpViews();
        setUpListener();
        setUpData();
        ad = new AlertDialog.Builder(activity)
                .setTitle("地址选择")
                .setView(addrLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        addr = province_list.get(mViewProvince.getCurrentItem()) + "." + city_list.get(mViewCity.getCurrentItem()) + "." +
                            district_list.get(mViewDistrict.getCurrentItem()) + "." + street_list.get(mViewStreet.getCurrentItem()) + "." +
                            community_list.get(mViewCommunity.getCurrentItem());
                        inputDate.setText(addr);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText("");
                    }
                }).show();
        return ad;
    }

    public AddressPicker(JSONArray info, Activity a) {
        address_info = info;
        cur_array = new JSONArray[5];
        activity = a;
    }

    private void setUpViews() {
        mViewProvince = (WheelView) addrLayout.findViewById(R.id.id_province);
        mViewCity = (WheelView) addrLayout.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) addrLayout.findViewById(R.id.id_district);
        mViewStreet = (WheelView) addrLayout.findViewById(R.id.id_street);
        mViewCommunity = (WheelView) addrLayout.findViewById(R.id.id_community);
        mViewAddr = (WheelView) addrLayout.findViewById(R.id.id_addr);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加change事件
        mViewStreet.addChangingListener(this);
        // 添加change事件
        mViewCommunity.addChangingListener(this);
        // 添加change事件
        mViewAddr.addChangingListener(this);
    }

    private void setUpData() {
        String provinces[];
        province_list = getAddrInfoItems(0, null);
        provinces = (String[])province_list.toArray(new String[province_list.size()]);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, provinces));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);
        mViewDistrict.setVisibleItems(5);
        mViewStreet.setVisibleItems(5);
        mViewCommunity.setVisibleItems(5);
        mViewAddr.setVisibleItems(5);
        updateCities();
        updateDistrict();
        updateStreet();
        updateCommunity();
        updateAddress();
    }

    private void updateAddress() {
        int pCurrent = mViewCommunity.getCurrentItem();
        if (pCurrent >= community_list.size())
            pCurrent = 0;
        String pname = community_list.get(pCurrent);
        setAddrInfoObj(4, pname);
        String address[];
        addr_list = getAddrInfoItems(5, pname);
        address = (String [])addr_list.toArray(new String[addr_list.size()]);
        mViewAddr.setViewAdapter(new ArrayWheelAdapter<String>(activity, address));
    }

    private void updateCommunity() {
        int pCurrent = mViewStreet.getCurrentItem();
        if (pCurrent >= street_list.size())
            pCurrent = 0;
        String pname = street_list.get(pCurrent);
        setAddrInfoObj(3, pname);
        String communities[];
        community_list = getAddrInfoItems(4, pname);
        communities = (String [])community_list.toArray(new String[community_list.size()]);
        mViewCommunity.setViewAdapter(new ArrayWheelAdapter<String>(activity, communities));
    }

    private void updateStreet() {
        int pCurrent = mViewDistrict.getCurrentItem();
        if (pCurrent >= district_list.size())
            pCurrent = 0;
        String pname = district_list.get(pCurrent);
        setAddrInfoObj(2, pname);
        String streets[];
        street_list = getAddrInfoItems(3, pname);
        streets = (String [])street_list.toArray(new String[street_list.size()]);
        mViewStreet.setViewAdapter(new ArrayWheelAdapter<String>(activity, streets));
    }

    private void updateDistrict() {
        int pCurrent = mViewCity.getCurrentItem();
        if (pCurrent >= city_list.size())
            pCurrent = 0;
        String pname = city_list.get(pCurrent);
        setAddrInfoObj(1, pname);
        String districts[];
        district_list = getAddrInfoItems(2, pname);
        districts = (String [])district_list.toArray(new String[district_list.size()]);
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, districts));
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        if (pCurrent >= province_list.size())
            pCurrent = 0;
        String pname = province_list.get(pCurrent);
        setAddrInfoObj(0, pname);
        String cities[];
        city_list = getAddrInfoItems(1, pname);
        cities = (String [])city_list.toArray(new String[city_list.size()]);
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
            updateDistrict();
            updateStreet();
            updateCommunity();
            updateAddress();
        } else if (wheel == mViewCity) {
            updateDistrict();
            updateStreet();
            updateCommunity();
            updateAddress();
        } else if (wheel == mViewDistrict) {
            updateStreet();
            updateCommunity();
            updateAddress();
        } else if (wheel == mViewStreet) {
            updateCommunity();
            updateAddress();
        } else if (wheel == mViewCommunity) {
            updateAddress();
        }
    }
    public ArrayList<String> getAddrInfoItems(int level, String name) {
        ArrayList<String> items = new ArrayList<String>();
        JSONArray array;
        try {
            if (level == 0) {
                array = address_info;
            } else {
                array = cur_array[level - 1];
            }

            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                items.add(item.keys().next());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void setAddrInfoObj(int level, String name) {
        for (int i = level; i < cur_array.length; i++)
            cur_array[i] = null;

        try {
            JSONArray array;
            if (level == 0) {
                array = address_info;
            } else {
                array = cur_array[level - 1];
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.keys().next().equals(name)) {
                    cur_array[level] = obj.getJSONArray(name);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
