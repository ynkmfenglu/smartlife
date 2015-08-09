package com.smart.life.ui;

import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

import com.smart.life.R;
import com.smart.life.Parser.GroupBuyLikeParser;
import com.smart.life.adapter.GroupBuyLikeAdapter;
import com.smart.life.adapter.GroupBuyMenuAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Good;
import com.smart.life.domain.User;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.myview.MyGridView;
import com.smart.life.myview.MyListView;
import com.smart.life.utils.HttpRequests;

public class GroupBuyActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private MyGridView gridview_menu;
	private MyListView listview_like;
	private GroupBuyMenuAdapter adapter;
	private GroupBuyLikeAdapter likeAdapter;
	private Button button_city;
	private ImageButton imagebutton_search;
	private ImageButton imagebutton_map;
	private SharedPreferences preferences;
	private AppContext appContext;
	// gridView����Դ
	private int[] i = new int[] { R.drawable.ic_category_0,
			R.drawable.ic_category_1, R.drawable.ic_category_2,
			R.drawable.ic_category_3, R.drawable.ic_category_4,
			R.drawable.ic_category_5, R.drawable.ic_category_6,
			R.drawable.ic_category_7 };

	private GroupBuyLikeParser parser;
	public static  ArrayList<Good> goods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridview_menu.setAdapter(adapter);

		appContext = (AppContext) getApplicationContext();
		
		String city = appContext.getCity();
		if (city != null && city != "") {
			button_city.setText(city);
		}
	}

	void init() {
		setContentView(R.layout.groupbuy_activity);
		gridview_menu = (MyGridView) findViewById(R.id.gridview_menu);
		listview_like = (MyListView) findViewById(R.id.listview_like);
		button_city = (Button) findViewById(R.id.button_city);
		imagebutton_search = (ImageButton) findViewById(R.id.imagebutton_search);
		imagebutton_map = (ImageButton) findViewById(R.id.imagebutton_map);

		listview_like.setOnItemClickListener(this);
		button_city.setOnClickListener(this);
		imagebutton_search.setOnClickListener(this);
		imagebutton_map.setOnClickListener(this);
		adapter = new GroupBuyMenuAdapter(this, i);

		gridview_menu.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position) {

				case 0:// �����µ�
					Intent intent0 = new Intent(GroupBuyActivity.this,
							TodayNewOrderActivity.class);
					startActivity(intent0);
					break;
				case 1:// ��Ӱ
					Intent intent1 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent1.putExtra("flag", 2);
					startActivity(intent1);
					break;
				case 2:// KTV
					Intent intent2 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent2.putExtra("flag", 5);
					startActivity(intent2);
					break;
				case 3:// �Ƶ�
					Intent intent3 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent3.putExtra("flag", 6);
					startActivity(intent3);
					break;
				case 4:// ��ʳ
					Intent intent4 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent4.putExtra("flag", 1);
					startActivity(intent4);
					break;
				case 5:// ���ư�Ħ
					Intent intent5 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent5.putExtra("flag", 7);
					startActivity(intent5);
					break;
				case 6:// �ܱ���
					Intent intent6 = new Intent(GroupBuyActivity.this,
							MovieActivity.class);
					intent6.putExtra("flag", 3);
					startActivity(intent6);
					break;
				case 7:// �������
					startActivity(new Intent(GroupBuyActivity.this,
							MoreClassifyActivity.class));
					break;

				default:
					break;
				}
			}
		});

		RequestConstant nrc = new RequestConstant();
		RequestConstant.requestUrl = UrlConstant.GOODURL;
		RequestConstant.context = this;
		nrc.setType(HttpRequests.HttpRequestType.GET);

		getServer(new Netcallback() {

			public void preccess(Object res, boolean flag) {

				if (res != null) {
					try {
						parser = new GroupBuyLikeParser();
						goods = parser.getGoods((String) res);

						appContext.setGoods(goods);
						likeAdapter = new GroupBuyLikeAdapter(
								GroupBuyActivity.this, goods);

						listview_like.setAdapter(likeAdapter);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, nrc);
		appContext = (AppContext) getApplicationContext();
		preferences = appContext.getPreferences();
		User user = new User();
		if(preferences!=null){
		String username = preferences.getString("username", null);
		String password = preferences.getString("password", null);
		if (username != null && password != null) {
			user.setUsername(username);
			user.setPassword(password);
			appContext.setUser(user);
		}
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, GroupBuyDetailsActivity.class);
		intent.setAction("GroupBuyActivity");
		intent.putExtra("position", position);
		startActivity(intent);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_city:
			preferences = appContext.getPreferences();
			Editor editor = preferences.edit();
			editor.putInt("isnext", 3);
			editor.commit();
			startActivityForResult(new Intent(this, SelectCityActivity.class),
					1);
			startActivity(new Intent(this, SelectCityActivity.class));
			break;
		case R.id.imagebutton_search:
			startActivity(new Intent(this, SeekActivity.class));
			break;
		case R.id.imagebutton_map:
			startActivity(new Intent(this, MyMapActivity.class));
			break;
		default:
			break;
		}

	}

	
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		String city = data.getStringExtra("city");
		button_city.setText(city);

	}

	

}