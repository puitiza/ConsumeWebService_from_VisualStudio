package com.example.apuitiza.consumewebservice_from_visualstudio.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Customers;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;

import java.util.List;

public class CustomerRecycleradapter extends RecyclerView.Adapter<CustomerRecycleradapter.CustomersViewHolder>{

    private List<Customers> lstCustomer;
    private Activity activity;

    public CustomerRecycleradapter(List<Customers> lstCustomer, Activity activity) {
        this.lstCustomer = lstCustomer;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_client, parent, false);
        return new CustomersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomersViewHolder holder, int position) {
        Customers cliente = lstCustomer.get(position);
        holder.city.setText(cliente.getCity());
        holder.companyName.setText(cliente.getCompanyName());
        holder.idCustomer.setText(cliente.getCustomerID());
        //Recuerda que si quieres crear listView de un ListView aqui se hace
    }

    @Override
    public int getItemCount() {
        return lstCustomer.size();
    }

    class CustomersViewHolder extends RecyclerView.ViewHolder {
        private TextView city, companyName, idCustomer;

        public CustomersViewHolder(View itemView) {
            super(itemView);

            city = (TextView) itemView.findViewById(R.id.city);
            companyName = (TextView) itemView.findViewById(R.id.companyName);
            idCustomer = (TextView) itemView.findViewById(R.id.idCustomer);

        }
    }
}
