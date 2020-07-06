package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 06, 07 , 2020
 */
public class MastersPojoServer implements Serializable {
    private String status;
    private String records;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "MastersPojoServer{" +
                "status='" + status + '\'' +
                ", records='" + records + '\'' +
                '}';
    }
}
