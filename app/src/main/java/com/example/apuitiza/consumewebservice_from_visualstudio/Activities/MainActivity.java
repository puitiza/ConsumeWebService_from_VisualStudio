package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.CustomerRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ErrorEvent;
import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ResultadoEvents;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoServiceProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG = ResultadoServiceProvider.class.getSimpleName();


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultadoEvent(ResultadoEvents event) {
        String dataRequest = event.getResultado().getGetDataResult();
//        txtResult.setText(dataRequest);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent event) {
//        txtResult.setText(null);
        Toast.makeText(this,event.getTxtError(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.apuitiza.consumewebservice_from_visualstudio.R.layout.activity_main);

        final RecyclerView customerRecycler = findViewById(R.id.recycle);

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://192.168.8.11:15021/Service1.svc/")
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
                    EventBus.getDefault().post(new ErrorEvent("No hay informacion del Resultado disponible"));
                }
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                Log.e(TAG,"No esta disponible la información");
                EventBus.getDefault().post(new ErrorEvent("Problema de Internet , No hay informacion del resultado disponible"));

            }
        });
    }

    private void requestResultado(String word) {
        ResultadoServiceProvider result = new ResultadoServiceProvider();
        result.getResultado(word);
    }

}
