package com.smart.life.adapter;

import com.smart.life.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MoreClassifyAdapter extends BaseAdapter {

	private Context context;
	private String[] strs;

	public MoreClassifyAdapter(Context context, String[] strs) {
		this.context = context;
		this.strs = strs;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return strs.length;
		
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView holdView = null;
		if (convertView == null) {
			holdView = new HoldView();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.moreclassify_cell, null);
			holdView.name =  (Button) convertView
					.findViewById(R.id.button_name);
			convertView.setTag(holdView);
		} else {
			holdView = (HoldView) convertView.getTag();
		}
		holdView.name.setText(strs[position]);
		return convertView;
	}

	class HoldView {
		Button name;
	}
}
