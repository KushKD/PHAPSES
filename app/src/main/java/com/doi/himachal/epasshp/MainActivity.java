package com.doi.himachal.epasshp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.GridView;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.doi.himachal.Adapter.HomeGridViewAdapter;
import com.doi.himachal.Adapter.SliderAdapter;
import com.doi.himachal.Modal.ModulesPojo;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.SuccessResponseDocuments;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.generic.GenericAsyncPostObject;
import com.doi.himachal.interfaces.AsyncTaskListenerObject;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.AppStatus;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;
import com.doi.himachal.utilities.SamplePresenter;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.Configurations;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;
import com.kushkumardhawan.locationmanager.constants.FailType;
import com.kushkumardhawan.locationmanager.constants.ProcessType;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObject {


    private static final int REQUEST_CODE_QR_SCAN = 101;  //103
    private static final int REQUEST_CODE_QR_SCAN_OUT = 103;
    CustomDialog CD = new CustomDialog();
    private final String LOGTAG = "QRCScanner-MainActivity";
    HomeGridViewAdapter adapter_modules;
    GridView home_gv;
    SliderView sliderView;
    SliderAdapter adapters = null;
    private ProgressDialog progressDialog;

    private SamplePresenter samplePresenter;
    public String userLocation = null;
    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        home_gv = findViewById(R.id.gv);
        sliderView = findViewById(R.id.imageSlider);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        samplePresenter = new SamplePresenter(this);
        getLocation();

        List<ModulesPojo> modules = new ArrayList<>();

        ModulesPojo mp = new ModulesPojo();
        mp.setId("1");
        mp.setName("Scan Incoming");
        mp.setLogo("scan");

        ModulesPojo mp2 = new ModulesPojo();
        mp2.setId("2");
        mp2.setName("Search Pass");
        mp2.setLogo("searchpass");



        ModulesPojo mp3 = new ModulesPojo();
        mp3.setId("3");
        mp3.setName("Manual Entry");
        mp3.setLogo("manualentry");

        ModulesPojo mp4 = new ModulesPojo();
        mp4.setId("4");
        mp4.setName("Scan Outgoing");
        mp4.setLogo("scan");

        ModulesPojo mp5 = new ModulesPojo();
        mp5.setId("5");
        mp5.setName("Logout from Barrier");
        mp5.setLogo("logout");


        modules.add(mp);
        modules.add(mp2);
        modules.add(mp3);
        modules.add(mp4);
        modules.add(mp5);

        // Log.e("userLocation",userLocation);


        adapter_modules = new HomeGridViewAdapter(this, (ArrayList<ModulesPojo>) modules);
        home_gv.setAdapter(adapter_modules);

        adapters = new SliderAdapter(MainActivity.this);
        sliderView.setSliderAdapter(adapters);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);

            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pullToRefresh.setRefreshing(false);
            }
        });

        home_gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (home_gv == null || home_gv.getChildCount() == 0) ? 0 : home_gv.getChildAt(0).getTop();
                pullToRefresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("We are Here", intent.getAction());
                if (intent.getAction() == "UploadServer") {
                    //SCAN_DATA
                    Log.e("We are Here 2", intent.getAction());

                    if (AppStatus.getInstance(MainActivity.this).isOnline()) {
                        Bundle extras = intent.getExtras();
                        ScanDataPojo data = (ScanDataPojo) extras.getSerializable("SCAN_DATA");
                        Log.e("Data From Dialog =====", data.toString());

                        //TODO Internet Check

                        UploadObject object = new UploadObject();
                      //  object.setUrl(Econstants.URL_HTTPS+"savebarrierdata");
                      //   object.setUrl(Econstants.URL_HTTPS+"savebarrierdatav1");  working code
                       // object.setUrl("http://covidepass.eypoc.com/savebarrierdata");
                        object.setUrl(Econstants.URL_HTTPS+"savebarrierdatav2");
                        object.setTasktype(TaskType.UPLOAD_SCANNED_PASS);
                        object.setMethordName("savebarrierdata");
                        object.setScanDataPojo(data);

                        new GenericAsyncPostObject(
                                MainActivity.this,
                                MainActivity.this,
                                TaskType.UPLOAD_SCANNED_PASS).
                                execute(object);
                    } else {
                        CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
                    }

                }else if (intent.getAction() == "UploadServerOut") {  //UploadServerOut
                    //SCAN_DATA
                    Log.e("We are Here 2", intent.getAction());

                    if (AppStatus.getInstance(MainActivity.this).isOnline()) {
                        Bundle extras = intent.getExtras();
                        ScanDataPojo data = (ScanDataPojo) extras.getSerializable("SCAN_DATA");
                        Log.e("Data From Dialog", data.toString());

                        //TODO Internet Check

                        UploadObject object = new UploadObject();
                        //  object.setUrl(Econstants.URL_HTTPS+"savebarrierdata");
                        //   object.setUrl(Econstants.URL_HTTPS+"savebarrierdatav1");  working code
                        // object.setUrl("http://covidepass.eypoc.com/savebarrierdata");
                        object.setUrl(Econstants.URL_HTTPS+"savebarrierdatav2");
                        object.setTasktype(TaskType.UPLOAD_SCANNED_PASS_OUT);
                        object.setMethordName("savebarrierdata");
                        object.setScanDataPojo(data);

                        new GenericAsyncPostObject(
                                MainActivity.this,
                                MainActivity.this,
                                TaskType.UPLOAD_SCANNED_PASS_OUT).
                                execute(object);
                    } else {
                        CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
                    }

                }



                else if (intent.getAction() == "UploadServerManual") {
                    //SCAN_DATA
                    Log.e("We are Here 2", intent.getAction());

                    if (AppStatus.getInstance(MainActivity.this).isOnline()) {
                        Bundle extras = intent.getExtras();
                        ScanDataPojo data = (ScanDataPojo) extras.getSerializable("SCAN_DATA");


                        //TODO Update LAT LONG
                        data = updateLocation(data);
                        Log.e("UploadServerManual", data.toString());
                        UploadObject object = new UploadObject();
                        object.setUrl(Econstants.URL_HTTPS+"savebarrierdata");
                        object.setTasktype(TaskType.UPLOAD_SCANNED_PASS);
                        object.setMethordName("savebarrierdata");
                        object.setScanDataPojo(data);

                        new GenericAsyncPostObject(
                                MainActivity.this,
                                MainActivity.this,
                                TaskType.UPLOAD_SCANNED_PASS).
                                execute(object);
                    } else {
                        CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
                    }

                } else if (intent.getAction() == "verifyData") {

                    //SCAN_DATA
                    Log.e("We are Here 2sd ", intent.getAction());

                    if (AppStatus.getInstance(MainActivity.this).isOnline()) {
                        Bundle extras = intent.getExtras();
                        String data = extras.getString("VERIFY_DATA");
                        Log.e("Data From Dialog", data.toString());

                        UploadObject object = new UploadObject();
                        object.setUrl(Econstants.URL_HTTPS+"verifydetails");
                        object.setTasktype(TaskType.VERIFY_DETAILS);
                        object.setMethordName("verifydetails");
                        object.setParam(data);


                        new GenericAsyncPostObject(
                                MainActivity.this,
                                MainActivity.this,
                                TaskType.VERIFY_DETAILS).
                                execute(object);

                    } else {
                        CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
                    }


                }
            }
        };


    }

    private ScanDataPojo updateLocation(ScanDataPojo scanData) {
        if (!userLocation.isEmpty()) {
            try {
                String[] locations = userLocation.split(",");
                scanData.setLatitude(locations[0]);
                scanData.setLongitude(locations[1]);
            } catch (Exception ex) {
                CD.showDialog(MainActivity.this, "Unable to get the Location.");
            }
        } else {
            scanData.setLatitude("0");
            scanData.setLongitude("0");
        }

        return scanData;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.d("Message", "COULD NOT GET A GOOD RESULT.");
            if (data == null) {
                return;
            } else {
                String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                Log.e("Rsult", result);
                if (result != null) {
                    CD.showDialog(MainActivity.this, "QR Code could not be scanned!");
                }
            }
        }


        if (requestCode == REQUEST_CODE_QR_SCAN) {

            if (data == null) {
                return;
            } else {
                //Getting the passed result
                String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Log.d(LOGTAG, "Have scan result in your app activity :" + result);
                // CD.showDialog(MainActivity.this, result);
                try {
                    ScanDataPojo scanData = JsonParse.getObjectSave(result);
                    scanData = updateScanData(scanData);
                    Log.e("UserLocation", userLocation);
                    Log.e("scanDate", scanData.toString());
                    CD.showDialogScanData(MainActivity.this, scanData);
                    // uploadDataToServer(scanData);

                } catch (JSONException | ParseException e) {
                    CD.showDialog(MainActivity.this, result);
                    e.printStackTrace();
                }


            }
        }

        if (requestCode == REQUEST_CODE_QR_SCAN_OUT) {

            if (data == null) {
                return;
            } else {
                //Getting the passed result
                String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Log.d(LOGTAG, "Have scan result in your app activity :" + result);
                // CD.showDialog(MainActivity.this, result);
                try {
                    ScanDataPojo scanData = JsonParse.getObjectSave(result);
                    scanData = updateScanData(scanData);
                    Log.e("UserLocation", userLocation);
                    Log.e("scanDate", scanData.toString());
                    CD.showDialogScanDataOut(MainActivity.this, scanData);
                    // uploadDataToServer(scanData);

                } catch (JSONException | ParseException e) {
                    CD.showDialog(MainActivity.this, result);
                    e.printStackTrace();
                }


            }
        }


    }


    private ScanDataPojo updateScanData(ScanDataPojo scanData) {


        scanData.setScannedByPhoneNumber(Preferences.getInstance().phone_number);
        scanData.setDistict(Preferences.getInstance().district_id);
        scanData.setBarrrier(Preferences.getInstance().barrier_id);

        if (!userLocation.isEmpty()) {
            try {
                String[] locations = userLocation.split(",");
                scanData.setLatitude(locations[0]);
                scanData.setLongitude(locations[1]);
            } catch (Exception ex) {
                CD.showDialog(MainActivity.this, "Unable to get the Location.");
            }
        } else {
            scanData.setLatitude("0");
            scanData.setLongitude("0");
        }

        return scanData;
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
        registerReceiver(mReceiver, new IntentFilter("UploadServer"));
        registerReceiver(mReceiver, new IntentFilter("UploadServerOut"));
        registerReceiver(mReceiver, new IntentFilter("verifyData"));
        registerReceiver(mReceiver, new IntentFilter("UploadServerManual"));
        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
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
        Log.e("Location GPS", text);
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
        if (taskType == TaskType.UPLOAD_SCANNED_PASS) {

            if (result.getResponse().isEmpty()) {
                CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
            } else {

                try {
                    SuccessResponseDocuments response = JsonParse.getSuccessResponseDocuments(result.getResponse());

                    if (response.getStatus().equalsIgnoreCase("200")) {
                        Log.e("verify", response.toString());
                        //TODO
                        CD.showDialogHTML(MainActivity.this, response.getResponse(), response.getMessage(),
                                response.getPass_file(), response.getCovid_test_file(), response.getOther_file());
                    } else {
                        CD.showDialog(MainActivity.this, response.getMessage());
                    }
                } catch (Exception ex) {
                    CD.showDialog(MainActivity.this, result.getResponse());
                }

            }


        }
        //UPLOAD_SCANNED_PASS_OUT
        else if (taskType == TaskType.UPLOAD_SCANNED_PASS_OUT) {

            if (result.getResponse().isEmpty()) {
                CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
            } else {

                try {
                    SuccessResponseDocuments response = JsonParse.getSuccessResponseDocuments(result.getResponse());

                    if (response.getStatus().equalsIgnoreCase("200")) {
                        Log.e("verify", response.toString());
                        //TODO
                        CD.showDialogHTML(MainActivity.this, response.getResponse(), response.getMessage(),
                                response.getPass_file(), response.getCovid_test_file(), response.getOther_file());
                    } else {
                        CD.showDialog(MainActivity.this, response.getMessage());
                    }
                } catch (Exception ex) {
                    CD.showDialog(MainActivity.this, result.getResponse());
                }

            }


        }


        else if (taskType == TaskType.VERIFY_DETAILS) {

            Log.e("We are Heter", "Result" + result.toString());
            if (result.getResponse().isEmpty()) {
                //Toast.makeText(MainActivity.this, "Data Stored Locally", Toast.LENGTH_SHORT).show();
                CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
            } else {
                //TODO PArse Json Response
                // Toast.makeText(MainActivity.this, "Data Stored Locally", Toast.LENGTH_SHORT).show();
                SuccessResponse response = JsonParse.getSuccessResponse(result.getResponse());
                Log.e("Status", response.getStatus());
                if (response.getStatus().equalsIgnoreCase("200")) {
                    CD.showDialog(MainActivity.this, response.getResponse());
                } else {
                    CD.showDialog(MainActivity.this, response.getMessage());
                }

            }


        }
    }



}
