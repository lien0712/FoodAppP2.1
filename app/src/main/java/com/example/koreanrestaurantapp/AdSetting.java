package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdSetting extends AppCompatActivity {
    private BottomNavigationView btnavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_setting);

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

    }
}