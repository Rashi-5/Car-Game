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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IdentifyCarImage extends AppCompatActivity {

    private static final String LOG_TAG = CarMake.class.getSimpleName();
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private static int imageIndex1;
    private static int imageIndex2;
    private static int imageIndex3;
    private TypedArray images;
    private Button start;
    private String[] names;
    private TextView carName;
    private int chosenNumber;
    private static int number;
    private TextView showAnswer;
    private String chosenImage;
    private TextView correct;
    Boolean checkOn;
    TextView count_view;
    Switch timer;
    private CountDownTimer countDownTimer;
    private static ArrayList<Integer> randomNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        imageView1 = findViewById(R.id.randomImg1);
        imageView2 = findViewById(R.id.randomImg2);
        imageView3 = findViewById(R.id.randomImg3);
        start = findViewById(R.id.start);
        images = getResources().obtainTypedArray(R.array.images);
        names = getResources().getStringArray(R.array.names);
        carName = findViewById(R.id.carName);
        showAnswer = findViewById(R.id.showAnswer);
        correct = findViewById(R.id.correct);

        timer = findViewById(R.id.timer);
        count_view = findViewById(R.id.timerView);

        Intent intent = getIntent();
        checkOn = intent.getBooleanExtra(MainActivity.TIMER_MESSAGE, false);
        Log.d(LOG_TAG , "checkOn :" + checkOn);

        if (checkOn){
            launchTimer();
            count_view.setVisibility(View.VISIBLE);
        }
        randomImages();

        if (savedInstanceState != null){
            randomNumbers = savedInstanceState.getIntegerArrayList("Array");
            number = savedInstanceState.getInt("randomNumber");

            randomNumbers.add(number);

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

        outState.putInt("randomNumber", number);
        randomNumbers.add(number);
        outState.putIntegerArrayList("Array", randomNumbers);

        outState.putInt("number1", imageIndex1);
        outState.putInt("number2", imageIndex2);
        outState.putInt("number3", imageIndex3);

        imageView1.setImageResource(images.getResourceId(imageIndex1, 1));
        imageView2.setImageResource(images.getResourceId(imageIndex2, 1));
        imageView3.setImageResource(images.getResourceId(imageIndex3, 1));

    }

    private void randomImages() {

        if (randomNumbers.size() == 30) {
            start.setText("Congratulations! You have Completed all the tasks..");
            start.setEnabled(false);
            count_view.setVisibility(View.INVISIBLE);

        } else {
            showAnswer.setVisibility(View.VISIBLE);
            carName.setVisibility(View.VISIBLE);
            correct.setVisibility(View.INVISIBLE);

            start.setText("Next");
            start.setClickable(false);
            showAnswer.setText("Select correct image!");
            showAnswer.setTextColor(Color.WHITE);
            Random random = new Random();

            imageIndex1 = random.nextInt(30);

            do {
                imageIndex2 = random.nextInt(30);
            } while (imageIndex1 == imageIndex2);

            do {
                imageIndex3 = random.nextInt(30);
            } while ((imageIndex1 == imageIndex3) || (imageIndex2 == imageIndex3));

            imageView1.setImageResource(images.getResourceId(imageIndex1, 1));
            imageView2.setImageResource(images.getResourceId(imageIndex2, 1));
            imageView3.setImageResource(images.getResourceId(imageIndex3, 1));

            String images1 = Arrays.asList(names).get(imageIndex1);
            String images2 = Arrays.asList(names).get(imageIndex2);
            String images3 = Arrays.asList(names).get(imageIndex3);

            String[] chosenImages = {images1, images2, images3};

            chosenNumber = random.nextInt(3);

            chosenImage = Arrays.asList(chosenImages).get(chosenNumber);
            carName.setText(chosenImage);

            number = Arrays.asList(chosenImages).indexOf(chosenImage);
//            randomNumbers.add(number);

            System.out.println(number);
            Log.d(LOG_TAG, String.valueOf(imageIndex1) + imageIndex2 + imageIndex3);
        }
    }

    public void startAction(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void imageClick1(View view) {

        if (chosenNumber == 0){
            showAnswer.setText("Correct!");
            showAnswer.setTextColor(Color.GREEN);
        }
        else {
            showAnswer.setText("Wrong! ");
            correct.setText("Number " + (chosenNumber + 1) + " Is the correct Answer");
            showAnswer.setTextColor(Color.RED);
            correct.setTextColor(Color.YELLOW);
        }
        start.setClickable(true);
        if (checkOn) {
            countDownTimer.cancel();
        }
        count_view.setText("00");
    }

    public void imageClick2(View view) {
        if (chosenNumber == 1){
            showAnswer.setText("Correct!");
            showAnswer.setTextColor(Color.GREEN);
        }
        else {
            showAnswer.setText("Wrong! ");
            correct.setText("Number " + (chosenNumber + 1) + " Is the correct Answer");
            showAnswer.setTextColor(Color.RED);
            correct.setTextColor(Color.YELLOW);
        }
        start.setClickable(true);
        if (checkOn) {
            countDownTimer.cancel();
        }
        count_view.setText("00");
    }

    public void imageClick3(View view) {
        if (chosenNumber == 2){
            showAnswer.setText("Correct!");
            showAnswer.setTextColor(Color.GREEN);
        }
        else {
            showAnswer.setText("Wrong! ");
            correct.setText("Number " + (chosenNumber + 1) + " Is the correct Answer");
            showAnswer.setTextColor(Color.RED);
            correct.setTextColor(Color.YELLOW);
        }
        start.setClickable(true);
        if (checkOn) {
            countDownTimer.cancel();
        }
        count_view.setText("00");
    }

    public void launchTimer() {
        countDownTimer = new CountDownTimer(20000,100){

            @Override
            public void onTick(long seconds) {
                count_view.setText(TimeUnit.MILLISECONDS.toSeconds(seconds) + "");
            }

            @Override
            public void onFinish() {
                count_view.setText("Times Up!");

                String text = showAnswer.getText().toString().toLowerCase();
                if (!(text.equals("correct"))){
                    start.setClickable(true);
                    showAnswer.setText("Number " + (chosenNumber + 1) + " Is the correct Answer");
                    showAnswer.setTextColor(Color.YELLOW);
                }
            }
        }.start();
    }
}