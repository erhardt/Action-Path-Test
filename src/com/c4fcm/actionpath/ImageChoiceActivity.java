package com.c4fcm.actionpath;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ImageChoiceActivity extends FragmentActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_image_choice);

	    ImageButton[] images = {
	    		(ImageButton) findViewById(R.id.surveyFirstImage),
	    		(ImageButton) findViewById(R.id.surveySecondImage),
	    		(ImageButton) findViewById(R.id.surveyThirdImage)
	    };
	    for(int i=0;i<images.length; i++){
	    	images[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
				   Toast.makeText(ImageChoiceActivity.this,
					"ImageButton is clicked!", Toast.LENGTH_SHORT).show();
				}
			});
	    }
	    
	}
	
	public void onResume(){
    	super.onResume();
    	NotificationManager notificationManager =(NotificationManager) 
    			  getSystemService(NOTIFICATION_SERVICE);
    	notificationManager.cancel(0);
	}
}
