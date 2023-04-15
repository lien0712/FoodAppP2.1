package com.example.koreanrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.MenuViewHolderAd;
import com.example.koreanrestaurantapp.ViewHolder.TableViewHolder;
import com.example.koreanrestaurantapp.model.Category;
import com.example.koreanrestaurantapp.model.Table;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TableCategory extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_table;
    FirebaseRecyclerAdapter<Category, MenuViewHolderAd> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_category);

        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");
        recycler_table=(RecyclerView) findViewById(R.id.recycler_table_menu);
        recycler_table.setHasFixedSize(true);
        recycler_table.setLayoutManager(new GridLayoutManager(this,2));

        loadTableCategoy();

    }
    private void loadTableCategoy() {
        adapter= new FirebaseRecyclerAdapter<Category, MenuViewHolderAd>(Category.class,R.layout.menu_item,MenuViewHolderAd.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolderAd menuViewHolderAd, Category category, int i) {
                String ca= category.getName();
                menuViewHolderAd.txtMenuName.setText((category.getName()));
                Picasso.with(getBaseContext()).load(category.getImage()).into(menuViewHolderAd.imageView);
                menuViewHolderAd.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category and send to new Activity
                        Intent foodListAd= new Intent(TableCategory.this,FoodListTable.class);
                        foodListAd.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(foodListAd);
                    }
                });
            }
        };
            recycler_table.setAdapter(adapter);

    }
}