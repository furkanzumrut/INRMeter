package com.android.inrmeter.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.inrmeter.adaptor.PharmacyAdaptor;
import com.android.inrmeter.adaptor.InrAdaptor;
import com.android.inrmeter.model.Pharmacy;
import com.android.inrmeter.model.Place;
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
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PharmacyList extends Activity {

	private static double lat;
	private static double lng;
	private static List<Pharmacy> hospitals;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.pharmacy_list);

		hospitals = new ArrayList();
		ListView lv;
		lv = (ListView) findViewById(com.android.inrmeter.activity.R.id.pharmacyList);
		new GetPlaces(this, lv).execute();

		lv.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {

		        Log.i("Click!", "Click"+position);
		       
		      
				//Intent intent = new Intent(PharmacyList.this, ActivityMap.class);
				//intent.putParcelableArrayListExtra("pharmacylist", (ArrayList<? extends Parcelable>) hospitals);
				//startActivity(intent);

		    }

		});

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

	public void PharmacySetAdaptor(ListView lv) {
		lv.setAdapter(new PharmacyAdaptor(this,
				com.android.inrmeter.activity.R.layout.pharmacy_list_row,
				hospitals));
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public static List<Pharmacy> getPharmacys() {
		return hospitals;
	}

	public static void setPharmacys(List<Pharmacy> hospitals) {
		PharmacyList.hospitals = hospitals;
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

			PharmacySetAdaptor(listView);

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
			LocationManager locationManager;
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null) {
				latitude = 40.991555;
				longitude = 28.797495;
			} else {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
			PlaceService service = new PlaceService(
					"AIzaSyAzDQavF33FI9g7HbApR_xs42dFrQiDGdc");
			List<Place> findPlaces = service.findPlaces(latitude, longitude,
					"pharmacy"); // hospiral for
			// hospital
			// atm for ATM

			placeName = new String[findPlaces.size()];
			imageUrl = new String[findPlaces.size()];

			for (int i = 0; i < findPlaces.size(); i++) {

				Place placeDetail = findPlaces.get(i);
				placeDetail.getIcon();

				System.out.println(placeDetail.getName());
				placeName[i] = placeDetail.getName();
				hospitals.add(new Pharmacy(placeDetail.getName()));
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