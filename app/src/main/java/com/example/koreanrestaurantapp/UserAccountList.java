package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.koreanrestaurantapp.ViewHolder.FoodViewHolderAd;
import com.example.koreanrestaurantapp.ViewHolder.UserAcountListVH;
import com.example.koreanrestaurantapp.model.Food;
import com.example.koreanrestaurantapp.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAccountList extends AppCompatActivity {

    private BottomNavigationView btnavView;
    TextView adInform, adUserAccount, adLogout;
    FirebaseRecyclerAdapter<User, UserAcountListVH> adapter;
    FirebaseDatabase database;
    DatabaseReference user;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_list);

        database= FirebaseDatabase.getInstance();
        user= database.getReference("User");
        recyclerView= findViewById(R.id.rvshowaccount);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnavView = findViewById(R.id.nav_view_setting_account);
        btnavView.setSelectedItemId(R.id.navbt_setting);
        btnavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.navbt_menu):
                        Intent intentReport= new Intent(UserAccountList.this,AdminActivity.class);
                        startActivity(intentReport);
                        return true;
                    case(R.id.navbt_table):
                        Intent intentTable= new Intent(UserAccountList.this,AdTable.class);
                        startActivity(intentTable);
                        return true;
                    case(R.id.navbt_report):
                        Intent intentSetting= new Intent(UserAccountList.this,AdReport.class);
                        startActivity(intentSetting);
                        return true;
                    case(R.id.navbt_setting):
                        return true;

                }

                return false;
            }
        });

        adInform= findViewById(R.id.adInformaccount);adUserAccount=findViewById(R.id.adUserAccountaccount);
        adUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserAccountList.this, AdSetting.class);
                startActivity(intent);
            }
        });

        adLogout=findViewById(R.id.logoutAdaccount);
        adLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserAccountList.this, MainActivity.class);
                startActivity(intent);
            }
        });
        adapter= new FirebaseRecyclerAdapter<User, UserAcountListVH>(
                User.class,
                R.layout.user_account_list_show,
                UserAcountListVH.class,
                user) {
            @Override
            protected void populateViewHolder(UserAcountListVH userAcountListVH, User user, int i) {
                userAcountListVH.accountphone.setText(user.getPhone());
                userAcountListVH.accountname.setText(user.getName());
                userAcountListVH.accountage.setText(user.getAge());
                userAcountListVH.accountemail.setText(user.getEmail());
                userAcountListVH.accountgender.setText(user.getGender());
                userAcountListVH.accountpassword.setText(user.getPassword());
            }
        };
        recyclerView.setAdapter(adapter);
}
}