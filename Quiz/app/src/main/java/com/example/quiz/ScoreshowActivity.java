package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;

public class ScoreshowActivity extends AppCompatActivity {

    private TextView scoreView;
    private TextView victoryShow;
    private TextView accuracyView;
    private TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreshow);
        scoreView = findViewById(R.id.score);
        victoryShow = findViewById(R.id.victoryShow);
        accuracyView = findViewById(R.id.accuracyView);
        questionView = findViewById(R.id.questionView);
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        String Victory = bundle.getString("Victory");
        double Accuracy = bundle.getDouble("Accuracy");
        int questionCounter = bundle.getInt("Question");
        victoryShow.setText(Victory);
        scoreView.setText("得到了" + score + "分");
        questionView.setText("你答了" + questionCounter + "題");
        accuracyView.setText("正確率為" + new BigDecimal(Accuracy).setScale(2, 1) + "%");

    }
}