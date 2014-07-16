package com.example.cvtd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;
import android.app.Activity;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ShortestPath extends Activity {
	GPSTracker gps;
	private double latitude;
	private double longitude;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_gps);
	}
	public void loadRoutes(View v)
	{
		
		gps = new GPSTracker(this);
		// check if GPS enabled		
		if(gps.canGetLocation()){
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
		}else{
			// can't get location i.e. GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}	
		TestAdapter mDbHelper = new TestAdapter(this);         
		mDbHelper.createDatabase();       
		mDbHelper.open();  
		Cursor testdata = mDbHelper.getLocInfo();
		HashMap<String, Double> map = new HashMap<String, Double>();
		if (testdata != null) {
			if (testdata.moveToFirst()) {
				do {
					String id1 = GetColumnValue(testdata, "_id");
					String latis = GetColumnValue(testdata, "Lat");
					String longt = GetColumnValue(testdata, "Longt");
					double dist = calcGeoDistance(latitude,longitude,Double.parseDouble(latis),Double.parseDouble(longt));
					//Log.e("DISTANCE", Double.toString(dist));
					map.put(id1, dist);
				} while (testdata.moveToNext());
			}
		}
		 ValueComparator bvc =  new ValueComparator(map);
	     TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
	     sorted_map.putAll(map);
	     String[] keys=new String[sorted_map.size()];   
	     sorted_map.keySet().toArray(keys);  
	     for (int i=keys.length-1;i>-1;i--) { // loop DESCENDINGLY  
	         String key=keys[i];  
	         Double value = sorted_map.get(key);  
	        // Log.e("Valuesss", value);
	     } 
		Toast.makeText(getApplicationContext(), "The nearest routes are"+sorted_map.lastKey(),
				 Toast.LENGTH_LONG).show();

	}

	public static String GetColumnValue(Cursor cur, String ColumnName) {
		try {
			String result = cur.getString(cur.getColumnIndex(ColumnName));
			//Log.e("RESULT", result);
			return result ;

		} catch (Exception ex) {
			return "";
		}
	}

	private double calcGeoDistance(final double lat1, final double lon1, final double lat2, final double lon2)
	{
		double distance = 0.0;
		try
		{
			final float[] results = new float[3];
			Location.distanceBetween(lat1, lon1, lat2, lon2, results);
			distance = results[0];
		}
		catch (final Exception ex)
		{
			distance = 0.0;
		}
		return distance;
	}

}
