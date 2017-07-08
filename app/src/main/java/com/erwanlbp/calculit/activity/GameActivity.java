package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;

import java.util.List;

public class GameActivity extends BaseActivity {
    private int currentNumber;
    private List<Integer> numbers;
    private int timeToPrint;

    private TextView tvNumber;
    private Handler changeNumberTimerHandler;
    private ProgressBar pbRemainingNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameConfig.getConfig().nextLevel();

        this.numbers = GameConfig.getConfig().getNumbers();
        this.timeToPrint = GameConfig.getConfig().getTimeToPrint();
        this.currentNumber = 0;

        this.tvNumber = (TextView) findViewById(R.id.tvNumber);
        this.changeNumberTimerHandler = new Handler();

        // ----- Progress Bar for the remaining numbers -----
        this.pbRemainingNumbers = (ProgressBar) findViewById(R.id.pbGameNumbers);
        this.pbRemainingNumbers.setMax(this.numbers.size() - 1); // Because we added the zero to the numbers list
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
                Intent intent = new Intent(GameActivity.this, AnswerActivity.class);
                startActivityForResult(intent, RQ_ANSWER);
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
