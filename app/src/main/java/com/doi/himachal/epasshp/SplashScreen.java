package com.doi.himachal.epasshp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.doi.himachal.presentation.CustomDialog;
import com.doi.himachal.utilities.Preferences;

public class SplashScreen extends AppCompatActivity {

    CustomDialog CD = new CustomDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        requestPermissions();

        Preferences.getInstance().loadPreferences(SplashScreen.this);
        if(Preferences.getInstance().isLoggedIn){
            System.out.println("Loed In "+Preferences.getInstance().isLoggedIn);

        }else{
            loadPrefrence();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Preferences.getInstance().isLoggedIn){
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }else{
                    Intent mainIntent = new Intent(SplashScreen.this, Registration.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }



            }
        }, 5000);
    }

    private void loadPrefrence() {
        Preferences.getInstance().loadPreferences(SplashScreen.this);
        Preferences.getInstance().district_id = "";
        Preferences.getInstance().barrier_id = "";
        Preferences.getInstance().phone_number = "";
        Preferences.getInstance().isLoggedIn = false;
        Preferences.getInstance().savePreferences(SplashScreen.this);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.CAMERA


                }, 0);
            }
        }


    }
}
