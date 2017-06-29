package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.erwanlbp.calculit.ActivityCode;
import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.activity.Enum.Difficulty;

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

        if(this.gameConfig == null)
            this.gameConfig = new GameConfig();

        Map<String, Integer> mapGameConfig = this.gameConfig.getParamsMap();
        ArrayList<Integer> numbers = this.gameConfig.getNumbers();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS, numbers);

        for (Map.Entry<String, Integer> entry : mapGameConfig.entrySet()) {
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
                int answer = data.getIntExtra(AnswerActivity.USER_ANSWER, 0);
                if (this.gameConfig.isCorrectResult(answer)) {
                    findViewById(R.id.tvAppName).setBackgroundColor(Color.GREEN);
                } else {
                    findViewById(R.id.tvAppName).setBackgroundColor(Color.RED);
                }
            }
        }
        if(requestCode == ActivityCode.SELECT_DIFFICULTY) {
            if(resultCode == RESULT_OK) {
                createGameConfig(data);
            }
        }
    }

    private void startAskAnswer() {
        Intent intent = new Intent(this, AnswerActivity.class);
        startActivityForResult(intent, ActivityCode.ANSWER);
    }

    public void selectDifficultyActivity(View view) {
        final Intent intent = new Intent(this, SelectDifficultyActivity.class);
        startActivityForResult(intent, ActivityCode.SELECT_DIFFICULTY);
    }

    private void createGameConfig(final Intent data) {
        final Difficulty difficulty = Difficulty.parse(data.getIntExtra(SelectDifficultyActivity.DIFFICULTY, Difficulty.EASY.getTimeToPrint()));
        gameConfig = new GameConfig(difficulty);
    }
}
