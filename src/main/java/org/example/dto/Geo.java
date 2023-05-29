package org.example.dto;

import com.google.gson.Gson;

public class Geo {
    private double lat;
    private double lng;

    public Geo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
