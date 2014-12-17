package com.android.inrmeter.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.inrmeter.adaptor.HospitalAdaptor;
import com.android.inrmeter.adaptor.InrAdaptor;
import com.android.inrmeter.model.Hospital;
import com.android.inrmeter.model.Place;
import com.android.inrmeter.util.GPSTracker;
import com.android.inrmeter.util.PlaceService;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class HospitalList extends Activity {

	private static double lat;
	private static double lng;
	private static List<Hospital> hospitals;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.hospital_list);

		hospitals = new ArrayList();
		ListView lv;
		lv = (ListView) findViewById(com.android.inrmeter.activity.R.id.hospitalList);
		new GetPlaces(this, lv).execute();

	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void HospitalSetAdaptor(ListView lv) {
		lv.setAdapter(new HospitalAdaptor(this,
				com.android.inrmeter.activity.R.layout.hospital_list_row,
				hospitals));
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public static List<Hospital> getHospitals() {
		return hospitals;
	}

	public static void setHospitals(List<Hospital> hospitals) {
		HospitalList.hospitals = hospitals;
	}

	class GetPlaces extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;
		private Context context;
		private String[] placeName;
		private String[] imageUrl;
		private ListView listView;

		public GetPlaces(Context context, ListView lv) {

			this.context = context;
			this.listView = lv;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			HospitalSetAdaptor(listView);

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setCancelable(true);
			dialog.setMessage("Loading..");
			dialog.isIndeterminate();
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			double latitude;
			double longitude;
			GPSTracker gps = new GPSTracker(context);
			Location location = gps.getLocation();

			PlaceService service = new PlaceService(
					"AIzaSyAzDQavF33FI9g7HbApR_xs42dFrQiDGdc");
			if(location==null){
				latitude = 40.991555;
				longitude = 28.797495;
			}else{
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
			List<Place> findPlaces = service.findPlaces(latitude, longitude,
					"hospital"); // hospiral for
			// hospital
			// atm for ATM

			placeName = new String[findPlaces.size()];
			imageUrl = new String[findPlaces.size()];

			for (int i = 0; i < findPlaces.size(); i++) {

				Place placeDetail = findPlaces.get(i);
				placeDetail.getIcon();

				System.out.println(placeDetail.getName());
				placeName[i] = placeDetail.getName();
				hospitals.add(new Hospital(placeDetail.getName()));
				imageUrl[i] = placeDetail.getIcon();

			}
			return null;
		}

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

}