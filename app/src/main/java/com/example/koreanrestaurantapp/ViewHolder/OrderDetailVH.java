package com.example.koreanrestaurantapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.R;

public class OrderDetailVH extends RecyclerView.ViewHolder  {

    public TextView nameFood, priceFood, quantityFood, discountFood;

    public OrderDetailVH(@NonNull View itemView) {
        super(itemView);
        nameFood= itemView.findViewById(R.id.tvNameFoodOrderReport);
        priceFood= itemView.findViewById(R.id.tvPriceFoodOrderReport);
        quantityFood= itemView.findViewById(R.id.tvQuantityFoodOrderReport);
        discountFood=itemView.findViewById(R.id.tvDiscountFoodOrderReport);
    }
}
