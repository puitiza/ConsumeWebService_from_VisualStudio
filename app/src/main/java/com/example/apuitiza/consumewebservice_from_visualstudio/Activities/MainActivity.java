package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ErrorEvent;
import com.example.apuitiza.consumewebservice_from_visualstudio.Events.ResultadoEvents;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoServiceProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtResult;
    private Button btnResult;
    private TextView txtResult;


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
        txtResult.setText(dataRequest);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent event) {
        txtResult.setText(null);
        Toast.makeText(this,event.getTxtError(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.apuitiza.consumewebservice_from_visualstudio.R.layout.activity_main);

        edtResult = findViewById(R.id.edtResult);
        btnResult = findViewById(R.id.btnResult);
        txtResult = findViewById(R.id.txtResult);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtResult.getText().toString())){
                    Toast.makeText(MainActivity.this,"Inserte un numero o una palabra",Toast.LENGTH_LONG).show();
                }else {
                    requestResultado(edtResult.getText().toString());
                }
            }
        });
//        requestResultado(String.valueOf(6));
    }

    private void requestResultado(String word) {
        ResultadoServiceProvider result = new ResultadoServiceProvider();
        result.getResultado(word);
    }

}
