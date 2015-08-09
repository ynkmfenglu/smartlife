package com.smart.life.adapter;

import java.util.List;

import com.smart.life.R;
import com.smart.life.domain.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	private Context context;
	private List<Address> addresses;

	public AddressAdapter(Context context, List<Address> addresses) {
		this.context = context;
		this.addresses = addresses;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addresses.size();
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

	public void setData(List<Address> addresses) {
		this.addresses = addresses;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.address_cell, null);
			holderView = new HolderView();
			holderView.name = (TextView) convertView.findViewById(R.id.name);
			holderView.phone = (TextView) convertView.findViewById(R.id.phone);
			holderView.area = (TextView) convertView.findViewById(R.id.area);
			holderView.address = (TextView) convertView
					.findViewById(R.id.address);
			holderView.postcode = (TextView) convertView
					.findViewById(R.id.postcode);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		holderView.name.setText("���� ��" + addresses.get(position).getName());
		holderView.phone.setText("�绰 ��" + addresses.get(position).getPhone());
		holderView.area.setText("ʡ�� ��" + addresses.get(position).getProvince()
				+ addresses.get(position).getCity()
				+ addresses.get(position).getArea());
		holderView.address.setText("��ַ ��"+addresses.get(position).getAddress());
		holderView.postcode.setText("�ʱ� ��"+addresses.get(position).getPostcode());
		return convertView;
	}

	class HolderView {
		TextView name;
		TextView phone;
		TextView area;
		TextView postcode;
		TextView address;
	}

}
