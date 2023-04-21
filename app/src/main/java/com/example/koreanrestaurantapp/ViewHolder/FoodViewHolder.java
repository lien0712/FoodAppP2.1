package com.example.koreanrestaurantapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name,food_price;
    public ImageView food_image, ic_fav;

    private ItemClickListener itemClickListener;
    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        food_name= (TextView) itemView.findViewById(R.id.food_name);
        food_image=(ImageView) itemView.findViewById(R.id.food_image);
        food_price=itemView.findViewById(R.id.food_price);
        ic_fav=itemView.findViewById(R.id.icfav);
        itemView.setOnClickListener((View.OnClickListener) this);
    }
    public void setItemOnClickListener(ItemClickListener itemClickListener){
        this.itemClickListener= itemClickListener;
    }
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
