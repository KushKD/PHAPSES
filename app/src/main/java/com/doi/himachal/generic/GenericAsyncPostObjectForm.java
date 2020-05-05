package com.doi.himachal.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.doi.himachal.Modal.OfflineDataEntry;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.Modal.UploadObjectManual;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.interfaces.AsyncTaskListenerObject;
import com.doi.himachal.interfaces.AsyncTaskListenerObjectForm;
import com.doi.himachal.network.HttpManager;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class GenericAsyncPostObjectForm extends AsyncTask<UploadObjectManual,Void , ResponsePojo> {

    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObjectForm taskListener_;
    TaskType taskType;
    private ProgressDialog mProgressDialog;

    public GenericAsyncPostObjectForm(Context context, AsyncTaskListenerObjectForm taskListener, TaskType taskType){
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
    protected ResponsePojo doInBackground(UploadObjectManual... offlineDataEntry) {
        UploadObjectManual data = null;
        data = offlineDataEntry[0];
        HttpManager http_manager = null;
        ResponsePojo Data_From_Server = null;
        boolean save = false;

        try{
            http_manager = new HttpManager();

            if(TaskType.MANUAL_FORM_UPLOAD.toString().equalsIgnoreCase(data.getTasktype().toString())){
                Log.e("Here","We Are");
                Data_From_Server = http_manager.PostData(data);
                Log.e("Data hhghsds",Data_From_Server.toString());

                return Data_From_Server;
            }




        }catch(Exception e){
            return Data_From_Server;
        }


        return Data_From_Server;
    }

    @Override
    protected void onPostExecute(ResponsePojo result) {
        super.onPostExecute(result);

        try {
            taskListener_.onTaskCompleted(result, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }
}
