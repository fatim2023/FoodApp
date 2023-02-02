package com.rajendra.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rajendra.foodapp.model.UserData;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText editText;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         editText=findViewById(R.id.editText);
         password=findViewById(R.id.password);
    }

    public void login(View view) {
        final String userName = editText.getText().toString().trim();
        final String password_txt = password.getText().toString().trim();
        if(userName.isEmpty() || password_txt.isEmpty()){
            Toast.makeText(this, "Please Enter All fields", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseFirestore.getInstance().collection("users").
                document(userName).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String pass=task.getResult().get("password",String.class);
                                if (pass.equals(password_txt)) {

                                    UserData userSession=new UserData(LoginActivity.this);
                                    userSession.setUserName(userName);
                                    Intent intent=new Intent(LoginActivity.this,StartActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "User Data Not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("SSSS", "onComplete: "+task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "Error Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signUp(View view) {
        final String userName = editText.getText().toString().trim();
        final String password_txt = password.getText().toString().trim();
        if(userName.isEmpty() || password_txt.isEmpty()){
            Toast.makeText(this, "Please Enter All fields", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseFirestore.getInstance().collection("users").
                document(userName).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Toast.makeText(LoginActivity.this, "User Name Already Found", Toast.LENGTH_SHORT).show();

                            } else {
                                HashMap<String,String> map=new HashMap<>();
                                map.put("userName",userName);
                                map.put("password",password_txt);

                                FirebaseFirestore.getInstance().collection("users").
                                        document(userName).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   UserData userSession=new UserData(LoginActivity.this);
                                                   userSession.setUserName(userName);
                                                   Intent intent=new Intent(LoginActivity.this,StartActivity.class);
                                                   startActivity(intent);
                                                   finish();

                                               }else{
                                                   Toast.makeText(LoginActivity.this, "Error While creating account", Toast.LENGTH_SHORT).show();
                                               }
                                            }
                                        });

                            }
                        } else {
                            Log.d("SSSS", "onComplete: "+task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "Error Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}