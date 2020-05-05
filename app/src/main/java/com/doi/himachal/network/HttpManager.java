package com.doi.himachal.network;

import android.util.Log;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.Modal.UploadObjectManual;
import com.doi.himachal.utilities.Econstants;
import com.doi.himachal.utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class HttpManager {

    public ResponsePojo PostData(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONStringer userJson = null;

        String URL = null;
        ResponsePojo response = null;
        JSONObject object =  new JSONObject();


        try {

            URL = data.getUrl();


            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn_.setRequestProperty("Authorization", "Basic " + Econstants.generateAuthenticationPasswrd("COVID@908#HeM@nT", "JovpQy2Cez6545sKDRvhX3p"));
            conn_.connect();


            // "mobile_information": "optional",
            // "other_information": "optional"

            object.put("logged_in_name", Preferences.getInstance().name);
            object.put("department_name", Preferences.getInstance().dept_name);
            object.put("scanned_by", data.getScanDataPojo().getScannedByPhoneNumber());


            userJson = new JSONStringer()
                    .object()
                    .key("barrier_district").value(data.getScanDataPojo().getDistict())
                    .key("barrier_id").value(data.getScanDataPojo().getBarrrier())
                    .key("latitude").value(data.getScanDataPojo().getLatitude())
                    .key("longititute").value(data.getScanDataPojo().getLongitude())
                    .key("pass_id").value(data.getScanDataPojo().getPassNo())
                    .key("scanned_date_time").value(data.getScanDataPojo().getScanDate())
                    .key("mobile_information").value(object.toString())
                    .key("other_information").value(data.getScanDataPojo().getNumber_of_passengers_manual())


                    .endObject();


            System.out.println(userJson.toString());
            Log.e("Object", userJson.toString());
            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
            out.write(userJson.toString());
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    Log.e("Error", conn_.getResponseMessage());
                    data.getScanDataPojo().setUploaddToServeer(false);
                    response = new ResponsePojo();
                    response = Econstants.createOfflineObject(URL, userJson.toString(), conn_.getResponseMessage() + conn_.getResponseCode(), data.getScanDataPojo());
                    return response;


                } else {
                    data.getScanDataPojo().setUploaddToServeer(true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojo();
                    response = Econstants.createOfflineObject(URL, userJson.toString(), sb.toString(), data.getScanDataPojo());

                }

            } catch (Exception e) {
                data.getScanDataPojo().setUploaddToServeer(false);
                response = new ResponsePojo();
                response = Econstants.createOfflineObject(URL, userJson.toString(), conn_.getResponseMessage() + conn_.getResponseCode(), data.getScanDataPojo());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }


    public ResponsePojo PostData(UploadObjectManual data) {
        Log.e("Here","We Are 2");
        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONObject userJson = null;

        String URL = null;
        ResponsePojo response = null;


        try {

            URL = data.getUrl();


            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn_.setRequestProperty("Authorization", "Basic " + Econstants.generateAuthenticationPasswrd("COVID@908#HeM@nT", "JovpQy2Cez6545sKDRvhX3p"));
            conn_.connect();
            Log.e("Here","We Are3");

            if (data.getOfflineDataEntry().getAaroyga_app_download().equalsIgnoreCase("Yes")) {
                data.getOfflineDataEntry().setAaroyga_app_download("Y");
            } else {
                data.getOfflineDataEntry().setAaroyga_app_download("N");
            }

            Log.e("Here","We Are4");

            try{
                userJson = new JSONObject();
                Log.e("Here","We Are5");
                userJson.put("pass_id",Integer.parseInt(data.getOfflineDataEntry().getPass_no()));
                userJson.put("no_of_person",Integer.parseInt(data.getOfflineDataEntry().getNo_of_persons()));
                userJson.put("persons_name",data.getOfflineDataEntry().getNames());
                userJson.put("vehicle_no",data.getOfflineDataEntry().getVehicle_number());
                Log.e("Here","We Are6");
               userJson.put("mobile_number",data.getOfflineDataEntry().getMobile());
                userJson.put("address",data.getOfflineDataEntry().getAddress());
                Log.e("Here","We Are7");
               userJson.put("state_from",Integer.parseInt(data.getOfflineDataEntry().getState_from()));
               userJson.put("district_to",Integer.parseInt(data.getOfflineDataEntry().getDistrict_to()));
                userJson.put("tehsil_to",Integer.parseInt(data.getOfflineDataEntry().getTehsil_to()));
                userJson.put("block_to",Integer.parseInt(data.getOfflineDataEntry().getBlock_to()));
                userJson.put("panchyat_to",Integer.parseInt(data.getOfflineDataEntry().getGram_panchayat()));
                userJson.put("pass_issuing_authority",data.getOfflineDataEntry().getPass_issue_authority());
                userJson.put("purpose",data.getOfflineDataEntry().getPurpose());
                userJson.put("aarogya_setu_app",data.getOfflineDataEntry().getAaroyga_app_download());
               userJson.put("barrier_district",Integer.parseInt(data.getOfflineDataEntry().getDistict_barrer_id()));
                userJson.put("barrier_id",Integer.parseInt(data.getOfflineDataEntry().getBarrier_id()));
                userJson.put("longititute",data.getOfflineDataEntry().getLongitude());
                userJson.put("latitude",data.getOfflineDataEntry().getLatitude());
                userJson.put("mobile_information",data.getOfflineDataEntry().getUser_mobile());
                userJson.put("scanned_date_time",data.getOfflineDataEntry().getTimeStamp());
                Log.e("Here","We Are8");

                Log.e("Here","We Are 5");
            }catch (JSONException ex){
                Log.e("Error",ex.getLocalizedMessage());
            }

            System.out.println(userJson.toString());
            Log.e("Object", userJson.toString());
            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
            out.write(userJson.toString());
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojo();
                    response = Econstants.createOfflineObject(URL, userJson.toString(), sb.toString());


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojo();
                    response = Econstants.createOfflineObject(URL, userJson.toString(), sb.toString());

                }

            } catch (Exception e) {
                response = new ResponsePojo();
                response = Econstants.createOfflineObject(URL, userJson.toString(), conn_.getResponseMessage() + conn_.getResponseCode());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Error",e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error",e.getLocalizedMessage());
        }  finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }


    public ResponsePojo PostDataParams(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONObject userJson = null;

        String URL = null;
        ResponsePojo response = null;


        try {

            URL = data.getUrl();
            Log.e("URL", URL.toString());


            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn_.setRequestProperty("Authorization", "Basic " + Econstants.generateAuthenticationPasswrd("COVID@908#HeM@nT", "JovpQy2Cez6545sKDRvhX3p"));
            conn_.connect();


            userJson = new JSONObject(data.getParam());


            System.out.println(userJson.toString());
            Log.e("Object", data.getParam());
            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
            out.write(data.getParam());
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    Log.e("Error", conn_.getResponseMessage());

                    response = new ResponsePojo();
                    response = Econstants.createOfflinePaam(URL, userJson.toString(), conn_.getResponseMessage() + conn_.getResponseCode(), data.getParam());
                    return response;


                } else {

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojo();
                    response = Econstants.createOfflinePaam(URL, userJson.toString(), sb.toString(), data.getParam());

                }

            } catch (Exception e) {

                response = new ResponsePojo();
                response = Econstants.createOfflinePaam(URL, data.getParam(), conn_.getResponseMessage() + conn_.getResponseCode(), data.getParam());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }
}
