package com.doi.himachal.epasshp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doi.himachal.Adapter.GenericAdapter;
import com.doi.himachal.Adapter.GenericAdapterBlocks;
import com.doi.himachal.Adapter.GenericAdapterGP;
import com.doi.himachal.Adapter.GenericAdapterStates;
import com.doi.himachal.Adapter.GenericAdapterTehsil;
import com.doi.himachal.Modal.AddMorePeoplePojo;
import com.doi.himachal.Modal.BlockPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.GramPanchayatPojo;
import com.doi.himachal.Modal.OfflineDataEntry;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.TehsilPojo;
import com.doi.himachal.Modal.UploadObjectManual;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.generic.GenericAsyncDatabaseObject;
import com.doi.himachal.generic.GenericAsyncPostObjectForm;
import com.doi.himachal.interfaces.AsyncTaskListenerDatabase;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectForm;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.AppStatus;
import com.doi.himachal.utilities.CommonUtils;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddMoreActivity extends AppCompatActivity implements AsyncTaskListenerObjectForm, AsyncTaskListenerDatabase {
    private OfflineDataEntry parent_details = null;

    TextView date, time, totalpersons;
    EditText names, numberpersons, vehiclenumber, mobilenumber, address, fromplace, placenameto, passno, authority, purpose, remarks;
    SearchableSpinner fromstate, fromdistrict, district, tehsil, block, gp, appdownloaded;
    DatabaseHandler DB = new DatabaseHandler(AddMoreActivity.this);
    CustomDialog CD = new CustomDialog();
    Button back, addperson, addmore;
    private ProgressDialog progressDialog;

    List<StatePojo> states = null;
    List<DistrictPojo> districts = null;
    List<DistrictPojo> fromdistricts = null;
    List<BlockPojo> blocks = null;
    List<TehsilPojo> tehsils = null;
    List<GramPanchayatPojo> grampanchayats = null;

    GenericAdapterStates adapter_states = null;
    GenericAdapter adapter, fromAdapter = null;
    GenericAdapterTehsil adapter_tehsil = null;
    GenericAdapterBlocks adapterBlocks = null;
    GenericAdapterGP adaptergp = null;

    LinearLayout grampanchayat;
    AddMorePeoplePojo addMorePeople = new AddMorePeoplePojo();

    String Global_fromstate, Global_fromdistrict, Global_todistrict, Global_totehsil, Global_toblock, Global_togramPanchayat,Global_toBlockName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);

        Intent getParent = getIntent();
        parent_details = (OfflineDataEntry) getParent.getSerializableExtra("PARENT");
        Log.e("PARENT", parent_details.toString());

        init();


        //GET States
        states = DB.getStates();
        adapter_states = new GenericAdapterStates(AddMoreActivity.this, android.R.layout.simple_spinner_item, states);
        fromstate.setAdapter(adapter_states);
        fromstate.setSelection(parent_details.getPosition_from_state());

        fromstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatePojo item = adapter_states.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("Stateid", item.getState_id());
                fromdistricts = DB.getDistrictsViaState(item.getState_id());
                Global_fromstate = item.getState_id();


                /**
                 * TODO HERE TODAY
                 */
                new GenericAsyncDatabaseObject(
                        AddMoreActivity.this,
                        AddMoreActivity.this,
                        TaskType.GET_DISTRICT_VIA_STATE).
                        execute(TaskType.GET_DISTRICT_VIA_STATE.toString(),item.getState_id());

//                if (!fromdistricts.isEmpty()) {
//                    fromAdapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, fromdistricts);
//                    fromdistrict.setAdapter(fromAdapter);
//                    fromdistrict.setSelection(parent_details.getPosition_from_district());
//
//                } else {
//                    CD.showDialog(AddMoreActivity.this, "No District found for the specific State");
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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Get Districts
        districts = DB.getDistrictsViaState("9");
        Log.e("@@@District", districts.toString());
        adapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, districts);
        district.setAdapter(adapter);
        district.setSelection((int) adapter.getItemId(parent_details.getPosition_to_district()));


        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("District Id-===", item.getDistrict_name());

                Global_todistrict = item.getDistrict_id();
                //TODO TODAY TWO
                /**
                 * TODO HERE TODAY
                 */
                new GenericAsyncDatabaseObject(
                        AddMoreActivity.this,
                        AddMoreActivity.this,
                        TaskType.GET_TEHSIL_VIA_DISTRICT).
                        execute(TaskType.GET_TEHSIL_VIA_DISTRICT.toString(),item.getDistrict_id());


                new GenericAsyncDatabaseObject(
                        AddMoreActivity.this,
                        AddMoreActivity.this,
                        TaskType.GET_BLOCK_VIA_DISTRICT).
                        execute(TaskType.GET_BLOCK_VIA_DISTRICT.toString(),item.getDistrict_id());
              //  tehsils = DB.getTehsilViaDistrict(item.getDistrict_id());
               // blocks = DB.getBlocksViaDistrict(item.getDistrict_id());

