package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.activity.Enum.Difficulty;

public class DifficultyActivity extends AppCompatActivity {

    public static final String DIFFICULTY = MainActivity.APPNAME + "difficulty";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    public void chooseDifficulty(final View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.buttonEasy :
                intent.putExtra(DIFFICULTY, Difficulty.EASY.getValue());
                break;
            case R.id.buttonMedium :
                intent.putExtra(DIFFICULTY, Difficulty.MEDIUM.getValue());
                break;
            case R.id.buttonHard :
                intent.putExtra(DIFFICULTY, Difficulty.HARD.getValue());
                break;
            case R.id.buttonGod :
                intent.putExtra(DIFFICULTY, Difficulty.GOD.getValue());
                break;
            default: intent.putExtra(DIFFICULTY, Difficulty.EASY.getValue());
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
