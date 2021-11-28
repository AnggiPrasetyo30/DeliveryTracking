package com.example.monitorbudimas.Model.faktur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class faktur {

    @SerializedName("id_faktur")
    @Expose
    private String idFaktur;

    @SerializedName("status")
    @Expose
    private String status;

    public String getIdFaktur() {
        return idFaktur;
    }

    public String getStatus() {
        return status;
    }
}
