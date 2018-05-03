package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.CustomerRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ApiUtils;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ResultadoService mResultadoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.apuitiza.consumewebservice_from_visualstudio.R.layout.activity_main);

        final RecyclerView customerRecycler = findViewById(R.id.recycle);
        mResultadoService = ApiUtils.getAPIService();
        Call<List<Customers>> customersData = mResultadoService.getListCustomer();
        customersData.enqueue(new Callback<List<Customers>>() {
            @Override
            public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                if(response.body()!= null){
                    List<Customers> lista_customers= response.body();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    customerRecycler.setLayoutManager(linearLayoutManager);

                    CustomerRecycleradapter recyclerAdapter = new CustomerRecycleradapter(lista_customers,MainActivity.this);
                    customerRecycler.setAdapter(recyclerAdapter);
                }else{
                    Toast.makeText(getApplicationContext(),"La información no se ppuede mostrar",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No esta disponible la información, problema con la red",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
