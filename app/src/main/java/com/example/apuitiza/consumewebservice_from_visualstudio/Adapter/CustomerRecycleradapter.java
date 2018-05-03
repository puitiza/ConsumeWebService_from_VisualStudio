package com.example.apuitiza.consumewebservice_from_visualstudio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Activities.OrdersActivity;
import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;

import java.util.List;

public class CustomerRecycleradapter extends RecyclerView.Adapter<CustomerRecycleradapter.CustomersViewHolder>{

    private List<Customers> lstCustomer;
    private Context mContext;

    public CustomerRecycleradapter(List<Customers> lstCustomer, Context context) {
        this.lstCustomer = lstCustomer;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CustomersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_client, parent, false);
        return new CustomersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomersViewHolder holder, int position) {
        final Customers cliente = lstCustomer.get(position);
        holder.city.setText(cliente.getCity());
        holder.companyName.setText(cliente.getCompanyName());
        holder.idCustomer.setText(cliente.getCustomerID());
        //Recuerda que si quieres crear listView de un ListView aqui se hace
        holder.layout_item_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"click "+ cliente.getCompanyName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, OrdersActivity.class);
                intent.putExtra("id_customer",cliente.getCustomerID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstCustomer.size();
    }

    class CustomersViewHolder extends RecyclerView.ViewHolder {
        private TextView city, companyName, idCustomer;
        CardView layout_item_client;

        public CustomersViewHolder(View itemView) {
            super(itemView);

            city =  itemView.findViewById(R.id.city);
            companyName = itemView.findViewById(R.id.companyName);
            idCustomer =  itemView.findViewById(R.id.idCustomer);
            layout_item_client =  itemView.findViewById(R.id.layout_item_client);
        }
    }
}
