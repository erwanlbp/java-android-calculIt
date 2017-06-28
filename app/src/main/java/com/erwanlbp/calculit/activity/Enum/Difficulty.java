package com.erwanlbp.calculit.activity.Enum;

public enum Difficulty {

    EASY(1000),
    MEDIUM(800),
    HARD(600),
    GOD(500);

    private final int timeToPrint;

    Difficulty(int timeToPrint) {
        this.timeToPrint = timeToPrint;
    }

    public int getValue() {
        return this.timeToPrint;
    }

    public static Difficulty parse(final String difficulty) {
        return Difficulty.valueOf(difficulty);
    }

    public static Difficulty parse(final int difficulty) {
        switch (difficulty) {
            case 1000:
                return EASY;
            case 800:
                return MEDIUM;
            case 600:
                return HARD;
            case 500:
                return GOD;
            default:
                return EASY;
        }
    }
}


