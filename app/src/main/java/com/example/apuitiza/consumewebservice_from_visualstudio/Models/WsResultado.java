
package com.example.apuitiza.consumewebservice_from_visualstudio.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WsResultado {

    @SerializedName("Exception")
    @Expose
    private String exception;
    @SerializedName("WasSuccessful")
    @Expose
    private Integer wasSuccessful;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Integer getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(Integer wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

}
