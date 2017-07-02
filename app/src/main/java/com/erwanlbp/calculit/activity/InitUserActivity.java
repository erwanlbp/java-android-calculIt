package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.erwanlbp.calculit.R;

public class InitUserActivity extends AppCompatActivity {

    public static final String USER_INFO = MainActivity.APPNAME + "USER_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_user);
    }

    public void returnAnswer(View view) {
        final String name = ((EditText) findViewById(R.id.edSetName)).getText().toString();

        Intent intent = new Intent();
        intent.putExtra(USER_INFO, name);
        setResult(RESULT_OK, intent);
        finish();
    }
}
