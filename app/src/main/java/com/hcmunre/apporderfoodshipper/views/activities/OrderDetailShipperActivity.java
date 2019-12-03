package com.hcmunre.apporderfoodshipper.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.database.OrderData;
import com.hcmunre.apporderfoodshipper.models.entity.OrderDetail;
import com.hcmunre.apporderfoodshipper.views.adapters.OrderDetailAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailShipperActivity extends AppCompatActivity {
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.txt_total_price)
    TextView txt_total_price;
    @BindView(R.id.txt_confirm)
    TextView txt_confirm;
    @BindView(R.id.recyc_order_detail)
    RecyclerView recyc_order_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_phone_call)
    ImageView image_phone_call;
    @BindView(R.id.linear_bottom)
    LinearLayout linear_bottom;
    @BindView(R.id.txt_order_status)
    TextView txt_order_status;
    @BindView(R.id.txt_payment)
    TextView txt_payment;
    OrderData orderData=new OrderData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_shipper);
        ButterKnife.bind(this);
        init();
        eventClick();
        contactCustomer();
        new getOrderDetailShipper().execute();
    }

    private void init() {
        toolbar.setTitle(Common.currrentShipperOrder.getOrderName());
        toolbar.setSubtitle("Id#"+Common.currrentShipperOrder.getOrderID());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_phone.setText(Common.currrentShipperOrder.getOrderPhone());
        txt_total_price.setText(new StringBuilder(Common.currrentShipperOrder.getTotalPrice()+"").append("đ"));
        //
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyc_order_detail.setLayoutManager(linearLayoutManager);
        recyc_order_detail.setItemAnimator(new DefaultItemAnimator());
        int status=Common.currrentShipperOrder.getShippingStatus();
        int payment=Common.currrentShipperOrder.getPayment();
        if(status==3){
            linear_bottom.setVisibility(View.GONE);
            txt_order_status.setText(".Đơn đã hoàn tất");
        }else if(status==2){
            txt_order_status.setText(".Đã nhận đơn");
        }else if(status==0){
            txt_order_status.setText(".Đơn đã hủy");
            linear_bottom.setVisibility(View.GONE);
        }
        if(payment==1){
            txt_payment.setText("Đã thanh toán bằng PayPal");
        }
    }
    private void eventClick()
    {
        txt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderDetailShipperActivity.this,ShipperMapActivity.class));
            }
        });
    }
    private void contactCustomer(){
        image_phone_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ Common.currrentShipperOrder.getOrderPhone()));
                if (ActivityCompat.checkSelfPermission(OrderDetailShipperActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }else {
                    startActivity(intent);
                }
            }
        });
    }
    public class getOrderDetailShipper extends AsyncTask<String,String,ArrayList<OrderDetail>> {
        @Override
        protected void onPostExecute(ArrayList<OrderDetail> orderDetails) {
            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(OrderDetailShipperActivity.this, orderDetails);
            recyc_order_detail.setAdapter(orderDetailAdapter);
            orderDetailAdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<OrderDetail> doInBackground(String... strings) {
            ArrayList<OrderDetail> orderDetails;
            orderDetails=orderData.getOrderDetail(Common.currrentShipperOrder.getOrderID());
            return orderDetails;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
