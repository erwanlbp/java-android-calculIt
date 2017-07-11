package com.erwanlbp.calculit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.enums.Difficulty;

public class DifficultyActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    public void chooseDifficulty(View view) {
        switch (view.getId()) {
            case R.id.btnDifficultyEasy:
                GameConfig.loadConfig(Difficulty.EASY);
                break;
            case R.id.btnDifficultyMedium:
                GameConfig.loadConfig(Difficulty.MEDIUM);
                break;
            case R.id.btnDifficultyHard:
                GameConfig.loadConfig(Difficulty.HARD);
                break;
            case R.id.btnDifficultyGod:
                GameConfig.loadConfig(Difficulty.GOD);
                break;
            default:
                Log.e(TAG, "Unkonw button ID " + view.getId());
                GameConfig.loadConfig(Difficulty.EASY);
                break;
        }

        setResult(RESULT_OK);
        finish();
    }
}
