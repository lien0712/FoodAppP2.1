package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.FoodViewHolderAd;
import com.example.koreanrestaurantapp.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class MenuAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout rootLayout;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolderAd> adapter;
    FloatingActionButton floatingActionButton;
    EditText edtNameFood, edtDescriptionFood, edtPriceFood,edtDiscountFood;
    Button btnSelect,btnUpload;
    Food newFood;
    ImageView imageFood;
    Uri saveUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        database= FirebaseDatabase.getInstance();
        foodList= database.getReference("Food");

        recyclerView=(RecyclerView) findViewById(R.id.ad_recycler_foods);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout=(RelativeLayout) findViewById(R.id.rootLayout);

        floatingActionButton= findViewById(R.id.addfoodfab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFoodDialog();
            }
        });

        if( getIntent()!=null){
            categoryId= getIntent().getStringExtra("CategoryId");
            if(!categoryId.isEmpty()&& categoryId!=null){
                loadListFood(categoryId);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE)){
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        } else if (item.getTitle().equals(Common.DELETE)) {
            deleteCategory(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteCategory(String key) {
        foodList.child(key).removeValue();
        Toast.makeText(this, "Delete this category", Toast.LENGTH_SHORT).show();

    }

    private void showUpdateDialog(String key, Food item) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(MenuAdmin.this);
        alertDialog.setTitle("Update category");

        LayoutInflater inflater= this.getLayoutInflater();
        View update_food= inflater.inflate(R.layout.add_new_food_ad,null);
        edtNameFood=update_food.findViewById(R.id.edtNameFood);
        edtDescriptionFood=update_food.findViewById(R.id.edtDescriptionFood);
        edtPriceFood=update_food.findViewById(R.id.edtPriceFood);
        edtDiscountFood=update_food.findViewById(R.id.edtDiscountFood);
        btnSelect= update_food.findViewById(R.id.selectbtnFood);
        btnUpload= update_food.findViewById(R.id.uploadbtnFood);
        imageFood= update_food.findViewById(R.id.imageFood);

        edtNameFood.setText(item.getName());
        edtDescriptionFood.setText(item.getDescription());
        edtPriceFood.setText(item.getPrice());
        edtDiscountFood.setText(item.getDiscount());
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        alertDialog.setView(update_food);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setName(edtNameFood.getText().toString());
                item.setDescription(edtDescriptionFood.getText().toString());
                item.setPrice(edtPriceFood.getText().toString());
                item.setDiscount(edtDiscountFood.getText().toString());
                foodList.child(key).setValue(item);
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
    private void showAddFoodDialog() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(MenuAdmin.this);
        alertDialog.setTitle("Update category");

        LayoutInflater inflater= this.getLayoutInflater();
        View add_food= inflater.inflate(R.layout.add_new_food_ad,null);
        edtNameFood=add_food.findViewById(R.id.edtNameFood);
        edtDescriptionFood=add_food.findViewById(R.id.edtDescriptionFood);
        edtPriceFood=add_food.findViewById(R.id.edtPriceFood);
        edtDiscountFood=add_food.findViewById(R.id.edtDiscountFood);
        btnSelect= add_food.findViewById(R.id.selectbtnFood);
        btnUpload= add_food.findViewById(R.id.uploadbtnFood);
        imageFood= add_food.findViewById(R.id.imageFood);

        //edtNameCategory.setText(item.getName());
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        alertDialog.setView(add_food);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(newFood !=null){
                    foodList.push().setValue(newFood);
                    Snackbar.make(rootLayout,"New Food"+newFood.getName()+"was added",Snackbar.LENGTH_SHORT).show();
                }
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

    private void changeImage() {
        if (saveUri != null){
            ProgressDialog dialog= new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();

            String imageName= UUID.randomUUID().toString();
            StorageReference imageFolder= storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText( MenuAdmin.this,"Uploaded", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newFood = new Food();
                            newFood.setName(edtNameFood.getText().toString());
                            newFood.setDescription(edtDescriptionFood.getText().toString());
                            newFood.setPrice(edtPriceFood.getText().toString());
                            newFood.setDiscount(edtDiscountFood.getText().toString());
                            newFood.setMenuId(categoryId);
                            newFood.setImage(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(MenuAdmin.this, "Failed to upload", Toast.LENGTH_SHORT).show();
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
            imageFood.setImageURI(saveUri);
            btnSelect.setText("Image Selected");
        }
    }

    private void loadListFood(String categoryId){
        //like Select * from Foods where MenuId=
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolderAd>(
                Food.class,
                R.layout.food_item,
                FoodViewHolderAd.class,
                foodList.orderByChild("menuId").equalTo(categoryId)) {


            @Override
            protected void populateViewHolder(FoodViewHolderAd foodViewHolderAd, Food food, int i) {
                foodViewHolderAd.food_name.setText(food.getName());
                Picasso.with(getBaseContext()).load(food.getImage()).into(foodViewHolderAd.food_image);

                foodViewHolderAd.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent= new Intent(MenuAdmin.this, Ad_food_detail.class);
                        intent.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        //Set Adapte
        recyclerView.setAdapter(adapter);
    }
}