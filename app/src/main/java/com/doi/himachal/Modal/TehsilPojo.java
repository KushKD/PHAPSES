package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 05 , 2020
 */
public class TehsilPojo implements Serializable {

    private String tehsil_id;
    private String tehsil_name;
    private String district_id;


    public String getTehsil_id() {
        return tehsil_id;
    }

    public void setTehsil_id(String tehsil_id) {
        this.tehsil_id = tehsil_id;
    }

    public String getTehsil_name() {
        return tehsil_name;
    }

    public void setTehsil_name(String tehsil_name) {
        this.tehsil_name = tehsil_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    @Override
    public String toString() {
        return tehsil_name;
    }
}
