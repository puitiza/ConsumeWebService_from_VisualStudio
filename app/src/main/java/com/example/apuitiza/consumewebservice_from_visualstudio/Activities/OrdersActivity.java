package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.OrderRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Order;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ApiUtils;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersActivity extends AppCompatActivity {

    private ResultadoService mResultadoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        final RecyclerView orderRecycler = findViewById(R.id.recycleOrders);
        mResultadoService = ApiUtils.getAPIService();

        if(getIntent().hasExtra("id_customer")){
            String id_customer = getIntent().getStringExtra("id_customer");
            Call<List<Order>> orderData = mResultadoService.getordersForCustomer(id_customer);
            orderData.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    if(response.body()!= null){
                        List<Order> lista_orders= response.body();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        orderRecycler.setLayoutManager(linearLayoutManager);

                        OrderRecycleradapter recyclerAdapter = new OrderRecycleradapter(lista_orders,OrdersActivity.this);
                        orderRecycler.setAdapter(recyclerAdapter);
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {

                }
            });
        }

    }
}