package com.android.inrmeter.util;


import java.util.ArrayList;
import java.util.List;

import com.android.inrmeter.model.Inr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "DBINR";
	private static final String TABLE_NAME = "TBLINR";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE "
				+ TABLE_NAME
				+ "(inrId INTEGER PRIMARY KEY, inrValue DOUBLE, inrDate TEXT, inrTime TEXT"
				+ ")";
		db.execSQL(sql);

	}

	public void InsertInr(Inr inr) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("inrValue", inr.getInrValue());
		values.put("inrDate", inr.getInrDate());
		values.put("inrTime", inr.getInrTime());

		db.insert(TABLE_NAME, DATABASE_NAME, values);
		db.close();

	}

	public List<Inr> InrListBetweenTwoDate(String date1, String date2) {
		List<Inr> inrs = new ArrayList<Inr>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		try {
//			cursor = db.query(TABLE_NAME, new String[] { "inrId", "inrValue",
//					"inrDate", "inrTime" }, null, null, null, null,
//					"inrId DESC");
			cursor = db.rawQuery("SELECT * FROM TBLINR WHERE inrDate BETWEEN '"
					+ date1 + "' AND '" + date2 + "'", null);
			while (cursor.moveToNext()) {
				Inr inr = new Inr();
				inr.setInrId(cursor.getInt(0));
				inr.setInrValue(cursor.getDouble(1));
				inr.setInrDate(cursor.getString(2));
				inr.setInrTime(cursor.getString(3));

				inrs.add(inr);

			}

		} finally {
			cursor.close();
			db.close();
		}

		return inrs;

	}

	public double avgINR() {

		List<Inr> inrs = new ArrayList<Inr>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		double avginr = 0.0;
		try {

			cursor = db.rawQuery(
					"SELECT avg(inrValue) FROM TBLINR WHERE inrDate", null);

			while (cursor.moveToNext()) {

				avginr = cursor.getDouble(0);

			}

		} finally {
			cursor.close();
			
			db.close();
		}

		return avginr;

	}

	public List<Inr> InrList() {
		List<Inr> inrs = new ArrayList<Inr>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_NAME, new String[] { "inrId", "inrValue",
					"inrDate", "inrTime" }, null, null, null, null,
					"inrId DESC");

			while (cursor.moveToNext()) {
				Inr inr = new Inr();
				inr.setInrId(cursor.getInt(0));
				inr.setInrValue(cursor.getDouble(1));
				inr.setInrDate(cursor.getString(2));
				inr.setInrTime(cursor.getString(3));

				inrs.add(inr);

			}

		} finally {
			cursor.close();
			db.close();
		}

		return inrs;

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
