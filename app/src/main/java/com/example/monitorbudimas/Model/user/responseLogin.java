package com.example.monitorbudimas.Model.user;


import com.google.gson.annotations.SerializedName;

public class responseLogin {

    public login getResponseLogin() {
        return responseLogin;
    }

    public void setResponseLogin(login responseLogin) {
        this.responseLogin = responseLogin;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @SerializedName("data")
    private login responseLogin;

    @SerializedName("pesan")
    private String pesan;

    @SerializedName("status")
    private boolean status;
}