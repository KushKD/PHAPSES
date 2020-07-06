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
import android.preference.PreferenceFragment;
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
import com.doi.himachal.Modal.MastersPojoServer;
import com.doi.himachal.Modal.ResponsePojoGet;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.TehsilPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.generic.Generic_Async_Get;
import com.doi.himachal.generic.Generic_Async_Post;
import com.doi.himachal.interfaces.AsyncTaskListenerObject;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectGet;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectPost;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.AppStatus;
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

public class Registration extends AppCompatActivity implements AsyncTaskListenerObjectPost {


    private String Global_district_id, GlobalDistrictName;
    private String Global_barrier_id, GlobalBarrierName;

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


        if (AppStatus.getInstance(Registration.this).isOnline()) {
            UploadObject object = new UploadObject();
            object.setUrl(Econstants.URL_HTTPS);
            object.setTasktype(TaskType.GET_DISTRICT_VIA_STATE);
            object.setMethordName("getdistricts");
            object.setParam("state_id=");
            object.setParam2(9);
            new Generic_Async_Post(
                    Registration.this,
                    Registration.this,
                    TaskType.GET_DISTRICT_VIA_STATE).
                    execute(object);
        } else {
            CD.showDialog(Registration.this, "Please connect to Internet and try again.");
        }


        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter_district.getItem(position);

                Global_district_id = item.getDistrict_id();
                GlobalDistrictName = item.getDistrict_name();

                if (AppStatus.getInstance(Registration.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_BARRIER_VIA_DISTRICTS);
                    object.setMethordName("getbarriers");
                    object.setParam("districtId=");
                    object.setParam2(Integer.parseInt(Global_district_id));
                    new Generic_Async_Post(
                            Registration.this,
                            Registration.this,
                            TaskType.GET_BARRIER_VIA_DISTRICTS).
                            execute(object);
                } else {
                    CD.showDialog(Registration.this, "Please connect to Internet and try again.");
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
                GlobalBarrierName = item.getBarrir_name();

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
                            Preferences.getInstance().district_name = GlobalDistrictName;
                            Preferences.getInstance().barrier_id = Global_barrier_id;
                            Preferences.getInstance().barrier_name = GlobalBarrierName;
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

    @Override
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_DISTRICT_VIA_STATE) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(Registration.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    if (jsonArray.length() != 0) {
                        districts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DistrictPojo pojo = new DistrictPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setDistrict_id(object.optString("district_id"));
                            pojo.setDistrict_name(object.optString("district_name"));
                            pojo.setState_id(object.optString("state_id"));

                            districts.add(pojo);
                        }

                        adapter_district = new GenericAdapter(Registration.this, android.R.layout.simple_spinner_item, districts);
                        sp_district.setAdapter(adapter_district);
                    } else {
                        CD.showDialog(Registration.this, "District Not Found.");
                        adapter_barrier = null;
                        sp_district.setAdapter(adapter_district);
                    }
                }else{
                    CD.showDialog(Registration.this,"Something went wrong. Please connect to Internet and try again.");
                }

            }

        } else if (taskType == TaskType.GET_BARRIER_VIA_DISTRICTS) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(Registration.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                        JSONArray jsonArray = new JSONArray(response.getRecords());
                        Log.e("Status", jsonArray.toString());
                        if (jsonArray.length() != 0) {
                            barrirs = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                DistrictBarrierPojo pojo = new DistrictBarrierPojo();
                                JSONObject object = jsonArray.getJSONObject(i);
                                pojo.setBarrier_id(object.optString("id"));
                                pojo.setBarrir_name(object.optString("name"));

                                barrirs.add(pojo);
                            }
                            DistrictBarrierPojo barrierPojo = new DistrictBarrierPojo();
                            barrierPojo.setBarrir_name("Other");
                            barrierPojo.setBarrier_id("0");
                            barrirs.add(barrierPojo);

                            if (!barrirs.isEmpty()) {
                                adapter_barrier = new GenericAdapterBarrier(Registration.this, android.R.layout.simple_spinner_item, barrirs);
                                sp_barrier.setAdapter(adapter_barrier);

                            } else {

                                barrirs = new ArrayList<>();
                                adapter_barrier = null;
                                DistrictBarrierPojo barrierPojox = new DistrictBarrierPojo();
                                barrierPojox.setBarrir_name("Other");
                                barrierPojox.setBarrier_id("0");
                                barrirs.add(barrierPojox);

                                Toast.makeText(Registration.this,"No Barrier found for the specific District",Toast.LENGTH_LONG).show();
                                adapter_barrier = new GenericAdapterBarrier(Registration.this, android.R.layout.simple_spinner_item, barrirs);
                                sp_barrier.setAdapter(adapter_barrier);
                            }
                        }
                }else{
                    barrirs = new ArrayList<>();
                    adapter_barrier = null;
                    DistrictBarrierPojo barrierPojo = new DistrictBarrierPojo();
                    barrierPojo.setBarrir_name("Other");
                    barrierPojo.setBarrier_id("0");
                    barrirs.add(barrierPojo);

                   Toast.makeText(Registration.this,"No Barrier found for the specific District",Toast.LENGTH_LONG).show();
                    adapter_barrier = new GenericAdapterBarrier(Registration.this, android.R.layout.simple_spinner_item, barrirs);
                    sp_barrier.setAdapter(adapter_barrier);
                }
            }


        }
    }
}