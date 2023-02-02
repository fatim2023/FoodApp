package com.rajendra.foodapp.api;

import com.rajendra.foodapp.model.FoodData;
import com.rajendra.foodapp.model.Foods;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;

public interface ApiInterface {

    //@GET("1b68a8f21e50b675eeb1")
    @GET("labs-886bc.appspot.com/o/food.json?alt=media&token=92c676d3-d6f5-4450-8cf0-441f71d1cbb8")
    Call<List<FoodData>> getAllData();



    @GET("favorites")
    Call<List<Foods>> getAll(@HeaderMap HashMap<String,String> hashMap);


    // lets make our model class of json data.

}
