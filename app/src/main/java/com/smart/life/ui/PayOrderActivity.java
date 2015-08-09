package com.smart.life.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Details;
import com.smart.life.domain.Good;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.utils.HttpRequests;

public class PayOrderActivity extends BaseActivity implements OnClickListener{

	//����
	private LinearLayout linearlayout_payorder_back;
	//����
	private TextView textview_payorder_singlePrice;
	//����
	private TextView textview_payorder_quantity;
	//�ܼ�
	private TextView textview_payorder_totalPrice;
	//����֧��
	private TextView textview_payorder_needPay;
	//ȷ��֧��
	private Button button_payorder_makeSure;
	private AppContext appContext;
	private Good good;
	private Details details;

	@Override
	void init() {
          setContentView(R.layout.payorder_activity);		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		appContext = (AppContext) getApplicationContext();
		
		List<Good> goods=appContext.getGoods();
		good = goods.get(GroupBuyDetailsActivity.position);
		
		details = OrderActivity.details;
		
		this.initView();
	}

	private void initView() {
		linearlayout_payorder_back = (LinearLayout) findViewById(R.id.linearlayout_payorder_back);
		linearlayout_payorder_back.setOnClickListener(this);
		
		textview_payorder_singlePrice = (TextView) findViewById(R.id.textview_payorder_singlePrice);
		textview_payorder_singlePrice.setText(good.getGoods_price()+"Ԫ");
		
		textview_payorder_quantity = (TextView) findViewById(R.id.textview_payorder_quantity);
		textview_payorder_quantity.setText(details.getDetails_quantity()+"");
		
		textview_payorder_totalPrice = (TextView) findViewById(R.id.textview_payorder_totalPrice);
		textview_payorder_totalPrice.setText(OrderActivity.i*good.getGoods_price()+"Ԫ");
		
		textview_payorder_needPay = (TextView) findViewById(R.id.textview_payorder_needPay);
		textview_payorder_needPay.setText(details.getDetails_prices()+"Ԫ");
		
		button_payorder_makeSure = (Button) findViewById(R.id.button_payorder_makeSure);
		button_payorder_makeSure.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearlayout_payorder_back:
			finish();
			break;
		case R.id.button_payorder_makeSure:
		    this.getHttp();
			Intent data = new Intent();
			setResult(22, data);
			finish();
			startActivity(new Intent(this,MainActivity.class));
			break;

		default:
			break;
		}
	}

	public void getHttp(){
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("details_time", details.getDetails_time());
		
		RequestConstant nrc=new RequestConstant();
		// post����
		nrc.setType(HttpRequests.HttpRequestType.POST);
		RequestConstant.context=this;
		RequestConstant.requestUrl=UrlConstant.DETAILSISPAYURL;
		RequestConstant.setMap(map);
		
		getServer(new Netcallback() {
			
			public void preccess(Object res, boolean flag) {
				System.out.println("��δ�����Ϊ�Ѹ���"+res);
			}
		}, nrc);
	}
	
	
}
