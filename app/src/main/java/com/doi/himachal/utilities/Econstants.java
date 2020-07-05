package com.doi.himachal.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ResponsePojoGet;
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

    public static ResponsePojo createOfflineObject(String url, String requestParams, String response) {
        ResponsePojo pojo = new ResponsePojo();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);





        return pojo;
    }

    public static ResponsePojoGet createOfflineObject(String url, String requestParams, String response, String Code, String functionName) {
        ResponsePojoGet pojo = new ResponsePojoGet();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);
        pojo.setFunctionName(functionName);
        pojo.setResponseCode(Code);

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


    public static final String HTML =  "<table style=\" font-family: 'Arial Black', Gadget, sans-serif;width: 100%; text-align: center;\" >\n" +
            "<thead style=\" background: #1C6EA4;  background: -moz-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%); background: -webkit-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);  background: linear-gradient(to bottom, #5592bb 0%, #327cad 66%, #1C6EA4 100%);\">\n" +
            "<tr>\n" +
            "<th style= \"border: 1px solid #AAAAAA; padding: 3px 2px;  font-size: 14px; font-weight: bold; color: #FFFFFF; height\"></th>\n" +
            "<th style= \"border: 1px solid #AAAAAA; padding: 3px 2px;  font-size: 14px; font-weight: bold; color: #FFFFFF;\"></th>\n" +
            "</tr>\n" +
            "</thead>\n" +
            "\n" +
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
            "<tr>\n" +
            "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
            "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">key</td>\n" +
            "<td style= \"border: 1px solid #AAAAAA; padding: 3px 2px; font-size: 14px; \">value</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "\n" +
            "\n" +
            "\n";


    /**
     * @param  activity
     * @return application version
     * Getting the Application Version of the App for Reference
     */
    public static String getVersion(Activity activity) {
        String version = "";
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            version = pInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return version;
        }
    }

  //  public static final String URL_HTTPS = "https://covid19epass.hp.gov.in/api/v1/";
    public static final String URL_HTTPS = "http://covid19epass.hp.gov.in/api/v1/";

    //getcategory

}
