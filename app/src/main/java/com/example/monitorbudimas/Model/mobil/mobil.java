package com.example.monitorbudimas.Model.mobil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class mobil implements Serializable {
    public String getUsername() {
        return username;
    }
    public String getSopir() {
        return sopir;
    }
    public String getUser_id() { return user_id; }

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("user_id")
    @Expose
    String user_id;

    @SerializedName("sopir")
    @Expose
    String sopir;
}
