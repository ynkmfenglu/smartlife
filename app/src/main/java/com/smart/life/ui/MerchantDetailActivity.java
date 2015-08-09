package com.smart.life.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.smart.life.R;
import com.smart.life.adapter.MerchantCommentAdapter;
import com.smart.life.adapter.MerchantGroupAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.domain.Good;
import com.smart.life.domain.Seller;
import com.smart.life.myview.MyListView;
import com.smart.life.utils.Logger;
import com.smart.life.utils.MyShare;
import com.smart.life.utils.SplitNetImagePath;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MerchantDetailActivity extends BaseActivity implements OnClickListener{
    private TextView name_textView,detail_comment,photo_count,phone_textview,address_textview,route_textview;
    private ImageView detail_button,detail_share,detail_favorite,image1,image2,image3,photo_imageskip,phone_imageskip,address_imageskip,route_imageskip;
    private RatingBar  ratingBar;
    private MyListView  group_listview,comment_listview;
    private MyShare  myShare;
	private Seller datas;	
	private String[]  strings,comments;
	private String  netImagePath;
	private Bitmap[] bitmaps;
	public static List<Good>  detail_goods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.merchant__details);
		super.onCreate(savedInstanceState);
	}
	void init() {
		// TODO Auto-generated method stub
		name_textView=(TextView) findViewById(R.id.name_textView);
		detail_comment=(TextView) findViewById(R.id.detail_comment);
		photo_count=(TextView) findViewById(R.id.photo_count);
		phone_textview=(TextView) findViewById(R.id.phone_textview);
		address_textview=(TextView) findViewById(R.id.address_textview);
		route_textview=(TextView) findViewById(R.id.route_textview);
		
		detail_button=(ImageView) findViewById(R.id.detail_button);
		detail_share=(ImageView) findViewById(R.id.detail_favorite);
		detail_favorite=(ImageView) findViewById(R.id.detail_share);
		image1=(ImageView)findViewById(R.id.image1);
		image2=(ImageView)findViewById(R.id.image2);
		image3=(ImageView)findViewById(R.id.image3);
		photo_imageskip=(ImageView)findViewById(R.id.photo_imageskip);
		phone_imageskip=(ImageView)findViewById(R.id.phone_imageskip);
		address_imageskip=(ImageView)findViewById(R.id.address_imageskip);
		route_imageskip=(ImageView)findViewById(R.id.route_imageskip);
		
		ratingBar=(RatingBar) findViewById(R.id.ratingBar1);
		group_listview=(MyListView) findViewById(R.id.group_listview);
		comment_listview=(MyListView)findViewById(R.id.comment_listview);	
		
		//����һ��ҳ���������
		Intent intent=getIntent();
		datas=new Seller();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("MerchantAll");
		intentFilter.addAction("MerchantSale");
		String fromMerchant=intent.getAction();
	
			if("MerchantAll".equals(fromMerchant)){
				int allPosition=intent.getIntExtra("allPosition", 0);
				datas=MerchantActivity.allList.get(allPosition);
			}else if("MerchantSale".equals(fromMerchant)){
				int salePosition=intent.getIntExtra("salePosition", 0);
				datas=MerchantActivity.saleList.get(salePosition);
			}else if("collect".equals(fromMerchant)){
				int collectposition = intent.getIntExtra("collectposition", 0);
				datas = CollectActivity.sellers.get(collectposition);
			}
		
		detail_share.setOnClickListener(this);
		Logger.i("merchantDetail:"+datas.getSeller_name());
		name_textView.setText(datas.getSeller_name());
	//	ratingBar.setNumStars((int) datas.getSeller_rank());
		detail_comment.setText(4+"");   //������
		strings=SplitNetImagePath.splitNetImagePath(datas.getSeller_picture());
		Thread  thread=new Thread(runnable);
		thread.start();
		
		//�����Ź��б�
		int seller_id=datas.getSeller_id();
		AppContext   appContext=(AppContext) getApplicationContext();
		List<Good>  goods=new ArrayList<Good>();
		goods=appContext.getGoods();
	    detail_goods=new ArrayList<Good>();
		for(int i=0;i<goods.size();i++){
			if((goods.get(i).getSeller_id())==seller_id){
				Good   good=new Good();
				good=goods.get(i);
				detail_goods.add(good);
			}
		}
		MerchantGroupAdapter  merchantGroupAdapter=new MerchantGroupAdapter(this, detail_goods);
		group_listview.setAdapter(merchantGroupAdapter);
		
		//�����б�
		comments=SplitNetImagePath.splitNetImagePath(datas.getSeller_comment());
	    MerchantCommentAdapter   merchantCommentAdapter=new MerchantCommentAdapter(this,comments);
		comment_listview.setAdapter(merchantCommentAdapter);
		
		photo_count.setText(strings.length+"");
		phone_textview.setText(datas.getSeller_phone());
		address_textview.setText(datas.getSeller_address());
		detail_button.setOnClickListener(this);
		detail_favorite.setOnClickListener(this);
		photo_imageskip.setOnClickListener(this);
		phone_imageskip.setOnClickListener(this);
		address_imageskip.setOnClickListener(this);
		
	}
	
	Runnable runnable = new Runnable() {
		public void run() {
			// �������ַת��ΪBitMap
			URL picUrl;
			bitmaps=new Bitmap[strings.length];
			try {
				for(int i=0;i<strings.length;i++){
				netImagePath=strings[i];
				picUrl = new URL(netImagePath);
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				bitmaps[i]=pngBM;
				}
				Message msg = new Message();
				msg.obj =bitmaps;
				handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bitmap[] pngBM = (Bitmap[]) msg.obj;
			Logger.i("pngBM"+pngBM.length);
			image1.setImageBitmap(pngBM[0]);
			image2.setImageBitmap(pngBM[1]);
			image3.setImageBitmap(pngBM[2]);
			Logger.i("image"+pngBM[0]);
			Logger.i("image"+pngBM[1]);
			Logger.i("image"+pngBM[2]);
		}
	};
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.detail_share:
			myShare=new MyShare(this);
			myShare.share("�̼ҷ���");
			break;
		case R.id.detail_button:
			finish();
			break;
		case R.id.detail_favorite:
			//startActivity(new Intent(this,.class));
			break;
			
		case R.id.phone_imageskip:
			Intent intent =new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+datas.getSeller_phone()));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
