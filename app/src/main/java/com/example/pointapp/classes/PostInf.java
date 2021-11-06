package com.example.pointapp.classes;

import com.google.gson.annotations.SerializedName;

public class PostInf {
    @SerializedName("token")
    public String token;

    @SerializedName("time")
    public Integer time;

    public PostInf(String token, Integer time) {
        this.token = token;
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
