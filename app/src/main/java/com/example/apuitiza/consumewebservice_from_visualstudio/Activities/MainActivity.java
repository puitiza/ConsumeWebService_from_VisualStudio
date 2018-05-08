package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.CustomerRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.apuitiza.consumewebservice_from_visualstudio.R.layout.activity_main);

        final RecyclerView customerRecycler = findViewById(R.id.recycle);

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://192.168.8.23:15021/Service1.svc/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        ResultadoService service = retrofit.create(ResultadoService.class);
        Call<List<Customers>> customersData = service.getListCustomer();
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
                    Toast.makeText(getApplicationContext(),"No hay informacion del Resultado disponible",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Problema de Internet , No hay informacion del resultado disponible",Toast.LENGTH_LONG).show();
            }
        });
    }
}
