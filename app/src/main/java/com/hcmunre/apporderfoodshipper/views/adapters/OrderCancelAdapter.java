package com.hcmunre.apporderfoodshipper.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.database.OrderData;
import com.hcmunre.apporderfoodshipper.models.database.ShipperData;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.views.activities.OrderDetailShipperActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCancelAdapter extends RecyclerView.Adapter<OrderCancelAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ShipperOrder> shipperOrders;
    ShipperData shipperData=new ShipperData();
    OrderData orderData=new OrderData();
    public OrderCancelAdapter(Context context, ArrayList<ShipperOrder> shipperOrders) {
        this.context = context;
        this.shipperOrders = shipperOrders;
    }

    @NonNull
    @Override
    public OrderCancelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shipper_order,null);
        return new OrderCancelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCancelAdapter.ViewHolder holder, final int position) {
        final ShipperOrder shipperOrder=shipperOrders.get(position);
        holder.txt_orderId.setText(shipperOrder.getOrderID()+"");
        holder.txt_quantity.setText(shipperOrder.getNumberOfItem()+"");
        holder.txt_total_price.setText(String.valueOf(holder.numberFormat.format(shipperOrder.getTotalPrice())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.currrentShipperOrder=shipperOrders.get(position);
                context.startActivity(new Intent(context, OrderDetailShipperActivity.class));
            }
        });
        holder.btn_receiv.setVisibility(View.GONE);
        holder.btn_cancel.setVisibility(View.GONE);
    }
    public void itemRemoved(int position) {
        shipperOrders.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return shipperOrders.size();
    }
    public void addItem(ArrayList<ShipperOrder> shipperOrder) {
        shipperOrders.addAll(shipperOrder);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_orderId)
        TextView txt_orderId;
        @BindView(R.id.txt_quantity)
        TextView txt_quantity;
        @BindView(R.id.txt_total_price)
        TextView txt_total_price;
        @BindView(R.id.btn_receive)
        TextView btn_receiv;
        @BindView(R.id.btn_cancel)
        TextView btn_cancel;
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
