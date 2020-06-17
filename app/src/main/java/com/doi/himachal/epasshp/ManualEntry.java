package com.doi.himachal.epasshp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doi.himachal.Adapter.GenericAdapter;
import com.doi.himachal.Adapter.GenericAdapterBarrier;
import com.doi.himachal.Adapter.GenericAdapterBlocks;
import com.doi.himachal.Adapter.GenericAdapterGP;
import com.doi.himachal.Adapter.GenericAdapterStates;
import com.doi.himachal.Adapter.GenericAdapterTehsil;
import com.doi.himachal.Modal.AddMorePeoplePojo;
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
import com.doi.himachal.generic.GenericAsyncDatabaseObject;
import com.doi.himachal.generic.GenericAsyncPostObject;
import com.doi.himachal.generic.GenericAsyncPostObjectForm;
import com.doi.himachal.interfaces.AsyncTaskListenerDatabase;
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

public class ManualEntry extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObjectForm, AsyncTaskListenerDatabase {

    TextView date, time, totalpersons,passenger;
    EditText names, numberpersons, vehiclenumber, mobilenumber, address, fromplace, placenameto, passno, authority, purpose, remarks;
   //SearchableSpinner
   SearchableSpinner  fromdistrict, district, tehsil, block, gp, appdownloaded;
    SearchableSpinner fromstate;
    DatabaseHandler DB = new DatabaseHandler(ManualEntry.this);
    CustomDialog CD = new CustomDialog();
    Button back, proceed, addmore;
    private ProgressDialog progressDialog;

    List<StatePojo> states = null;
    List<DistrictPojo> districts = null;
    List<DistrictPojo> fromdistricts = null;
    List<BlockPojo> blocks = null;
    List<TehsilPojo> tehsils = null;
    List<AddMorePeoplePojo> addPersons = new ArrayList<>();
    List<GramPanchayatPojo> grampanchayats = null;

    GenericAdapterStates adapter_states = null;
    GenericAdapter adapter, fromAdapter = null;
    GenericAdapterTehsil adapter_tehsil = null;
    GenericAdapterBlocks adapterBlocks = null;
    GenericAdapterGP adaptergp = null;

    LinearLayout grampanchayat;
    OfflineDataEntry offlineDataEntry = new OfflineDataEntry();

    String Global_fromstate, Global_fromdistrict, Global_todistrict, Global_totehsil, Global_toblock, Global_togramPanchayat, Global_toBlockName;
    int Global_fromstatePosition, Global_fromdistrictPosition, Global_todistrictPosition, Global_totehsilPosition,
            Global_toblockPosition, Global_togramPanchayatPosition ;

