package com.doi.himachal.epasshp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.doi.himachal.Adapter.GenericAdapter;
import com.doi.himachal.Adapter.GenericAdapterBarrier;
import com.doi.himachal.Modal.DistrictBarrierPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import cmreliefdund.kushkumardhawan.com.instructions.MaterialTutorialActivity;
import cmreliefdund.kushkumardhawan.com.instructions.TutorialItem;

public class Registration extends AppCompatActivity {


    private String Global_district_id;
    private String Global_barrier_id;

    public List<DistrictPojo> districts = null;
    public List<DistrictPojo> districts_sp = null;
    public List<DistrictBarrierPojo> barrirs = null;
    CustomDialog CD = new CustomDialog();

    GenericAdapter adapter_district = null;
    GenericAdapterBarrier adapter_barrier = null;

    com.doi.spinnersearchable.SearchableSpinner sp_district, sp_barrier;
    EditText phone;
    Button register;

    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sp_district = findViewById(R.id.sp_district);
        sp_district.setTitle("Please Select District");
        sp_district.setPrompt("Please Select District");
        sp_barrier = findViewById(R.id.sp_barrier);
        sp_barrier.setTitle("Please Select Barrier");
        sp_barrier.setPrompt("Please Select Barrier");

        phone = findViewById(R.id.phone);
        register = findViewById(R.id.register);

        requestPermissions();
//        Preferences.getInstance().loadPreferences(Registration.this);
//        if(!Preferences.getInstance().showtutorial){
//            loadTutorial();
//        }


        final DatabaseHandler DB = new DatabaseHandler(Registration.this);
        if (DB.getNoOfRowsCountDistrict() == 0 && DB.getNoOfRowsCountBarrir() ==0) {
            Registration.create_db_districts mouDetails_task = new Registration.create_db_districts();
            mouDetails_task.execute();

            Registration.create_db_districtsBarriers bariers = new Registration.create_db_districtsBarriers();
            bariers.execute();
        }else{
           // CD.showDialog(Registration.this, "Something bad happened. Please restart th application.");
        }

        List<DistrictPojo> districts = DB.getDistricts();





         adapter_district = new GenericAdapter(this, android.R.layout.simple_spinner_item, districts);
        sp_district.setAdapter(adapter_district);

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter_district.getItem(position);

                Global_district_id = item.getDistrict_id();
                List<DistrictBarrierPojo> barrier =DB.getBarriers(item.getDistrict_id());

                if(!barrier.isEmpty()){
                    adapter_barrier = new GenericAdapterBarrier(Registration.this, android.R.layout.simple_spinner_item, barrier);
                    sp_barrier.setAdapter(adapter_barrier);

                }else{
                    CD.showDialog(Registration.this,"No Barrier found for the specific District");
                    adapter_barrier = null;
                    sp_barrier.setAdapter(adapter_barrier);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_barrier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictBarrierPojo item = adapter_barrier.getItem(position);
                Log.e("We are Here",item.getBarrier_id());
                Global_barrier_id = item.getBarrier_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!phone.getText().toString().trim().isEmpty() && phone.getText().toString().trim().length()==10){

                    //Clicked
                    Preferences.getInstance().loadPreferences(Registration.this);
                    Preferences.getInstance().district_id = Global_district_id;
                    Preferences.getInstance().barrier_id = Global_barrier_id;
                    Preferences.getInstance().phone_number = phone.getText().toString().trim();

                    Preferences.getInstance().isLoggedIn = true;
                   // Preferences.getInstance().showtutorial = true;
                    Preferences.getInstance().savePreferences(Registration.this);

                    Intent mainIntent = new Intent(Registration.this, MainActivity.class);
                    Registration.this.startActivity(mainIntent);
                    Registration.this.finish();
                }else{
                    CD.showDialog(Registration.this,"Please enter a valid 10 digit Mobile number");
                }


            }
        });


        }

    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem("Scan the QR Code to verify the ePass", "",
                R.color.slide_4,R.drawable.qrcode);

//        TutorialItem tutorialItem2 = new TutorialItem("View Information about the ePass on the fly.", "",
//                R.color.slide_2,  R.drawable.tut_page_2_background);



        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
       // tutorialItems.add(tutorialItem2);

        return tutorialItems;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE


                }, 0);
            }
        }


    }


     class create_db_districts extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "Loading.. Please Wait", true);
            dialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            //Read file From Assets

            JSONArray m_jArry = null;
            try {
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "districts.json"));

                districts = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    DistrictPojo districtPojo = new DistrictPojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    districtPojo.setDistrict_id(jo_inside.getString("district_id"));
                    districtPojo.setDistrict_name(jo_inside.getString("district_name"));

                    districts.add(districtPojo);


                }
                if (districts.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                } else {
                    //Store Data to Database  //mou_details
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addDistrict(districts);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return m_jArry.toString();


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            dialog.dismiss();
        }
    }

    class create_db_districtsBarriers extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "Loading.. Please Wait", true);
            dialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            //Read file From Assets

            JSONArray m_jArry = null;
            try {
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "barriers.json"));

                barrirs = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    DistrictBarrierPojo districtBarrierPojo = new DistrictBarrierPojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    districtBarrierPojo.setBarrier_id(jo_inside.getString("id"));
                    districtBarrierPojo.setBarrier_district_id(jo_inside.getString("district_id"));
                    districtBarrierPojo.setBarrir_name(jo_inside.getString("barrier_name"));

                    barrirs.add(districtBarrierPojo);


                }
                if (barrirs.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                } else {
                    //Store Data to Database  //mou_details
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addDistrictBarriers(barrirs);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return m_jArry.toString();


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            dialog.dismiss();
        }
    }
}
