package com.example.koreanrestaurantapp.ViewHolder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.Cart;
import com.example.koreanrestaurantapp.Database.Database;
import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.R;
import com.example.koreanrestaurantapp.model.Order;
import com.google.android.material.internal.TextDrawableHelper;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name, txt_price,numCart;
    ImageView minusBtn, plusBtn;
    public ImageView img_cart_image;
    private ItemClickListener itemClickListener;
    int quantity;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name=(TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView) itemView.findViewById(R.id.cart_item_price);
        numCart=itemView.findViewById(R.id.num_cart);
        quantity= Integer.parseInt(numCart.getText().toString());

        minusBtn=(ImageView) itemView.findViewById(R.id.minusBtnCart);
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>1){
                    quantity -=1;
                }
                numCart.setText(String.valueOf(quantity));
            }
        });

        plusBtn= (ImageView) itemView.findViewById(R.id.plusBtnCart);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity +=1;
                numCart.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    public List<Order> listData= new ArrayList<>();
    private Context context;
    private Cart cart;

    int priceOfFood, quantityOfFood;


    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);

        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Locale locale= new Locale("en","US");
        NumberFormat nfm=NumberFormat.getCurrencyInstance(locale);
        priceOfFood=Integer.parseInt(listData.get(position).getPrice());
        quantityOfFood=Integer.parseInt(listData.get(position).getQuantity());
        int price=priceOfFood*quantityOfFood;
        holder.txt_price.setText(nfm.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        holder.numCart.setText(listData.get(position).getQuantity());


        holder.numCart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Order order= listData.get(position);
                order.setQuantity(holder.numCart.getText().toString());
                new Database(context).updateCart(order);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
