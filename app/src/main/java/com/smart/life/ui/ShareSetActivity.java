package com.smart.life.ui;

import com.smart.life.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareSetActivity extends BaseActivity implements OnClickListener{
private ImageView imageview_sharesetback;
private TextView textview_sharesetback;
	@Override
	void init() {
		setContentView(R.layout.shareset_acitvity);
		imageview_sharesetback = (ImageView) findViewById(R.id.imageview_sharesettuanback);
		textview_sharesetback = (TextView) findViewById(R.id.textview_sharesetmeituanback);
		
		imageview_sharesetback.setOnClickListener(this);
		textview_sharesetback.setOnClickListener(this);
	}
	public void onClick(View v) {
		finish();
		
	}

}
