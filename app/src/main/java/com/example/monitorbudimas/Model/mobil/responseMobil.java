package com.example.monitorbudimas.Model.mobil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseMobil {

    @SerializedName("kode")
    @Expose
    private int kode;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("data")
    @Expose
    private List<mobil> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<mobil> getData() {
        return data;
    }

    public void setData(List<mobil> data) {
        this.data = data;
    }

}
