package com.gcl.library;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.library.R;
import com.gcl.service.HtmlSer;
import com.gcl.util.NetState;
import com.gcl.util.PreferenceUtil;
import com.gcl.util.ToastUtil;

public class LoginActivity extends Activity {

	private EditText name;
	private EditText pwd;
	private Button go;

	private LinearLayout login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		findView();
		
		startAnimation();

		setListener();
		
		tryLogin();
	}

	private void setListener() {
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!NetState.with(LoginActivity.this).detectNetState()) {
					ToastUtil.showMsg(LoginActivity.this, "网络未连接");
					return;
				}

				String lib_name = name.getText().toString();
				String lib_pwd = pwd.getText().toString();

				if (lib_name != null && lib_name.length() != 0
						&& lib_pwd != null && lib_pwd.length() != 0) {
					login(lib_name, lib_pwd);
				} else {
					ToastUtil.showMsg(LoginActivity.this, "请填写完整信息");
				}
			}
		});
	}

	private void tryLogin() {
		String name = PreferenceUtil.With(this).getItem("lib_name");
		String pwd = PreferenceUtil.With(this).getItem("lib_pwd");
		if (!NetState.with(this).detectNetState()) {
			ToastUtil.showMsg(this, "网络未连接");
			return;
		}
		if (name != null && pwd != null && name.length() != 0 && pwd.length() != 0) {
			login(name, pwd);
		}
	}

	private void login(final String name, final String pwd) {
		new AsyncTask<String, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				return HtmlSer.login(params[0], params[1]);
			}

			protected void onPostExecute(Boolean result) {
				if (!result) {
					ToastUtil.showMsg(LoginActivity.this, "密码或用户名错误");
				} else {
					PreferenceUtil.With(LoginActivity.this).setItem("lib_name", name);
					PreferenceUtil.With(LoginActivity.this).setItem("lib_pwd", pwd);
					goMainActivity();
				}
			};

		}.execute(name, pwd);
	}

	private void goMainActivity() {
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		finish();
	}

	private void startAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000);
		login.startAnimation(alphaAnimation);
	}

	private void findView() {
		name = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		go = (Button) findViewById(R.id.go);

		login = (LinearLayout) findViewById(R.id.login);

	}
}
