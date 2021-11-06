package com.example.pointapp.classes;

import com.google.gson.annotations.SerializedName;

public class ActPass {
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private String status;

    public ActPass(String token, String status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
