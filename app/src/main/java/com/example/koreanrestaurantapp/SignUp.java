package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koreanrestaurantapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText edtPhone, edtName, edtPassword,confirmPass,email,gender,age;
    TextView signInfromUp;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone= (EditText) findViewById(R.id.edtPhone);
        edtName= (EditText) findViewById(R.id.edtName);
        edtPassword= (EditText) findViewById(R.id.edtPassword);
        confirmPass=findViewById(R.id.edtPasswordConfirm);
        btnSignup= (Button) findViewById(R.id.btnSignUp);
        signInfromUp=findViewById(R.id.signInFromUp);
        email=findViewById(R.id.edtEmail);
        gender=findViewById(R.id.edtGender);
        age=findViewById(R.id.edtAge);
        signInfromUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUp.this,SignIn.class );
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user= database.getReference("User");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if already user phone
                        if( dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            Toast.makeText(SignUp.this,"Phone number already register", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(!edtPassword.getText().toString().equals(confirmPass.getText().toString())){
                                Toast.makeText(SignUp.this,"Password is not the same with Confirm Password", Toast.LENGTH_SHORT).show();
                            }else {
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), "user", email.getText().toString(), gender.getText().toString(), age.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);

                                Toast.makeText(SignUp.this, "Sign up successfully ", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}