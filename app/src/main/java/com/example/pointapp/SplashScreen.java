package com.example.pointapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.Calendar;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences prefs;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SURNAME = "Surname";
    public static final String APP_PREFERENCES_TOKEN = "Token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(!prefs.getString(APP_PREFERENCES_TOKEN, "Token").equals("Token")){
            Calendar calendar = Calendar.getInstance();
            String currentMonth, currentDay, currentYear;
            currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            currentYear = String.valueOf(calendar.get(Calendar.YEAR));

            Intent intent = new Intent(SplashScreen.this, DayTimeTable.class);
            intent.putExtra("Month", currentMonth);
            intent.putExtra("Date", currentDay);
            intent.putExtra("Year", currentYear);
            CountDownTimer timer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    startActivity(intent);
                }
            };
            timer.start();
        }else{
            CountDownTimer timer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            };
            timer.start();
        }
    }
}