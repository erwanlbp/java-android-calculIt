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
        for(Difficulty difficulty : Difficulty.values())
            highscores.put(difficulty, 0L);
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }
    // ----- Singleton end -----


    // FirebaseUser contains the id, name, email
    private FirebaseUser firebaseUser;

    public void authentified(FirebaseUser firebaseUser) {
        // TODO [MAYBE] Mettre le Firebase.getUserDatas ici ?
        this.firebaseUser = firebaseUser;
    }

    public boolean isAuthentified() {
        return this.firebaseUser != null;
    }

    public String getID() {
        return isAuthentified() ? firebaseUser.getUid() : "";
    }

    public String getName() {
        return isAuthentified() ? firebaseUser.getDisplayName() : "";
    }

    public String getEmail() {
        return isAuthentified() ? firebaseUser.getEmail() : "";
    }

    public Uri getPhotoURL() {
        return isAuthentified() ? firebaseUser.getPhotoUrl() : null;
    }


    // highscores contains the highscore in every difficulty
    private Map<Difficulty, Long> highscores;

    public long getHighScore(Difficulty difficulty) {
        return this.highscores.get(difficulty);
    }

    public void setHighScore(Difficulty difficulty, long newScore) {
        this.highscores.put(difficulty, newScore);
    }

    public boolean updateHighScore(Difficulty difficulty, long newScore) {
        if (getHighScore(difficulty) < newScore) {
            setHighScore(difficulty, newScore);
            FirebaseDB.getFireBaseDB().updateHighScore(difficulty, newScore);
            return true;
        }
        return false;
    }
}
