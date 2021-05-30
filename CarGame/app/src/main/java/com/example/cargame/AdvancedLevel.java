package com.example.cargame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AdvancedLevel extends AppCompatActivity {
    private static final String LOG_TAG = CarMake.class.getSimpleName();
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private static int imageIndex1;
    private static int imageIndex2;
    private static int imageIndex3;
    private TypedArray images;
    private String[] manufacturer;
    private Button submit;
    private TextInputEditText input1;
    private TextInputEditText input2;
    private TextInputEditText input3;
    private Boolean correct1 = false;
    private Boolean correct2 = false;
    private Boolean correct3 = false;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private String images1;
    private String images2;
    private String images3;
    private String firstInput;
    private String secondInput;
    private String thirdInput;
    private int points = 0;
    private TextView result;
    private Button start;
    private int count = 0;
    private TextView count_view;
    Switch timer;
    private Boolean checkOn;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        imageView1 = findViewById(R.id.randomImg1);
        imageView2 = findViewById(R.id.randomImg2);
        imageView3 = findViewById(R.id.randomImg3);

        result = findViewById(R.id.correct_view);
        images = getResources().obtainTypedArray(R.array.images);
        manufacturer = getResources().getStringArray(R.array.manufacturer);
        start = findViewById(R.id.advanced);

        input1 = findViewById(R.id.firstText);
        input2 = findViewById(R.id.secondText);
        input3 = findViewById(R.id.thirdText);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);

        timer = findViewById(R.id.timer);
        count_view = findViewById(R.id.countdown);
        submit = findViewById(R.id.submit);

        Intent intent = getIntent();
        checkOn = intent.getBooleanExtra(MainActivity.TIMER_MESSAGE, false);
        Log.d(LOG_TAG , "checkOn :" + checkOn);

        if (checkOn){
            launchTimer();
            count_view.setVisibility(View.VISIBLE);
        }

        generateRandomImages();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });

        if (savedInstanceState != null) {
            imageIndex1 = savedInstanceState.getInt("number1");
            imageIndex2 = savedInstanceState.getInt("number2");
            imageIndex3 = savedInstanceState.getInt("number3");

            imageView1.setImageResource(images.getResourceId(imageIndex1, 1));
            imageView2.setImageResource(images.getResourceId(imageIndex2, 1));
            imageView3.setImageResource(images.getResourceId(imageIndex3, 1));
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("number1", imageIndex1);
        outState.putInt("number2", imageIndex2);
        outState.putInt("number3", imageIndex3);

        imageView1.setImageResource(images.getResourceId(imageIndex1, 1));
        imageView2.setImageResource(images.getResourceId(imageIndex2, 1));
        imageView3.setImageResource(images.getResourceId(imageIndex3, 1));

    }

    public void generateRandomImages() {

        Random random = new Random();

        input1.setTextColor(Color.BLACK);
        input2.setTextColor(Color.BLACK);
        input3.setTextColor(Color.BLACK);

        imageIndex1 = random.nextInt(30);

        do {
            imageIndex2 = random.nextInt(30);
        }while (imageIndex1 == imageIndex2);

        do{
            imageIndex3 = random.nextInt(30);
        }while ((imageIndex1 == imageIndex3) || (imageIndex2 == imageIndex3));

        imageView1.setImageResource(images.getResourceId(imageIndex1, 1));
        imageView2.setImageResource(images.getResourceId(imageIndex2, 1));
        imageView3.setImageResource(images.getResourceId(imageIndex3, 1));

        Log.d(LOG_TAG, String.valueOf(imageIndex1) + imageIndex2 + imageIndex3);
    }

    public void checkAnswers() {

        count++;

        images1 = Arrays.asList(manufacturer).get(imageIndex1).toLowerCase();
        images2 = Arrays.asList(manufacturer).get(imageIndex2).toLowerCase();
        images3 = Arrays.asList(manufacturer).get(imageIndex3).toLowerCase();

        Log.d(LOG_TAG, images1 + images2 + images3);

        firstInput = input1.getText().toString().toLowerCase();
        secondInput = input2.getText().toString().toLowerCase();
        thirdInput = input3.getText().toString().toLowerCase();

            if (firstInput.equals(images1)) {
                correct1 = true;
                input1.setEnabled(false);
                input1.setTextColor(Color.GREEN);
                points++;
            } else {
                input1.setTextColor(Color.RED);
            }
            if (secondInput.equals(images2)) {
                correct2 = true;
                input2.setEnabled(false);
                input2.setTextColor(Color.GREEN);
                points++;
            } else {
                input2.setTextColor(Color.RED);
            }
            if (thirdInput.equals(images3)) {
                correct3 = true;
                input3.setEnabled(false);
                input3.setTextColor(Color.GREEN);
                points++;
            } else {
                input3.setTextColor(Color.RED);
            }

            if (correct1 && correct2 && correct3) {
                result.setText(points + " Points");
                result.setTextColor(Color.GREEN);
                if (checkOn) {
                    countDownTimer.cancel();
                }
            }
            else {
                result.setText(points + " Points");
                result.setTextColor(Color.RED);
            }

        if (count >= 3) {
            result.setText("You Loose!" + points + " Points");
            result.setTextColor(Color.RED);
            checkTime();
        }
    }

    public void checkTime(){

            if (!(firstInput.equals(images1))){
                answer1.setText(images1);
                answer1.setTextColor(Color.YELLOW);
            }
            if (!(secondInput.equals(images1))){
                answer2.setText(images2);
                answer2.setTextColor(Color.YELLOW);
            }
            if (!(thirdInput.equals(images3))){
                answer3.setText(images3);
                answer3.setTextColor(Color.YELLOW);
            }

        if (checkOn) {
            countDownTimer.cancel();
            count_view.setText("00");
        }
    }

    public void resetInput1(View view) {
        input1.setText("");
        start.setText("Next");
    }

    public void resetInput2(View view) {
        input2.setText("");
        start.setText("Next");
    }

    public void resetInput3(View view) {
        input3.setText("");
        start.setText("Next");
    }

    public void StartActivity(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void launchTimer() {
        countDownTimer = new CountDownTimer(60000,100){

            @Override
            public void onTick(long seconds) {
                count_view.setText(TimeUnit.MILLISECONDS.toSeconds(seconds) + " Seconds Left!");
            }

            @Override
            public void onFinish() {
                count_view.setText("Times Up!");
                checkAnswers();
                checkTime();
            }
        }.start();
    }
}