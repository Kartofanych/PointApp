package com.example.pointapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointapp.classes.ActPass;
import com.example.pointapp.classes.Coord;
import com.example.pointapp.classes.GetWeather;
import com.example.pointapp.classes.MeetingsForDay;
import com.example.pointapp.classes.PostInf;
import com.example.pointapp.classes.Stats;
import com.example.pointapp.classes.Token;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;


public class DayTimeTable extends AppCompatActivity implements StepperFormListener {

    private LayoutInflater infl;
    private LinearLayout cont;
    private RelativeLayout tool;
    private String date;
    private View bSheet;
    private ImageView _yam;
    private BottomSheetBehavior SmallSheet;
    private UserNameStep userNameStep, userNameStep1, userNameStep2, userNameStep3;
    private ImageView account, weather;
    private TextView gradus, _date, men_name;
    private ImageView back, id_weather;
    private ConstraintLayout weath;
    private ArrayList<UserNameStep> userNameSteps = new ArrayList<>();
    private RelativeLayout darkBack;
    private SwitchCompat switchCompat;
    private int one = 0;
    private Animation alpha, no_alpha;
    private int size = 0;
    private SharedPreferences prefs;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SURNAME = "Surname";
    public static final String APP_PREFERENCES_TOKEN = "Token";
    public static final String APP_PREFERENCES_ACTIVE = "Active";
    private VerticalStepperFormView formView;

    private TextView km, kol;

