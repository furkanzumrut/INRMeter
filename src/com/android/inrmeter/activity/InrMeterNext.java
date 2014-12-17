package com.android.inrmeter.activity;

import com.android.inrmeter.util.CircularSeekBar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class InrMeterNext extends Activity {

	private Button nearbyhospitalbutton;
	private Button nearbypharmacybutton;
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
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.inr_meter_next);
		sendSMS("+905076284453", "Doctor, your patient has problem with his inr value. Immediately, contact him!");

		nearbyhospitalbutton = (Button) findViewById(com.android.inrmeter.activity.R.id.nearbyhospitalbutton);
		nearbypharmacybutton = (Button) findViewById(com.android.inrmeter.activity.R.id.nearbypharmacybutton);

		nearbyhospitalbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InrMeterNext.this,
						HospitalList.class);
				startActivity(intent);

			}
		});

		nearbypharmacybutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InrMeterNext.this,
						PharmacyList.class);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void sendSMS(String phoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
