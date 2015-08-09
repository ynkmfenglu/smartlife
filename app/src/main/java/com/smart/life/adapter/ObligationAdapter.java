package com.smart.life.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.R.bool;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.domain.Details;
import com.smart.life.domain.Good;
import com.smart.life.ui.OrderActivity;
import com.smart.life.ui.OrderDetailActivity;
import com.smart.life.ui.PayOrderActivity;
import com.smart.life.utils.SplitNetImagePath;

public class ObligationAdapter extends BaseAdapter {
	private Context context;
	private List<Details> details;
	private List<Good> goods;
	private boolean ispay;
	Bitmap pngBM ;//����ͼƬ
	Bitmap[] bitmaps;


	public ObligationAdapter(Context context, List<Details> details,boolean ispay) {
		this.context = context;
		this.details = details;
		this.ispay = ispay;
		AppContext appContext = (AppContext) context.getApplicationContext();
		goods = appContext.getGoods();
		
		
		bitmaps=new Bitmap[details.size()];
		ImageCache imageCache=new ImageCache();
		imageCache.start();

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return details.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.obligation_cell, null);
			holderView.imageview = (ImageView) convertView
					.findViewById(R.id.imageview_goodpicture);
			holderView.goodsname = (TextView) convertView
					.findViewById(R.id.textview_goodname);
			holderView.price = (TextView) convertView
					.findViewById(R.id.textview_goodcontent);
			holderView.num = (TextView) convertView
					.findViewById(R.id.textview_num);
			holderView.pay = (Button) convertView.findViewById(R.id.button_pay);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		holderView.goodsname.setText(getNameById(details.get(position)
				.getGood_id()));
		holderView.price.setText("�ܼۣ�"
				+ details.get(position).getDetails_prices() + "Ԫ");
		holderView.num.setText("������"
				+ details.get(position).getDetails_quantity());
		holderView.imageview.setImageBitmap(bitmaps[position]);
	
		holderView.pay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
			  OrderActivity.details = details.get(position);
              context.startActivity(new Intent(context, OrderActivity.class));
			}
		});
		if(ispay){
			holderView.pay.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	class HolderView {
		ImageView imageview;
		TextView goodsname;
		TextView price;
		TextView num;
		Button pay;
	}

	private String getNameById(int id) {
		String name = null;
		for (int i = 0; i < goods.size(); i++) {
			if (goods.get(i).getGoods_id() == id) {
				name = goods.get(i).getGoods_name();
			}
		}
		return name;
	}

	private String getPathById(int id) {
		String path = null;
		for (int i = 0; i < goods.size(); i++) {
			if (goods.get(i).getGoods_id() == id) {
				path = goods.get(i).getGoods_picturePath();
			}
		}
		return path;

	}
class ImageCache extends Thread{
		
		public void run() {
			for(int i =0;i<details.size();i++){
            String	netPictruePath	=  getPathById(details.get(i).getGood_id());
            String[] strings=SplitNetImagePath.splitNetImagePath(netPictruePath);
            //��ʾ��һ��ͼƬ��ΪĬ��ͼƬ
            String pictruePath=strings[0];
			// �������ַת��ΪBitMap
			URL picUrl; 
			try {
				picUrl = new URL(pictruePath);
			    pngBM = BitmapFactory.decodeStream(picUrl.openStream());
			    bitmaps[i]=pngBM;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
	}
}
