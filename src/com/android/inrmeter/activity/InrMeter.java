package com.android.inrmeter.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.android.inrmeter.activity.HospitalList.GetPlaces;
import com.android.inrmeter.model.Hospital;
import com.android.inrmeter.model.Inr;
import com.android.inrmeter.model.Place;
import com.android.inrmeter.util.CircularSeekBar;
import com.android.inrmeter.util.Database;
import com.android.inrmeter.util.PlaceService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InrMeter extends Activity {

	private CircularSeekBar seekbar;
	private TextView textView;
	private TextView measureText;
	
	private Button continuebutton;
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
		setContentView(R.layout.inr_meter);
		seekbar = (CircularSeekBar) findViewById(com.android.inrmeter.activity.R.id.circularSeekBar1);
		textView = (TextView) findViewById(com.android.inrmeter.activity.R.id.inrMeterValue);
		measureText = (TextView) findViewById(com.android.inrmeter.activity.R.id.measureText);
		
		final MediaPlayer sound1 = MediaPlayer.create(this, R.raw.heart_monitor);
		final MediaPlayer sound2 = MediaPlayer.create(this, R.raw.attention);
		continuebutton = (Button) findViewById(com.android.inrmeter.activity.R.id.continuebutton);
		seekbar.setMax(700);
		new PlayMusic(this,sound1).execute();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 700; i = i + 1) {
					final int value = i;
					doFakeWork();
					seekbar.post(new Runnable() {
						@Override
						public void run() {

							seekbar.setProgress(value);
							if (value == 700) {

								Random r = new Random();
								double rangeMax = 4.5;
								double rangeMin = 0.8;
								double randomValue = rangeMin
										+ (rangeMax - rangeMin)
										* r.nextDouble();
								String upToNCharacters = String.valueOf(
										randomValue).substring(
										0,
										Math.min(String.valueOf(randomValue)
												.length(), 4));
								textView.setText(upToNCharacters);
								Database db = new Database(getApplicationContext());
								Inr inr = new Inr();
								
								DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
								Calendar cal = Calendar.getInstance();
								String inrDate = dateFormat.format(cal.getTime());
								String inrTime = dateFormat2.format(cal.getTime());
								db.InsertInr(new Inr(Double.parseDouble(upToNCharacters),inrDate,inrTime));
								double avginr = db.avgINR();
								String avginrd = String.valueOf(
										avginr).substring(
										0,
										Math.min(String.valueOf(avginr)
												.length(), 4));
								if (avginr >= 3.5) {
									measureText.setText("Your Average INR value: \n"+avginrd+" is high!");
									measureText.setTextColor(Color.RED);
									continuebutton.setVisibility(1);
									sound1.stop();
									new PlayMusic(getApplicationContext(),sound2).execute();
									int secondsDelayed = 1;

									new Handler().postDelayed(new Runnable() {
										public void run() {

											startActivity(new Intent(InrMeter.this, InrMeterNext.class));
											finish();
										}
									}, secondsDelayed * 2000);
									
								} else {
									measureText.setText("Your Average INR value: \n"+avginrd+" is normal.");
									sound1.stop();
									
								}
									
								
							}
						}
					});
				}
			}
		};
		new Thread(runnable).start();

		continuebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InrMeter.this, InrMeterNext.class);
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
	// Simulating something timeconsuming
	private void doFakeWork() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	class PlayMusic extends AsyncTask<Void, Void, Void> {


		private Context context;
		private MediaPlayer sound;

		public PlayMusic(Context context, MediaPlayer sound) {

			this.context = context;
			this.sound = sound;


		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);


		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			sound.start();
			return null;
		}

	}

}