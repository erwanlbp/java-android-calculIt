package com.erwanlbp.calculit.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.erwanlbp.calculit.activity.MainActivity;
import com.erwanlbp.calculit.model.User;


public class LocalSave extends AppCompatActivity {

    public void saveCurrentGame(final int level, final String difficulty) {
        final User user = User.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SAVE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(user.getID() + "/highScore", level + 1);
        editor.putString(user.getID() + "/difficulty", difficulty);
        editor.apply();
    }
}
