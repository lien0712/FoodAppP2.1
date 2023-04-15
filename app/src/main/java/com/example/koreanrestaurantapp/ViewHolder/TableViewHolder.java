package com.example.koreanrestaurantapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.R;

public class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tableName;
    private ItemClickListener itemClickListener;

    public TableViewHolder(@NonNull View itemView) {
        super(itemView);
        tableName=(TextView) itemView.findViewById(R.id.table_name);

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
