package com.android.inrmeter.activity;





import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Splash2 extends Activity {
	
	private ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		//actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.splash2);

		int secondsDelayed = 1;
		new Handler().postDelayed(new Runnable() {
			public void run() {

				startActivity(new Intent(Splash2.this, MainActivity.class));
				finish();
			}
		}, secondsDelayed * 2000);
	}
	  


}