//                if (!tehsils.isEmpty()) {
//                    adapter_tehsil = new GenericAdapterTehsil(AddMoreActivity.this, android.R.layout.simple_spinner_item, tehsils);
//                    tehsil.setAdapter(adapter_tehsil);
//                    tehsil.setSelection((int) adapter_tehsil.getItemId(parent_details.getPosition_to_tehsil()));
//
//                } else {
//                    CD.showDialog(AddMoreActivity.this, "No Tehsils found for the specific District");
//                    adapter_tehsil = null;
//                    tehsil.setAdapter(adapter_tehsil);
//                }
//                if (!blocks.isEmpty()) {
//
//                    adapterBlocks = new GenericAdapterBlocks(AddMoreActivity.this, android.R.layout.simple_spinner_item, blocks);
//                    block.setAdapter(adapterBlocks);
//                    block.setSelection((int) adapterBlocks.getItemId(parent_details.getPosition_to_block()));
//
//                } else {
//                    CD.showDialog(AddMoreActivity.this, "No Blocks found for the specific District");
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


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMoreActivity.this.finish();
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
                //TODO TODAY THIRD
                new GenericAsyncDatabaseObject(
                        AddMoreActivity.this,
                        AddMoreActivity.this,
                        TaskType.GET_GP_VIA_DISTRICT).
                        execute(TaskType.GET_GP_VIA_DISTRICT.toString(),item.getBlock_code());
