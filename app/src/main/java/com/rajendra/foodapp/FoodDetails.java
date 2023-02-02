package com.rajendra.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rajendra.foodapp.model.Foods;
import com.rajendra.foodapp.model.UserData;

public class FoodDetails extends AppCompatActivity {

    // now we will get data from intent and set to UI

    ImageView imageView;
    TextView itemName, itemPrice, itemRating;
    RatingBar ratingBar;

    String name, price, rating, imageUrl,id;
    private int available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        final Intent intent = getIntent();

        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        available = intent.getIntExtra("ava",0);
        imageUrl = intent.getStringExtra("image");
        id = intent.getStringExtra("id");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        TextView textView8 = findViewById(R.id.textView8);
        Button button = findViewById(R.id.button);
        final String userName=new UserData(this).gtUserName();
        final Foods foods= (Foods) getIntent().getSerializableExtra("data");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent1=new Intent(FoodDetails.this,AddToDeliveryActivity.class);
              intent1.putExtra("data",foods);
              startActivity(intent1);
            }
        });

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText("SR "+foods.getPrice());
        textView8.setText("Available: "+String.valueOf(available));
        final ImageView imageView7=findViewById(R.id.imageView7);

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection(userName).document(userName+"_"+foods.get_id())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if (task.getResult().exists()){
                                FirebaseFirestore.getInstance().collection("fav")
                                        .document(userName+"_"+foods.get_id()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    imageView7.setImageResource(R.drawable.ic_like);
                                                    Toast.makeText(FoodDetails.this, "Remove From Favourite", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }else{
                                FirebaseFirestore.getInstance().collection(userName)
                                        .document(userName+"_"+foods.get_id()).set(foods).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    imageView7.setImageResource(R.drawable.ic_like_added);
                                                    Toast.makeText(FoodDetails.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        });


        FirebaseFirestore.getInstance().collection(userName).document(userName+"_"+id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if(task.isSuccessful()){
                  if (task.getResult().exists()){
                      imageView7.setImageResource(R.drawable.ic_like_added);
                  }
              }
            }
        });


    }
}
