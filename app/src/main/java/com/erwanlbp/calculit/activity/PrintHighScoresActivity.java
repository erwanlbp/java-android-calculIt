package com.erwanlbp.calculit.activity;

import android.content.Intent;
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
import com.erwanlbp.calculit.model.User;

public class PrintHighScoresActivity extends AppCompatActivity {

    private int highScoreEasy;
    private int highScoreMedium;
    private int highScoreHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_high_score);

        Intent intent = getIntent();
        // TODO Do something with the pseudo
        String pseudo = intent.getStringExtra(User.USER_PSEUDO);
        highScoreEasy = intent.getIntExtra(User.USER_HIGH_SCORE_EASY, 0);
        highScoreMedium = intent.getIntExtra(User.USER_HIGH_SCORE_MEDIUM, 0);
        highScoreHard = intent.getIntExtra(User.USER_HIGH_SCORE_HARD, 0);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerHS);

        //TODO a changer : initGlobalHS();
        changeHighScore(Difficulty.EASY);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final String difficultyStr = spinner.getSelectedItem().toString();
                final Difficulty difficulty = Difficulty.parse(difficultyStr);
                changeHighScore(difficulty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //TODO to improve
            }
        });
    }

    private void changeHighScore(Difficulty difficulty) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutHS);
        TableRow row = new TableRow(this);

        TextView textViewName = new TextView(this);
        textViewName.setGravity(Gravity.CENTER);
        textViewName.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView textViewHighScore = new TextView(this);
        textViewHighScore.setGravity(Gravity.CENTER);
        textViewHighScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        row.addView(textViewName);
        row.addView(textViewHighScore);

        tableLayout.addView(row);

        switch (difficulty) {
            case MEDIUM:
                // Get data firebase high score Medium
                textViewName.setText(Difficulty.MEDIUM.toString());
                textViewHighScore.setText(String.valueOf(highScoreMedium));
                break;
            case HARD:
                // Get data firebase high score Hard
                textViewName.setText(Difficulty.HARD.toString());
                textViewHighScore.setText(String.valueOf(highScoreHard));
                break;
            default: // Case EASY
                // Get data firebase high score Easy
                textViewName.setText(Difficulty.EASY.toString());
                textViewHighScore.setText(String.valueOf(highScoreEasy));
                break;
        }
    }
}
