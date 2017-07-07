package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.erwanlbp.calculit.config.ActivityCode;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.LocalSave;

public class PrintResultsActivity extends AppCompatActivity {

    private LocalSave localSave;
    private boolean correctAnswer;
    public static final String CORRECT_ANSWER = MainActivity.APPNAME + "CORRECT_ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_results);

        Intent intent = getIntent();
        final int correctResult = intent.getIntExtra(GameConfig.CONFIG_CORRECT_RESULT, 0);
        final int userAnswer = intent.getIntExtra(AnswerActivity.USER_ANSWER, 0);
        final int level = intent.getIntExtra(GameConfig.CONFIG_LEVEL, -1);
        final String difficulty = intent.getStringExtra(GameConfig.CONFIG_DIFFICULTY);

        TextView tvResultAnswer = (TextView) findViewById(R.id.tvResultAnswer);
        if (correctResult == userAnswer)
            tvResultAnswer.setText(R.string.label_correct_answer);
        else
            tvResultAnswer.setText(R.string.label_wrong_answer);

        TextView tvUserAnswer = (TextView) findViewById(R.id.tvYourAnswer);
        tvUserAnswer.setText(String.valueOf(userAnswer));

        TextView tvCorrectResult = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvCorrectResult.setText(String.valueOf(correctResult));

        TextView tvLevel = (TextView) findViewById(R.id.tvPrintResultLevelValue);
        tvLevel.setText(String.valueOf(level));

        if (correctResult != userAnswer) {
            Button buttonNextLevel = (Button) findViewById(R.id.button_print_results_next_level);
            buttonNextLevel.setEnabled(false);
            correctAnswer = false;
        } else {
            localSave.saveCurrentGame(level, difficulty);
            correctAnswer = true;
        }
    }

    public void backHome(View view) {
        Intent intent = new Intent();
        intent.putExtra(CORRECT_ANSWER, correctAnswer);
        setResult(ActivityCode.RC_BACK_HOME, intent);
        finish();
    }

    public void nextLevel(View view) {
        setResult(ActivityCode.RC_NEXT_LEVEL);
        finish();
    }
}
