package com.example.apuitiza.consumewebservice_from_visualstudio.Services;

import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Order;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.WsResultado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ResultadoService {

    @GET("GetData/{word}")
    Call<Resultado> getResultado(@Path("word")String word);

    @GET("getAllCustomers")
    Call<List<Customers>> getListCustomer();

    @GET("getOrdersForCustomer/{customerID}")
    Call<List<Order>> getordersForCustomer (@Path("customerID")String customerID);

    @GET("deleteCustomer/{customerID}")
    Call<WsResultado> deleteCustomer (@Path("customerID")String customerID);

    @POST("updateOrderAddress")
    Call<Integer> updateOrderAddress(@Body Order order);

    @POST("createCustomer")
    Call<WsResultado> createCustomer(@Body Customers customers);
}
