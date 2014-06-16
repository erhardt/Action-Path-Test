package com.c4fcm.actionpath;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.graphics.Typeface;
import android.widget.ScrollView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ImageChoiceActivity extends FragmentActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//	    setContentView(R.layout.activity_image_choice);
//
//	    ImageButton[] images = {
//	    		(ImageButton) findViewById(R.id.surveyFirstImage),
//	    		(ImageButton) findViewById(R.id.surveySecondImage),
//	    		(ImageButton) findViewById(R.id.surveyThirdImage)
//	    };
//	    for(int i=0;i<images.length; i++){
//	    	images[i].setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					Intent i = new Intent(arg0.getContext(), SurveyThanksActivity.class);
//					startActivity(i); 
//					finish();
//				}
//			});
//	    }
	
		// Instantiate text for question and responses
		String question = new String("What\'s the best option for connecting the new Chuckie Harris Park to Broadway?");
		String responses[] = { "A. Street paint at intersection", "B. Gate over Cross St", "C. Grass corridor by Senior Ctr" };
		String images[] = { "option1_streetpaint", "option2_gate", "option3_grass" };
	
		// Build Survey Layout based on current question, responses and images
		ScrollView surveyLayout = buildSurveyLayout(question, responses, images);
			
		// Make surveyLayout the ContentView
		setContentView(surveyLayout);
	}
	
	public ScrollView buildSurveyLayout(String question, String responses[], String images[]){
		
		// Get and scale dimensions from dps to pixels based on screen size
		int marginTiny = dpToPx(getResources().getDimension(R.dimen.margin_tiny));
		int marginSmall = dpToPx(getResources().getDimension(R.dimen.margin_small));
		int marginMedium = dpToPx(getResources().getDimension(R.dimen.margin_medium));	
		int imageHeight = dpToPx(200);
		
		// Get reusable colors
		int bgColor = getResources().getColor(R.color.SurveyBackgroundColor);
		int fontColor = getResources().getColor(R.color.SurveyFontColor);
		
		// Instantiate ScrollView layout for survey page
		ScrollView surveyLayout = new ScrollView(this);
		surveyLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		surveyLayout.setBackgroundColor(bgColor);
			
		// Create container LinearLayout
		LinearLayout containerLayout = new LinearLayout(this);
		containerLayout.setBackgroundColor(bgColor);
		containerLayout.setOrientation(LinearLayout.VERTICAL);
		containerLayout.setPadding(marginSmall,marginSmall,marginSmall,marginSmall);
		containerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		// Create LinearLayout for question text
		LinearLayout questionLayout = new LinearLayout(this);
		questionLayout.setOrientation(LinearLayout.VERTICAL);
	   	LinearLayout.LayoutParams qlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        qlp.setMargins(0, marginMedium, 0, marginMedium); // rlp.setMargins(left, top, right, bottom);
        questionLayout.setLayoutParams(qlp);
        
		TextView questionText = new TextView(this);
		questionText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		questionText.setText(question);
		questionText.setTextAppearance(this, android.R.style.TextAppearance_Large);
		questionText.setTextColor(fontColor);
		questionText.setTypeface(Typeface.DEFAULT_BOLD);
				
		questionLayout.addView(questionText);
		containerLayout.addView(questionLayout);
		
		// Create LinearLayouts for responses with images
	    for (int i=0;i<responses.length; i++) {
	    	
	    	LinearLayout responseLayout = new LinearLayout(this);
	    	responseLayout.setOrientation(LinearLayout.VERTICAL);
    	   	LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        rlp.setMargins(0, 0, 0, marginSmall); // rlp.setMargins(left, top, right, bottom);
	        responseLayout.setLayoutParams(rlp);
	      
	        // Create response text
	    	TextView responseText = new TextView(this);
    	   	LinearLayout.LayoutParams rtp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        rtp.setMargins(0, marginSmall, 0, marginTiny); // rtp.setMargins(left, top, right, bottom);
	        responseText.setLayoutParams(rtp);
	        responseText.setText(responses[i]);
			responseText.setTextAppearance(this, android.R.style.TextAppearance_Large);
			responseText.setTextColor(fontColor);
			responseLayout.addView(responseText);
			
			// Create images to click on for each response if used
			if (images.length != 0){
				ImageButton responseImage = new ImageButton(this);
				responseImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, imageHeight));
				responseImage.setBackgroundColor(bgColor);
				responseImage.setAdjustViewBounds(true); // corrects for padding added by scaling
				responseImage.setScaleType(ScaleType.FIT_START);
				responseImage.setImageResource(getResources().getIdentifier(images[i], "drawable", getPackageName()));
				responseImage.setTag(images[i]); // makes it retrievable later
				responseImage.setOnClickListener(new View.OnClickListener() {
					@Override // make image clickable
					public void onClick(View arg0) {
						Intent i = new Intent(arg0.getContext(), SurveyThanksActivity.class);
						startActivity(i); 
						finish();
					}
				});
				responseLayout.addView(responseImage);
			} 
			
			containerLayout.addView(responseLayout);	
	    }			
		
		// Add LinearLayouts to ScrollView
		surveyLayout.addView(containerLayout);
		
		return surveyLayout;
	}
	
	private int dpToPx(float dp){
		//return (int)Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
		return (int)(dp*getResources().getDisplayMetrics().density);
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
