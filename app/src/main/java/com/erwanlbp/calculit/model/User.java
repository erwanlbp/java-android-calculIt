package com.erwanlbp.calculit.model;

import android.net.Uri;

import com.erwanlbp.calculit.activity.MainActivity;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private FirebaseUser firebaseUser;

    private int highScoreEasy;
    public static final String USER_HIGH_SCORE_EASY = MainActivity.APPNAME + "USER_HIGH_SCORE_EASY";

    private int highScoreMedium;
    public static final String USER_HIGH_SCORE_MEDIUM = MainActivity.APPNAME + "USER_HIGH_SCORE_MEDIUM";

    private int highScoreHard;
    public static final String USER_HIGH_SCORE_HARD = MainActivity.APPNAME + "USER_HIGH_SCORE_HARD";

    public User() {
        this(null, 0, 0, 0);
    }

    public User(FirebaseUser firebaseUser, int highScoreEasy, int highScoreMedium, int highScoreHard) {
        this.firebaseUser = firebaseUser;
        this.highScoreEasy = highScoreEasy;
        this.highScoreMedium = highScoreMedium;
        this.highScoreHard = highScoreHard;
    }

    public String getName() {
        return firebaseUser.getDisplayName();
    }

    public int getHighScore(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY)
            return getHighScoreEasy();
        if (difficulty == Difficulty.MEDIUM)
            return getHighScoreMedium();
        if (difficulty == Difficulty.HARD)
            return getHighScoreHard();
        return -1;
    }

    private void setHighScore(Difficulty difficulty, int newScore) {
        if (difficulty == Difficulty.EASY)
            this.highScoreEasy = newScore;
        if (difficulty == Difficulty.MEDIUM)
            this.highScoreMedium = newScore;
        if (difficulty == Difficulty.HARD)
            this.highScoreHard = newScore;
    }

    public int getHighScoreEasy() {
        return highScoreEasy;
    }


    public int getHighScoreMedium() {
        return highScoreMedium;
    }


    public int getHighScoreHard() {
        return highScoreHard;
    }

    public String getEmail() {
        return firebaseUser.getEmail();
    }

    public void updateEmail(String email) {
        firebaseUser.updateEmail(email);
    }

    public Uri getPhotoURL() {
        return firebaseUser.getPhotoUrl();
    }

    public boolean updateHighScore(Difficulty difficulty, int newScore) {
        if (getHighScore(difficulty) < newScore) {
            setHighScore(difficulty, newScore);
            FirebaseDB.getFireBaseDB().updateHighScore(this.firebaseUser.getUid(), difficulty, newScore);
            return true;
        }
        return false;
    }
}
