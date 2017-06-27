package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private List<Integer> numbers;
    private int timeToPrint;
    private int curNumber;

    private TextView tvNumber;
    private Handler changeNumberTimerHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        this.timeToPrint = intent.getIntExtra(GameConfig.CONFIG_TIME_TO_PRINT, GameConfig.CONFIG_DEFAULT_TIME_TO_PRINT);

        this.numbers = intent.getIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS);
        this.numbers.add(0, 0);

        this.tvNumber = (TextView) findViewById(R.id.tvNumber);

        this.curNumber = 0;

        this.changeNumberTimerHandler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.startChangingNumbers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.stopChangingNumber();
    }

    private Runnable changeNumberTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (curNumber >= numbers.size()) {
                stopChangingNumber();
                setResult(RESULT_OK);
                finish();
            } else {
                tvNumber.setText(String.valueOf(numbers.get(curNumber)));
                curNumber++;
                changeNumberTimerHandler.postDelayed(changeNumberTimerRunnable, timeToPrint);
            }
        }
    };

    private void startChangingNumbers() {
        this.changeNumberTimerRunnable.run();
    }

    private void stopChangingNumber() {
        this.changeNumberTimerHandler.removeCallbacks(this.changeNumberTimerRunnable);
    }
}
