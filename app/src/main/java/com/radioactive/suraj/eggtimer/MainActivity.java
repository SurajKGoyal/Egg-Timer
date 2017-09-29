package com.radioactive.suraj.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    ImageView eggImageView;
    CountDownTimer countDownTimer;
    boolean counterIsActive = false;

    //this function will update the timer
    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft-minutes*60;

        String secondString = Integer.toString(seconds);
        if(seconds <= 9 ){
            secondString="0"+secondString;
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondString);
    }

    //this function will be caller whenever the control button is pressed
    public void controlTimer(View view) {
        if (counterIsActive == false) {

            controllerButton.setText("Stop");
            counterIsActive = true;
            timerSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    eggImageView.setImageResource(R.drawable.crackedegg);
                    mPlayer.start();
                }
            }.start();
        }else{
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            timerSeekBar.setEnabled(true);
            countDownTimer.cancel();
            counterIsActive=false;
            controllerButton.setText("Go!");
            eggImageView.setImageResource(R.drawable.eggclip);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        eggImageView=(ImageView)findViewById(R.id.eggImageView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

