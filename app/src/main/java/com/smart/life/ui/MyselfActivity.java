package com.smart.life.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.User;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.utils.AddressPicker;
import com.smart.life.utils.DateTimePickDialogUtil;
import com.smart.life.utils.HttpRequests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyselfActivity extends Activity implements OnClickListener {
	private ImageView iv_back;
	private TextView tv_back, tv_address;
	private EditText et_username, et_birth, et_phone;
	private RadioButton rbtn_male, rbtn_female;
	private Button btn_confirm;
	private String oldname;
	private AppContext appContext;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	void init() {
		setContentView(R.layout.myself_activity);
		iv_back = (ImageView) findViewById(R.id.iv_about);
		tv_back = (TextView) findViewById(R.id.tv_slife);
		tv_address = (TextView) findViewById(R.id.tv_address);
		et_username = (EditText) findViewById(R.id.et_uname);
		et_birth = (EditText) findViewById(R.id.et_birth);
		et_phone = (EditText) findViewById(R.id.et_phone);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		rbtn_male = (RadioButton) findViewById(R.id.radioMale);
		rbtn_female = (RadioButton) findViewById(R.id.radioFemale);

		iv_back.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		et_birth.setOnClickListener(this);
		tv_address.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);

		appContext = (AppContext) getApplicationContext();
		user = appContext.getUser();
		if (user != null) {
			et_username.setText(user.getUsername());
			rbtn_male.setChecked(user.getSex() == 1);
			rbtn_female.setChecked(user.getSex() == 0);
			et_birth.setText(user.getBirth());
			et_phone.setText(user.getPhone());
			tv_address.setSingleLine(false);
			tv_address.setLines(2);
			tv_address.setText(user.getAddress());
		}
		oldname = user.getUsername();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_about:
		case R.id.tv_slife:
			finish();
			break;
		case R.id.btn_confirm:
			HttpRequests req = new HttpRequests();
			req.nrc = new RequestConstant();
			// post请求
			req.nrc.setType(HttpRequests.HttpRequestType.POST);
			final String newname = et_username.getText().toString();

			RequestConstant.requestUrl = UrlConstant.CHANGEUSENAMEURL;
			RequestConstant.context = this;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newname", newname);
			map.put("oldname", oldname);
			map.put("phone", et_phone.getText().toString());
			map.put("birth", et_birth.getText().toString());
			map.put("address", tv_address.getText().toString());
			int sex;
			if (rbtn_male.isChecked())
				sex = 1;
			else
				sex = 0;
			map.put("sex", sex);
			RequestConstant.map = map;

			req.getServer(new Netcallback() {

				public void preccess(Object res, boolean flag) {
					if (res != null) {
						try {
							JSONObject object = new JSONObject((String) res);
							String success = object.optString("success");
							if (success.equals("1")) {
								Toast.makeText(MyselfActivity.this,
										"修改成功", Toast.LENGTH_SHORT).show();
								user.setUsername(newname);
								appContext.setUser(user);
								Intent data = new Intent();
								data.putExtra("newname", newname);
								setResult(19, data);
								finish();
							} else {
								Toast.makeText(MyselfActivity.this,
										"出现不知名的错误", Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}, req.nrc);

			break;
		case R.id.et_birth:
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(	this, "1970-01-01");
			dateTimePicKDialog.dateTimePicKDialog(et_birth);
			break;
		case R.id.tv_address:
			AddressPicker addrPicker = new AddressPicker(appContext.getAddress_info(), this);
			addrPicker.addrPickDialog(tv_address);
			break;
		default:
			break;
		}

	}
}
