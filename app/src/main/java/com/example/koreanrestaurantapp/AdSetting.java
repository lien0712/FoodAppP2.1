package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AdSetting extends AppCompatActivity {
    private BottomNavigationView btnavView;
    TextView adInform, adUserAccount, adLogout;
    EditText userPhoneInf,userNameInf,userAgeInf,userGenderInf,userEmailInf,userPasswordInf;
    Button btnAdupdateInf,btnSelect,btnUpload;
    ImageView avata;
    FirebaseDatabase database;
    DatabaseReference user;
    User current;
    Uri saveUri;
    FirebaseStorage storage;
    StorageReference storageReference;
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
        avata= findViewById(R.id.userAdImage);
        avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(current);
            }
        });

        current= Common.currentUser;
        userPhoneInf.setText(current.getPhone());
        userNameInf.setText(current.getName());
        userAgeInf.setText(current.getAge());
        userGenderInf.setText(Common.currentUser.getGender());
        userEmailInf.setText(current.getEmail());
        userPasswordInf.setText(current.getPassword());

        Picasso.with(getBaseContext()).load(current.getImage()).into(avata);

        btnAdupdateInf= findViewById(R.id.btnAdupdateInf);
        btnAdupdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(current.getPhone(),current);

            }
        });

    }

    private void showDialog(User item) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(AdSetting.this);
        alertDialog.setTitle("Update avata");

        LayoutInflater inflater= this.getLayoutInflater();
        View update_avata= inflater.inflate(R.layout.add_new_avata,null);
        avata=update_avata.findViewById(R.id.imageAvata);
        btnSelect=update_avata.findViewById(R.id.selectbtnAva);
        btnUpload=update_avata.findViewById(R.id.uploadbtnAva);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(item);
            }
        });
        alertDialog.setView(update_avata);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //change image in app
                item.setImage(String.valueOf(saveUri));
                user.child(current.getPhone()).setValue(item);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
    private void changeImage(User item) {
        if (saveUri != null){
            ProgressDialog dialog= new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();
            String imageName= UUID.randomUUID().toString();
            storageReference= FirebaseStorage.getInstance().getReference("avata/"+imageName);

            storageReference.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText( AdSetting.this,"Uploaded", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            avata.setImageURI(null);
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    current.setImage(uri.toString());
                                    user.child(current.getPhone()).setValue(item);
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(AdSetting.this, "", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress= (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    dialog.setMessage("Uploaded"+progress+"%");
                }
            });
        }
    }
    private void selectImage() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && data!=null && data.getData()!=null){
            saveUri= data.getData();
            avata.setImageURI(saveUri);
            btnSelect.setText("Image Selected");
        }
    }
    private void updateInfo(String key,User item) {
        current.setAge(userAgeInf.getText().toString());
        current.setGender(userGenderInf.getText().toString());
        current.setPassword(userPasswordInf.getText().toString());

//        if (current.getPassword().toString().equals(userPasswordConfirmInf.getText().toString())){
//            user.child(current.getPhone()).setValue(current);
//        }
        user.child(key).setValue(item);
        Intent intent= new Intent(AdSetting.this,SignIn.class);
        startActivity(intent);
    }
}