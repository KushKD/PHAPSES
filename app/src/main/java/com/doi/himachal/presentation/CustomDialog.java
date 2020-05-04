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
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.epasshp.MainActivity;
import com.doi.himachal.epasshp.R;
import com.doi.himachal.utilities.CommonUtils;
import com.doi.himachal.utilities.DateTime;
import com.doi.himachal.utilities.Econstants;

import java.text.ParseException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class CustomDialog {
    int downloadIdOne;


    public void showDialog(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
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

    public void showDialogHTML(final Activity activity, String msg, final String msgServer) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_web);

        String html = "<table style=\" font-family: 'Arial Black', Gadget, sans-serif;width: 100%; text-align: center;\" >\n" +
                "<thead style=\" background: #1C6EA4;  background: -moz-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%); background: -webkit-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);  background: linear-gradient(to bottom, #5592bb 0%, #327cad 66%, #1C6EA4 100%);\">\n" +
                "<tr>\n" +
                "<th style= \"border: 1px solid #AAAAAA; padding: 3px 2px;  font-size: 14px; font-weight: bold; color: #FFFFFF; height\"></th>\n" +
                "<th style= \"border: 1px solid #AAAAAA; padding: 3px 2px;  font-size: 14px; font-weight: bold; color: #FFFFFF;\"></th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
                "<td style= \" border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
                "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>";

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels);
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
        Button dialog_verified = (Button)dialog.findViewById(R.id.dialog_verified);

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
                // activity.finish();

                Intent intent = new Intent("verifyData");
                intent.setPackage(activity.getPackageName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("VERIFY_DATA", msgServer);
                intent.putExtras(bundle);
                activity.sendBroadcast(intent);
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

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TextView name = (TextView)dialog.findViewById(R.id.name);
        TextView passnumber =  (TextView)dialog.findViewById(R.id.passnumber);
        TextView vehiclenumber =  (TextView)dialog.findViewById(R.id.vehiclenumber);
        TextView mobile =  (TextView)dialog.findViewById(R.id.mobile);
        TextView dateissue =  (TextView)dialog.findViewById(R.id.dateissue);
       TextView district  =  (TextView)dialog.findViewById(R.id.district);
        TextView barrier =  (TextView)dialog.findViewById(R.id.barrier);
        TextView datescan =  (TextView)dialog.findViewById(R.id.datescan);
        TextView timescan =  (TextView)dialog.findViewById(R.id.timescan);
     final   EditText number_of_passengers =  (EditText) dialog.findViewById(R.id.number_of_passengers);




       // Log.e("====Manual Entry", scanData.getNumber_of_passengers_manual());
        DatabaseHandler DB = new DatabaseHandler(activity);


        // name.setText(taskPojo.getTask_name());
        passnumber.setText(scanData.getPassNo());
        vehiclenumber.setText("-");
        mobile.setText(scanData.getMobileNumbr());
        dateissue.setText(scanData.getDateIssueDate());
        district.setText(DB.getDistrictNameById(scanData.getDistict()));
        barrier.setText(DB.getBarrierNameById(scanData.getDistict(),scanData.getBarrrier()));
        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));
        Log.e("ScanDae",DateTime.Change_Date_Format_second(scanData.getScanDate()));
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

                if(!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null ){

                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
                    System.out.println("====Manual Entry" +scanData.getNumber_of_passengers_manual());
                }else{
                    scanData.setNumber_of_passengers_manual("0");
                    System.out.println("====Manual Entry" +scanData.getNumber_of_passengers_manual());
                }
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
//
//    public void showDialog_insideGridView(final Activity activity, final String dept_id) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_inside_grid);
//
//
//
//
//        LinearLayout district = (LinearLayout) dialog.findViewById(R.id.district);
//        LinearLayout state = (LinearLayout) dialog.findViewById(R.id.state);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        district.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //district
//                Intent details = new Intent(activity, NodalOfficerList.class);
//                details.putExtra("department_id", dept_id);
//                details.putExtra("user_type", "District");
//                activity.startActivity(details);
//                dialog.dismiss();
//            }
//        });
//
//        state.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //state
//                Intent details = new Intent(activity, NodalOfficerList.class);
//                details.putExtra("department_id", dept_id);
//                details.putExtra("user_type", "State");
//                activity.startActivity(details);
//                dialog.dismiss();
//            }
//        });
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    //PDF
//    @SuppressLint("NewApi")
//    public void showDialogDownloadPDF(final Activity activity, String pdf_url, final String pdf_name) {
//
//        Log.e("!@!@!@!URl",pdf_url);
//        Log.e("!@!@!@!@Name",pdf_name);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(pdf_name);
//        text.setTypeface(Econstants.getTypefaceBold(activity));
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, pdf_name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+pdf_name);
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    @SuppressLint("NewApi")
//    public void showDialogDownloadPDFWithName(final Activity activity, String pdf_url, final String pdf_name , final String name) {
//
//        Log.e("!@!@!@!URl",pdf_url);
//        Log.e("!@!@!@!@Name",pdf_name);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(name);
//        text.setTypeface(Econstants.getTypefaceBold(activity));
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, pdf_name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+pdf_name);
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//
//    @SuppressLint("NewApi")
//    public void showDialogDownloadImageWithName(final Activity activity, String url, final String name , final String display_name) {
//
//        Log.e("!@!@!@!URl",url);
//        Log.e("!@!@!@!@Name",name);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(display_name);
//        text.setTypeface(Econstants.getTypefaceBold(activity));
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(url, HardwareDetails.getRootDirPath(activity), name)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+name);
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "image/*");
//                            // target.setFlags(Intent.FLAG_H);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//



