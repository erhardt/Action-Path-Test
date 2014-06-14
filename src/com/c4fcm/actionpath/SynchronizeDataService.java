package com.c4fcm.actionpath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import au.com.bytecode.opencsv.CSVReader;

public class SynchronizeDataService extends IntentService {

	private static final String DEBUG_TAG = "SynchronizeDataService";
	String geofenceDataURL = "https://docs.google.com/spreadsheets/d/10FdA4gcHDOhjuoUq43LPOVROFgx0SpcMk51ntbTcQjs/export?format=csv";
	
	public SynchronizeDataService(){
		super("SynchronizeDataService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		//Bundle extras = intent.getExtras();
		fetchGeofenceData();

	}
	
	protected void fetchGeofenceData(){
	    ConnectivityManager connMgr = (ConnectivityManager) 
	            getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo != null && networkInfo.isConnected()) {
	            try {
		        	String geofenceCSVData = downloadUrl(geofenceDataURL);
		        	
		        	//Log.i("SynchronizeData",geofenceCSVData);

		        	//explicitly skip the first line
		            CSVReader reader = new CSVReader(new StringReader(geofenceCSVData),',','\"',1);
		            List<String[]> myEntries = reader.readAll();
		            reader.close();
		            for (String[] s : myEntries) {
		            	Log.i("Line",Arrays.toString(s));
		            }	
		        	
	            } catch (IOException e) {
	                Log.i("SynchronizeData","Unable to retrieve web page. URL may be invalid.");
	            }
	        } else {
	            Log.i("SynchronizeData", "Connection Not Available");
	        }
	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();
	        
	        //int len = conn.getContentLength();
	       /* long contentLength = Long.parseLong(conn.getHeaderField("Content-Length"));

	        Log.i("Content-Length", Long.toString(contentLength));*/

	        // Convert the InputStream into a string
	        String contentAsString = readIt(is);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
//	    reader = new InputStreamReader(stream, "UTF-8");      
	    reader = new InputStreamReader(stream, "UTF-8");      

	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int b;
	    while((b = reader.read())!=-1){
	    	buffer.write(b);
	    }
	    return buffer.toString("UTF-8");
	}
}