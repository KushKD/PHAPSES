package com.doi.himachal.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class Preferences {

    private static Preferences instance;
        private  String preferenceName = "com.doi.himachal.id";
        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;
        private String KEY_DISTRICTID = "district_id";
        private String KEY_BARRIERID = "barrier_id";
        private String KEY_PHONENUMBER = "phone_number";
        private String KEY_IS_LOGED_IN = "isLoggedIn";
        private String KEY_LOAD_TUTORIAL = "showtutorial";



        public String district_id,barrier_id,phone_number;
        public boolean isLoggedIn, showtutorial;


        private Preferences()
        {

        }

        public synchronized static Preferences getInstance()
        {
            if(instance == null)
                instance = new Preferences();
            return instance;
        }

        public void loadPreferences(Context c)
        {
            preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
            district_id = preferences.getString(KEY_DISTRICTID, "");
            barrier_id = preferences.getString(KEY_BARRIERID, "");
            phone_number = preferences.getString(KEY_PHONENUMBER, "");

            isLoggedIn = preferences.getBoolean(KEY_IS_LOGED_IN, isLoggedIn);
            showtutorial = preferences.getBoolean(KEY_LOAD_TUTORIAL,showtutorial);


        }

        public void savePreferences(Context c)
        {
            preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString(KEY_DISTRICTID, district_id);
            editor.putString(KEY_BARRIERID, barrier_id);
            editor.putString(KEY_PHONENUMBER, phone_number);
            editor.putBoolean(KEY_IS_LOGED_IN, isLoggedIn);
            editor.putBoolean(KEY_LOAD_TUTORIAL, showtutorial);
            //editor.clear();
            editor.commit();
        }

}