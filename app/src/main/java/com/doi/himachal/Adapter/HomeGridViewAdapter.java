package com.doi.himachal.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.doi.himachal.Modal.ModulesPojo;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.epasshp.History;
import com.doi.himachal.epasshp.ManualEntry;
import com.doi.himachal.epasshp.R;
import com.doi.himachal.epasshp.Registration;
import com.doi.himachal.lazyloader.ImageLoader;
import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.Preferences;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class HomeGridViewAdapter extends BaseAdapter {
    Context c;
    ArrayList<ModulesPojo> gridHome;



    ImageLoader il = new ImageLoader(c);
    CustomDialog CD = new CustomDialog();


    public HomeGridViewAdapter(Context c, ArrayList<ModulesPojo> spacecrafts) {
        this.c = c;
        this.gridHome = spacecrafts;
    }

    @Override
    public int getCount() {
        return gridHome.size();
    }

    @Override
    public Object getItem(int i) {
        return gridHome.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.home_gridview_model, viewGroup, false);
        }

        final ModulesPojo s = (ModulesPojo) this.getItem(i);

        ImageView img = (ImageView) view.findViewById(R.id.spacecraftImg);
        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);


        nameTxt.setText(s.getName());


        if (s.getLogo().equalsIgnoreCase("null")) {
            //show uk icon
            String fnm = "uttarakhand";
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        } else {
            String fnm = s.getLogo();
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s.getId().equalsIgnoreCase("1")) {
                    Intent i = new Intent(c.getApplicationContext(), QrCodeActivity.class);
                    ((Activity) c).startActivityForResult(i, 101);

                }
//                if (s.getId().equalsIgnoreCase("")) {
//
//                    DatabaseHandler DB = new DatabaseHandler(c);
//                    CD.showDialog((Activity) c, Integer.toString(DB.getNoOfRowsCount()));
//
//                }
                if (s.getId().equalsIgnoreCase("2")) {
                    try {
                        CD.showDialogSearchByPassId((Activity) c);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (s.getId().equalsIgnoreCase("3")) {

                    Intent i = new Intent(c.getApplicationContext(), ManualEntry.class);

                    (c).startActivity(i);

                }

                if (s.getId().equalsIgnoreCase("4")) {
                    Intent i = new Intent(c.getApplicationContext(), QrCodeActivity.class);
                    ((Activity) c).startActivityForResult(i, 103);

                }
                if (s.getId().equalsIgnoreCase("5")) {
                    Preferences.getInstance().loadPreferences(c.getApplicationContext());


                    Preferences.getInstance().district_id = "";
                    Preferences.getInstance().district_name = "";
                    Preferences.getInstance().barrier_name = "";
                    Preferences.getInstance().barrier_id = "";
                    Preferences.getInstance().phone_number = "";
                    Preferences.getInstance().isLoggedIn = false;


                    Preferences.getInstance().savePreferences(c.getApplicationContext());
                    Toast.makeText(c.getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(c.getApplicationContext(), Registration.class);
                    (c).startActivity(mainIntent);
                    ((Activity) c).finish();


                }


            }
        });

        return view;
    }





}
