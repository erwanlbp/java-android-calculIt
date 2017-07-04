package com.erwanlbp.calculit.model;

import android.net.Uri;

import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class User {

    // ----- Singleton -----

    private static User user;

    private User() {
        this.firebaseUser = null;
        this.highscores = new HashMap<>();
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }


    // ----- Fields -----

    // FirebaseUser contains the id, name, email
    private FirebaseUser firebaseUser;

    public void authentified(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public boolean isAuthentified() {
        return this.firebaseUser != null;
    }

    public String getID() {
        return firebaseUser.getUid();
    }

    public String getName() {
        return firebaseUser.getDisplayName();
    }

    public String getEmail() {
        return firebaseUser.getEmail();
    }

    public Uri getPhotoURL() {
        return firebaseUser.getPhotoUrl();
    }


    // highscores contains the highscore in every difficulty
    private Map<Difficulty, Integer> highscores;

    public int getHighScore(Difficulty difficulty) {
        return this.highscores.get(difficulty);
    }

    private void setHighScore(Difficulty difficulty, int newScore) {
        this.highscores.put(difficulty, newScore);
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
