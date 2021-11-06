package com.example.pointapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pointapp.classes.PostReg;
import com.example.pointapp.classes.Token;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SURNAME = "Surname";
    public static final String APP_PREFERENCES_TOKEN = "Token";


    private EditText _name, _surname;
    private Button _reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        _name = findViewById(R.id.name);
        _surname = findViewById(R.id.surname);

        _reg = findViewById(R.id.reg);
        _reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegIt();
            }
        });

        _name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    _name.clearFocus();
                    _name.setCursorVisible(false);

                    return true;
                }
                return false;
            }
        });
        _surname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // perform action on key press

                    // hide soft keyboard programmatically
                    view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    // clear focus and hide cursor from edit text
                    _name.clearFocus();
                    _name.setCursorVisible(false);

                    return true;
                }
                return false;
            }
        });
    }
    private void RegIt(){

        PostReg reg = new PostReg(_name.getText().toString(), _surname.getText().toString());

        Call<Token> loginCall = ApiClient.getUserService().allInfo(reg);
        loginCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Вход выполнен!", Toast.LENGTH_SHORT).show();

                    Token token = response.body();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(APP_PREFERENCES_NAME, _name.getText().toString());
                    editor.putString(APP_PREFERENCES_SURNAME, _surname.getText().toString());
                    editor.putString(APP_PREFERENCES_TOKEN, token.getToken());
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, DayTimeTable.class));
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}