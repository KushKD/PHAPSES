package com.doi.himachal.Modal;


import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 06, 05 , 2020
 */
public class PhoneDetailsPojo implements Serializable {


    private String sreial;
    private String model;
    private String id;
    private String manufacturer;
    private String brand;
    private String sdk;
    private String version_code;


    public String getSreial() {
        return sreial;
    }

    public void setSreial(String sreial) {
        this.sreial = sreial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    @Override
    public String toString() {
        return "PhoneDetailsPojo{" +
                "sreial='" + sreial + '\'' +
                ", model='" + model + '\'' +
                ", id='" + id + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", brand='" + brand + '\'' +
                ", sdk='" + sdk + '\'' +
                ", version_code='" + version_code + '\'' +
                '}';
    }
}
