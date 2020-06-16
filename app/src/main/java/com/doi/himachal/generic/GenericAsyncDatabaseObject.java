package com.doi.himachal.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.interfaces.AsyncTaskListenerDatabase;
import com.doi.himachal.interfaces.AsyncTaskListenerObject;
import com.doi.himachal.network.HttpManager;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class GenericAsyncDatabaseObject extends AsyncTask<String,Void , List<?>> {

    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerDatabase taskListener_;
    TaskType taskType;
    private ProgressDialog mProgressDialog;

    public GenericAsyncDatabaseObject(Context context, AsyncTaskListenerDatabase taskListener, TaskType taskType){
        this.context = context;
        this.taskListener_ = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected List<?> doInBackground(String... params) {
        String data = null;
        List<?> resultset = null ;
        data = params[1];
        String taskType_ = params[0];
        DatabaseHandler DB = new DatabaseHandler(context);
        if(taskType_.equalsIgnoreCase(TaskType.GET_DISTRICT_VIA_STATE.toString())){
            resultset = new ArrayList<>();
            resultset = DB.getDistrictsViaState(params[1]);
        }
        else if(taskType_.equalsIgnoreCase(TaskType.GET_TEHSIL_VIA_DISTRICT.toString())){
            resultset = new ArrayList<>();
            resultset = DB.getTehsilViaDistrict(params[1]);
        }
        else if(taskType_.equalsIgnoreCase(TaskType.GET_BLOCK_VIA_DISTRICT.toString())){
            resultset = new ArrayList<>();
            resultset = DB.getBlocksViaDistrict(params[1]);
        }else if(taskType_.equalsIgnoreCase(TaskType.GET_GP_VIA_DISTRICT.toString())){
            resultset = new ArrayList<>();
            resultset = DB.getGPViaDistrict(params[1]);
        }






        return resultset;
    }

    @Override
    protected void onPostExecute(List<?> dataset) {
        super.onPostExecute(dataset);

        try {
            taskListener_.onTaskCompleted(dataset, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }


}
