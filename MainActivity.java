package com.example.petmonster;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ImageView monsterView;
    private TextView monsterText ;

    private ImageButton foodBtn;
    private ImageButton sleepBtn;
    private ImageButton waterBtn;

    private int foodAmt;
    private int waterAmt;
    private int sleepAmt;

    boolean isCounterRunning  = false;

    CountDownTimer timer;

    //healthTotal will be the sum of all indicators, therefore easier to manage monster state
    private int healthTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monsterView = findViewById(R.id.monster_view) ;
        monsterView.setImageResource(R.drawable.mo_happy);

        monsterText = findViewById(R.id.monster_status_text);
        monsterText.setText("Hello Master. Please feed me, quench my thirst, allow me to sleep.");

        foodBtn = findViewById(R.id.btn_add_food);
        sleepBtn = findViewById(R.id.btn_add_sleep);
        waterBtn = findViewById(R.id.btn_add_water);


        //initialize values here, how many days can one survive without...
        waterAmt =4;
        foodAmt =21;
        sleepAmt =11;


        timer = new CountDownTimer(86400000, 5000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                Log.d("timer", "days remaining: " + millisUntilFinished / 1000);

                //don't allow amounts to go below zero
                if (foodAmt>=0) foodAmt= foodAmt-1;
                if (waterAmt>=0) waterAmt = waterAmt-1;
                if (sleepAmt>=0) sleepAmt = sleepAmt-1;
                updateMonster();

            }

            public void onFinish() {
                Log.d("timer ", "done!");
                isCounterRunning = false;
            }
        }.start();

        updateMonster();

    }


    public void addFood(View view){
        if (foodAmt < 21) {
            foodAmt = foodAmt + 1;
            updateMonster();
            restartTimer();
            //playSound("playChew");
        }
        else{
            monsterText.setText("I'm FULL! Stop feeding me! ");
        }

    }



    public void addSleep(View view){
        if (sleepAmt < 11) {
            sleepAmt = sleepAmt + 1;
            updateMonster();
            restartTimer();
        }
        else{
            monsterText.setText("I'm awake! I don't wanna sleep! ");
        }

    }
    public void addWater(View view){
        if (waterAmt < 4) {
            waterAmt = waterAmt + 1;
            Log.d("addWater", " " + waterAmt);

            updateMonster();
            restartTimer();
        }
        else{
            monsterText.setText("I'm not thirsty! You'll drown me!");
        }

    }


    public void updateMonster(){


        healthTotal= waterAmt + foodAmt + sleepAmt;
        Log.d("health" , "total " +healthTotal);


        //change the imageView of the monster, to account for emotions

        if(healthTotal >=300 ){

            monsterView.setImageResource(R.drawable.mo_happy);
        }
        else if (healthTotal<=150 && healthTotal >10){

            monsterView.setImageResource(R.drawable.mo_cry);
        }
        else if (foodAmt<=50){

            monsterView.setImageResource(R.drawable.mo_hungry);
        }
        else if(healthTotal<=0){

            monsterView.setImageResource(R.drawable.mo_dead);
            //stop time
            timer.cancel();
            monsterText.setText("I am dead, thanks for nothing. ");

        }


    }

    public void restartTimer(){

        if( !isCounterRunning ){
            isCounterRunning = true;
            timer.start();
        }
        else{
            timer.cancel(); // cancel
            timer.start();  // then restart
        }
    }



}