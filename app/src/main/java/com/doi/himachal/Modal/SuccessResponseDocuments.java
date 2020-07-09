package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class SuccessResponseDocuments implements Serializable {

    private String status;
    private String message;  //pass_id,id
    private String response;
    private String pass_file;
    private String other_file;
    private String covid_test_file;


    public String getPass_file() {
        return pass_file;
    }

    public void setPass_file(String pass_file) {
        this.pass_file = pass_file;
    }

    public String getOther_file() {
        return other_file;
    }

    public void setOther_file(String other_file) {
        this.other_file = other_file;
    }

    public String getCovid_test_file() {
        return covid_test_file;
    }

    public void setCovid_test_file(String covid_test_file) {
        this.covid_test_file = covid_test_file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "SuccessResponseDocuments{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                ", pass_file='" + pass_file + '\'' +
                ", other_file='" + other_file + '\'' +
                ", covid_test_file='" + covid_test_file + '\'' +
                '}';
    }
}