//                grampanchayats = DB.getGPViaDistrict(item.getBlock_code());
//
//                Log.e("Size", Integer.toString(grampanchayats.size()));
//
//                if (grampanchayats.size() != 0) {
//
//                    if(item.getBlock_name().contains("Town")){
//                        GramPanchayatPojo pojo = new GramPanchayatPojo();
//                        pojo.setGp_id("0");
//                        pojo.setGp_name("Please Select");
//                        grampanchayats.add(0, pojo);
//                    }
//
//                    grampanchayat.setVisibility(View.VISIBLE);
//                    adaptergp = new GenericAdapterGP(AddMoreActivity.this, android.R.layout.simple_spinner_item, grampanchayats);
//                    gp.setAdapter(adaptergp);
//                    gp.setSelection((int) adaptergp.getItemId(parent_details.getPosition_to_panchayat()));
//
//                } else {
//                    // CD.showDialog(AddMoreActivity.this, "No Panchayats found for the specific blocks");
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



            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setData(parent_details);

        addperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numberpersons.getText().toString().isEmpty()) {


                    addMorePeople.setFrom_state(Global_fromstate);
                    parent_details.setState_from(Global_fromstate);
                    addMorePeople.setFrom_district(Global_fromdistrict);
                    parent_details.setDistrict_from(Global_fromdistrict);

                    addMorePeople.setDistrict(Global_todistrict);
                    parent_details.setDistrict_to(Global_todistrict);
                    addMorePeople.setTehsil(Global_totehsil);
                    parent_details.setTehsil_to(Global_totehsil);
                    addMorePeople.setBlock_town(Global_toblock);
                    parent_details.setBlock_to(Global_toblock);


                    addMorePeople.setApp_downloaded(appdownloaded.getSelectedItem().toString());
                    parent_details.setAaroyga_app_download(appdownloaded.getSelectedItem().toString());


                    if (passno.getText().toString() == null || passno.getText().toString().isEmpty()) {
                        addMorePeople.setPass_number("NA");
                        parent_details.setPass_no("NA");
                    } else {
                        addMorePeople.setPass_number(passno.getText().toString().trim());
                        parent_details.setPass_no(passno.getText().toString().trim());
                    }

                    if (authority.getText().toString() == null || authority.getText().toString().isEmpty()) {
                        addMorePeople.setAutority("NA");
                        parent_details.setPass_issue_authority("NA");
                    } else {
                        addMorePeople.setAutority(authority.getText().toString().trim());
                        parent_details.setPass_issue_authority(authority.getText().toString().trim());
                    }


                    if (fromplace.getText().toString() == null || fromplace.getText().toString().isEmpty()) {
                        addMorePeople.setFrom_place("");
                        parent_details.setPlace_form("");
                    } else {
                        addMorePeople.setFrom_place(fromplace.getText().toString().trim());
                        parent_details.setPlace_form(fromplace.getText().toString());
                    }

                    if (placenameto.getText().toString() == null || placenameto.getText().toString().isEmpty()) {
                        addMorePeople.setPlace("");
                        parent_details.setPlace_to("");
                    } else {
                        addMorePeople.setPlace(placenameto.getText().toString().trim());
                        parent_details.setPlace_to(placenameto.getText().toString().trim());
                    }

                    if (purpose.getText().toString() == null || purpose.getText().toString().isEmpty()) {
                        addMorePeople.setPurpose("");
                        parent_details.setPurpose("");
                    } else {
                        addMorePeople.setPurpose(purpose.getText().toString().trim());
                        parent_details.setPurpose(purpose.getText().toString().trim());
                    }

                    if (Global_togramPanchayat == null || Global_togramPanchayat.isEmpty()) {
                        addMorePeople.setGp_ward("0");
                        parent_details.setGram_panchayat("0");
                        Log.e("Global_togramPanchayat", Global_togramPanchayat);
                    } else {
                        Log.e("Global_togramPanchayat", Global_togramPanchayat);
                        addMorePeople.setGp_ward(Global_togramPanchayat);
                        parent_details.setGram_panchayat(Global_togramPanchayat);
                    }

                    if (remarks.getText().toString() == null || remarks.getText().toString().isEmpty()) {
                        addMorePeople.setRemarks("");
                        parent_details.setRemarks("");

                    } else {

                        addMorePeople.setRemarks(remarks.getText().toString().trim());
                        parent_details.setRemarks(remarks.getText().toString().trim());
                    }


                    //Edit Text get data
                    if (names.getText().toString() != null && !names.getText().toString().isEmpty()) {
                        addMorePeople.setEnter_name(names.getText().toString().trim());
                        parent_details.setNames(names.getText().toString().trim());
                        //if (numberpersons.getText().toString() != null && !numberpersons.getText().toString().isEmpty()) {
                        // addMorePeople.setNumber_of_persons(numberpersons.getText().toString().trim());

                        if (vehiclenumber.getText().toString() != null && !vehiclenumber.getText().toString().isEmpty()) {
                            addMorePeople.setVehical_number(vehiclenumber.getText().toString().trim());
                            parent_details.setVehicle_number(vehiclenumber.getText().toString().trim());

                            if (mobilenumber.getText().toString() != null && mobilenumber.getText().toString().length() == 10 && !mobilenumber.getText().toString().isEmpty()) {
                                addMorePeople.setMobile_number(mobilenumber.getText().toString().trim());
                                parent_details.setMobile(mobilenumber.getText().toString().trim());

                               // parent_details.setNo_of_persons("1");



                                UploadObjectManual object = new UploadObjectManual();
                                object.setUrl("http://covid19epass.hp.gov.in/api/v1/saveofflinebarrierdata");
                                object.setTasktype(TaskType.MANUAL_FORM_UPLOAD);
                                object.setMethordName("saveofflinebarrierdata");
                                object.setOfflineDataEntry(parent_details);


                                //TODO SEND OBECT TO SERVER
                                System.out.println("#@#@#@#@#@"+addMorePeople.toString());
                                Log.e("From State @#@#@#@#@", Global_fromstate);
                                System.out.println(parent_details.toString());
//TODO
                                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                                    new GenericAsyncPostObjectForm(
                                            AddMoreActivity.this,
                                            AddMoreActivity.this,
                                            TaskType.MANUAL_FORM_UPLOAD).
                                            execute(object);
                                } else {
                                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
                                }

                                //TODO SEND OBECT TO SERVER

//                                    Intent intent = new Intent();
//                                    intent.putExtra("AddMore", addMorePeople);
//                                   setResult(Activity.RESULT_OK, intent);
//
//                                    finish();


                            } else {
                                CD.showDialog(AddMoreActivity.this, "Please enter valid 10 digit mobile phone number.");
                            }

                        } else {
                            CD.showDialog(AddMoreActivity.this, "Please enter Vehicle Number .");
                        }


                        //  } //else {
                        //   CD.showDialog(AddMoreActivity.this, "Please enter number of persons travelling .");
                        // }


                    } else {
                        CD.showDialog(AddMoreActivity.this, "Please enter name .");
                    }


                } else {
                    CD.showDialog(AddMoreActivity.this, "Please Enter the number of Persons");
                }
            }
        });

    }

    private void setData(OfflineDataEntry parent_details) {

        vehiclenumber.setText(parent_details.getVehicle_number());
        fromplace.setText(parent_details.getPlace_form());
        placenameto.setText(parent_details.getPlace_to());
        passno.setText(parent_details.getPass_no());
        authority.setText(parent_details.getPass_issue_authority());
        purpose.setText(parent_details.getPurpose());
        remarks.setText(parent_details.getRemarks());
        if(parent_details.getOtherPersons()==null){
            totalpersons.setText("2");
        }else{
            Log.e("size",Integer.toString(parent_details.getOtherPersons().size()));
            totalpersons.setText(Integer.toString(parent_details.getOtherPersons().size() + 2));
        }



        Log.e("getState_from", parent_details.getState_from());

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
        addperson = findViewById(R.id.addperson);
        remarks = findViewById(R.id.remarks);

        addmore = findViewById(R.id.addmore);
        totalpersons = findViewById(R.id.totalpersons);


        grampanchayat = findViewById(R.id.gml);


    }


    public static <T> List<T> convertSetToList(Set<T> set) {
        // create a list from Set
        List<T> list = new ArrayList<>(set);

        // return the list
        return list;
    }

    @Override
    public void onTaskCompleted(ResponsePojo result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.MANUAL_FORM_UPLOAD) {
            // CD.showDialog(ManualEntry.this,result.getResponse());
            SuccessResponse response = JsonParse.getSuccessResponse(result.getResponse());
            if (response.getStatus().equalsIgnoreCase("200")) {
                // CD.showDialogCloseActivity(AddMoreActivity.this, "Data Saved Successfully. " + response.getMessage());
                Intent intent = new Intent();
                intent.putExtra("AddMore", addMorePeople);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                CD.showDialogHTMLGeneric(AddMoreActivity.this, response.getResponse());
            }
        }

    }

    @Override
    public void onTaskCompleted(List<?> data, TaskType taskType) throws JSONException {
        //TODO TODAY
        if(taskType == TaskType.GET_DISTRICT_VIA_STATE){
            List<DistrictPojo> pojo = (List<DistrictPojo>) data;
            if (!pojo.isEmpty()) {
                fromAdapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, pojo);
                fromdistrict.setAdapter(fromAdapter);
                fromdistrict.setSelection(parent_details.getPosition_from_district());

            } else {
                CD.showDialog(AddMoreActivity.this, "No District found for the specific State");
                fromAdapter = null;
                fromdistrict.setAdapter(fromAdapter);
            }
        }
        else if(taskType == TaskType.GET_TEHSIL_VIA_DISTRICT){
            List<TehsilPojo> tehsils = (List<TehsilPojo>) data;
            if (!tehsils.isEmpty()) {
                adapter_tehsil = new GenericAdapterTehsil(AddMoreActivity.this, android.R.layout.simple_spinner_item, tehsils);
                tehsil.setAdapter(adapter_tehsil);
                tehsil.setSelection((int) adapter_tehsil.getItemId(parent_details.getPosition_to_tehsil()));

            } else {
                CD.showDialog(AddMoreActivity.this, "No Tehsils found for the specific District");
                adapter_tehsil = null;
                tehsil.setAdapter(adapter_tehsil);
            }
        }

        else if(taskType == TaskType.GET_BLOCK_VIA_DISTRICT){
            List<BlockPojo> blocks = (List<BlockPojo>) data;
            if (!blocks.isEmpty()) {

                adapterBlocks = new GenericAdapterBlocks(AddMoreActivity.this, android.R.layout.simple_spinner_item, blocks);
                block.setAdapter(adapterBlocks);
                block.setSelection((int) adapterBlocks.getItemId(parent_details.getPosition_to_block()));

            } else {
                CD.showDialog(AddMoreActivity.this, "No Blocks found for the specific District");
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
                adaptergp = new GenericAdapterGP(AddMoreActivity.this, android.R.layout.simple_spinner_item, grampanchayats);
                gp.setAdapter(adaptergp);
                gp.setSelection((int) adaptergp.getItemId(parent_details.getPosition_to_panchayat()));

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
