package com.example.pointapp.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting {

    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("address")
    private String address;
    @SerializedName("longitude")
    private Float longitude;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("time")
    private String time;
    @SerializedName("phone")
    private String number;

    public Meeting(String name, String surname, String address, Float longitude, Float latitude, String time, String number) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}