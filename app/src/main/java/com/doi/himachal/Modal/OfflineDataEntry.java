package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 05 , 2020
 */
public class OfflineDataEntry implements Serializable {

    private String names;
    private String no_of_persons;
    private String vehicle_number;
    private String mobile;
    private String address;
    private String state_from;
    private String place_form;
    private String district_to;
    private String tehsil_to;
    private String block_to;
    private String gram_panchayat;
    private String place_to;
    private String pass_no;
    private String pass_issue_authority;
    private String purpose;
    private String aaroyga_app_download;
    private String barrier_id;
    private String distict_barrer_id;
    private String user_mobile;
    private String latitude;
    private String longitude;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNo_of_persons() {
        return no_of_persons;
    }

    public void setNo_of_persons(String no_of_persons) {
        this.no_of_persons = no_of_persons;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState_from() {
        return state_from;
    }

    public void setState_from(String state_from) {
        this.state_from = state_from;
    }

    public String getPlace_form() {
        return place_form;
    }

    public void setPlace_form(String place_form) {
        this.place_form = place_form;
    }

    public String getDistrict_to() {
        return district_to;
    }

    public void setDistrict_to(String district_to) {
        this.district_to = district_to;
    }

    public String getTehsil_to() {
        return tehsil_to;
    }

    public void setTehsil_to(String tehsil_to) {
        this.tehsil_to = tehsil_to;
    }

    public String getBlock_to() {
        return block_to;
    }

    public void setBlock_to(String block_to) {
        this.block_to = block_to;
    }

    public String getGram_panchayat() {
        return gram_panchayat;
    }

    public void setGram_panchayat(String gram_panchayat) {
        this.gram_panchayat = gram_panchayat;
    }

    public String getPlace_to() {
        return place_to;
    }

    public void setPlace_to(String place_to) {
        this.place_to = place_to;
    }

    public String getPass_no() {
        return pass_no;
    }

    public void setPass_no(String pass_no) {
        this.pass_no = pass_no;
    }

    public String getPass_issue_authority() {
        return pass_issue_authority;
    }

    public void setPass_issue_authority(String pass_issue_authority) {
        this.pass_issue_authority = pass_issue_authority;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAaroyga_app_download() {
        return aaroyga_app_download;
    }

    public void setAaroyga_app_download(String aaroyga_app_download) {
        this.aaroyga_app_download = aaroyga_app_download;
    }

    public String getBarrier_id() {
        return barrier_id;
    }

    public void setBarrier_id(String barrier_id) {
        this.barrier_id = barrier_id;
    }

    public String getDistict_barrer_id() {
        return distict_barrer_id;
    }

    public void setDistict_barrer_id(String distict_barrer_id) {
        this.distict_barrer_id = distict_barrer_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "OfflineDataEntry{" +
                "names='" + names + '\'' +
                ", no_of_persons='" + no_of_persons + '\'' +
                ", vehicle_number='" + vehicle_number + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", state_from='" + state_from + '\'' +
                ", place_form='" + place_form + '\'' +
                ", district_to='" + district_to + '\'' +
                ", tehsil_to='" + tehsil_to + '\'' +
                ", block_to='" + block_to + '\'' +
                ", gram_panchayat='" + gram_panchayat + '\'' +
                ", place_to='" + place_to + '\'' +
                ", pass_no='" + pass_no + '\'' +
                ", pass_issue_authority='" + pass_issue_authority + '\'' +
                ", purpose='" + purpose + '\'' +
                ", aaroyga_app_download='" + aaroyga_app_download + '\'' +
                ", barrier_id='" + barrier_id + '\'' +
                ", distict_barrer_id='" + distict_barrer_id + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
