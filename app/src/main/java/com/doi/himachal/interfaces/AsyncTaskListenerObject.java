package com.doi.himachal.interfaces;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.enums.TaskType;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObject {
    void onTaskCompleted(ResponsePojo result, TaskType taskType) throws JSONException;
}
