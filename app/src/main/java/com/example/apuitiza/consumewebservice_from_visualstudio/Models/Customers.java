package com.example.apuitiza.consumewebservice_from_visualstudio.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customers {

    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("CustomerID")
    @Expose
    private String customerID;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
