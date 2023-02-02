package com.rajendra.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rajendra.foodapp.model.Foods;
import com.rajendra.foodapp.model.Order;
import com.rajendra.foodapp.model.UserData;

public class AddToDeliveryActivity extends AppCompatActivity {
    EditText editText;
    EditText editText1;
    EditText editText2;
    Foods foods;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_delivery);
        editText=findViewById(R.id.editText);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        foods=(Foods) getIntent().getSerializableExtra("data");
        userName=new UserData(this).gtUserName();
    }

    public void add(View view) {
        String  name=editText.getText().toString().trim();
        String  phone=editText1.getText().toString().trim();
        String  address=editText2.getText().toString().trim();
        if(name.isEmpty() || phone.isEmpty() || address.isEmpty()){
            Toast.makeText(this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
            return;
        }
        Order order=new Order(name,address,phone,foods);
        FirebaseFirestore.getInstance().collection(userName+"_"+"orders").document(foods.get_id()).set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  Toast.makeText(AddToDeliveryActivity.this, "Order Added", Toast.LENGTH_SHORT).show();
              }
            }
        });


    }
}