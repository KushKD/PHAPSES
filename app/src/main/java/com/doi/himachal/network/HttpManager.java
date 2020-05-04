package com.doi.himachal.network;

import android.util.Log;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.utilities.Econstants;

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
            conn_.setRequestProperty("Authorization", "Basic "+Econstants.generateAuthenticationPasswrd("COVID@908#HeM@nT","JovpQy2Cez6545sKDRvhX3p"));
            conn_.connect();





                   // "mobile_information": "optional",
                   // "other_information": "optional"

            userJson = new JSONStringer()
                    .object()
                    .key("barrier_district").value(data.getScanDataPojo().getDistict())
                    .key("barrier_id").value(data.getScanDataPojo().getBarrrier())
                    .key("latitude").value(data.getScanDataPojo().getLatitude())
                    .key("longititute").value(data.getScanDataPojo().getLongitude())
                    .key("pass_id").value(data.getScanDataPojo().getPassNo())
                    .key("scanned_date_time").value(data.getScanDataPojo().getScanDate())
                    .key("mobile_information").value("")
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
                    response = Econstants.createOfflineObject(URL,userJson.toString(),conn_.getResponseMessage()+conn_.getResponseCode(),data.getScanDataPojo());
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
                    response = Econstants.createOfflineObject(URL,userJson.toString(),sb.toString(),data.getScanDataPojo());

                }

            } catch (Exception e) {
                data.getScanDataPojo().setUploaddToServeer(false);
                response = new ResponsePojo();
                response = Econstants.createOfflineObject(URL,userJson.toString(),conn_.getResponseMessage()+conn_.getResponseCode(),data.getScanDataPojo());
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

    public ResponsePojo PostDataParams(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONObject userJson = null;

        String URL = null;
        ResponsePojo response = null;


        try {

            URL = data.getUrl();
            Log.e("URL",URL.toString());


            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn_.setRequestProperty("Authorization", "Basic "+Econstants.generateAuthenticationPasswrd("COVID@908#HeM@nT","JovpQy2Cez6545sKDRvhX3p"));
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
                    response = Econstants.createOfflinePaam(URL,userJson.toString(),conn_.getResponseMessage()+conn_.getResponseCode(),data.getParam());
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
                    response = Econstants.createOfflinePaam(URL,userJson.toString(),sb.toString(),data.getParam());

                }

            } catch (Exception e) {

                response = new ResponsePojo();
                response = Econstants.createOfflinePaam(URL,data.getParam(),conn_.getResponseMessage()+conn_.getResponseCode(),data.getParam());
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
