package com.c4fcm.actionpath;

public class SurveyGeofence extends SimpleGeofence {

	private String surveyKey;
	
	public SurveyGeofence(double latitude, double longitude, float radius,
			long expiration, int transition) {
		super(latitude, longitude, radius, expiration, transition);
	}

	public SurveyGeofence(String surveyKey, double latitude, double longitude, float radius,
			long expiration, int transition) {
		super(latitude, longitude, radius, expiration, transition);
		setSurveyKey(surveyKey);
	}
	
	public SurveyGeofence(String surveyKey, String geofenceId, double latitude, double longitude, float radius,
			long expiration, int transition) {
		super(geofenceId, latitude, longitude, radius, expiration, transition);
		setSurveyKey(surveyKey);
	}
	
	public void setSurveyKey(String sk){
		this.surveyKey = sk;
	}
	
	public String getSurveyKey(){
		return this.surveyKey;
	}
}
