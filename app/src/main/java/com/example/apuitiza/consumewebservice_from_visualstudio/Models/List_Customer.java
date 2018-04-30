
package com.example.apuitiza.consumewebservice_from_visualstudio.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List_Customer {
    //GetAllCustomersResult is el nombre de JSON
   /* public class Person {

        @SerializedName("name")
        private String personName;

        @SerializedName("bd")
        private String birthDate;

    }*/

    /*{
        "name":"chintan",
            "bd":"01-01-1990"
    }*/

    @SerializedName("GetAllCustomersResult")
    @Expose
    private List<Customers> list_Customers = null;

    public List<Customers> getList_Customers() {
        return list_Customers;
    }

    public void setList_Customers(List<Customers> list_Customers) {
        this.list_Customers = list_Customers;
    }
}
