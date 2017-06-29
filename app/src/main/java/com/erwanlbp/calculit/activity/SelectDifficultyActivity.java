package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.activity.Enum.Difficulty;

public class SelectDifficultyActivity extends AppCompatActivity {

    public static final String DIFFICULTY = MainActivity.APPNAME + "difficulty";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    public void chooseDifficulty(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.buttonEasy :
                intent.putExtra(DIFFICULTY, Difficulty.EASY.getTimeToPrint());
                break;
            case R.id.buttonMedium :
                intent.putExtra(DIFFICULTY, Difficulty.MEDIUM.getTimeToPrint());
                break;
            case R.id.buttonHard :
                intent.putExtra(DIFFICULTY, Difficulty.HARD.getTimeToPrint());
                break;
            case R.id.buttonGod :
                intent.putExtra(DIFFICULTY, Difficulty.GOD.getTimeToPrint());
                break;
            default: intent.putExtra(DIFFICULTY, Difficulty.EASY.getTimeToPrint());
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
