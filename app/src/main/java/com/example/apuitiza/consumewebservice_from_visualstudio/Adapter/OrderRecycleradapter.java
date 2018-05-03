package com.example.apuitiza.consumewebservice_from_visualstudio.Adapter;

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

import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Order;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;

import java.util.List;

public class OrderRecycleradapter extends RecyclerView.Adapter<OrderRecycleradapter.OrderViewHolder>{

    private List<Order> lstOrders;
    private Context mContext;

    public OrderRecycleradapter(List<Order> lstOrders, Context mContext) {
        this.lstOrders = lstOrders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_orders, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final Order order_cliente = lstOrders.get(position);

        holder.orderDate.setText(order_cliente.getOrderDate());
        holder.orderID.setText(String.valueOf(order_cliente.getOrderID()));
        holder.shipAddress.setText(order_cliente.getShipAddress());
        holder.shipCity.setText(order_cliente.getShipCity());
        holder.shipName.setText(order_cliente.getShipName());
        holder.shipPostcode.setText(order_cliente.getShipPostcode());
        holder.shippedDate.setText(order_cliente.getShippedDate());

        //Recuerda que si quieres crear listView de un ListView aqui se hace
        holder.layout_item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"click "+ order_cliente.getOrderID(),Toast.LENGTH_LONG).show();
               /* Intent intent = new Intent(mContext, OrdersActivity.class);
                intent.putExtra("id_customer",cliente.getCustomerID());
                mContext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstOrders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderDate,orderID,shipAddress, shipCity, shipName,shipPostcode, shippedDate;
        CardView layout_item_order;

        public OrderViewHolder(View itemView) {
            super(itemView);

            orderDate =  itemView.findViewById(R.id.orderDate);
            orderID = itemView.findViewById(R.id.orderID);
            shipAddress =  itemView.findViewById(R.id.shipAddress);
            shipCity = itemView.findViewById(R.id.shipCity);
            shipName = itemView.findViewById(R.id.shipName);
            shipPostcode = itemView.findViewById(R.id.shipPostcode);
            shippedDate = itemView.findViewById(R.id.shippedDate);
            layout_item_order =  itemView.findViewById(R.id.layout_item_order);
        }
    }
}
