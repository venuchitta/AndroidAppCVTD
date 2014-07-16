package com.example.cvtd;
import java.text.ParseException;
import java.util.HashMap;

import com.example.time.*;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {
	public Spinner spinner1;
	GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerSpinnerItemSelection();
	}

	public void addListenerSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		CustomOnItemSelectedListener cs =new CustomOnItemSelectedListener(this);
		spinner1.setOnItemSelectedListener(cs);
	}
	public void loadBusData(View v) throws NumberFormatException, ParseException
	{
		TestAdapter mDbHelper = new TestAdapter(this); 
		timeTableGenerate tg = new timeTableGenerate(this,mDbHelper);
		mDbHelper.createDatabase();       
		mDbHelper.open();
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		String str = spinner1.getSelectedItem().toString();
		Log.e("Main Busses", str); 
		HashMap <Integer,Time> busTimes=tg.timesBus(mDbHelper.StrMapper(str));
		Cursor testdata = mDbHelper.getTestData(str);
		mDbHelper.close();
		String[] desc = new String[]{"descr"};
		String [] times = toMapper(busTimes,desc.length);
		// List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		int [] toViewIDs = new int[]{R.id.field1};
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter
		(       this,		// Context
				R.layout.item_layout,	// Row layout template
				testdata,					// cursor (set of DB records to map)
				desc,			// DB Column names
				toViewIDs     // XML field
				);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(myCursorAdapter);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String[] toMapper (HashMap<Integer,Time> times, int length)
	{
		String [] res = new String[length];
		for (int i=0;i<length;i++)
			res[i] = "";
		for ( Integer key : times.keySet() ) 
		    res[key] = times.get(key).toString(); 
		return res;
	}
}
