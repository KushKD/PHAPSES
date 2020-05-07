package com.doi.himachal.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.VerifyObject;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.epasshp.MainActivity;
import com.doi.himachal.epasshp.R;
import com.doi.himachal.json.JsonParse;
import com.doi.himachal.utilities.CommonUtils;
import com.doi.himachal.utilities.DateTime;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;

import org.json.JSONException;

import java.text.ParseException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class CustomDialog {
    int downloadIdOne;


    public void showDialog(final Activity activity, String msg)  {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.50);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setText(msg);

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogCloseActivity(final Activity activity, String msg)  {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.50);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setText(msg);

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogHTML(final Activity activity, final String msg, final String msgServer) throws JSONException {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_web);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WebView webView = (WebView) dialog.findViewById(R.id.dialog_result);
        final EditText remarks = dialog.findViewById(R.id.remarks);
        webView.setVerticalScrollBarEnabled(true);
        webView.requestFocus();
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        //msg

        webView.loadDataWithBaseURL("", msg, mimeType, encoding, "");

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
        Button dialog_verified = (Button) dialog.findViewById(R.id.dialog_verified);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        dialog_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //TODO
                Log.e("Message",msgServer);
                //{"pass_id":51827,"id":587}
                String serverMessage = null;
                try{
                    VerifyObject objectVerify = JsonParse.createVerifyMessage(msgServer);
                    if(remarks.getText().toString()!=null || !remarks.getText().toString().isEmpty()){
                        objectVerify.setRemarks(remarks.getText().toString().trim());
                    }else{
                        objectVerify.setRemarks("");
                    }
                    //Create Object
                     serverMessage = JsonParse.createJson(objectVerify);
                    Log.e("Server ",serverMessage);
                }catch (JSONException ex){
                    serverMessage = msgServer;
                }


                Intent intent = new Intent("verifyData");
                intent.setPackage(activity.getPackageName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("VERIFY_DATA", serverMessage);
                intent.putExtras(bundle);
                activity.sendBroadcast(intent);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogHTMLGeneric(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_web_generic);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WebView webView = (WebView) dialog.findViewById(R.id.dialog_result);
        webView.setVerticalScrollBarEnabled(true);
        webView.requestFocus();
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";


        webView.loadDataWithBaseURL("", msg, mimeType, encoding, "");

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });


        dialog.show();

    }


    public void showDialogScanData(final Activity activity, final ScanDataPojo scanData) throws ParseException {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pojo_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TextView name = (TextView)dialog.findViewById(R.id.name);
        TextView passnumber = (TextView) dialog.findViewById(R.id.passnumber);
        TextView vehiclenumber = (TextView) dialog.findViewById(R.id.vehiclenumber);
        TextView mobile = (TextView) dialog.findViewById(R.id.mobile);
        TextView dateissue = (TextView) dialog.findViewById(R.id.dateissue);
        TextView district = (TextView) dialog.findViewById(R.id.district);
        TextView barrier = (TextView) dialog.findViewById(R.id.barrier);
        TextView datescan = (TextView) dialog.findViewById(R.id.datescan);
        TextView timescan = (TextView) dialog.findViewById(R.id.timescan);
        final EditText number_of_passengers = (EditText) dialog.findViewById(R.id.number_of_passengers);


        // Log.e("====Manual Entry", scanData.getNumber_of_passengers_manual());
        DatabaseHandler DB = new DatabaseHandler(activity);


        // name.setText(taskPojo.getTask_name());
        passnumber.setText(scanData.getPassNo());
        vehiclenumber.setText("-");
        mobile.setText(scanData.getMobileNumbr());
        dateissue.setText(scanData.getDateIssueDate());
        district.setText(DB.getDistrictNameById(scanData.getDistict()));
        barrier.setText(DB.getBarrierNameById(scanData.getDistict(), scanData.getBarrrier()));
        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));
        Log.e("ScanDae", DateTime.Change_Date_Format_second(scanData.getScanDate()));
        timescan.setText(DateTime.changeTime(scanData.getScanDate()));
//        task_completed_by.setText(taskPojo.getTask_completed_by_name());


        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button proceed = (Button) dialog.findViewById(R.id.proceed);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null) {

                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
                } else {
                    scanData.setNumber_of_passengers_manual("0");
                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
                }

                scanData.setVersionApp(Econstants.getVersion(activity));
                //Start ASYNC TASK TODO
                // prepare your parameters that need to be sent back to activity
                Intent intent = new Intent("UploadServer");
                intent.setPackage(activity.getPackageName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("SCAN_DATA", scanData);
                intent.putExtras(bundle);
                activity.sendBroadcast(intent);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogSearchByPassId(final Activity activity) throws ParseException {
        final ScanDataPojo scanData = new ScanDataPojo();
        scanData.setScannedByPhoneNumber(Preferences.getInstance().phone_number);
        scanData.setDistict(Preferences.getInstance().district_id);
        scanData.setBarrrier(Preferences.getInstance().barrier_id);
        scanData.setLatitude("0");
        scanData.setLongitude("0");
        scanData.setPassNo("-");
        scanData.setMobileNumbr("-");
        scanData.setPrsonNo("-");
        scanData.setDateIssueDate("-");
        scanData.setScanDate(CommonUtils.getCurrentDate());
        scanData.setVersionApp(Econstants.getVersion(activity));
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pojopassid_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TextView name = (TextView)dialog.findViewById(R.id.name);
        final EditText passnumber = (EditText) dialog.findViewById(R.id.passnumber);


        TextView district = (TextView) dialog.findViewById(R.id.district);
        TextView barrier = (TextView) dialog.findViewById(R.id.barrier);
        TextView datescan = (TextView) dialog.findViewById(R.id.datescan);
        TextView timescan = (TextView) dialog.findViewById(R.id.timescan);
        final EditText number_of_passengers = (EditText) dialog.findViewById(R.id.number_of_passengers);


        // Log.e("====Manual Entry", scanData.getNumber_of_passengers_manual());
        DatabaseHandler DB = new DatabaseHandler(activity);


        // name.setText(taskPojo.getTask_name());


        district.setText(DB.getDistrictNameById(scanData.getDistict()));
        barrier.setText(DB.getBarrierNameById(scanData.getDistict(), scanData.getBarrrier()));
        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));

        timescan.setText(DateTime.changeTime(scanData.getScanDate()));
//        task_completed_by.setText(taskPojo.getTask_completed_by_name());


        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button proceed = (Button) dialog.findViewById(R.id.proceed);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null) {

                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
                } else {
                    scanData.setNumber_of_passengers_manual("0");
                }


                if (!passnumber.getText().toString().isEmpty() && passnumber.getText().toString() != null) {

                    scanData.setPassNo(passnumber.getText().toString());
                    System.out.println("====Manual Entry" + scanData.getPassNo());
                    Intent intent = new Intent("UploadServerManual");
                    intent.setPackage(activity.getPackageName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SCAN_DATA", scanData);
                    intent.putExtras(bundle);
                    activity.sendBroadcast(intent);
                    dialog.dismiss();
                } else {
                    showDialog(activity, "Please Enter Pass Number");
                }


            }
        });

        dialog.show();

    }

}
