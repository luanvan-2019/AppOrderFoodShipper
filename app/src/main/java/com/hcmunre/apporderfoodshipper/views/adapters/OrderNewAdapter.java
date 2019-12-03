package com.hcmunre.apporderfoodshipper.views.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.hcmunre.apporderfoodshipper.models.entity.Order;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.views.activities.OrderDetailShipperActivity;
import com.hcmunre.apporderfoodshipper.views.activities.ShipperMapActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderNewAdapter extends RecyclerView.Adapter<OrderNewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ShipperOrder> shipperOrders;
    ShipperData shipperData=new ShipperData();
    OrderData orderData=new OrderData();
    public OrderNewAdapter(Context context, ArrayList<ShipperOrder> shipperOrders) {
        this.context = context;
        this.shipperOrders = shipperOrders;
    }

    @NonNull
    @Override
    public OrderNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shipper_order,null);
        return new OrderNewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderNewAdapter.ViewHolder holder, final int position) {
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
        holder.btn_receiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.currrentShipperOrder=shipperOrders.get(position);
                //2:received
                boolean success=shipperData.updateStatusShipping(shipperOrder.getOrderID(),2);
                Order order=new Order();
                order.setOrderId(shipperOrder.getOrderID());
                order.setOrderStatus(2);
                boolean success_update_order=orderData.updateOrder(order);
                if(success==true){
                    if(success_update_order==true){
                        Common.showToast(context,"Đã nhận");
                        itemRemoved(position);
                    }else{
                        Log.d(Common.TAG,"Lỗi");
                    }
                }else {
                    Log.d(Common.TAG,"Lỗi");
                }
                context.startActivity(new Intent(context, ShipperMapActivity.class));
            }
        });
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0:cancel
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Hủy đơn")
                        .setMessage("Bạn có muốn hủy đơn không ?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean success=shipperData.updateStatusShipping(shipperOrder.getOrderID(),0);
                                Order order=new Order();
                                order.setOrderId(shipperOrder.getOrderID());
                                order.setOrderStatus(3);
                                boolean success_update_order=orderData.updateOrder(order);
                                if(success==true){
                                    if(success_update_order==true){
                                        Common.showToast(context,"Đã hủy");
                                        itemRemoved(position);
                                    }else{
                                        Log.d(Common.TAG,"Lỗi");
                                    }
                                }else {
                                    Log.d(Common.TAG,"Lỗi");
                                }
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


            }
        });
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
