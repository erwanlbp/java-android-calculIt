package com.erwanlbp.calculit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.config.GameConfig;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static final String SAVE_FILE = APPNAME + "SAVE_FILE";

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.btnLaunchGame)
    Button btnLaunchGame;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (!logUser()) {
            startUserActivity();
        }

        loadSaveFile();

        setSupportActionBar(toolbarMain);

        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @OnClick(R.id.btnLaunchGame)
    public void startGame(View view) {
        final Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnMainDifficulty)
    public void startDifficulty(View view) {
        final Intent intent = new Intent(this, DifficultyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnMainHighscores)
    public void startPrintHighScores() {
        final Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnMainToUser)
    public void startUserActivity() {
        final Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent, RQ_USER);
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

    private void loadSaveFile() {
        User user = User.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SAVE_FILE, Context.MODE_PRIVATE);
        final String userID = user.getID();
        final int level = sharedPreferences.getInt(userID + AnswerActivity.LOCAL_SAVE_LEVEL, 0);
        final String difficultyStr = sharedPreferences.getString(userID + AnswerActivity.LOCAL_SAVE_DIFFICULTY, "EASY");
        Difficulty difficulty;
        try {
            difficulty = Difficulty.parse(difficultyStr);
        } catch (Exception e) {
            Toast.makeText(this, "Error loading save file", Toast.LENGTH_SHORT).show();
            return;
        }

        GameConfig.loadConfig(difficulty, level);
    }

    private void updateUI() {
        if (GameConfig.hasGameInProgress()) {
            btnLaunchGame.setText(R.string.continue_game);
        } else {
            btnLaunchGame.setText(R.string.launch_a_game);
        }
    }
}
