package com.erwanlbp.calculit.firebase;

import android.util.Log;
import android.util.Pair;

import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDB {

    private static final String TAG = "FirebaseDB";

    private static FirebaseDB firebaseDB;

    private DatabaseReference database;
    private DatabaseReference usersReference;

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

    public void getUserDatas() {
        User user = User.getInstance();
        if (!user.isAuthentified()) {
            return;
        }

        usersReference.child(user.getID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User userInner = User.getInstance();
                        Log.i(TAG, "Get user " + userInner.getID());

                        // TODO Get datas...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void updateHighScore(String userID, Difficulty difficulty, int newScore) {
        // TODO Get ID from Singleton
        Map<String, Object> highScoreUpdates = new HashMap<>();
        highScoreUpdates.put("/users/" + userID + "/highscore-" + difficulty.toString(), newScore);
//        highScoreUpdates.put("/highscore-"+difficulty.toString()+"/") // Use push()
        this.database.updateChildren(highScoreUpdates);
    }

    public void deleteAll(FirebaseUser user) {
        if (user == null) {
            return;
        }
        this.usersReference.child(user.getUid()).removeValue();
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
