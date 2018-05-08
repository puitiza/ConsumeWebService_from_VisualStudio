package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.CustomerRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.OrderRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Order;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.WsResultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ApiUtils;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ResultadoService mResultadoService;
    //Esto es para floartingActionButton
    private FloatingActionButton fab;
    FrameLayout rootLayout;
    private CustomerRecycleradapter recyclerAdapter;
    private List<Customers> lista_customers;
    private RecyclerView customerRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.apuitiza.consumewebservice_from_visualstudio.R.layout.activity_main);
        fab = findViewById(R.id.fabAddCustomer);
        customerRecycler = findViewById(R.id.recycle);
        mResultadoService = ApiUtils.getAPIService();
        Call<List<Customers>> customersData = mResultadoService.getListCustomer();
        customersData.enqueue(new Callback<List<Customers>>() {
            @Override
            public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                if(response.body()!= null){
                    lista_customers = response.body();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    customerRecycler.setLayoutManager(linearLayoutManager);

                    recyclerAdapter = new CustomerRecycleradapter(lista_customers,MainActivity.this);
                    customerRecycler.setAdapter(recyclerAdapter);

                    rootLayout = findViewById(R.id.activity_main);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showAlertForCreateCustomer();
                        }
                    });
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

    private void showAlertForCreateCustomer() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Registrar Cliente");
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.layout_dialog_create_customer, null);
        dialog.setView(viewInflated);

        final MaterialEditText customerID = viewInflated.findViewById(R.id.edtCustomerID);
        final MaterialEditText companyName = viewInflated.findViewById(R.id.edtCompanyName);
        final MaterialEditText city = viewInflated.findViewById(R.id.edtCity);

        dialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int thisPosition) {
                String st_customerID = customerID.getText().toString().trim();
                String st_companyName = companyName.getText().toString().trim();
                String st_city = city.getText().toString().trim();

                if(st_customerID.length()>0 && st_companyName.length()>0 && st_city.length()>0){
                    CreateCustomer(st_customerID,st_companyName,st_city,thisPosition);
                }else{
                    Snackbar.make(rootLayout,"Ingrese un nombre de Tienda",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void CreateCustomer(String customerID, String companyName, String city, final int thisPosition) {
        final Customers customer = new Customers();
        customer.setCustomerID(customerID);
        customer.setCompanyName(companyName);
        customer.setCity(city);
        Call<WsResultado> wsResultadoData = mResultadoService.createCustomer(customer);

        wsResultadoData.enqueue(new Callback<WsResultado>() {
            @Override
            public void onResponse(Call<WsResultado> call, Response<WsResultado> response) {
                if(response.body()!= null){
                    WsResultado result= response.body();
                    if(result.getWasSuccessful() == 0){
                        Snackbar.make(rootLayout,"Fallo al guardar",Snackbar.LENGTH_SHORT).show();
                    }
                    if(result.getWasSuccessful() == 1 ){
                        //Esto es para agregar un item dentro del RecyclerView
                        lista_customers.add(customer);

                        //Esto es para ordernar la lista_customer  alfabeticamente por el ID
                            Collections.sort(lista_customers, new Comparator<Customers>() {
                                @Override
                                public int compare(Customers item1, Customers item2) {
                                    return item1.getCustomerID().compareToIgnoreCase(item2.getCustomerID());
                                }

                            });
                         //cuando termina la lista ya esta ordenada

                        //Esta funcion es para sacar la posicion de un objeto especifico en una lista
                        int position = lista_customers.indexOf(customer);

                        //Esto notifica el cambio
                        recyclerAdapter.notifyDataSetChanged();
                        //Esto es para que scroolle hasta la position correcta
                        customerRecycler.smoothScrollToPosition(position);

                        Toast.makeText(getApplicationContext(),"Se guardo exitosamente",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Snackbar.make(rootLayout,"No se puede cargar los datos",Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<WsResultado> call, Throwable t) {

            }
        });
    }
}
