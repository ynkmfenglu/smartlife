package com.smart.life.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.life.R;
import com.smart.life.adapter.MapItemAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.domain.Good;
import com.smart.life.domain.Seller;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;

public class OverItemT extends ItemizedOverlay<MyOverlayItem> {

	private List<MyOverlayItem> GeoList = new ArrayList<MyOverlayItem>();
	private Context mContext;
	private View mPopView;
	private MapView mapView;
	private double mLat1 = 29.85601378875219;// 39.9022; // point1 纬度
	private double mLon1 = 114.34592489501953;// 116.3822; // point1 经度
	private double mLat2 = 29.83603378875219;
	private double mLon2 = 114.32593489501953;
	private double mLat3 = 29.84604378875219;
	private double mLon3 = 114.31596489501953;

	public OverItemT(Drawable arg0, Context context, MapView mapView) {
		super(arg0);
		this.mContext = context;
		this.mapView = mapView;
		mPopView = LayoutInflater.from(context).inflate(
				R.layout.map_overlay_details, null);
		mapView.addView(mPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, null,
				MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
		this.initView();
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		GeoList.add(new MyOverlayItem(p1, "P1", "point1"));
		GeoList.add(new MyOverlayItem(p2, "P2", "point2"));
		GeoList.add(new MyOverlayItem(p3, "P3", "point3"));
		// createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
		populate();
	}

	public void initView() {
		// 头部商家名字
		TextView textview_map_seller_name = (TextView) mPopView
				.findViewById(R.id.textview_map_seller_name);

		// 下面的ListView显示商家的商品
		ListView listview_map = (ListView) mPopView
				.findViewById(R.id.listview_map);
	    MapItemAdapter adapter=new MapItemAdapter(mContext);
	    listview_map.setAdapter(adapter);
	}

	@Override
	protected MyOverlayItem createItem(int arg0) {
		return GeoList.get(arg0);
	}

	@Override
	public int size() {
		return GeoList.size();
	}

	// 处理当点击事件
	@Override
	protected boolean onTap(int index) {
		GeoPoint pt = GeoList.get(index).getPoint();

		mapView.updateViewLayout(mPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.BOTTOM_CENTER));
		mPopView.setVisibility(View.VISIBLE);
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		mPopView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}
