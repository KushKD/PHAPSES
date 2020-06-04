package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 05 , 2020
 */
public class GramPanchayatPojo implements Serializable {

    private String gp_id;
    private String gp_name;
    private String block_id;
    private String is_active;
    private String is_deleted;
    private String panchayat_pradhan_number;


    public String getGp_id() {
        return gp_id;
    }

    public void setGp_id(String gp_id) {
        this.gp_id = gp_id;
    }

    public String getGp_name() {
        return gp_name;
    }

    public void setGp_name(String gp_name) {
        this.gp_name = gp_name;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getPanchayat_pradhan_number() {
        return panchayat_pradhan_number;
    }

    public void setPanchayat_pradhan_number(String panchayat_pradhan_number) {
        this.panchayat_pradhan_number = panchayat_pradhan_number;
    }

    @Override
    public String toString() {
        return gp_name;
    }
}
