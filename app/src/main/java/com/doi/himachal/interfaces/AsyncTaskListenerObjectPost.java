package com.doi.himachal.interfaces;

import com.doi.himachal.Modal.ResponsePojoGet;
import com.doi.himachal.enums.TaskType;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObjectPost {
    void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException;
}