package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 06 , 2020
 */
public class AddMorePeoplePojo implements Serializable {

    private String vehical_number;
    private String number_of_persons;
    private String enter_name;
    private String mobile_number;

    private String from_state;
    private String from_district;
    private String from_place;

    private String district;
    private String tehsil;
    private String block_town;
    private String gp_ward;
    private String place;

    private String pass_number;
    private String autority;
    private String purpose;
    private String remarks;
    private String app_downloaded;

    private String category;
    private String subCategory;

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVehical_number() {
        return vehical_number;
    }

    public void setVehical_number(String vehical_number) {
        this.vehical_number = vehical_number;
    }

    public String getNumber_of_persons() {
        return number_of_persons;
    }

    public void setNumber_of_persons(String number_of_persons) {
        this.number_of_persons = number_of_persons;
    }

    public String getEnter_name() {
        return enter_name;
    }

    public void setEnter_name(String enter_name) {
        this.enter_name = enter_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getFrom_state() {
        return from_state;
    }

    public void setFrom_state(String from_state) {
        this.from_state = from_state;
    }

    public String getFrom_district() {
        return from_district;
    }

    public void setFrom_district(String from_district) {
        this.from_district = from_district;
    }

    public String getFrom_place() {
        return from_place;
    }

    public void setFrom_place(String from_place) {
        this.from_place = from_place;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getBlock_town() {
        return block_town;
    }

    public void setBlock_town(String block_town) {
        this.block_town = block_town;
    }

    public String getGp_ward() {
        return gp_ward;
    }

    public void setGp_ward(String gp_ward) {
        this.gp_ward = gp_ward;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPass_number() {
        return pass_number;
    }

    public void setPass_number(String pass_number) {
        this.pass_number = pass_number;
    }

    public String getAutority() {
        return autority;
    }

    public void setAutority(String autority) {
        this.autority = autority;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApp_downloaded() {
        return app_downloaded;
    }

    public void setApp_downloaded(String app_downloaded) {
        this.app_downloaded = app_downloaded;
    }


    @Override
    public String toString() {
        return "AddMorePeoplePojo{" +
                "vehical_number='" + vehical_number + '\'' +
                ", number_of_persons='" + number_of_persons + '\'' +
                ", enter_name='" + enter_name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", from_state='" + from_state + '\'' +
                ", from_district='" + from_district + '\'' +
                ", from_place='" + from_place + '\'' +
                ", district='" + district + '\'' +
                ", tehsil='" + tehsil + '\'' +
                ", block_town='" + block_town + '\'' +
                ", gp_ward='" + gp_ward + '\'' +
                ", place='" + place + '\'' +
                ", pass_number='" + pass_number + '\'' +
                ", autority='" + autority + '\'' +
                ", purpose='" + purpose + '\'' +
                ", remarks='" + remarks + '\'' +
                ", app_downloaded='" + app_downloaded + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                '}';
    }
}
