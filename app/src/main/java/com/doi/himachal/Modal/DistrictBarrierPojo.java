package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class DistrictBarrierPojo implements Serializable {

    private String barrier_id;
    private String barrier_district_id;
    private String barrir_name;


    public String getBarrier_id() {
        return barrier_id;
    }

    public void setBarrier_id(String barrier_id) {
        this.barrier_id = barrier_id;
    }

    public String getBarrier_district_id() {
        return barrier_district_id;
    }

    public void setBarrier_district_id(String barrier_district_id) {
        this.barrier_district_id = barrier_district_id;
    }

    public String getBarrir_name() {
        return barrir_name;
    }

    public void setBarrir_name(String barrir_name) {
        this.barrir_name = barrir_name;
    }

    @Override
    public String toString() {
        return barrir_name;
    }
}
