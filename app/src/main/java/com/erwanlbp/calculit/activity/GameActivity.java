package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erwanlbp.calculit.activity.config.GameConfig;
import com.erwanlbp.calculit.R;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private List<Integer> numbers;
    private int timeToPrint;
    private int currentNumber;

    private TextView tvNumber;
    private Handler changeNumberTimerHandler;
    private ProgressBar pbRemainingNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        this.timeToPrint = intent.getIntExtra(GameConfig.CONFIG_TIME_TO_PRINT, GameConfig.CONFIG_DEFAULT_TIME_TO_PRINT);

        this.numbers = intent.getIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS);
        this.numbers.add(0, 0);

        this.tvNumber = (TextView) findViewById(R.id.tvNumber);

        this.currentNumber = 0;

        this.changeNumberTimerHandler = new Handler();

        // ----- Progress Bar for the remaining numbers -----
        this.pbRemainingNumbers = (ProgressBar) findViewById(R.id.pbGameNumbers);
        this.pbRemainingNumbers.setMax(this.numbers.size()-1); // Because we added the zero to the numbers list
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
            if (currentNumber >= numbers.size()) {
                stopChangingNumber();
                setResult(RESULT_OK);
                finish();
            } else {
                pbRemainingNumbers.setProgress(currentNumber);
                tvNumber.setText(String.valueOf(numbers.get(currentNumber)));
                tvNumber.setTextColor(currentNumber % 2 == 0 ? Color.GRAY : Color.WHITE);
                currentNumber++;
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
