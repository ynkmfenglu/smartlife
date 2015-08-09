package com.smart.life.ui;

import com.smart.life.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreActivity extends BaseActivity implements OnClickListener {
	private TextView tv_myself, tv_account, tv_feedback, tv_version, tv_logout;
	private ImageView iv_myself, iv_account, iv_feedback, iv_version;
	private RelativeLayout rl_myself, rl_account, rl_feedback, rl_version, rl_logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	void init() {
		setContentView(R.layout.more_activity2);
		tv_myself = (TextView)findViewById(R.id.myself_name);
		tv_account = (TextView)findViewById(R.id.account_sercurity);
		tv_feedback = (TextView)findViewById(R.id.feedback);
		tv_version = (TextView)findViewById(R.id.version);
		tv_logout = (TextView)findViewById(R.id.logout);

		iv_myself = (ImageView)findViewById(R.id.myself_img);
		iv_account = (ImageView)findViewById(R.id.account_img);
		iv_feedback = (ImageView)findViewById(R.id.feedback_img);
		iv_version = (ImageView)findViewById(R.id.version_img);

		rl_myself = (RelativeLayout)findViewById(R.id.myself_layout);
		rl_account = (RelativeLayout)findViewById(R.id.account_layout);
		rl_feedback = (RelativeLayout)findViewById(R.id.feedback_layout);
		rl_version = (RelativeLayout)findViewById(R.id.version_layout);
		rl_logout = (RelativeLayout)findViewById(R.id.logout_layout);

		tv_myself.setOnClickListener(this);
		tv_account.setOnClickListener(this);
		tv_feedback.setOnClickListener(this);
		tv_version.setOnClickListener(this);
		tv_logout.setOnClickListener(this);

		iv_myself.setOnClickListener(this);
		iv_account.setOnClickListener(this);
		iv_feedback.setOnClickListener(this);
		iv_version.setOnClickListener(this);

		rl_myself.setOnClickListener(this);
		rl_account.setOnClickListener(this);
		rl_feedback.setOnClickListener(this);
		rl_version.setOnClickListener(this);
		rl_logout.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.myself_name:
			case R.id.myself_img:
			case R.id.myself_layout:
				startActivity(new Intent(this, MyselfActivity.class));
				break;
			case R.id.account_sercurity:
			case R.id.account_img:
			case R.id.account_layout:
				startActivity(new Intent(this, ModPasswordActivity.class));
				break;
			case R.id.feedback:
			case R.id.feedback_img:
			case R.id.feedback_layout:
				startActivity(new Intent(this, AccountActivity.class));
				break;
			case R.id.version:
			case R.id.version_img:
			case R.id.version_layout:
				startActivity(new Intent(this, AccountActivity.class));
				break;
			case R.id.logout:
			case R.id.logout_layout:
				startActivity(new Intent(this, AccountActivity.class));
				break;
			default:
				break;
		}
	}
}