    private SamplePresenter samplePresenter;
    public String userLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);


        init();
        samplePresenter = new SamplePresenter(this);
        getLocation();

        numberpersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

               // if(addPersons.isEmpty()){
                    if (numberpersons.getText().toString().equalsIgnoreCase("1")) {
                        addmore.setVisibility(View.GONE);
                        proceed.setVisibility(View.VISIBLE);
                    } else if (numberpersons.getText().toString().equalsIgnoreCase("0")) {
                        addmore.setVisibility(View.GONE);
                        proceed.setVisibility(View.GONE);
                    } else{
                        checkList(s.toString());
                       // addmore.setVisibility(View.VISIBLE);
                        //proceed.setVisibility(View.GONE);
                    }


            }
        });

        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!numberpersons.getText().toString().isEmpty()) {


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
                    offlineDataEntry.setPosition_from_state(Global_fromstatePosition);
                    offlineDataEntry.setDistrict_from(Global_fromdistrict);
                    offlineDataEntry.setPosition_from_district(Global_fromdistrictPosition);

                    offlineDataEntry.setDistrict_to(Global_todistrict);
                    offlineDataEntry.setPosition_to_district(Global_todistrictPosition);
                    offlineDataEntry.setTehsil_to(Global_totehsil);
                    offlineDataEntry.setPosition_to_tehsil(Global_totehsilPosition);
                    offlineDataEntry.setBlock_to(Global_toblock);
                    offlineDataEntry.setPosition_to_block(Global_toblockPosition);
                    offlineDataEntry.setPosition_to_panchayat(Global_togramPanchayatPosition);


                    offlineDataEntry.setTimeStamp(CommonUtils.getCurrentDate());
                    offlineDataEntry.setAaroyga_app_download(appdownloaded.getSelectedItem().toString());
                    offlineDataEntry.setBarrier_id(Preferences.getInstance().barrier_id);
                    offlineDataEntry.setDistict_barrer_id(Preferences.getInstance().district_id);
                    offlineDataEntry.setUser_mobile(Preferences.getInstance().phone_number);


                    if (address.getText().toString() == null || address.getText().toString().isEmpty()) {
                        offlineDataEntry.setAddress("");
                    } else {
                        offlineDataEntry.setAddress(address.getText().toString().trim());
                    }

                    if (passno.getText().toString() == null || passno.getText().toString().isEmpty()) {
                        offlineDataEntry.setPass_no("NA");
                    } else {
                        offlineDataEntry.setPass_no(passno.getText().toString().trim());
                    }

                    if (authority.getText().toString() == null || authority.getText().toString().isEmpty()) {
                        offlineDataEntry.setPass_issue_authority("NA");
                    } else {
                        offlineDataEntry.setPass_issue_authority(authority.getText().toString().trim());
                    }


                    if (fromplace.getText().toString() == null || fromplace.getText().toString().isEmpty()) {
                        offlineDataEntry.setPlace_form("");
                    } else {
                        offlineDataEntry.setPlace_form(fromplace.getText().toString().trim());
                    }

                    if (placenameto.getText().toString() == null || placenameto.getText().toString().isEmpty()) {
                        offlineDataEntry.setPlace_to("");
                    } else {
                        offlineDataEntry.setPlace_to(placenameto.getText().toString().trim());
                    }

                    if (purpose.getText().toString() == null || purpose.getText().toString().isEmpty()) {
                        offlineDataEntry.setPurpose("");
                    } else {
                        offlineDataEntry.setPurpose(purpose.getText().toString().trim());
                    }

                    if (Global_togramPanchayat == null || Global_togramPanchayat.isEmpty()) {
                        offlineDataEntry.setGram_panchayat("0");
                        Log.e("Global_togramPanchayat", Global_togramPanchayat);
                    } else {
                        Log.e("Global_togramPanchayat", Global_togramPanchayat);
                        offlineDataEntry.setGram_panchayat(Global_togramPanchayat);
                    }

                    if (remarks.getText().toString() == null || remarks.getText().toString().isEmpty()) {
                        offlineDataEntry.setRemarks("");

                    } else {

                        offlineDataEntry.setRemarks(remarks.getText().toString().trim());
                    }


                    //Edit Text get data
                    if (names.getText().toString() != null && !names.getText().toString().isEmpty()) {
                        offlineDataEntry.setNames(names.getText().toString().trim());
                        if (numberpersons.getText().toString() != null && !numberpersons.getText().toString().isEmpty()) {
                            offlineDataEntry.setNo_of_persons(numberpersons.getText().toString().trim());

                            if (vehiclenumber.getText().toString() != null && !vehiclenumber.getText().toString().isEmpty()) {
                                offlineDataEntry.setVehicle_number(vehiclenumber.getText().toString().trim());

                                if (mobilenumber.getText().toString() != null && mobilenumber.getText().toString().length()==10 && !mobilenumber.getText().toString().isEmpty()) {
                                    offlineDataEntry.setMobile(mobilenumber.getText().toString().trim());

                                    // if (passno.getText().toString() != null && !passno.getText().toString().isEmpty()) {
                                    // offlineDataEntry.setPass_no(passno.getText().toString().trim());

                                    // if (authority.getText().toString() != null && !authority.getText().toString().isEmpty()) {
                                    //   offlineDataEntry.setPass_issue_authority(authority.getText().toString().trim());


                                    //TODO SEND OBECT TO SERVER
                                    System.out.println(offlineDataEntry.toString());

                                    //TODO Go to add more Activity
                                    Intent addmore = new Intent(ManualEntry.this, AddMoreActivity.class);
                                    addmore.putExtra("PARENT", offlineDataEntry);
                                    startActivityForResult(addmore, 1);


//                                    } else {
//                                        CD.showDialog(ManualEntry.this, "Please enter Pass Issuing Authority .");
//                                    }


//                                } else {
//                                    CD.showDialog(ManualEntry.this, "Please enter valid Pass number .");
//                                }


                                } else {
                                    CD.showDialog(ManualEntry.this, "Please enter valid mobile phone number .");
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


                } else {
                    CD.showDialog(ManualEntry.this, "Please Enter the number of Persons");
                }
            }
        });


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
                Global_fromstatePosition = position;
                Global_fromstate = item.getState_id();

                /**
                 * TODO HERE TODAY
                 */
                new GenericAsyncDatabaseObject(
                        ManualEntry.this,
                        ManualEntry.this,
                        TaskType.GET_DISTRICT_VIA_STATE).
                        execute(TaskType.GET_DISTRICT_VIA_STATE.toString(),item.getState_id());
//                fromdistricts = DB.getDistrictsViaState(item.getState_id());
//                if (!fromdistricts.isEmpty()) {
//                    fromAdapter = new GenericAdapter(ManualEntry.this, android.R.layout.simple_spinner_item, fromdistricts);
//                    fromdistrict.setAdapter(fromAdapter);
//
//                } else {
//                    CD.showDialog(ManualEntry.this, "No District found for the specific State");
//                    fromAdapter = null;
//                    fromdistrict.setAdapter(fromAdapter);
//                }


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
                Global_fromdistrictPosition = position;
                Log.e("District Pos", Integer.toString(position));


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
                Log.e("Position-===", Integer.toString(position));

                Global_todistrict = item.getDistrict_id();
                Global_todistrictPosition = position;

                //TODO TODAY TWO
                /**
                 * TODO HERE TODAY
                 */
                new GenericAsyncDatabaseObject(
                        ManualEntry.this,
                        ManualEntry.this,
                        TaskType.GET_TEHSIL_VIA_DISTRICT).
                        execute(TaskType.GET_TEHSIL_VIA_DISTRICT.toString(),item.getDistrict_id());


                new GenericAsyncDatabaseObject(
                        ManualEntry.this,
                        ManualEntry.this,
                        TaskType.GET_BLOCK_VIA_DISTRICT).
                        execute(TaskType.GET_BLOCK_VIA_DISTRICT.toString(),item.getDistrict_id());


                //tehsils = DB.getTehsilViaDistrict(item.getDistrict_id());
             //   blocks = DB.getBlocksViaDistrict(item.getDistrict_id());

//                if (!tehsils.isEmpty()) {
//                    adapter_tehsil = new GenericAdapterTehsil(ManualEntry.this, android.R.layout.simple_spinner_item, tehsils);
//                    tehsil.setAdapter(adapter_tehsil);
//
//                } else {
//                    CD.showDialog(ManualEntry.this, "No Tehsils found for the specific District");
//                    adapter_tehsil = null;
//                    tehsil.setAdapter(adapter_tehsil);
//                }
//                if (!blocks.isEmpty()) {
//
//                    adapterBlocks = new GenericAdapterBlocks(ManualEntry.this, android.R.layout.simple_spinner_item, blocks);
//                    block.setAdapter(adapterBlocks);
//
//                } else {
//                    CD.showDialog(ManualEntry.this, "No Blocks found for the specific District");
//                    adapterBlocks = null;
//                    block.setAdapter(adapterBlocks);
//                }


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
                Global_totehsilPosition = position;


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
                Global_toBlockName = item.getBlock_name();
                Global_toblockPosition = position;

                //TODO TODAY THIRD
                new GenericAsyncDatabaseObject(
                        ManualEntry.this,
                        ManualEntry.this,
                        TaskType.GET_GP_VIA_DISTRICT).
                        execute(TaskType.GET_GP_VIA_DISTRICT.toString(),item.getBlock_code());
             //   grampanchayats = DB.getGPViaDistrict(item.getBlock_code());

//                Log.e("Size", Integer.toString(grampanchayats.size()));
//
//                if (grampanchayats.size() != 0) {
//                    if(item.getBlock_name().contains("Town")){
//                        GramPanchayatPojo pojo = new GramPanchayatPojo();
//                        pojo.setGp_id("0");
//                        pojo.setGp_name("Please Select");
//                        grampanchayats.add(0, pojo);
//                    }
//                    grampanchayat.setVisibility(View.VISIBLE);
//                    adaptergp = new GenericAdapterGP(ManualEntry.this, android.R.layout.simple_spinner_item, grampanchayats);
//                    gp.setAdapter(adaptergp);
//
//                } else {
//                    // CD.showDialog(ManualEntry.this, "No Panchayats found for the specific blocks");
//                    adaptergp = null;
//                    gp.setAdapter(adaptergp);
//                    grampanchayat.setVisibility(View.GONE);
//                    Global_togramPanchayat = "0";
//                }


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
                Global_togramPanchayatPosition = position;


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
                offlineDataEntry.setPosition_from_state(Global_fromstatePosition);
                offlineDataEntry.setDistrict_from(Global_fromdistrict);
                offlineDataEntry.setPosition_from_district(Global_fromdistrictPosition);

                offlineDataEntry.setDistrict_to(Global_todistrict);
                offlineDataEntry.setPosition_to_district(Global_todistrictPosition);
                offlineDataEntry.setTehsil_to(Global_totehsil);
                offlineDataEntry.setPosition_to_tehsil(Global_totehsilPosition);
                offlineDataEntry.setBlock_to(Global_toblock);
                offlineDataEntry.setPosition_to_block(Global_toblockPosition);

                offlineDataEntry.setTimeStamp(CommonUtils.getCurrentDate());
                offlineDataEntry.setAaroyga_app_download(appdownloaded.getSelectedItem().toString());
                offlineDataEntry.setBarrier_id(Preferences.getInstance().barrier_id);
                offlineDataEntry.setDistict_barrer_id(Preferences.getInstance().district_id);
                offlineDataEntry.setUser_mobile(Preferences.getInstance().phone_number);


                if (address.getText().toString() == null || address.getText().toString().isEmpty()) {
                    offlineDataEntry.setAddress("");
                } else {
                    offlineDataEntry.setAddress(address.getText().toString().trim());
                }

                if (passno.getText().toString() == null || passno.getText().toString().isEmpty()) {
                    offlineDataEntry.setPass_no("NA");
                } else {
                    offlineDataEntry.setPass_no(passno.getText().toString().trim());
                }

                if (authority.getText().toString() == null || authority.getText().toString().isEmpty()) {
                    offlineDataEntry.setPass_issue_authority("NA");
                } else {
                    offlineDataEntry.setPass_issue_authority(authority.getText().toString().trim());
                }


                if (fromplace.getText().toString() == null || fromplace.getText().toString().isEmpty()) {
                    offlineDataEntry.setPlace_form("");
                } else {
                    offlineDataEntry.setPlace_form(fromplace.getText().toString().trim());
                }

                if (placenameto.getText().toString() == null || placenameto.getText().toString().isEmpty()) {
                    offlineDataEntry.setPlace_to("");
                } else {
                    offlineDataEntry.setPlace_to(placenameto.getText().toString().trim());
                }

                if (purpose.getText().toString() == null || purpose.getText().toString().isEmpty()) {
                    offlineDataEntry.setPurpose("");
                } else {
                    offlineDataEntry.setPurpose(purpose.getText().toString().trim());
                }

                if (Global_togramPanchayat == null || Global_togramPanchayat.isEmpty()) {
                    offlineDataEntry.setGram_panchayat("0");
                    Log.e("Global_togramPanchayat", Global_togramPanchayat);
                } else {
                    Log.e("Global_togramPanchayat", Global_togramPanchayat);
                    offlineDataEntry.setGram_panchayat(Global_togramPanchayat);
                }

                if (remarks.getText().toString() == null || remarks.getText().toString().isEmpty()) {
                    offlineDataEntry.setRemarks("");

                } else {

                    offlineDataEntry.setRemarks(remarks.getText().toString().trim());
                }


                //Edit Text get data
                if (names.getText().toString() != null && !names.getText().toString().isEmpty()) {
                    offlineDataEntry.setNames(names.getText().toString().trim());
                    if (numberpersons.getText().toString() != null && !numberpersons.getText().toString().isEmpty()) {
                        offlineDataEntry.setNo_of_persons(numberpersons.getText().toString().trim());

                        if (vehiclenumber.getText().toString() != null && !vehiclenumber.getText().toString().isEmpty()) {
                            offlineDataEntry.setVehicle_number(vehiclenumber.getText().toString().trim());

                            if (mobilenumber.getText().toString() != null && mobilenumber.getText().toString().length()==10 && !mobilenumber.getText().toString().isEmpty()) {
                                offlineDataEntry.setMobile(mobilenumber.getText().toString().trim());

                                // if (passno.getText().toString() != null && !passno.getText().toString().isEmpty()) {
                                // offlineDataEntry.setPass_no(passno.getText().toString().trim());

                                // if (authority.getText().toString() != null && !authority.getText().toString().isEmpty()) {
                                //   offlineDataEntry.setPass_issue_authority(authority.getText().toString().trim());


                                UploadObjectManual object = new UploadObjectManual();
                                object.setUrl("http://covid19epass.hp.gov.in/api/v1/saveofflinebarrierdata");
                                object.setTasktype(TaskType.MANUAL_FORM_UPLOAD);
                                object.setMethordName("saveofflinebarrierdata");
                                object.setOfflineDataEntry(offlineDataEntry);


                                //TODO SEND OBECT TO SERVER
                                System.out.println(offlineDataEntry.toString());
//TODO
                                if (AppStatus.getInstance(ManualEntry.this).isOnline()) {
                                    new GenericAsyncPostObjectForm(
                                            ManualEntry.this,
                                            ManualEntry.this,
                                            TaskType.MANUAL_FORM_UPLOAD).
                                            execute(object);
                                } else {
                                    CD.showDialog(ManualEntry.this, "Please connect to Internet and try again.");
                                }


//                                    } else {
//                                        CD.showDialog(ManualEntry.this, "Please enter Pass Issuing Authority .");
//                                    }


//                                } else {
//                                    CD.showDialog(ManualEntry.this, "Please enter valid Pass number .");
//                                }


                            } else {
                                CD.showDialog(ManualEntry.this, "Please enter valid mobile phone number.");
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

    private void checkList(String s) {
        Log.e("Data",s);
        if(s!=null){
            try{
                int number = Integer.parseInt(s);
                Log.e("number",Integer.toString(number));
                if(!addPersons.isEmpty()){

                    if(number > (addPersons.size()+1) ) {
                        addmore.setVisibility(View.VISIBLE);
                        proceed.setVisibility(View.GONE);
                    }else{
                        Log.e("number","We are in extreme case.");
                        Log.e("number",Integer.toString(number));
                        Log.e("list",Integer.toString(addPersons.size()+1));
                       // CD.showDialog(ManualEntry.this,"There are already ");
                        //TODO
                        //Remove elements here
                        Toast.makeText(this, "Total number of Persons Added is "+Integer.toString(addPersons.size()+1), Toast.LENGTH_SHORT).show();
                        proceed.setVisibility(View.VISIBLE);
                        addmore.setVisibility(View.GONE);
                    }
                }else{
                    addmore.setVisibility(View.VISIBLE);
                    proceed.setVisibility(View.GONE);
                }

            }catch (Exception ex){
                Log.e("number",ex.getLocalizedMessage());
            }

        }else{
            proceed.setVisibility(View.VISIBLE);
            addmore.setVisibility(View.GONE);
        }

    }


    private void init() {

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        totalpersons = findViewById(R.id.totalpersons);
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

        addmore = findViewById(R.id.addmore);
        passenger = findViewById(R.id.passenger);


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

        if (taskType == TaskType.MANUAL_FORM_UPLOAD) {
            // CD.showDialog(ManualEntry.this,result.getResponse());
            SuccessResponse response = JsonParse.getSuccessResponse(result.getResponse());
            if (response.getStatus().equalsIgnoreCase("200")) {
                CD.showDialogCloseActivity(ManualEntry.this, "Data Saved Successfully. " + response.getMessage());
            } else {
                CD.showDialogHTMLGeneric(ManualEntry.this, response.getResponse());
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddMorePeoplePojo addMore = null;
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                 addMore = (AddMorePeoplePojo) data.getSerializableExtra("AddMore");
                Log.e("Add more", addMore.toString());
                CD.showDialog(ManualEntry.this,addMore.getEnter_name()+" Added Successfully.");
                addPersons.add(addMore);
                passenger.setText("Person added");
                totalpersons.setText(Integer.toString( addPersons.size()+1));
                Log.e("Size Other Persons", Integer.toString(addPersons.size()));
                if ((addPersons.size() + 1) == Integer.parseInt(numberpersons.getText().toString())) {
                    offlineDataEntry.setOtherPersons(addPersons);
                    totalpersons.setText(Integer.toString(offlineDataEntry.getOtherPersons().size()+1));
                    Log.e("Size Other Persons", Integer.toString(offlineDataEntry.getOtherPersons().size()));
                    proceed.setVisibility(View.VISIBLE);
                    addmore.setVisibility(View.GONE);
                } else {
                    proceed.setVisibility(View.GONE);
                    addmore.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onTaskCompleted(List<?> data, TaskType taskType) throws JSONException {
        //TODO TODAY
        if(taskType == TaskType.GET_DISTRICT_VIA_STATE){
            List<DistrictPojo> pojo = (List<DistrictPojo>) data;
            if (!pojo.isEmpty()) {
                fromAdapter = new GenericAdapter(ManualEntry.this, android.R.layout.simple_spinner_item, pojo);
                fromdistrict.setAdapter(fromAdapter);

            } else {
                CD.showDialog(ManualEntry.this, "No District found for the specific State");
                fromAdapter = null;
                fromdistrict.setAdapter(fromAdapter);
            }
        }
        else if(taskType == TaskType.GET_TEHSIL_VIA_DISTRICT){
            List<TehsilPojo> tehsils = (List<TehsilPojo>) data;
            if (!tehsils.isEmpty()) {
                adapter_tehsil = new GenericAdapterTehsil(ManualEntry.this, android.R.layout.simple_spinner_item, tehsils);
                tehsil.setAdapter(adapter_tehsil);

            } else {
                CD.showDialog(ManualEntry.this, "No Tehsils found for the specific District");
                adapter_tehsil = null;
                tehsil.setAdapter(adapter_tehsil);
            }
        }

        else if(taskType == TaskType.GET_BLOCK_VIA_DISTRICT){
            List<BlockPojo> blocks = (List<BlockPojo>) data;
            if (!blocks.isEmpty()) {

                adapterBlocks = new GenericAdapterBlocks(ManualEntry.this, android.R.layout.simple_spinner_item, blocks);
                block.setAdapter(adapterBlocks);

            } else {
                CD.showDialog(ManualEntry.this, "No Blocks found for the specific District");
                adapterBlocks = null;
                block.setAdapter(adapterBlocks);
            }
        }

        else if(taskType == TaskType.GET_GP_VIA_DISTRICT){
            List<GramPanchayatPojo> grampanchayats = (List<GramPanchayatPojo>) data;
            Log.e("Size", Integer.toString(grampanchayats.size()));

            if (grampanchayats.size() != 0) {

                if(Global_toBlockName.contains("Town")){
                    GramPanchayatPojo pojo = new GramPanchayatPojo();
                    pojo.setGp_id("0");
                    pojo.setGp_name("Please Select");
                    grampanchayats.add(0, pojo);
                }
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

    }
}


