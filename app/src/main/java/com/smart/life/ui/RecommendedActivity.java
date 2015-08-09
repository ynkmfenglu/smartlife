package com.smart.life.ui;

import com.smart.life.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RecommendedActivity extends BaseActivity implements OnClickListener{
	private ImageView imageview_back,imageview_back2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	void init() {
		setContentView(R.layout.recommended_activity);
		imageview_back = (ImageView) findViewById(R.id.imageview_back);
		imageview_back2 =(ImageView) findViewById(R.id.imageview_back2);
		
		
		imageview_back.setOnClickListener(this);
		imageview_back2.setOnClickListener(this);
		
		
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview_back:
		case R.id.imageview_back2:
			finish();
			
			break;

		default:
			break;
		}
		
	}
	

}
