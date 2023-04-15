package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.TableViewHolder;
import com.example.koreanrestaurantapp.model.Table;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdTable extends AppCompatActivity {
    private BottomNavigationView btnavView;
    FirebaseDatabase database;
    DatabaseReference table;

    RecyclerView recycler_table;
    FirebaseRecyclerAdapter<Table, TableViewHolder> adapter;
    TextView tableName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_table);

        database= FirebaseDatabase.getInstance();
        table= database.getReference("Table");

        btnavView = findViewById(R.id.nav_view_table);
        btnavView.setSelectedItemId(R.id.navbt_table);
        btnavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.navbt_menu):
                        Intent intentMenu= new Intent(AdTable.this,AdminActivity.class);
                        startActivity(intentMenu);
                        return true;
                    case(R.id.navbt_table):

                        return true;
                    case(R.id.navbt_report):
                        Intent intentReport= new Intent(AdTable.this,AdReport.class);
                        startActivity(intentReport);
                        return true;
                    case(R.id.navbt_setting):
                        Intent intentSetting= new Intent(AdTable.this,AdSetting.class);
                        startActivity(intentSetting);
                        return true;
                }

                return false;
            }
        });


        recycler_table=(RecyclerView) findViewById(R.id.recycler_table);
        recycler_table.setHasFixedSize(true);
        recycler_table.setLayoutManager(new GridLayoutManager(this,2));

        loadTable();

    }

    private void loadTable() {
        adapter= new FirebaseRecyclerAdapter<Table, TableViewHolder>(Table.class,R.layout.table_item,TableViewHolder.class,table) {
            @Override
            protected void populateViewHolder(TableViewHolder tableViewHolder, Table table, int i) {
                tableViewHolder.tableName.setText(table.getName());
                tableViewHolder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent table= new Intent(AdTable.this,DetailTable.class);
                        table.putExtra("TableId", adapter.getRef(position).getKey());
                        startActivity(table);
                    }
                });
            }
        };
        recycler_table.setAdapter(adapter);

    }
}