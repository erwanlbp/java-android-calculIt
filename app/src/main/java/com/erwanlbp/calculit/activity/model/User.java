package com.erwanlbp.calculit.activity.model;

import com.erwanlbp.calculit.activity.MainActivity;

public class User {

    private String name;
    public static final String USER_NAME = MainActivity.APPNAME + "USER_NAME";

    private int highScore;
    public static final String USER_HIGH_SCORE = MainActivity.APPNAME + "USER_HIGH_SCORE";

    public User(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", highScore=" + highScore +
                '}';
    }
}
