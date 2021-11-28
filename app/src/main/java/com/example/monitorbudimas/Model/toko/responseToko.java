package com.example.monitorbudimas.Model.toko;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseToko {
    @SerializedName("kode")
    @Expose
    private int kode;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("data")
    @Expose
    private List<toko> data;

    public int getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<toko> getData() {
        return data;
    }

}
