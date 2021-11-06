package com.example.pointapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class MonthTimeTable extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView name;
    private SharedPreferences prefs;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SURNAME = "Surname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_time_table);
        name = findViewById(R.id.name);
        calendarView = findViewById(R.id.calendar);

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String tool = prefs.getString(APP_PREFERENCES_SURNAME, "Surname") + " " + prefs.getString(APP_PREFERENCES_NAME, "Name").substring(0,1) + ".";
        name.setText(tool);

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setCalendarOrientation(0);
        calendarView.setWeekendDays(new HashSet(){{
            add(Calendar.SUNDAY);
        }});
        calendarView.setSelectionType(SelectionType.SINGLE);

        calendarView.setSelectionManager(new RangeSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                Intent toDay = new Intent(MonthTimeTable.this, DayTimeTable.class);

                Date date1 = calendarView.getSelectedDays().get(0).getCalendar().getTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);

                String currentMonth, currentDay, currentYear;
                currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
                currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                currentYear = String.valueOf(calendar.get(Calendar.YEAR));
                Log.d("1", currentDay + currentMonth+currentYear);
                toDay.putExtra("Date", currentDay);
                toDay.putExtra("Month", currentMonth);
                toDay.putExtra("Year", currentYear);

                new CountDownTimer(200, 200) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        startActivity(toDay);
                    }
                }.start();
            }
        }));
    }
}