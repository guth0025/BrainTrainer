package thisnthat.braintrainer;

import android.nfc.Tag;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    int sum;//Sum of math equation

    int positive;//Correct Answers

    int negative;//Wrong Answers

    TextView timerView;

    TextView question;

    TextView correctAnswer;

    TextView score;

    RelativeLayout layout;

    int timeLeft;

    boolean gameIsOn;

    CountDownTimer timer;

    Button retry;

    GridLayout gridLayout;

    Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        question = (TextView)findViewById(R.id.question);

        correctAnswer = (TextView)findViewById(R.id.correct_answer);

        score = (TextView)findViewById(R.id.points);

        timerView = (TextView)findViewById(R.id.timer);

        layout = (RelativeLayout)findViewById(R.id.layout);

        retry = (Button)findViewById(R.id.retry_button);

        gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        quit = (Button)findViewById(R.id.quit);

        timeLeft = 30;

        gameIsOn = true;

        sum = setQuestion();

        setAnswers();

       timer =  new CountDownTimer(31000,1000)//Game timer
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timerView.setText(String.valueOf(millisUntilFinished/1000+"s"));

                timeLeft--;

            }

            @Override
            public void onFinish()
            {

                gridLayout.setVisibility(View.INVISIBLE);

                correctAnswer.setText("Final Score \n"+positive+"/"+negative);

                timerView.setText("0");

                gameIsOn = false;

                quit.animate().alpha(1).setDuration(1000);

                retry.animate().alpha(1).setDuration(1000);

                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retry.animate().alpha(0);
                        //finish();
                        System.exit(0);
                    }
                });

                retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        gridLayout.setVisibility(View.VISIBLE);

                        retry.animate().alpha(0);

                        timeLeft = 30;

                        setQuestion();

                        setAnswers();

                        positive = 0;

                        negative = 0;

                        setScore(0,0);

                        gameIsOn = true;

                        startTimer();
                    }
                });



            }


        }.start();




    }


    public void startTimer()
    {

        timer.cancel();
        timer.start();


    }


    public void checkAnswer(View view)
    {

        Button thisButton = (Button)findViewById(view.getId());

                if(Integer.parseInt(thisButton.getText().toString()) == sum && gameIsOn)
                {

                    correctAnswer.setText("Correct!");

                    setScore(1,0);

                    sum = setQuestion();

                    setAnswers();

                }

                else if(gameIsOn)
                {

                    correctAnswer.setText("Wrong!");

                    setScore(0,1);

                    sum = setQuestion();

                    setAnswers();

                }



    }

    public int setQuestion()//Grabs random number from 1-20
    {


        Random rand = new Random();



        int a = rand.nextInt(20)+1;

        int b = rand.nextInt(20)+1;

        question.setText(Integer.toString(a)+"+"+Integer.toString(b));

        return a+b;

    }

    public void setAnswers()//Adds random number from 1-40
    {
        ArrayList<Integer> answers = new ArrayList<>();

        Button answer1 = (Button)findViewById(R.id.answer1);
        Button answer2 = (Button)findViewById(R.id.answer2);
        Button answer3 = (Button)findViewById(R.id.answer3);
        Button answer4 = (Button)findViewById(R.id.answer4);



        Random rand = new Random();

        answers.add(sum);
        answers.add(rand.nextInt(40)+1);
        answers.add(rand.nextInt(40)+1);
        answers.add(rand.nextInt(40)+1);

        for(int i = 1;i<answers.size();i++)
        {
            if(answers.get(i) == sum)//If any answers are duplicate of correct answer add random number to it
            {
                int temp = answers.get(i);
                temp = temp + rand.nextInt(4)+1;
                answers.set(i,temp);
            }
        }


        Collections.shuffle(answers);

        answer1.setText(answers.get(0).toString());
        answer2.setText(answers.get(1).toString());
        answer3.setText(answers.get(2).toString());
        answer4.setText(answers.get(3).toString());


    }

    public void setScore(int positive,int negative)
    {


        this.positive +=positive;

        this.negative += negative;

        score.setText(this.positive+"/"+this.negative);



    }






}

