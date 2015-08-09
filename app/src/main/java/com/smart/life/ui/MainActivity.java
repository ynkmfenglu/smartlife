package com.smart.life.ui;

import com.smart.life.R;
import com.smart.life.app.AppContext;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnClickListener {

	private TabHost host;
	private final static String HOME_STRING = "HOME_STRING";//首页
	private final static String SERVICE_STRING = "SERVICE_STRING";//社区服务
	private final static String FRD_STRING = "FRD_STRING";//街坊四邻
	private final static String LIFE_STRING = "LIFE_STRING";//生活
	private final static String MORE_STRING = "MORE_STRING";//更多
	private ImageView img_home;
	private ImageView img_service;
	private ImageView img_frd;
	private ImageView img_life;
	private ImageView img_more;
	private TextView  text_home;
	private TextView  text_service;
	private TextView  text_frd;
	private TextView  text_life;
	private TextView  text_more;
	private LinearLayout linearLayout_home;
	private LinearLayout linearlayout_service;
	private LinearLayout linearlayout_frd;
	private LinearLayout linearlayout_life;
	private LinearLayout linearlayout_more;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.getScreenDisplay();
		
		this.initView();
		host = getTabHost();
		host.setup();
		setHomeTab();
		setServiceTab();
		setFrdTab();
		setLifeTab();
		setMoreTab();
		host.setCurrentTabByTag(HOME_STRING);

	}

	public void initView(){
		img_home=(ImageView) findViewById(R.id.img_home);
		img_service=(ImageView) findViewById(R.id.img_service);
		img_frd=(ImageView) findViewById(R.id.img_frd);
		img_life=(ImageView) findViewById(R.id.img_life);
		img_more=(ImageView) findViewById(R.id.img_more);
		img_home.setOnClickListener(this);
		img_service.setOnClickListener(this);
		img_frd.setOnClickListener(this);
		img_life.setOnClickListener(this);
		img_more.setOnClickListener(this);

		text_home=(TextView) findViewById(R.id.text_home);
		text_service=(TextView) findViewById(R.id.text_service);
		text_frd=(TextView) findViewById(R.id.text_frd);
		text_life=(TextView) findViewById(R.id.text_life);
		text_more=(TextView) findViewById(R.id.text_more);

		linearLayout_home=(LinearLayout) findViewById(R.id.linearlayout_home);
		linearlayout_service=(LinearLayout) findViewById(R.id.linearlayout_service);
		linearlayout_frd=(LinearLayout) findViewById(R.id.linearlayout_frd);
		linearlayout_life=(LinearLayout) findViewById(R.id.linearlayout_life);
		linearlayout_more=(LinearLayout) findViewById(R.id.linearlayout_more);

		linearLayout_home.setOnClickListener(this);
		linearlayout_service.setOnClickListener(this);
		linearlayout_frd.setOnClickListener(this);
		linearlayout_life.setOnClickListener(this);
		linearlayout_more.setOnClickListener(this);
	}

	private void setHomeTab() {
		TabSpec tabSpec = host.newTabSpec(HOME_STRING);
		tabSpec.setIndicator(HOME_STRING);
		Intent intent = new Intent(MainActivity.this, HomeActivity.class);
		tabSpec.setContent(intent);
		host.addTab(tabSpec);
	}

	private void setServiceTab() {
		TabSpec tabSpec = host.newTabSpec(SERVICE_STRING);
		tabSpec.setIndicator(SERVICE_STRING);
		Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
		tabSpec.setContent(intent);
		host.addTab(tabSpec);
	}

	private void setFrdTab() {
		TabSpec tabSpec = host.newTabSpec(FRD_STRING);
		tabSpec.setIndicator(FRD_STRING);
		Intent intent = new Intent(MainActivity.this, MerchantActivity.class);
		tabSpec.setContent(intent);
		host.addTab(tabSpec);
	}

	private void setLifeTab() {
		TabSpec tabSpec = host.newTabSpec(LIFE_STRING);
		tabSpec.setIndicator(LIFE_STRING);
		Intent intent = new Intent(MainActivity.this, CommunityLifeActivity.class);
		tabSpec.setContent(intent);
		host.addTab(tabSpec);
	}

	private void setMoreTab() {
		TabSpec tabSpec = host.newTabSpec(MORE_STRING);
		tabSpec.setIndicator(MORE_STRING);
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		tabSpec.setContent(intent);
		host.addTab(tabSpec);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearlayout_home:
		case R.id.img_home:
			host.setCurrentTabByTag(HOME_STRING);
			img_home.setBackgroundResource(R.drawable.ic_menu_deal_on);
			text_home.setTextColor(getResources().getColor(R.color.green));
			img_service.setBackgroundResource(R.drawable.ic_menu_poi_off);
			text_service.setTextColor(getResources().getColor(R.color.textgray));
			img_frd.setBackgroundResource(R.drawable.ic_menu_poi_off);
			text_frd.setTextColor(getResources().getColor(R.color.textgray));
			img_life.setBackgroundResource(R.drawable.ic_menu_user_off);
			text_life.setTextColor(getResources().getColor(R.color.textgray));
			img_more.setBackgroundResource(R.drawable.ic_menu_more_off);
			text_more.setTextColor(getResources().getColor(R.color.textgray));
			break;
		case R.id.linearlayout_service:
		case R.id.img_service:
			host.setCurrentTabByTag(SERVICE_STRING);
			img_home.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_home.setTextColor(getResources().getColor(R.color.textgray));
			img_service.setBackgroundResource(R.drawable.ic_menu_deal_on);
			text_service.setTextColor(getResources().getColor(R.color.green));
			img_frd.setBackgroundResource(R.drawable.ic_menu_poi_off);
			text_frd.setTextColor(getResources().getColor(R.color.textgray));
			img_life.setBackgroundResource(R.drawable.ic_menu_user_off);
			text_life.setTextColor(getResources().getColor(R.color.textgray));
			img_more.setBackgroundResource(R.drawable.ic_menu_more_off);
			text_more.setTextColor(getResources().getColor(R.color.textgray));
			
			break;

		case R.id.linearlayout_frd:
		case R.id.img_frd:	
			host.setCurrentTabByTag(FRD_STRING);
			img_home.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_home.setTextColor(getResources().getColor(R.color.textgray));
			img_service.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_service.setTextColor(getResources().getColor(R.color.textgray));
			img_frd.setBackgroundResource(R.drawable.ic_menu_poi_on);
			text_frd.setTextColor(getResources().getColor(R.color.green));
			img_life.setBackgroundResource(R.drawable.ic_menu_user_off);
			text_life.setTextColor(getResources().getColor(R.color.textgray));
			img_more.setBackgroundResource(R.drawable.ic_menu_more_off);
			text_more.setTextColor(getResources().getColor(R.color.textgray));
			break;

		case R.id.linearlayout_life:
		case R.id.img_life:
			host.setCurrentTabByTag(LIFE_STRING);
			img_home.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_home.setTextColor(getResources().getColor(R.color.textgray));
			img_service.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_service.setTextColor(getResources().getColor(R.color.textgray));
			img_frd.setBackgroundResource(R.drawable.ic_menu_poi_off);
			text_frd.setTextColor(getResources().getColor(R.color.textgray));
			img_life.setBackgroundResource(R.drawable.ic_menu_user_on);
			text_life.setTextColor(getResources().getColor(R.color.green));
			img_more.setBackgroundResource(R.drawable.ic_menu_more_off);
			text_more.setTextColor(getResources().getColor(R.color.textgray));
			break;

		case R.id.linearlayout_more:
		case R.id.img_more:
			host.setCurrentTabByTag(MORE_STRING);
			img_home.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_home.setTextColor(getResources().getColor(R.color.textgray));
			img_service.setBackgroundResource(R.drawable.ic_menu_deal_off);
			text_service.setTextColor(getResources().getColor(R.color.textgray));
			img_frd.setBackgroundResource(R.drawable.ic_menu_poi_off);
			text_frd.setTextColor(getResources().getColor(R.color.textgray));
			img_life.setBackgroundResource(R.drawable.ic_menu_user_off);
			text_life.setTextColor(getResources().getColor(R.color.textgray));
			img_more.setBackgroundResource(R.drawable.ic_menu_more_on);
			text_more.setTextColor(getResources().getColor(R.color.green));
			break;

		default:
			break;
		}
	}
	
	private void getScreenDisplay(){
		 Display display=this.getWindowManager().getDefaultDisplay();
	     int screenWidth = display.getWidth();
	     int screenHeight=display.getHeight();
	     
	     AppContext appContext=(AppContext) getApplicationContext();
	     appContext.setScreenWidth(screenWidth);
	     appContext.setScreenHeight(screenHeight);
	}
}
