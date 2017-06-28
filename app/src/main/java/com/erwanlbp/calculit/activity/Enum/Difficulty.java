package com.erwanlbp.calculit.activity.Enum;

public enum Difficulty {

    EASY(10),
    MEDIUM(20),
    HARD(30),
    GOD(50);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Difficulty parse(final String difficulty) {
        return Difficulty.valueOf(difficulty);
    }

    public static Difficulty parse(final int difficulty) {
        switch (difficulty) {
            case 10:
                return EASY;
            case 20:
                return MEDIUM;
            case 30:
                return HARD;
            case 50:
                return GOD;
            default:
                return EASY;
        }
    }
}


