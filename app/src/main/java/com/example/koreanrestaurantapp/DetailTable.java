package com.example.koreanrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.koreanrestaurantapp.Database.Database;
import com.example.koreanrestaurantapp.ViewHolder.CartAdapter;
import com.example.koreanrestaurantapp.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailTable extends AppCompatActivity {

    RecyclerView listFoodtable;
    Button btnSelectFood,btnPay;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotal;
    List<Order> cart= new ArrayList<>();
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_table);

        database= FirebaseDatabase.getInstance();
        request= database.getReference("Requests");
        recyclerView=(RecyclerView) findViewById(R.id.list_food_table);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotal=(TextView) findViewById(R.id.txtTotalTable);

        listFoodtable= findViewById(R.id.list_food_table);
        btnSelectFood= findViewById(R.id.btnSelectFood);
        btnSelectFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DetailTable.this,TableCategory.class);
                startActivity(intent);
            }
        });

        loadListFood();
        btnPay=findViewById(R.id.btnPay);
    }


    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter= new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        int total=0;
        for(Order order:cart){
            total+= (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en","US");
            NumberFormat nfm= NumberFormat.getCurrencyInstance(locale);

            txtTotal.setText(nfm.format(total));
        }
    }
}