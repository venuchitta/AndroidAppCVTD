package com.example.cvtd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
 
public class mainScreen extends Activity {
 
	ImageButton button,button2,button3;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		addListenerOnButton();
	}
 
	public void addListenerOnButton() {
		final Context context = this;
		button =  (ImageButton)findViewById(R.id.img1);
		button2 = (ImageButton)findViewById(R.id.img2);
		button3 = (ImageButton)findViewById(R.id.img3);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);   
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, MapDemoActivity.class);
                            startActivity(intent);   
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, ShortestPath.class);
                            startActivity(intent);   
			}
		});
	}
}