package com.android.inrmeter.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.android.inrmeter.activity.R;
import com.android.inrmeter.activity.HospitalList.GetPlaces;
import com.android.inrmeter.activity.R.id;
import com.android.inrmeter.activity.R.layout;
import com.android.inrmeter.activity.R.menu;
import com.android.inrmeter.adaptor.InrAdaptor;
import com.android.inrmeter.model.Hospital;
import com.android.inrmeter.model.Inr;
import com.android.inrmeter.model.Place;
import com.android.inrmeter.util.Database;
import com.android.inrmeter.util.PlaceService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

public class InrList extends Activity {
	private ActionBar actionBar;
	private DatePicker dpResult;
	private DatePicker dpResult2;
	static final int DATE_DIALOG_ID = 998;
	static final int DATE_DIALOG_ID2 = 999;
	private int year;
	private int month;
	private int day;
	private int year2;
	private int month2;
	private int day2;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.inr_list);
		setCurrentDateOnView();
		
		lv = (ListView) findViewById(com.android.inrmeter.activity.R.id.listView1);

		List<Inr> inrList = new ArrayList<Inr>();

		new GetINR(this, lv, inrList).execute();

	}
	public void setCurrentDateOnView() {
		 
		
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		dpResult2 = (DatePicker) findViewById(R.id.dpResult2);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		year2 = c.get(Calendar.YEAR);
		month2 = c.get(Calendar.MONTH);
		day2 = c.get(Calendar.DAY_OF_MONTH);
	
		// set current date into datepicker
		dpResult.init(year, month, day, null);
		dpResult2.init(year2, month2, day2, null);
 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.inrlist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		case com.android.inrmeter.activity.R.id.action_inrdate:
			showDialog(DATE_DIALOG_ID);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		case DATE_DIALOG_ID2:
			return new DatePickerDialog(this, datePickerListener2, year2, month2, day2);
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year2 = selectedYear;
			month2 = selectedMonth;
			day2 = selectedDay;

			System.out.println(year2+" " +month2+" "+day2);

			// set selected date into datepicker also
			dpResult2.init(year2, month2, day2, null);
			lv = (ListView) findViewById(com.android.inrmeter.activity.R.id.listView1);

			List<Inr> inrList = new ArrayList<Inr>();

			
			
			new GetINRBetweenTwoDate(InrList.this, lv, inrList, year, month, day, year2, month2, day2).execute();
		}
	};
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			System.out.println(year+" " +month+" "+day);

			// set selected date into datepicker also
			
			dpResult.init(year, month, day, null);
			showDialog(DATE_DIALOG_ID2);

		}
	};

	class GetINR extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;
		private Context context;
		private String[] placeName;
		private String[] imageUrl;
		private ListView listView;
		private List<Inr> inrList;

		public GetINR(Context context, ListView lv, List<Inr> inrList) {

			this.context = context;
			this.listView = lv;
			this.inrList = inrList;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			listView.setAdapter(new InrAdaptor(context,
					com.android.inrmeter.activity.R.layout.inr_list_row,
					inrList));

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
			Database db = new Database(getApplicationContext());
			inrList = db.InrList();

			return null;
		}

	}
	class GetINRBetweenTwoDate extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;
		private Context context;
		private String[] placeName;
		private String[] imageUrl;
		private ListView listView;
		private List<Inr> inrList;
		private int year;
		private int year2;
		private int day;
		private int day2;
		private int month;
		private int month2;
		public GetINRBetweenTwoDate(Context context, ListView lv, List<Inr> inrList,int year,int month,int day,int year2,int month2,int day2) {

			this.context = context;
			this.listView = lv;
			this.inrList = inrList;
			this.year = year;
			this.year2 = year2;
			this.month = month;
			this.month2 = month2;
			this.day = day;
			this.day2 = day2;
			this.month2 = month2;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			listView.setAdapter(new InrAdaptor(context,
					com.android.inrmeter.activity.R.layout.inr_list_row,
					inrList));

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
			Database db = new Database(getApplicationContext());
			//inrList = db.InrListBetweenTwoDate(year+"-"+month+"-"+day, year2+"-"+month2+"-"+day2);
			inrList = db.InrListBetweenTwoDate("2014-01-01", "2014-12-25");
		    //System.out.println(year+"-"+month+"-"+day);
			//System.out.println(year2+"-"+month2+"-"+day2);
			//System.out.println("INR VALUE: "+inrList.get(0).getInrValue());
			return null;
		}

	}	
}
