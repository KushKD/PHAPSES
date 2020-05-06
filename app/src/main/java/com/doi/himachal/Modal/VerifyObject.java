package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 07, 05 , 2020
 */
public class VerifyObject implements Serializable {

    private String pass_id;
    private String id;
    private String remarks;


    public String getPass_id() {
        return pass_id;
    }

    public void setPass_id(String pass_id) {
        this.pass_id = pass_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "VerifyObject{" +
                "pass_id='" + pass_id + '\'' +
                ", id='" + id + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
