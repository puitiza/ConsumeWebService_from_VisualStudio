package com.example.apuitiza.consumewebservice_from_visualstudio.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Adapter.CustomerRecycleradapter;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Resultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.WsResultado;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Util.RecyclerViewItemClickListener;
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
/*---------------------------------------Web Services with Basic Authentication----------------------------------------------------*/
        final MaterialEditText userBA = findViewById(R.id.edtUser_BAuthentication);
        final MaterialEditText passBA = findViewById(R.id.edtPass_BAuthentication);
        final MaterialEditText datoBA = findViewById(R.id.edtDato);
        final TextView getData = findViewById(R.id.getData);
        Button btnGuardar;
        btnGuardar = findViewById(R.id.addBasicAuth);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st_userBA = userBA.getText().toString().trim();
                String st_passBA = passBA.getText().toString().trim();
                String st_datoBA = datoBA.getText().toString().trim();
                String base = st_userBA + ":"+st_passBA;

                String AuthHeader = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

                Call<Resultado> resultadoData = mResultadoService.getResultadoBasicAuthentication(AuthHeader,st_datoBA);
                resultadoData.enqueue(new Callback<Resultado>() {
                    @Override
                    public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                        if(response.body()!= null){
                            Toast.makeText(getApplicationContext(),"ya dio",Toast.LENGTH_SHORT).show();
                            getData.setText(response.body().getGetDataResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<Resultado> call, Throwable t) {

                    }
                });
            }
        });
        /*---------------------------------------Web Services with Basic Authentication----------------------------------------------------*/

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
                    recyclerAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
                        /* @Override
                         public void onItemClick(View view, int position) {
                             Toast.makeText(MainActivity.this, getResources().getString(R.string.clicked_item, albumList.get(position).getAlbumName()), Toast.LENGTH_SHORT).show();
                         }*/
                        @Override
                        public void onItemLongClick(View view,final int position) {
                            String options[] = new String[] {"Editar Cliente", "Eliminar Cliente"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Select your option: "+lista_customers.get(position).getCustomerID());
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    switch (item){
                                        case 0 :
                                            // showAlertForEditCustomer(lista_customers.get(info.position));
                                            Toast.makeText(getApplicationContext(),"Editar Cliente",Toast.LENGTH_SHORT).show();
                                        case 1 :
                                            showAlertForDeleteCustomer(lista_customers.get(position));
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                    rootLayout = findViewById(R.id.activity_main);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showAlertForCreateCustomer();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"La información no se puede mostrar",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No esta disponible la información, problema con la red",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertForDeleteCustomer(final Customers customers) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Eliminar Cliente : "+ customers.getCustomerID());
        dialog.setMessage("¿Estas seguro que quieres eliminar el cliente : "+customers.getCompanyName()+" ?");

        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int thisPosition) {
                Call<WsResultado> wsResultadoData = mResultadoService.deleteCustomer(customers.getCustomerID());
                wsResultadoData.enqueue(new Callback<WsResultado>() {
                    @Override
                    public void onResponse(Call<WsResultado> call, Response<WsResultado> response) {
                        if(response.body()!= null){
                            WsResultado result= response.body();
                            if(result.getWasSuccessful() == -3 || result.getWasSuccessful() == -1){
                                Snackbar.make(rootLayout,result.getException(),Snackbar.LENGTH_LONG).show();
                            }
                            if(result.getWasSuccessful() == 1 ){
//--------------------------------Esto es para remover un item dentro del RecyclerView---------------------------------------
                                lista_customers.remove(customers);
                                recyclerAdapter.notifyDataSetChanged();
//---------------------------------------------------------------------------------------------------------------------------
                                Snackbar.make(rootLayout,"Se elimino correctamente",Snackbar.LENGTH_LONG).show();
                            }

                        }else{
                            Snackbar.make(rootLayout,"No se puede cargar los datos",Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WsResultado> call, Throwable t) {
                        Snackbar.make(rootLayout,"Sin acceso a Internet",Snackbar.LENGTH_SHORT).show();
                    }
                });
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
//   ----------------------------------Esto es para agregar un item dentro del RecyclerView----------------------------------------
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
//---------------------------------------------------------------------------------------------------------------------------------
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
