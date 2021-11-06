package com.example.pointapp;

import android.content.Intent;

import com.example.pointapp.classes.ActPass;
import com.example.pointapp.classes.GetWeather;
import com.example.pointapp.classes.Meeting;
import com.example.pointapp.classes.MeetingsForDay;
import com.example.pointapp.classes.PostInf;
import com.example.pointapp.classes.PostReg;
import com.example.pointapp.classes.Stats;
import com.example.pointapp.classes.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService{

    @POST("api/authorize")
    Call<Token> allInfo(@Body PostReg login);
    @POST("api/getmeetingsforday")
    Call<MeetingsForDay> dayMeets(@Body PostInf postInf);
    @GET("api/getweather")
    Call<GetWeather> getWeather();
    @POST("api/setmanagerstatus")
    Call<String> setStatus(@Body ActPass actPass);
    @POST("api/getmanagerstats")
    Call<Stats> getAllStats(@Body Token token);


}