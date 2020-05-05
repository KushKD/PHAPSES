package com.doi.himachal.Modal;

import com.doi.himachal.enums.TaskType;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class UploadObjectManual implements Serializable {

    private String url;
    private OfflineDataEntry offlineDataEntry;
    private TaskType tasktype;
    private String methordName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OfflineDataEntry getOfflineDataEntry() {
        return offlineDataEntry;
    }

    public void setOfflineDataEntry(OfflineDataEntry offlineDataEntry) {
        this.offlineDataEntry = offlineDataEntry;
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
        return "UploadObjectManual{" +
                "url='" + url + '\'' +
                ", offlineDataEntry=" + offlineDataEntry +
                ", tasktype=" + tasktype +
                ", methordName='" + methordName + '\'' +
                '}';
    }
}
