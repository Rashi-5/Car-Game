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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hint extends AppCompatActivity {

    private static ArrayList<Integer> randomNumbers = new ArrayList<>();
    private static int imageIndex;
    private static final String LOG_TAG = CarMake.class.getSimpleName();
    private ImageView imageView;
    private TypedArray images;
    private EditText guess;
    private Button start;
    private TextView hint;
    private TextView lives;
    private Button submit;
    private String[] brand;
    private String carName;
    private int length;
    char[] nameArray;
    private char[] dashes;
    private int life = 3;
    private Boolean checkOn;
    private TextView count_view;
    private String letterInput;
    private Switch timer;
    private final ArrayList<String> foundName = new ArrayList<>(20);
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        imageView = findViewById(R.id.randomImg);
        images = getResources().obtainTypedArray(R.array.images);
        guess = findViewById(R.id.guess);
        start = findViewById(R.id.hintStart);
        hint = findViewById(R.id.hintText);
        lives = findViewById(R.id.lives);
        submit = findViewById(R.id.okay);
        brand = getResources().getStringArray(R.array.brand);
        timer = findViewById(R.id.timer);
        count_view = findViewById(R.id.count_view);

        Intent intent = getIntent();
        checkOn = intent.getBooleanExtra(MainActivity.TIMER_MESSAGE, false);
        Log.d(LOG_TAG , "checkOn :" + checkOn);

        if (checkOn) {
            launchTimer();
            count_view.setVisibility(View.VISIBLE);
        }

        generateImage();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCar();
            }
        });

        if (savedInstanceState != null){
            randomNumbers = savedInstanceState.getIntegerArrayList("Array");
            imageIndex = savedInstanceState.getInt("randomNumber");
            imageView.setImageResource(images.getResourceId(imageIndex, 1));
            randomNumbers.add(imageIndex);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("randomNumber", imageIndex);
        randomNumbers.add(imageIndex);
        outState.putIntegerArrayList("Array", randomNumbers);
        imageView.setImageResource(images.getResourceId(imageIndex, 1));
    }

    public void generateImage() {
        life = 3;
        start.setText("Next");
        submit.setEnabled(true);
        lives.setTextColor(Color.WHITE);
        foundName.clear();

        if (randomNumbers.size() == 14){
            lives.setText("Congratulations! You have Completed all the tasks..");
            start.setEnabled(false);
            count_view.setVisibility(View.INVISIBLE);
        }
        else {
            Random random = new Random();
            do {
                imageIndex = random.nextInt(30);

            } while (randomNumbers.contains(imageIndex));

//            randomNumbers.add(imageIndex);
            imageView.setImageResource(images.getResourceId(imageIndex, 1));

            carName = Arrays.asList(brand).get(imageIndex).toLowerCase();

            length = carName.length();

            dashes = new char[length];

            for (int i = 0; i < length; i++) {
                dashes[i] = ('-');
            }

            hint.setText(String.valueOf(dashes));
            lives.setText("You have 3 lives!");

            nameArray = carName.toCharArray();

            System.out.println(nameArray);
        }
    }

    public void findCar() {
        char letter;
        letterInput = guess.getText().toString().toLowerCase();

        if (letterInput.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter a letter!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            letter = letterInput.charAt(0);

            if (letterInput.length() > 1) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please enter one letter at a time", Toast.LENGTH_LONG);
                toast.show();
            } else {

                if (!(foundName.contains(letterInput))) {
                    if (life != 0) {
                        if (carName.contains(letterInput)) {

                            for (int i = 0; i <= length - 1; i++) {
                                if (carName.charAt(i) == letter) {
                                    dashes[i] = (letter);
                                    hint.setText(String.valueOf(dashes));
                                }
                            }

                            lives.setText("CORRECT!");
                            lives.setTextColor(Color.GREEN);
                            foundName.add(letterInput);

                            if (String.valueOf(dashes).equals(carName)) {
                                submit.setEnabled(false);
                                lives.setText("You Won!");
                                lives.setTextColor(Color.GREEN);
                                if (checkOn) {
                                    countDownTimer.cancel();
                                }
                            }

                            System.out.println(foundName);
                            System.out.println(dashes);

                        } else {
                            life--;
                            lives.setText("WRONG!");
                            lives.setTextColor(Color.RED);

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    life + " lives left!", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    }else {
                        timesUp();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            letterInput + " Already exists!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            guess.setText("");
        }
    }

    public void timesUp(){
        if (life == 0) {
            lives.setText("You Loose!");
            lives.setTextColor(Color.RED);
            submit.setEnabled(false);
            hint.setText(carName);
            hint.setTextColor(Color.YELLOW);
            if (checkOn) {
                countDownTimer.cancel();
            }
            count_view.setText("00");
        }
    }

    public void CheckOn(){

            letterInput = guess.getText().toString().toLowerCase();
            if (letterInput.equals("")) {
                life--;
                Log.d(LOG_TAG, "lives first: " + life);
                lives.setText(life + " Lives left!");
            }
            findCar();
    }

    public void start(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    Boolean got1 = false;
    Boolean got2 = false;

    public void launchTimer() {
        countDownTimer = new CountDownTimer(60000,100){

            @Override
            public void onTick(long seconds) {
                count_view.setText(TimeUnit.MILLISECONDS.toSeconds(seconds) + " Seconds Left!");

                if (!(foundName.size() > 2)) {

                    if ((seconds < 40000) && (!got1)){
                        if (life >= 0) {
                            got1 = true;
                            CheckOn();
                            System.out.println(foundName.size());
                        }
                    }else {
                        timesUp();
                    }
                }

                else if ((seconds < 20000) && !got2){
                    if (life >= 0) {
                        if (!(foundName.size() > 3)) {
                            got2 = true;
                            CheckOn();
                        }
                    }else {
                        life = 0;
                        timesUp();
                    }
                }
            }

            @Override
            public void onFinish() {
                count_view.setText("Times Up!");
                 life = 0;
                 timesUp();
                 findCar();
            }
        }.start();
    }
}
