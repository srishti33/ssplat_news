package com.example.kinnect.api;

import com.example.kinnect.models.headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<headlines> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    }

