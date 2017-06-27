package com.erwanlbp.calculit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.erwanlbp.calculit.R;

public class AnswerActivity extends AppCompatActivity {

    public static final int ASK_USER_ANSWER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
    }
}
