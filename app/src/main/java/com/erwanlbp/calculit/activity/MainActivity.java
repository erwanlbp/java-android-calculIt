package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String APPNAME = "com.erwanlbp.calculit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchGame(View view) {
        GameConfig config = new GameConfig(5, 1000, 10);
        Map<String, Integer> gameConfig = config.getParamsMap();
        ArrayList<Integer> numbers = config.getNumbers();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS, numbers);
        for (Map.Entry<String, Integer> entry : gameConfig.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        startActivityForResult(intent, GameActivity.FINISHED_COUNTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GameActivity.FINISHED_COUNTING) {
            startAskAnswer();
        }
        if (requestCode == AnswerActivity.ASK_USER_ANSWER) {
            System.out.println(">>> Received user answer");
        }

    }

    private void startAskAnswer() {
        Intent intent = new Intent(this, AnswerActivity.class);

        startActivityForResult(intent, AnswerActivity.ASK_USER_ANSWER);
    }
}
