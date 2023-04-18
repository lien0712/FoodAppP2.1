package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.OrderViewHolder;
import com.example.koreanrestaurantapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdReport extends AppCompatActivity {
    private BottomNavigationView btnavView;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_report);

        btnavView = findViewById(R.id.nav_view_report);
        btnavView.setSelectedItemId(R.id.navbt_report);
        btnavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.navbt_menu):
                        Intent intentReport= new Intent(AdReport.this,AdminActivity.class);
                        startActivity(intentReport);
                        return true;
                    case(R.id.navbt_table):
                        Intent intentTable= new Intent(AdReport.this,AdTable.class);
                        startActivity(intentTable);
                        return true;
                    case(R.id.navbt_report):
                        return true;
                    case(R.id.navbt_setting):
                        Intent intentSetting= new Intent(AdReport.this,AdSetting.class);
                        startActivity(intentSetting);
                        return true;
                }

                return false;
            }
        });

        database= FirebaseDatabase.getInstance();
        requests= database.getReference("Request");

        recyclerView= (RecyclerView) findViewById(R.id.listReportOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orderlayout,
                OrderViewHolder.class,
                requests
        ) {

            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(convertCodeToStatus(request.getStatus()));
                request.getAddress();
                orderViewHolder.txtOrderAddress.setText(request.getAddress());
                orderViewHolder.txtOrderPhone.setText(request.getPhone());

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent detailorderreport= new Intent(AdReport.this,Ad_detail_order_report.class);
                        detailorderreport.putExtra("FoodOrderId", adapter.getRef(position).getKey());
                        startActivity(detailorderreport);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Đã giao";
        else if (status.equals("1"))
            return "Đang giao";
        else
            return "Đang chuẩn bị ";

    }
}