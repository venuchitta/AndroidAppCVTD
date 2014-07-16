package com.example.cvtd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.example.time.*;

public class timeTableGenerate extends Activity {
	TestAdapter mDbHelper; 
	Calendar cal = Calendar.getInstance(); 
	String delims = "-";
    private final Context mContext;

	public timeTableGenerate(Context context,TestAdapter mDbHelper) {
	        this.mContext = context; 
	        this.mDbHelper = mDbHelper;
	}

	@SuppressLint("UseSparseArrays")
	public HashMap<Integer,Time> timesBus(String busNo) throws NumberFormatException, ParseException{
		String str = null;
		mDbHelper.createDatabase();       
		mDbHelper.open();  
		Cursor testdata = mDbHelper.getBusTimes(busNo);
		HashMap<Integer, Time> hm = new HashMap<Integer,Time>();
		if (testdata != null) {
			if (testdata.moveToFirst()) {
				do {
					str = GetColumnValue(testdata, "stopString");
					Log.e("Bus String",str);
				} while (testdata.moveToNext());
			}
		}
		testdata.close();
		String [] bus_info = str.split(delims);
		
		for (int i=0;i<bus_info.length/2;i++)
		{
			String temp = bus_info [2*i+1];
			Time timeTemp = GetDayTime(busNo).add(Integer.parseInt(bus_info [2*i+1]));
			Log.e("temp i.e Int",bus_info[2*i]);
			timeTemp.toString();
			hm.put(Integer.parseInt(bus_info[2*i]),timeTemp );
		}
		return hm;
	}

	public String GetColumnValue(Cursor cur, String ColumnName) {
		try {
			String result = cur.getString(cur.getColumnIndex(ColumnName));
			return result ;
		} catch (Exception ex) {
			return "";
		}
	}

	@SuppressLint("SimpleDateFormat")
	public Time GetDayTime (String str) throws ParseException
	{
		Cursor testdata = mDbHelper.getBusArrivalInfo(str,getCurrDay());
		ArrayList <Time> arr = new ArrayList<Time>();
		if (testdata != null) {
			if (testdata.moveToFirst()) {
				do {
					String times=GetColumnValue(testdata, "time").trim();
					String[] values = times.split("[: ]");
					Time time = new Time(Integer.parseInt(values[0]),
							Integer.parseInt(values[1]),values[2].trim());
					arr.add(time); 
				} while (testdata.moveToNext());
			}
		}
		testdata.close();
		SimpleDateFormat df = new SimpleDateFormat("HH-mm-aa");
		String date = df.format(Calendar.getInstance().getTime());
		String[] dateTokens = date.split(delims);
		Time presTime = new Time(Integer.parseInt(dateTokens[0]),
				Integer.parseInt(dateTokens[1]),dateTokens[2]);
		for (int i=0;i<arr.size();i++)
		{
			int com=arr.get(i).compare(arr.get(i),presTime);
			if (com == -1)
				return arr.get(i);
		}
		return null;
	}

	public String getCurrDay()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		// If current day is Sunday, day=1. Saturday, day=7.
		if (day >=1 && day <=5)
			return "MF";
		else if (day== 7)
			return "S";
		return "S";
	}

	public Context getmContext() {
		return mContext;
	}
}
