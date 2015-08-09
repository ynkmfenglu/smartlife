package com.smart.life.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Good;
import com.smart.life.domain.User;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.myview.CustomDialog;
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.MyShare;
import com.smart.life.utils.SplitNetImagePath;

public class GroupBuyDetailsActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout relativeLayout_groupbuydetails_left;// ����
	private ImageView imageView_details;// ��ʾ��Ĭ��ͼƬ
	private FrameLayout imageView_groupbuydetails_background;// ͼƬ����
	String[] strings;// ����ͼƬ��ַ����
	private String netImagePath;// ��һ��ͼƬ��ַ
	CustomDialog dialog;
	public  static int position;
	private ImageButton imagebutton_groupbuydetails_favorite;//�ղ�
	private Button imagebutton_groupbuydetails_participate;//��������
	private int i = 1;
	private List<Good> goods;
	private AppContext appContext;
	private User user;
	private Good good;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupbuy_details);

		appContext = (AppContext) getApplicationContext();
		goods = appContext.getGoods();
        
		Intent intent = getIntent();
		if (intent.getAction().equals("GroupBuyActivity")) {
			position = intent.getIntExtra("position", 0);
			good=goods.get(position);
			String netPath =good.getGoods_picturePath();
			strings = SplitNetImagePath.splitNetImagePath(netPath);
			netImagePath = strings[0];
		} else if (intent.getAction().equals("GroupBuyDetailsViewPAger")){
			netImagePath = intent.getStringExtra(netImagePath);
		} else if (intent.getAction().equals("TodayNewOrderActivity")) {
			int goods_id=intent.getIntExtra("goods_id", 0);
			good=getData(goods_id);
			String netPath =good.getGoods_picturePath();
			strings = SplitNetImagePath.splitNetImagePath(netPath);
			netImagePath = strings[0];
		} else if (intent.getAction().equals("MovieActivity")) {
			int goods_id=intent.getIntExtra("goods_id", 0);
			good=getData(goods_id);
			String netPath =good.getGoods_picturePath();
			strings = SplitNetImagePath.splitNetImagePath(netPath);
			netImagePath = strings[0];
		}else if(intent.getAction().equals("MerchantDetailActivity")){
			int position=intent.getIntExtra("position", 0);
			good=MerchantDetailActivity.detail_goods.get(position);
		}

		this.initView();
	}

	public Good getData(int goods_id){
		Good good=null;
		for(int i=0;i<goods.size();i++){
			if(goods_id==goods.get(i).getGoods_id()){
				good=goods.get(i);
			}
		}
		return good;
	}
	
	public void initView() {
		//����
		relativeLayout_groupbuydetails_left = (RelativeLayout) findViewById(R.id.relativeLayout_groupbuydetails_left);
		relativeLayout_groupbuydetails_left.setOnClickListener(this);

		imageView_details = (ImageView) findViewById(R.id.imageView_details);
		// �����̣߳����ͼƬ
		Thread thread = new Thread(runnable);
		thread.start();

		imageView_groupbuydetails_background = (FrameLayout) findViewById(R.id.imageView_groupbuydetails_background);
		imageView_groupbuydetails_background.setOnClickListener(this);
		//�ղ�
		imagebutton_groupbuydetails_favorite = (ImageButton) findViewById(R.id.imagebutton_groupbuydetails_favorite);
		imagebutton_groupbuydetails_favorite.setOnClickListener(this);
		// ����
		ImageButton imagebutton_groupbuydetails_share = (ImageButton) findViewById(R.id.imagebutton_groupbuydetails_share);
		imagebutton_groupbuydetails_share.setOnClickListener(this);
		//��������
		imagebutton_groupbuydetails_participate = (Button) findViewById(R.id.imagebutton_groupbuydetails_participate);
		imagebutton_groupbuydetails_participate.setOnClickListener(this);
		//���ڵļ۸�
		TextView textView_curPrice=(TextView) findViewById(R.id.textView_curPrice);
		textView_curPrice.setText(good.getGoods_price()+"Ԫ");
		//ԭ���ļ۸�
		TextView textView_oldPrice=(TextView) findViewById(R.id.textView_oldPrice);
		textView_oldPrice.setText(good.getGoods_oldPrice()+"Ԫ");
		//���»���
		textView_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		//��Ʒ������
		TextView textView_groupbuy_name=(TextView) findViewById(R.id.textView_groupbuy_name);
		textView_groupbuy_name.setText(good.getGoods_name());
		//��Ʒ����Ϣ
		TextView textView_groupbuy_content=(TextView) findViewById(R.id.textView_groupbuy_content);
		textView_groupbuy_content.setText(good.getGoods_saleInfo());

		user = appContext.getUser();
	}

	Runnable runnable = new Runnable() {
		public void run() {
			// �������ַת��ΪBitMap
			URL picUrl;
			try {
				picUrl = new URL(netImagePath);
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				Message msg = new Message();
				msg.obj = pngBM;
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
			Bitmap pngBM = (Bitmap) msg.obj;
			imageView_details.setImageBitmap(pngBM);
		}
	};
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relativeLayout_groupbuydetails_left:// ����
			finish();
			break;
		case R.id.imageView_groupbuydetails_background:// ͼƬ����
			Intent intent = new Intent(this, GroupBuyImageDetailsActivity.class);
			intent.putExtra("netPathStrings", strings);
			intent.putExtra("goods_name", good.getGoods_name());
			intent.putExtra("goods_saleInfo", good.getGoods_saleInfo());
			startActivity(intent);
			break;
		case R.id.imagebutton_groupbuydetails_favorite:// ��ϲ��
			if (user != null) {
				int id = goods.get(position).getGoods_id();

				String username = user.getUsername();

				RequestConstant nrc = new RequestConstant();
				// post����
				nrc.setType(HttpRequests.HttpRequestType.POST);
				RequestConstant.requestUrl = UrlConstant.CHANGECOLLECTURL;
				RequestConstant.context = this;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("action", "" + i);
				map.put("id", "" + id);
				map.put("username", username);
				RequestConstant.setMap(map);

				getServer(new Netcallback() {

					public void preccess(Object res, boolean flag) {
						if (res != null) {
							try {
								JSONObject object = new JSONObject((String) res);
								String success = object.optString("success");
								if (success.equals("1")) {
									if (i == -1) {
										imagebutton_groupbuydetails_favorite
												.setImageResource(R.drawable.ic_action_favorite_on);
										Toast.makeText(
												GroupBuyDetailsActivity.this,
												"���ղسɹ�", Toast.LENGTH_SHORT)
												.show();
									} else {
										imagebutton_groupbuydetails_favorite
												.setImageResource(R.drawable.ic_action_favorite_off);
										Toast.makeText(
												GroupBuyDetailsActivity.this,
												"��ȡ���ղ�", Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									Toast.makeText(
											GroupBuyDetailsActivity.this,
											"�����쳣,���Ժ�����",
											Toast.LENGTH_SHORT).show();
									imagebutton_groupbuydetails_favorite
									.setImageResource(R.drawable.ic_action_favorite_on);
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}, nrc);

				i = -i;

			} else {
				Toast.makeText(this, "��,���ȵ�¼", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.imagebutton_groupbuydetails_share:// ����

			MyShare myShare = new MyShare(this);
			myShare.share("����Ҫ���������");

			break;
		case R.id.imagebutton_groupbuydetails_participate:// ��������

			if (user != null) {
				startActivityForResult(new Intent(GroupBuyDetailsActivity.this,
						OrderActivity.class),21);
			} else {
				Toast.makeText(this, "��,���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
			
			
			

		default:
			break;
		}
	}

	@Override
	void init() {
		// TODO Auto-generated method stub

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			finish();
		}
	}
}
