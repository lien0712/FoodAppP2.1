package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.FoodViewHolder;
import com.example.koreanrestaurantapp.ViewHolder.FoodViewHolderAd;
import com.example.koreanrestaurantapp.ViewHolder.OrderDetailVH;
import com.example.koreanrestaurantapp.ViewHolder.OrderViewHolder;
import com.example.koreanrestaurantapp.model.Food;
import com.example.koreanrestaurantapp.model.Order;
import com.example.koreanrestaurantapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Ad_detail_order_report extends AppCompatActivity {

    TextView name, phone, addr, status, total;
    RecyclerView foodRecycle;

    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference detailOrder;
    String foodOrderId="";
    Request currentOrder;
    FirebaseRecyclerAdapter<Order, OrderDetailVH> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail_order_report);

        name=findViewById(R.id.tvNameOrderReport);
        phone=findViewById(R.id.tvPhoneOrderReport);
        addr=findViewById(R.id.tvAddressOrderReport);
        status=findViewById(R.id.tvStatusOrderReport);
        total=findViewById(R.id.tvTotalOrderReport);

        database= FirebaseDatabase.getInstance();
        detailOrder= database.getReference("Request");

        foodRecycle=(RecyclerView) findViewById(R.id.rvFoodOrderReport);
        foodRecycle.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        foodRecycle.setLayoutManager(layoutManager);

        if( getIntent()!=null){
            foodOrderId= getIntent().getStringExtra("FoodOrderId");
            if(!foodOrderId.isEmpty()&& foodOrderId!=null){
                loadDetailOrder(foodOrderId);
            }
        }



    }

    private void loadDetailOrder(String foodId){

        detailOrder.child(foodId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOrder=snapshot.getValue(Request.class);
                name.setText(String.format("Name: %s",currentOrder.getName()));
                phone.setText(String.format("Phone: %s",currentOrder.getPhone()));
                addr.setText(String.format("Address: %s",currentOrder.getAddress()));
                status.setText(String.format("Status: %s",currentOrder.getStatus()));
                total.setText(String.format("Total: %s",currentOrder.getTotal()));

                adapter = new FirebaseRecyclerAdapter<Order, OrderDetailVH>(
                        Order.class,
                        R.layout.ad_detail_food_order_report,
                        OrderDetailVH.class,
                        detailOrder.child(foodId+"/food") ){

                    @Override
                    protected void populateViewHolder(OrderDetailVH orderDetailVH, Order order, int i) {
                        orderDetailVH.nameFood.setText(String.format("Name: %s",order.getProductName()));
                        orderDetailVH.priceFood.setText(String.format("Price: %s",order.getPrice()));
                        orderDetailVH.quantityFood.setText(String.format("Quantity: %s",order.getQuantity()));
                        orderDetailVH.discountFood.setText(String.format("Discount: %s",order.getDiscount()));
                    }
                };

                foodRecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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