package com.doi.himachal.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ScanDataPojo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class Econstants {

    public static final String Date_Format = "dd-MM-yyyy";

    public static final String loadJSONFromAsset(Activity activity, String jsonName){
        String json = null;
        try {
            InputStream is = activity.getAssets().open(jsonName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ResponsePojo createOfflineObject(String url, String requestParams, String response, ScanDataPojo scanDataPojo) {
        ResponsePojo pojo = new ResponsePojo();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);

        pojo.setPassNo(scanDataPojo.getPassNo());
        pojo.setPrsonNo(scanDataPojo.getPrsonNo());
        pojo.setMobileNumbr(scanDataPojo.getMobileNumbr());
        pojo.setLatitude(scanDataPojo.getLatitude());
        pojo.setLongitude(scanDataPojo.getLongitude());
        pojo.setDistict(scanDataPojo.getDistict());
        pojo.setBarrrier(scanDataPojo.getBarrrier());
        pojo.setDateIssueDate(scanDataPojo.getDateIssueDate());
        pojo.setScanDate(scanDataPojo.getScanDate());
        pojo.setUploaddToServeer(scanDataPojo.isUploaddToServeer());
        pojo.setScannedByPhoneNumber(scanDataPojo.getScannedByPhoneNumber());
        pojo.setManual_persons(scanDataPojo.getNumber_of_passengers_manual());



        return pojo;
    }

    public static ResponsePojo createOfflinePaam(String url, String requestParams, String response, String params) {
        ResponsePojo pojo = new ResponsePojo();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);
        pojo.setParams(params);





        return pojo;
    }



    public static String generateAuthenticationPasswrd(String username, String password)
            throws UnsupportedEncodingException {
        StringBuilder SB = new StringBuilder();
        SB.append(username + ":" + password);
        System.out.println(Base64.encodeToString(SB.toString().getBytes(),Base64.DEFAULT));

        return Base64.encodeToString(SB.toString().getBytes(),Base64.DEFAULT);
      //  return Base64.getEncoder().encodeToString(SB.toString().getBytes("utf-8"));
    }
}
