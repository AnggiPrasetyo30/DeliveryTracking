package com.example.monitorbudimas.Model.user;

import com.google.gson.annotations.SerializedName;

public class login {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    @SerializedName("username")
    private String username;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("posisi")
    private String posisi;

    public String getStatus() {
        return status;
    }

    @SerializedName("status")
    private String status;
}