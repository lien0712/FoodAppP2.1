package com.example.koreanrestaurantapp.ViewHolder;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.R;

public class UserAcountListVH  extends RecyclerView.ViewHolder{

    public TextView accountphone, accountname, accountage, accountgender, accountpassword,accountemail;

    public UserAcountListVH(@NonNull View itemView) {
        super(itemView);

        accountphone= itemView.findViewById(R.id.accountphone);
        accountname= itemView.findViewById(R.id.accountname);
        accountage= itemView.findViewById(R.id.accountage);
        accountgender= itemView.findViewById(R.id.accountgender);
        accountemail= itemView.findViewById(R.id.accountemail);
        accountpassword= itemView.findViewById(R.id.accountpassword);
    }
}
