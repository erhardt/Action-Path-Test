package com.c4fcm.actionpath;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SurveyGeofenceStore extends SimpleGeofenceStore {

	public SurveyGeofenceStore(Context context) {
		super(context);
	}

	 /**
     * Save a geofence.
     * @param id The geofence id
     * @param geofence The {@link SurveyGeofence} containing the
     * values you want to save in SharedPreferences
     */
    public void setGeofence(String id, SurveyGeofence geofence) {
        /*
         * Get a SharedPreferences editor instance. 
         */
        Editor editor = mPrefs.edit();
        editor.putString(
        		getGeofenceFieldKey(id, GeofenceUtils.KEY_SURVEY_KEY),
                (String) geofence.getSurveyKey()
        		);
        editor.commit();
    	commitBasicGeofenceValues(id, geofence);
        if(!geofenceStoreKeys.contains(id)){
        	geofenceStoreKeys.add(id);
        }
    }
	
	/**
	 * NOTE: THIS IS A TOTAL COPY FROM SimpleGeoFenceStore and should be refactored
     * Returns a stored geofence by its id, or returns {@code null}
     * if it's not found.
     *
     * @param id The ID of a stored geofence
     * @return A geofence defined by its center and radius. See
     * {@link SurveyGeofence}
     */
    public SurveyGeofence getGeofence(String id) {

    	String surveyKey = mPrefs.getString(
    			getGeofenceFieldKey(id,GeofenceUtils.KEY_SURVEY_KEY),
    			GeofenceUtils.INVALID_STRING_VALUE); 
    	
        double lat = mPrefs.getFloat(
                getGeofenceFieldKey(id, GeofenceUtils.KEY_LATITUDE),
                GeofenceUtils.INVALID_FLOAT_VALUE);

        double lng = mPrefs.getFloat(
                getGeofenceFieldKey(id, GeofenceUtils.KEY_LONGITUDE),
                GeofenceUtils.INVALID_FLOAT_VALUE);

        float radius = mPrefs.getFloat(
                getGeofenceFieldKey(id, GeofenceUtils.KEY_RADIUS),
                GeofenceUtils.INVALID_FLOAT_VALUE);

        long expirationDuration = mPrefs.getLong(
                getGeofenceFieldKey(id, GeofenceUtils.KEY_EXPIRATION_DURATION),
                GeofenceUtils.INVALID_LONG_VALUE);

        int transitionType = mPrefs.getInt(
                getGeofenceFieldKey(id, GeofenceUtils.KEY_TRANSITION_TYPE),
                GeofenceUtils.INVALID_INT_VALUE);

        // validate returned values
        if (validateGeofenceValues(lat,lng,radius,expirationDuration,transitionType)) {
            // Return a true Geofence object
            return new SurveyGeofence(surveyKey, id, lat, lng, 
            		radius, expirationDuration, transitionType);
        // Otherwise, return null.
        } else {
            return null;
        }
    }
	
    /*
     * Get unique survey keys for a set of geofence IDs
     */
    public ArrayList<String> getUniqueSurveyKeys(String[] geofenceIds){
    	String[] ids = new HashSet<String>(Arrays.asList(geofenceIds)).toArray(new String[0]);
    	ArrayList<String> surveyKeys = new ArrayList<String>();
    	for(String id: ids){
    		SurveyGeofence sg = getGeofence(id);
    		surveyKeys.add(sg.getSurveyKey());
    	}
    	return surveyKeys;
    }
	
}
