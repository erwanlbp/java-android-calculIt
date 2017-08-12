package com.erwanlbp.calculit.firebase;

import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {

    private static final String TAG = "FirebaseDB";

    // ----- Firebase paths -----

    // -- HIGHSCORES --
    public static final String HIGHSCORES = "highscores-";
    public static final String HIGHSCORES_SCORE = "score";
    public static final String HIGHSCORES_NAME = "name";

    // -- USER --
    public static final String USERS = "users";
    public static final String USER_HIGHSCORE = "highscore-";


    private DatabaseReference database;
    private DatabaseReference usersReference;

    // ----- Singleton -----
    private static FirebaseDB firebaseDB;

    private FirebaseDB() {
        this.database = FirebaseDatabase.getInstance().getReference();
        this.usersReference = FirebaseDatabase.getInstance().getReference(USERS);
    }

    public static FirebaseDB getFireBaseDB() {
        if (firebaseDB == null) {
            firebaseDB = new FirebaseDB();
        }
        return firebaseDB;
    }
    // ----- Singleton end -----


    public void getUserDatas() {
        User user = User.getInstance();
        if (!user.isAuthentified()) {
            return;
        }

        usersReference.child(user.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userInner = User.getInstance();
                for (Difficulty difficulty : Difficulty.values()) {
                    Object highscore = dataSnapshot.child(USER_HIGHSCORE + difficulty.toString()).getValue();
                    if (highscore != null)
                        userInner.setHighScore(difficulty, (long) highscore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO Log error
            }
        });
    }

    public void updateHighScore(Difficulty difficulty, long newScore) {
        User user = User.getInstance();
        // Only authentified user can save highscores
        if (!user.isAuthentified())
            return;

        Map<String, Object> highScoreUpdates = new HashMap<>();
        highScoreUpdates.put("/" + USERS + "/" + user.getID() + "/" + USER_HIGHSCORE + difficulty.toString(), newScore);
        highScoreUpdates.put("/" + HIGHSCORES + difficulty.toString() + "/" + user.getID() + "/" + HIGHSCORES_NAME, user.getName());
        highScoreUpdates.put("/" + HIGHSCORES + difficulty.toString() + "/" + user.getID() + "/" + HIGHSCORES_SCORE, newScore);
        this.database.updateChildren(highScoreUpdates);
        // Order the highscores by the scores
        this.database.child(HIGHSCORES + difficulty.toString()).child(user.getID()).setPriority(-newScore); // TODO [TEST] pas sur que le -newscore marche
    }

    public void deleteAll() {
        User user = User.getInstance();
        if (!user.isAuthentified())
            return;

        this.usersReference.child(user.getID()).removeValue();
        for (Difficulty difficulty : Difficulty.values())
            this.database.child(HIGHSCORES + difficulty.toString()).child(user.getID()).removeValue();
    }
}