//    @SuppressLint("NewApi")
//    public void showDialogDownloadVideo(final Activity activity, String video_url, final String video_name) {
//
//        Log.e("!@!@!@!URl",video_url);
//        Log.e("!@!@!@!@Name",video_name);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(video_name);
//        text.setTypeface(Econstants.getTypefaceBold(activity));
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(video_url, HardwareDetails.getRootDirPath(activity), video_name)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, video_name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+video_name);
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "video/*");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            Intent intent = Intent.createChooser(target, "Open Video");
//                            try {
//                                Log.e("Video Player ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a VLC here, or something
//                                Log.e("Video Player ","Not Installed");
//                                Toast.makeText(activity, "Download Video Player", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//    @SuppressLint("NewApi")
//    public void showDialogDownloadPDFWithoutAsOnDate(final Activity activity, String pdf_url, final String pdf_name) {
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(pdf_name);
//        text.setTypeface(Econstants.getTypefaceBold(activity));
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name+".pdf")
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, pdf_name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+pdf_name+".pdf");
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//
//
//    public void showDialog_CloseActivity(final Activity activity, String msg) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom);
//
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
//        text.setText(msg);
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                activity.finish();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    //showDialog_NotCloseActivity
//    public void showDialog_NotCloseActivity(final Activity activity, String msg) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom);
//
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
//        text.setText(msg);
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//
//
//
//
//
//    public void showDialog_moredetails(final Activity activity, final MoreDetailsPoJo pojo) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_moredetails);
//
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView contact_person_name = (TextView) dialog.findViewById(R.id.contact_person_name);
//        TextView contact_person_email = (TextView) dialog.findViewById(R.id.contact_person_email);
//        TextView contact_person_phone = (TextView) dialog.findViewById(R.id.contact_person_phone);
//
//        contact_person_name.setText(pojo.getName_of_representative());
//        contact_person_email.setText(pojo.getEmail());
//        contact_person_phone.setText(pojo.getPhone_number());
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        contact_person_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getPhone_number().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Phone number not available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent i = new Intent(Intent.ACTION_DIAL);
//                    String p = "tel:" + pojo.getPhone_number().toString().trim();
//                    i.setData(Uri.parse(p));
//                    activity.startActivity(i);
//                }
//            }
//        });
//
//        contact_person_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getEmail().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Emial not Available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("plain/text");
//                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { pojo.getEmail() });
//                    //intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                    //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
//                    activity.startActivity(Intent.createChooser(intent, ""));
//                }
//            }
//        });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialog_NodalOfficerDetails(final Activity activity, final NodalOfficerPojo pojo) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_moredetails);
//
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView contact_person_name = (TextView) dialog.findViewById(R.id.contact_person_name);
//        TextView contact_person_email = (TextView) dialog.findViewById(R.id.contact_person_email);
//        TextView contact_person_phone = (TextView) dialog.findViewById(R.id.contact_person_phone);
//
//        contact_person_name.setText(pojo.getFull_name());
//        contact_person_email.setText(pojo.getEmail());
//        contact_person_phone.setText(pojo.getMobile());
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        contact_person_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getMobile().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Phone number not available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent i = new Intent(Intent.ACTION_DIAL);
//                    String p = "tel:" + pojo.getMobile().toString().trim();
//                    i.setData(Uri.parse(p));
//                    activity.startActivity(i);
//                }
//            }
//        });
//
//        contact_person_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getEmail().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Emial not Available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("plain/text");
//                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { pojo.getEmail() });
//                    //intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                    //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
//                    activity.startActivity(Intent.createChooser(intent, ""));
//                }
//            }
//        });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }

