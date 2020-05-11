package com.doi.himachal.json;

import android.util.Log;

import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.SuccessResponse;
import com.doi.himachal.Modal.VerifyObject;
import com.doi.himachal.utilities.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class JsonParse {

    public static ScanDataPojo getObjectSave(String qrcodeData) throws JSONException {

        JSONObject responseObject = new JSONObject(qrcodeData);
        ScanDataPojo data = new ScanDataPojo();
        data.setPassNo(responseObject.getString("pass"));
        data.setMobileNumbr(responseObject.getString("mobile"));
        data.setPrsonNo(responseObject.optString("person"));
        data.setDateIssueDate(responseObject.getString("date"));
        data.setScanDate(CommonUtils.getCurrentDate());

        return data;
    }

    public static SuccessResponse getSuccessResponse(String data) throws JSONException {

        Object json = new JSONTokener(data).nextValue();
        if (json instanceof JSONObject){
            Log.e("true","Is Json Object");
        } else {
            Log.e("false","Not Json");
        }

        Log.e("IS Valid Json", String.valueOf(isJSONValid(data)));

        JSONObject responseObject = new JSONObject(data);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(responseObject.getString("STATUS"));
        Log.e("!!!!!Status",sr.getStatus());
        sr.setMessage(responseObject.getString("MSG"));
        sr.setResponse(responseObject.getString("RESPONSE"));
        Log.e("############SR",sr.toString());
        return sr;
    }

    public static VerifyObject createVerifyMessage(String data) throws JSONException {

        JSONObject responseObject = new JSONObject(data);
        VerifyObject sr = new VerifyObject();
        sr.setId(responseObject.getString("id"));
        sr.setPass_id(responseObject.getString("pass_id"));

        return sr;
    }

    //createJson
    public static String createJson(VerifyObject data) throws JSONException {

        JSONObject object = new JSONObject();
        object.put("id",data.getId());
        object.put("pass_id",data.getPass_id());
        object.put("remarks",data.getRemarks());



        return object.toString();
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            Log.e("Delay",ex.getLocalizedMessage());
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
