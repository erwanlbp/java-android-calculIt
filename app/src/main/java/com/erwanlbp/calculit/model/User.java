package com.erwanlbp.calculit.model;

import com.erwanlbp.calculit.activity.MainActivity;
import com.erwanlbp.calculit.enums.Difficulty;

public class User {

    private String email;

    private String pseudo;
    public static final String USER_PSEUDO = MainActivity.APPNAME + "USER_PSEUDO";

    private int highScoreEasy;
    public static final String USER_HIGH_SCORE_EASY = MainActivity.APPNAME + "USER_HIGH_SCORE_EASY";

    private int highScoreMedium;
    public static final String USER_HIGH_SCORE_MEDIUM = MainActivity.APPNAME + "USER_HIGH_SCORE_MEDIUM";

    private int highScoreHard;
    public static final String USER_HIGH_SCORE_HARD = MainActivity.APPNAME + "USER_HIGH_SCORE_HARD";

    private boolean isAuthentified;

    public User() {
    }

    public User(String email, String pseudo, int highScoreEasy, int highScoreMedium, int highScoreHard) {
        this.email = email;
        this.pseudo = pseudo;
        this.highScoreEasy = highScoreEasy;
        this.highScoreMedium = highScoreMedium;
        this.highScoreHard = highScoreHard;
        this.isAuthentified = false;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getHighScoreEasy() {
        return highScoreEasy;
    }

    public void setHighScoreEasy(int highScoreEasy) {
        this.highScoreEasy = highScoreEasy;
    }

    public int getHighScoreMedium() {
        return highScoreMedium;
    }

    public void setHighScoreMedium(int highScoreMedium) {
        this.highScoreMedium = highScoreMedium;
    }

    public int getHighScoreHard() {
        return highScoreHard;
    }

    public void setHighScoreHard(int highScoreHard) {
        this.highScoreHard = highScoreHard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthentified() {
        return isAuthentified;
    }

    public void authentify() {
        this.isAuthentified = true;
    }

    public boolean updateHighScore(Difficulty difficulty, int newScore) {
        if (difficulty == Difficulty.EASY)
            if (this.highScoreEasy < newScore) {
                this.highScoreEasy = newScore;
                return true;
            }
        if (difficulty == Difficulty.MEDIUM)
            if (this.highScoreMedium < newScore) {
                this.highScoreMedium = newScore;
                return true;
            }
        if (difficulty == Difficulty.HARD)
            if (this.highScoreHard < newScore) {
                this.highScoreHard = newScore;
                return true;
            }
        return false;
    }
}
