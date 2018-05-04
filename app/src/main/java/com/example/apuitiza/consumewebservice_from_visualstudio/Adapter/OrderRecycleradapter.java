package com.example.apuitiza.consumewebservice_from_visualstudio.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apuitiza.consumewebservice_from_visualstudio.Models.Order;
import com.example.apuitiza.consumewebservice_from_visualstudio.R;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ApiUtils;
import com.example.apuitiza.consumewebservice_from_visualstudio.Services.ResultadoService;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class OrderRecycleradapter extends RecyclerView.Adapter<OrderRecycleradapter.OrderViewHolder>{

    private List<Order> lstOrders;
    private Context mContext;

    private ResultadoService mResultadoService;

    public OrderRecycleradapter(List<Order> lstOrders, Context mContext) {
        this.lstOrders = lstOrders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_orders, parent, false);
        mResultadoService = ApiUtils.getAPIService();
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
               // Toast.makeText(mContext,"click "+ order_cliente.getOrderID(),Toast.LENGTH_LONG).show();
                showInfoDialog_Order(order_cliente);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstOrders.size();
    }

    private void showInfoDialog_Order(final Order order) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Informacion:");
        dialog.setMessage("Se muestra la informacion de la Orden");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View register_layout = inflater.inflate(R.layout.layout_show_info_order,null);

        final MaterialEditText edtOrderDate = register_layout.findViewById(R.id.edtOrderDate);
        final MaterialEditText edtShipAddress = register_layout.findViewById(R.id.edtShipAddress);
        final MaterialEditText edtShipCity = register_layout.findViewById(R.id.edtShipCity);
        final MaterialEditText edtShipName = register_layout.findViewById(R.id.edtShipName);
        final MaterialEditText edtShipPostCode = register_layout.findViewById(R.id.edtShipPostCode);
        final MaterialEditText edtShippedDate = register_layout.findViewById(R.id.edtShippedDate);

        edtOrderDate.setText(order.getOrderDate());
        edtShipAddress.setText(order.getShipAddress());
        edtShipCity.setText(order.getShipCity());
        edtShipName.setText(order.getShipName());
        edtShipPostCode.setText(order.getShipPostcode());
        edtShippedDate.setText(order.getShippedDate());



        dialog.setView(register_layout);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                //Actualizando
                order.setShipAddress(edtShipAddress.getText().toString());
                order.setShipCity(edtShipCity.getText().toString());
                order.setShipName(edtShipName.getText().toString());
                order.setShipPostcode(edtShipPostCode.getText().toString());
               // Toast.makeText(mContext,"Info mostrada",Toast.LENGTH_SHORT).show();

                retrofit2.Call<Integer> resultData = mResultadoService.updateOrderAddress(order);

                //Luego de Actualizar el campo notifyDataSetChanged lo que hace es que notifica el cambio y actualiza el recyclerView
                notifyDataSetChanged();
                resultData.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(retrofit2.Call<Integer> call, Response<Integer> response) {
                        Integer i = response.body();
                        int anthi =  Integer.parseInt(i.toString());
                        if(i == 0){
                            Toast.makeText(mContext,"Exito",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Integer> call, Throwable t) {

                    }
                });
            }
        });
        dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
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
