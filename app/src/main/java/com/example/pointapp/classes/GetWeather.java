package com.example.pointapp.classes;

import com.google.gson.annotations.SerializedName;

public class GetWeather {
    @SerializedName("id")
    private String id;
    @SerializedName("temp")
    private String temp;

    public GetWeather(String id, String temp) {
        this.id = id;
        this.temp = temp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
