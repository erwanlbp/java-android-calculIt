package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.erwanlbp.calculit.R;

public class AnswerActivity extends AppCompatActivity {

    public static final String USER_ANSWER = MainActivity.APPNAME + "USER_ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
    }

    public void returnAnswer(View view) {
        String answerStr = ((EditText) findViewById(R.id.etUserAnswer)).getText().toString();
        int answer;
        try {
            answer = Integer.parseInt(answerStr);
        } catch (NumberFormatException nfe) {
            TextView tvError = (TextView) findViewById(R.id.tvAnswerError);
            tvError.setText(R.string.label_error_answer);
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(USER_ANSWER, answer);
        setResult(RESULT_OK, intent);
        finish();
    }
}
