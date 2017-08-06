package com.erwanlbp.calculit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;

public class AnswerActivity extends BaseActivity {

    public static final String LOCAL_SAVE_LEVEL = "/level";
    public static final String LOCAL_SAVE_DIFFICULTY = "/difficulty";

    private boolean answerIsCorrect;
    private boolean hasAnswered;
    private int userAnswer;

    private GameConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_answer));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        config = GameConfig.getConfig();

        hasAnswered = false;
        answerIsCorrect = false;

        ((TextView) findViewById(R.id.tvAnswerLevelValue)).setText(String.valueOf(config.getLevel()));

        updateUI();
    }

    public void returnAnswer(View view) {
        // Hide keyboard
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

        String answerStr = ((EditText) findViewById(R.id.etUserAnswer)).getText().toString();
        try {
            userAnswer = Integer.parseInt(answerStr);
        } catch (NumberFormatException nfe) {
            return;
        }

        hasAnswered = true;
        answerIsCorrect = (userAnswer == config.getCorrectResult());

        updateUI();

        if (answerIsCorrect) {
            User.getInstance().updateHighScore(config.getDifficulty(), config.getLevel());
            saveCurrentGame(config.getLevel(), config.getDifficulty());
        } else {
            GameConfig.getConfig().reset();
            deleteSavedGame();
        }
    }

    private void updateUI() {
        if (hasAnswered) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((TextView) findViewById(R.id.tvAnswerCorrectOrWrong)).setText(answerIsCorrect ? R.string.label_correct_answer : R.string.label_wrong_answer);
            ((TextView) findViewById(R.id.tvUserAnswer)).setText(String.valueOf(userAnswer));
            if (answerIsCorrect)
                findViewById(R.id.tvUserAnswer).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else {
                // If answer is not correct : show also the correct one
                TextView correctAnswer = (TextView) findViewById(R.id.tvCorrectAnswer);
                correctAnswer.setText(String.valueOf(config.getCorrectResult()));
                correctAnswer.setVisibility(View.VISIBLE);
                findViewById(R.id.tvAnswerDifferent).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.layoutResults).setVisibility(View.VISIBLE);
            findViewById(R.id.layoutAnswer).setVisibility(View.GONE);
            if (answerIsCorrect) {
                findViewById(R.id.btnAnswerNextLevel).setVisibility(View.VISIBLE);
                findViewById(R.id.btnAnswerRestartGame).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.layoutAnswerBtns).setVisibility(View.VISIBLE);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        // If user didn't answer he can't return home
        if (!hasAnswered) return;
        // FIXME If user quit app and restart, he can continue it's play

        super.onBackPressed();
        backHome(null);
    }

    public void backHome(View view) {
        finish();
    }

    public void restartGame(View view) {
        GameConfig.loadConfig(config.getDifficulty());
        nextLevel(null);
    }

    public void nextLevel(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveCurrentGame(final int level, final Difficulty difficulty) {
        User user = User.getInstance();
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(MainActivity.SAVE_FILE, MODE_PRIVATE).edit();
        sharedPreferences.putInt(user.getID() + LOCAL_SAVE_LEVEL, level);
        sharedPreferences.putString(user.getID() + LOCAL_SAVE_DIFFICULTY, difficulty.toString());
        sharedPreferences.apply();
    }

    private void deleteSavedGame() {
        User user = User.getInstance();
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(MainActivity.SAVE_FILE, MODE_PRIVATE).edit();
        sharedPreferences.remove(user.getID() + LOCAL_SAVE_LEVEL);
        sharedPreferences.remove(user.getID() + LOCAL_SAVE_DIFFICULTY);
        sharedPreferences.apply();
    }
}
