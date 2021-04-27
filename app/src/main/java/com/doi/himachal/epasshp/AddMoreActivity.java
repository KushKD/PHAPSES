package com.doi.himachal.epasshp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.doi.himachal.Adapter.GenericAdapterCategory;
import com.doi.himachal.Adapter.GenericAdapterGP;
import com.doi.himachal.Adapter.GenericAdapterStates;
import com.doi.himachal.Adapter.GenericAdapterSubCategory;
import com.doi.himachal.Adapter.GenericAdapterTehsil;
import com.doi.himachal.Modal.AddMorePeoplePojo;
import com.doi.himachal.Modal.BlockPojo;
import com.doi.himachal.Modal.CategoryPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.GramPanchayatPojo;
import com.doi.himachal.Modal.MastersPojoServer;
import com.doi.himachal.Modal.OfflineDataEntry;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ResponsePojoGet;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.SubCategoryPojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.TehsilPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.Modal.UploadObjectManual;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.generic.GenericAsyncDatabaseObject;
import com.doi.himachal.generic.GenericAsyncPostObjectForm;
import com.doi.himachal.generic.Generic_Async_Get;
import com.doi.himachal.generic.Generic_Async_Post;
import com.doi.himachal.interfaces.AsyncTaskListenerDatabase;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectForm;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectGet;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectPost;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.AppStatus;
import com.doi.himachal.utilities.CommonUtils;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddMoreActivity extends AppCompatActivity implements AsyncTaskListenerObjectForm, AsyncTaskListenerObjectGet, AsyncTaskListenerObjectPost {
    private OfflineDataEntry parent_details = null;

    TextView date, time, totalpersons , red_zone;
    EditText names, numberpersons, vehiclenumber, mobilenumber, address, fromplace, placenameto, passno, authority, purpose, remarks, qplace;
    SearchableSpinner fromstate, fromdistrict, district, tehsil, block, gp, appdownloaded,category_sp,subcategory_sp,quarantine;
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
    List<CategoryPojo> categories = null;

    GenericAdapterSubCategory adapterSubCategory = null;
    List<SubCategoryPojo> subCategories  = null;
    GenericAdapterStates adapter_states = null;
    GenericAdapter adapter, fromAdapter = null;
    GenericAdapterTehsil adapter_tehsil = null;
    GenericAdapterBlocks adapterBlocks = null;
    GenericAdapterGP adaptergp = null;
    GenericAdapterCategory adapterCategory = null;

    LinearLayout quarentine_layout;

    LinearLayout grampanchayat;
    AddMorePeoplePojo addMorePeople = new AddMorePeoplePojo();

    String Global_fromstate,Global_Category,Global_SubCategory, Global_fromdistrict, Global_todistrict, Global_totehsil, Global_toblock, Global_togramPanchayat,Global_toBlockName,Global_Quarentine, Global_QuarentinePlace;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);

        Intent getParent = getIntent();
        parent_details = (OfflineDataEntry) getParent.getSerializableExtra("PARENT");
        Log.e("PARENT", parent_details.toString());

        init();

        if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
            UploadObject object = new UploadObject();
            object.setUrl(Econstants.URL_HTTPS);
            object.setTasktype(TaskType.GET_CATEGORIES);
            object.setMethordName("getcategory");
            object.setParam("");

            new Generic_Async_Get(
                    AddMoreActivity.this,
                    AddMoreActivity.this,
                    TaskType.GET_CATEGORIES).
                    execute(object);

            UploadObject statesObject = new UploadObject();
            statesObject.setUrl(Econstants.URL_HTTPS);
            statesObject.setTasktype(TaskType.GET_STATES);
            statesObject.setMethordName("getstates");
            statesObject.setParam("");

            new Generic_Async_Get(
                    AddMoreActivity.this,
                    AddMoreActivity.this,
                    TaskType.GET_STATES).
                    execute(statesObject);
        } else {
            CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
        }


