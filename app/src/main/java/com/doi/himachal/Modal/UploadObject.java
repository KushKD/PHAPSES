package com.doi.himachal.Modal;

import com.doi.himachal.enums.TaskType;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class UploadObject implements Serializable {

    private String url;
    private ScanDataPojo scanDataPojo;
    private TaskType tasktype;
    private String methordName;
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ScanDataPojo getScanDataPojo() {
        return scanDataPojo;
    }

    public void setScanDataPojo(ScanDataPojo scanDataPojo) {
        this.scanDataPojo = scanDataPojo;
    }

    public TaskType getTasktype() {
        return tasktype;
    }

    public void setTasktype(TaskType tasktype) {
        this.tasktype = tasktype;
    }

    public String getMethordName() {
        return methordName;
    }

    public void setMethordName(String methordName) {
        this.methordName = methordName;
    }

    @Override
    public String toString() {
        return "UploadObject{" +
                "url='" + url + '\'' +
                ", scanDataPojo=" + scanDataPojo +
                ", tasktype=" + tasktype +
                ", methordName='" + methordName + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
