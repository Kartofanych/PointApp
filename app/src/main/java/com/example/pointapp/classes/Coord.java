package com.example.pointapp.classes;

public class Coord {
    private float latitude;
    private float longtitude;

    public Coord(float longtitude, float latitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }
}
