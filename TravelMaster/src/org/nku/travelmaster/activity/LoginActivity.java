package org.nku.travelmaster.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.nku.travelmaster.internet.WebAccessUtils;
import org.nku.travelmaster.po.Users;
import org.nku.travelmaster.utils.ExitApplication;
import org.nku.travelmaster.utils.ExitDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText txtAccount, txtPassword;
	private Button btnRegister, btnLogin;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		
		this.setContentView(R.layout.activity_login);

		this.txtAccount = (EditText) this.findViewById(R.id.txtAccount);
		this.txtPassword = (EditText) this.findViewById(R.id.txtPassword);

		this.btnRegister = (Button) this.findViewById(R.id.btnRegister);
		this.btnLogin = (Button) this.findViewById(R.id.btnLogin);

		this.btnRegister.setOnClickListener(new ViewOcl());
		this.btnLogin.setOnClickListener(new ViewOcl());

	}

	private void BtnRegister() {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	private void BtnLogin() {
		String account = txtAccount.getText().toString().trim();
		String password = txtPassword.getText().toString().trim();

		if (account.length() == 0 || password.length() == 0) {
			ToastMessage("请输入用户名和密码");
			return;
		}

		new LoginTask().execute(account, password);
	}

	private void ToastMessage(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	private class ViewOcl implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnRegister:
				BtnRegister();
				break;
			case R.id.btnLogin:
				BtnLogin();
				break;
			default:
				break;
			}
		}
	}

	private class LoginTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Users user = new Users();
			user.setUname(arg0[0]);
			user.setUpwd(arg0[1]);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-ddhh:mm:ss")
					.create();
			String user_data = gson.toJson(user);

			List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
			lstNameValuePairs.add(new BasicNameValuePair("user_data", user_data));

			String response = WebAccessUtils.httpRequest("AndroidLoginServlet",
					lstNameValuePairs);

			Users u = null;
			u = gson.fromJson(response, Users.class);

			if (u != null) {
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", u);
				intent.putExtras(bundle);
				startActivity(intent);
				//结束当前活动
				finish();
				return "登录成功!";
			} else {
				return "账号或密码错误!";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			alertDialog(result);
		}
	}

	protected void alertDialog(String msg) {
		AlertDialog.Builder builder = new Builder(LoginActivity.this);

		builder.setTitle("提示");

		builder.setMessage(msg);

		builder.setNegativeButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			ExitDialog.ShowExitDialog(LoginActivity.this);
		}
		return super.onKeyDown(keyCode, event);
	}
}
