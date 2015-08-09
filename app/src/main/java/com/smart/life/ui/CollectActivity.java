package com.smart.life.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.smart.life.R;
import com.smart.life.Parser.GroupBuyLikeParser;
import com.smart.life.Parser.MerchantParser;
import com.smart.life.adapter.CollectAdapter;
import com.smart.life.adapter.CollectSellerAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Good;
import com.smart.life.domain.Seller;
import com.smart.life.domain.User;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.myview.MyListView;
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.XListView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CollectActivity extends BaseActivity implements OnClickListener {
	private ImageView imageview_back, imageview_back2;
	private Button button_groupbuy, button_seller;
	private LinearLayout line_groupbuy, line_seller;
	private MyListView listview;
	private TextView textview;
	private XListView xListView;
	private GroupBuyLikeParser parser;
	private CollectSellerAdapter adapter2;
	private CollectAdapter collectAdapter;
	public static ArrayList<Seller> sellers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	void init() {
		setContentView(R.layout.collect_activity);
		imageview_back = (ImageView) findViewById(R.id.imageview_back);
		imageview_back2 = (ImageView) findViewById(R.id.imageview_back2);
		button_groupbuy = (Button) findViewById(R.id.button_groupbuy);
		button_seller = (Button) findViewById(R.id.button_seller);
		line_groupbuy = (LinearLayout) findViewById(R.id.line_groupbuy);
		line_seller = (LinearLayout) findViewById(R.id.line_seller);
		listview = (MyListView) findViewById(R.id.listview_collect);
		textview = (TextView) findViewById(R.id.textview_collect);
		xListView = (XListView) findViewById(R.id.xlistview);

		imageview_back.setOnClickListener(this);
		imageview_back2.setOnClickListener(this);
		button_groupbuy.setOnClickListener(this);
		button_seller.setOnClickListener(this);

		AppContext appContext = (AppContext) getApplicationContext();
		User user = appContext.getUser();
		String username = user.getUsername();

		RequestConstant nrc = new RequestConstant();
		RequestConstant.requestUrl = UrlConstant.COLLECTURL;
		RequestConstant.context = this;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		RequestConstant.setMap(map);
		nrc.setType(HttpRequests.HttpRequestType.POST);

		getServer(new Netcallback() {

			public void preccess(Object res, boolean flag) {
				System.out.println("res" + res);
				if (res != null) {
					String[] strs = ((String) res).split("\\+");
					String gres = strs[0];
					String sres = strs[1];
					try {
						parser = new GroupBuyLikeParser();
						List<Good> goods = parser.getGoods(gres);
						if (goods != null) {
							textview.setVisibility(View.GONE);
							listview.setVisibility(View.VISIBLE);
							collectAdapter = new CollectAdapter(
									CollectActivity.this, goods);
							listview.setAdapter(collectAdapter);
						}
						MerchantParser parser2 = new MerchantParser();
						sellers = parser2.getMerchant(sres);
						if (sellers != null) {
							adapter2 = new CollectSellerAdapter(
									CollectActivity.this, sellers);
							
							xListView.setAdapter(adapter2);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, nrc);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview_back:
		case R.id.imageview_back2:
			finish();
			break;
		case R.id.button_seller:
			button_seller.setTextColor(getResources().getColor(R.color.black));
			button_groupbuy.setTextColor(getResources().getColor(
					R.color.textgray));
			line_seller.setVisibility(View.VISIBLE);
			line_groupbuy.setVisibility(View.INVISIBLE);
			if(collectAdapter!=null){
				textview.setVisibility(View.GONE);
				listview.setVisibility(View.GONE);
				xListView.setVisibility(View.VISIBLE);
			}else{
				textview.setVisibility(View.VISIBLE);
				listview.setVisibility(View.GONE);
				xListView.setVisibility(View.GONE);
			}
			
			break;
		case R.id.button_groupbuy:
			button_groupbuy
					.setTextColor(getResources().getColor(R.color.black));
			button_seller.setTextColor(getResources()
					.getColor(R.color.textgray));
			line_groupbuy.setVisibility(View.VISIBLE);
			line_seller.setVisibility(View.INVISIBLE);
			if(collectAdapter!=null){
				xListView.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE);
				textview.setVisibility(View.GONE);
			}else{
				xListView.setVisibility(View.GONE);
				listview.setVisibility(View.GONE);
				textview.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}

	}

}
