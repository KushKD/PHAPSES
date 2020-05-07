package com.doi.himachal.epasshp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doi.himachal.Adapter.GenericAdapter;
import com.doi.himachal.Adapter.GenericAdapterBarrier;
import com.doi.himachal.Adapter.GenericAdapterBlocks;
import com.doi.himachal.Adapter.GenericAdapterGP;
import com.doi.himachal.Adapter.GenericAdapterStates;
import com.doi.himachal.Adapter.GenericAdapterTehsil;
import com.doi.himachal.Modal.BlockPojo;
import com.doi.himachal.Modal.DistrictBarrierPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.GramPanchayatPojo;
import com.doi.himachal.Modal.OfflineDataEntry;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.TehsilPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.Modal.UploadObjectManual;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.generic.GenericAsyncPostObject;
import com.doi.himachal.generic.GenericAsyncPostObjectForm;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectForm;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.AppStatus;
import com.doi.himachal.utilities.CommonUtils;
import com.doi.himachal.utilities.DateTime;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;
import com.doi.himachal.utilities.SamplePresenter;
import com.doi.spinnersearchable.SearchableSpinner;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.Configurations;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;
import com.kushkumardhawan.locationmanager.constants.FailType;
import com.kushkumardhawan.locationmanager.constants.ProcessType;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManualEntry extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObjectForm {

    TextView date, time;
    EditText names, numberpersons, vehiclenumber, mobilenumber, address, fromplace, placenameto, passno, authority, purpose , remarks;
    SearchableSpinner fromstate, fromdistrict, district, tehsil, block, gp, appdownloaded;
    DatabaseHandler DB = new DatabaseHandler(ManualEntry.this);
    CustomDialog CD = new CustomDialog();
    Button back, proceed;
    private ProgressDialog progressDialog;

    List<StatePojo> states = null;
    List<DistrictPojo> districts = null;
    List<DistrictPojo> fromdistricts = null;
    Set<BlockPojo> blocks = null;
    List<TehsilPojo> tehsils = null;
    List<GramPanchayatPojo> grampanchayats = null;

    GenericAdapterStates adapter_states = null;
    GenericAdapter adapter, fromAdapter = null;
    GenericAdapterTehsil adapter_tehsil = null;
    GenericAdapterBlocks adapterBlocks = null;
    GenericAdapterGP adaptergp = null;

    LinearLayout grampanchayat;

    String Global_fromstate, Global_fromdistrict, Global_todistrict, Global_totehsil, Global_toblock, Global_togramPanchayat;

    private SamplePresenter samplePresenter;
    public String userLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);


        init();
        samplePresenter = new SamplePresenter(this);
        getLocation();

        //GET States
        states = DB.getStates();
        adapter_states = new GenericAdapterStates(ManualEntry.this, android.R.layout.simple_spinner_item, states);
        fromstate.setAdapter(adapter_states);

        fromstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatePojo item = adapter_states.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("Stateid", item.getState_id());
                fromdistricts = DB.getDistrictsViaState(item.getState_id());
                Global_fromstate = item.getState_id();

                if (!fromdistricts.isEmpty()) {
                    fromAdapter = new GenericAdapter(ManualEntry.this, android.R.layout.simple_spinner_item, fromdistricts);
                    fromdistrict.setAdapter(fromAdapter);

                } else {
                    CD.showDialog(ManualEntry.this, "No District found for the specific State");
                    fromAdapter = null;
                    fromdistrict.setAdapter(fromAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fromdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = fromAdapter.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("District", item.getDistrict_id());

                Global_fromdistrict = item.getDistrict_id();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Get Districts
        districts = DB.getDistrictsViaState("9");
        Log.e("@@@District", districts.toString());
        adapter = new GenericAdapter(ManualEntry.this, android.R.layout.simple_spinner_item, districts);
        district.setAdapter(adapter);


        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("District Id-===", item.getDistrict_name());

                Global_todistrict = item.getDistrict_id();
                tehsils = DB.getTehsilViaDistrict(item.getDistrict_id());
                blocks = DB.getBlocksViaDistrict(item.getDistrict_id());

                if (!tehsils.isEmpty()) {
                    adapter_tehsil = new GenericAdapterTehsil(ManualEntry.this, android.R.layout.simple_spinner_item, tehsils);
                    tehsil.setAdapter(adapter_tehsil);

                } else {
                    CD.showDialog(ManualEntry.this, "No Tehsils found for the specific District");
                    adapter_tehsil = null;
                    tehsil.setAdapter(adapter_tehsil);
                }
                if (!blocks.isEmpty()) {

                    adapterBlocks = new GenericAdapterBlocks(ManualEntry.this, android.R.layout.simple_spinner_item, convertSetToList(blocks));
                    block.setAdapter(adapterBlocks);

                } else {
                    CD.showDialog(ManualEntry.this, "No Blocks found for the specific District");
                    adapterBlocks = null;
                    block.setAdapter(adapterBlocks);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TehsilPojo item = adapter_tehsil.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("Tehsil Id-===", item.getTehsil_id());
                Global_totehsil = item.getTehsil_id();


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BlockPojo item = adapterBlocks.getItem(position);
                Log.e("data", item.toString());
                Log.e("data", item.getBlock_code());
                //  Global_district_id = item.getDistrict_id();
                Log.e("Block Id-===", item.getBlock_code());
                Global_toblock = item.getBlock_code();
                grampanchayats = DB.getGPViaDistrict(item.getBlock_code());
                Log.e("Size", Integer.toString(grampanchayats.size()));

                if (grampanchayats.size() != 0) {
                    grampanchayat.setVisibility(View.VISIBLE);
                    adaptergp = new GenericAdapterGP(ManualEntry.this, android.R.layout.simple_spinner_item, grampanchayats);
                    gp.setAdapter(adaptergp);

                } else {
                   // CD.showDialog(ManualEntry.this, "No Panchayats found for the specific blocks");
                    adaptergp = null;
                    gp.setAdapter(adaptergp);
                    grampanchayat.setVisibility(View.GONE);
                    Global_togramPanchayat = "0";
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GramPanchayatPojo item = adaptergp.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("Gram Panchayat Id-===", item.getGp_id());
                Global_togramPanchayat = item.getGp_id();


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualEntry.this.finish();
            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OfflineDataEntry offlineDataEntry = new OfflineDataEntry();
                offlineDataEntry.setVersionCode(Econstants.getVersion(ManualEntry.this));


                if (!userLocation.isEmpty()) {
                    try {
                        String[] locations = userLocation.split(",");
                        offlineDataEntry.setLatitude(locations[0]);
                        offlineDataEntry.setLongitude(locations[1]);
                    } catch (Exception ex) {
                        CD.showDialog(ManualEntry.this, "Unable to get the Location.");
                    }
                } else {
                    offlineDataEntry.setLatitude("0");
                    offlineDataEntry.setLongitude("0");
                }

                offlineDataEntry.setState_from(Global_fromstate);
                offlineDataEntry.setDistrict_from(Global_fromdistrict);
                offlineDataEntry.setDistrict_to(Global_todistrict);
                offlineDataEntry.setTehsil_to(Global_totehsil);
                offlineDataEntry.setBlock_to(Global_toblock);

                offlineDataEntry.setTimeStamp(CommonUtils.getCurrentDate());
                offlineDataEntry.setAaroyga_app_download(appdownloaded.getSelectedItem().toString());
                offlineDataEntry.setBarrier_id(Preferences.getInstance().barrier_id);
                offlineDataEntry.setDistict_barrer_id(Preferences.getInstance().district_id);
                offlineDataEntry.setUser_mobile(Preferences.getInstance().phone_number);


                if(address.getText().toString()==null || address.getText().toString().isEmpty()){
                    offlineDataEntry.setAddress("");
                }else{
                    offlineDataEntry.setAddress(address.getText().toString().trim());
                }

                if(fromplace.getText().toString()==null || fromplace.getText().toString().isEmpty()){
                    offlineDataEntry.setPlace_form("");
                }else{
                    offlineDataEntry.setPlace_form(fromplace.getText().toString().trim());
                }

                if(placenameto.getText().toString()==null || placenameto.getText().toString().isEmpty()){
                    offlineDataEntry.setPlace_to("");
                }else{
                    offlineDataEntry.setPlace_to(placenameto.getText().toString().trim());
                }

                if(purpose.getText().toString()==null || purpose.getText().toString().isEmpty()){
                    offlineDataEntry.setPurpose("");
                }else{
                    offlineDataEntry.setPurpose(purpose.getText().toString().trim());
                }

                if(Global_togramPanchayat==null || Global_togramPanchayat.isEmpty()){
                    offlineDataEntry.setGram_panchayat("0");
                    Log.e("Global_togramPanchayat",Global_togramPanchayat);
                }else{
                    Log.e("Global_togramPanchayat",Global_togramPanchayat);
                    offlineDataEntry.setGram_panchayat(Global_togramPanchayat);
                }

                if(remarks.getText().toString()==null || remarks.getText().toString().isEmpty()){
                    offlineDataEntry.setRemarks("");

                }else{

                    offlineDataEntry.setRemarks(remarks.getText().toString().trim());
                }






                //Edit Text get data
                if (names.getText().toString() != null && !names.getText().toString().isEmpty()) {
                    offlineDataEntry.setNames(names.getText().toString().trim());
                    if (numberpersons.getText().toString() != null && !numberpersons.getText().toString().isEmpty()) {
                        offlineDataEntry.setNo_of_persons(numberpersons.getText().toString().trim());

                        if (vehiclenumber.getText().toString() != null && !vehiclenumber.getText().toString().isEmpty()) {
                            offlineDataEntry.setVehicle_number(vehiclenumber.getText().toString().trim());

                            if (mobilenumber.getText().toString() != null && !mobilenumber.getText().toString().isEmpty() ) {
                                offlineDataEntry.setMobile(mobilenumber.getText().toString().trim());

                                if (passno.getText().toString() != null && !passno.getText().toString().isEmpty()) {
                                    offlineDataEntry.setPass_no(passno.getText().toString().trim());

                                    if (authority.getText().toString() != null && !authority.getText().toString().isEmpty()) {
                                        offlineDataEntry.setPass_issue_authority(authority.getText().toString().trim());


                                        UploadObjectManual object = new UploadObjectManual();
                                        object.setUrl("http://covid19epass.hp.gov.in/api/v1/saveofflinebarrierdata");
                                        object.setTasktype(TaskType.MANUAL_FORM_UPLOAD);
                                        object.setMethordName("saveofflinebarrierdata");
                                        object.setOfflineDataEntry(offlineDataEntry);


                                        //TODO SEND OBECT TO SERVER
                                        System.out.println(offlineDataEntry.toString());

                                        if(AppStatus.getInstance(ManualEntry.this).isOnline()){
                                            new GenericAsyncPostObjectForm(
                                                    ManualEntry.this,
                                                    ManualEntry.this,
                                                    TaskType.MANUAL_FORM_UPLOAD).
                                                    execute(object);
                                        }else{
                                            CD.showDialog(ManualEntry.this, "Please connect to Internet and try again.");
                                        }




                                    } else {
                                        CD.showDialog(ManualEntry.this, "Please enter Pass Issuing Authority .");
                                    }


                                } else {
                                    CD.showDialog(ManualEntry.this, "Please enter valid Pass number .");
                                }


                            } else {
                                CD.showDialog(ManualEntry.this, "Please enter valid mobile phone number of the driver .");
                            }

                        } else {
                            CD.showDialog(ManualEntry.this, "Please enter Vehicle Number .");
                        }


                    } else {
                        CD.showDialog(ManualEntry.this, "Please enter number of persons travelling .");
                    }


                } else {
                    CD.showDialog(ManualEntry.this, "Please enter name .");
                }


            }
        });


//CommonUtils.getCurrentDate()
        try {
            date.setText(DateTime.Change_Date_Format_second(CommonUtils.getCurrentDate()));
            time.setText(DateTime.changeTime(CommonUtils.getCurrentDate()));
        } catch (ParseException e) {
            time.setText("-");
            e.printStackTrace();
        }


    }

    public static <T> List<T> convertSetToList(Set<T> set) {
        // create a list from Set
        List<T> list = new ArrayList<>(set);

        // return the list
        return list;
    }

    private void init() {

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        names = findViewById(R.id.names);
        numberpersons = findViewById(R.id.numberpersons);
        vehiclenumber = findViewById(R.id.vehiclenumber);
        mobilenumber = findViewById(R.id.mobilenumber);
        address = findViewById(R.id.address);
        fromplace = findViewById(R.id.fromplace);
        placenameto = findViewById(R.id.placenameto);
        passno = findViewById(R.id.passno);
        authority = findViewById(R.id.authority);
        purpose = findViewById(R.id.purpose);

        fromstate = findViewById(R.id.fromstate);
        fromdistrict = findViewById(R.id.fromdistrict);
        district = findViewById(R.id.district);
        tehsil = findViewById(R.id.tehsil);
        block = findViewById(R.id.block);
        gp = findViewById(R.id.gp);
        appdownloaded = findViewById(R.id.appdownloaded);
        back = findViewById(R.id.back);
        proceed = findViewById(R.id.proceed);
        remarks = findViewById(R.id.remarks);

        grampanchayat = findViewById(R.id.gml);


    }


    /**
     * Location Interface Methords
     *
     * @return
     */
    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Permission Required !", "GPS needs to be turned on.");
    }

    @Override
    public void onLocationChanged(Location location) {
        samplePresenter.onLocationChanged(location);
    }

    @Override
    public void onLocationFailed(@FailType int type) {
        samplePresenter.onLocationFailed(type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public String getText() {
        return "";  //locationText.getText().toString()
    }

    @Override
    public void setText(String text) {
        //locationText.setText(text);
        Log.e("Location GPS???", text);
        userLocation = text;
    }

    @Override
    public void updateProgress(String text) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(text);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int processType) {
        samplePresenter.onProcessTypeChanged(processType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samplePresenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(ResponsePojo result, TaskType taskType) throws JSONException {

        if(taskType== TaskType.MANUAL_FORM_UPLOAD){
           // CD.showDialog(ManualEntry.this,result.getResponse());
            SuccessResponse response = JsonParse.getSuccessResponse(result.getResponse());
            if (response.getStatus().equalsIgnoreCase("200")) {
                CD.showDialogCloseActivity(ManualEntry.this, "Data Saved Successfully. "+ response.getMessage());
            } else {
                CD.showDialogHTMLGeneric(ManualEntry.this, response.getResponse());
            }
        }

    }
}
