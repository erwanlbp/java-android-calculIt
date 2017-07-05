package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.erwanlbp.calculit.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintHighScoresActivity extends AppCompatActivity {

    private Map<Difficulty, Integer> highscores;

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_high_score);

        Intent intent = getIntent();
        highscores = new HashMap<>();

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerHS);
        tableLayout = (TableLayout) findViewById(R.id.tableLayoutHS);

        showPersonnalHighScores();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final String difficultyStr = spinner.getSelectedItem().toString();
                try {
                    final Difficulty difficulty = Difficulty.parse(difficultyStr);
                    showGlobalHighScores(difficulty);
                } catch (IllegalArgumentException iae) {
                    showPersonnalHighScores();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                showPersonnalHighScores();
            }
        });
    }

    private void showPersonnalHighScores() {
        tableLayout.removeAllViewsInLayout();
        for (Difficulty difficulty : Difficulty.values()) {
            TableRow row = new TableRow(this);

            TextView tvDifficulty = new TextView(this);
            tvDifficulty.setGravity(Gravity.CENTER);
            tvDifficulty.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvDifficulty.setText(difficulty.toString());
            row.addView(tvDifficulty);

            TextView tvHighScore = new TextView(this);
            tvHighScore.setGravity(Gravity.CENTER);
            tvHighScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvHighScore.setText(String.valueOf(highscores.get(difficulty)));
            row.addView(tvHighScore);

            tableLayout.addView(row);
        }
    }

    private void showGlobalHighScores(Difficulty difficulty) {
        List<Pair<String, Integer>> highscores = FirebaseDB.getFireBaseDB().getHighScores(difficulty);

        tableLayout.removeAllViewsInLayout();
        for (Pair<String, Integer> highScore : highscores) {
            TableRow row = new TableRow(this);

            TextView tvName = new TextView(this);
            tvName.setGravity(Gravity.CENTER);
            tvName.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvName.setText(highScore.first);
            row.addView(tvName);

            TextView tvHighScore = new TextView(this);
            tvHighScore.setGravity(Gravity.CENTER);
            tvHighScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvHighScore.setText(String.valueOf(highScore.second));
            row.addView(tvHighScore);

            tableLayout.addView(row);
        }
    }
}
