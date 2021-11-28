package com.example.monitorbudimas.Model.loading;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responseUstart {
    public start getData() {
        return data;
    }

    @SerializedName("data")
    @Expose
    private start data;
}
