package com.erwanlbp.calculit.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.enums.Difficulty;

public class DifficultyActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_difficulty));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Toast.makeText(this, "Unkonw button ID " + view.getId(), Toast.LENGTH_SHORT).show();
                return;
        }

        setResult(RESULT_OK);
        finish();
    }
}
