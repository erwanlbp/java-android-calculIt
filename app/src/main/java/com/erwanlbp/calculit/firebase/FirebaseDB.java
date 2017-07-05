package com.erwanlbp.calculit.firebase;

import android.util.Log;
import android.util.Pair;

import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDB {

    // ----- Singleton -----
    private static FirebaseDB firebaseDB;

    private FirebaseDB() {
        this.database = FirebaseDatabase.getInstance().getReference();
        this.usersReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public static FirebaseDB getFireBaseDB() {
        if (firebaseDB == null) {
            firebaseDB = new FirebaseDB();
        }
        return firebaseDB;
    }
    // ----- Singleton end -----


    private static final String TAG = "FirebaseDB";

    private DatabaseReference database;
    private DatabaseReference usersReference;

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
                    Object highscore = dataSnapshot.child("highscore-" + difficulty.toString()).getValue();
                    if (highscore != null)
                        userInner.setHighScore(difficulty, (long) highscore);
                }
                Log.i(TAG, "Get user " + userInner.getID());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Cancelled getUserDatas");
            }
        });
    }

    public void updateHighScore(Difficulty difficulty, long newScore) {
        User user = User.getInstance();
        // Only authentified user can save highscores
        if (!user.isAuthentified())
            return;

        Map<String, Object> highScoreUpdates = new HashMap<>();
        highScoreUpdates.put("/users/" + user.getID() + "/highscore-" + difficulty.toString(), newScore);
        highScoreUpdates.put("/highscores-" + difficulty.toString() + "/" + user.getID() + "/name", user.getName());
        highScoreUpdates.put("/highscores-" + difficulty.toString() + "/" + user.getID() + "/score", newScore);
        this.database.updateChildren(highScoreUpdates);
    }

    public void deleteAll() {
        User user = User.getInstance();
        if (!user.isAuthentified())
            return;

        this.usersReference.child(user.getID()).removeValue();
        for (Difficulty difficulty : Difficulty.values())
            this.database.child("highscores-" + difficulty.toString()).child(user.getID()).removeValue();
    }

    public List<Pair<String, Integer>> getHighScores(Difficulty difficulty) {
//        List<Pair<String, Integer>> highscores = new ArrayList<>();
//
//        FirebaseDatabase.getInstance().getReference("highscore-"+difficulty.toString())
//
//
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Log.i(TAG, "Get user " + firebaseUser.getUid());
//                        User user = dataSnapshot.getValue(User.class);
//                        if (user != null) {
//                            highscores[0] = user.getHighScoreEasy();
//                            highscores[1] = user.getHighScoreMedium();
//                            highscores[2] = user.getHighScoreHard();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "Failed getting user " + firebaseUser.getUid());
//                    }
//                });

        return null;
    }
}
