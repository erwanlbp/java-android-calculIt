package com.erwanlbp.calculit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.erwanlbp.calculit.config.ActivityCode;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String APPNAME = "com.erwanlbp.calculit";
    public static final String SAVE_FILE = APPNAME + "SAVE_FILE";
    private GameConfig gameConfig;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!logUser()) {
            startUserActivity(null);
        }

        updateUI(loadSaveFile());
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

        startActivityForResult(intent, ActivityCode.RQ_GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.RQ_GAME && resultCode == RESULT_OK) {
            startAskAnswer();
        }
        if (requestCode == ActivityCode.RQ_ANSWER && resultCode == RESULT_OK) {
            int userAnswer = data.getIntExtra(AnswerActivity.USER_ANSWER, 0);
            startPrintResults(userAnswer);
        }
        if (requestCode == ActivityCode.RQ_SELECT_DIFFICULTY && resultCode == RESULT_OK) {
            createGameConfig(data);
        }
        if (requestCode == ActivityCode.RQ_SHOW_RESULT) {
            if (resultCode == ActivityCode.RC_NEXT_LEVEL) {
                gameConfig = gameConfig.nextLevel();
                launchGame(null);
            }
            if (resultCode == ActivityCode.RC_BACK_HOME) {
                // TODO Finished Play
                User.getInstance().updateHighScore(gameConfig.getDifficulty(), gameConfig.getLevel() - 1); // -1 Cause it mean he failed this level
                if (data.getBooleanExtra(PrintResultsActivity.CORRECT_ANSWER, false)) {
                    gameConfig = gameConfig.nextLevel();
                    updateUI(true);
                } else {
                    // Reset game config
                    gameConfig = new GameConfig(gameConfig.getDifficulty());
                    updateUI(false);
                }
            }
        }
        if (requestCode == ActivityCode.RQ_USER) {
            if (resultCode == RESULT_OK) {
                logUser();
            } else {
                // We only deal with user connected
                // TODO [IMPROVE] Allow user no to be authentified
                startUserActivity(null);
            }
        }
    }

    private void startAskAnswer() {
        Intent intent = new Intent(this, AnswerActivity.class);
        startActivityForResult(intent, ActivityCode.RQ_ANSWER);
    }

    private void startPrintResults(int userAnswer) {
        int correctResult = gameConfig.getCorrectResult();
        Intent intent = new Intent(this, PrintResultsActivity.class);
        intent.putExtra(AnswerActivity.USER_ANSWER, userAnswer);
        intent.putExtra(GameConfig.CONFIG_CORRECT_RESULT, correctResult);
        intent.putExtra(GameConfig.CONFIG_LEVEL, gameConfig.getLevel());
        intent.putExtra(GameConfig.CONFIG_DIFFICULTY, gameConfig.getDifficulty().toString());
        startActivityForResult(intent, ActivityCode.RQ_SHOW_RESULT);
    }

    public void selectDifficultyActivity(View view) {
        final Intent intent = new Intent(this, SelectDifficultyActivity.class);
        startActivityForResult(intent, ActivityCode.RQ_SELECT_DIFFICULTY);
    }

    private void createGameConfig(final Intent data) {
        final Difficulty difficulty = Difficulty.parse(data.getIntExtra(SelectDifficultyActivity.DIFFICULTY, Difficulty.EASY.getTimeToPrint()));
        gameConfig = new GameConfig(difficulty);
    }

    public void startPrintHighScores(View view) {
        final Intent intent = new Intent(this, PrintHighScoresActivity.class);
        startActivityForResult(intent, ActivityCode.RQ_SHOW_HIGH_SCORE);
    }

    public void startUserActivity(View view) {
        final Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent, ActivityCode.RQ_USER);
    }

    public boolean logUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            return false;

        User.getInstance().authentified(firebaseUser);
        FirebaseDB.getFireBaseDB().getUserDatas();
        // TODO [CHANGE] when scores are stored locally

        return true;
    }

    private boolean loadSaveFile() {
        final User user = User.getInstance();
        final SharedPreferences sharedPreferences = this.getSharedPreferences(SAVE_FILE, Context.MODE_PRIVATE);
        final String userID = user.getID();
        final int highScore = sharedPreferences.getInt(userID + "/highScore", 0);
        final String difficulty = sharedPreferences.getString(userID + "/difficulty", "EASY");

        this.gameConfig = new GameConfig(Difficulty.parse(difficulty), highScore);

        return highScore != 0;
    }

    private void updateUI(boolean continueGame) {
        TextView textView = (TextView) findViewById(R.id.buttonLaunchGame);
        if(continueGame) {
            textView.setText(R.string.continue_game);
        } else {
            textView.setText(R.string.launch_a_game);
        }
    }
}
