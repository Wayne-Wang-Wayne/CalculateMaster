package com.example.calculatemaster;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Random randomNum = new Random();
    int winTime = 0;
    int loseTime = 0;
    int answer;
    int restOfTime = 15000;
    boolean stillHaveTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView timeLeft = findViewById(R.id.timeLeft);
        TextView numPlusNum = findViewById(R.id.numPlusNum);
        TextView playStatus = findViewById(R.id.playStatus);
        Button topLeft = findViewById(R.id.topLeft);
        Button topRight = findViewById(R.id.topRight);
        Button bottomLeft = findViewById(R.id.bottomLeft);
        Button bottomRight = findViewById(R.id.bottomRight);
        Button startPlay = findViewById(R.id.startPlay);
        TextView doneSign = findViewById(R.id.done);

        timeLeft.setText(getString(R.string.time_left_string, 15));
        playStatus.setText(getString(R.string.win_lose_string, 0, 0));

        doneSign.setAlpha(0);

        ArrayList<Button> buttonViews = new ArrayList<>();
        buttonViews.add(topLeft);
        buttonViews.add(topRight);
        buttonViews.add(bottomLeft);
        buttonViews.add(bottomRight);
        MediaPlayer yayay = MediaPlayer.create(this, R.raw.yayayayay);
        MediaPlayer boo = MediaPlayer.create(this, R.raw.sadsadsad);


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (restOfTime > 0) {
                    handler.postDelayed(this, 1000);
                }
                if (restOfTime == 0) {
                    stillHaveTime = false;
                    doneSign.setAlpha(1);
                }
                timeLeft.setText(getString(R.string.time_left_string, restOfTime / 1000));

                restOfTime = restOfTime - 1000;
            }
        };

        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doneSign.setAlpha(0);
                winTime = 0;
                loseTime = 0;
                playStatus.setText(getString(R.string.win_lose_string, winTime, loseTime));
                handler.removeCallbacks(runnable);
                handler.post(runnable);
                resetBoard(buttonViews);
                restOfTime = 15000;
                stillHaveTime = true;

            }

        });

        for (int i = 0; i < 4; i++) {
            Button buttonView = buttonViews.get(i);
            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stillHaveTime) {
                        if (answer == Integer.parseInt(buttonView.getText().toString())) {
                            winTime++;
                            playStatus.setText(getString(R.string.win_lose_string, winTime, loseTime));
                            resetBoard(buttonViews);
                            //聲音
                            yayay.stop();
                            boo.stop();
                            try {
                                yayay.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            yayay.start();

                        } else {
                            loseTime++;
                            playStatus.setText(getString(R.string.win_lose_string, winTime, loseTime));
                            resetBoard(buttonViews);
                            //聲音
                            boo.stop();
                            yayay.stop();
                            try {
                                boo.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            boo.start();
                        }

                    }


                }
            });


        }


    }

    public void resetBoard(ArrayList<Button> buttonViews) {
        TextView numPlusNum = findViewById(R.id.numPlusNum);
        int firstNum = randomNum.nextInt(100);
        int secondNum = randomNum.nextInt(100);
        numPlusNum.setText(firstNum + "+" + secondNum);
        //重置四個答案的數字
        //ＳＥＴ答案位置
        int answerOrder = randomNum.nextInt(buttonViews.size());

        answer = firstNum + secondNum;
        Set<Integer> values = new HashSet<>();
        values.add(answer);

        for (int i = 0; i < 4; i++) {
            if (i == answerOrder) {
                buttonViews.get(i).setText(String.valueOf(answer));
            } else {

                int restOfNumValue = randomNum.nextInt(199);

                while (!values.add(restOfNumValue)) {
                    restOfNumValue = randomNum.nextInt(199);
                }
                buttonViews.get(i).setText(String.valueOf(restOfNumValue));
            }
        }
    }

}