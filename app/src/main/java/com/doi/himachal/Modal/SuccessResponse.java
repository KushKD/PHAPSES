package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class SuccessResponse implements Serializable {

    private String status;
    private String message;  //pass_id,id
    private String response;



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
        return "SuccessResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
