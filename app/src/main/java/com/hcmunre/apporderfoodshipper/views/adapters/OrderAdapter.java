package com.hcmunre.apporderfoodshipper.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.models.entity.Order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orderArrrayList;

    public OrderAdapter(Context context, ArrayList<Order> orderArrrayList) {
        this.context = context;
        this.orderArrrayList = orderArrrayList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_view,null);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
            final Order order=orderArrrayList.get(position);
            holder.order_id.setText(order.getmOrderId());
    }

    @Override
    public int getItemCount() {
        return orderArrrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.order_id)
        TextView order_id;
        @BindView(R.id.order_name)
        TextView order_name;
        @BindView(R.id.order_phone)
        TextView order_phone;
        @BindView(R.id.order_address)
        TextView order_address;
        @BindView(R.id.order_date)
        TextView order_date;
        @BindView(R.id.order_status)
        TextView order_status;
        @BindView(R.id.order_price)
        TextView order_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
