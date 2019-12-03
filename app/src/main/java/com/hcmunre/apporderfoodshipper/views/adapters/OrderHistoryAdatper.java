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
import com.hcmunre.apporderfoodshipper.models.entity.Order;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.views.activities.OrderDetailShipperActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdatper extends RecyclerView.Adapter<OrderHistoryAdatper.ViewHolder> {
    private Context context;
    private ArrayList<ShipperOrder> orders;
    SimpleDateFormat simpleDateFormat;
    OrderData orderData=new OrderData();
    public OrderHistoryAdatper(Context context, ArrayList<ShipperOrder> listOrderHistory) {
        this.context = context;
        this.orders = listOrderHistory;
        simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
    }
    public void setData(ArrayList<Order> listOrderHistory) {
        this.orders.clear();
        listOrderHistory.addAll(listOrderHistory);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderHistoryAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_history,null);
        return new OrderHistoryAdatper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdatper.ViewHolder holder, final int position) {
        ShipperOrder order=orders.get(position);
        holder.txt_orderId.setText(order.getOrderID()+"");
        holder.txt_quantity.setText(order.getNumberOfItem()+"");
        holder.txt_total_price.setText(String.valueOf(holder.numberFormat.format(order.getTotalPrice())));
        holder.txt_name_customer.setText(order.getOrderName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.currrentShipperOrder=orders.get(position);
                context.startActivity(new Intent(context, OrderDetailShipperActivity.class));
            }
        });
    }
    public void itemRemoved(int position) {
        orders.remove(position);
        notifyItemRemoved(position);

    }
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addItem(ArrayList<ShipperOrder> orderArrayList) {
        orders.addAll(orderArrayList);
        notifyDataSetChanged();
    }
    public void refresh(ArrayList<ShipperOrder> itemsw) {
        this.orders = itemsw;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_orderId)
        TextView txt_orderId;
        @BindView(R.id.txt_name_customer)
        TextView txt_name_customer;
        @BindView(R.id.txt_quantity)
        TextView txt_quantity;
        @BindView(R.id.txt_total_price)
        TextView txt_total_price;
        @BindView(R.id.btn_confirm)
        TextView btn_confirm;
        @BindView(R.id.btn_cancel)
        TextView btn_cancel;
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            btn_confirm.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }
    }


}
