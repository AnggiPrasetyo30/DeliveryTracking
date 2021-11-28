package com.example.monitorbudimas.Model.loading;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responseStart {
    @SerializedName("kode")
    @Expose
    private int kode;
    @SerializedName("pesan")
    @Expose
    private String pesan;

    public int getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }
}
