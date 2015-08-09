package com.smart.life.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.smart.life.R;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.utils.AddressPicker;
import com.smart.life.utils.DateTimePickDialogUtil;
import com.smart.life.utils.HttpRequests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText edittext_username, edittext_password, edittext_password_ag;
	private Button button_confirm;
	private ImageView imageview_back;
	private TextView textview_back;
	private TextView tv_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	void init() {
		setContentView(R.layout.register_activity);
		edittext_username = (EditText) findViewById(R.id.edittext_username);
		edittext_password = (EditText) findViewById(R.id.edittext_password);
		tv_address = (TextView) findViewById(R.id.tv_address);
		edittext_password_ag = (EditText) findViewById(R.id.edittext_password_ag);
		button_confirm = (Button) findViewById(R.id.button_confirm);
		imageview_back = (ImageView) findViewById(R.id.imageview_back);
		textview_back = (TextView) findViewById(R.id.textview_slife);

		button_confirm.setOnClickListener(this);
		imageview_back.setOnClickListener(this);
		textview_back.setOnClickListener(this);
		tv_address.setOnClickListener(this);

		tv_address.setSingleLine(false);
		tv_address.setLines(2);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview_back:
		case R.id.textview_slife:
			finish();
			break;
		case R.id.tv_address:
			AppContext context = (AppContext)getApplicationContext();
			AddressPicker addrPicker = new AddressPicker(context.getAddress_info(), this);
			addrPicker.addrPickDialog(tv_address);
			break;
		case R.id.button_confirm:// 确认注册
			final String username = edittext_username.getText().toString();
			final String password = edittext_password.getText().toString();
			final String password_ag = edittext_password_ag.getText().toString();
			final String address = tv_address.getText().toString();

			if (username.equals("") || password.equals("") || password_ag.equals("") || address.equals("")) {
				Toast.makeText(RegisterActivity.this,
						"填写信息不完整", Toast.LENGTH_LONG)
						.show();
				break;
			}

			if (!password.equals(password_ag)){
				Toast.makeText(RegisterActivity.this,
						"两次密码输入不一致", Toast.LENGTH_LONG)
						.show();
				break;
			}

			HttpRequests req = new HttpRequests();
			req.nrc = new RequestConstant();
			// post请求
			req.nrc.setType(HttpRequests.HttpRequestType.POST);
			RequestConstant.requestUrl = UrlConstant.REGISTERURL;
			RequestConstant.context = this;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", username);
			map.put("password", password);
			map.put("area", address);
			RequestConstant.map = map;
			req.context = this;

			req.getServer(new Netcallback() {
				public void preccess(Object res, boolean flag) {
					if (res != null) {
						try {
							JSONObject object = new JSONObject((String) res);
							String success = object.optString("result");
							if (success.equals("1")) {
								Intent data = new Intent();
								data.putExtra("username", username);
								data.putExtra("password", password);
								data.putExtra("area_name", address);
								setResult(RESULT_OK, data);
								Toast.makeText(RegisterActivity.this,
										"注册成功", Toast.LENGTH_LONG)
										.show();
								finish();
							} else {
								Toast.makeText(RegisterActivity.this,
										"该账号已被注册", Toast.LENGTH_LONG)
										.show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}, req.nrc);
			break;
		default:
			break;
		}
	}
}
