package com.hcmunre.apporderfoodshipper.views.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.models.database.ShipperData;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.views.adapters.OrderHistoryAdatper;
import com.hcmunre.apporderfoodshipper.views.adapters.OrderNewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.recyc_order_history)
    RecyclerView recyc_order_history;
    @BindView(R.id.linear_order_new)
    LinearLayout linear_order_new;
    ShipperData shipperData = new ShipperData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setTitle("Lịch sử đơn hàng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyc_order_history.setLayoutManager(linearLayoutManager);
        recyc_order_history.setItemAnimator(new DefaultItemAnimator());
        swipe_layout.setColorSchemeResources(R.color.colorPrimary);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getOrderForShipper(0,10,swipe_layout);
            }
        });
        new getOrderForShipper(0,10,swipe_layout);
    }

    public class getOrderForShipper extends AsyncTask<String,String, ArrayList<ShipperOrder>> {
        int startIndex,endIndex;
        SwipeRefreshLayout swipeRefreshLayout;

        public getOrderForShipper(int startIndex, int endIndex, SwipeRefreshLayout swipeRefreshLayout) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.swipeRefreshLayout = swipeRefreshLayout;
            this.execute();
        }

        @Override
        protected void onPreExecute() {
            if(!swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<ShipperOrder> shipperOrders) {
            if (shipperOrders.size() > 0) {
                OrderHistoryAdatper orderNewAdapter = new OrderHistoryAdatper(OrderHistoryActivity.this, shipperOrders);
                recyc_order_history.setAdapter(orderNewAdapter);
                linear_order_new.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            } else {
                linear_order_new.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        protected ArrayList<ShipperOrder> doInBackground(String... strings) {
            ArrayList<ShipperOrder> shipperOrders;
            shipperOrders=shipperData.getOrderForShipper(PreferenceUtilsShipper.getUserId(OrderHistoryActivity.this),3,startIndex,endIndex);
            return shipperOrders;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
