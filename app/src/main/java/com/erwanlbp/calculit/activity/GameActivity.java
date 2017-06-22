package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int maxNumber = intent.getIntExtra(GameConfig.CONFIG_MAX_NUMBER, GameConfig.CONFIG_DEFAULT_MAX_NUMBER);
        int timeToPrint = intent.getIntExtra(GameConfig.CONFIG_TIME_TO_PRINT, GameConfig.CONFIG_DEFAULT_TIME_TO_PRINT);
        List<Integer> numbers = intent.getIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS);

        TextView tvMaxNumber = (TextView) findViewById(R.id.tvMaxNumber);
        tvMaxNumber.setText(String.valueOf(maxNumber));

        TextView tvTimeToPrint = (TextView) findViewById(R.id.tvTimeToPrint);
        tvTimeToPrint.setText(String.valueOf(timeToPrint));

        TextView tvNumbers = (TextView) findViewById(R.id.tvNumbers);
        String numbersStr = "";
        for (Integer i : numbers) {
            numbersStr += i+" ";
        }
        tvNumbers.setText(numbersStr);
    }


}
