package com.example.cargame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    public static final int TEXT_REQUEST = 1;
    public static final String TIMER_MESSAGE = "com.example.cargame.timer_MESSAGE";
    Switch timer;
    public static  Boolean time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
    }

    public void launchCarMake(View view) {
        Log.d(LOG_TAG, "Car make launched!");
        Intent intent = new Intent(this, CarMake.class);
        time = timer.isChecked();
        intent.putExtra(TIMER_MESSAGE,time);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void launchHint(View view) {
        Log.d(LOG_TAG, "Hints launched!");
        Intent intent = new Intent(this, Hint.class);

        time = timer.isChecked();
        intent.putExtra(TIMER_MESSAGE,time);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void launchAdvancedLevel(View view) {
        Log.d(LOG_TAG, "Advanced Level Launched!");
        Intent intent = new Intent(this, AdvancedLevel.class);

        time = timer.isChecked();
        intent.putExtra(TIMER_MESSAGE,time);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void launchCarImage(View view) {
        Log.d(LOG_TAG, "Identify Car Imaged Launched!");
        Intent intent = new Intent(this, IdentifyCarImage.class);

        time = timer.isChecked();
        intent.putExtra(TIMER_MESSAGE,time);
        startActivityForResult(intent, TEXT_REQUEST);
    }

}