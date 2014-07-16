package com.example.cvtd;

import java.io.IOException; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase; 
import android.util.Log; 

public class TestAdapter  
{ 
	public TestAdapter(Context context)  
	{ 
		this.mContext = context; 
		mDbHelper = new DataBaseHelper(mContext); 
	}
	protected static final String TAG = "DataAdapter";
	private final Context mContext; 
	private SQLiteDatabase mDb; 
	private DataBaseHelper mDbHelper; 
	public TestAdapter createDatabase() throws SQLException  
	{ 
		try  
		{ 
			mDbHelper.createDataBase(); 
		}  
		catch (IOException mIOException)  
		{ 
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase"); 
			throw new Error("UnableToCreateDatabase"); 
		} 
		return this; 
	}

	public TestAdapter open() throws SQLException  
	{ 
		try  
		{ 
			mDbHelper.openDataBase(); 
			mDbHelper.close(); 
			mDb = mDbHelper.getReadableDatabase(); 
		}  
		catch (SQLException mSQLException)  
		{ 
			Log.e(TAG, "open >>"+ mSQLException.toString()); 
			throw mSQLException; 
		} 
		return this; 
	} 

	public void close()  
	{ 
		mDbHelper.close(); 
	} 

	public Cursor getTestData(String str) 
	{ 
		try 
		{ 

			String bus_str = StrMapper(str.trim());
			String sql ="SELECT _id,descr FROM bus_info WHERE bus_no is '"+bus_str+"'"; 
			Cursor mCur = mDb.rawQuery(sql, null); 
			if (mCur!=null) 
				mCur.moveToFirst();
			return mCur; 
		} 
		catch (SQLException mSQLException)  
		{ 
			throw mSQLException; 
		} 
	}

	public Cursor getLocInfo() 
	{ 
		try 
		{ 
			String sql ="SELECT _id,Lat,Longt FROM bus_table";
			Cursor mCur = mDb.rawQuery(sql, null); 
			if (mCur!=null) 
				mCur.moveToFirst();
			return mCur; 
		} 
		catch (SQLException mSQLException)  
		{ 
			throw mSQLException; 
		} 
	}

	public Cursor getBusArrivalInfo(String str, String day) 
	{ 
		try 
		{ 
			String bus_str = StrMapper(str.trim());
			String sql ="SELECT _id,time FROM busArrival WHERE busNo is '"+bus_str+"' "+"AND days is '"+day.trim()+"'";
			Cursor mCur = mDb.rawQuery(sql, null); 
			if (mCur!=null) 
				mCur.moveToFirst();
			return mCur; 
		} 
		catch (SQLException mSQLException)  
		{ 
			throw mSQLException; 
		} 
	}
	
	public Cursor getBusTimes(String str) 
	{ 
		try 
		{ 
			String sql ="SELECT _id,stopString FROM busTime WHERE busNo is '"+str.trim()+"'"; 
			Cursor mCur = mDb.rawQuery(sql, null); 
			if (mCur!=null) 
				mCur.moveToFirst();
			return mCur; 
		} 
		catch (SQLException mSQLException)  
		{ 
			throw mSQLException; 
		} 
	}

	public String StrMapper (String str)
	{
		String[] desc = new String[]{"Route 1", "Route 2", "Route 3", "Route 4","Route 5",
				"Route 6","Route 7","Route 8","Route 9","Route 10","Route 11","Route 1E",
				"Route CVN","Route CVS Express","Route CVS Offpeak","Route CVS Off",
		"Route Franklin Connection Country"};
		String[] num = new String[]{"1","2","3","4","5","6","7","8","9","10","11",
				"1E","cvn","cvs-express","cvs-offpeak","cvs-peak","frankin"};
		for (int i=0;i<desc.length;i++)
			if (str.equals(desc[i]))
				return num[i];
		return "1"; 	 
	}
} 




