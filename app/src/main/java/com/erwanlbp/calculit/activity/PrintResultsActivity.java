package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.erwanlbp.calculit.GameConfig;
import com.erwanlbp.calculit.R;

import org.w3c.dom.Text;

public class PrintResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_results);

        Intent intent = getIntent();
        int correctResult = intent.getIntExtra(GameConfig.CONFIG_CORRECT_RESULT, 0);
        int userAnswer = intent.getIntExtra(AnswerActivity.USER_ANSWER, correctResult - 1);  // To be sure that the default value is wrong

        TextView tvResultAnswer = (TextView) findViewById(R.id.tvResultAnswer);
        if (correctResult == userAnswer)
            tvResultAnswer.setText(R.string.label_correct_answer);
        else
            tvResultAnswer.setText(R.string.label_wrong_answer);

        TextView tvUserAnswer = (TextView) findViewById(R.id.tvYourAnswer);
        tvUserAnswer.setText(String.valueOf(userAnswer));

        TextView tvCorrectResult = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvCorrectResult.setText(String.valueOf(correctResult));
    }

    public void backHome(View view) {
        finish();
    }
}
