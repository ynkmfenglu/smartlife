package com.smart.life.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.life.R;
import com.smart.life.domain.Good;
import com.smart.life.utils.SplitNetImagePath;

public class GroupBuyLikeAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Good> goods;
	//private ListView mListView;
	Bitmap pngBM ;//����ͼƬ
	Bitmap[] bitmaps;

	public GroupBuyLikeAdapter(Context context, ArrayList<Good> goods) 
	{
		this.context = context;
		this.goods = goods;
		//this.mListView=mListView;
		bitmaps=new Bitmap[goods.size()];
		ImageCache imageCache=new ImageCache();
		imageCache.start();
		try {
			imageCache.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCount() {
		return goods.size();
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
		HolderView holderView = null;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.groupbuylike_cell, null);
			holderView.imageview_goodpicture = (ImageView) convertView
					.findViewById(R.id.imageview_goodpicture);
			holderView.imageview_isFreeOrder = (ImageView) convertView
					.findViewById(R.id.imageview_isFreeOrder);
			holderView.imageview_goodnew = (ImageView) convertView
					.findViewById(R.id.imageview_goodnew);
			holderView.textview_goodname = (TextView) convertView
					.findViewById(R.id.textview_goodname);
			holderView.textview_goodcontent = (TextView) convertView
					.findViewById(R.id.textview_goodcontent);
			holderView.textview_goodprice = (TextView) convertView
					.findViewById(R.id.textview_goodprice);
			holderView.textview_peoplenum = (TextView) convertView
					.findViewById(R.id.textview_peoplenum);
			holderView.textview_goodoldprice = (TextView) convertView
					.findViewById(R.id.textview_goodoldprice);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		if (goods.get(position).getGoods_isLatest() == 1) {
			holderView.imageview_goodnew.setVisibility(View.VISIBLE);
		} else {
			holderView.imageview_goodnew.setVisibility(View.GONE);
		}

		if (goods.get(position).getGoods_isFreeOrder() == 1) {
			holderView.imageview_isFreeOrder.setVisibility(View.VISIBLE);
		} else {
			holderView.imageview_isFreeOrder.setVisibility(View.GONE);
		}

		holderView.imageview_goodpicture.setImageBitmap(bitmaps[position]);

		holderView.textview_goodname.setText(goods.get(position).getGoods_name());
		holderView.textview_goodcontent.setText(goods.get(position).getGoods_saleInfo());
		holderView.textview_goodprice.setText(Html.fromHtml(goods.get(position).getGoods_price()
				+ "<font color=#A39898 font-size=13>" + "Ԫ</font>"));
		holderView.textview_goodoldprice.setText(goods.get(position).getGoods_oldPrice() + "Ԫ");
		holderView.textview_goodoldprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG);
		holderView.textview_peoplenum.setText("����" + goods.get(position).getGoods_salerNum());
		return convertView;
	}
	
	class HolderView {
		ImageView imageview_goodpicture;
		ImageView imageview_isFreeOrder;
		ImageView imageview_goodnew;
		TextView textview_goodname;
		TextView textview_goodcontent;
		TextView textview_goodprice;
		TextView textview_peoplenum;
		TextView textview_goodoldprice;
	}
  
	class ImageCache extends Thread{
		
		public void run() {
			for(int i =0;i<goods.size();i++){
            String	netPictruePath	=goods.get(i).getGoods_picturePath();
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