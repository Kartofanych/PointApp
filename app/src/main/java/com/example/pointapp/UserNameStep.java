package com.example.pointapp;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ernestoyaquello.com.verticalstepperform.Step;

public class UserNameStep extends Step<Boolean> {

    private TextView userName;
    private TextView place;
    private String fTime, fPlace, fName, surname, number;
    private Float _longtitude, _latitude;
    private ImageView map, callBut;


    @Override
    public boolean isCompleted() {
        return super.isCompleted();
    }



    public UserNameStep(String _time, String _place, String _name, String _surname, String title, Float longitude, Float latitude, String num, String subtitle) {
        this(title, "");
        this.fTime = _time;
        this.fPlace = _place;
        this.fName = _name;
        this.surname = _surname;
        this._latitude = latitude;
        this._longtitude = longitude;
        this.number = num;
    }

    public UserNameStep(String title, String subtitle) {
        super(title, subtitle);
    }

    @NonNull
    @Override
    public View createStepContentLayout() {

        // We create this step view by inflating an XML layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View timeStepContent = inflater.inflate(R.layout.step_lay, null, false);
        updatedAll(timeStepContent);
        return timeStepContent;
    }



    @Override
    protected void onStepOpened(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    public Boolean getStepData() {
        return true;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return null;
    }

    @Override
    public void restoreStepData(Boolean data) {

    }


    @Override
    protected IsDataValid isStepDataValid(Boolean stepData) {
        return new IsDataValid(true);

    }


    private void updatedAll(View v) {

        place = v.findViewById(R.id.place);
        userName = v.findViewById(R.id.name);
        map = v.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("yandexnavi://build_route_on_map?lat_to="+ _latitude +"&lon_to="+_longtitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("ru.yandex.yandexnavi");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        callBut = v.findViewById(R.id.call);
        callBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(number);
            }
        });
        userName.setText(surname + " " + fName);
        place.setText(fPlace);
    }

    private void dialContactPhone(final String phoneNumber) {
        getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}