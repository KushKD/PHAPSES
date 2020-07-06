package com.doi.himachal.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.doi.himachal.Modal.ResponsePojoGet;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectGet;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectPost;
import com.doi.himachal.network.HttpManager;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 07 , 2020
 */
public class Generic_Async_Post extends AsyncTask<UploadObject,Void , ResponsePojoGet> {


    String outputStr;
    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObjectPost taskListener;
    TaskType taskType;

    public Generic_Async_Post(Context context, AsyncTaskListenerObjectPost taskListener, TaskType taskType) {
        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected ResponsePojoGet doInBackground(UploadObject... uploadObjects) {
        ResponsePojoGet Data_From_Server = null;
        HttpManager http_manager = null;
        try {
            http_manager = new HttpManager();
          //  if(uploadObjects[0].getTasktype().toString().equalsIgnoreCase(TaskType.GET_DISTRICT_VIA_STATE.toString())){
            //    Log.e("We Here", uploadObjects[0].getMethordName());
                Data_From_Server = http_manager.PostDataParamsGeneric(uploadObjects[0]);
                return Data_From_Server;
            //}


        } catch (Exception e) {
            Log.e("Value Saved",e.getLocalizedMessage().toString());
        }
        return Data_From_Server;

    }

    @Override
    protected void onPostExecute(ResponsePojoGet result) {
        super.onPostExecute(result);
        try {
            taskListener.onTaskCompleted(result, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
}