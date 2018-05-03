package com.example.apuitiza.consumewebservice_from_visualstudio.Services;

import android.util.Log;

import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ErrorEvent;
import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ResultadoEvents;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadoServiceProvider {

    private static  final String TAG = ResultadoServiceProvider.class.getSimpleName();

    private Retrofit retrofit;


    private Retrofit getRetrofit(){
        if(this.retrofit == null){
            this.retrofit= new Retrofit.Builder()
                    //.baseUrl("http://192.168.43.176:15021/Service1.svc/")
                    .baseUrl("http://192.168.8.11:15021/Service1.svc/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    

    public void getResultado(String word){
        ResultadoService service = getRetrofit().create(ResultadoService.class);
        Call<Resultado> resultadoData = service.getResultado(word);
        resultadoData.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if(response.body()!= null){
                    Resultado results= response.body();
                    String result = response.body().getGetDataResult();
                    Log.e(TAG,"la respuesta es = "+result);
                    EventBus.getDefault().post(new ResultadoEvents(results));
                }else{
                    EventBus.getDefault().post(new ErrorEvent("No hay informacion del Resultado disponible"));
                }

            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                Log.e(TAG,"No esta disponible la información");
                EventBus.getDefault().post(new ErrorEvent("Problema de Internet , No hay informacion del resultado disponible"));
            }
        });
    }

}
