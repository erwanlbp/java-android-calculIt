package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.erwanlbp.calculit.config.ActivityCode;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String APPNAME = "com.erwanlbp.calculit";
    private GameConfig gameConfig;
    private User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, InitUserActivity.class);
        startActivityForResult(intent, ActivityCode.INIT_USER);
    }

    public void launchGame(View view) {

        if (this.gameConfig == null)
            this.gameConfig = new GameConfig();

        Map<String, Integer> mapGameConfig = this.gameConfig.getParamsMap();
        ArrayList<Integer> numbers = this.gameConfig.getNumbers();

        final Intent intent = new Intent(this, GameActivity.class);
        intent.putIntegerArrayListExtra(GameConfig.CONFIG_NUMBERS, numbers);

        for (Map.Entry<String, Integer> entry : mapGameConfig.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }

        startActivityForResult(intent, ActivityCode.GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.GAME) {
            if (resultCode == RESULT_OK)
                startAskAnswer();
        }
        if (requestCode == ActivityCode.ANSWER) {
            if (resultCode == RESULT_OK) {
                int userAnswer = data.getIntExtra(AnswerActivity.USER_ANSWER, 0);
                startPrintResults(userAnswer);
            }
        }
        if (requestCode == ActivityCode.SELECT_DIFFICULTY) {
            if (resultCode == RESULT_OK) {
                createGameConfig(data);
            }
        }
        if (requestCode == ActivityCode.SHOW_RESULT) {
            if (resultCode == PrintResultsActivity.NEXT_LEVEL) {
                gameConfig = gameConfig.nextLevel();
                launchGame(null);
            }
            if (resultCode == PrintResultsActivity.BACK_HOME) {
                // TODO Finished Play
                // Finished playing, so last level is the current one
                this.user.updateHighScore(gameConfig.getDifficulty(), gameConfig.getLevel());
                // Reset game config
                gameConfig = new GameConfig(gameConfig.getDifficulty());
            }
        }
        if (requestCode == ActivityCode.INIT_USER) {
            if (resultCode == RESULT_OK) {
                this.user = new User(data.getStringExtra(InitUserActivity.USER_EMAIL), data.getStringExtra(InitUserActivity.USER_INFO), 0, 0, 0);
            }
        }
    }

    private void startAskAnswer() {
        Intent intent = new Intent(this, AnswerActivity.class);
        startActivityForResult(intent, ActivityCode.ANSWER);
    }

    private void startPrintResults(int userAnswer) {
        int correctResult = gameConfig.getCorrectResult();
        Intent intent = new Intent(this, PrintResultsActivity.class);
        intent.putExtra(AnswerActivity.USER_ANSWER, userAnswer);
        intent.putExtra(GameConfig.CONFIG_CORRECT_RESULT, correctResult);
        intent.putExtra(GameConfig.CONFIG_LEVEL, gameConfig.getLevel());
        startActivityForResult(intent, ActivityCode.SHOW_RESULT);
    }

    public void selectDifficultyActivity(View view) {
        final Intent intent = new Intent(this, SelectDifficultyActivity.class);
        startActivityForResult(intent, ActivityCode.SELECT_DIFFICULTY);
    }

    private void createGameConfig(final Intent data) {
        final Difficulty difficulty = Difficulty.parse(data.getIntExtra(SelectDifficultyActivity.DIFFICULTY, Difficulty.EASY.getTimeToPrint()));
        gameConfig = new GameConfig(difficulty);
    }

    public void startPrintHighScores(View view) {
        final Intent intent = new Intent(this, PrintHighScoresActivity.class);
        intent.putExtra(User.USER_PSEUDO, user.getPseudo());
        intent.putExtra(User.USER_HIGH_SCORE_EASY, user.getHighScoreEasy());
        intent.putExtra(User.USER_HIGH_SCORE_MEDIUM, user.getHighScoreMedium());
        intent.putExtra(User.USER_HIGH_SCORE_HARD, user.getHighScoreHard());
        startActivityForResult(intent, ActivityCode.SHOW_HIGH_SCORE);
    }
}
