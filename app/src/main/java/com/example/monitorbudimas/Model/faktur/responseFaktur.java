package com.example.monitorbudimas.Model.faktur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseFaktur {
    @SerializedName("kode")
    @Expose
    private int kode;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("data")
    @Expose
    private List<faktur> data;

    public int getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<faktur> getData()  {
        return data;
    }
}
