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
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.HttpRequests.HttpRequestType;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModPasswordActivity extends Activity implements
		OnClickListener {
	private ImageView iv_back;
	private TextView tv_back;
	private EditText et_oldpassword, et_newpassword, et_newpassword2;
	private Button btn_confirm;
	private String username;
	private User user;
	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	void init() {
		setContentView(R.layout.modpasswd_activity);
		iv_back = (ImageView) findViewById(R.id.iv_about);
		tv_back = (TextView) findViewById(R.id.tv_slife);
		et_oldpassword = (EditText) findViewById(R.id.oldpassword);
		et_newpassword = (EditText) findViewById(R.id.newpassword);
		et_newpassword2 = (EditText) findViewById(R.id.newpassword2);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		btn_confirm.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_back.setOnClickListener(this);

		appContext = (AppContext) getApplicationContext();
		user = appContext.getUser();
		username = user.getUsername();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_about:
		case R.id.tv_slife:
			finish();
			break;
		case R.id.button_confirm:

			String oldpassword = et_oldpassword.getText().toString();
			String newpassword = et_newpassword.getText().toString();
			String newpassword2 = et_newpassword2.getText().toString();

			if (oldpassword.equals("") || newpassword.equals("") || newpassword2.equals("")) {
				Toast.makeText(ModPasswordActivity.this,
						"填写信息不完整", Toast.LENGTH_LONG)
						.show();
				break;
			}

			if (newpassword.equals(newpassword2)) {
				HttpRequests req = new HttpRequests();
				req.nrc = new RequestConstant();
				// post请求
				req.nrc.setType(HttpRequestType.POST);

				RequestConstant.requestUrl = UrlConstant.CHANGEPASSWORDURL;
				RequestConstant.context = this;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", username);
				map.put("oldpassword", oldpassword);
				map.put("newpassword", newpassword);
				RequestConstant.map = map;
				req.context = this;

				req.getServer(new Netcallback() {

					public void preccess(Object res, boolean flag) {
						if (res != null) {
							try {
								JSONObject object = new JSONObject((String) res);
								String success = object.optString("result");
								if (success.equals("1")) {
									Toast.makeText(ModPasswordActivity.this,
											"修改成功", Toast.LENGTH_SHORT).show();
									finish();
								} else {
									Toast.makeText(ModPasswordActivity.this,
											"修改失败，请核对你的当前密码",
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, req.nrc);

			} else {
				Toast.makeText(this, "两次输入密码不正确，请重新输入", Toast.LENGTH_SHORT)
						.show();
				et_newpassword.setText("");
				et_newpassword2.setText("");
			}

			break;
		default:
			break;
		}

	}

}
