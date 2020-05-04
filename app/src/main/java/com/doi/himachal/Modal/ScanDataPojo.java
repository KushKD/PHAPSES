package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class ScanDataPojo implements Serializable {

    private String passNo;
    private String mobileNumbr;
    private String prsonNo;
    private String dateIssueDate;


    private String scanDate;
    private String scannedByPhoneNumber;
    private String latitude;
    private String longitude;
    private String distict;
    private String barrrier;
    private boolean uploaddToServeer;

    private String number_of_passengers_manual;

    public String getNumber_of_passengers_manual() {
        return number_of_passengers_manual;
    }

    public void setNumber_of_passengers_manual(String number_of_passengers_manual) {
        this.number_of_passengers_manual = number_of_passengers_manual;
    }

    public String getPassNo() {
        return passNo;
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo;
    }

    public String getMobileNumbr() {
        return mobileNumbr;
    }

    public void setMobileNumbr(String mobileNumbr) {
        this.mobileNumbr = mobileNumbr;
    }

    public String getPrsonNo() {
        return prsonNo;
    }

    public void setPrsonNo(String prsonNo) {
        this.prsonNo = prsonNo;
    }

    public String getDateIssueDate() {
        return dateIssueDate;
    }

    public void setDateIssueDate(String dateIssueDate) {
        this.dateIssueDate = dateIssueDate;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getScannedByPhoneNumber() {
        return scannedByPhoneNumber;
    }

    public void setScannedByPhoneNumber(String scannedByPhoneNumber) {
        this.scannedByPhoneNumber = scannedByPhoneNumber;
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

    public String getDistict() {
        return distict;
    }

    public void setDistict(String distict) {
        this.distict = distict;
    }

    public String getBarrrier() {
        return barrrier;
    }

    public void setBarrrier(String barrrier) {
        this.barrrier = barrrier;
    }

    public boolean isUploaddToServeer() {
        return uploaddToServeer;
    }

    public void setUploaddToServeer(boolean uploaddToServeer) {
        this.uploaddToServeer = uploaddToServeer;
    }

    @Override
    public String toString() {
        return "ScanDataPojo{" +
                "passNo='" + passNo + '\'' +
                ", mobileNumbr='" + mobileNumbr + '\'' +
                ", prsonNo='" + prsonNo + '\'' +
                ", dateIssueDate='" + dateIssueDate + '\'' +
                ", scanDate='" + scanDate + '\'' +
                ", scannedByPhoneNumber='" + scannedByPhoneNumber + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", distict='" + distict + '\'' +
                ", barrrier='" + barrrier + '\'' +
                ", uploaddToServeer=" + uploaddToServeer +
                ", number_of_passengers_manual='" + number_of_passengers_manual + '\'' +
                '}';
    }
}
