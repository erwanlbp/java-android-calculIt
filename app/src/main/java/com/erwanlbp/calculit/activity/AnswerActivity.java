package com.erwanlbp.calculit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerActivity extends BaseActivity {

    public static final String LOCAL_SAVE_LEVEL = "/level";
    public static final String LOCAL_SAVE_DIFFICULTY = "/difficulty";

    private boolean answerIsCorrect;
    private boolean hasAnswered;
    private int userAnswer;

    @BindView(R.id.tvUserAnswer)
    TextView tvUserAnswer;
    @BindView(R.id.tvAnswerLevelValue)
    TextView tvAnswerLevelValue;
    @BindView(R.id.etUserAnswer)
    EditText etUserAnswer;
    @BindView(R.id.tvAnswerCorrectOrWrong)
    TextView tvAnswerCorrectOrWrong;
    @BindView(R.id.tvCorrectAnswer)
    TextView tvCorrectAnswer;
    @BindView(R.id.tvAnswerDifferent)
    TextView tvAnswerDifferent;
    @BindView(R.id.layoutResults)
    View layoutResults;
    @BindView(R.id.layoutAnswer)
    View layoutAnswer;
    @BindView(R.id.btnAnswerNextLevel)
    Button btnAnswerNextLevel;
    @BindView(R.id.btnAnswerRestartGame)
    Button btnAnswerRestartGame;
    @BindView(R.id.layoutAnswerBtns)
    View layoutAnswerBtns;
    @BindView(R.id.toolbar_answer)
    Toolbar toolbarAnswer;

    private GameConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        ButterKnife.bind(this);

        setSupportActionBar(toolbarAnswer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        config = GameConfig.getConfig();

        hasAnswered = false;
        answerIsCorrect = false;

        tvAnswerLevelValue.setText(String.valueOf(config.getLevel()));

        updateUI();
    }

    @OnClick(R.id.btnAnswerGo)
    public void returnAnswer(View view) {
        // Hide keyboard
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

        String answerStr = etUserAnswer.getText().toString();
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
            tvAnswerCorrectOrWrong.setText(answerIsCorrect ? R.string.label_correct_answer : R.string.label_wrong_answer);
            tvUserAnswer.setText(String.valueOf(userAnswer));
            if (answerIsCorrect)
                tvUserAnswer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else {
                // If answer is not correct : show also the correct one
                tvCorrectAnswer.setText(String.valueOf(config.getCorrectResult()));
                tvCorrectAnswer.setVisibility(View.VISIBLE);
                tvAnswerDifferent.setVisibility(View.VISIBLE);
            }
            layoutResults.setVisibility(View.VISIBLE);
            layoutAnswer.setVisibility(View.GONE);
            if (answerIsCorrect) {
                btnAnswerNextLevel.setVisibility(View.VISIBLE);
                btnAnswerRestartGame.setVisibility(View.VISIBLE);
            }
            layoutAnswerBtns.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btnAnswerHome)
    public void backHome(View view) {
        finish();
    }

    @OnClick(R.id.btnAnswerRestartGame)
    public void restartGame(View view) {
        GameConfig.loadConfig(config.getDifficulty());
        nextLevel(null);
    }

    @OnClick(R.id.btnAnswerNextLevel)
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
