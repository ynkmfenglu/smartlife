package com.smart.life.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.smart.life.R;
import com.smart.life.Parser.DetailParser;
import com.smart.life.adapter.ObligationAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Details;
import com.smart.life.domain.User;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.utils.HttpRequests;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ObligationActivity extends BaseActivity implements OnClickListener {
	private ImageView imageview_back, imageview_back2;
	private Button button_groupbuy, button_movie;
	private LinearLayout line_group, line_movie,linearLayout_lottery,linearLayout_data;
	private AppContext appContext;
	private User user;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		RequestConstant nrc = new RequestConstant();
		// post请求
		nrc.setType(HttpRequests.HttpRequestType.POST);

		RequestConstant.requestUrl = UrlConstant.NOPAYURL;
		RequestConstant.context = this;
		Map<String, Object> map = new HashMap<String, Object>();
		String username = user.getUsername();
		map.put("username", username);
		map.put("ispay","0");
		RequestConstant.map = map;

		getServer(new Netcallback() {

			public void preccess(Object res, boolean flag) {
				if (res != null) {
					DetailParser parser = new DetailParser();
					List<Details> details = parser.getDetail((String) res);
					if(details.size()>0){
						linearLayout_data.setVisibility(View.VISIBLE);
						linearLayout_lottery.setVisibility(View.GONE);
						ObligationAdapter adapter = new ObligationAdapter(
								ObligationActivity.this, details,false);
						listview.setAdapter(adapter);
					}
				}
			}
		}, nrc);
	}

	@Override
	void init() {
		setContentView(R.layout.obligation_activity);
		imageview_back = (ImageView) findViewById(R.id.imageview_back);
		imageview_back2 = (ImageView) findViewById(R.id.imageview_back2);
		button_groupbuy = (Button) findViewById(R.id.button_groupbuy);
		button_movie = (Button) findViewById(R.id.button_movie);
		line_group = (LinearLayout) findViewById(R.id.line_groupbuy);
		line_movie = (LinearLayout) findViewById(R.id.line_seller);
		listview = (ListView) findViewById(R.id.listview_obligation);
		linearLayout_lottery = (LinearLayout) findViewById(R.id.linearLayout_lottery);
		linearLayout_data = (LinearLayout) findViewById(R.id.linearLayout_data);

		imageview_back.setOnClickListener(this);
		imageview_back2.setOnClickListener(this);
		button_groupbuy.setOnClickListener(this);
		button_movie.setOnClickListener(this);

		appContext = (AppContext) getApplicationContext();
		user = appContext.getUser();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview_back:
		case R.id.imageview_back2:
			finish();
			break;
		case R.id.button_groupbuy:
			button_groupbuy
					.setTextColor(getResources().getColor(R.color.black));
			button_movie
					.setTextColor(getResources().getColor(R.color.textgray));
			line_group.setVisibility(View.VISIBLE);
			line_movie.setVisibility(View.INVISIBLE);
			linearLayout_data.setVisibility(View.VISIBLE);
			linearLayout_lottery.setVisibility(View.GONE);
			break;
		case R.id.button_movie:
			button_movie.setTextColor(getResources().getColor(R.color.black));
			button_groupbuy.setTextColor(getResources().getColor(
					R.color.textgray));
			line_movie.setVisibility(View.VISIBLE);
			line_group.setVisibility(View.INVISIBLE);
			linearLayout_data.setVisibility(View.GONE);
			linearLayout_lottery.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

}
