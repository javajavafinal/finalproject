package com.example.life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int lifeNow = 100;
    private int wrongCount = 0;
    private int rightCount = 0;
    private SeekBar life;
    private Button rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3;
    private TextView lifeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        life = findViewById(R.id.life);
        lifeView = findViewById(R.id.lifeView);
        rightAnswer = findViewById(R.id.rightAnswer);
        wrongAnswer1 = findViewById(R.id.wrongAnswer1);
        wrongAnswer2 = findViewById(R.id.wrongAnswer2);
        wrongAnswer3 = findViewById(R.id.wrongAnswer3);

        life.setProgress(lifeNow);

        rightAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wrongCount = 0;
                rightCount++;
                if(rightCount > 1 && lifeNow <= 97){
                    lifeNow = lifeNow + 3;
                    life.setProgress(lifeNow);
                }
                if(lifeNow < 101 && lifeNow > 0) {
                    lifeView.setText(lifeNow + "/100");
                }

            }
        });
        wrongAnswer1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wrongCount++;
                rightCount = 0;
                if(wrongCount > 0){
                    lifeNow = lifeNow - 3;
                    life.setProgress(lifeNow);
                    if(lifeNow < 0){
                        life.setProgress(0);
                        lifeView.setText("0/100");
                    }
                }
                if(lifeNow < 101 && lifeNow > 0) {
                    lifeView.setText(lifeNow + "/100");
                }
            }
        });
        wrongAnswer2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wrongCount++;
                rightCount = 0;
                if(wrongCount > 0){
                    lifeNow = lifeNow - 3;
                    life.setProgress(lifeNow);
                    if(lifeNow < 0){
                        life.setProgress(0);
                        lifeView.setText("0/100");
                    }
                }
                if(lifeNow < 101 && lifeNow > 0) {
                    lifeView.setText(lifeNow + "/100");
                }
            }
        });
        wrongAnswer3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wrongCount++;
                rightCount = 0;
                if(wrongCount > 0){
                    lifeNow = lifeNow - 3;
                    life.setProgress(lifeNow);
                    if(lifeNow < 0){
                        life.setProgress(0);
                        lifeView.setText("0/100");
                    }
                }
                if(lifeNow < 101 && lifeNow > 0) {
                    lifeView.setText(lifeNow + "/100");
                }
            }
        });
    }
}