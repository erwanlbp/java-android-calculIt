package com.erwanlbp.calculit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.enums.Difficulty;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.erwanlbp.calculit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HighscoresActivity extends BaseActivity {
    private static final String TAG = "HighscoresActivity";
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerHS);
        tableLayout = (TableLayout) findViewById(R.id.tableLayoutHS);

        showPersonnalHighScores();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int selectedIndex = spinner.getSelectedItemPosition();
                if (selectedIndex > 0) {
                    try {
                        final Difficulty difficulty = Difficulty.values()[selectedIndex - 1];
                        showGlobalHighScores(difficulty);
                        return;
                    } catch (Exception e) {
                        Log.e(TAG, "Error selecting index");
                    }
                }
                showPersonnalHighScores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                showPersonnalHighScores();
            }
        });
    }

    private void showPersonnalHighScores() {
        tableLayout.removeAllViewsInLayout();
        Log.i(TAG, "showPersonnalHighscores");
        for (Difficulty difficulty : Difficulty.values()) {
            TableRow row = new TableRow(this);

            TextView tvDifficulty = new TextView(this);
            tvDifficulty.setGravity(Gravity.CENTER);
            tvDifficulty.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvDifficulty.setText(difficulty.toString());
            row.addView(tvDifficulty);

            TextView tvHighScore = new TextView(this);
            tvHighScore.setGravity(Gravity.CENTER);
            tvHighScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            tvHighScore.setText(String.valueOf(User.getInstance().getHighScore(difficulty)));
            row.addView(tvHighScore);

            tableLayout.addView(row);
        }
    }

    private void showGlobalHighScores(final Difficulty difficulty) {
        tableLayout.removeAllViewsInLayout();
        Log.i(TAG, "showGlobalHighscores for " + difficulty.toString());

        FirebaseDatabase.getInstance().getReference(FirebaseDB.HIGHSCORES + difficulty.toString()).limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tableLayout.removeAllViewsInLayout();
                for (DataSnapshot userHighScore : dataSnapshot.getChildren()) {
                    TableRow row = new TableRow(HighscoresActivity.this);

                    TextView tvName = new TextView(HighscoresActivity.this);
                    tvName.setGravity(Gravity.CENTER);
                    tvName.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    tvName.setText(String.valueOf(userHighScore.child(FirebaseDB.HIGHSCORES_NAME).getValue()));
                    row.addView(tvName);

                    TextView tvHighScore = new TextView(HighscoresActivity.this);
                    tvHighScore.setGravity(Gravity.CENTER);
                    tvHighScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    tvHighScore.setText(String.valueOf(userHighScore.child(FirebaseDB.HIGHSCORES_SCORE).getValue()));
                    row.addView(tvHighScore);

                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed show highscores-" + difficulty.toString());
            }
        });


    }
}
