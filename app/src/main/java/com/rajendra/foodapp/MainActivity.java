package com.rajendra.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rajendra.foodapp.adapter.AllFoodsAdapter;
import com.rajendra.foodapp.adapter.AllMenuAdapter;
import com.rajendra.foodapp.adapter.PopularAdapter;
import com.rajendra.foodapp.adapter.RecommendedAdapter;
import com.rajendra.foodapp.model.AllMenu;
import com.rajendra.foodapp.model.FoodData;
import com.rajendra.foodapp.model.Foods;
import com.rajendra.foodapp.model.Order;
import com.rajendra.foodapp.model.Popular;
import com.rajendra.foodapp.model.Recommended;
import com.rajendra.foodapp.api.ApiInterface;
import com.rajendra.foodapp.api.RetrofitClient;
import com.rajendra.foodapp.model.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    RecyclerView popularRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user=new UserData(this).gtUserName();
        mode=getIntent().getStringExtra("mode");
        if(mode.equals("all")){
            apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("X-RapidAPI-Key","958ace7befmsh93056095b036438p1b5dbfjsn536bae185589");
            hashMap.put("X-RapidAPI-Host","favoritefoodapi.p.rapidapi.com");
            apiInterface.getAll(hashMap).enqueue(new Callback<List<Foods>>() {
                @Override
                public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                    if(response.code()==200){
                        for (Foods foods : response.body()) {
                            Log.d("SSSS", "onResponse: "+foods.getFavoriteDish());

                        }
                        getAllFoods(response.body());

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<List<Foods>> call, Throwable t) {

                }
            });
        }else if(mode.equals("fav")){
            FirebaseFirestore.getInstance().collection(user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if(task.isSuccessful()){
                       List<Foods> list=task.getResult().toObjects(Foods.class);
                       getAllFoods(list);
                   }
                }
            });
        }else{
            FirebaseFirestore.getInstance().collection(user+"_"+"orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        List<Order> list=task.getResult().toObjects(Order.class);
                        List<Foods> foods=new ArrayList<>();
                        for (Order order:list){
                            foods.add(order.getFoods());
                        }
                        getAllFoods(foods);
                    }
                }
            });
        }



//        Call<List<FoodData>> call = apiInterface.getAllData();
//        call.enqueue(new Callback<List<FoodData>>() {
//            @Override
//            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
//
//                List<FoodData> foodDataList = response.body();
//                if(foodDataList!=null){
//                    if(!foodDataList.isEmpty()){
//                        getPopularData(foodDataList.get(0).getPopular());
//
//                        getRecommendedData(foodDataList.get(0).getRecommended());
//
//                        getAllMenu(foodDataList.get(0).getAllmenu());
//                    }else{
//                        Log.d("SSSS", "onResponse: "+foodDataList.size());
//                    }
//
//                }else{
//                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                // lets run it.
//                // we have fetched data from server.
//                // now we have to show data in app using recycler view
//                // lets make recycler view adapter
//                // we have setup and bind popular section
//                // in a same way we add recommended and all menu items
//                // we add two adapter class for allmenu and recommended items.
//                // so lets do it fast.
//
//            }
//
//            @Override
//            public void onFailure(Call<List<FoodData>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
//            }
//        });




    }

    private void  getPopularData(List<Popular> popularList){
        Log.d("SSSS", "getPopularData: "+popularList.size());

        popularRecyclerView = findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }
    private void  getAllFoods(List<Foods> list){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        AllFoodsAdapter adapter = new AllFoodsAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void  getAllMenu(List<AllMenu> allmenuList){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }
    // today w are going to make a food app like zomato and swiggy.
    // we have 3 category in data
    // popular items, recommended items and all menu,
    // lets have have a look on json data.
    // so lets start coding.
    // lets add retrofit dependency in gradle file for network call.
    // we have setup model class and adapter class
    // now we are going to setup data in recyclerview.
    // complited all recyclerview
    // now we will setup on click listener on items.
    // tutorial complited see you in the next video.
}
