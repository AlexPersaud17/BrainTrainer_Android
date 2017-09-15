package com.alexpersaud.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainMenu;
    RelativeLayout gameBoard;

    Button startAddition;
    Button startSubtraction;
    Button startMultiplication;
    Button startDivision;

    TextView result;
    TextView points;
    TextView question;
    TextView timer;
    String operator;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Button playAgain;

    CountDownTimer countDown;

    ArrayList<Integer> answers = new ArrayList<Integer>();

    int locationOfCorrectAnswer;
    int score = 0;
    int totalQuestions = 0;

    public void startGame(View view){
        operator = view.getTag().toString();
        mainMenu.setVisibility(View.INVISIBLE);
        gameBoard.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgain));
    }

    public void backToMainMenu(View view){
        mainMenu.setVisibility(View.VISIBLE);
        gameBoard.setVisibility(View.INVISIBLE);
        countDown.cancel();
    }

    public void playAgain(View view){
        score = 0;
        totalQuestions = 0;
        timer.setText("30s");
        points.setText("0/0");
        result.setText("");
        playAgain.setVisibility(View.INVISIBLE);

        generateQuestion();

        countDown = new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                timer.setText("0s");
                result.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(totalQuestions));
            }
        }.start();
    }

    public void generateQuestion(){
        Random rand = new Random();
        int x = rand.nextInt(20)+1;
        int y = rand.nextInt(20)+1;
        String operation = "";

        if(operator.equals("+")) {
            operation = "+";
        }else if(operator.equals("-")){
            operation = "-";
        }else if(operator.equals("*")){
            operation = "*";
        }else if(operator.equals("/")){
            operation = "/";
        }

        question.setText(Integer.toString(x) + operation + Integer.toString(y));

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for(int i = 0; i < 4; i++){
            if(i==locationOfCorrectAnswer){
                if(operation == "+"){
                    answers.add(x + y);
                }else if(operation == "-"){
                    answers.add(x - y);
                }else if(operation == "*"){
                    answers.add(x * y);
                }else if(operation == "/"){
                    answers.add(x / y);
                }
            } else {
                if(operation == "+"){
                    incorrectAnswer = rand.nextInt(41);
                    while(incorrectAnswer == x + y){
                        incorrectAnswer = rand.nextInt(41);
                    }
                    answers.add(incorrectAnswer);
                }else if(operation == "-"){
                    incorrectAnswer = rand.nextInt(41)-20;
                    while(incorrectAnswer == x - y){
                        incorrectAnswer = rand.nextInt(41)-20;
                    }
                    answers.add(incorrectAnswer);
                }else if(operation == "*"){
                    incorrectAnswer = rand.nextInt(401);
                    while(incorrectAnswer == x * y){
                        incorrectAnswer = rand.nextInt(401);
                    }
                    answers.add(incorrectAnswer);
                }else if(operation == "/"){
                    incorrectAnswer = rand.nextInt(21);
                    while(incorrectAnswer == x / y){
                        incorrectAnswer = rand.nextInt(21);
                    }
                    answers.add(incorrectAnswer);
                }
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            result.setText("Correct!");
        } else {
            result.setText("Wrong!");
        }
        totalQuestions++;
        points.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

        generateQuestion();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAddition = (Button) findViewById(R.id.addition);
        startSubtraction = (Button) findViewById(R.id.subtraction);
        startMultiplication = (Button) findViewById(R.id.multiplication);
        startDivision = (Button) findViewById(R.id.division);


        question = (TextView) findViewById(R.id.question);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        result = (TextView) findViewById(R.id.result);
        points = (TextView) findViewById(R.id.points);
        timer = (TextView) findViewById(R.id.timer);
        playAgain = (Button) findViewById(R.id.playAgain);

        gameBoard = (RelativeLayout) findViewById(R.id.gameBoard);
        mainMenu = (RelativeLayout) findViewById(R.id.mainMenu);
    }
}
