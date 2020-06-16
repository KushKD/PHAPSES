package com.doi.himachal.interfaces;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.enums.TaskType;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerDatabase {
    void onTaskCompleted(List<?> data , TaskType taskType) throws JSONException;
}
