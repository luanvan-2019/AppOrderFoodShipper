package com.hcmunre.apporderfoodshipper.views.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.models.database.ShipperData;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.views.activities.PreferenceUtilsShipper;
import com.hcmunre.apporderfoodshipper.views.adapters.OrderNewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewOrderFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.recyc_order)
    RecyclerView recyc_order;
    @BindView(R.id.linear_order_new)
    LinearLayout linear_order_new;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    ShipperData shipperData=new ShipperData();
    public NewOrderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder= ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyc_order.setLayoutManager(layoutManager);
        recyc_order.setItemAnimator(new DefaultItemAnimator());
        //swipe
        swipe_layout.setColorSchemeResources(R.color.colorPrimary);
        //swipe
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getOrderForShipper(0, 10,swipe_layout);
            }
        });
        new getOrderForShipper(0, 10,swipe_layout);

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
                OrderNewAdapter orderNewAdapter = new OrderNewAdapter(getActivity(), shipperOrders);
                recyc_order.setAdapter(orderNewAdapter);
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
            shipperOrders=shipperData.getOrderForShipper(PreferenceUtilsShipper.getUserId(getActivity()),1,startIndex,endIndex);

            return shipperOrders;
        }

    }

    private void refresh(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,4000);
            }
        };
        handler.postDelayed(runnable,4000);
    }

}
