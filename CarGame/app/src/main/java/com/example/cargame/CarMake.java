package com.example.cargame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CarMake extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = CarMake.class.getSimpleName();
    private TypedArray images;
    private ImageView imageView;
    private Button change;
    Spinner spinner;
    private String[] names;
    private static int imageIndex;
    private TextView showAnswer;
    private TextView results;
    private String item;
    private String buttonText;
    Boolean checkOn;
    TextView count_view;
    Switch timer;
    private CountDownTimer countDownTimer;
    private static long millis = 20000;
    private static ArrayList<Integer> randomNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        imageView = findViewById(R.id.randomImg);
        change = findViewById(R.id.change);
        images = getResources().obtainTypedArray(R.array.images);
        names = getResources().getStringArray(R.array.names);
        showAnswer = findViewById(R.id.answer);
        results = findViewById(R.id.getName);
        timer = findViewById(R.id.timer);
        count_view = findViewById(R.id.countView);


        Intent intent = getIntent();
        checkOn = intent.getBooleanExtra(MainActivity.TIMER_MESSAGE, false);
        Log.d(LOG_TAG , "checkOn :" + checkOn);

        if (checkOn){
            launchTimer();
            count_view.setVisibility(View.VISIBLE);
        }
        randomImage();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(item);
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CarMake.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array
                .names));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (spinner != null){
            spinner.setOnItemSelectedListener(this);
        }

        if (savedInstanceState != null){
            randomNumbers = savedInstanceState.getIntegerArrayList("Array");
            imageIndex = savedInstanceState.getInt("randomNumber");

            if (!(randomNumbers.contains(imageIndex))) {
                randomNumbers.add(imageIndex);
            }
            imageView.setImageResource(images.getResourceId(imageIndex, 1));

            if (checkOn){
                millis = savedInstanceState.getLong("millis");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("randomNumber", imageIndex);
        if (!(randomNumbers.contains(imageIndex))) {
            randomNumbers.add(imageIndex);
        }
        outState.putIntegerArrayList("Array", randomNumbers);
        imageView.setImageResource(images.getResourceId(imageIndex, 1));

        if (checkOn){
            outState.putLong("millis", millis);
        }
    }

    @SuppressLint("SetTextI18n")
    private void chooseImage(String item) {

        int index = Arrays.asList(names).indexOf(item);
        String correctImage = Arrays.asList(names).get(imageIndex);
        buttonText = change.getText().toString().toLowerCase();

        if (buttonText.equals("identify")){
            change.setText("Next");

            if (index == imageIndex) {
                showAnswer.setText("Correct");
                showAnswer.setTextColor(Color.GREEN);
                results.setText("");
                Log.d(LOG_TAG, "Image index " + imageIndex);
                if (checkOn) {
                    countDownTimer.cancel();
                }
                count_view.setText("00");
            }
            else {
                showAnswer.setText("Wrong! ");
                showAnswer.setTextColor(Color.RED);
                results.setText(correctImage);
                results.setTextColor(Color.YELLOW);
                if (checkOn) {
                    countDownTimer.cancel();
                }
                count_view.setText("00");
            }
        }
        if(buttonText.equals("next")){
            Intent intent = getIntent();
            finish();
            startActivity(intent);

            change.setText("Identify");
            showAnswer.setText("");
        }
        Log.d(LOG_TAG, "index " + index);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d(LOG_TAG, "Start DropDown");
        item = parent.getItemAtPosition(position).toString();
        Log.d(LOG_TAG, "End DropDown");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        showAnswer.setText("Select an answer!");
    }

    public void randomImage(){
        Random random = new Random();

        if (randomNumbers.size() == 30){
            change.setText("Congratulations! You have Completed all the tasks..");
            showAnswer.setVisibility(View.INVISIBLE);
            count_view.setVisibility(View.INVISIBLE);
            results.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            change.setEnabled(false);
        }
        else {
            do {
                imageIndex = random.nextInt(30);

            } while (randomNumbers.contains(imageIndex));

//            randomNumbers.add(imageIndex);
            imageView.setImageResource(images.getResourceId(imageIndex, 1));

            System.out.println(randomNumbers);
            Log.d(LOG_TAG, String.valueOf(imageIndex));
        }
    }

    public void launchTimer() {
        countDownTimer = new CountDownTimer(millis,100){

            @Override
            public void onTick(long seconds) {
                count_view.setText(TimeUnit.MILLISECONDS.toSeconds(seconds) + " Seconds Left!");
            }

            @Override
            public void onFinish() {
                count_view.setText("Finished!");
                chooseImage(item);
            }
        }.start();
    }
}