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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class UserInform extends AppCompatActivity {

    ImageView avata;
    TextView userPhoneInf,userNameInf,userEmailInf;
    EditText userAgeInf,userGenderInf,userPasswordInf,userPasswordConfirmInf;
    Button btnUpdateInf,btnSelect,btnUpload;
    Uri saveUri;
    FirebaseDatabase database;
    DatabaseReference user;
    User current;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inform);

        database= FirebaseDatabase.getInstance();
        user=database.getReference("User");
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        avata= findViewById(R.id.userImage);
        avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(current);
            }
        });
        userPhoneInf= findViewById(R.id.userPhoneInf);
        userNameInf= findViewById(R.id.userNameInf);
        userAgeInf= findViewById(R.id.userAgeInf);
        userGenderInf= findViewById(R.id.userGenderInf);
        userEmailInf= findViewById(R.id.userEmailInf);
        userPasswordInf= findViewById(R.id.userPasswordInf);

        current=Common.currentUser;
        userPhoneInf.setText(current.getPhone());
        userNameInf.setText(current.getName());
        userAgeInf.setText(current.getAge());
        userGenderInf.setText(current.getGender());
        userEmailInf.setText(current.getEmail());
        userPasswordInf.setText(current.getPassword());

        Picasso.with(getBaseContext()).load(current.getImage()).into(avata);

        btnUpdateInf= findViewById(R.id.btnupdateInf);
        btnUpdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(current.getPhone(),current);

            }
        });


    }

    private void showDialog(User item) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(UserInform.this);
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
                    Toast.makeText( UserInform.this,"Uploaded", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserInform.this, "", Toast.LENGTH_SHORT).show();
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
        Intent intent= new Intent(UserInform.this,SignIn.class);
        startActivity(intent);
    }
}