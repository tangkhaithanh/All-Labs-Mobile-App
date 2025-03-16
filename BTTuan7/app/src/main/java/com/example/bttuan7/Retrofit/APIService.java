package com.example.bttuan7.Retrofit;

import android.icu.util.ULocale;

import com.example.bttuan7.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("/app.iotstar.vn/appfoods/categories")
    Call<List<Category>>getAllCategory();
}
