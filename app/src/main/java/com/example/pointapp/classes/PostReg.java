package com.example.pointapp.classes;

import com.google.gson.annotations.SerializedName;

public class PostReg {

    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;

    public PostReg(String name, String surname) {
        this.name = name;
        this.surname = surname;
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
}