//        //GET States
//        states = DB.getStates();
//        adapter_states = new GenericAdapterStates(AddMoreActivity.this, android.R.layout.simple_spinner_item, states);
//        fromstate.setAdapter(adapter_states);

        quarantine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Global_Quarentine = quarantine.getSelectedItem().toString();
                if(Global_Quarentine.equalsIgnoreCase("--Select--")){
                    Global_Quarentine = "";
                    parent_details.setQuarantine(Global_Quarentine);
                    Log.e("tere",Global_Quarentine);
                    quarentine_layout.setVisibility(View.GONE);
                }else if(Global_Quarentine.equalsIgnoreCase("Institutional")){
                    Global_Quarentine = "Institutional";
                    Log.e("tere",Global_Quarentine);
                    parent_details.setQuarantine(Global_Quarentine);
                    quarentine_layout.setVisibility(View.VISIBLE);

                }else{
                    Global_Quarentine = "Home";
                    Log.e("tere",Global_Quarentine);
                    parent_details.setQuarantine(Global_Quarentine);
                    quarentine_layout.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fromstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatePojo item = adapter_states.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("Stateid", item.getState_id());



                /**
                 *   7 == Gujarat
                 *   12 == Karnataka
                 *   15 == Maharashtra
                 *   21 == Punjab
                 *   22 == Rajasthan
                 *   27 == Uttar Pradesh
                 *   34 == Delhi
                 */

                if(item.getState_name().equalsIgnoreCase("Gujarat") ||
                        item.getState_name().equalsIgnoreCase("Karnataka") ||
                        item.getState_name().equalsIgnoreCase("Maharashtra") ||
                        item.getState_name().equalsIgnoreCase("Punjab") ||
                        item.getState_name().equalsIgnoreCase("Rajasthan") ||
                        item.getState_name().equalsIgnoreCase("Uttar Pradesh") ||
                        item.getState_name().equalsIgnoreCase("Delhi") ){

                    red_zone.setVisibility(View.VISIBLE);
                    red_zone.setBackgroundColor(Color.parseColor("#800000"));
                    red_zone.setTextColor(Color.WHITE);
                    red_zone.setText("Red Zone Area");

                }else{
                    red_zone.setVisibility(View.GONE);
                }

                fromdistricts = DB.getDistrictsViaState(item.getState_id());
                Global_fromstate = item.getState_id();

                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_DISTRICT_VIA_STATE);
                    object.setMethordName("getdistricts");
                    object.setParam("state_id=");
                    object.setParam2(Integer.parseInt(Global_fromstate));
                    new Generic_Async_Post(
                            AddMoreActivity.this,
                            AddMoreActivity.this,
                            TaskType.GET_DISTRICT_VIA_STATE).
                            execute(object);
                } else {
                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
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

        category_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryPojo item = adapterCategory.getItem(position);


                //  Global_district_id = item.getDistrict_id();
                Log.e("category ID", item.getCategory_id());
                Global_Category = item.getCategory_id();
                //Get Sub Category
                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_SUBCATEGORY);
                    object.setMethordName("getsubpasscategory");
                    object.setParam("pass_category_id=");
                    object.setParam2(Integer.parseInt(Global_Category));
                    new Generic_Async_Post(
                            AddMoreActivity.this,
                            AddMoreActivity.this,
                            TaskType.GET_SUBCATEGORY).
                            execute(object);
                } else {
                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subcategory_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubCategoryPojo item = adapterSubCategory.getItem(position);


                //  Global_district_id = item.getDistrict_id();
                Log.e("Sub category ID", item.getId());
                Global_SubCategory = item.getId();
                //Get Sub Category



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Get Districts TODO
        if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
            UploadObject object = new UploadObject();
            object.setUrl(Econstants.URL_HTTPS);
            object.setTasktype(TaskType.GET_DISTRICT_VIA_STATE_LOCAL);
            object.setMethordName("getdistricts");
            object.setParam("state_id=");
            object.setParam2(9);
            new Generic_Async_Post(
                    AddMoreActivity.this,
                    AddMoreActivity.this,
                    TaskType.GET_DISTRICT_VIA_STATE_LOCAL).
                    execute(object);
        } else {
            CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
        }


        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictPojo item = adapter.getItem(position);

                //  Global_district_id = item.getDistrict_id();
                Log.e("District Id-===", item.getDistrict_name());

                Global_todistrict = item.getDistrict_id();
                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_TEHSIL_VIA_DISTRICT);
                    object.setMethordName("gettehsils");
                    object.setParam("district=");
                    object.setParam2(Integer.parseInt(Global_todistrict));
                    new Generic_Async_Post(
                            AddMoreActivity.this,
                            AddMoreActivity.this,
                            TaskType.GET_TEHSIL_VIA_DISTRICT).
                            execute(object);
                } else {
                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
                }


                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_BLOCK_VIA_DISTRICT);
                    object.setMethordName("getblocks");
                    object.setParam("district=");
                    object.setParam2(Integer.parseInt(Global_todistrict));
                    new Generic_Async_Post(
                            AddMoreActivity.this,
                            AddMoreActivity.this,
                            TaskType.GET_BLOCK_VIA_DISTRICT).
                            execute(object);
                } else {
                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
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
                if (AppStatus.getInstance(AddMoreActivity.this).isOnline()) {
                    UploadObject object = new UploadObject();
                    object.setUrl(Econstants.URL_HTTPS);
                    object.setTasktype(TaskType.GET_GP_VIA_DISTRICT);
                    object.setMethordName("getpanchyatv1");
                    object.setParam("block=");
                    object.setParam2(Integer.parseInt(Global_toblock));
                    new Generic_Async_Post(
                            AddMoreActivity.this,
                            AddMoreActivity.this,
                            TaskType.GET_GP_VIA_DISTRICT).
                            execute(object);
                } else {
                    CD.showDialog(AddMoreActivity.this, "Please connect to Internet and try again.");
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

        setData(parent_details);

        addperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numberpersons.getText().toString().isEmpty()) {


                    if(qplace.getText() != null || !qplace.getText().toString().isEmpty()){
                        parent_details.setQuarantinePlace(qplace.getText().toString());
                    }else{
                        parent_details.setQuarantinePlace("");
                    }


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
                    addMorePeople.setCategory(Global_Category);
                    parent_details.setCategoryId(Global_Category);

                    addMorePeople.setSubCategory(Global_SubCategory);
                    parent_details.setSubCategoryId(Global_SubCategory);


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
                                object.setUrl(Econstants.URL_HTTPS+"saveofflinebarrierdatav1");
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
        category_sp = findViewById(R.id.category_sp);
        subcategory_sp = findViewById(R.id.subcategory_sp);
        quarantine = findViewById(R.id.quarantine);
        qplace = findViewById(R.id.qplace);
        quarentine_layout = findViewById(R.id.quarentine_layout);

        red_zone = findViewById(R.id.red_zone);

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
            // CD.showDialog(AddMoreActivity.this,result.getResponse());
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
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {
        if (taskType == TaskType.GET_CATEGORIES) {
            Log.e("Result==", result.toString());
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                try {
                    SuccessResponse response = JsonParse.getSuccessResponse(result.getResponse());

                    if (response.getStatus().equalsIgnoreCase("200")) {

                        //Parse Json Array
                        JSONArray jsonArray = new JSONArray(response.getResponse());
                        if (jsonArray.length() != 0) {
                            categories = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CategoryPojo pojo = new CategoryPojo();
                                JSONObject object = jsonArray.getJSONObject(i);
                                pojo.setCategory_id(object.optString("category_id"));
                                pojo.setCategory_name(object.optString("category_name"));


                                categories.add(pojo);
                            }

                            adapterCategory = new GenericAdapterCategory(AddMoreActivity.this, android.R.layout.simple_spinner_item, categories);
                            category_sp.setAdapter(adapterCategory);
                            category_sp.setSelection((int) adapterCategory.getItemId(parent_details.getPosition_to_category()));


                        } else {
                            CD.showDialog(AddMoreActivity.this, response.getMessage());
                        }
                    } else {
                        CD.showDialog(AddMoreActivity.this, response.getMessage());
                    }

                } catch (Exception ex) {
                    CD.showDialog(AddMoreActivity.this, result.getResponse());
                }
            }
        }else if (taskType == TaskType.GET_STATES) {


            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    Log.e("Status", jsonArray.toString());
                    if (jsonArray.length() != 0) {
                        states = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            StatePojo pojo = new StatePojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setState_id(object.optString("id"));
                            pojo.setState_name(object.optString("name"));

                            states.add(pojo);
                        }
                        StatePojo statePojo = new StatePojo();
                        statePojo.setState_id("-1");
                        statePojo.setState_name("-- Select --");
                        states.add(0, statePojo);

                        if (!states.isEmpty()) {
                            adapter_states = new GenericAdapterStates(AddMoreActivity.this, android.R.layout.simple_spinner_item, states);
                            fromstate.setAdapter(adapter_states);
                            fromstate.setSelection(parent_details.getPosition_from_state());

                        } else {

                            CD.showDialog(AddMoreActivity.this, "States Not Found. Something bad happened.");
                            adapter_states = null;
                            fromstate.setAdapter(adapter_states);
                        }
                    }
                } else {
                    CD.showDialog(AddMoreActivity.this, "States Not Found. Something bad happened.");
                    adapter_states = null;
                    fromstate.setAdapter(adapter_states);
                }
            }
        } else if (taskType == TaskType.GET_SUBCATEGORY) {


            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    Log.e("Status", jsonArray.toString());
                    if (jsonArray.length() != 0) {
                        subCategories = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SubCategoryPojo pojo = new SubCategoryPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setId(object.optString("id"));
                            pojo.setName(object.optString("name"));
                            subCategories.add(pojo);
                        }

                        SubCategoryPojo subCategoryPojo = new SubCategoryPojo();
                        subCategoryPojo.setId("-1");
                        subCategoryPojo.setName("-- Select --");
                        subCategories.add(0, subCategoryPojo);

                        if (!subCategories.isEmpty()) {
                            adapterSubCategory = new GenericAdapterSubCategory(AddMoreActivity.this, android.R.layout.simple_spinner_item, subCategories);
                            subcategory_sp.setAdapter(adapterSubCategory);
                            subcategory_sp.setSelection((int) adapter.getItemId(parent_details.getPosition_to_subcategory()));

                        } else {

                            CD.showDialog(AddMoreActivity.this, "Sub Categories Not Found. Something bad happened.");
                            adapterSubCategory = null;
                            subcategory_sp.setAdapter(adapterSubCategory);
                        }
                    }
                } else {
                    CD.showDialog(AddMoreActivity.this, "Sub Categories  Not Found. Something bad happened.");
                    adapterSubCategory = null;
                    subcategory_sp.setAdapter(adapterSubCategory);
                }
            }
        }else if (taskType == TaskType.GET_DISTRICT_VIA_STATE) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    districts = new ArrayList<>();
                    DistrictPojo districtPojo = new DistrictPojo();
                    districtPojo.setDistrict_id("-1");
                    districtPojo.setDistrict_name("-- Select --");
                    districts.add(0, districtPojo);

                    JSONArray jsonArray = new JSONArray(response.getRecords());

                    if (jsonArray.length() != 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            DistrictPojo pojo = new DistrictPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setDistrict_id(object.optString("district_id"));
                            pojo.setDistrict_name(object.optString("district_name"));
                            pojo.setState_id(object.optString("state_id"));

                            districts.add(pojo);
                        }

                        fromAdapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, districts);
                        fromdistrict.setAdapter(fromAdapter);
                        fromdistrict.setSelection(parent_details.getPosition_from_district());
                    } else {
                        CD.showDialog(AddMoreActivity.this, "District Not Found.");
                        fromAdapter = null;
                        fromdistrict.setAdapter(fromAdapter);
                    }
                } else {
                    districts = new ArrayList<>();
                    DistrictPojo districtPojo = new DistrictPojo();
                    districtPojo.setDistrict_id("-1");
                    districtPojo.setDistrict_name("-- Select --");
                    districts.add(0, districtPojo);
                    fromAdapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, districts);
                    fromdistrict.setAdapter(fromAdapter);
                   // CD.showDialog(AddMoreActivity.this, "Something went wrong. Please connect to Internet and try again.");
                }

            }

        } else if (taskType == TaskType.GET_DISTRICT_VIA_STATE_LOCAL) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
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
                        DistrictPojo districtPojo = new DistrictPojo();
                        districtPojo.setDistrict_id("-1");
                        districtPojo.setDistrict_name("-- Select --");
                        districts.add(0, districtPojo);

                        adapter = new GenericAdapter(AddMoreActivity.this, android.R.layout.simple_spinner_item, districts);
                        district.setAdapter(adapter);
                        district.setSelection((int) adapter.getItemId(parent_details.getPosition_to_district()));
                    } else {
                        CD.showDialog(AddMoreActivity.this, "District Not Found.");
                        adapter = null;
                        district.setAdapter(adapter);
                    }
                } else {
                    CD.showDialog(AddMoreActivity.this, "Something went wrong. Please connect to Internet and try again.");
                }

            }

        }

        //TODO
        else if (taskType == TaskType.GET_TEHSIL_VIA_DISTRICT) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    tehsils = new ArrayList<>();
                    if (jsonArray.length() != 0) {
                        TehsilPojo tehsilPojo = new TehsilPojo();
                        tehsilPojo.setTehsil_id("-1");
                        tehsilPojo.setTehsil_name("-- Select --");
                        tehsils.add(0, tehsilPojo);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TehsilPojo pojo = new TehsilPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setTehsil_id(object.optString("id"));
                            pojo.setTehsil_name(object.optString("name"));

                            tehsils.add(pojo);
                        }

                        adapter_tehsil = new GenericAdapterTehsil(AddMoreActivity.this, android.R.layout.simple_spinner_item, tehsils);
                        tehsil.setAdapter(adapter_tehsil);
                        tehsil.setSelection((int) adapter_tehsil.getItemId(parent_details.getPosition_to_tehsil()));
                    } else {
                        tehsils = new ArrayList<>();
                        TehsilPojo tehsilPojo = new TehsilPojo();
                        tehsilPojo.setTehsil_id("-1");
                        tehsilPojo.setTehsil_name("-- Select --");
                        tehsils.add(0, tehsilPojo);
                        adapter_tehsil = new GenericAdapterTehsil(AddMoreActivity.this, android.R.layout.simple_spinner_item, tehsils);
                        tehsil.setAdapter(adapter_tehsil);
                    }
                } else {
                    //CD.showDialog(AddMoreActivity.this, "Something went wrong. Please connect to Internet and try again.");
                    tehsils = new ArrayList<>();
                    TehsilPojo tehsilPojox = new TehsilPojo();
                    tehsilPojox.setTehsil_id("-1");
                    tehsilPojox.setTehsil_name("-- Select --");
                    tehsils.add(0, tehsilPojox);
                    adapter_tehsil = new GenericAdapterTehsil(AddMoreActivity.this, android.R.layout.simple_spinner_item, tehsils);
                    tehsil.setAdapter(adapter_tehsil);
                }

            }

        } else if (taskType == TaskType.GET_BLOCK_VIA_DISTRICT) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    if (jsonArray.length() != 0) {
                        blocks = new ArrayList<>();
                        BlockPojo blocksPojo = new BlockPojo();
                        blocksPojo.setBlock_code("-1");
                        blocksPojo.setBlock_name("-- Select --");
                        blocks.add(0, blocksPojo);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BlockPojo pojo = new BlockPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setBlock_name(object.optString("name"));
                            pojo.setBlock_code(object.optString("id"));

                            blocks.add(pojo);
                        }


                        adapterBlocks = new GenericAdapterBlocks(AddMoreActivity.this, android.R.layout.simple_spinner_item, blocks);
                        block.setAdapter(adapterBlocks);
                        block.setSelection((int) adapterBlocks.getItemId(parent_details.getPosition_to_block()));
                    } else {
                        BlockPojo blocksPojo = new BlockPojo();
                        blocksPojo.setBlock_code("-1");
                        blocksPojo.setBlock_name("-- Select --");
                        blocks.add(0, blocksPojo);
                        // CD.showDialog(ManualEntry.this, "No Blocks Found.");
                        adapterBlocks = new GenericAdapterBlocks(AddMoreActivity.this, android.R.layout.simple_spinner_item, blocks);
                        block.setAdapter(adapterBlocks);
                    }
                } else {
                    blocks = new ArrayList<>();
                    BlockPojo blocksPojo = new BlockPojo();
                    blocksPojo.setBlock_code("-1");
                    blocksPojo.setBlock_name("-- Select --");
                    blocks.add(0, blocksPojo);
                    // CD.showDialog(ManualEntry.this, "No Blocks Found.");
                    adapterBlocks = new GenericAdapterBlocks(AddMoreActivity.this, android.R.layout.simple_spinner_item, blocks);
                    block.setAdapter(adapterBlocks);
                }

            }

        } else if (taskType == TaskType.GET_GP_VIA_DISTRICT) {
            if (result.getResponse().isEmpty()) {
                CD.showDialog(AddMoreActivity.this, "Please Connect to Internet and try again.");
            } else {

                MastersPojoServer response = JsonParse.MasterPojo(result.getResponse());
                Log.e("Status", response.getStatus());
                if (Integer.parseInt(response.getStatus()) == 200) {
                    JSONArray jsonArray = new JSONArray(response.getRecords());
                    if (jsonArray.length() != 0) {
                        grampanchayats = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            GramPanchayatPojo pojo = new GramPanchayatPojo();
                            JSONObject object = jsonArray.getJSONObject(i);
                            pojo.setGp_name(object.optString("name"));
                            pojo.setGp_id(object.optString("panchyat_code"));

                            grampanchayats.add(pojo);
                        }


                        if (Global_toBlockName.contains("Town")) {
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
                        adaptergp = null;
                        gp.setAdapter(adaptergp);
                        grampanchayat.setVisibility(View.GONE);
                        Global_togramPanchayat = "0";
                    }
                } else {
                    CD.showDialog(AddMoreActivity.this, "Something went wrong. Please connect to Internet and try again.");
                }

            }

        }
    }
}