//    public void showDialog_StakeHolders(final Activity activity, final StakeHoldersPojo pojo) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_moredetails);
//
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView contact_person_name = (TextView) dialog.findViewById(R.id.contact_person_name);
//        TextView contact_person_email = (TextView) dialog.findViewById(R.id.contact_person_email);
//        TextView contact_person_phone = (TextView) dialog.findViewById(R.id.contact_person_phone);
//        TextView partner_name = (TextView)dialog.findViewById(R.id.partner_name);
//        TextView stakeholder_nature = (TextView)dialog.findViewById(R.id.stakeholder_nature);
//
//        contact_person_name.setText(pojo.getUser_name());
//        contact_person_email.setText(pojo.getEmail_id());
//        contact_person_phone.setText(pojo.getContact_number());
//        partner_name.setText(pojo.getPartner_name());
//        stakeholder_nature.setText(pojo.getStakeholder_nature());
//
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        contact_person_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getContact_number().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Phone number not available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent i = new Intent(Intent.ACTION_DIAL);
//                    String p = "tel:" + pojo.getContact_number().toString().trim();
//                    i.setData(Uri.parse(p));
//                    activity.startActivity(i);
//                }
//            }
//        });
//
//        contact_person_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getEmail_id().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Emial not Available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("plain/text");
//                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { pojo.getEmail_id() });
//                    //intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                    //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
//                    activity.startActivity(Intent.createChooser(intent, ""));
//                }
//            }
//        });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialog_Acts(final Activity activity, final ActsPojo pojo) {
//        //final Dialog dialog = new Dialog(new ContextThemeWrapper(activity, R.style.DialogSlideAnim));
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_acts_policies);
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        LinearLayout brief = (LinearLayout) dialog.findViewById(R.id.brief);
//        brief.setVisibility(View.GONE);
//        LinearLayout hindi = (LinearLayout) dialog.findViewById(R.id.hindi);
//        hindi.setVisibility(View.GONE);
//        LinearLayout english = (LinearLayout) dialog.findViewById(R.id.english);
//        english.setVisibility(View.GONE);
//        Button brief_button = (Button) dialog.findViewById(R.id.brief_button);
//        Button english_button = (Button) dialog.findViewById(R.id.english_button);
//        Button hindi_button = (Button) dialog.findViewById(R.id.hindi_button);
//        TextView name = (TextView)dialog.findViewById(R.id.name);
//        name.setText(pojo.getAct_name_english());
//        name.setTypeface(Econstants.getTypefaceRegular(activity));
//
//        Log.e("brief",pojo.getAct_path_brief());
//        Log.e("english",pojo.getAct_path_english());
//        Log.e("hindi",pojo.getAct_path_hindi());
//        //name
//
//        if(!pojo.getAct_path_brief().isEmpty() ){
//            brief.setVisibility(View.VISIBLE);
//        }
//        if(!pojo.getAct_path_english().isEmpty()){
//            english.setVisibility(View.VISIBLE);
//        }
//        if(!pojo.getAct_path_hindi().isEmpty()){
//            hindi.setVisibility(View.VISIBLE);
//        }
//
//
//        if(AppStatus.getInstance(activity).isOnline()){
//            brief_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //TODO AS ON DATE CHECK
//                    showDialogDownloadPDFWithoutAsOnDate(activity,pojo.getAct_path_brief(),pojo.getAct_name_english());
//
//                }
//            });
//
//            english_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO AS ON DATE CHECK
//                    showDialogDownloadPDFWithoutAsOnDate(activity,pojo.getAct_path_english(),pojo.getAct_name_english());
//
//                }
//            });
//
//
//
//            hindi_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO AS ON DATE CHECK
//                    showDialogDownloadPDFWithoutAsOnDate(activity,pojo.getAct_path_hindi(),pojo.getAct_name_english());
//
//                }
//            });
//        }else{
//            Toast.makeText(activity, Econstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
//        }
//
//
//
//
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialog_Comments(final Activity activity, final TransactionsPojo pojo) {
//        //final Dialog dialog = new Dialog(new ContextThemeWrapper(activity, R.style.DialogSlideAnim));
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_comments);
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        LinearLayout brief = (LinearLayout) dialog.findViewById(R.id.brief);
//
//
//        if(pojo.getUploaded_file().equalsIgnoreCase("-")){
//            brief.setVisibility(View.GONE);
//        }else{
//            brief.setVisibility(View.VISIBLE);
//        }
//
//        Button download = (Button) dialog.findViewById(R.id.download_file);
//        TextView filename = (TextView)dialog.findViewById(R.id.file_name);
//        TextView comments = (TextView)dialog.findViewById(R.id.comments);
//        TextView comments_by = (TextView)dialog.findViewById(R.id.comments_by);
//
//        //   filename.setText(pojo.getComment()+"."+extension);
//        // name.setTypeface(Econstants.getTypefaceRegular(activity));
//
//        comments.setText(pojo.getComment());
//        comments_by.setText(pojo.getUploaded_by_name()+"\t"+ Econstants.formattedDateFromString("","",pojo.getCreated()));
//
//
//        //name
//
//
//
//
//        if(AppStatus.getInstance(activity).isOnline()){
//            download.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //TODO AS ON DATE CHECK
//                    //Check the Extention of File
//                    String uri = pojo.getUploaded_file();
//                    String extension = uri.substring(uri.lastIndexOf("."));
//                    Log.e("extension",extension);
//
//                    if(extension.equalsIgnoreCase(".jpg") ||
//                            extension.equalsIgnoreCase(".jpeg" )||
//                            extension.equalsIgnoreCase(".png" )){
//                        showDialogDownloadImageWithName(activity,pojo.getUploaded_file(),pojo.getComment()+extension, pojo.getComment());
//
//
//
//                    }
//                    else if(extension.equalsIgnoreCase(".pdf")){
//                        showDialogDownloadPDFWithName(activity,pojo.getUploaded_file(),pojo.getComment()+extension, pojo.getComment());
//                    }
////                  else  if(extension.equalsIgnoreCase("doc") ||
////                            extension.equalsIgnoreCase("docm" )||
////                            extension.equalsIgnoreCase("docx" )){
////
////                    }
//                    else{
//                        showDialog(activity,"Unsupported Format");
//                    }
//
//                    //  showDialogDownloadPDFWithoutAsOnDate(activity,pojo.getAct_path_brief(),pojo.getAct_name_english());
//
//                }
//            });
//
//
//        }else{
//            Toast.makeText(activity, Econstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
//        }
//
//
//
//
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//
//
//
//
//    //Dialog Company contact List
//    public void showDialog_CompanyContactList(final Activity activity, final CompanyContactListPojo pojo) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_moredetails);
//
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.50);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView contact_person_name = (TextView) dialog.findViewById(R.id.contact_person_name);
//        TextView contact_person_email = (TextView) dialog.findViewById(R.id.contact_person_email);
//        TextView contact_person_phone = (TextView) dialog.findViewById(R.id.contact_person_phone);
//        TextView partner_name = (TextView)dialog.findViewById(R.id.partner_name);
//        TextView stakeholder_nature = (TextView)dialog.findViewById(R.id.stakeholder_nature);
//
//        contact_person_name.setText(pojo.getName());
//        contact_person_email.setText(pojo.getEmail());
//        contact_person_phone.setText(pojo.getMobile_no_1());
//        // partner_name.setText(pojo.getPartner_name());
//        stakeholder_nature.setText(pojo.getDesignation());
//
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        contact_person_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getMobile_no_1().toString().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Phone number not available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent i = new Intent(Intent.ACTION_DIAL);
//                    String p = "tel:" + pojo.getMobile_no_1().toString().trim();
//                    i.setData(Uri.parse(p));
//                    activity.startActivity(i);
//                }
//            }
//        });
//
//        contact_person_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pojo.getEmail().trim().equalsIgnoreCase("")) {
//                    Toast.makeText(activity, "Emial not Available", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("plain/text");
//                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { pojo.getEmail() });
//                    //intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                    //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
//                    activity.startActivity(Intent.createChooser(intent, ""));
//                }
//            }
//        });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
}
