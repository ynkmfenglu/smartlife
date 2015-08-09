package com.smart.life.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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

public class OrderActivity extends BaseActivity implements OnClickListener {

	private AppContext appContext;
	private List<Good> goods;
	private Good good;
	//Ĭ�Ϲ��������
	private TextView textview_order_number;
	public static int i=1;
	//��Ʒ�ܼ۸�
	private TextView textview_order_totalPrice;
	public static Details details;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	void init() {
		setContentView(R.layout.activity_order);
		
		appContext = (AppContext) getApplicationContext();
		goods = appContext.getGoods();
		good = goods.get(GroupBuyDetailsActivity.position);
		
		// ����
		LinearLayout linearLayout_login_back = (LinearLayout) findViewById(R.id.linearLayout_login_back);
		linearLayout_login_back.setOnClickListener(this);
		// ��Ʒ����
		TextView textview_order_singlePrice = (TextView) findViewById(R.id.textview_order_singlePrice);
		textview_order_singlePrice.setText(good.getGoods_price()+"Ԫ");
		// ������Ʒ����
		TextView textview_order_name = (TextView) findViewById(R.id.textview_order_name);
		textview_order_name.setText(good.getGoods_name());
		//��Ʒ����
		textview_order_number = (TextView) findViewById(R.id.textview_order_number);
		textview_order_number.setText(i+"");
		//��Ʒ�ܼ۸�
		textview_order_totalPrice = (TextView) findViewById(R.id.textview_order_totalPrice);
		textview_order_totalPrice.setText(good.getGoods_price()+"Ԫ");
		textview_order_totalPrice.setOnClickListener(this);
		// ��Ʒ�������
		ImageView imageview_order_in = (ImageView) findViewById(R.id.imageview_order_in);
		imageview_order_in.setOnClickListener(this);
		// ��Ʒ��������
		ImageView imageview_order_out = (ImageView) findViewById(R.id.imageview_order_out);
		imageview_order_out.setOnClickListener(this);
		// �ύ����
		Button button_order_submit = (Button) findViewById(R.id.button_order_submit);
		button_order_submit.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout_login_back:
            finish();
			break;
		case R.id.imageview_order_in:
			if(i>=0){
				i++;
				textview_order_number.setText(i+"");
				double order_totalPrice1=i*(good.getGoods_price());
				textview_order_totalPrice.setText(order_totalPrice1+"Ԫ");
			}
			break;
		case R.id.imageview_order_out:
			if(i>=1){
				i--;
				textview_order_number.setText(i+"");
				double order_totalPrice2=i*(good.getGoods_price());
				textview_order_totalPrice.setText(order_totalPrice2+"Ԫ");
			}
			break;
		case R.id.button_order_submit:
			
			Map<String,Object> map=new HashMap<String,Object>();
			details = new Details();
			details.setGood_id(good.getGoods_id());
			
			details.setDetails_quantity(i);
			details.setDetails_prices(i*(good.getGoods_price()));
			//δ����
			details.setDetails_isPay(0);
			//ϵͳʱ��
			Date date = new Date ();
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM--dd hh:mm");
			String time = f.format(date);
			details.setDetails_time(time);
			
			map.put("good_id", details.getGood_id()+"");
			map.put("details_prices", details.getDetails_prices()+"");
			map.put("details_quantity", details.getDetails_quantity()+"");
			map.put("details_time", details.getDetails_time());
			map.put("details_isPay", details.getDetails_isPay()+"");
			
			String user_name=appContext.getUser().getUsername();
			map.put("user_name", user_name+"");
			
			RequestConstant nrc=new RequestConstant();
			// post����
			nrc.setType(HttpRequests.HttpRequestType.POST);
			RequestConstant.context=this;
			RequestConstant.requestUrl=UrlConstant.DETAILSURL;
			RequestConstant.setMap(map);
			
			getServer(new Netcallback(){

				public void preccess(Object res, boolean flag) {
					// TODO Auto-generated method stub
					System.out.println("Details�������緵�ص�ֵ"+res);
				}
				
			}, nrc);
			
		    startActivityForResult(new Intent(this,PayOrderActivity.class),22);
		    
			break;

		default:
			break;
		}
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(data!=null){
    		setResult(21, data);
    		finish();
    	}
    }
}