    private ArrayList<Coord> coordinates = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_time_table);


        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        infl = getLayoutInflater();

        alpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
        no_alpha = AnimationUtils.loadAnimation(this,R.anim.alpha_to_zero);
        darkBack = findViewById(R.id.dark_back);
        tool = findViewById(R.id.tool);
        cont = findViewById(R.id.container);
        bSheet = findViewById(R.id.bottom_sheet);
        _yam = findViewById(R.id.yam);
        account = findViewById(R.id.account);
        weather = findViewById(R.id.weather);
        gradus = findViewById(R.id.gradus);
        back = findViewById(R.id.back);
        weath = findViewById(R.id.weather_window);
        _date = findViewById(R.id.date);
        id_weather = findViewById(R.id.id_weather);
        switchCompat = findViewById(R.id.switcher);
        km = findViewById(R.id.marsh_txt);
        kol = findViewById(R.id.kol_txt);
        men_name = findViewById(R.id.men_name);

        men_name.setText(prefs.getString(APP_PREFERENCES_SURNAME,"Surname") + " " + prefs.getString(APP_PREFERENCES_NAME, "Name"));

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            date = extras.getString("Month") + " " + extras.getString("Date") + ", " + extras.getString("Year");
            size = extras.getInt("size");
        }
        _date.setText(date);
        @ColorInt int[] colors = new int[3];
        colors[0] = Color.parseColor("#FFFFFFFF");
        colors[1] = Color.parseColor("#FFFFFFFF");
        colors[2] = Color.parseColor("#32FFFFFF");

        float[] floats = new float[3];
        floats[0] = 0;
        floats[1] = 0.5f;
        floats[2] = 1;
        Shader shader = new LinearGradient(0,0,0,gradus.getLineHeight(),
                colors, floats,Shader.TileMode.REPEAT);
        gradus.getPaint().setShader(shader);

        formView =  findViewById(R.id.vertical_stepper_form);
        userNameStep = new UserNameStep("15:30", "Проспект Шинников, 44", "Карим А.", "Насыбуллин", "19:30", 1f,1f,"89196353800","1");
        userNameStep1 = new UserNameStep("17:10", "Проспект Вахитова, 31", "Коротков Данил И.", "17:10", "hard", 1f,1f,"89196353800","1");
        userNameStep2 = new UserNameStep("18:20", "Проспект Строителей, 54", "Галиев Ирек И.", "18:20","hard", 1f,1f,"89196353800","1");
        userNameStep3 = new UserNameStep("19:45", "Проспект Победы, 25", "Васнецов Игорь Д.", "19:45","hard", 1f,1f,"89196353800","1");


        if(prefs.getString(APP_PREFERENCES_ACTIVE,"Active").equals("Active")){
            switchCompat.setChecked(true);
        }else{
            switchCompat.setChecked(false);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //////////////////////////////
                SharedPreferences.Editor editor = prefs.edit();
                if(b){
                    CallState(1);
                    editor.putString(APP_PREFERENCES_ACTIVE, "Active");
                }else{
                    CallState(0);
                    editor.putString(APP_PREFERENCES_ACTIVE, "Passive");
                }
                editor.apply();
            }
        });

        SmallSheet = BottomSheetBehavior.from(bSheet);

        SmallSheet.setHideable(true);
        SmallSheet.setSkipCollapsed(true);
        SmallSheet.setState(BottomSheetBehavior.STATE_HIDDEN);



        SmallSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                   if(newState == BottomSheetBehavior.STATE_HIDDEN){
                       _yam.setClickable(true);
                       account.setClickable(true);
                       weather.setClickable(true);
                       formView.setClickable(true);
                    }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    darkBack.getBackground().setAlpha((int)(255 * slideOffset));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmallSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(one == 0) {
                    TurnBackOff(0);
                    one = 1;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(one == 0) {
                    weath.setVisibility(View.GONE);

                    TurnBackOn(1);
                    one = 1;
                }
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(one == 0) {
                    weath.setVisibility(View.VISIBLE);
                    TurnBackOff(1);
                    one = 1;
                }
            }
        });
        darkBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(one == 0) {
                        if(SmallSheet.getState() == BottomSheetBehavior.STATE_HIDDEN){
                        TurnBackOn(1);
                        one = 1;
                    }
                }
                return true;
            }
        });


        GetInfo();
        GetWeather();
        GetStats();
        //GetOfflineInfo();
        TurnBackOff(1);
    }

    private void CallState(int state){
        ActPass actPass = new ActPass(prefs.getString(APP_PREFERENCES_TOKEN, "Token"), String.valueOf(state));
        Call<String> getStatus = ApiClient.getUserService().setStatus(actPass);
        getStatus.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("1","Good");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void GetWeather(){
        Call<GetWeather> getWeatherCall = ApiClient.getUserService().getWeather();
        getWeatherCall.enqueue(new Callback<GetWeather>() {
            @Override
            public void onResponse(Call<GetWeather> call, Response<GetWeather> response) {
                if(response.isSuccessful()){
                    GetWeather getWeather = response.body();
                    ChangeWeather(getWeather.getId(), getWeather.getTemp());
                }
            }

            @Override
            public void onFailure(Call<GetWeather> call, Throwable t) {

            }
        });

    }
    private void ChangeWeather(String weather_id, String temp){
        if(Integer.parseInt(weather_id) < 300) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.rain_pain));
            //Thunderstorm
        } else if (Integer.parseInt(weather_id) < 500) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.rain_sun_cloud));
            //Drizzle
        } else if (Integer.parseInt(weather_id) < 600) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.rain));
            //Rain
        } else if (Integer.parseInt(weather_id) < 700) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.snow));
            //Snow
        } else if (Integer.parseInt(weather_id) < 800) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.fog));
            //Atmosphere
        } else if (Integer.parseInt(weather_id) < 801) {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.sun));
            //Clear
        } else {
            id_weather.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
        }
        gradus.setText((int)Float.parseFloat(temp) + "°");
    }

    private void GetOfflineInfo(){
        formView.setVisibility(View.VISIBLE);
        formView.setup(this, userNameStep, userNameStep1, userNameStep2, userNameStep3)
                .displayStepButtons(false)
                //.basicColorScheme(Color.parseColor("#000000"), Color.parseColor("#000000"), Color.parseColor("#FFFFFF"))
                .includeConfirmationStep(false)
                .displayBottomNavigation(false)
                .displayDifferentBackgroundColorOnDisabledElements(false)
                .alphaOfDisabledElements(1)
                .init();
    }

    private void GetInfo(){

        int j = (int) (new Date().getTime()/1000);
        PostInf postInf = new PostInf(prefs.getString(APP_PREFERENCES_TOKEN, "Token"), j);
        Call<MeetingsForDay> meetings = ApiClient.getUserService().dayMeets(postInf);
        meetings.enqueue(new Callback<MeetingsForDay>() {
            @Override
            public void onResponse(Call<MeetingsForDay> call, Response<MeetingsForDay> response) {
                if(response.isSuccessful()){

                    Log.d("1", response.body().toString());
                    MeetingsForDay meetingsForDay = response.body();
                    if(meetingsForDay.getMeetings()!=null) {
                        size = meetingsForDay.getMeetings().size();
                        for (int i = 0; i < meetingsForDay.getMeetings().size(); i++) {
                            UserNameStep h = new UserNameStep(meetingsForDay.getMeetings().get(i).getTime(),
                                    meetingsForDay.getMeetings().get(i).getAddress(),
                                    meetingsForDay.getMeetings().get(i).getName(),
                                    meetingsForDay.getMeetings().get(i).getSurname(),
                                    "Встреча в: " + meetingsForDay.getMeetings().get(i).getTime(),
                                    meetingsForDay.getMeetings().get(i).getLongitude(),
                                    meetingsForDay.getMeetings().get(i).getLatitude(), meetingsForDay.getMeetings().get(i).getNumber(),
                                    "nice");
                            h.markAsCompleted(true);
                            userNameSteps.add(i, h);
                            Coord coord = new Coord(
                                    meetingsForDay.getMeetings().get(i).getLongitude(),
                                    meetingsForDay.getMeetings().get(i).getLatitude()
                            );
                            coordinates.add(coord);
                        }
                        Log.d("1", String.valueOf(size));
                        hey(size);
                        fullMap();
                    }
                }else{
                    Toast.makeText(DayTimeTable.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeetingsForDay> call, Throwable t) {
                Toast.makeText(DayTimeTable.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hey(int size){
        if(size == 0){
            formView.setVisibility(View.GONE);
        }else{
            formView.setVisibility(View.VISIBLE);
        }

        if(size == 1) {
            formView.setup(this, userNameSteps.get(0))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 2){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 3){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 4){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 5){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3), userNameSteps.get(4))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 6){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3), userNameSteps.get(4),
                    userNameSteps.get(5))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 7){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3), userNameSteps.get(4),
                    userNameSteps.get(5), userNameSteps.get(6))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size == 8){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3), userNameSteps.get(4),
                    userNameSteps.get(5), userNameSteps.get(6), userNameSteps.get(7))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }else if(size >= 9){
            formView.setup(this, userNameSteps.get(0), userNameSteps.get(1), userNameSteps.get(2), userNameSteps.get(3), userNameSteps.get(4),
                    userNameSteps.get(5), userNameSteps.get(6), userNameSteps.get(7),userNameSteps.get(8))
                    .displayStepButtons(false)
                    .includeConfirmationStep(false)
                    .displayBottomNavigation(false)
                    .displayDifferentBackgroundColorOnDisabledElements(false)
                    .alphaOfDisabledElements(1)
                    .init();
        }
    }

    private void fullMap(){
        if(coordinates.size()>2) {
            _yam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "yandexnavi://build_route_on_map?" + "lat_from=" + coordinates.get(0).getLatitude() + "&lon_from=" + coordinates.get(0).getLongtitude()
                            + "&lat_to=" + coordinates.get(coordinates.size() - 1).getLatitude() + "&lon_to=" + coordinates.get(coordinates.size() - 1).getLongtitude() + "&";
                    for (int i = 1; i < coordinates.size(); i++) {
                        if (i != coordinates.size() - 1) {
                            int l = i - 1;
                            url += "lat_via_" + l + "=" + coordinates.get(i).getLatitude() + "&lon_via_" + l + "=" + coordinates.get(i).getLongtitude();
                        }
                    }
                    Uri uri = Uri.parse(url);
                    String s = "yandexnavi://build_route_on_map?lat_from=55.75&lon_from=37.58&lat_to=55.75&lon_to=37.64&lat_via_0=55.75&lon_via_0=37.62";
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("ru.yandex.yandexnavi");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    private void GetStats(){
        Token token = new Token(prefs.getString(APP_PREFERENCES_TOKEN, "Token"));
        Call<Stats> getAll = ApiClient.getUserService().getAllStats(token);
        getAll.enqueue(new Callback<Stats>() {
            @Override
            public void onResponse(Call<Stats> call, Response<Stats> response) {
                if(response.isSuccessful()){
                    Stats stats = response.body();
                    km.setText(String.valueOf((int)stats.getPath())+" km");
                    kol.setText(String.valueOf(stats.getCount()));
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {

            }
        });
    }

    private void TurnBackOff(int event){
        Log.d("1", String.valueOf(event));
        _yam.setClickable(false);
        account.setClickable(false);
        weather.setClickable(false);
        formView.setClickable(false);
        darkBack.setVisibility(View.VISIBLE);
        darkBack.getBackground().setAlpha(255);
        darkBack.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                one = 0;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(event == 1) {
            weath.setVisibility(View.VISIBLE);
            weath.startAnimation(alpha);
        }
    }

    private void TurnBackOn(int event){
        Log.d("1", String.valueOf(event));
        _yam.setClickable(true);
        account.setClickable(true);
        weather.setClickable(true);
        formView.setClickable(true);
        darkBack.setVisibility(View.VISIBLE);
        darkBack.startAnimation(no_alpha);
        no_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                one = 0;
                darkBack.setVisibility(View.GONE);
                weath.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(event == 1) {
            weath.startAnimation(no_alpha);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Закончить сессию?");
        alertDialogBuilder.setMessage("Нажмите " + "\"да\"" + " для выхода.")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onCompletedForm() {

    }

    @Override
    public void onCancelledForm() {

    }

}