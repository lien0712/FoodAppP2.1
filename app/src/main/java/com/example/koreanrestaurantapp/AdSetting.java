package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdSetting extends AppCompatActivity {
    private BottomNavigationView btnavView;
    TextView adInform, adUserAccount, adLogout;
    EditText userPhoneInf,userNameInf,userAgeInf,userGenderInf,userEmailInf,userPasswordInf,userPasswordConfirmInf;
    FirebaseDatabase database;
    DatabaseReference user;
    User current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_setting);

        database= FirebaseDatabase.getInstance();
        user=database.getReference("User");
        btnavView = findViewById(R.id.nav_view_setting);
        btnavView.setSelectedItemId(R.id.navbt_setting);
        btnavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.navbt_menu):
                        Intent intentReport= new Intent(AdSetting.this,AdminActivity.class);
                        startActivity(intentReport);
                        return true;
                    case(R.id.navbt_table):
                        Intent intentTable= new Intent(AdSetting.this,AdTable.class);
                        startActivity(intentTable);
                        return true;
                    case(R.id.navbt_report):
                        Intent intentSetting= new Intent(AdSetting.this,AdReport.class);
                        startActivity(intentSetting);
                        return true;
                    case(R.id.navbt_setting):
                        return true;

                }

                return false;
            }
        });

        adInform= findViewById(R.id.adInform);
        adUserAccount=findViewById(R.id.adUserAccount);
        adUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdSetting.this, UserAccountList.class);
                startActivity(intent);
            }
        });
        adLogout=findViewById(R.id.logoutAd);
        adLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdSetting.this,MainActivity.class);
                startActivity(intent);
            }
        });

        userPhoneInf= findViewById(R.id.aduserPhoneInf);
        userNameInf= findViewById(R.id.aduserNameInf);
        userAgeInf= findViewById(R.id.aduserAgeInf);
        userGenderInf= findViewById(R.id.aduserGenderInf);
        userEmailInf= findViewById(R.id.aduserEmailInf);
        userPasswordInf= findViewById(R.id.aduserPasswordInf);
        userPasswordConfirmInf= findViewById(R.id.aduserPasswordConfirmInf);
        current= Common.currentUser;
        userPhoneInf.setText(current.getPhone());
        userNameInf.setText(current.getName());
        userAgeInf.setText(current.getAge());
        userGenderInf.setText(Common.currentUser.getGender());
        userEmailInf.setText(current.getEmail());
        userPasswordInf.setText(current.getPassword());
        userPasswordConfirmInf.setText(current.getPassword());


    }
}