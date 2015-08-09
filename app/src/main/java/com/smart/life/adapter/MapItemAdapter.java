package com.smart.life.adapter;

import com.smart.life.R;
import com.smart.life.adapter.GroupBuyLikeAdapter.HolderView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapItemAdapter extends BaseAdapter {
	
	Context mContext=null;
	String[] strings1={"��ס1��,��׼���Է�/���˵��Է�2ѡ1����������ס","�����׷���ס1����������ס�����WIFI","��ס1����׼��/���˼�2ѡ1����������ס"};
	String[] strings2={"158Ԫ","122Ԫ","86Ԫ"};
	
	public MapItemAdapter(Context context){
		this.mContext=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings1.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.map_overlay_item, null);
			holder=new ViewHolder();
			holder.textview_map_seller_content=(TextView) convertView.findViewById(R.id.textview_map_seller_content);
			holder.textview_map_seller_curPrice=(TextView) convertView.findViewById(R.id.textview_map_seller_curPrice);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textview_map_seller_content.setText(strings1[position]);
		holder.textview_map_seller_curPrice.setText(strings2[position]);
		return convertView;
	}
	
	class ViewHolder{
		TextView textview_map_seller_content;
		TextView textview_map_seller_curPrice;
	}

}
