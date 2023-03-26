package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    int i;
    boolean counterIsActive=false;

    //Getting seekbar
    SeekBar seekBar;

    //Getting textView value
    TextView textView;

    //Countdown timer
    CountDownTimer countDownTimer;

    //Button
    Button button;

    //UpdateTimer function
    public void updateTimer(int i){
        int value=(int)i/60;
        int left=i-(value*60);
        if(left<10)
            textView.setText("0"+Integer.toString(value)+":"+"0"+Integer.toString(left));
        else
            textView.setText("0"+Integer.toString(value)+":"+Integer.toString(left));
        seekBar.setProgress(i);
    }

    //reset timer
    public void resetTimer(){
        textView.setText("00:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        button.setText("START");
        countDownTimer.cancel();
        counterIsActive=false;
    }
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(301);
        seekBar.setMin(1);

        textView = findViewById(R.id.textView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    //Updating button
    @SuppressLint("SetTextI18n")
    public void clickFunction(View view) {
        button=(Button)findViewById(R.id.button2);
        if (counterIsActive) {
            resetTimer();
        }
        else {
            seekBar.setEnabled(false);
            counterIsActive=true;
            button.setText("STOP");
            i = seekBar.getProgress();
            countDownTimer = new CountDownTimer((i * 1000), 1000) {
                @Override
                public void onFinish() {
                    Log.i("info", "Done!!!");
                    MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.airhorn);
                    ring.start();
                    resetTimer();
                }

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                    Log.i("info", "seconds left:" + Long.toString(l / 1000));
                }
            }.start();
        }
    }
}