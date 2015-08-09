package com.smart.life.ui;

import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.domain.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountActivity extends Activity implements OnClickListener {
	private ImageView iv_uname,iv_back, iv_back2;
	private TextView tv_uname;
	private LinearLayout ll_uname, ll_passwd,ll_phone,	ll_address;
    private Button btn_exit;
	private AppContext appContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_activity);
		init();
	}

	private void init() {
		appContext = (AppContext) getApplicationContext();
		User user = appContext.getUser();
		ll_uname = (LinearLayout) findViewById(R.id.ll_uname);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back2 = (ImageView) findViewById(R.id.iv_back2);
		iv_uname = (ImageView) findViewById(R.id.iv_uname);
		tv_uname = (TextView) findViewById(R.id.tv_uname);
		ll_passwd = (LinearLayout) findViewById(R.id.ll_passwd);
		ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
		ll_address = (LinearLayout) findViewById(R.id.ll_address);
		btn_exit = (Button) findViewById(R.id.btn_exit);

		ll_uname.setOnClickListener(this);
		ll_passwd.setOnClickListener(this);
		ll_phone.setOnClickListener(this);
		ll_address.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		iv_back2.setOnClickListener(this);
		btn_exit.setOnClickListener(this);

		if (user != null) {
			tv_uname.setText(user.getUsername());
			if (user.getSex() == 0) {
				iv_uname.setImageResource(R.drawable.user_admin_name);
			} else {
				iv_uname.setImageResource(R.drawable.user_admin_name);
			}
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_uname:
			startActivityForResult(new Intent(this, MyselfActivity.class),19);
			break;
		case R.id.iv_back:
		case R.id.iv_back2:
			finish();
			break;
		case R.id.ll_passwd:
			startActivity(new Intent(this, ModPasswordActivity.class));
			break;
		case R.id.ll_address:
			startActivity(new Intent(this, AddressManageActivity.class));
			break;
		case R.id.btn_exit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("确定退出吗");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
				//	appContext.setUser(null);
					Intent data = new Intent();
					setResult(11);
					finish();
				}
			
			});
			
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.create();
			builder.show();
			break;
		default:
			break;
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		      if(data!=null){
		    	  String newname = data.getStringExtra("newname");
		    	  tv_uname.setText(newname);
		      }
	}
}
