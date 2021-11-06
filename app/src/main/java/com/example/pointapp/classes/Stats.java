package com.example.pointapp.classes;

import com.google.gson.annotations.SerializedName;

public class Stats {
    @SerializedName("path")
    private float path;
    @SerializedName("count")
    private int count;

    public Stats(int path, int count) {
        this.path = path;
        this.count = count;
    }

    public float getPath() {
        return path;
    }

    public void setPath(float path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
