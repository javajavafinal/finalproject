package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private SeekBar life;
    private TextView lifeView;
    private int score;
    private int lifeNow = 100;
    private int rightCount = 0;
    private int wrongCount = 0;
    private boolean answered;
    private String Victory;
    private double accuracy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuestion = findViewById(R.id.txtQuestion);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtQuestionCount);
        life = findViewById(R.id.life);
        lifeView = findViewById(R.id.lifeView);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.btnconfirmnext);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);
        life.setProgress(lifeNow);
        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if ( rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() ) {
                        checkAnswer();
                    } else {
                        Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {

        rbGroup.clearCheck();
        rb1.setBackgroundColor(Color.WHITE);
        rb2.setBackgroundColor(Color.WHITE);
        rb3.setBackgroundColor(Color.WHITE);
        rb4.setBackgroundColor(Color.WHITE);

        if (questionCounter < questionCountTotal && lifeNow > 0) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        } else {
            finishQuiz();
        }


    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            wrongCount = 0;
            rightCount++;
            textViewScore.setText("Score: " + score);
            if(rightCount > 1 && lifeNow <= 95){
                lifeNow = lifeNow + 5;
                life.setProgress(lifeNow);
            }
            if(lifeNow < 101 && lifeNow > 0) {
                lifeView.setText("Hp:" + lifeNow + "/100");
            }
        }else{
            wrongCount++;
            rightCount = 0;
            if(wrongCount > 0){
                lifeNow = lifeNow - 10;
                life.setProgress(lifeNow);
                if(lifeNow < 0){
                    life.setProgress(0);
                    lifeView.setText("0/100");
                }
            }
            if(lifeNow < 101 && lifeNow > 0) {
                lifeView.setText("Hp:" + lifeNow + "/100");
            }
        }
        showSolution(answerNr, rbSelected);
    }

    private void showSolution(int answerNr, View rbSelected) {

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb1.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("太強了吧");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("差一點點... \n 答案是A");
                }
                break;
            case 2:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb2.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("你是鬼吧");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("差一點點... \n 答案是B");
                }
                break;
            case 3:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb3.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("你猜對的吧");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("差一點點... \n 答案是C");
                }
                break;
            case 4:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb4.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("答對答對");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("差一點點... \n 答案是D");
                }
                break;
        }

        if (lifeNow > 0 && questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }


    private void finishQuiz() {
        if(lifeNow > 0)
        {
            Victory = "恭喜你活下來";
        }else{
            Victory = "遊戲結束";
        }
        Intent intent = new Intent();
        intent.setClass(MainActivity.this , ScoreshowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("Victory", Victory);
        bundle.putDouble("Accuracy", ((double)score/(double)questionCounter)*100);
        bundle.putInt("Question", questionCounter);
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
    }
}