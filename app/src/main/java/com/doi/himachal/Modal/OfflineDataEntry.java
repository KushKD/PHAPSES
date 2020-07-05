package com.doi.himachal.Modal;

import java.io.Serializable;
import java.util.List;

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
    private String district_from;
    private String place_form;
    private String district_to;
    private String tehsil_to;
    private String block_to;
    private String gram_panchayat;
    private  String CategoryId;
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
    private String remarks;
    private String versionCode;
    private String address_two_;
    private String address_three_;
    private String address_four_;
    private String address_other_;

    private int position_from_state;
    private int position_from_district;

    private int position_to_district;
    private int position_to_block;
    private int position_to_panchayat;
    private int position_to_tehsil;
    private int position_to_category;

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public int getPosition_to_category() {
        return position_to_category;
    }

    public void setPosition_to_category(int position_to_category) {
        this.position_to_category = position_to_category;
    }

    private List<AddMorePeoplePojo> otherPersons;

    public List<AddMorePeoplePojo> getOtherPersons() {
        return otherPersons;
    }

    public void setOtherPersons(List<AddMorePeoplePojo> otherPersons) {
        this.otherPersons = otherPersons;
    }

    public int getPosition_from_state() {
        return position_from_state;
    }

    public void setPosition_from_state(int position_from_state) {
        this.position_from_state = position_from_state;
    }

    public int getPosition_from_district() {
        return position_from_district;
    }

    public void setPosition_from_district(int position_from_district) {
        this.position_from_district = position_from_district;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDistrict_from() {
        return district_from;
    }

    public void setDistrict_from(String district_from) {
        this.district_from = district_from;
    }

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


    public String getAddress_two_() {
        return address_two_;
    }

    public void setAddress_two_(String address_two_) {
        this.address_two_ = address_two_;
    }

    public String getAddress_three_() {
        return address_three_;
    }

    public void setAddress_three_(String address_three_) {
        this.address_three_ = address_three_;
    }

    public String getAddress_four_() {
        return address_four_;
    }

    public void setAddress_four_(String address_four_) {
        this.address_four_ = address_four_;
    }

    public String getAddress_other_() {
        return address_other_;
    }

    public void setAddress_other_(String address_other_) {
        this.address_other_ = address_other_;
    }

    public int getPosition_to_district() {
        return position_to_district;
    }

    public void setPosition_to_district(int position_to_district) {
        this.position_to_district = position_to_district;
    }

    public int getPosition_to_block() {
        return position_to_block;
    }

    public void setPosition_to_block(int position_to_block) {
        this.position_to_block = position_to_block;
    }

    public int getPosition_to_panchayat() {
        return position_to_panchayat;
    }

    public void setPosition_to_panchayat(int position_to_panchayat) {
        this.position_to_panchayat = position_to_panchayat;
    }

    public int getPosition_to_tehsil() {
        return position_to_tehsil;
    }

    public void setPosition_to_tehsil(int position_to_tehsil) {
        this.position_to_tehsil = position_to_tehsil;
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
                ", district_from='" + district_from + '\'' +
                ", place_form='" + place_form + '\'' +
                ", district_to='" + district_to + '\'' +
                ", tehsil_to='" + tehsil_to + '\'' +
                ", block_to='" + block_to + '\'' +
                ", gram_panchayat='" + gram_panchayat + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
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
                ", remarks='" + remarks + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", address_two_='" + address_two_ + '\'' +
                ", address_three_='" + address_three_ + '\'' +
                ", address_four_='" + address_four_ + '\'' +
                ", address_other_='" + address_other_ + '\'' +
                ", position_from_state=" + position_from_state +
                ", position_from_district=" + position_from_district +
                ", position_to_district=" + position_to_district +
                ", position_to_block=" + position_to_block +
                ", position_to_panchayat=" + position_to_panchayat +
                ", position_to_tehsil=" + position_to_tehsil +
                ", position_to_category=" + position_to_category +
                ", otherPersons=" + otherPersons +
                '}';
    }
}
