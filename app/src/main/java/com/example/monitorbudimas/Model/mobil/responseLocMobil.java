package com.example.monitorbudimas.Model.mobil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responseLocMobil {


    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getLongitude() {
        return longitude;
    }

    public String getPesan() {
        return pesan;
    }

    public String getLatitude() {
        return latitude;
    }

    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("latitude")
    @Expose
    private String latitude;

}
