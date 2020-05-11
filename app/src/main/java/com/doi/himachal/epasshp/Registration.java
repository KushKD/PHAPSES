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
import com.doi.himachal.Modal.BlockPojo;
import com.doi.himachal.Modal.DistrictBarrierPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.GramPanchayatPojo;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.TehsilPojo;
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

    public List<StatePojo> states = null;
    public List<DistrictPojo> districts = null;
    public List<TehsilPojo> tehsils = null;
    public List<BlockPojo> blocks = null;
    public List<GramPanchayatPojo> gp = null;


    public List<DistrictBarrierPojo> barrirs = null;
    CustomDialog CD = new CustomDialog();

    GenericAdapter adapter_district = null;
    GenericAdapterBarrier adapter_barrier = null;

    com.doi.spinnersearchable.SearchableSpinner sp_district, sp_barrier;
    EditText phone, depat_name, name;
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
        depat_name = findViewById(R.id.depat_name);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);


//        Preferences.getInstance().loadPreferences(Registration.this);
//        if(!Preferences.getInstance().showtutorial){
//            loadTutorial();
//        }


        final DatabaseHandler DB = new DatabaseHandler(Registration.this);
        if (DB.getNoOfRowsCountDistrict() == 0 &&
                DB.getNoOfRowsCountBarrir() == 0 &&
                DB.getNoOfRowsState() == 0 &&
                DB.getNoOfRowsBlocks() == 0 &&
                DB.getNoOfRowsGP() == 0 &&
                DB.getNoOfRowsTehsil() == 0) {

            Registration.create_db_states states = new Registration.create_db_states();
            states.execute();


            Registration.create_db_districts mouDetails_task = new Registration.create_db_districts();
            mouDetails_task.execute();

            Registration.create_db_districtsBarriers bariers = new Registration.create_db_districtsBarriers();
            bariers.execute();

            Registration.create_db_tehsils tehsils = new Registration.create_db_tehsils();
            tehsils.execute();

            Registration.create_db_blocks blocks = new Registration.create_db_blocks();
            blocks.execute();

            Registration.create_db_gp gp = new Registration.create_db_gp();
            gp.execute();


        } else {

            try {
                districts = DB.getDistrictsViaState("9");
                DistrictPojo otherDistrict = new DistrictPojo();
                otherDistrict.setDistrict_id("0");
                otherDistrict.setDistrict_name("Other");
                districts.add(otherDistrict);
               // districts = DB.getDistrictsViaState();
                adapter_district = new GenericAdapter(this, android.R.layout.simple_spinner_item, districts);
                sp_district.setAdapter(adapter_district);
            } catch (Exception ex) {
                CD.showDialog(Registration.this, "Something Bad happened . Please reinstall the application and try again.");
            }

        }


        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter_district.getItem(position);

                Global_district_id = item.getDistrict_id();


                List<DistrictBarrierPojo> barrier = DB.getBarriers(item.getDistrict_id());
                DistrictBarrierPojo barrierPojo =  new DistrictBarrierPojo();
                barrierPojo.setBarrir_name("Other");
                barrierPojo.setBarrier_id("0");
                barrier.add(barrierPojo);


                if (!barrier.isEmpty()) {
                    adapter_barrier = new GenericAdapterBarrier(Registration.this, android.R.layout.simple_spinner_item, barrier);
                    sp_barrier.setAdapter(adapter_barrier);

                } else {
                    CD.showDialog(Registration.this, "No Barrier found for the specific District");
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
                Log.e("We are Here", item.getBarrier_id());
                Global_barrier_id = item.getBarrier_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!phone.getText().toString().trim().isEmpty() && phone.getText().toString().trim().length() == 10) {

                    if (!name.getText().toString().isEmpty() && name.getText().toString() != null) {

                        if (!depat_name.getText().toString().isEmpty() && depat_name.getText().toString() != null) {
                            //Clicked
                            Preferences.getInstance().loadPreferences(Registration.this);
                            Preferences.getInstance().district_id = Global_district_id;
                            Preferences.getInstance().barrier_id = Global_barrier_id;
                            Preferences.getInstance().name = name.getText().toString().trim();
                            Preferences.getInstance().dept_name = depat_name.getText().toString().trim();
                            Preferences.getInstance().phone_number = phone.getText().toString().trim();

                            Preferences.getInstance().isLoggedIn = true;
                            // Preferences.getInstance().showtutorial = true;
                            Preferences.getInstance().savePreferences(Registration.this);

                            Intent mainIntent = new Intent(Registration.this, MainActivity.class);
                            Registration.this.startActivity(mainIntent);
                            Registration.this.finish();
                        } else {
                            CD.showDialog(Registration.this, "Please enter Department name");
                        }

                    } else {
                        CD.showDialog(Registration.this, "Please enter name");
                    }


                } else {
                    CD.showDialog(Registration.this, "Please enter a valid 10 digit Mobile number");
                }


            }
        });


    }


    class create_db_states extends AsyncTask<String, String, String> {

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
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "states.json"));

                states = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    StatePojo statePojo = new StatePojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    statePojo.setState_id(jo_inside.getString("state_id"));
                    statePojo.setState_name(jo_inside.getString("state_name"));

                    states.add(statePojo);


                }
                if (!states.isEmpty()) {
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addStates(states);
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

    class create_db_districts extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "Preparing for the first time. This may take upto 1 minute, please be patient", true);
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
                    districtPojo.setState_id(jo_inside.getString("state_id"));
                    districtPojo.setAlertZone(jo_inside.getString("alert_zone"));

                    districts.add(districtPojo);


                }
                DistrictPojo otherDistrict = new DistrictPojo();
                otherDistrict.setDistrict_id("0");
                otherDistrict.setDistrict_name("Other");
                districts.add(otherDistrict);
                if (!districts.isEmpty()) {
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
            DatabaseHandler DB = new DatabaseHandler(Registration.this);
            districts = DB.getDistrictsViaState("9");
            DistrictPojo otherDistrict = new DistrictPojo();
            otherDistrict.setDistrict_id("0");
            otherDistrict.setDistrict_name("Other");
            districts.add(otherDistrict);

            adapter_district = new GenericAdapter(Registration.this, android.R.layout.simple_spinner_item, districts);
            sp_district.setAdapter(adapter_district);

            dialog.dismiss();
        }
    }

    class create_db_tehsils extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "Preparing for the first time. This may take upto 1 minute, please be patient", true);
            dialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            //Read file From Assets

            JSONArray m_jArry = null;
            try {
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "tehsils.json"));

                tehsils = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    TehsilPojo tehsil = new TehsilPojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    tehsil.setTehsil_id(jo_inside.getString("tehsil_id"));
                    tehsil.setTehsil_name(jo_inside.getString("tehsil_name"));
                    tehsil.setDistrict_id(jo_inside.getString("district_id"));

                    tehsils.add(tehsil);


                }
                if (!tehsils.isEmpty()) {
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addTehsil(tehsils);
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

    class create_db_blocks extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "\"Preparing for the first time. This may take upto 1 minute, please be patient\"", true);
            dialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            //Read file From Assets

            JSONArray m_jArry = null;
            try {
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "blocks.json"));

                blocks = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    BlockPojo block = new BlockPojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    block.setBlock_code(jo_inside.optString("block_id"));
                    block.setBlock_name(jo_inside.optString("block_name"));
                    block.setDistrict_id(jo_inside.optString("district_id"));
                    block.setBlock_code(jo_inside.optString("block_code"));
                    block.setIs_active(jo_inside.optString("is_active"));
                    block.setIs_deleted(jo_inside.optString("is_deleted"));

                    blocks.add(block);


                }
                if (!blocks.isEmpty()) {
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addBlocks(blocks);
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

    class create_db_gp extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Registration.this, "Loading", "Preparing for the first time. This may take upto 1 minute, please be patient", true);
            dialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            //Read file From Assets

            JSONArray m_jArry = null;
            try {
                m_jArry = new JSONArray(Econstants.loadJSONFromAsset(Registration.this, "gram_panchayat.json"));

                gp = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {

                    GramPanchayatPojo gp_ = new GramPanchayatPojo();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    gp_.setGp_id(jo_inside.optString("pachayat_code"));
                    gp_.setGp_name(jo_inside.optString("panchayat_name"));
                    gp_.setBlock_id(jo_inside.optString("block_id"));
                    gp_.setIs_active(jo_inside.optString("is_active"));
                    gp_.setIs_deleted(jo_inside.optString("is_deleted"));

                    gp.add(gp_);


                }
                if (!gp.isEmpty()) {
                    DatabaseHandler DB = new DatabaseHandler(Registration.this);
                    DB.addGp(gp);
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

            dialog = ProgressDialog.show(Registration.this, "Loading", "Preparing for the first time. This may take upto 1 minute, please be patient", true);
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
                if (!barrirs.isEmpty()) {
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
