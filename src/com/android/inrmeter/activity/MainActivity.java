package com.android.inrmeter.activity;

import com.android.inrmeter.activity.R;
import com.android.inrmeter.activity.R.id;
import com.android.inrmeter.activity.R.layout;
import com.android.inrmeter.activity.R.menu;
import com.android.inrmeter.activity.R.raw;
import com.android.inrmeter.activity.InrList;
import com.android.inrmeter.activity.MainActivity;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		actionBar = getActionBar();

		// Action Bar Başlığı saklar
		actionBar.setDisplayShowTitleEnabled(false);

		Button button4 = (Button) findViewById(R.id.measurebutton);
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, InrMeter.class);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case com.android.inrmeter.activity.R.id.action_inrhistory:
			Intent intent = new Intent(MainActivity.this, InrList.class);
			startActivity(intent);
			break;

		case com.android.inrmeter.activity.R.id.action_inrhospital:
			Intent intent2 = new Intent(MainActivity.this, HospitalList.class);
			startActivity(intent2);
			break;
		case com.android.inrmeter.activity.R.id.action_inrpharmacy:
			Intent intent3 = new Intent(MainActivity.this, PharmacyList.class);
			startActivity(intent3);
			break;
		case com.android.inrmeter.activity.R.id.action_about:
			Intent intent4 = new Intent(MainActivity.this, About.class);
			startActivity(intent4);
			break;
		case com.android.inrmeter.activity.R.id.action_help:
			Intent intent5 = new Intent(MainActivity.this, Help.class);
			startActivity(intent5);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
