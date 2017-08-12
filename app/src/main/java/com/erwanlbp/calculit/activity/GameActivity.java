package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends BaseActivity {
    private int currentNumber;
    private List<Integer> numbers;
    private int timeToPrint;

    private Handler changeNumberTimerHandler;

    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.pbGameNumbers)
    ProgressBar pbRemainingNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        GameConfig.getConfig().nextLevel();

        this.numbers = GameConfig.getConfig().getNumbers();
        this.timeToPrint = GameConfig.getConfig().getTimeToPrint();
        this.currentNumber = 0;

        this.changeNumberTimerHandler = new Handler();

        // ----- Progress Bar for the remaining numbers -----
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Wait for the game to finish...", Toast.LENGTH_SHORT).show();
    }

    private Runnable changeNumberTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentNumber >= numbers.size()) {
                stopChangingNumber();
                Intent intent = new Intent(GameActivity.this, AnswerActivity.class);
                startActivity(intent);
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
