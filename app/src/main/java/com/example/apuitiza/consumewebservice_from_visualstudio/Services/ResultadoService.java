package com.example.apuitiza.consumewebservice_from_visualstudio.Services;

import com.example.apuitiza.consumewebservice_from_visualstudio.Models.List_Customer;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultadoService {
    @GET("GetData/{word}")
    Call<Resultado> getResultado(@Path("word")String word);

    @GET(".")
    Call<List<List_Customer>> getListCustomer();

}
