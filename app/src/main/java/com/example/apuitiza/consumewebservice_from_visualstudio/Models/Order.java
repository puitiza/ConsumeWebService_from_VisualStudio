
package com.example.apuitiza.consumewebservice_from_visualstudio.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("ShipAddress")
    @Expose
    private String shipAddress;
    @SerializedName("ShipCity")
    @Expose
    private String shipCity;
    @SerializedName("ShipName")
    @Expose
    private String shipName;
    @SerializedName("ShipPostcode")
    @Expose
    private String shipPostcode;
    @SerializedName("ShippedDate")
    @Expose
    private String shippedDate;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipPostcode() {
        return shipPostcode;
    }

    public void setShipPostcode(String shipPostcode) {
        this.shipPostcode = shipPostcode;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

}
