package com.smart.life.ui;

import com.smart.life.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutMeiTuanActivity extends BaseActivity implements
		OnClickListener {
	private ImageView imageview_back;
	private TextView textview_slife;
	private Button button_meituanphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	void init() {
		setContentView(R.layout.aboutmeituan_activity);
		imageview_back = (ImageView) findViewById(R.id.iv_about);
		textview_slife = (TextView) findViewById(R.id.textview_slife);
		button_meituanphone = (Button) findViewById(R.id.button_meituanphone);

		imageview_back.setOnClickListener(this);
		textview_slife.setOnClickListener(this);
		button_meituanphone.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_about:
		case R.id.textview_slife:
			finish();
			break;
		case R.id.button_meituanphone:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:4006605335"));
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
