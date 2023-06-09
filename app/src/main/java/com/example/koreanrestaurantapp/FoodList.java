package com.example.koreanrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.koreanrestaurantapp.Database.Database;
import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.FoodViewHolder;
import com.example.koreanrestaurantapp.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    Database localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database= FirebaseDatabase.getInstance();
        foodList= database.getReference("Food");

        localDB= new Database(this);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_foods);
        recyclerView.setHasFixedSize(true);
        //layoutManager= new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        if( getIntent()!=null){
            categoryId= getIntent().getStringExtra("CategoryId");
            if(!categoryId.isEmpty()){
                loadListFood(categoryId);
            }
        }
    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId))
            //like Select * from Foods where MenuId=
        {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                food.getName();
                foodViewHolder.food_name.setText(food.getName());
                foodViewHolder.food_price.setText(String.format("%s vnd",food.getPrice()));
                Picasso.with(getBaseContext()).load(food.getImage()).into(foodViewHolder.food_image);
                //favorite
                if(localDB.isFavorite(adapter.getRef(i).getKey())){
                    foodViewHolder.ic_fav.setImageResource(R.drawable.ic_favorite_24);
                }
                //chang state
                foodViewHolder.ic_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!localDB.isFavorite(adapter.getRef(i).getKey())){
                            localDB.addToFavorite(adapter.getRef(i).getKey());
                            foodViewHolder.ic_fav.setImageResource(R.drawable.ic_favorite_24);
                            Toast.makeText(FoodList.this, ""+food.getName()+" was added to favorite", Toast.LENGTH_SHORT).show();
                        }else {
                            localDB.removeToFavorite(adapter.getRef(i).getKey());
                            foodViewHolder.ic_fav.setImageResource(R.drawable.ic_favorite_border_24);
                            Toast.makeText(FoodList.this, ""+food.getName()+" was removed from favorite", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                final Food local = food;
                foodViewHolder.setItemOnClickListener (new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        Intent foodDetail= new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };

        //Set Adapter

        recyclerView.setAdapter(adapter);
    }
}