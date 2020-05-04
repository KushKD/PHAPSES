package com.doi.himachal.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.ScanDataPojo;
import com.doi.himachal.Modal.UploadObject;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.enums.TaskType;
import com.doi.himachal.interfaces.AsyncTaskListenerObject;
import com.doi.himachal.network.HttpManager;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class GenericAsyncPostObject extends AsyncTask<UploadObject,Void , ResponsePojo> {

    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObject taskListener_;
    TaskType taskType;
    private ProgressDialog mProgressDialog;

    public GenericAsyncPostObject(Context context, AsyncTaskListenerObject taskListener, TaskType taskType){
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
    protected ResponsePojo doInBackground(UploadObject... uploadObjects) {
        UploadObject data = null;
        data = uploadObjects[0];
        HttpManager http_manager = null;
        ResponsePojo Data_From_Server = null;
        boolean save = false;

        try{
            http_manager = new HttpManager();

            if(TaskType.UPLOAD_SCANNED_PASS.toString().equalsIgnoreCase(data.getTasktype().toString())){
                Data_From_Server = http_manager.PostData(data);
                Log.e("Data hhghsds",Data_From_Server.toString());

                //Save Data to DB
                try{
                    DatabaseHandler DB = new DatabaseHandler(context);
                    save =  DB.addOfflineAccess(Data_From_Server);
                    if(save){
                        Log.e("Value Saved","Database");
                    }
                }catch (Exception ex){
                    Log.e("Value Saved",ex.getLocalizedMessage().toString());
                }



                return Data_From_Server;
            }
            else if(TaskType.VERIFY_DETAILS.toString().equalsIgnoreCase(data.getTasktype().toString())){
                Data_From_Server = http_manager.PostDataParams(data);
                Log.e("Verify Details",Data_From_Server.toString());

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
