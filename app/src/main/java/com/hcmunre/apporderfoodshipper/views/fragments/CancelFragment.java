package com.hcmunre.apporderfoodshipper.views.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
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
import com.hcmunre.apporderfoodshipper.views.adapters.OrderCancelAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CancelFragment extends Fragment{

    Unbinder unbinder;
    @BindView(R.id.recyc_cancel_order)
    RecyclerView recyc_cancel_order;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.linear_order_new)
    LinearLayout linear_order_new;
    ShipperData shipperData=new ShipperData();
    public CancelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cancel, container, false);
        unbinder= ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyc_cancel_order.setLayoutManager(layoutManager);
        recyc_cancel_order.setItemAnimator(new DefaultItemAnimator());
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
                OrderCancelAdapter orderCancelAdapter = new OrderCancelAdapter(getActivity(), shipperOrders);
                recyc_cancel_order.setAdapter(orderCancelAdapter);
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
            shipperOrders=shipperData.getOrderForShipper(PreferenceUtilsShipper.getUserId(getActivity()),0,startIndex,endIndex);
            return shipperOrders;
        }
    }

}
