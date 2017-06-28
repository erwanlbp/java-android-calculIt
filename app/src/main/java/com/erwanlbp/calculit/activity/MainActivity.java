package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.erwanlbp.calculit.ActivityCode;
import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String APPNAME = "com.erwanlbp.calculit";

    private GameConfig gameConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchGame(View view) {
        // TODO Remove when select difficulty is merged
        this.gameConfig = new GameConfig(5, 1500, 10);
        Map<String, Integer> gameConfig = this.gameConfig.getParamsMap();
        ArrayList<Integer> numbers = this.gameConfig.getNumbers();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS, numbers);
        for (Map.Entry<String, Integer> entry : gameConfig.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        startActivityForResult(intent, ActivityCode.GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.GAME) {
            if (resultCode == RESULT_OK)
                startAskAnswer();
        }
        if (requestCode == ActivityCode.ANSWER) {
            if (resultCode == RESULT_OK) {
                int correctResult = gameConfig.getCorrectResult();
                int userAnswer = data.getIntExtra(AnswerActivity.USER_ANSWER, correctResult - 1); // To be sure that the default value is wrong
                startPrintResults(userAnswer, correctResult);
            }
        }
    }

    private void startAskAnswer() {
        Intent intent = new Intent(this, AnswerActivity.class);
        startActivityForResult(intent, ActivityCode.ANSWER);
    }

    private void startPrintResults(int userAnswer, int correctResult) {
        Intent intent = new Intent(this, PrintResultsActivity.class);
        intent.putExtra(AnswerActivity.USER_ANSWER, userAnswer);
        intent.putExtra(GameConfig.CONFIG_CORRECT_RESULT, correctResult);
        startActivityForResult(intent, ActivityCode.SHOW_RESULT);
    }
}
