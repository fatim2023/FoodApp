package com.rajendra.foodapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

   // private static final String BASE_URL = "https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/foodapp/";
    //private static final String BASE_URL = "https://api.npoint.io/";
    //private static final String BASE_URL = "https://firebasestorage.googleapis.com/v0/b/";
    private static final String BASE_URL = "https://favoritefoodapi.p.rapidapi.com/food/api/v1/";

    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }

}
