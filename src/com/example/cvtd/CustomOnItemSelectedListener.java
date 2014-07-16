package com.example.cvtd;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


public class CustomOnItemSelectedListener extends Activity implements OnItemSelectedListener {
    private final Context mContext; 
    public CustomOnItemSelectedListener(Context context)  
    { 
        this.mContext = context; 
    } 
  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
	Toast.makeText(parent.getContext(), 
		"Selected Route is : " + parent.getItemAtPosition(pos).toString(),
		Toast.LENGTH_SHORT).show();
  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
public Context getmContext() {
	return mContext;
}
 
}

