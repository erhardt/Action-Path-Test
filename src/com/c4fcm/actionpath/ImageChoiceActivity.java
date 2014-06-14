package com.c4fcm.actionpath;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
					Intent i = new Intent(arg0.getContext(), SurveyThanksActivity.class);
					startActivity(i); 
					finish();
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
	
	  //creates a PendingIntent
    public PendingIntent getPendingIntent() {
    	Log.v("INTENT","returning an intent for ImageChoiceActivity.class");
    	return PendingIntent.getActivity(this, 0, new Intent(this,
    			SurveyThanksActivity.class), 0);
    }

}
