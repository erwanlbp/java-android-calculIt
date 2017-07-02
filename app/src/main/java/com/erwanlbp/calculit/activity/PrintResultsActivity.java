package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.R;

public class PrintResultsActivity extends AppCompatActivity {

    public static final int NEXT_LEVEL = 2;
    public static final int BACK_HOME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_results);

        Intent intent = getIntent();
        int correctResult = intent.getIntExtra(GameConfig.CONFIG_CORRECT_RESULT, 0);
        int userAnswer = intent.getIntExtra(AnswerActivity.USER_ANSWER, 0);
        int level = intent.getIntExtra(GameConfig.CONFIG_LEVEL, -1);

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
        }

    }

    public void backHome(View view) {
        Intent intent = new Intent();
        setResult(BACK_HOME, intent);
        finish();
    }

    public void nextLevel(View view) {
        setResult(NEXT_LEVEL);
        finish();
    }
}
