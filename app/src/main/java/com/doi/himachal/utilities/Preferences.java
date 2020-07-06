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
    private String KEY_DISTRICTNAME = "district_name";
        private String KEY_BARRIERID = "barrier_id";
    private String KEY_BARRIERNAME = "barrier_name";
        private String KEY_PHONENUMBER = "phone_number";
        private String KEY_IS_LOGED_IN = "isLoggedIn";
        private String KEY_LOAD_TUTORIAL = "showtutorial";
    private String KEY_NAME = "name";
    private String KEY_NAME_DEPARTMENT = "dept_name";



        public String district_id,district_name,barrier_id,barrier_name,phone_number,name,dept_name;
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
            district_name =  preferences.getString(KEY_DISTRICTNAME, "");
            barrier_id = preferences.getString(KEY_BARRIERID, "");
            barrier_name =  preferences.getString(KEY_BARRIERNAME, "");
            phone_number = preferences.getString(KEY_PHONENUMBER, "");

            isLoggedIn = preferences.getBoolean(KEY_IS_LOGED_IN, isLoggedIn);
            showtutorial = preferences.getBoolean(KEY_LOAD_TUTORIAL,showtutorial);
            name = preferences.getString(KEY_NAME,"");
            dept_name = preferences.getString(KEY_NAME_DEPARTMENT,"");


        }

        public void savePreferences(Context c)
        {
            preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString(KEY_DISTRICTID, district_id);
            editor.putString(KEY_DISTRICTNAME,district_name);
            editor.putString(KEY_BARRIERNAME,barrier_name);
            editor.putString(KEY_BARRIERID, barrier_id);
            editor.putString(KEY_PHONENUMBER, phone_number);
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_NAME_DEPARTMENT, dept_name);
            editor.putBoolean(KEY_IS_LOGED_IN, isLoggedIn);
            editor.putBoolean(KEY_LOAD_TUTORIAL, showtutorial);
            //editor.clear();
            editor.commit();
        }

}