package com.example.koreanrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInform extends AppCompatActivity {

    ImageView avata;
    EditText userPhoneInf,userNameInf,userAgeInf,userGenderInf,userEmailInf,userPasswordInf,userPasswordConfirmInf;
    Button btnUpdateInf;

    FirebaseDatabase database;
    DatabaseReference user;
    User current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inform);

        database= FirebaseDatabase.getInstance();
        user=database.getReference("User");

        avata= findViewById(R.id.userImage);
        userPhoneInf= findViewById(R.id.userPhoneInf);
        userNameInf= findViewById(R.id.userNameInf);
        userAgeInf= findViewById(R.id.userAgeInf);
        userGenderInf= findViewById(R.id.userGenderInf);
        userEmailInf= findViewById(R.id.userEmailInf);
        userPasswordInf= findViewById(R.id.userPasswordInf);
        userPasswordConfirmInf= findViewById(R.id.userPasswordConfirmInf);
        current=Common.currentUser;
        userPhoneInf.setText(current.getPhone());
        userNameInf.setText(current.getName());
        userAgeInf.setText(current.getAge());
        userGenderInf.setText(Common.currentUser.getGender());
        userEmailInf.setText(current.getEmail());
        userPasswordInf.setText(current.getPassword());
        userPasswordConfirmInf.setText(current.getPassword());

        btnUpdateInf= findViewById(R.id.btnupdateInf);
        btnUpdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(current);

            }
        });


    }

    private void updateInfo(User current) {
        current.setName(userNameInf.getText().toString());
        current.setAge(userAgeInf.getText().toString());
        current.setGender(userGenderInf.getText().toString());
        current.setEmail(userEmailInf.getText().toString());
        current.setPassword(userPasswordInf.getText().toString());
        //current.setName(userNameInf.getText().toString());
        if (current.getPassword().toString().equals(userPasswordConfirmInf.getText().toString())){
            user.child(current.getPhone()).setValue(current);
        }
        Intent intent= new Intent(UserInform.this,SignIn.class);
        startActivity(intent);
    }
}