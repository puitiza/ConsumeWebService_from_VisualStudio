
package com.example.apuitiza.consumewebservice_from_visualstudio.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resultado {

    @SerializedName("GetDataResult")
    @Expose
    private String getDataResult;

    public String getGetDataResult() {
        return getDataResult;
    }

    public void setGetDataResult(String getDataResult) {
        this.getDataResult = getDataResult;
    }

}
