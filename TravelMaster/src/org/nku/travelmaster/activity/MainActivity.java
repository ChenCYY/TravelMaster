package org.nku.travelmaster.activity;

import org.nku.travelmaster.utils.ExitApplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {
	// fragment
	private HomepageFragment homepageFragment;
	private HotattractionFragment hotattractionFragment;
	private ForumFragment forumFragment;
	private UsercenterFragment usercenterFragment;
	// 消息界面布局
	private View homepageLayoutView;
	private View hotattractionLayoutView;
	private View forumLayoutView;
	private View usercenterLayoutView;
	// 在Tab布局上显示图标的控件
	private ImageView homepageImageView;
	private ImageView hotattractionImageView;
	private ImageView forumImageView;
	private ImageView usercenterImageView;
	// 在Tab布局上显示标题的控件
	private TextView homepageTextView;
	private TextView hotattractionTextView;
	private TextView forumTextView;
	private TextView usercenterTextView;

	private FragmentManager fragmentManager;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(arg0);
		ExitApplication.getInstance().addActivity(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 初始化布局元素
		initViews();
		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(3);
	}

	private void initViews() {
		homepageLayoutView = findViewById(R.id.homepage_layout);
		hotattractionLayoutView = findViewById(R.id.hotattraction_layout);
		forumLayoutView = findViewById(R.id.forum_layout);
		usercenterLayoutView = findViewById(R.id.usercenter_layout);

		homepageImageView = (ImageView) findViewById(R.id.homepage_image);
		hotattractionImageView = (ImageView) findViewById(R.id.hotattraction_image);
		forumImageView = (ImageView) findViewById(R.id.forum_image);
		usercenterImageView = (ImageView) findViewById(R.id.usercenter_image);

		homepageTextView = (TextView) findViewById(R.id.homepage_text);
		hotattractionTextView = (TextView) findViewById(R.id.hotattraction_text);
		forumTextView = (TextView) findViewById(R.id.forum_text);
		usercenterTextView = (TextView) findViewById(R.id.usercenter_text);

		homepageLayoutView.setOnClickListener(this);
		hotattractionLayoutView.setOnClickListener(this);
		forumLayoutView.setOnClickListener(this);
		usercenterLayoutView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.homepage_layout:
			setTabSelection(0);
			break;
		case R.id.hotattraction_layout:
			setTabSelection(1);
			break;
		case R.id.forum_layout:
			setTabSelection(2);
			break;
		case R.id.usercenter_layout:
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了tab时，改变控件的图片和文字颜色
			homepageImageView.setImageResource(R.drawable.homepage_selected);
			homepageTextView.setTextColor(Color.WHITE);
			if (homepageFragment == null) {
				// 如果Fragment为空，则创建一个并添加到界面上
				homepageFragment = new HomepageFragment();
				transaction.add(R.id.content, homepageFragment);
			} else {
				// 如果Fragment不为空，则直接将它显示出来
				transaction.show(homepageFragment);
			}
			break;
		case 1:
			hotattractionImageView
					.setImageResource(R.drawable.hotattraction_selected);
			hotattractionTextView.setTextColor(Color.WHITE);
			if (hotattractionFragment == null) {
				hotattractionFragment = new HotattractionFragment();
				transaction.add(R.id.content, hotattractionFragment);
			} else {
				transaction.show(hotattractionFragment);
			}
			break;
		case 2:
			forumImageView.setImageResource(R.drawable.forum_selected);
			forumTextView.setTextColor(Color.WHITE);
			if (forumFragment == null) {
				forumFragment = new ForumFragment();
				transaction.add(R.id.content, forumFragment);
			} else {
				transaction.show(forumFragment);
			}
			break;
		case 3:
			usercenterImageView
					.setImageResource(R.drawable.usercenter_selected);
			usercenterTextView.setTextColor(Color.WHITE);
			if (usercenterFragment == null) {
				usercenterFragment = new UsercenterFragment();
				transaction.add(R.id.content, usercenterFragment);
			} else {
				transaction.show(usercenterFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	private void clearSelection() {
		homepageImageView.setImageResource(R.drawable.homepage_unselected);
		homepageTextView.setTextColor(Color.parseColor("#82858b"));
		hotattractionImageView
				.setImageResource(R.drawable.hotattraction_unselected);
		hotattractionTextView.setTextColor(Color.parseColor("#82858b"));
		forumImageView.setImageResource(R.drawable.forum_unselected);
		forumTextView.setTextColor(Color.parseColor("#82858b"));
		usercenterImageView.setImageResource(R.drawable.usercenter_unselected);
		usercenterTextView.setTextColor(Color.parseColor("#82858b"));
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (homepageFragment != null) {
			transaction.hide(homepageFragment);
		}
		if (hotattractionFragment != null) {
			transaction.hide(hotattractionFragment);
		}
		if (forumFragment != null) {
			transaction.hide(forumFragment);
		}
		if (usercenterFragment != null) {
			transaction.hide(usercenterFragment);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			org.nku.travelmaster.utils.ExitDialog.ShowExitDialog(MainActivity.this);
		}
		return super.onKeyDown(keyCode, event);
	}
}
